package com.incture.cherrywork.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.FilterDto;
import com.incture.cherrywork.dtos.SalesDocHeaderDto;
import com.incture.cherrywork.dtos.SalesDocItemDto;
import com.incture.cherrywork.dtos.ScheduleLineDto;
import com.incture.cherrywork.entities.SalesDocHeaderDo;
import com.incture.cherrywork.entities.SalesDocItemDo;
import com.incture.cherrywork.entities.SalesDocItemPrimaryKeyDo;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.ScheduleLineDo;
import com.incture.cherrywork.entities.ScheduleLinePrimaryKeyDo;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.repositories.ISalesDocHeaderRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.util.HelperClass;
import com.incture.cherrywork.util.ServicesUtil;
import com.incture.cherrywork.workflow.repositories.ISalesDocItemRepository;
import com.incture.cherrywork.workflow.repositories.IScheduleLineRepository;

@SuppressWarnings("deprecation")
@Service
@Transactional
public class SalesDocHeaderDaoImpl extends BaseDao<SalesDocHeaderDo, SalesDocHeaderDto> implements SalesDocHeaderDao {

	// Control the list of Items here by flag
	private Boolean listofItemTrigger = Boolean.FALSE;
	@Lazy
	@Autowired
	private SalesDocItemDaoImpl salesDocItemDao;

	@PersistenceContext
	private EntityManager entityManager;

	public Session getSession() {
		Session session = entityManager.unwrap(Session.class);
		return session;
	}

	@Autowired
	private SessionFactory sessionfactory;

	@Autowired
	private ISalesDocHeaderRepository salesDocHeaderRepository;

	@Autowired
	private IScheduleLineRepository scheduleLineRepository;

	@Autowired
	private ISalesDocItemRepository salesDocItemRepository;

	@Override
	public String saveSalesDocHeader(SalesDocHeaderDto salesDocHeaderDto) throws ExecutionFault {
		System.err.println("Final save start..");
		try {
			// Enabling the list of Items here by flag
			listofItemTrigger = Boolean.TRUE;
			List<SalesDocItemDto> itemListDto = salesDocHeaderDto.getSalesDocItemList();
			if (itemListDto != null && !itemListDto.isEmpty()) {
				for (SalesDocItemDto salesDocItemDto : itemListDto) {

					// Setting Foreign key in Item level from Header level
					salesDocItemDto.setSalesHeaderNo(salesDocHeaderDto.getSalesOrderNum());

					// Setting Schedule line level
					List<ScheduleLineDto> scheduleLineDtoList = salesDocItemDto.getScheduleLineList();

					if (scheduleLineDtoList != null && !scheduleLineDtoList.isEmpty()) {
						scheduleLineDtoList.forEach(schLine -> {

							// Setting Foreign Key for Schedule Line Level
							schLine.setSalesHeaderNo(salesDocItemDto.getSalesHeaderNo());
							schLine.setSalesItemOrderNo(salesDocItemDto.getSalesItemOrderNo());
							ScheduleLineDo scheduleLineDo = ObjectMapperUtils.map(schLine, ScheduleLineDo.class);
							// System.err.println("ScheduleLineKey:
							// "+scheduleLineDo.getScheduleLineKey());
							// if(scheduleLineDo.getScheduleLineKey() == null){
							// scheduleLineDo.setScheduleLineKey(new
							// ScheduleLinePrimaryKeyDo(ServicesUtil.randomId(),ObjectMapperUtils.map(salesDocItemDto,
							// SalesDocItemDo.class)));
							// }
							// System.err.println("ScheduleLineKey:
							// "+scheduleLineDo.getScheduleLineKey());
							// ScheduleLineDo savedScheduleLineDo =
							// scheduleLineRepository.save(scheduleLineDo);
							// System.err.println("[salesDocHeaderDaoImpl][saveSalesDocHeader]
							// savedScheduleLineDo
							// "+savedScheduleLineDo.toString());
						});
					}
					// SalesDocItemDo salesDocItemDo =
					// ObjectMapperUtils.map(salesDocItemDto,
					// SalesDocItemDo.class);
					// System.err.println("salesDocItemKey:
					// "+salesDocItemDo.getSalesDocItemKey());
					// if(salesDocItemDo.getSalesDocItemKey() == null){
					// SalesDocHeaderDo salesDocHeader =
					// ObjectMapperUtils.map(salesDocHeaderDto,
					// SalesDocHeaderDo.class);
					// salesDocItemDo.setSalesDocItemKey(new
					// SalesDocItemPrimaryKeyDo(ServicesUtil.randomId(),
					// salesDocHeader));
					// }
					// System.err.println("salesDocItemKey:
					// "+salesDocItemDo.getSalesDocItemKey());
					// SalesDocItemDo savedSalesDocItemDo =
					// salesDocItemRepository.save(salesDocItemDo);
					// System.err.println("[salesDocHeaderDaoImpl][saveSalesDocHeader]
					// savedSalesDocItemDo "+savedSalesDocItemDo.toString());
				}
			}

			SalesDocHeaderDo salesDocHeaderDo = importDto(salesDocHeaderDto);
			// salesDocHeaderDo.setRequestId(ServicesUtil.randomId());
			SalesDocHeaderDo savedSalesDocHeaderDo = salesDocHeaderRepository.save(salesDocHeaderDo);
			System.err.println("[salesDocHeaderDaoImpl][saveSalesDocHeader] savedSalesDocHeaderDo "
					+ savedSalesDocHeaderDo.toString());
			System.err.println("Saved Sales Doc Header");

			// save was throwing constraint violation exception
			// getSession().save(salesDocHeaderDo);

			return "Sales Document Header is created with " + salesDocHeaderDo.getSalesOrderNum();
		} catch (NoResultException | NullPointerException e) {
			System.err.println("[saveSalesDocHeader] Exception " + e.getMessage());
			e.printStackTrace();
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			System.err.println("[saveSalesDocHeader] Exception " + e.getMessage());
			e.printStackTrace();
			throw e;
		}

	}

	public String updateSalesDocHeader(SalesDocHeaderDto salesDocHeaderDto) throws ExecutionFault {
		try (Session session = sessionfactory.openSession()) {

			// Enabling the list of Items here by flag
			listofItemTrigger = Boolean.TRUE;
			List<SalesDocItemDto> itemListDto = salesDocHeaderDto.getSalesDocItemList();
			if (itemListDto != null && !itemListDto.isEmpty()) {
				for (SalesDocItemDto salesDocItemDto : itemListDto) {

					// Setting Foreign key in Item level from Header level
					salesDocItemDto.setSalesHeaderNo(salesDocHeaderDto.getSalesOrderNum());

					// Setting Schedule line level
					List<ScheduleLineDto> scheduleLineDtoList = salesDocItemDto.getScheduleLineList();

					if (scheduleLineDtoList != null && !scheduleLineDtoList.isEmpty()) {
						scheduleLineDtoList.forEach(schLine -> {

							// Setting Foreign Key for Schedule Line Level
							schLine.setSalesHeaderNo(salesDocItemDto.getSalesHeaderNo());
							schLine.setSalesItemOrderNo(salesDocItemDto.getSalesItemOrderNo());
						});
					}
				}
			}

			SalesDocHeaderDo salesDocHeaderDo = importDto(salesDocHeaderDto);
			/*
			 * //getSession().merge(salesDocHeaderDo); since it was not updating
			 * properly getSession().saveOrUpdate(salesDocHeaderDo);
			 * getSession().flush(); getSession().clear();
			 */

			// Session session = sessionfactory.openSession();
			Transaction tx = session.beginTransaction();

			session.saveOrUpdate(salesDocHeaderDo);

			tx.commit();
			session.close();

			return "Sales Document Header is updated with " + salesDocHeaderDo.getSalesOrderNum();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public SalesDocHeaderDo importDto(SalesDocHeaderDto fromDto) {
		SalesDocHeaderDo salesDocHeaderDo = null;
		if (fromDto != null) {
			salesDocHeaderDo = new SalesDocHeaderDo();

			salesDocHeaderDo.setCreatedBy(fromDto.getCreatedBy());
			salesDocHeaderDo.setCreditStatus(fromDto.getCreditStatus());
			salesDocHeaderDo.setCustomerPo(fromDto.getCustomerPo());
			salesDocHeaderDo.setCustomerPoType(fromDto.getCustomerPoType());
			salesDocHeaderDo.setDeliveryStatus(fromDto.getDeliveryStatus());
			salesDocHeaderDo.setDistributionChannel(fromDto.getDistributionChannel());
			salesDocHeaderDo.setDivision(fromDto.getDivision());
			salesDocHeaderDo.setDocCurrency(fromDto.getDocCurrency());
			salesDocHeaderDo.setOrderCategory(fromDto.getOrderCategory());
			salesDocHeaderDo.setOrderReason(fromDto.getOrderReason());
			salesDocHeaderDo.setOrderType(fromDto.getOrderType());
			salesDocHeaderDo.setSalesman(fromDto.getSalesman());
			salesDocHeaderDo.setBillToParty(fromDto.getBillToParty());
			salesDocHeaderDo.setBillToPartyText(fromDto.getBillToPartyText());
			salesDocHeaderDo.setPayer(fromDto.getPayer());
			salesDocHeaderDo.setPayerText(fromDto.getPayerText());
			salesDocHeaderDo.setDivisionText(fromDto.getDivisionText());
			salesDocHeaderDo.setDistributionChannelText(fromDto.getDistrChanText());
			salesDocHeaderDo.setSalesOrgText(fromDto.getSalesOrgText());
			salesDocHeaderDo.setOverallStatus(fromDto.getOverallStatus());
			salesDocHeaderDo.setRejectionStatus(fromDto.getRejectionStatus());
			salesDocHeaderDo.setCreditBlock(fromDto.getCreditBlock());
			salesDocHeaderDo.setHeaderBillBlockCode(fromDto.getHeaderBillBlockCode());
			salesDocHeaderDo.setDeliveryBlockCode(fromDto.getDeliveryBlockCode());
			salesDocHeaderDo.setSalesOrg(fromDto.getSalesOrg());
			salesDocHeaderDo.setOrderReasonText(fromDto.getOrderReasonText());
			salesDocHeaderDo.setOrderRemark(fromDto.getOrderRemark());
			salesDocHeaderDo.setSoldToParty(fromDto.getSoldToParty());
			salesDocHeaderDo.setShipToParty(fromDto.getShipToParty());
			salesDocHeaderDo.setDeliveryBlockText(fromDto.getDeliveryBlockCodeText());
			salesDocHeaderDo.setDocTypeText(fromDto.getDocTypeText());
			salesDocHeaderDo.setShipToPartyText(fromDto.getShipToPartyText());
			salesDocHeaderDo.setSoldToPartyText(fromDto.getSoldToPartyText());
			salesDocHeaderDo.setCondGroup5(fromDto.getCondGroup5());
			salesDocHeaderDo.setApprovalStatus(fromDto.getApprovalStatus());
			salesDocHeaderDo.setSdProcessStatus(fromDto.getSdProcessStatus());
			salesDocHeaderDo.setOrderApprovalReason(fromDto.getOrderApprovalReason());
			salesDocHeaderDo.setTotalNetAmount(fromDto.getTotalNetAmount());
			salesDocHeaderDo.setOrdererNA(fromDto.getOrdererNA());
			salesDocHeaderDo.setCondGroup5Text(fromDto.getCondGroup5Text());
			salesDocHeaderDo.setAttachmentUrl(fromDto.getAttachmentUrl());
			salesDocHeaderDo.setRequestedBy(fromDto.getRequestedBy());

			// Setting Primary Key
			if (fromDto.getSalesOrderNum() != null) {
				salesDocHeaderDo.setSalesOrderNum(fromDto.getSalesOrderNum());
			}

			// Converting Date from String
			// salesDocHeaderDo.setSalesOrderDate(ConvertStringToDate(fromDto.getSalesOrderDate()));

			salesDocHeaderDo.setSalesOrderDate(fromDto.getSalesOrderDate());

			// Field is mandatory as in foreign key here
			// RequestMasterDo reqMaster = new RequestMasterDo();
			// reqMaster.setRequestId(fromDto.getReqMasterId());
			//
			// salesDocHeaderDo.setReqMaster(reqMaster);
			salesDocHeaderDo.setRequestId(fromDto.getReqMasterId());

			// Converting list level to entity using import List method and
			// checking the content of it
			List<SalesDocItemDo> salesDocItemDoList = salesDocItemDao.importList(fromDto.getSalesDocItemList());
			if (salesDocItemDoList != null && !salesDocItemDoList.isEmpty()) {
				salesDocHeaderDo.setSalesDocItemList(salesDocItemDoList);
			}
		}
		return salesDocHeaderDo;
	}

	@Override
	public SalesDocHeaderDto exportDto(SalesDocHeaderDo entity) {
		SalesDocHeaderDto salesDocHeaderDto = null;
		if (entity != null) {
			salesDocHeaderDto = new SalesDocHeaderDto();

			System.err.println("Header Entity data at DAO came for sales order num " + entity.getSalesOrderNum());

			salesDocHeaderDto.setCreatedBy(entity.getCreatedBy());
			salesDocHeaderDto.setCreditStatus(entity.getCreditStatus());
			salesDocHeaderDto.setCustomerPo(entity.getCustomerPo());
			salesDocHeaderDto.setCustomerPoType(entity.getCustomerPoType());
			salesDocHeaderDto.setDeliveryStatus(entity.getDeliveryStatus());
			salesDocHeaderDto.setDistributionChannel(entity.getDistributionChannel());
			salesDocHeaderDto.setDivision(entity.getDivision());
			salesDocHeaderDto.setDocCurrency(entity.getDocCurrency());
			salesDocHeaderDto.setOrderCategory(entity.getOrderCategory());
			salesDocHeaderDto.setOrderReason(entity.getOrderReason());
			salesDocHeaderDto.setOrderType(entity.getOrderType());
			salesDocHeaderDto.setOverallStatus(entity.getOverallStatus());
			salesDocHeaderDto.setRejectionStatus(entity.getRejectionStatus());
			salesDocHeaderDto.setCreditBlock(entity.getCreditBlock());
			salesDocHeaderDto.setHeaderBillBlockCode(entity.getHeaderBillBlockCode());
			salesDocHeaderDto.setDeliveryBlockCode(entity.getDeliveryBlockCode());
			salesDocHeaderDto.setSalesOrg(entity.getSalesOrg());
			salesDocHeaderDto.setOrderReasonText(entity.getOrderReasonText());
			salesDocHeaderDto.setOrderRemark(entity.getOrderRemark());
			salesDocHeaderDto.setSoldToParty(entity.getSoldToParty());
			salesDocHeaderDto.setShipToParty(entity.getShipToParty());
			salesDocHeaderDto.setDeliveryBlockCodeText(entity.getDeliveryBlockText());
			salesDocHeaderDto.setDocTypeText(entity.getDocTypeText());
			salesDocHeaderDto.setSoldToPartyText(entity.getSoldToPartyText());
			salesDocHeaderDto.setShipToPartyText(entity.getShipToPartyText());
			salesDocHeaderDto.setSalesman(entity.getSalesman());
			salesDocHeaderDto.setCondGroup5(entity.getCondGroup5());
			salesDocHeaderDto.setBillToParty(entity.getBillToParty());
			salesDocHeaderDto.setBillToPartyText(entity.getBillToPartyText());
			salesDocHeaderDto.setPayer(entity.getPayer());
			salesDocHeaderDto.setPayerText(entity.getPayerText());
			salesDocHeaderDto.setDivisionText(entity.getDivisionText());
			salesDocHeaderDto.setDistrChanText(entity.getDistributionChannelText());
			salesDocHeaderDto.setSalesOrgText(entity.getSalesOrgText());
			salesDocHeaderDto.setApprovalStatus(entity.getApprovalStatus());
			salesDocHeaderDto.setSdProcessStatus(entity.getSdProcessStatus());
			salesDocHeaderDto.setOrderApprovalReason(entity.getOrderApprovalReason());
			salesDocHeaderDto.setTotalNetAmount(entity.getTotalNetAmount());
			salesDocHeaderDto.setOrdererNA(entity.getOrdererNA());
			salesDocHeaderDto.setCondGroup5Text(entity.getCondGroup5Text());
			salesDocHeaderDto.setAttachmentUrl(entity.getAttachmentUrl());
			salesDocHeaderDto.setRequestedBy(entity.getRequestedBy());

			// Setting Primary Key
			if (entity.getSalesOrderNum() != null) {
				salesDocHeaderDto.setSalesOrderNum(entity.getSalesOrderNum());
			}

			// Setting Foreign Key
			// salesDocHeaderDto.setReqMasterId(entity.getReqMaster().getRequestId());
			salesDocHeaderDto.setReqMasterId(entity.getRequestId());

			// Converting String from Date
			// salesDocHeaderDto.setSalesOrderDate(ConvertDateToString(entity.getSalesOrderDate()));

			salesDocHeaderDto.setSalesOrderDate(entity.getSalesOrderDate());

			System.err.println(
					"listofItemTrigger for sales order " + entity.getSalesOrderNum() + " is : " + listofItemTrigger);

			// Converting list level to dto using export List method and
			// checking the content of it
			// Item level list for SO header
			if (listofItemTrigger) {
				System.err.println("inside fetching item list data");
				List<SalesDocItemDto> salesDocItemDtoList = salesDocItemDao.exportList(entity.getSalesDocItemList());
				System.err
						.println("sales order " + entity.getSalesOrderNum() + " item data is : " + salesDocItemDtoList);
				if (salesDocItemDtoList != null && !salesDocItemDtoList.isEmpty()) {
					salesDocHeaderDto.setSalesDocItemList(salesDocItemDtoList);
				}
			}
		}
		return salesDocHeaderDto;

	}

	public SalesDocHeaderDto exportSOHtoSDH(SalesOrderHeader entity) {
		SalesDocHeaderDto salesDocHeaderDto = null;
		// if (entity != null) {
		// salesDocHeaderDto = new SalesDocHeaderDto();
		//
		// System.err.println("Header Entity data at DAO came for sales order
		// num " + entity.getSalesHeaderId());

		// salesDocHeaderDto.setCreatedBy(entity.getCreatedBy());
		// salesDocHeaderDto.setCreditStatus(entity.getCreditStatus());
		// salesDocHeaderDto.setCustomerPo(entity.getCustomerPo());
		// salesDocHeaderDto.setCustomerPoType(entity.getCustomerPoType());
		// salesDocHeaderDto.setDeliveryStatus(entity.getDeliveryStatus());
		// salesDocHeaderDto.setDistributionChannel(entity.getDistributionChannel());
		// salesDocHeaderDto.setDivision(entity.getDivision());
		// salesDocHeaderDto.setDocCurrency(entity.getDocCurrency());
		// salesDocHeaderDto.setOrderCategory(entity.getOrderCategory());
		// salesDocHeaderDto.setOrderReason(entity.getOrderReason());
		// salesDocHeaderDto.setOrderType(entity.getOrderType());
		// salesDocHeaderDto.setOverallStatus(entity.getOverallStatus());
		// salesDocHeaderDto.setRejectionStatus(entity.getRejectionStatus());
		// salesDocHeaderDto.setCreditBlock(entity.getCreditBlock());
		// salesDocHeaderDto.setHeaderBillBlockCode(entity.getHeaderBillBlockCode());
		// salesDocHeaderDto.setDeliveryBlockCode(entity.getDeliveryBlockCode());
		// salesDocHeaderDto.setSalesOrg(entity.getSalesOrg());
		// salesDocHeaderDto.setOrderReasonText(entity.getOrderReasonText());
		// salesDocHeaderDto.setOrderRemark(entity.getOrderRemark());
		// salesDocHeaderDto.setSoldToParty(entity.getSoldToParty());
		// salesDocHeaderDto.setShipToParty(entity.getShipToParty());
		// salesDocHeaderDto.setDeliveryBlockCodeText(entity.getDeliveryBlockText());
		// salesDocHeaderDto.setDocTypeText(entity.getDocTypeText());
		// salesDocHeaderDto.setSoldToPartyText(entity.getSoldToPartyText());
		// salesDocHeaderDto.setShipToPartyText(entity.getShipToPartyText());
		// salesDocHeaderDto.setSalesman(entity.getSalesman());
		// salesDocHeaderDto.setCondGroup5(entity.getCondGroup5());
		// salesDocHeaderDto.setBillToParty(entity.getBillToParty());
		// salesDocHeaderDto.setBillToPartyText(entity.getBillToPartyText());
		// salesDocHeaderDto.setPayer(entity.getPayer());
		// salesDocHeaderDto.setPayerText(entity.getPayerText());
		// salesDocHeaderDto.setDivisionText(entity.getDivisionText());
		// salesDocHeaderDto.setDistrChanText(entity.getDistributionChannelText());
		// salesDocHeaderDto.setSalesOrgText(entity.getSalesOrgText());
		// salesDocHeaderDto.setApprovalStatus(entity.getApprovalStatus());
		// salesDocHeaderDto.setSdProcessStatus(entity.getSdProcessStatus());
		// salesDocHeaderDto.setOrderApprovalReason(entity.getOrderApprovalReason());
		// salesDocHeaderDto.setTotalNetAmount(entity.getTotalNetAmount());
		// salesDocHeaderDto.setOrdererNA(entity.getOrdererNA());
		// salesDocHeaderDto.setCondGroup5Text(entity.getCondGroup5Text());
		// salesDocHeaderDto.setAttachmentUrl(entity.getAttachmentUrl());
		// salesDocHeaderDto.setRequestedBy(entity.getRequestedBy());
		//
		// // Setting Primary Key
		// if (entity.getSalesOrderNum() != null) {
		// salesDocHeaderDto.setSalesOrderNum(entity.getSalesOrderNum());
		// }
		//
		// // Setting Foreign Key
		// //
		// salesDocHeaderDto.setReqMasterId(entity.getReqMaster().getRequestId());
		// salesDocHeaderDto.setReqMasterId(entity.getRequestId());
		//
		// // Converting String from Date
		// //
		// salesDocHeaderDto.setSalesOrderDate(ConvertDateToString(entity.getSalesOrderDate()));
		//
		// salesDocHeaderDto.setSalesOrderDate(entity.getSalesOrderDate());
		//
		// System.err.println(
		// "listofItemTrigger for sales order " + entity.getSalesOrderNum() + "
		// is : " + listofItemTrigger);
		//
		// // Converting list level to dto using export List method and
		// // checking the content of it
		// // Item level list for SO header
		// if (listofItemTrigger) {
		// System.err.println("inside fetching item list data");
		// List<SalesDocItemDto> salesDocItemDtoList =
		// salesDocItemDao.exportList(entity.getSalesDocItemList());
		// System.err
		// .println("sales order " + entity.getSalesOrderNum() + " item data is
		// : " + salesDocItemDtoList);
		// if (salesDocItemDtoList != null && !salesDocItemDtoList.isEmpty()) {
		// salesDocHeaderDto.setSalesDocItemList(salesDocItemDtoList);
		// }
		// }
		// }
		return salesDocHeaderDto;

	}

	@Override
	public List<SalesDocHeaderDo> importList(List<SalesDocHeaderDto> list) {
		if (list != null && !list.isEmpty()) {
			List<SalesDocHeaderDo> doList = new ArrayList<>();
			for (SalesDocHeaderDto entity : list) {

				doList.add(importDto(entity));
			}
			return doList;
		}
		return Collections.emptyList();
	}

	@Override
	public List<SalesDocHeaderDto> exportList(List<SalesDocHeaderDo> list) {
		if (list != null && !list.isEmpty()) {
			List<SalesDocHeaderDto> dtoList = new ArrayList<>();
			for (SalesDocHeaderDo entity : list) {

				dtoList.add(exportDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public String saveOrUpdateSalesDocHeader(SalesDocHeaderDto salesDocHeaderDto) throws ExecutionFault {
		try {
			// Enabling the list of Items here by flag
			listofItemTrigger = Boolean.TRUE;
			List<SalesDocItemDto> itemListDto = salesDocHeaderDto.getSalesDocItemList();
			if (itemListDto != null && !itemListDto.isEmpty()) {
				for (SalesDocItemDto salesDocItemDto : itemListDto) {

					// Setting Foreign key in Item level from Header level
					salesDocItemDto.setSalesHeaderNo(salesDocHeaderDto.getSalesOrderNum());

					// Setting Schedule line level
					List<ScheduleLineDto> scheduleLineDtoList = salesDocItemDto.getScheduleLineList();

					if (scheduleLineDtoList != null && !scheduleLineDtoList.isEmpty()) {
						scheduleLineDtoList.forEach(schLine -> {

							// Setting Foreign Key for Schedule Line Level
							schLine.setSalesHeaderNo(salesDocItemDto.getSalesHeaderNo());
							schLine.setSalesItemOrderNo(salesDocItemDto.getSalesItemOrderNo());
						});
					}
				}
			}

			SalesDocHeaderDo salesDocHeaderDo = importDto(salesDocHeaderDto);
			getSession().saveOrUpdate(salesDocHeaderDo);
			getSession().flush();
			getSession().clear();

			return "Sales Document Header is saved with " + salesDocHeaderDo.getSalesOrderNum();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public List<SalesDocHeaderDto> getSalesDocHeadersWithItems(List<String> salesOrderNumList) {

		// Enabling the list of Items here by flag
		listofItemTrigger = Boolean.TRUE;
		List<SalesDocHeaderDo> salesOrderHeaderList = new ArrayList<>();

		salesOrderNumList.forEach(salesOrderNum -> {
			SalesDocHeaderDo salesDocHeaderDo = getSession().get(SalesDocHeaderDo.class, salesOrderNum);
			if (salesDocHeaderDo != null) {
				salesOrderHeaderList.add(salesDocHeaderDo);
			}
		});

		return exportList(salesOrderHeaderList);
	}

	@Override
	public List<SalesDocHeaderDto> listAllSalesDocHeaders() {
		// Enabling the list of Items here by flag
		listofItemTrigger = Boolean.TRUE;
		return exportList(getSession().createQuery("from SalesDocHeaderDo", SalesDocHeaderDo.class).list());
	}

	@Override
	public SalesDocHeaderDto getSalesDocHeaderById(String salesHeaderOrderId) {
		// Enabling the list of Items here by flag
		listofItemTrigger = Boolean.TRUE;
		return exportDto(getSession().get(SalesDocHeaderDo.class, salesHeaderOrderId));
	}

	@Override
	public SalesDocHeaderDto getSalesDocHeaderByIdWithOutFlag(String salesHeaderOrderId) {
		return exportDtoWithOutFlag(getSession().get(SalesDocHeaderDo.class, salesHeaderOrderId));

	}

	private SalesDocHeaderDto exportDtoWithOutFlag(SalesDocHeaderDo entity) {
		SalesDocHeaderDto salesDocHeaderDto = null;
		if (entity != null) {
			salesDocHeaderDto = new SalesDocHeaderDto();

			System.err.println("Header Entity data at DAO came for sales order num " + entity.getSalesOrderNum());

			salesDocHeaderDto.setCreatedBy(entity.getCreatedBy());
			salesDocHeaderDto.setCreditStatus(entity.getCreditStatus());
			salesDocHeaderDto.setCustomerPo(entity.getCustomerPo());
			salesDocHeaderDto.setCustomerPoType(entity.getCustomerPoType());
			salesDocHeaderDto.setDeliveryStatus(entity.getDeliveryStatus());
			salesDocHeaderDto.setDistributionChannel(entity.getDistributionChannel());
			salesDocHeaderDto.setDivision(entity.getDivision());
			salesDocHeaderDto.setDocCurrency(entity.getDocCurrency());
			salesDocHeaderDto.setOrderCategory(entity.getOrderCategory());
			salesDocHeaderDto.setOrderReason(entity.getOrderReason());
			salesDocHeaderDto.setOrderType(entity.getOrderType());
			salesDocHeaderDto.setOverallStatus(entity.getOverallStatus());
			salesDocHeaderDto.setRejectionStatus(entity.getRejectionStatus());
			salesDocHeaderDto.setCreditBlock(entity.getCreditBlock());
			salesDocHeaderDto.setHeaderBillBlockCode(entity.getHeaderBillBlockCode());
			salesDocHeaderDto.setDeliveryBlockCode(entity.getDeliveryBlockCode());
			salesDocHeaderDto.setSalesOrg(entity.getSalesOrg());
			salesDocHeaderDto.setOrderReasonText(entity.getOrderReasonText());
			salesDocHeaderDto.setOrderRemark(entity.getOrderRemark());
			salesDocHeaderDto.setSoldToParty(entity.getSoldToParty());
			salesDocHeaderDto.setShipToParty(entity.getShipToParty());
			salesDocHeaderDto.setDeliveryBlockCodeText(entity.getDeliveryBlockText());
			salesDocHeaderDto.setDocTypeText(entity.getDocTypeText());
			salesDocHeaderDto.setSoldToPartyText(entity.getSoldToPartyText());
			salesDocHeaderDto.setShipToPartyText(entity.getShipToPartyText());
			salesDocHeaderDto.setSalesman(entity.getSalesman());
			salesDocHeaderDto.setCondGroup5(entity.getCondGroup5());
			salesDocHeaderDto.setBillToParty(entity.getBillToParty());
			salesDocHeaderDto.setBillToPartyText(entity.getBillToPartyText());
			salesDocHeaderDto.setPayer(entity.getPayer());
			salesDocHeaderDto.setPayerText(entity.getPayerText());
			salesDocHeaderDto.setDivisionText(entity.getDivisionText());
			salesDocHeaderDto.setDistrChanText(entity.getDistributionChannelText());
			salesDocHeaderDto.setSalesOrgText(entity.getSalesOrgText());
			salesDocHeaderDto.setApprovalStatus(entity.getApprovalStatus());
			salesDocHeaderDto.setSdProcessStatus(entity.getSdProcessStatus());
			salesDocHeaderDto.setOrderApprovalReason(entity.getOrderApprovalReason());
			salesDocHeaderDto.setTotalNetAmount(entity.getTotalNetAmount());
			salesDocHeaderDto.setOrdererNA(entity.getOrdererNA());
			salesDocHeaderDto.setCondGroup5Text(entity.getCondGroup5Text());
			salesDocHeaderDto.setAttachmentUrl(entity.getAttachmentUrl());
			salesDocHeaderDto.setRequestedBy(entity.getRequestedBy());

			// Setting Primary Key
			if (entity.getSalesOrderNum() != null) {
				salesDocHeaderDto.setSalesOrderNum(entity.getSalesOrderNum());
			}

			// Setting Foreign Key
			// salesDocHeaderDto.setReqMasterId(entity.getReqMaster().getRequestId());
			salesDocHeaderDto.setReqMasterId(entity.getRequestId());

			// Converting String from Date
			// salesDocHeaderDto.setSalesOrderDate(ConvertDateToString(entity.getSalesOrderDate()));

			salesDocHeaderDto.setSalesOrderDate(entity.getSalesOrderDate());

			// Converting list level to dto using export List method and
			// checking the content of it
			// Item level list for SO header
			List<SalesDocItemDto> salesDocItemDtoList = salesDocItemDao.exportList(entity.getSalesDocItemList());
			System.err.println("sales order " + entity.getSalesOrderNum() + " item data is : " + salesDocItemDtoList);
			if (salesDocItemDtoList != null && !salesDocItemDtoList.isEmpty()) {
				salesDocHeaderDto.setSalesDocItemList(salesDocItemDtoList);
			}
		}
		return salesDocHeaderDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SalesDocHeaderDto getSalesDocHeaderWithoutItemsById(String salesHeaderOrderId) {
		// Disabling the list of Items here by flag
		System.err.println("salesHeaderOrderId " + salesHeaderOrderId);
		listofItemTrigger = Boolean.FALSE;
		SalesDocHeaderDto salesDocHeaderDto = null;
		String query = "from SalesDocHeaderDo where salesOrderNum=:soNum";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("soNum", salesHeaderOrderId);

		List<SalesDocHeaderDo> list = q1.getResultList();

		if (list.size() == 0) {
			System.err.println("[SalesDocHeaderDaoImpl][getSalesDocHeaderWithoutItemsById]No SalesOrder");
			return salesDocHeaderDto;
		}
		return ObjectMapperUtils.map(list.get(0), SalesDocHeaderDto.class);
	}

	@Override
	public String deleteSalesDocHeaderById(String salesHeaderOrderId) throws ExecutionFault {
		try {
			// Enabling the list of Items here by flag
			// Session session = sessionfactory.openSession();
			// Transaction tx1 = session.beginTransaction();
			listofItemTrigger = Boolean.TRUE;
			SalesDocHeaderDo salesDocHeaderDo = getSession().load(SalesDocHeaderDo.class, salesHeaderOrderId);
			if (salesDocHeaderDo != null) {

				getSession().delete(salesDocHeaderDo);

				// session.createNativeQuery("DELETE FROM REQUEST_MASTER WHERE
				// REF_DOC_NUM =:REF_DOC_NUM")
				// .setParameter("REF_DOC_NUM", salesHeaderOrderId);
				// session.createNativeQuery("DELETE FROM SCHEDULE_LINE WHERE
				// SALES_ORDER_NUM =:SALES_ORDER_NUM")
				// .setParameter("SALES_ORDER_NUM", salesHeaderOrderId);
				// session.createNativeQuery("DELETE FROM SALES_DOC_ITEM WHERE
				// SALES_ORDER_NUM =:SALES_ORDER_NUM")
				// .setParameter("SALES_ORDER_NUM", salesHeaderOrderId);
				// session.createNativeQuery("DELETE FROM SALES_DOC_HEADER WHERE
				// SALES_ORDER_NUM =:SALES_ORDER_NUM")
				// .setParameter("SALES_ORDER_NUM", salesHeaderOrderId);
				// tx1.commit();
				// session.clear();
				// session.close();

				return "Sales Document Header Order is completedly removed";
			} else {
				return "Sales Document Header Order is not found on Order id : " + salesHeaderOrderId;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	@Override
	public List<SalesDocHeaderDto> listAllSalesDocHeaderWithoutItems() {
		// Disabling the list of Items here by flag
		listofItemTrigger = Boolean.FALSE;
		return exportList(getSession().createQuery("from SalesDocHeaderDo", SalesDocHeaderDo.class).list());
	}

	@Override
	public String getRequestIdWithSoHeader(String salesHeaderOrderId) {
		// Disabling the list of Items here by flag
		listofItemTrigger = Boolean.FALSE;
		String query = "select h.reqMaster.requestId from SalesOrderHeader h where h.salesOrderNum = :soNum";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("soNum", salesHeaderOrderId);
		return (String) q1.getSingleResult();
		// return getSession()
		// .createQuery("select h.reqMaster.requestId from SalesDocHeaderDo h
		// where h.salesOrderNum = :soNum",
		// String.class)
		// .setParameter("soNum", salesHeaderOrderId).getSingleResult();
	}

	@Override
	public List<String> filteredSalesDocHeader(FilterDto filterData) throws ExecutionFault {
		try {
			// when all fields are either null or empty
			if (HelperClass.checkString(filterData.getStatus()) && HelperClass.checkString(filterData.getCustomerCode())
					&& HelperClass.checkString(filterData.getSalesDocNumInitial())
					&& HelperClass.checkString(filterData.getSalesDocNumEnd())
					&& HelperClass.checkString(filterData.getMaterialGroupFor())
					&& HelperClass.checkString(filterData.getDistributionChannel())
					&& filterData.getInitialDate() == null && filterData.getEndDate() == null
					&& HelperClass.checkString(filterData.getSalesOrg())
					&& HelperClass.checkString(filterData.getDivision())
					&& HelperClass.checkString(filterData.getMaterialGroup())
					&& HelperClass.checkString(filterData.getCustomerPo())
					&& HelperClass.checkString(filterData.getItemDlvBlock())
					&& HelperClass.checkString(filterData.getShipToParty())
					&& HelperClass.checkString(filterData.getHeaderDlvBlock())
					&& HelperClass.checkString(filterData.getSapMaterialNum())) {

				System.err.println("For First time Login");
				// getting list of so based on initial rights of login user
				// comment if data access Control goes wrong
				return createQueryforRights(filterData);

				// get all the sales orders from database uncomment if you don't
				// want data access control
				// return listAllSalesDocHeaderWithoutItems();

			} else {

				// Enable the list of Items here by flag
				listofItemTrigger = Boolean.TRUE;
				return creatingCustomQueryForFilters(filterData);

			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> createQueryforRights(FilterDto filterData) {
		System.err.println("createQueryforRights");
		Map<String, String> map = null;
		try {
			// map = (Map<String, String>)
			// HelperClass.fetchUserInfoInIdp(filterData.getUserPID());
			if (map.isEmpty()) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

		// checking if logged in person have all rights
		boolean checkIfAllRights = false;
		if (checkIfAllRights) {
			// if logged person have all rights then return all sales order
			return getSession().createQuery(
					"select distinct i.decisionSetId from SalesDocHeaderDo s join SalesDocItemDo i on s.salesOrderNum = i.salesDocItemKey.salesDocHeader.salesOrderNum",
					String.class).list();
		} else {
			// if logged person do not have all rights build query

			StringBuilder query = new StringBuilder(
					"select distinct i.decisionSetId from SalesDocHeaderDo s join SalesDocItemDo i on s.salesOrderNum = i.salesDocItemKey.salesDocHeader.salesOrderNum");

			if (map.get("customAttribute1") != null && !"*".equals(map.get("customAttribute1"))) {
				query.append(" where i.decisionSetId IS NOT NULL and ");

				String[] split1 = map.get("customAttribute1").split("@");
				query.append("s.salesOrg IN ( ");
				for (int i = 0; i < split1.length; i++) {
					if (i != split1.length - 1) {
						query.append("'" + split1[i] + "'" + ",");
					} else {
						query.append("'" + split1[i] + "'" + " ) ");
					}
				}
			}
			// System.out.println(query);
			// if 1 and 2 both are not null and not *
			if (map.get("customAttribute1") != null && !"*".equals(map.get("customAttribute1"))
					&& map.get("customAttribute2") != null && !"*".equals(map.get("customAttribute2"))) {
				query.append(" AND ");
			}

			// if 1 is * and 2 is not *
			if (map.get("customAttribute1") != null && "*".equals(map.get("customAttribute1"))
					&& map.get("customAttribute2") != null && !"*".equals(map.get("customAttribute2"))) {
				query.append(" where i.decisionSetId IS NOT NULL and ");
			}

			if (map.get("customAttribute2") != null && !"*".equals(map.get("customAttribute2"))) {
				String[] split2 = map.get("customAttribute2").split("@");
				query.append("s.distributionChannel IN ( ");
				for (int i = 0; i < split2.length; i++) {
					if (i != split2.length - 1) {
						query.append("'" + split2[i] + "'" + ",");
					} else {
						query.append("'" + split2[i] + "'" + " ) ");
					}
				}
			}
			// System.out.println(query);

			// if 1/2 not * and 3 are not *
			if ((map.get("customAttribute1") != null && !"*".equals(map.get("customAttribute1"))
					|| map.get("customAttribute2") != null && !"*".equals(map.get("customAttribute2")))
					&& map.get("customAttribute3") != null && !"*".equals(map.get("customAttribute3"))) {
				query.append(" AND ");
			}
			// if 1,2 both are * and 3 is not *
			if (map.get("customAttribute1") != null && "*".equals(map.get("customAttribute1"))
					&& map.get("customAttribute2") != null && "*".equals(map.get("customAttribute2"))
					&& map.get("customAttribute3") != null && !"*".equals(map.get("customAttribute3"))) {
				query.append(" where i.decisionSetId IS NOT NULL and ");
			}
			if (map.get("customAttribute3") != null && !"*".equals(map.get("customAttribute3"))) {
				String[] split3 = map.get("customAttribute3").split("@");
				query.append("s.division IN ( ");
				for (int i = 0; i < split3.length; i++) {
					if (i != split3.length - 1) {
						query.append("'" + split3[i] + "'" + ",");
					} else {
						query.append("'" + split3[i] + "'" + " ) ");
					}

				}
			}
			// System.out.println(query);
			// if 1/2/3 not * and 4 are not *
			if ((map.get("customAttribute1") != null && !"*".equals(map.get("customAttribute1"))
					|| map.get("customAttribute2") != null && !"*".equals(map.get("customAttribute2"))
					|| map.get("customAttribute3") != null && !"*".equals(map.get("customAttribute3")))
					&& map.get("customAttribute4") != null && !"*".equals(map.get("customAttribute4"))) {
				query.append(" AND ");
			}
			// if 1,2,3 All are * and 4 is not *
			if (map.get("customAttribute1") != null && "*".equals(map.get("customAttribute1"))
					&& map.get("customAttribute2") != null && "*".equals(map.get("customAttribute2"))
					&& map.get("customAttribute3") != null && "*".equals(map.get("customAttribute3"))
					&& map.get("customAttribute4") != null && !"*".equals(map.get("customAttribute4"))) {
				query.append(" where i.decisionSetId IS NOT NULL and ");
			}

			if (map.get("customAttribute4") != null && !"*".equals(map.get("customAttribute4"))) {
				String[] split4 = map.get("customAttribute4").split("@");
				query.append("i.materialGroup IN ( ");
				for (int i = 0; i < split4.length; i++) {
					if (i != split4.length - 1) {
						query.append("'" + split4[i] + "'" + ",");
					} else {
						query.append("'" + split4[i] + "'" + " ) ");
					}
				}
			}
			// System.out.println(query);
			// if 1/2/3/4 not * and 5 are not *
			if ((map.get("customAttribute1") != null && !"*".equals(map.get("customAttribute1"))
					|| map.get("customAttribute2") != null && !"*".equals(map.get("customAttribute2"))
					|| map.get("customAttribute3") != null && !"*".equals(map.get("customAttribute3"))
					|| map.get("customAttribute4") != null && !"*".equals(map.get("customAttribute4")))
					&& map.get("customAttribute5") != null && !"*".equals(map.get("customAttribute5"))) {
				query.append(" AND ");
			}
			// if 1,2,3,4 All are * and 5 is not *
			if (map.get("customAttribute1") != null && "*".equals(map.get("customAttribute1"))
					&& map.get("customAttribute2") != null && "*".equals(map.get("customAttribute2"))
					&& map.get("customAttribute3") != null && "*".equals(map.get("customAttribute3"))
					&& map.get("customAttribute4") != null && "*".equals(map.get("customAttribute4"))
					&& map.get("customAttribute5") != null && !"*".equals(map.get("customAttribute5"))) {
				query.append(" where i.decisionSetId IS NOT NULL and ");
			}

			if (map.get("customAttribute5") != null && !"*".equals(map.get("customAttribute5"))) {
				String[] split5 = map.get("customAttribute5").split("@");
				query.append("i.materialGroupFor IN ( ");
				for (int i = 0; i < split5.length; i++) {
					if (i != split5.length - 1) {
						query.append("'" + split5[i] + "'" + ",");
					} else {
						query.append("'" + split5[i] + "'" + " ) ");
					}

				}
			}

			// confusion in between shipToParty and soldToParty

			// if 1/2/3/4/5 not * and 6 are not *
			if ((map.get("customAttribute1") != null && !"*".equals(map.get("customAttribute1"))
					|| map.get("customAttribute2") != null && !"*".equals(map.get("customAttribute2"))
					|| map.get("customAttribute3") != null && !"*".equals(map.get("customAttribute3"))
					|| map.get("customAttribute4") != null && !"*".equals(map.get("customAttribute4"))
					|| map.get("customAttribute5") != null && !"*".equals(map.get("customAttribute5")))
					&& map.get("customAttribute6") != null && !"*".equals(map.get("customAttribute6"))) {
				query.append(" AND ");
			}
			// if 1,2,3,4,5 All are * and 6 is not *
			if (map.get("customAttribute1") != null && "*".equals(map.get("customAttribute1"))
					&& map.get("customAttribute2") != null && "*".equals(map.get("customAttribute2"))
					&& map.get("customAttribute3") != null && "*".equals(map.get("customAttribute3"))
					&& map.get("customAttribute4") != null && "*".equals(map.get("customAttribute4"))
					&& map.get("customAttribute5") != null && "*".equals(map.get("customAttribute5"))
					&& map.get("customAttribute6") != null && !"*".equals(map.get("customAttribute6"))) {
				query.append(" where i.decisionSetId IS NOT NULL and ");
			}

			if (map.get("customAttribute6") != null && !"*".equals(map.get("customAttribute6"))) {
				String[] split6 = map.get("customAttribute6").split("@");
				query.append(" s.soldToParty IN ( ");
				for (int i = 0; i < split6.length; i++) {
					if (i != split6.length - 1) {
						query.append("'" + split6[i] + "'" + ",");
					} else {
						query.append("'" + split6[i] + "'" + " ) ");
					}

				}
			}

			System.err.println("Query for filterData = " + query);

			listofItemTrigger = Boolean.FALSE;
			return getSession().createQuery(query.toString(), String.class).list();

		}

	}

	private List<String> creatingCustomQueryForFilters(FilterDto filterData) {
		StringBuilder startingQuery = new StringBuilder();
		StringBuilder subQuery = new StringBuilder();
		final String and = " AND ";

		startingQuery.append("select distinct i.decisionSetId from SalesDocHeaderDo h join SalesDocItemDo i on "
				+ "h.salesOrderNum = i.salesDocItemKey.salesDocHeader.salesOrderNum where i.decisionSetId IS NOT NULL and ");

		// Appending Sub Query according to filters
		queryAppenderForFilters(filterData, subQuery, and);

		// Setting Parameters for QUERY
		return settingParaForFilterQuery(filterData, startingQuery, subQuery);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<String> settingParaForFilterQuery(FilterDto filterData, StringBuilder startingQuery,
			StringBuilder subQuery) {

		// Creating and Appending sub QUERY
		startingQuery.append(subQuery);
		System.err.println("Query to be searched : " + startingQuery.toString());
		// Setting Parameters for QUERY
		Query query = (Query) getSession().createQuery(startingQuery.toString(), String.class);
		System.err.println("Query : " + query);

		if (filterData.getCustomerCode() != null && filterData.getCustomerCode().trim().length() != 0) {
			query.setParameter("customerCode", filterData.getCustomerCode());
		}

		if (filterData.getDistributionChannel() != null && filterData.getDistributionChannel().trim().length() != 0) {
			query.setParameter("distributionChannel", filterData.getDistributionChannel());
		}

		if (filterData.getSalesDocNumEnd() != null && filterData.getSalesDocNumEnd().trim().length() != 0
				&& filterData.getSalesDocNumInitial() != null
				&& filterData.getSalesDocNumInitial().trim().length() != 0) {
			query.setParameter("soNumInitial", filterData.getSalesDocNumInitial()).setParameter("soNumEnd",
					filterData.getSalesDocNumEnd());
		}

		if (filterData.getStatus() != null && filterData.getStatus().trim().length() != 0) {
			query.setParameter("overallStatus", filterData.getStatus());
		}

		if (filterData.getInitialDate() != null && filterData.getEndDate() != null) {
			query.setParameter("startDate", filterData.getInitialDate()).setParameter("endDate",
					filterData.getEndDate());
		}

		if (filterData.getSalesOrg() != null && filterData.getSalesOrg().trim().length() != 0) {
			query.setParameter("salesOrg", filterData.getSalesOrg());
		}

		if (filterData.getDivision() != null && filterData.getDivision().trim().length() != 0) {
			query.setParameter("division", filterData.getDivision());
		}

		if (filterData.getCustomerPo() != null && filterData.getCustomerPo().trim().length() != 0) {
			query.setParameter("customerPo", filterData.getCustomerPo());
		}

		if (filterData.getShipToParty() != null && filterData.getShipToParty().trim().length() != 0) {
			query.setParameter("shipToParty", filterData.getShipToParty());
		}

		if (filterData.getMaterialGroupFor() != null && filterData.getMaterialGroupFor().trim().length() != 0) {
			query.setParameter("materialGroupFor", filterData.getMaterialGroupFor());
		}

		if (filterData.getMaterialGroup() != null && filterData.getMaterialGroup().trim().length() != 0) {
			query.setParameter("materialGroup", filterData.getMaterialGroup());
		}

		if (filterData.getItemDlvBlock() != null && filterData.getItemDlvBlock().trim().length() != 0) {
			query.setParameter("itemDlvBlock", filterData.getItemDlvBlock());
		}

		if (filterData.getHeaderDlvBlock() != null && filterData.getHeaderDlvBlock().trim().length() != 0) {
			query.setParameter("deliveryBlockCode", filterData.getHeaderDlvBlock());
		}

		if (filterData.getSapMaterialNum() != null && filterData.getSapMaterialNum().trim().length() != 0) {
			query.setParameter("sapMaterialNum", filterData.getSapMaterialNum());
		}

		return query.getResultList();
	}

	private void queryAppenderForFilters(FilterDto filterData, StringBuilder subQuery, final String and) {
		if (filterData.getCustomerCode() != null && filterData.getCustomerCode().trim().length() != 0) {
			subQuery.append(" h.soldToParty = :customerCode ");
		}

		if (filterData.getDistributionChannel() != null && filterData.getDistributionChannel().trim().length() != 0) {
			if (filterData.getCustomerCode() != null && filterData.getCustomerCode().trim().length() != 0) {
				subQuery.append(and);
			}
			subQuery.append(" h.distributionChannel = :distributionChannel ");
		}

		if (filterData.getSalesDocNumInitial() != null && filterData.getSalesDocNumInitial().trim().length() != 0
				&& filterData.getSalesDocNumEnd() != null && filterData.getSalesDocNumEnd().trim().length() != 0) {
			if (filterData.getCustomerCode() != null && filterData.getCustomerCode().trim().length() != 0
					|| filterData.getDistributionChannel() != null
							&& filterData.getDistributionChannel().trim().length() != 0) {
				subQuery.append(and);
			}
			subQuery.append(" h.salesOrderNum between :soNumInitial and :soNumEnd ");
		}

		if (filterData.getStatus() != null && filterData.getStatus().trim().length() != 0) {
			if (filterData.getCustomerCode() != null && filterData.getCustomerCode().trim().length() != 0
					|| filterData.getDistributionChannel() != null
							&& filterData.getDistributionChannel().trim().length() != 0
					|| filterData.getSalesDocNumInitial() != null
							&& filterData.getSalesDocNumInitial().trim().length() != 0
					|| filterData.getSalesDocNumEnd() != null && filterData.getSalesDocNumEnd().trim().length() != 0) {
				subQuery.append(and);
			}
			subQuery.append(" h.overallStatus = :overallStatus ");
		}

		if (filterData.getInitialDate() != null && filterData.getEndDate() != null) {
			if (filterData.getCustomerCode() != null && filterData.getCustomerCode().trim().length() != 0
					|| filterData.getDistributionChannel() != null
							&& filterData.getDistributionChannel().trim().length() != 0
					|| filterData.getSalesDocNumInitial() != null
							&& filterData.getSalesDocNumInitial().trim().length() != 0
							&& filterData.getSalesDocNumEnd() != null
							&& filterData.getSalesDocNumEnd().trim().length() != 0
					|| filterData.getStatus() != null && filterData.getStatus().trim().length() != 0) {
				subQuery.append(and);
			}
			subQuery.append(" h.salesOrderDate between :startDate and :endDate ");
		}

		if (filterData.getSalesOrg() != null && filterData.getSalesOrg().trim().length() != 0) {
			if (filterData.getCustomerCode() != null && filterData.getCustomerCode().trim().length() != 0
					|| filterData.getDistributionChannel() != null
							&& filterData.getDistributionChannel().trim().length() != 0
					|| filterData.getSalesDocNumInitial() != null
							&& filterData.getSalesDocNumInitial().trim().length() != 0
							&& filterData.getSalesDocNumEnd() != null
							&& filterData.getSalesDocNumEnd().trim().length() != 0
					|| filterData.getStatus() != null && filterData.getStatus().trim().length() != 0
					|| filterData.getInitialDate() != null && filterData.getEndDate() != null) {
				subQuery.append(and);
			}
			subQuery.append(" h.salesOrg = :salesOrg ");
		}

		if (filterData.getDivision() != null && filterData.getDivision().trim().length() != 0) {
			if (filterData.getCustomerCode() != null && filterData.getCustomerCode().trim().length() != 0
					|| filterData.getDistributionChannel() != null
							&& filterData.getDistributionChannel().trim().length() != 0
					|| filterData.getSalesDocNumInitial() != null
							&& filterData.getSalesDocNumInitial().trim().length() != 0
							&& filterData.getSalesDocNumEnd() != null
							&& filterData.getSalesDocNumEnd().trim().length() != 0
					|| filterData.getStatus() != null && filterData.getStatus().trim().length() != 0
					|| filterData.getInitialDate() != null && filterData.getEndDate() != null
					|| filterData.getSalesOrg() != null && filterData.getSalesOrg().trim().length() != 0) {
				subQuery.append(and);
			}
			subQuery.append(" h.division = :division ");
		}

		if (filterData.getCustomerPo() != null && filterData.getCustomerPo().trim().length() != 0) {
			if (filterData.getCustomerCode() != null && filterData.getCustomerCode().trim().length() != 0
					|| filterData.getDistributionChannel() != null
							&& filterData.getDistributionChannel().trim().length() != 0
					|| filterData.getSalesDocNumInitial() != null
							&& filterData.getSalesDocNumInitial().trim().length() != 0
							&& filterData.getSalesDocNumEnd() != null
							&& filterData.getSalesDocNumEnd().trim().length() != 0
					|| filterData.getStatus() != null && filterData.getStatus().trim().length() != 0
					|| filterData.getInitialDate() != null && filterData.getEndDate() != null
					|| filterData.getSalesOrg() != null && filterData.getSalesOrg().trim().length() != 0
					|| filterData.getDivision() != null && filterData.getDivision().trim().length() != 0) {
				subQuery.append(and);
			}
			subQuery.append(" h.customerPo = :customerPo ");
		}

		if (filterData.getShipToParty() != null && filterData.getShipToParty().trim().length() != 0) {
			if (filterData.getCustomerPo() != null && filterData.getCustomerPo().trim().length() != 0
					|| filterData.getCustomerCode() != null && filterData.getCustomerCode().trim().length() != 0
					|| filterData.getDistributionChannel() != null
							&& filterData.getDistributionChannel().trim().length() != 0
					|| filterData.getSalesDocNumInitial() != null
							&& filterData.getSalesDocNumInitial().trim().length() != 0
							&& filterData.getSalesDocNumEnd() != null
							&& filterData.getSalesDocNumEnd().trim().length() != 0
					|| filterData.getStatus() != null && filterData.getStatus().trim().length() != 0
					|| filterData.getInitialDate() != null && filterData.getEndDate() != null
					|| filterData.getSalesOrg() != null && filterData.getSalesOrg().trim().length() != 0
					|| filterData.getDivision() != null && filterData.getDivision().trim().length() != 0) {
				subQuery.append(and);
			}
			subQuery.append(" h.shipToParty = :shipToParty ");
		}

		if (filterData.getHeaderDlvBlock() != null && filterData.getHeaderDlvBlock().trim().length() != 0) {
			if (filterData.getCustomerCode() != null && filterData.getCustomerCode().trim().length() != 0
					|| filterData.getDistributionChannel() != null
							&& filterData.getDistributionChannel().trim().length() != 0
					|| filterData.getSalesDocNumInitial() != null
							&& filterData.getSalesDocNumInitial().trim().length() != 0
							&& filterData.getSalesDocNumEnd() != null
							&& filterData.getSalesDocNumEnd().trim().length() != 0
					|| filterData.getStatus() != null && filterData.getStatus().trim().length() != 0
					|| filterData.getInitialDate() != null && filterData.getEndDate() != null
					|| filterData.getSalesOrg() != null && filterData.getSalesOrg().trim().length() != 0
					|| filterData.getDivision() != null && filterData.getDivision().trim().length() != 0
					|| filterData.getCustomerPo() != null && filterData.getCustomerPo().trim().length() != 0
					|| filterData.getShipToParty() != null && filterData.getShipToParty().trim().length() != 0) {
				subQuery.append(and);
			}
			subQuery.append(" h.deliveryBlockCode = :deliveryBlockCode ");

		}

		if (filterData.getSapMaterialNum() != null && filterData.getSapMaterialNum().trim().length() != 0) {
			if (filterData.getCustomerCode() != null && filterData.getCustomerCode().trim().length() != 0
					|| filterData.getDistributionChannel() != null
							&& filterData.getDistributionChannel().trim().length() != 0
					|| filterData.getSalesDocNumInitial() != null
							&& filterData.getSalesDocNumInitial().trim().length() != 0
							&& filterData.getSalesDocNumEnd() != null
							&& filterData.getSalesDocNumEnd().trim().length() != 0
					|| filterData.getStatus() != null && filterData.getStatus().trim().length() != 0
					|| filterData.getInitialDate() != null && filterData.getEndDate() != null
					|| filterData.getSalesOrg() != null && filterData.getSalesOrg().trim().length() != 0
					|| filterData.getDivision() != null && filterData.getDivision().trim().length() != 0
					|| filterData.getCustomerPo() != null && filterData.getCustomerPo().trim().length() != 0
					|| filterData.getShipToParty() != null && filterData.getShipToParty().trim().length() != 0
					|| filterData.getHeaderDlvBlock() != null && filterData.getHeaderDlvBlock().trim().length() != 0) {
				subQuery.append(and);
			}
			subQuery.append(" i.sapMaterialNum = :sapMaterialNum ");

		}

		if (filterData.getMaterialGroupFor() != null && filterData.getMaterialGroupFor().trim().length() != 0) {
			if (filterData.getCustomerCode() != null && filterData.getCustomerCode().trim().length() != 0
					|| filterData.getDistributionChannel() != null
							&& filterData.getDistributionChannel().trim().length() != 0
					|| filterData.getSalesDocNumInitial() != null
							&& filterData.getSalesDocNumInitial().trim().length() != 0
							&& filterData.getSalesDocNumEnd() != null
							&& filterData.getSalesDocNumEnd().trim().length() != 0
					|| filterData.getStatus() != null && filterData.getStatus().trim().length() != 0
					|| filterData.getInitialDate() != null && filterData.getEndDate() != null
					|| filterData.getSalesOrg() != null && filterData.getSalesOrg().trim().length() != 0
					|| filterData.getDivision() != null && filterData.getDivision().trim().length() != 0
					|| filterData.getCustomerPo() != null && filterData.getCustomerPo().trim().length() != 0
					|| filterData.getShipToParty() != null && filterData.getShipToParty().trim().length() != 0
					|| filterData.getHeaderDlvBlock() != null && filterData.getHeaderDlvBlock().trim().length() != 0
					|| filterData.getSapMaterialNum() != null && filterData.getSapMaterialNum().trim().length() != 0) {
				subQuery.append(and);
			}
			subQuery.append(" i.materialGroupFor = :materialGroupFor ");

		}

		if (filterData.getMaterialGroup() != null && filterData.getMaterialGroup().trim().length() != 0) {
			if (filterData.getCustomerCode() != null && filterData.getCustomerCode().trim().length() != 0
					|| filterData.getDistributionChannel() != null
							&& filterData.getDistributionChannel().trim().length() != 0
					|| filterData.getSalesDocNumInitial() != null
							&& filterData.getSalesDocNumInitial().trim().length() != 0
							&& filterData.getSalesDocNumEnd() != null
							&& filterData.getSalesDocNumEnd().trim().length() != 0
					|| filterData.getStatus() != null && filterData.getStatus().trim().length() != 0
					|| filterData.getInitialDate() != null && filterData.getEndDate() != null
					|| filterData.getSalesOrg() != null && filterData.getSalesOrg().trim().length() != 0
					|| filterData.getDivision() != null && filterData.getDivision().trim().length() != 0
					|| filterData.getCustomerPo() != null && filterData.getCustomerPo().trim().length() != 0
					|| filterData.getMaterialGroupFor() != null && filterData.getMaterialGroupFor().trim().length() != 0
					|| filterData.getShipToParty() != null && filterData.getShipToParty().trim().length() != 0
					|| filterData.getHeaderDlvBlock() != null && filterData.getHeaderDlvBlock().trim().length() != 0
					|| filterData.getSapMaterialNum() != null && filterData.getSapMaterialNum().trim().length() != 0) {
				subQuery.append(and);
			}
			subQuery.append(" i.materialGroup = :materialGroup ");
		}

		if (filterData.getItemDlvBlock() != null && filterData.getItemDlvBlock().trim().length() != 0) {
			if (filterData.getCustomerCode() != null && filterData.getCustomerCode().trim().length() != 0
					|| filterData.getDistributionChannel() != null
							&& filterData.getDistributionChannel().trim().length() != 0
					|| filterData.getSalesDocNumInitial() != null
							&& filterData.getSalesDocNumInitial().trim().length() != 0
							&& filterData.getSalesDocNumEnd() != null
							&& filterData.getSalesDocNumEnd().trim().length() != 0
					|| filterData.getStatus() != null && filterData.getStatus().trim().length() != 0
					|| filterData.getInitialDate() != null && filterData.getEndDate() != null
					|| filterData.getSalesOrg() != null && filterData.getSalesOrg().trim().length() != 0
					|| filterData.getDivision() != null && filterData.getDivision().trim().length() != 0
					|| filterData.getCustomerPo() != null && filterData.getCustomerPo().trim().length() != 0
					|| filterData.getMaterialGroupFor() != null && filterData.getMaterialGroupFor().trim().length() != 0
					|| filterData.getMaterialGroup() != null && filterData.getMaterialGroup().trim().length() != 0
					|| filterData.getShipToParty() != null && filterData.getShipToParty().trim().length() != 0
					|| filterData.getHeaderDlvBlock() != null && filterData.getHeaderDlvBlock().trim().length() != 0
					|| filterData.getSapMaterialNum() != null && filterData.getSapMaterialNum().trim().length() != 0) {
				subQuery.append(and);
			}
			subQuery.append(" i.itemDlvBlock = :itemDlvBlock ");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer testQuery() throws ExecutionFault {
		try {

			List<SalesDocHeaderDo> list = getSession()
					.createQuery(
							"select distinct h from SalesDocHeaderDo h join SalesDocItemDo i on h.salesOrderNum = i.salesDocItemKey.salesDocHeader.salesOrderNum "
									+ "where h.customerPo IN ('test01','test02') AND h.salesOrg IN ('TH54','TH53')")
					.list();

			return list.size();

		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public SalesDocHeaderDto getSalesDocHeaderByIdSession(String salesOrderNum) {
		// Enabling the list of Items here by flag
		listofItemTrigger = Boolean.TRUE;
		SalesDocHeaderDto headerDto = exportDto(getSession()
				.createQuery("from SalesDocHeaderDo s where s.salesOrderNum =: salesOrderNum", SalesDocHeaderDo.class)
				.setParameter("salesOrderNum", salesOrderNum).getSingleResult());
		getSession().flush();
		getSession().clear();
		return headerDto;
	}

}
