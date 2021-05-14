package com.incture.cherrywork.dao;

import java.util.List;

import com.incture.cherrywork.dtos.CommentDto;
import com.incture.cherrywork.exceptions.ExecutionFault;


public interface CommentDao {

	public String saveOrUpdateComment(CommentDto commentDto) throws ExecutionFault;

	public List<CommentDto> listAllComments();

	public CommentDto getCommentById(String commentId);

	public String deleteCommentById(String commentId) throws ExecutionFault;

	public List<CommentDto> getCommentListByRefId(String refId);
}