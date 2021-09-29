package com.incture.cherrywork.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.entities.AttributeDetailsDo;
import com.incture.cherrywork.entities.DomainTextDo;
import com.incture.cherrywork.repositories.AttributeDetailsRepository;
import com.incture.cherrywork.repositories.DomainTextRepository;
import com.incture.cherrywork.util.HelperClass;



@Service
public class AttributeDetailsServicesImpl implements AttributeDetailsServices {

	@Autowired
	private AttributeDetailsRepository repo;

	@Autowired
	private DomainTextRepository domainRepo;

	@Override
	public ResponseEntity<?> findById(String id) {
		try {
			if (!HelperClass.checkString(id)) {
				Optional<AttributeDetailsDo> model = repo.findById(id);
				if (model.isPresent()) {
					return new ResponseEntity<>(model.get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<>("AppErrorMsgConstants.DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT" + "Please provide Attribute details id.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> saveOrUpdate(AttributeDetailsDo model) {
		try {
			if (!HelperClass.checkString(model.getDomainCode()) && !HelperClass.checkString(model.getAttributeId())
					&& !HelperClass.checkString(model.getAttributeDesc())) {
				Optional<DomainTextDo> domainTextDo = domainRepo.findById(model.getDomainCode());
				if (domainTextDo.isPresent()) {

					AttributeDetailsDo attributeDetailsDo = repo.findByAttributeIdAndDomainCodeAndAttributeDesc(
							model.getAttributeId(), model.getDomainCode(), model.getAttributeDesc());

					if (attributeDetailsDo == null) {
						AttributeDetailsDo entity = repo.saveAndFlush(model);
						return new ResponseEntity<>(entity, HttpStatus.CREATED);
					} else {
						return new ResponseEntity<>(
								"Attribute Id, Attribute Desc and Domain code already exists. Please change values.",
								HttpStatus.CONFLICT);
					}
				} else {
					return new ResponseEntity<>("Domain Code doesn't exist. Please save the Domain Code first.",
							HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<>(
						"AppErrorMsgConstants.INVALID_INPUT"
								+ "Please provide Domain Code, Attribute Desc and Attribute id.",
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
				return new ResponseEntity<>("Attribute details " + id + " is successfully deleted.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT" + "Please provide Attribute details id.",
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
			List<AttributeDetailsDo> list = repo.findAll();
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

	@Override
	public ResponseEntity<?> findByDomainCode(String domainCode) {
		try {
			if (!HelperClass.checkString(domainCode)) {
				List<AttributeDetailsDo> attributeDetailsDoList = repo
						.findByDomainCodeOrderByAttributeIdAsc(domainCode);
				if (!attributeDetailsDoList.isEmpty()) {
					return new ResponseEntity<>(attributeDetailsDoList, HttpStatus.OK);
				} else {
					return new ResponseEntity<>("AppErrorMsgConstants.DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT "+ "Please provide domain code.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
