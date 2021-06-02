//package com.incture.cherrywork.returns.dao;
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
//import com.incture.cherrywork.dtos.ReturnItemDto;
//import com.incture.cherrywork.entities.ReturnItemDo;
//import com.incture.cherrywork.entities.ReturnItemPrimaryKey;
//import com.incture.cherrywork.entities.ReturnRequestHeaderDo;
//import com.incture.cherrywork.exceptions.ExecutionFault;
//
//
//
//
//@Repository
//@Component
//public class ReturnItemDaoImpl extends BaseDao<ReturnItemDo, ReturnItemDto> implements ReturnItemDao {
//
//	@Override
//	public ReturnItemDo importDto(ReturnItemDto dto) {
//		ReturnItemDo returnItemDo = null;
//		if (dto != null) {
//			returnItemDo = new ReturnItemDo();
//
//			returnItemDo.setBatch(dto.getBatch());
//			returnItemDo.setExpiryDate(dto.getExpiryDate());
//			returnItemDo.setHasExchange(dto.getHasExchange());
//			returnItemDo.setHigherLevel(dto.getHigherLevel());
//			returnItemDo.setMaterial(dto.getMaterial());
//			returnItemDo.setNetPrice(dto.getNetPrice());
//			returnItemDo.setNetWorth(dto.getNetWorth());
//			returnItemDo.setQuantitySales(dto.getQuantitySales());
//			returnItemDo.setReasonOfReturn(dto.getReasonOfReturn());
//			returnItemDo.setRefDocItem(dto.getRefDocItem());
//			returnItemDo.setRefDocNum(dto.getRefDocNum());
//			returnItemDo.setSalesUnit(dto.getSalesUnit());
//			returnItemDo.setShortText(dto.getShortText());
//			returnItemDo.setStorageLocation(dto.getStorageLocation());
//
//			ReturnRequestHeaderDo returnRequestHeaderDo = null;
//
//			// Setting Foreign Key
//			if (dto.getReturnReqNum() != null) {
//				returnRequestHeaderDo = new ReturnRequestHeaderDo();
//				returnRequestHeaderDo.setReturnReqNum(dto.getReturnReqNum());
//			}
//
//			// Setting Primary Key
//			if (dto.getReturnReqItemId() != null && returnRequestHeaderDo != null) {
//				returnItemDo.setKey(new ReturnItemPrimaryKey(dto.getReturnReqItemId(), returnRequestHeaderDo));
//			}
//
//		}
//		return returnItemDo;
//	}
//
//	@Override
//	public ReturnItemDto exportDto(ReturnItemDo entity) {
//		ReturnItemDto returnItemDto = null;
//		if (entity != null) {
//			returnItemDto = new ReturnItemDto();
//
//			returnItemDto.setBatch(entity.getBatch());
//			returnItemDto.setExpiryDate(entity.getExpiryDate());
//			returnItemDto.setHasExchange(entity.getHasExchange());
//			returnItemDto.setHigherLevel(entity.getHigherLevel());
//			returnItemDto.setMaterial(entity.getMaterial());
//			returnItemDto.setNetPrice(entity.getNetPrice());
//			returnItemDto.setNetWorth(entity.getNetWorth());
//			returnItemDto.setQuantitySales(entity.getQuantitySales());
//			returnItemDto.setReasonOfReturn(entity.getReasonOfReturn());
//			returnItemDto.setRefDocItem(entity.getRefDocItem());
//			returnItemDto.setRefDocNum(entity.getRefDocNum());
//			returnItemDto.setSalesUnit(entity.getSalesUnit());
//			returnItemDto.setShortText(entity.getShortText());
//			returnItemDto.setStorageLocation(entity.getStorageLocation());
//
//			// Setting Foreign Key
//			returnItemDto.setReturnReqNum(entity.getKey().getReturnReqHeaderDo().getReturnReqNum());
//
//			// Setting PK
//			if (entity.getKey().getReturnReqItemid() != null) {
//				returnItemDto.setReturnReqItemId(entity.getKey().getReturnReqItemid());
//			}
//		}
//		return returnItemDto;
//
//	}
//
//	@Override
//	public List<ReturnItemDo> importList(List<ReturnItemDto> list) {
//		if (list != null && !list.isEmpty()) {
//			List<ReturnItemDo> dtoList = new ArrayList<>();
//			for (ReturnItemDto entity : list) {
//
//				dtoList.add(importDto(entity));
//			}
//			return dtoList;
//		}
//		return Collections.emptyList();
//	}
//
//	@Override
//	public List<ReturnItemDto> exportList(List<ReturnItemDo> list) {
//		if (list != null && !list.isEmpty()) {
//			List<ReturnItemDto> dtoList = new ArrayList<>();
//			for (ReturnItemDo entity : list) {
//
//				dtoList.add(exportDto(entity));
//			}
//			return dtoList;
//		}
//		return Collections.emptyList();
//	}
//
//	@Override
//	public String saveOrUpdateReturnItem(ReturnItemDto returnItemDto) throws ExecutionFault {
//		try {
//			ReturnItemDo returnItemDo = importDto(returnItemDto);
//			getSession().saveOrUpdate(returnItemDo);
//			getSession().flush();
//			getSession().clear();
//			return "Return Item is successfully created";
//		} catch (NoResultException | NullPointerException e) {
//			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	@Override
//	public List<ReturnItemDto> listAllReturnItems() {
//		return exportList(getSession().createQuery("from ReturnItemDo", ReturnItemDo.class).list());
//	}
//
//	@Override
//	public ReturnItemDto getReturnItemById(String returnReqNum, String returnReqItemid) {
//		ReturnRequestHeaderDo reqHeaderDo = new ReturnRequestHeaderDo();
//		reqHeaderDo.setReturnReqNum(returnReqNum);
//		return exportDto(getSession().get(ReturnItemDo.class, new ReturnItemPrimaryKey(returnReqItemid, reqHeaderDo)));
//	}
//
//	@Override
//	public String deleteReturnItemById(String returnReqNum, String returnReqItemid) throws ExecutionFault {
//		try {
//
//			ReturnRequestHeaderDo reqHeaderDo = new ReturnRequestHeaderDo();
//			reqHeaderDo.setReturnReqNum(returnReqNum);
//			ReturnItemDo returnItemDo = getSession().byId(ReturnItemDo.class)
//					.load(new ReturnItemPrimaryKey(returnReqItemid, reqHeaderDo));
//			if (returnItemDo != null) {
//				getSession().delete(returnItemDo);
//				return "Return Item is completedly removed";
//			} else {
//				return "Return Item is not found on Key : " + returnReqNum + " and " + returnReqItemid;
//			}
//		} catch (Exception e) {
//			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
//		}
//	}
//
//}
