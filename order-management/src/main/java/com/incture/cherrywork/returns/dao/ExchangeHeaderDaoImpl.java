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
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//
//import com.incture.cherrywork.dao.BaseDao;
//import com.incture.cherrywork.dao.CommentDaoImpl;
//import com.incture.cherrywork.dtos.CommentDto;
//import com.incture.cherrywork.dtos.ExchangeHeaderDto;
//import com.incture.cherrywork.entities.ExchangeHeaderDo;
//import com.incture.cherrywork.entities.ExchangeHeaderPrimaryKey;
//import com.incture.cherrywork.entities.ReturnRequestHeaderDo;
//import com.incture.cherrywork.exceptions.ExecutionFault;
//
//
//
//@Repository
//@Component
//public class ExchangeHeaderDaoImpl extends BaseDao<ExchangeHeaderDo, ExchangeHeaderDto> implements ExchangeHeaderDao {
//
//	@Autowired
//	private ExchangeItemDaoImpl exchangeItemRepo;
//
//	@Autowired
//	private CommentDaoImpl commentRepo;
//
//	
//	public ExchangeHeaderDo importDto(ExchangeHeaderDto dto) {
//		ExchangeHeaderDo exchangeHeaderDo = null;
//		if (dto != null) {
//			exchangeHeaderDo = new ExchangeHeaderDo();
//
//			exchangeHeaderDo.setApprovalStatus(dto.getApprovalStatus());
//			exchangeHeaderDo.setBillingBlock(dto.getBillingBlock());
//			exchangeHeaderDo.setCreatedBy(dto.getCreatedBy());
//			exchangeHeaderDo.setCreditStatus(dto.getCreditStatus());
//			exchangeHeaderDo.setCustomerPo(dto.getCustomerPo());
//			exchangeHeaderDo.setCustomerPoType(dto.getCustomerPoType());
//			exchangeHeaderDo.setDeliveryBlock(dto.getDeliveryBlock());
//			exchangeHeaderDo.setDeliveryStatus(dto.getDeliveryStatus());
//			exchangeHeaderDo.setDistributionChannel(dto.getDistributionChannel());
//			exchangeHeaderDo.setDivision(dto.getDivision());
//			exchangeHeaderDo.setDocCurrency(dto.getDocCurrency());
//			exchangeHeaderDo.setOrderApprovalReason(dto.getOrderApprovalReason());
//			exchangeHeaderDo.setOrderCategory(dto.getOrderCategory());
//			exchangeHeaderDo.setOrderReason(dto.getOrderReason());
//			exchangeHeaderDo.setOrderType(dto.getOrderType());
//			exchangeHeaderDo.setOverallStatus(dto.getOverallStatus());
//			exchangeHeaderDo.setRejectionStatus(dto.getRejectionStatus());
//			exchangeHeaderDo.setSalesOrderDate(dto.getSalesOrderDate());
//			exchangeHeaderDo.setSalesOrg(dto.getSalesOrg());
//			exchangeHeaderDo.setSoldToParty(dto.getSoldToParty());
//			exchangeHeaderDo.setSdProcessStatus(dto.getSdProcessStatus());
//			exchangeHeaderDo.setTotalNetAmount(dto.getTotalNetAmount());
//
//			// Setting Primary Key
//			// Setting Foreign Key
//			ReturnRequestHeaderDo returnRequestHeaderDo = new ReturnRequestHeaderDo();
//			returnRequestHeaderDo.setReturnReqNum(dto.getReturnReqNum());
//			exchangeHeaderDo.setKey(new ExchangeHeaderPrimaryKey(dto.getExchangeReqNum(), returnRequestHeaderDo));
//
//			// One to Many btwn return request and return items
//			if (dto.getExchangeItemList() != null && !dto.getExchangeItemList().isEmpty()) {
//				exchangeHeaderDo.setExchangeItemList(exchangeItemRepo.importList(dto.getExchangeItemList()));
//			}
//
//			// Setting Comment List from comment table
//			saveCommentList(dto.getCommentDtoList(), dto.getExchangeReqNum());
//
//		}
//		return exchangeHeaderDo;
//	}
//
//	public void saveCommentList(List<CommentDto> commentDtoList, String exchangeReqNum) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	
//	public ExchangeHeaderDto exportDto(ExchangeHeaderDo entity) {
//		ExchangeHeaderDto exchangeHeaderDto = null;
//		if (entity != null) {
//			exchangeHeaderDto = new ExchangeHeaderDto();
//
//			exchangeHeaderDto.setApprovalStatus(entity.getApprovalStatus());
//			exchangeHeaderDto.setBillingBlock(entity.getBillingBlock());
//			exchangeHeaderDto.setCreatedBy(entity.getCreatedBy());
//			exchangeHeaderDto.setCreditStatus(entity.getCreditStatus());
//			exchangeHeaderDto.setCustomerPo(entity.getCustomerPo());
//			exchangeHeaderDto.setCustomerPoType(entity.getCustomerPoType());
//			exchangeHeaderDto.setDeliveryBlock(entity.getDeliveryBlock());
//			exchangeHeaderDto.setDeliveryStatus(entity.getDeliveryStatus());
//			exchangeHeaderDto.setDistributionChannel(entity.getDistributionChannel());
//			exchangeHeaderDto.setDivision(entity.getDivision());
//			exchangeHeaderDto.setDocCurrency(entity.getDocCurrency());
//			exchangeHeaderDto.setOrderApprovalReason(entity.getOrderApprovalReason());
//			exchangeHeaderDto.setOrderCategory(entity.getOrderCategory());
//			exchangeHeaderDto.setOrderReason(entity.getOrderReason());
//			exchangeHeaderDto.setOrderType(entity.getOrderType());
//			exchangeHeaderDto.setOverallStatus(entity.getOverallStatus());
//			exchangeHeaderDto.setRejectionStatus(entity.getRejectionStatus());
//			exchangeHeaderDto.setSalesOrderDate(entity.getSalesOrderDate());
//			exchangeHeaderDto.setSalesOrg(entity.getSalesOrg());
//			exchangeHeaderDto.setSoldToParty(entity.getSoldToParty());
//			exchangeHeaderDto.setSdProcessStatus(entity.getSdProcessStatus());
//			exchangeHeaderDto.setTotalNetAmount(entity.getTotalNetAmount());
//
//			// Setting Primary Key
//			// Setting Foreign Key
//			exchangeHeaderDto.setReturnReqNum(entity.getKey().getReturnRequestHeaderDo().getReturnReqNum());
//			exchangeHeaderDto.setExchangeReqNum(entity.getKey().getExchangeReqNum());
//
//			// One to Many btwn return request and return items
//			if (entity.getExchangeItemList() != null && !entity.getExchangeItemList().isEmpty()) {
//				exchangeHeaderDto.setExchangeItemList(exchangeItemRepo.exportList(entity.getExchangeItemList()));
//			}
//
//			// Setting Comment List from comment table
//			List<CommentDto> commentDtoList = commentRepo.getCommentListByRefId(entity.getKey().getExchangeReqNum());
//			if (commentDtoList != null && !commentDtoList.isEmpty()) {
//				exchangeHeaderDto.setCommentDtoList(commentDtoList);
//			}
//
//		}
//		return exchangeHeaderDto;
//
//	}
//
//	
//	public List<ExchangeHeaderDo> importList(List<ExchangeHeaderDto> list) {
//		if (list != null && !list.isEmpty()) {
//			List<ExchangeHeaderDo> dtoList = new ArrayList<>();
//			for (ExchangeHeaderDto entity : list) {
//
//				dtoList.add(importDto(entity));
//			}
//			return dtoList;
//		}
//		return Collections.emptyList();
//	}
//
//	
//	public List<ExchangeHeaderDto> exportList(List<ExchangeHeaderDo> list) {
//		if (list != null && !list.isEmpty()) {
//			List<ExchangeHeaderDto> dtoList = new ArrayList<>();
//			for (ExchangeHeaderDo entity : list) {
//
//				dtoList.add(exportDto(entity));
//			}
//			return dtoList;
//		}
//		return Collections.emptyList();
//	}
//
//	@Override
//	public String saveOrUpdateExchangeHeader(ExchangeHeaderDto exchangeHeaderDto) throws ExecutionFault {
//		try {
//			ExchangeHeaderDo exchangeHeaderDo = importDto(exchangeHeaderDto);
//			getSession().saveOrUpdate(exchangeHeaderDo);
//			getSession().flush();
//			getSession().clear();
//			return "Exchange Header is successfully created";
//		} catch (NoResultException | NullPointerException e) {
//			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	@Override
//	public List<ExchangeHeaderDto> listAllExchangeHeaders() {
//		return exportList(getSession().createQuery("from ExchangeHeaderDo", ExchangeHeaderDo.class).list());
//	}
//
//	@Override
//	public ExchangeHeaderDto getExchangeHeaderById(String exchangeReqNum, String returnReqNum) {
//		// Setting Foreign Key
//		ReturnRequestHeaderDo reqHeaderDo = new ReturnRequestHeaderDo();
//		reqHeaderDo.setReturnReqNum(returnReqNum);
//		return exportDto(
//				getSession().get(ExchangeHeaderDo.class, new ExchangeHeaderPrimaryKey(exchangeReqNum, reqHeaderDo)));
//	}
//
//	@Override
//	public String deleteExchangeHeaderById(String exchangeReqNum, String returnReqNum) throws ExecutionFault {
//		try {
//			// Setting Foreign Key
//			ReturnRequestHeaderDo reqHeaderDo = new ReturnRequestHeaderDo();
//			reqHeaderDo.setReturnReqNum(returnReqNum);
//
//			ExchangeHeaderDo exchangeHeaderDo = getSession().byId(ExchangeHeaderDo.class)
//					.load(new ExchangeHeaderPrimaryKey(exchangeReqNum, reqHeaderDo));
//			if (exchangeHeaderDo != null) {
//				getSession().delete(exchangeHeaderDo);
//				return "Exchange Header is completedly removed";
//			} else {
//				return "Exchange Header is not found on Key : " + returnReqNum + " and " + exchangeReqNum;
//			}
//		} catch (Exception e) {
//			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
//		}
//	}
//
//}
//
