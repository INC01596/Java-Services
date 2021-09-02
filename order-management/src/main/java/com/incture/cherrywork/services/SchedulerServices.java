

package com.incture.cherrywork.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.SalesOrderItem;
import com.incture.cherrywork.odata.dto.OdataSchHeaderStartDto;
import com.incture.cherrywork.odata.dto.OdataSchItemStartDto;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepository;
import com.incture.cherrywork.repositories.ISalesOrderItemRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.sales.constants.EnOrderActionStatus;
import com.incture.cherrywork.sales.constants.EnOverallDocumentStatus;
import com.incture.cherrywork.sales.constants.EnUpdateIndicator;
import com.incture.cherrywork.util.SequenceNumberGen;
import com.incture.cherrywork.util.ServicesUtil;

@Service("SchedulerServices")
@Transactional
public class SchedulerServices {

	@Autowired
	ISalesOrderHeaderRepository salesOrderHeaderRepository;

	@Autowired
	ISalesOrderItemRepository salesOrderItemRepository;

	@Autowired
	private SalesOrderOdataServices odataServices;

	@PersistenceContext
	private EntityManager entityManager;

	private SequenceNumberGen sequenceNumberGen;

//	@Scheduled(cron = "0 12 * * * ?")
	public ResponseEntity<Object> headerScheduler() {
		// logger.debug("[SalesHeaderDao][headerScheduler] Start : " + new
		// Date());
		System.err.println("[SalesHeaderDao][headerScheduler] Start : " + new Date());
		// Response response = new Response();
		ArrayList<String> list = new ArrayList<>();
		// Session session = null;
		// Transaction tx = null;
		try {
			OdataSchHeaderStartDto odataSchHeaderStartDto = odataServices.headerScheduler();
			System.err.println("odataSchHeaderStartDto" + odataSchHeaderStartDto.toString());
			List<SalesOrderHeaderDto> listSalesHeaderDto = salesOrderHeaderRepository
					.convertData(odataSchHeaderStartDto);
			for (SalesOrderHeaderDto salesHeaderDto : listSalesHeaderDto) {
				if (salesHeaderDto.getUpdateIndicator().equals(EnUpdateIndicator.DELETE)) {
					// session = sessionFactory.openSession();
					// tx = session.beginTransaction();
					Query query = entityManager
							.createQuery("delete from SalesOrderHeader s where s.s4DocumentId=:s4DocumentId");
					query.setParameter("s4DocumentId", salesHeaderDto.getS4DocumentId());
					query.executeUpdate();
					list.add("D-" + salesHeaderDto.getS4DocumentId());
					// session.flush();
					// session.clear();
					// tx.commit();
				} else {
					// session = sessionFactory.openSession();
					// tx = session.beginTransaction();
					Query query = entityManager.createQuery(
							"select s.tableKey from SalesOrderHeader s where s.s4DocumentId=:s4DocumentId");
					query.setParameter("s4DocumentId", salesHeaderDto.getS4DocumentId());
					List<String> objList = query.getResultList();
					// session.flush();
					// session.clear();
					// tx.commit();

					if (objList != null && objList.size() > 0) {
						// session = sessionFactory.openSession();
						// tx = session.beginTransaction();
						String tableKey = objList.get(0).toString();
						list.add("U-" + tableKey);
						// logger.debug("[SalesHeaderDao][headerScheduler] table
						// key : " + tableKey);
						System.err.println("[SalesHeaderDao][headerScheduler] table key : " + tableKey);

						StringBuffer sb = new StringBuffer("update SALES_HEADER set ");
						sb.append("CLIENT_SPECIFIC = " + salesHeaderDto.getClientSpecific() + ", ");
						sb.append("HEADER_ID = '" + salesHeaderDto.getSalesHeaderId() + "', ");
						sb.append("S4_DOCUMENT_ID = '" + salesHeaderDto.getS4DocumentId() + "', ");
						sb.append("DOCUMENT_TYPE = '" + salesHeaderDto.getDocumentType() + "', ");
						sb.append("DOCUMENT_CATEGORY = '" + salesHeaderDto.getDocumentCategory() + "', ");
						// sb.append("SALES_ORG = '" +
						// salesHeaderDto.getSalesOrganization() + "', ");
						sb.append("DISTRIBUTION_CHANNEL = '" + salesHeaderDto.getDistributionChannel() + "', ");
						sb.append("DIVSION = '" + salesHeaderDto.getDivision() + "', ");
						sb.append("SALES_OFFICE = '" + salesHeaderDto.getSalesOffice() + "', ");
						sb.append("SOLD_TO_PARTY = '" + salesHeaderDto.getSoldToParty() + "', ");
						sb.append("SHIP_TO_PARTY = '" + salesHeaderDto.getShipToParty() + "', ");
						sb.append("CUSTOMER_PO_NUM = '" + salesHeaderDto.getCustomerPoNum().replace("'", "''") + "', ");
						if (!ServicesUtil.isEmpty(salesHeaderDto.getCustomerPODate()))
							sb.append("CUSTOMER_PO_DATE = '" + salesHeaderDto.getCustomerPODate() + "', ");
						else
							sb.append("CUSTOMER_PO_DATE = null, ");
						sb.append("REQUEST_DELIVERY_DATE = '" + salesHeaderDto.getRequestDeliveryDate() + "', ");
						sb.append("SHIPPING_TYPE = '" + salesHeaderDto.getShippingType() + "', ");
						sb.append("TOTAL_SO_QUANTITY = " + salesHeaderDto.getTotalSalesOrderQuantity() + ", ");
						sb.append("NET_VALUE = " + salesHeaderDto.getNetValue() + ", ");
						sb.append("TOTAL_SO_QUANTITY_SA = " + salesHeaderDto.getTotalSalesOrderQuantitySA() + ", ");
						sb.append("NET_VALUE_SA = " + salesHeaderDto.getNetValueSA() + ", ");
						sb.append("PLANT = '" + salesHeaderDto.getPlant() + "', ");
						sb.append("DOCUMENT_CURRENCY = '" + salesHeaderDto.getDocumentCurrency() + "', ");
						sb.append("INCO_TERMS1 = '" + salesHeaderDto.getIncoTerms1() + "', ");
						sb.append("INCO_TERMS2 = '" + salesHeaderDto.getIncoTerms2() + "', ");
						sb.append("DELIVERED_QUANTITY = " + salesHeaderDto.getDeliveredQuantity() + ", ");
						sb.append("OUTSTANDING_QUANTITY = " + salesHeaderDto.getOutstandingQuantity() + ", ");
						sb.append("CREDIT_BLOCK_QUANTITY = " + salesHeaderDto.getCreditBlockQuantity() + ", ");
						sb.append("ON_TIME_DELIVERED_QUANTITY = " + salesHeaderDto.getOnTimeDeliveredQuantity() + ", ");
						sb.append("DELIVERY_LEADING_DAYS = " + salesHeaderDto.getDeliveryLeadingDays() + ", ");
						sb.append("PAYMENT_LEADING_DAYS = " + salesHeaderDto.getPaymentLeadingDays() + ", ");
						sb.append("CREATED_DATE = '" + salesHeaderDto.getCreatedDate() + "', ");
						sb.append("CREATED_BY = '" + salesHeaderDto.getCreatedBy() + "', ");
						if (!ServicesUtil.isEmpty(salesHeaderDto.getPaymentChqDetail()))
							sb.append("PAYMENT_CHQ_DETAIL = " + salesHeaderDto
									.getPaymentChqDetail()/* .ordinal() */ + ", ");
						if (!ServicesUtil.isEmpty(salesHeaderDto.getOverallDocumentStatus1()))
							sb.append("OVERALL_DOCUMENT_STATUS = "
									+ salesHeaderDto.getOverallDocumentStatus1().ordinal() + ", ");
						sb.append("DELIVERY_STATUS = " + salesHeaderDto.getDeliveryStatus() + ", ");
						sb.append("DELIVERY_TOLERANCE = '" + salesHeaderDto.getDeliveryTolerance() + "', ");
						// sb.append("OVER_DELIVERY_TOLERANCE = '" +
						// salesHeaderDto.getOverDeliveryTolerance() + "', ");
						// sb.append("UNDER_DELIVERY_TOLERANCE = '" +
						// salesHeaderDto.getUnderDeliveryTolerance() + "', ");
						sb.append("COLOR_CODING_DETAILS = '" + salesHeaderDto.getColorCodingDetails() + "', ");
						sb.append("COMMENTS = '" + salesHeaderDto.getComments().replace("'", "''") + "', ");
						sb.append("BANK_NAME = '" + salesHeaderDto.getBankName().replace("'", "''") + "', ");
						sb.append("PROJECT_NAME = '" + salesHeaderDto.getProjectName().replace("'", "''") + "', ");
						sb.append("PO_TYPE_FIELD = '" + salesHeaderDto.getPoTypeField() + "', ");
						sb.append("PIECE_GUARANTEE = " + salesHeaderDto.getPieceGuarantee() + ", ");
						sb.append("IS_CUSTOMER_ACK = " + salesHeaderDto.getAcknowledgementStatus() + ", ");
						sb.append("REFERENCE_DOCUMENT = '" + salesHeaderDto.getReferenceDocument() + "', ");
						if (!ServicesUtil.isEmpty(salesHeaderDto.getUpdateIndicator1()))
							sb.append("UPDATE_INDICATOR = " + salesHeaderDto.getUpdateIndicator1().ordinal() + ", ");
						// sb.append("LAST_UPDATED_ON = '" +
						// salesHeaderDto.getLastUpdatedOn() + "', ");
						sb.append("SYNC_STATUS = " + salesHeaderDto.getSyncStatus() + ", ");
						if (!ServicesUtil.isEmpty(salesHeaderDto.getOverallDocumentStatus1()) && salesHeaderDto
								.getOverallDocumentStatus1().equals(EnOverallDocumentStatus.COMPLETELY_PROCESSED))
							sb.append("IS_OPEN = false, ");
						else
							sb.append("IS_OPEN = true, ");
						if (salesHeaderDto.getDocumentType().equals("IN")
								|| salesHeaderDto.getDocumentType().equals("QT")) {
							sb.append("PAYMENT_STATUS = '0', ");
						} else {
							if (salesHeaderDto.getPlant().equals("4321")) {
								BigDecimal status = new BigDecimal(0);
								if (salesHeaderDto.getTotalSalesOrderQuantity().equals(new BigDecimal(0.000))) {
									status = new BigDecimal(0.000);
								} else {
									// status =
									// salesHeaderDto.getTotalSalesOrderQuantity()
									// .subtract(salesHeaderDto.getCreditBlockQuantity());
									// status =
									// status.divide(salesHeaderDto.getTotalSalesOrderQuantity(),
									// 3,
									// RoundingMode.HALF_UP);
									// status = status.multiply(new
									// BigDecimal(100.000));
								}
								Integer value = status.intValue();
								sb.append("PAYMENT_STATUS = '" + value.toString() + "', ");
							} else if (salesHeaderDto.getPlant().equals("CODD")) {
								BigDecimal status = new BigDecimal(0);
								if (salesHeaderDto.getTotalSalesOrderQuantitySA().equals(new BigDecimal(0.000))) {
									status = new BigDecimal(0.000);
								} else {
									// status =
									// salesHeaderDto.getTotalSalesOrderQuantitySA()
									// .subtract(salesHeaderDto.getCreditBlockQuantity());
									// status =
									// status.divide(salesHeaderDto.getTotalSalesOrderQuantitySA(),
									// 3,
									// RoundingMode.HALF_UP);
									// status = status.multiply(new
									// BigDecimal(100.000));
								}
								Integer value = status.intValue();
								sb.append("PAYMENT_STATUS = '" + value.toString() + "', ");
							}
						}

						sb.append("DOCUMENT_PROCESS_STATUS = 2 ");
						sb.append("where TABLE_KEY = '" + tableKey + "'");
						Query query1 = entityManager.createNativeQuery(sb.toString());
						query1.executeUpdate();
						// session.flush();
						// session.clear();
						// tx.commit();
					} else {
						// session = sessionFactory.openSession();
						// tx = session.beginTransaction();
						salesHeaderDto.setTableKey(UUID.randomUUID().toString().replaceAll("-", ""));
						salesHeaderDto.setDocumentProcessStatus(EnOrderActionStatus.CREATED);
						if (!ServicesUtil.isEmpty(salesHeaderDto.getOverallDocumentStatus()) && salesHeaderDto
								.getOverallDocumentStatus().equals(EnOverallDocumentStatus.COMPLETELY_PROCESSED))
							salesHeaderDto.setIsOpen(false);
						else
							salesHeaderDto.setIsOpen(true);
						if (salesHeaderDto.getDocumentType().equals("IN")
								|| salesHeaderDto.getDocumentType().equals("QT")) {
							salesHeaderDto.setPaymentStatus("0");
						} else {
							if (salesHeaderDto.getPlant().equals("4321")) {
								BigDecimal status = new BigDecimal(0);
								if (salesHeaderDto.getTotalSalesOrderQuantity().equals(new BigDecimal(0.000))) {
									status = new BigDecimal(0.000);
								} else {
									status = salesHeaderDto.getTotalSalesOrderQuantity()
											.subtract(salesHeaderDto.getCreditBlockQuantity());
									status = status.divide(salesHeaderDto.getTotalSalesOrderQuantity(), 3,
											RoundingMode.HALF_UP);
									status = status.multiply(new BigDecimal(100.000));
								}
								Integer value = status.intValue();
								salesHeaderDto.setPaymentStatus(value.toString());
							} else if (salesHeaderDto.getPlant().equals("CODD")) {
								BigDecimal status = new BigDecimal(0);
								if (salesHeaderDto.getTotalSalesOrderQuantitySA().equals(new BigDecimal(0.000))) {
									status = new BigDecimal(0.000);
								} else {
									status = salesHeaderDto.getTotalSalesOrderQuantitySA()
											.subtract(salesHeaderDto.getCreditBlockQuantity());
									status = status.divide(salesHeaderDto.getTotalSalesOrderQuantitySA(), 3,
											RoundingMode.HALF_UP);
									status = status.multiply(new BigDecimal(100.000));
								}
								Integer value = status.intValue();
								salesHeaderDto.setPaymentStatus(value.toString());
							}
						}
						list.add("I-" + salesHeaderDto.getTableKey());
						salesOrderHeaderRepository.save(ObjectMapperUtils.map(salesHeaderDto, SalesOrderHeader.class));
						// session.flush();
						// session.clear();
						// tx.commit();
					}
				}
			}

			String ackResponse = odataServices.headerAckScheduler();
			// logger.debug("[SalesHeaderDao][headerScheduler] ackResponse : " +
			// ackResponse);
			System.err.println("[SalesHeaderDao][headerScheduler] ackResponse : " + ackResponse);
		} catch (Exception e) {
			// logger.error("[SalesHeaderDao][headerScheduler] Exception : " +
			// e.getMessage());
			System.err.println("[SalesHeaderDao][headerScheduler] Exception : " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", "Error Updating")
					.body(null);
		}
		// logger.debug("[SalesHeaderDao][headerScheduler] Ended : " + new
		// Date());
		System.err.println("[SalesHeaderDao][headerScheduler] Ended : " + new Date());
		return ResponseEntity.status(HttpStatus.OK).header("message", "Successfully Updated").body(list);
	}

//	@Scheduled(cron = "0 12 * * * ?")
	@SuppressWarnings({ "unchecked", "resource" })
	public ResponseEntity<Object> itemScheduler() {
		// logger.debug("[SalesItemDetailsDao][itemScheduler] Start : " + new
		// Date());
		System.err.println("[SalesItemDetailsDao][itemScheduler] Start : " + new Date());
		// Response response = new Response();
		ArrayList<String> list = new ArrayList<>();
		// Session session = null;
		// Transaction tx = null;
		try {
			OdataSchItemStartDto odataSchItemStartDto = odataServices.itemScheduler();
			System.err.println("odataSchItemStartDto" + odataSchItemStartDto);
			List<SalesOrderItemDto> listSalesItemDetailsDto = salesOrderItemRepository
					.convertData(odataSchItemStartDto);
			System.err.println("listSalesItemDetailsDto" + listSalesItemDetailsDto);
			for (SalesOrderItemDto salesItemDetailsDto : listSalesItemDetailsDto) {
				if (salesItemDetailsDto.getUpdateIndicator().equals(EnUpdateIndicator.DELETE)) {
					// session = sessionFactory.openSession();
					// tx = session.beginTransaction();
					StringBuffer sb1 = new StringBuffer(
							"delete from SalesOrderItem s where s.s4DocumentId=:s4DocumentId and s.material=:material and s.plant=:plant");
					if (!ServicesUtil.isEmpty(salesItemDetailsDto.getMaterialDescription()))
						sb1.append(" and s.materialDescription=:materialDescription");
					if (!ServicesUtil.isEmpty(salesItemDetailsDto.getLineItemNumber()))
						sb1.append(" and s.lineItemNumber=:lineItemNumber");
					Query query = entityManager.createQuery(sb1.toString());
					query.setParameter("s4DocumentId", salesItemDetailsDto.getS4DocumentId());
					query.setParameter("material", salesItemDetailsDto.getMaterial());
					query.setParameter("plant", salesItemDetailsDto.getPlant());

					if (!ServicesUtil.isEmpty(salesItemDetailsDto.getMaterialDescription()))
						query.setParameter("materialDescription", salesItemDetailsDto.getMaterialDescription());
					if (!ServicesUtil.isEmpty(salesItemDetailsDto.getLineItemNumber()))
						query.setParameter("lineItemNumber", salesItemDetailsDto.getLineItemNumber());
					query.executeUpdate();
					list.add("D-" + salesItemDetailsDto.getSalesItemId());
					// session.flush();
					// session.clear();
					// tx.commit();
				} else {
					// session = sessionFactory.openSession();
					// tx = session.beginTransaction();
					StringBuffer sb1 = new StringBuffer(
							"select s.salesItemId from SalesOrderItem s where s.s4DocumentId=:s4DocumentId ");
					if (!ServicesUtil.isEmpty(salesItemDetailsDto.getLineItemNumber()))
						sb1.append(" and s.lineItemNumber=:lineItemNumber");
					Query query = entityManager.createQuery(sb1.toString());
					query.setParameter("s4DocumentId", salesItemDetailsDto.getS4DocumentId());
					if (!ServicesUtil.isEmpty(salesItemDetailsDto.getLineItemNumber()))
						query.setParameter("lineItemNumber", salesItemDetailsDto.getLineItemNumber());
					List<String> objList = query.getResultList();
					// session.flush();
					// session.clear();
					// tx.commit();

					if (objList != null && objList.size() > 0) {
						// session = sessionFactory.openSession();
						// tx = session.beginTransaction();
						String salesItemId = objList.get(0).toString();
						list.add("U-" + salesItemId);
						// logger.debug("[SalesItemDetailsDao][itemScheduler]
						// matId : " + salesItemId);
						System.err.println("[SalesItemDetailsDao][itemScheduler] matId : " + salesItemId);
						salesItemDetailsDto.setSalesItemId(salesItemId);

						StringBuffer sb = new StringBuffer("update SALES_ITEM set ");
						sb.append("CLIENT_SPECIFIC = " + salesItemDetailsDto.getClientSpecific() + ", ");
						sb.append("LINE_ITEM_NUMBER = " + salesItemDetailsDto.getLineItemNumber() + ", ");
						sb.append("S4_DOCUMENT_ID = '" + salesItemDetailsDto.getS4DocumentId() + "', ");
						sb.append("MATERIAL = '" + salesItemDetailsDto.getMaterial() + "', ");
						sb.append("MATERIAL_DESC = '" + salesItemDetailsDto.getMaterialDescription() + "', ");
						sb.append("ORD_QUANTITY = " + salesItemDetailsDto.getOrderQuantity() + ", ");
						sb.append("ITEM_CATEGORY = '" + salesItemDetailsDto.getItemCategory() + "', ");
						sb.append("PLANT = '" + salesItemDetailsDto.getPlant() + "', ");
						sb.append("NET_VALUE = '" + salesItemDetailsDto.getNetValue() + "', ");
						sb.append("AMOUNT_B4_VAT = " + salesItemDetailsDto.getAmountBeforeVAT() + ", ");
						sb.append("DOCUMENT_CURRENCY = '" + salesItemDetailsDto.getDocumentCurrency() + "', ");
						sb.append("VAT_AMOUNT = " + salesItemDetailsDto.getVATAmount() + ", ");
						sb.append("VAT_PERCENT = " + salesItemDetailsDto.getVATPercent() + ", ");
						sb.append("TOTAL_AMOUNT_INCL_VAT = " + salesItemDetailsDto.getTotalAmountIncludingVAT() + ", ");
						sb.append("DELIVERED_QUANTITY = " + salesItemDetailsDto.getDeliveredQuantity() + ", ");
						sb.append("DELIVERED_PIECES = " + salesItemDetailsDto.getDeliveredPieces() + ", ");
						sb.append("OUTSTANDING_QUANTITY = " + salesItemDetailsDto.getOutstandingQuantity() + ", ");
						sb.append("OUTSTANDING_PIECES = " + salesItemDetailsDto.getOutstandingPieces() + ", ");
						sb.append("AVAILABILITY_STATUS = '" + salesItemDetailsDto.getAvailabilityStatus() + "', ");
						if (!ServicesUtil.isEmpty(salesItemDetailsDto.getPaymentChequeDetail()))
							sb.append("PAYMENT_CHQ_DETAIL = " + salesItemDetailsDto.getPaymentChequeDetail().ordinal()
									+ ", ");
						sb.append("DELIVERY_STATUS = " + salesItemDetailsDto.getDeliveryStatus() + ", ");
						sb.append("CREDIT_BLOCK_QUANTITY = " + salesItemDetailsDto.getCreditBlockQuantity() + ", ");
						sb.append("ON_TIME_DELIVERED_QUANTITY = " + salesItemDetailsDto.getOnTimeDeliveredQuantity()
								+ ", ");
						if (!ServicesUtil.isEmpty(salesItemDetailsDto.getOverallProcessingStatus()))
							sb.append("OVERALL_PROCESSING_STATUS = "
									+ salesItemDetailsDto.getOverallProcessingStatus().ordinal() + ", ");
						sb.append("NO_OF_PIECES = '" + salesItemDetailsDto.getNumberOfPieces() + "', ");
						sb.append("NO_OF_BUNDLES = " + salesItemDetailsDto.getNumberOfBundles() + ", ");
						sb.append("BASE_PRICE = '" + salesItemDetailsDto.getBasePrice() + "', ");
						sb.append("EXTRAS = " + salesItemDetailsDto.getExtras() + ", ");
						sb.append("QUALITY_TEST_EXTRAS = " + salesItemDetailsDto.getQualityTestExtras() + ", ");
						sb.append("DISCOUNT1 = " + salesItemDetailsDto.getDiscount1() + ", ");
						sb.append("ENTERED_ORD_QUANTITY = " + salesItemDetailsDto.getEnteredOrdQuantity() + ", ");
						sb.append("STANDARD = '" + salesItemDetailsDto.getStandard() + "', ");
						sb.append("STANDARD_DESC = '" + salesItemDetailsDto.getStandardDescription() + "', ");
						sb.append("SECTION_GRADE = '" + salesItemDetailsDto.getSectionGrade() + "', ");
						sb.append("SECTION_GRADE_DESC = '" + salesItemDetailsDto.getSectionGradeDescription() + "', ");
						sb.append("SIZE = '" + salesItemDetailsDto.getSize() + "', ");
						sb.append("KG_PER_METER = " + salesItemDetailsDto.getKgPerMeter() + ", ");
						sb.append("LENGTH = " + salesItemDetailsDto.getLength() + ", ");
						sb.append("BARS_PER_BUNDLE = " + salesItemDetailsDto.getBarsPerBundle() + ", ");
						sb.append("SECTION_GROUP = '" + salesItemDetailsDto.getSectionGroup() + "', ");
						sb.append("LEVEL2_ID = '" + salesItemDetailsDto.getLevel2Id() + "', ");
						sb.append("CE_LOGO = '" + salesItemDetailsDto.getCeLogo() + "', ");
						sb.append("SECTION = '" + salesItemDetailsDto.getSection() + "', ");
						sb.append("SIZE_GROUP = " + salesItemDetailsDto.getSizeGroup() + ", ");
						sb.append("ISI_LOGO = '" + salesItemDetailsDto.getIsiLogo() + "', ");
						sb.append("IMPACT_TEST = " + salesItemDetailsDto.getImpactTest() + ", ");
						sb.append("BEND_TEST = " + salesItemDetailsDto.getBendTest() + ", ");
						sb.append("INSPECTION_TEST = " + salesItemDetailsDto.getInspection() + ", ");
						sb.append("IS_ELEMENT_BORON_REQ = " + salesItemDetailsDto.getIsElementBoronRequired() + ", ");
						sb.append("ULTRASONOIC_TEST = " + salesItemDetailsDto.getUltraSonicTest() + ", ");
						sb.append("GRADE_PRICING_GROUP = '" + salesItemDetailsDto.getGradePricingGroup() + "', ");
						sb.append("TOTAL_NO_PIECES = " + salesItemDetailsDto.getTotalNumberOfPieces() + ", ");
						sb.append("BUNDLE_WT = " + salesItemDetailsDto.getBundleWeight() + ", ");
						if (!ServicesUtil.isEmpty(salesItemDetailsDto.getUpdateIndicator1()))
							sb.append(
									"UPDATE_INDCATOR = " + salesItemDetailsDto.getUpdateIndicator1().ordinal() + ", ");
						sb.append("CHANGED_ON = '" + salesItemDetailsDto.getChangedOn() + "', ");
						sb.append("SYNC_STATUS = " + salesItemDetailsDto.getSyncStatus() + " ");
						sb.append("where SALES_ITEM_ID = '" + salesItemId + "'");

						Query query1 = entityManager.createNativeQuery(sb.toString());
						query1.executeUpdate();
						// session.flush();
						// session.clear();
						// tx.commit();
					} else {
						Session session = entityManager.unwrap(Session.class);
						// tx = session.beginTransaction();
						sequenceNumberGen = SequenceNumberGen.getInstance();

						salesItemDetailsDto
								.setSalesItemId(sequenceNumberGen.getNextSeqNumber("IT", new Integer(8), session));
						list.add("I-" + salesItemDetailsDto.getSalesItemId());
						salesOrderItemRepository.save(ObjectMapperUtils.map(salesItemDetailsDto, SalesOrderItem.class));
						// session.flush();
						// session.clear();
						// tx.commit();
					}
				}
			}
			// response.setData(list);
			// response.setMessage("Successfully Updated");
			// response.setStatus(HttpStatus.OK.getReasonPhrase());
			// response.setStatusCode(HttpStatus.OK.value());
			String ackResponse = odataServices.itemAckScheduler();
			// logger.debug("[SalesItemDetailsDao][itemScheduler] ackResponse :
			// " + ackResponse);
			System.err.println("[SalesItemDetailsDao][itemScheduler] ackResponse : " + ackResponse);
		} catch (Exception e) {
			// logger.error("[SalesItemDetailsDao][itemScheduler] Exception : "
			// + e.getMessage());
			System.err.println("[SalesItemDetailsDao][itemScheduler] Exception : " + e.getMessage());
			e.printStackTrace();
			// response.setMessage("Error Updating");
			// response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
			// response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", "Error Updating")
					.body(null);

		}
		// logger.debug("[SalesItemDetailsDao][itemScheduler] Ended : " + new
		// Date());
		System.err.println("[SalesItemDetailsDao][itemScheduler] Ended : " + new Date());
		return ResponseEntity.status(HttpStatus.OK).header("message", "Successfully Updated").body(list);
	}

}
