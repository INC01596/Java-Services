package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.incture.cherrywork.dtos.CommentDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.services.CommentService;



@RestController
@RequestMapping("/comment")
public class CommentController {

	public CommentController() {
		//HelperClass.getLogger(this.getClass().getName()).info("inside comment controller");
	
		System.err.println("Inside comment Type Determination controller");
	}

	@Autowired
	private CommentService services;

	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity createComment(@RequestBody CommentDto commentDto) {
		//HelperClass.getLogger(this.getClass().getName()).info("Comment Created Successfully");
		return services.saveOrUpdateComment(commentDto);
	}

	@PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity updateComment(@RequestBody CommentDto commentDto) {
		System.err.println("Comment Updated Successfully");
		if (commentDto.getCommentId() != null) {
			return services.saveOrUpdateComment(commentDto);
		} else {
			return new ResponseEntity(commentDto, HttpStatus.OK, "Comment Id field is mandatory.",
					ResponseStatus.SUCCESS);
		}
	}

	@GetMapping("/list")
	public ResponseEntity listAllComment() {
		//HelperClass.getLogger(this.getClass().getName()).info("Inside Comments List");
		return services.listAllComments();
	}

	@DeleteMapping("/deleteById/{commentId}")
	public ResponseEntity deleteCommentById(@PathVariable("commentId") String commentId) {
		System.err.println("Inside Delete Comment by Id");
		return services.deleteCommentById(commentId);
	}

	@GetMapping("/findCommentById/{commentId}")
	public ResponseEntity findCommentById(@PathVariable("commentId") String commentId) {
		System.err.println("Inside Comment find by Id method");
		return services.getCommentById(commentId);
	}

	@GetMapping("/findByRefDocNum/{refDocNum}")
	public ResponseEntity findCommentListByRefDocNum(@PathVariable("refDocNum") String refDocNum) {
		System.err.println("Inside Comment find by Id method");
		return services.getCommentListByRefId(refDocNum);
	}

}
