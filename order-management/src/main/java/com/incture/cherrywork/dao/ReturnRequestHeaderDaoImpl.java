package com.incture.cherrywork.dao;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.CommentDto;
import com.incture.cherrywork.dtos.ReturnRequestHeaderDto;
import com.incture.cherrywork.entities.ReturnRequestHeaderDo;
import com.incture.cherrywork.exceptions.ExecutionFault;



@Repository
@Component
public class ReturnRequestHeaderDaoImpl extends BaseDao<ReturnRequestHeaderDo, ReturnRequestHeaderDto>
		implements ReturnRequestHeaderDao {

	@Autowired
	private ReturnItemDaoImpl returnItemRepo;

	@Autowired
	private ExchangeHeaderDaoImpl exchangeHeaderRepo;

	@Autowired
	private CommentDaoImpl commentRepo;

	@Override
	public ReturnRequestHeaderDo importDto(ReturnRequestHeaderDto dto) {
		ReturnRequestHeaderDo returnRequestHeaderDo = null;
		if (dto != null) {
			returnRequestHeaderDo = new ReturnRequestHeaderDo();

			returnRequestHeaderDo.setOrderReason(dto.getOrderReason());
			returnRequestHeaderDo.setRefDocNum(dto.getRefDocNum());
			returnRequestHeaderDo.setReferenceDocType(dto.getReferenceDocType());
			returnRequestHeaderDo.setRequestCategory(dto.getRequestCategory());
			returnRequestHeaderDo.setRequestDate(dto.getRequestDate());
			returnRequestHeaderDo.setRequestedBy(dto.getRequestedBy());
			returnRequestHeaderDo.setRequestShortText(dto.getRequestShortText());
			returnRequestHeaderDo.setRequestStatusCode(dto.getRequestStatusCode());
			returnRequestHeaderDo.setRequestType(dto.getRequestType());
			returnRequestHeaderDo.setWorkflowInstance(dto.getWorkflowInstance());

			// Setting Primary Key
			if (dto.getReturnReqNum() != null) {
				returnRequestHeaderDo.setReturnReqNum(dto.getReturnReqNum());
			}

			// One to One btwn return request and exchange header
			if (dto.getExchangeHeaderDto() != null) {
				returnRequestHeaderDo.setExchangeHeader(exchangeHeaderRepo.importDto(dto.getExchangeHeaderDto()));
			}

			// One to Many btwn return request and return items
			if (dto.getReturnItemList() != null && !dto.getReturnItemList().isEmpty()) {
				returnRequestHeaderDo.setReturnItemList(returnItemRepo.importList(dto.getReturnItemList()));
			}

			// Setting Comment List from comment table
			saveCommentList(dto.getCommentDtoList(), dto.getReturnReqNum());
		}
		return returnRequestHeaderDo;
	}

	private void saveCommentList(List<CommentDto> commentDtoList, String returnReqNum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ReturnRequestHeaderDto exportDto(ReturnRequestHeaderDo entity) {
		ReturnRequestHeaderDto returnRequestHeaderDto = null;
		if (entity != null) {
			returnRequestHeaderDto = new ReturnRequestHeaderDto();

			returnRequestHeaderDto.setOrderReason(entity.getOrderReason());
			returnRequestHeaderDto.setRefDocNum(entity.getRefDocNum());
			returnRequestHeaderDto.setReferenceDocType(entity.getReferenceDocType());
			returnRequestHeaderDto.setRequestCategory(entity.getRequestCategory());
			returnRequestHeaderDto.setRequestDate(entity.getRequestDate());
			returnRequestHeaderDto.setRequestedBy(entity.getRequestedBy());
			returnRequestHeaderDto.setRequestShortText(entity.getRequestShortText());
			returnRequestHeaderDto.setRequestStatusCode(entity.getRequestStatusCode());
			returnRequestHeaderDto.setRequestType(entity.getRequestType());
			returnRequestHeaderDto.setWorkflowInstance(entity.getWorkflowInstance());

			// Setting Primary Key
			if (entity.getReturnReqNum() != null) {
				returnRequestHeaderDto.setReturnReqNum(entity.getReturnReqNum());
			}

			// One to One btwn return request and exchange header
			if (entity.getExchangeHeader() != null) {
				returnRequestHeaderDto.setExchangeHeaderDto(exchangeHeaderRepo.exportDto(entity.getExchangeHeader()));
			}

			// One to Many btwn return request and return items
			if (entity.getReturnItemList() != null && !entity.getReturnItemList().isEmpty()) {
				returnRequestHeaderDto.setReturnItemList(returnItemRepo.exportList(entity.getReturnItemList()));
			}

			// Setting Comment List from comment table
			List<CommentDto> commentDtoList = commentRepo.getCommentListByRefId(entity.getReturnReqNum());
			if (commentDtoList != null && !commentDtoList.isEmpty()) {
				returnRequestHeaderDto.setCommentDtoList(commentDtoList);
			}

		}
		return returnRequestHeaderDto;

	}

	@Override
	public List<ReturnRequestHeaderDo> importList(List<ReturnRequestHeaderDto> list) {
		if (list != null && !list.isEmpty()) {
			List<ReturnRequestHeaderDo> dtoList = new ArrayList<>();
			for (ReturnRequestHeaderDto entity : list) {

				dtoList.add(importDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public List<ReturnRequestHeaderDto> exportList(List<ReturnRequestHeaderDo> list) {
		if (list != null && !list.isEmpty()) {
			List<ReturnRequestHeaderDto> dtoList = new ArrayList<>();
			for (ReturnRequestHeaderDo entity : list) {

				dtoList.add(exportDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public String saveOrUpdateReturnReqHeader(ReturnRequestHeaderDto returnRequestHeaderDto) throws ExecutionFault {
		try {

			// ReturnRequestHeaderDo retReqHeaderDo =
			// importDto(returnRequestHeaderDto);
			// List<ReturnItemDo> itemListDo =
			// retReqHeaderDo.getReturnItemList();
			// if (itemListDo != null && !itemListDo.isEmpty()) {
			//
			// // Setting Foreign Key for Return Item
			// itemListDo.forEach(returnItem -> {
			// if (returnItem.getKey().getReturnReqItemid() != null) {
			// returnItem.setKey(
			// new
			// ReturnItemPrimaryKey(returnItem.getKey().getReturnReqItemid(),
			// retReqHeaderDo));
			// }
			// });
			// }
			//
			// if (retReqHeaderDo.getExchangeHeader() != null) {
			//
			// // Setting Exchange Header Foreign Key
			// ExchangeHeaderDo exchangeHeaderDo =
			// retReqHeaderDo.getExchangeHeader();
			// exchangeHeaderDo.setKey(new ExchangeHeaderPrimaryKey(
			// returnRequestHeaderDto.getExchangeHeaderDto().getExchangeReqNum(),
			// retReqHeaderDo));
			// retReqHeaderDo.setExchangeHeader(exchangeHeaderDo);
			//
			// // List of Exchange Items of Exchange Header
			// List<ExchangeItemDo> exchangeItemDoList =
			// exchangeHeaderDo.getExchangeItemList();
			//
			// // Setting Foreign Keys for Exchange Item
			// if (exchangeItemDoList != null && !exchangeItemDoList.isEmpty())
			// {
			// exchangeItemDoList.forEach(item -> {
			// if (item.getKey().getExchangeReqItemid() != null) {
			// item.setKey(
			// new ExchangeItemPrimaryKey(item.getKey().getExchangeReqItemid(),
			// exchangeHeaderDo));
			// }
			// });
			// }
			// }

			getSession().saveOrUpdate(importDto(returnRequestHeaderDto));
			getSession().flush();
			getSession().clear();
			return "Return Request Header is saved with : " + returnRequestHeaderDto.getReturnReqNum();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<ReturnRequestHeaderDto> listAllReturnReqHeaders() {
		return exportList(getSession().createQuery("from ReturnRequestHeaderDo", ReturnRequestHeaderDo.class).list());
	}

	@Override
	public ReturnRequestHeaderDto getReturnReqHeaderById(String returnReqNum) {
		return exportDto(getSession().get(ReturnRequestHeaderDo.class, returnReqNum));
	}

	@Override
	public String deleteReturnReqHeaderById(String returnReqNum) throws ExecutionFault {
		try {
			ReturnRequestHeaderDo returnRequestHeaderDo = getSession().byId(ReturnRequestHeaderDo.class)
					.load(returnReqNum);
			if (returnRequestHeaderDo != null) {
				getSession().delete(returnRequestHeaderDo);
				return "Return Request Header is completedly removed";
			} else {
				return "Return Request Header is not found on Key : " + returnReqNum;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

}
