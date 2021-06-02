package com.incture.cherrywork.dao;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.SalesDocItemDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderInput;
import com.incture.cherrywork.dtos.ScheduleLineDto;
import com.incture.cherrywork.entities.SalesDocHeaderDo;
import com.incture.cherrywork.entities.SalesDocItemDo;
import com.incture.cherrywork.entities.SalesDocItemPrimaryKeyDo;
import com.incture.cherrywork.entities.ScheduleLineDo;
import com.incture.cherrywork.exceptions.ExecutionFault;



@Repository
@Component
public class SalesDocItemDaoImpl extends BaseDao<SalesDocItemDo, SalesDocItemDto> implements SalesDocItemDao {
  
	@Lazy
	@Autowired
	private ScheduleLineDaoImpl scheduleLineRepo;

	@Lazy
	@Autowired
	private SalesDocHeaderDaoImpl salesDocHeaderRepo;

	@Autowired
	private SessionFactory sessionfactory;

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

	@Override
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

	@Override
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

	@Override
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
			getSession().merge(salesDocItemDo);
			getSession().flush();
			getSession().clear();
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
			Session session = sessionfactory.openSession();
			Transaction tx = session.beginTransaction();

			SalesDocItemDo salesDocItemDo = importDto(salesDocItemDto);
			session.saveOrUpdate(salesDocItemDo);

			tx.commit();
			session.close();
			return "Sales Document Item is successfully created with =" + salesDocItemDo.getSalesDocItemKey();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SalesDocItemDto> listAllSalesDocItems() {
		return exportList(getSession().createQuery("from SalesDocItemDo", SalesDocItemDo.class).list());
	}

	@Override
	public SalesDocItemDto getSalesDocItemById(String salesItemId, String salesHeaderId) {
		// Creating Sales Header Entity For inserting in Composite PK
		SalesDocHeaderDo salesDocHeader = new SalesDocHeaderDo();
		// Setting Sales Header Primary Key
		salesDocHeader.setSalesOrderNum(salesHeaderId);
		return exportDto(
				getSession().get(SalesDocItemDo.class, new SalesDocItemPrimaryKeyDo(salesItemId, salesDocHeader)));
	}

	@Override
	public String deleteSalesDocItemById(String salesItemId, String salesHeaderId) throws ExecutionFault {
		try {
			// Creating Sales Header Entity For inserting in Composite PK
			SalesDocHeaderDo salesDocHeader = new SalesDocHeaderDo();
			// Setting Sales Header Primary Key
			salesDocHeader.setSalesOrderNum(salesHeaderId);
			SalesDocItemDo salesDocItemDo = getSession().byId(SalesDocItemDo.class)
					.load(new SalesDocItemPrimaryKeyDo(salesItemId, salesDocHeader));
			if (salesDocItemDo != null) {
				getSession().delete(salesDocItemDo);
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
		return exportList(getSession().createQuery(
				"select item from SalesDocItemDo item where item.salesDocItemKey.salesDocHeader.salesOrderNum =: salesHeaderId",
				SalesDocItemDo.class).setParameter("salesHeaderId", salesHeaderId).getResultList());
	}

	@Override
	public List<String> getDSBySalesHeaderID(String salesHeaderId) {

		return getSession().createQuery(
				"select distinct ds.decisionSetId from SalesDocItemDo ds where ds.salesDocItemKey.salesDocHeader.salesOrderNum = :salesOrderNum and ds.decisionSetId is not null",
				String.class).setParameter("salesOrderNum", salesHeaderId).getResultList();
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
		Session session = sessionfactory.openSession();
		try {
			// Creating Sales Header Entity For inserting in Composite PK
			SalesDocHeaderDo salesDocHeader = salesDocHeaderRepo
					.importDto(salesDocHeaderRepo.getSalesDocHeaderById(salesHeaderId));

			SalesDocItemDo salesDocItemDo = getSession().byId(SalesDocItemDo.class)
					.load(new SalesDocItemPrimaryKeyDo(salesItemId, salesDocHeader));
			if (salesDocItemDo != null) {

				Transaction tx = session.beginTransaction();
				Query<String> query = session.createQuery("update SalesDocItemDo s set s.decisionSetId =: decisionSetId"
						+ " where s.salesDocItemKey.salesItemOrderNo =: salesItemOrderNo"
						+ " and s.salesDocItemKey.salesDocHeader.salesOrderNum =: salesOrderNum");

				query.setParameter("decisionSetId", decisionSet);
				query.setParameter("salesItemOrderNo", salesItemId);
				query.setParameter("salesOrderNum", salesHeaderId);

				query.executeUpdate();
				tx.commit();
				session.clear();
				session.close();

				return "Sales Document Item is updated with decision set : " + decisionSet;
			} else {

				session.close();
				return "Sales Document Item is not found on Item id : " + salesItemId + " and " + salesDocHeader;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	@Override
	public List<String> getItemListByDataSet(String dataSet) {
		if (dataSet != null) {

			return getSession().createQuery(
					"select item.salesDocItemKey.salesItemOrderNo from SalesDocItemDo item where item.decisionSetId = :decisionSetId",
					String.class).setParameter("decisionSetId", dataSet).getResultList();

		} else {
			return null;

		}

	}

	@Override
	public List<SalesDocItemDto> getSalesDocItemsByDecisionSetId(String decisionSetId) {
		return exportList(getSession()
				.createQuery("from SalesDocItemDo item where item.decisionSetId = :dsId", SalesDocItemDo.class)
				.setParameter("dsId", decisionSetId).getResultList());
	}

}

