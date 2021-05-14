package com.incture.cherrywork.services;


import com.incture.cherrywork.dtos.CommentDto;
import com.incture.cherrywork.dtos.ResponseEntity;


public interface CommentService {

	public ResponseEntity saveOrUpdateComment(CommentDto commentDto);

	public ResponseEntity listAllComments();

	public ResponseEntity deleteCommentById(String commentId);

	public ResponseEntity getCommentById(String commentId);

	public ResponseEntity getCommentListByRefId(String refId);

}
