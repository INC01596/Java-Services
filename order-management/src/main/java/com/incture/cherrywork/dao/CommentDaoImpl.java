package com.incture.cherrywork.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.CommentDto;
import com.incture.cherrywork.entities.CommentDo;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.repositories.ICommentRepository;

@Service
@Transactional
public class CommentDaoImpl implements CommentDao {

	@Autowired
	private ICommentRepository commentRepository;
	
	@PersistenceContext
	private EntityManager entityManager;


	public CommentDo importDto(CommentDto dto) {
		CommentDo commentDo = null;
		if (dto != null) {
			commentDo = new CommentDo();

			commentDo.setComments(dto.getComments());
			commentDo.setUpdatedBy(dto.getUpdatedBy());
			commentDo.setRefDocNum(dto.getRefDocNum());

			// Setting Primary Key
			if (dto.getCommentId() != null) {
				commentDo.setCommentId(dto.getCommentId());
			}

			// Converting String from Date
			commentDo.setCommentTime(dto.getCommentTime());
		}
		return commentDo;
	}

	public CommentDto exportDto(CommentDo entity) {
		CommentDto commentDto = null;
		if (entity != null) {
			commentDto = new CommentDto();

			commentDto.setComments(entity.getComments());
			commentDto.setUpdatedBy(entity.getUpdatedBy());
			commentDto.setRefDocNum(entity.getRefDocNum());

			// Setting Primary Key
			if (entity.getCommentId() != null) {
				commentDto.setCommentId(entity.getCommentId());
			}

			// Converting Date from String
			commentDto.setCommentTime(entity.getCommentTime());
		}
		return commentDto;
	}

	public List<CommentDo> importList(List<CommentDto> list) {
		if (list != null && !list.isEmpty()) {
			List<CommentDo> dtoList = new ArrayList<>();
			for (CommentDto entity : list) {

				dtoList.add(importDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	public List<CommentDto> exportList(List<CommentDo> list) {
		if (list != null && !list.isEmpty()) {
			List<CommentDto> dtoList = new ArrayList<>();
			for (CommentDo entity : list) {

				dtoList.add(exportDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public String saveOrUpdateComment(CommentDto commentDto) throws ExecutionFault {
		try {
			CommentDo commentDo = importDto(commentDto);
			commentRepository.save(commentDo);
			return "Comment is successfully created with =" + commentDo.getCommentId();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<CommentDto> listAllComments() {
		return exportList(commentRepository.findAll());
	}

	@Override
	public CommentDto getCommentById(String commentId) {
		return exportDto(commentRepository.getOne(commentId));
	}

	@Override
	public String deleteCommentById(String commentId) throws ExecutionFault {
		try {
			CommentDo commentDo = commentRepository.getOne(commentId);
			if (commentDo != null) {
				commentRepository.delete(commentDo);
				return "Comment is completedly removed";
			} else {
				return "Comment is not found on Comment id : " + commentId;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	@Override
	public List<CommentDto> getCommentListByRefId(String refId) {
		return exportList(entityManager.createQuery("from CommentDo c where c.refDocNum = :refDocId", CommentDo.class)
				.setParameter("refDocId", refId).getResultList());
	}

}
