package com.incture.cherrywork.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.SalesDocHeaderDto;
import com.incture.cherrywork.dtos.SalesDocItemDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderInput;
import com.incture.cherrywork.dtos.ScheduleLineDto;
import com.incture.cherrywork.entities.SalesDocHeaderDo;
import com.incture.cherrywork.entities.SalesDocItemDo;
import com.incture.cherrywork.entities.SalesDocItemPrimaryKeyDo;
import com.incture.cherrywork.entities.ScheduleLineDo;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.repositories.ISalesDocHeaderRepository;
import com.incture.cherrywork.repositories.ISalesDocItemRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;

@Service
@Transactional
public class SalesDocItemDaoImpl implements SalesDocItemDao {

	@Lazy
	@Autowired
	private ScheduleLineDaoImpl scheduleLineRepo;

	@Lazy
	@Autowired
	private SalesDocHeaderDaoImpl salesDocHeaderRepo;

	@Autowired
	private ISalesDocItemRepository salesDocItemRepository;

	@Autowired
	private SalesDocHeaderDao salesDocHeaderDao;

	@Autowired
	private ISalesDocHeaderRepository salesDocHeaderRepository;

	

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public SalesDocItemDo importDto(SalesDocItemDto fromDto) {
		SalesDocItemDo salesDocItemDo = null;
		if (fromDto != null) {
			salesDocItemDo = new SalesDocItemDo();

			salesDocItemDo.setSapMaterialNum(fromDto.getSapMaterialNum());
			salesDocItemDo.setBatchNum(fromDto.getBatchNum());
			salesDocItemDo.setSplPrice(fromDto.getSplPrice());
			salesDocItemDo.setMaterialGroup(fromDto.getMaterialGroup());
			salesDocItemDo.setShortText(fromDto.getShortText());
			salesDocItemDo.setItemCategory(fromDto.getItemCategory());
			salesDocItemDo.setItemType(fromDto.getItemType());
			salesDocItemDo.setSalesUnit(fromDto.getSalesUnit());
			salesDocItemDo.setItemBillingBlock(fromDto.getItemBillingBlock());
			salesDocItemDo.setItemDlvBlock(fromDto.getItemDlvBlock());
			salesDocItemDo.setPartialDlv(fromDto.getPartialDlv());
			salesDocItemDo.setRefDocNum(fromDto.getRefDocNum());
			salesDocItemDo.setRefDocItem(fromDto.getRefDocItem());
			salesDocItemDo.setPlant(fromDto.getPlant());
			salesDocItemDo.setStorageLoc(fromDto.getStorageLoc());
			salesDocItemDo.setNetPrice(Double.parseDouble(fromDto.getNetPrice()));
			salesDocItemDo.setDocCurrency(fromDto.getDocCurrency());
			salesDocItemDo.setPricingUnit(fromDto.getPricingUnit());
			salesDocItemDo.setCoudUnit(fromDto.getCoudUnit());
			salesDocItemDo.setNetWorth(Double.parseDouble(fromDto.getNetWorth()));
			salesDocItemDo.setOverallStatus(fromDto.getOverallStatus());
			salesDocItemDo.setDeliveryStatus(fromDto.getDeliveryStatus());
			salesDocItemDo.setReasonForRejection(fromDto.getReasonForRejection());
			salesDocItemDo.setReasonForRejectionText(fromDto.getReasonForRejectionText());
			salesDocItemDo.setMaterialGroupFor(fromDto.getMaterialGroup4());
			salesDocItemDo.setBaseUnit(fromDto.getBaseUnit());
			salesDocItemDo.setHigherLevelItem(fromDto.getHigherLevelItem());
			salesDocItemDo.setTaxAmount(fromDto.getTaxAmount());
			salesDocItemDo.setOldMatCode(fromDto.getOldMatCode());
			salesDocItemDo.setConvDen(fromDto.getConvDen());
			salesDocItemDo.setConvNum(fromDto.getConvNum());
			salesDocItemDo.setCuConfQtyBase(fromDto.getCuConfQtyBase());
			salesDocItemDo.setCuConfQtySales(fromDto.getCuConfQtySales());
			salesDocItemDo.setCuReqQtySales(fromDto.getCuReqQtySales());
			salesDocItemDo.setOrderedQtySales(fromDto.getOrderedQtySales());
			salesDocItemDo.setDecisionSetId(fromDto.getDecisionSetId());
			salesDocItemDo.setItemStagingStatus(fromDto.getItemStagingStatus());
			salesDocItemDo.setItemCategText(fromDto.getItemCategText());
			salesDocItemDo.setSalesArea(fromDto.getSalesArea());
			salesDocItemDo.setSalesTeam(fromDto.getSalesTeam());
			salesDocItemDo.setMatExpiryDate(fromDto.getMatExpiryDate());
			salesDocItemDo.setSerialNumber(fromDto.getSerialNumber());

			// Converting list level to entity using import List method and
			// checking the content of it
			List<ScheduleLineDo> scheduleLineList = scheduleLineRepo.importList(fromDto.getScheduleLineList());
			if (scheduleLineList != null && !scheduleLineList.isEmpty()) {
				salesDocItemDo.setScheduleLineList(scheduleLineList);
			}

			// Converting Date from String
			// salesDocItemDo.setFirstDeliveryDate(ConvertStringToDate(fromDto.getFirstDeliveryDate()));
			salesDocItemDo.setFirstDeliveryDate(fromDto.getFirstDeliveryDate());

			// Setting Composite Primary Key and Foreign Key
			SalesDocHeaderDo salesDocHeaderDo = new SalesDocHeaderDo();
			// Setting Foreign Key
			salesDocHeaderDo.setSalesOrderNum(fromDto.getSalesHeaderNo());
			// Setting Composite Primary Key
			salesDocItemDo
					.setSalesDocItemKey(new SalesDocItemPrimaryKeyDo(fromDto.getSalesItemOrderNo(), salesDocHeaderDo));

			// try {
			// // Setting Comment List from comment table
			// saveCommentList(fromDto.getCommentDtoList(),
			// fromDto.getReturnReqNum());
			// } catch (ExecutionFault e) {
			// return null;
			// }

		}
		return salesDocItemDo;
	}

	
	public SalesDocItemDto exportDto(SalesDocItemDo entity) {
		SalesDocItemDto salesDocItemDto = null;
		if (entity != null) {

			salesDocItemDto = new SalesDocItemDto();

			System.err.println("Item Entity data at DAO came for sales order num "
					+ entity.getSalesDocItemKey().getSalesDocHeader().getSalesOrderNum() + " and item num is "
					+ entity.getSalesDocItemKey().getSalesItemOrderNo());
			salesDocItemDto.setSapMaterialNum(entity.getSapMaterialNum());
			salesDocItemDto.setBatchNum(entity.getBatchNum());
			salesDocItemDto.setSplPrice(entity.getSplPrice());
			salesDocItemDto.setMaterialGroup(entity.getMaterialGroup());
			salesDocItemDto.setShortText(entity.getShortText());
			salesDocItemDto.setItemCategory(entity.getItemCategory());
			salesDocItemDto.setItemType(entity.getItemType());
			salesDocItemDto.setSalesUnit(entity.getSalesUnit());
			salesDocItemDto.setItemBillingBlock(entity.getItemBillingBlock());
			salesDocItemDto.setItemDlvBlock(entity.getItemDlvBlock());
			salesDocItemDto.setPartialDlv(entity.getPartialDlv());
			salesDocItemDto.setRefDocNum(entity.getRefDocNum());
			salesDocItemDto.setRefDocItem(entity.getRefDocItem());
			salesDocItemDto.setPlant(entity.getPlant());
			salesDocItemDto.setStorageLoc(entity.getStorageLoc());
			// Split net price and add zero
			salesDocItemDto.setNetPrice(entity.getNetPrice().toString());

			salesDocItemDto.setDocCurrency(entity.getDocCurrency());
			salesDocItemDto.setPricingUnit(entity.getPricingUnit());
			salesDocItemDto.setCoudUnit(entity.getCoudUnit());
			salesDocItemDto.setOldMatCode(entity.getOldMatCode());
			salesDocItemDto.setNetWorth(entity.getNetWorth().toString());
			salesDocItemDto.setOverallStatus(entity.getOverallStatus());
			salesDocItemDto.setDeliveryStatus(entity.getDeliveryStatus());
			salesDocItemDto.setReasonForRejection(entity.getReasonForRejection());
			salesDocItemDto.setReasonForRejectionText(entity.getReasonForRejectionText());
			salesDocItemDto.setMaterialGroup4(entity.getMaterialGroupFor());
			salesDocItemDto.setBaseUnit(entity.getBaseUnit());
			salesDocItemDto.setHigherLevelItem(entity.getHigherLevelItem());
			salesDocItemDto.setTaxAmount(entity.getTaxAmount());
			salesDocItemDto.setConvDen(entity.getConvDen());
			salesDocItemDto.setConvNum(entity.getConvNum());
			salesDocItemDto.setCuConfQtyBase(entity.getCuConfQtyBase());
			salesDocItemDto.setCuConfQtySales(entity.getCuConfQtySales());
			salesDocItemDto.setCuReqQtySales(entity.getCuReqQtySales());
			salesDocItemDto.setOrderedQtySales(entity.getOrderedQtySales());
			salesDocItemDto.setDecisionSetId(entity.getDecisionSetId());
			salesDocItemDto.setItemStagingStatus(entity.getItemStagingStatus());
			salesDocItemDto.setItemCategText(entity.getItemCategText());
			salesDocItemDto.setSalesArea(entity.getSalesArea());
			salesDocItemDto.setSalesTeam(entity.getSalesTeam());
			salesDocItemDto.setMatExpiryDate(entity.getMatExpiryDate());
			salesDocItemDto.setSerialNumber(entity.getSerialNumber());

			// Converting list level to entity using import List method and
			// checking the content of it
			List<ScheduleLineDto> scheduleLineList = scheduleLineRepo.exportList(entity.getScheduleLineList());
			if (scheduleLineList != null && !scheduleLineList.isEmpty()) {
				salesDocItemDto.setScheduleLineList(scheduleLineList);
				StringBuilder itemDlvBlockCodes = new StringBuilder();
				int size = 0;
				for (ScheduleLineDto scheduleLineDto : scheduleLineList) {
					size++;
					if (scheduleLineDto.getSchlineDeliveryBlock() != null) {
						itemDlvBlockCodes.append(scheduleLineDto.getSchlineDeliveryBlock());
						if (size != scheduleLineList.size()) {
							itemDlvBlockCodes.append(",");
						}
					}
				}
				salesDocItemDto.setItemDlvBlockText(scheduleLineList.get(0).getRelfordelText());
				salesDocItemDto.setItemDlvBlock(itemDlvBlockCodes.toString());
			}

			// Converting String from Date
			// salesDocItemDto.setFirstDeliveryDate(ConvertDateToString(entity.getFirstDeliveryDate()));
			salesDocItemDto.setFirstDeliveryDate(entity.getFirstDeliveryDate());

			// Setting Foreign Key with Composite Primary Key
			salesDocItemDto.setSalesHeaderNo(entity.getSalesDocItemKey().getSalesDocHeader().getSalesOrderNum());
			// Setting Primary Key
			salesDocItemDto.setSalesItemOrderNo(entity.getSalesDocItemKey().getSalesItemOrderNo());

			// Setting stock block logic
			// If req qty is greater than conf Qty
			if (entity.getCuReqQtySales() != null && entity.getCuConfQtySales() != null) {
				if (entity.getCuReqQtySales() > entity.getCuConfQtySales()) {
					salesDocItemDto.setItemStockBlock("ACTIVE");
				}
			}

		}
		return salesDocItemDto;

	}

	
	public List<SalesDocItemDo> importList(List<SalesDocItemDto> list) {
		if (list != null && !list.isEmpty()) {
			List<SalesDocItemDo> dtoList = new ArrayList<>();
			for (SalesDocItemDto entity : list) {

				dtoList.add(importDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	
	public List<SalesDocItemDto> exportList(List<SalesDocItemDo> list) {
		if (list != null && !list.isEmpty()) {
			List<SalesDocItemDto> dtoList = new ArrayList<>();
			for (SalesDocItemDo entity : list) {

				dtoList.add(exportDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public String saveOrUpdateSalesDocItem(SalesDocItemDto salesDocItemDto) throws ExecutionFault {
		try {
			SalesDocItemDo salesDocItemDo = importDto(salesDocItemDto);
			salesDocItemRepository.save(salesDocItemDo);
			return "Sales Document Item is successfully created with =" + salesDocItemDo.getSalesDocItemKey();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	// added for DS update - Lock issue // Mohit is also using this method for
	// update from save/edit functionality
	@Override
	public String saveOrUpdateSalesDocItemForDS(SalesDocItemDto salesDocItemDto) throws ExecutionFault {

		try {

			String query = "from SalesDocHeaderDo where salesOrderNum=:soNum";
			Query q1 = entityManager.createQuery(query);
			q1.setParameter("soNum", salesDocItemDto.getSalesHeaderNo());
			SalesDocHeaderDo header = (SalesDocHeaderDo) q1.getSingleResult();

			SalesDocHeaderDto headerDto = salesDocHeaderDao.exportDto(header);
			List<SalesDocItemDto> itemList = new ArrayList<>();
			itemList.add(salesDocItemDto);
			headerDto.setSalesDocItemList(itemList);

			List<SalesDocItemDto> itemListDto = headerDto.getSalesDocItemList();
			if (itemListDto != null && !itemListDto.isEmpty()) {
				for (SalesDocItemDto item : itemListDto) {

					// Setting Foreign key in Item level from Header level
					item.setSalesHeaderNo(headerDto.getSalesOrderNum());

					// Setting Schedule line level
					List<ScheduleLineDto> scheduleLineDtoList = item.getScheduleLineList();

					if (scheduleLineDtoList != null && !scheduleLineDtoList.isEmpty()) {
						scheduleLineDtoList.forEach(schLine -> {

							// Setting Foreign Key for Schedule Line Level
							schLine.setSalesHeaderNo(item.getSalesHeaderNo());
							schLine.setSalesItemOrderNo(item.getSalesItemOrderNo());
							ScheduleLineDo scheduleLineDo = ObjectMapperUtils.map(schLine, ScheduleLineDo.class);

						});
					}
				}
			}

			SalesDocHeaderDo salesDocHeaderDo = salesDocHeaderDao.importDto(headerDto);
			System.err.println(
					"[salesDocHeaderDaoImpl][saveSalesDocHeader] salesDocHeaderDo: " + salesDocHeaderDo.toString());
			// salesDocHeaderDo.setRequestId(ServicesUtil.randomId());
			SalesDocHeaderDo savedSalesDocHeaderDo = null;
			try {
				savedSalesDocHeaderDo = salesDocHeaderRepository.save(salesDocHeaderDo);
			} catch (Exception e) {
				System.err.println("exception occured while saving(decision set): " + e.getMessage());
				e.printStackTrace();
			}
			System.err.println("[salesDocHeaderDaoImpl][saveSalesDocHeader] savedSalesDocHeaderDo "
					+ savedSalesDocHeaderDo.toString());
			System.err.println("Saved Sales Doc Header");

			// SalesDocItemDo salesDocItemDo = importDto(salesDocItemDto);
			// System.err.println("[saveOrUpdateSalesDocItemForDS]
			// salesDocItemDo: "+salesDocItemDo.toString());
			// salesDocItemRepository.save(salesDocItemDo);

			return "Sales Document Item is successfully created with =";
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SalesDocItemDto> listAllSalesDocItems() {
		String query = "from SalesDocItemDo";
		Query q1 = entityManager.createQuery(query);
		return exportList(q1.getResultList());
	}

	@Override
	public SalesDocItemDto getSalesDocItemById(String salesItemId, String salesHeaderId) {
		// Creating Sales Header Entity For inserting in Composite PK
		SalesDocHeaderDo salesDocHeader = new SalesDocHeaderDo();
		// Setting Sales Header Primary Key
		salesDocHeader.setSalesOrderNum(salesHeaderId);
		// return null;
		String query = "from SalesDocItemDo item where item.salesDocItemKey.salesDocHeader.salesOrderNum = :soNum";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("soNum", salesHeaderId);
		return exportDto(
				(SalesDocItemDo)q1.getSingleResult());
	}

	@SuppressWarnings("unused")
	@Override
	public String deleteSalesDocItemById(String salesItemId, String salesHeaderId) throws ExecutionFault {
		try {
			// Creating Sales Header Entity For inserting in Composite PK
			SalesDocHeaderDo salesDocHeader = new SalesDocHeaderDo();
			// Setting Sales Header Primary Key
			salesDocHeader.setSalesOrderNum(salesHeaderId);
			SalesDocItemDo salesDocItemDo = null;
			// SalesDocItemDo salesDocItemDo =
			// getSession().byId(SalesDocItemDo.class)
			// .load(new SalesDocItemPrimaryKeyDo(salesItemId, salesDocHeader));
			if (salesDocItemDo != null) {
				salesDocItemRepository.delete(salesDocItemDo);
				return "Sales Document Item is completedly removed";
			} else {
				return "Sales Document Item is not found on Item id : " + salesItemId;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	@Override
	public List<SalesDocItemDto> listOfItemsInSalesOrder(String salesHeaderId) {

		String query = "from SalesDocItemDo item where item.salesDocItemKey.salesDocHeader.salesOrderNum =: salesHeaderId";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("salesHeaderId", salesHeaderId);
		return exportList(q1.getResultList());
	}

	@Override
	public List<String> getDSBySalesHeaderID(String salesHeaderId) {

		String query = "select distinct ds.decisionSetId from SalesDocItemDo ds where ds.salesDocItemKey.salesDocHeader.salesOrderNum = :salesOrderNum and ds.decisionSetId is not null";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("salesOrderNum", salesHeaderId);
		return q1.getResultList();
	}

	@Override
	public List<SalesDocItemDto> listOfItemsFromMultiItemId(SalesOrderHeaderInput soInput) {
		List<String> salesDocItemIdList = soInput.getSalesOrderItemIdList();
		List<SalesDocItemDto> salesDocItemDtoList = new ArrayList<>();
		for (String itemId : salesDocItemIdList) {
			SalesDocItemDto item = getSalesDocItemById(itemId, soInput.getSalesOrderHeaderId());
			if (item != null) {
				salesDocItemDtoList.add(item);
			}
		}
		return salesDocItemDtoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	synchronized public String updateSalesDocItemWithDecisionSet(String decisionSet, String salesItemId,
			String salesHeaderId) throws ExecutionFault {
		try {
			// Creating Sales Header Entity For inserting in Composite PK
			SalesDocHeaderDo salesDocHeader = salesDocHeaderRepo
					.importDto(salesDocHeaderRepo.getSalesDocHeaderById(salesHeaderId));

			String query1 = "from SalesDocItemDo item where item.salesDocItemKey.salesDocHeader.salesOrderNum = :soNum";
			Query q1 = entityManager.createQuery(query1);
			q1.setParameter("soNum", salesHeaderId);

			SalesDocItemDo salesDocItemDo = (SalesDocItemDo) q1.getSingleResult();
					
			if (salesDocItemDo != null) {

				String query2 = "update SalesDocItemDo s set s.decisionSetId =: decisionSetId"
						+ " where s.salesDocItemKey.salesItemOrderNo =: salesItemOrderNo"
						+ " and s.salesDocItemKey.salesDocHeader.salesOrderNum =: salesOrderNum";
				Query q2 = entityManager.createQuery(query2);

				q2.setParameter("decisionSetId", decisionSet);
				q2.setParameter("salesItemOrderNo", salesItemId);
				q2.setParameter("salesOrderNum", salesHeaderId);

				q2.executeUpdate();

				return "Sales Document Item is updated with decision set : " + decisionSet;
			} else {

				return "Sales Document Item is not found on Item id : " + salesItemId + " and " + salesDocHeader;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	@Override
	public List<String> getItemListByDataSet(String dataSet) {
		if (dataSet != null) {

			String query = "select item.salesDocItemKey.salesItemOrderNo from SalesDocItemDo item where item.decisionSetId = :decisionSetId";
			Query q1 = entityManager.createQuery(query);
			q1.setParameter("decisionSetId", dataSet);
			return q1.getResultList();

		} else {
			return null;

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalesDocItemDto> getSalesDocItemsByDecisionSetId(String decisionSetId) {
		String query = "from SalesDocItemDo item where item.decisionSetId = :dsId";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("dsId", decisionSetId);
		return exportList(q1.getResultList());
	}

}
