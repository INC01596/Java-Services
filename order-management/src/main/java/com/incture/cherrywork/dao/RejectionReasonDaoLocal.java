package com.incture.cherrywork.dao;

import java.util.List;

import com.incture.cherrywork.dtos.RejectionReasonDto;



public interface RejectionReasonDaoLocal {

	

	void saveRejectionReason(RejectionReasonDto dto);

	void deleteRejectionReason(RejectionReasonDto dto);

}