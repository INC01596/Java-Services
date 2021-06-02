//package com.incture.cherrywork.returns.dao;
//
//
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import javax.persistence.NoResultException;
//
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//
//import com.incture.cherrywork.dao.BaseDao;
//import com.incture.cherrywork.dtos.ExchangeItemDto;
//import com.incture.cherrywork.entities.ExchangeHeaderDo;
//import com.incture.cherrywork.entities.ExchangeHeaderPrimaryKey;
//import com.incture.cherrywork.entities.ExchangeItemDo;
//import com.incture.cherrywork.entities.ExchangeItemPrimaryKey;
//import com.incture.cherrywork.entities.ReturnRequestHeaderDo;
//import com.incture.cherrywork.exceptions.ExecutionFault;
//
//
//
//@Repository
//@Component
//public class ExchangeItemDaoImpl extends BaseDao<ExchangeItemDo, ExchangeItemDto> implements ExchangeItemDao {
//
//	@Override
//	public ExchangeItemDo importDto(ExchangeItemDto dto) {
//		ExchangeItemDo exchangeItemDo = null;
//		if (dto != null) {
//			exchangeItemDo = new ExchangeItemDo();
//
//			exchangeItemDo.setSapMaterialNum(dto.getSapMaterialNum());
//			exchangeItemDo.setBatchNum(dto.getBatchNum());
//			exchangeItemDo.setMaterialGroup(dto.getMaterialGroup());
//			exchangeItemDo.setShortText(dto.getShortText());
//			exchangeItemDo.setItemCategory(dto.getItemCategory());
//			exchangeItemDo.setItemType(dto.getItemType());
//			exchangeItemDo.setSalesUnit(dto.getSalesUnit());
//			exchangeItemDo.setItemBillingBlock(dto.getItemBillingBlock());
//			exchangeItemDo.setItemDlvBlock(dto.getItemDlvBlock());
//			exchangeItemDo.setPartialDlv(dto.getPartialDlv());
//			exchangeItemDo.setRefDocNum(dto.getRefDocNum());
//			exchangeItemDo.setRefDocItem(dto.getRefDocItem());
//			exchangeItemDo.setPlant(dto.getPlant());
//			exchangeItemDo.setStorageLoc(dto.getStorageLoc());
//			exchangeItemDo.setNetPrice(dto.getNetPrice());
//			exchangeItemDo.setDocCurrency(dto.getDocCurrency());
//			exchangeItemDo.setPricingUnit(dto.getPricingUnit());
//			exchangeItemDo.setCoudUnit(dto.getCoudUnit());
//			exchangeItemDo.setNetWorth(dto.getNetWorth());
//			exchangeItemDo.setOverallStatus(dto.getOverallStatus());
//			exchangeItemDo.setDeliveryStatus(dto.getDeliveryStatus());
//			exchangeItemDo.setReasonForRejection(dto.getReasonForRejection());
//			exchangeItemDo.setMaterialGroupFor(dto.getMaterialGroupFor());
//			exchangeItemDo.setBaseUnit(dto.getBaseUnit());
//			exchangeItemDo.setHigherLevelItem(dto.getHigherLevelItem());
//			exchangeItemDo.setTaxAmount(dto.getTaxAmount());
//			exchangeItemDo.setConvDen(dto.getConvDen());
//			exchangeItemDo.setConvNum(dto.getConvNum());
//			exchangeItemDo.setCuConfQtyBase(dto.getCuConfQtyBase());
//			exchangeItemDo.setCuConfQtySales(dto.getCuConfQtySales());
//			exchangeItemDo.setCuReqQtySales(dto.getCuReqQtySales());
//			exchangeItemDo.setOrderedQtySales(dto.getOrderedQtySales());
//			exchangeItemDo.setRefReturnReqItemid(dto.getRefReturnReqItemid());
//			exchangeItemDo.setRefReturnReqNum(dto.getRefReturnReqNum());
//			exchangeItemDo.setFirstDeliveryDate(dto.getFirstDeliveryDate());
//
//			// Setting Key
//			// Setting Foreign Key
//			ExchangeHeaderDo exchangeHeaderDo = new ExchangeHeaderDo();
//			ReturnRequestHeaderDo returnRequestHeaderDo = new ReturnRequestHeaderDo();
//			returnRequestHeaderDo.setReturnReqNum(dto.getReturnReqNum());
//			exchangeHeaderDo.setKey(new ExchangeHeaderPrimaryKey(dto.getExchangeReqNum(), returnRequestHeaderDo));
//			exchangeItemDo.setKey(new ExchangeItemPrimaryKey(dto.getExchangeReqItemId(), exchangeHeaderDo));
//		}
//		return exchangeItemDo;
//	}
//
//	@Override
//	public ExchangeItemDto exportDto(ExchangeItemDo entity) {
//		ExchangeItemDto exchangeItemDto = null;
//		if (entity != null) {
//			exchangeItemDto = new ExchangeItemDto();
//
//			exchangeItemDto.setSapMaterialNum(entity.getSapMaterialNum());
//			exchangeItemDto.setBatchNum(entity.getBatchNum());
//			exchangeItemDto.setMaterialGroup(entity.getMaterialGroup());
//			exchangeItemDto.setShortText(entity.getShortText());
//			exchangeItemDto.setItemCategory(entity.getItemCategory());
//			exchangeItemDto.setItemType(entity.getItemType());
//			exchangeItemDto.setSalesUnit(entity.getSalesUnit());
//			exchangeItemDto.setItemBillingBlock(entity.getItemBillingBlock());
//			exchangeItemDto.setItemDlvBlock(entity.getItemDlvBlock());
//			exchangeItemDto.setPartialDlv(entity.getPartialDlv());
//			exchangeItemDto.setRefDocNum(entity.getRefDocNum());
//			exchangeItemDto.setRefDocItem(entity.getRefDocItem());
//			exchangeItemDto.setPlant(entity.getPlant());
//			exchangeItemDto.setStorageLoc(entity.getStorageLoc());
//			exchangeItemDto.setNetPrice(entity.getNetPrice());
//			exchangeItemDto.setDocCurrency(entity.getDocCurrency());
//			exchangeItemDto.setPricingUnit(entity.getPricingUnit());
//			exchangeItemDto.setCoudUnit(entity.getCoudUnit());
//			exchangeItemDto.setNetWorth(entity.getNetWorth());
//			exchangeItemDto.setOverallStatus(entity.getOverallStatus());
//			exchangeItemDto.setDeliveryStatus(entity.getDeliveryStatus());
//			exchangeItemDto.setReasonForRejection(entity.getReasonForRejection());
//			exchangeItemDto.setMaterialGroupFor(entity.getMaterialGroupFor());
//			exchangeItemDto.setBaseUnit(entity.getBaseUnit());
//			exchangeItemDto.setHigherLevelItem(entity.getHigherLevelItem());
//			exchangeItemDto.setTaxAmount(entity.getTaxAmount());
//			exchangeItemDto.setConvDen(entity.getConvDen());
//			exchangeItemDto.setConvNum(entity.getConvNum());
//			exchangeItemDto.setCuConfQtyBase(entity.getCuConfQtyBase());
//			exchangeItemDto.setCuConfQtySales(entity.getCuConfQtySales());
//			exchangeItemDto.setCuReqQtySales(entity.getCuReqQtySales());
//			exchangeItemDto.setOrderedQtySales(entity.getOrderedQtySales());
//			exchangeItemDto.setRefReturnReqItemid(entity.getRefReturnReqItemid());
//			exchangeItemDto.setRefReturnReqNum(entity.getRefReturnReqNum());
//			exchangeItemDto.setFirstDeliveryDate(entity.getFirstDeliveryDate());
//
//			// Setting Key
//			// Setting Foreign Key
//			exchangeItemDto.setReturnReqNum(
//					entity.getKey().getExchangeHeaderDo().getKey().getReturnRequestHeaderDo().getReturnReqNum());
//			exchangeItemDto.setExchangeReqNum(entity.getKey().getExchangeHeaderDo().getKey().getExchangeReqNum());
//			exchangeItemDto.setExchangeReqItemId(entity.getKey().getExchangeReqItemid());
//		}
//		return exchangeItemDto;
//	}
//
//	@Override
//	public List<ExchangeItemDo> importList(List<ExchangeItemDto> list) {
//		if (list != null && !list.isEmpty()) {
//			List<ExchangeItemDo> dtoList = new ArrayList<>();
//			for (ExchangeItemDto entity : list) {
//
//				dtoList.add(importDto(entity));
//			}
//			return dtoList;
//		}
//		return Collections.emptyList();
//	}
//
//	@Override
//	public List<ExchangeItemDto> exportList(List<ExchangeItemDo> list) {
//		if (list != null && !list.isEmpty()) {
//			List<ExchangeItemDto> dtoList = new ArrayList<>();
//			for (ExchangeItemDo entity : list) {
//
//				dtoList.add(exportDto(entity));
//			}
//			return dtoList;
//		}
//		return Collections.emptyList();
//	}
//
//	@Override
//	public String saveOrUpdateExchangeItem(ExchangeItemDto exchangeItemDto) throws ExecutionFault {
//		try {
//			ExchangeItemDo exchangeItemDo = importDto(exchangeItemDto);
//			getSession().saveOrUpdate(exchangeItemDo);
//			getSession().flush();
//			getSession().clear();
//			return "Exchange Item is successfully created";
//		} catch (NoResultException | NullPointerException e) {
//			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
//		} catch (Exception e) {
//			throw e;
//		}
//
//	}
//
//	@Override
//	public List<ExchangeItemDto> listAllExchangeItems() {
//		return exportList(getSession().createQuery("from ExchangeItemDo", ExchangeItemDo.class).list());
//	}
//
//	@Override
//	public ExchangeItemDto getExchangeItemById(String returnReqNum, String exchangeReqItemid, String exchangeReqNum) {
//
//		// Creating Sales Header Entity For inserting in Composite PK
//		ExchangeHeaderDo exchangeHeaderDo = new ExchangeHeaderDo();
//
//		ReturnRequestHeaderDo retReqHeaderDo = new ReturnRequestHeaderDo();
//		retReqHeaderDo.setReturnReqNum(returnReqNum);
//
//		// Setting Sales Header Primary Key
//		exchangeHeaderDo.setKey(new ExchangeHeaderPrimaryKey(exchangeReqNum, retReqHeaderDo));
//
//		return exportDto(getSession().get(ExchangeItemDo.class,
//				new ExchangeItemPrimaryKey(exchangeReqItemid, exchangeHeaderDo)));
//	}
//
//	@Override
//	public String deleteExchangeItemById(String returnReqNum, String exchangeReqItemid, String exchangeReqNum)
//			throws ExecutionFault {
//		try {
//			// Creating Sales Header Entity For inserting in Composite PK
//			ExchangeHeaderDo exchangeHeaderDo = new ExchangeHeaderDo();
//
//			ReturnRequestHeaderDo retReqHeaderDo = new ReturnRequestHeaderDo();
//			retReqHeaderDo.setReturnReqNum(returnReqNum);
//
//			// Setting Sales Header Primary Key
//			exchangeHeaderDo.setKey(new ExchangeHeaderPrimaryKey(exchangeReqNum, retReqHeaderDo));
//
//			ExchangeItemDo exchangeItemDo = getSession().byId(ExchangeItemDo.class)
//					.load(new ExchangeItemPrimaryKey(exchangeReqItemid, exchangeHeaderDo));
//			;
//			if (exchangeItemDo != null) {
//				getSession().delete(exchangeItemDo);
//				return "Exchange Item is completedly removed";
//			} else {
//				return "Exchange Item is not found on Item id : " + returnReqNum + " " + exchangeReqNum + " "
//						+ exchangeReqItemid;
//			}
//		} catch (Exception e) {
//			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
//		}
//	}
//
//}
//
