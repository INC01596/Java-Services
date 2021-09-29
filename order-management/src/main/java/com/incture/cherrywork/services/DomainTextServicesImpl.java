package com.incture.cherrywork.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.entities.DomainTextDo;
import com.incture.cherrywork.repositories.DomainTextRepository;
import com.incture.cherrywork.util.HelperClass;


@Service
public class DomainTextServicesImpl implements DomainTextServices {

	@Autowired
	private DomainTextRepository repo;

	@Override
	public ResponseEntity<?> findById(String id) {
		try {
			if (!HelperClass.checkString(id)) {
				Optional<DomainTextDo> model = repo.findById(id);
				if (model.isPresent()) {
					return new ResponseEntity<>(model.get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<>("AppErrorMsgConstants.DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT" + "Please provide domain code.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> saveOrUpdate(DomainTextDo model) {
		try {
			if (!HelperClass.checkString(model.getDomainCode())) {
				DomainTextDo entity = repo.saveAndFlush(model);
				return new ResponseEntity<>(entity, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT "+ "Please provide domain code.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> deleteById(String id) {
		try {
			if (!HelperClass.checkString(id)) {
				repo.deleteById(id);
				return new ResponseEntity<>("Domain code " + id + " is successfully deleted.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT" + "Please provide domain code.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG "+ e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> listAll() {
		try {
			List<DomainTextDo> list = repo.findAll();
			if (!list.isEmpty()) {
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
}

