package com.incture.cherrywork.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.RejectionReasonDto;
import com.incture.cherrywork.entities.RejectionReasonDo;



@Repository("RejectionReasonDao")
public class RejectionReasonDao extends BaseDao<RejectionReasonDo, RejectionReasonDto>
		implements RejectionReasonDaoLocal {

	@Override
	public RejectionReasonDo importDto(RejectionReasonDto dto) {

		RejectionReasonDo entity = new RejectionReasonDo();

		if (dto != null) {

			entity.setRejectReason(dto.getRejectReason());
		}

		return entity;
	}

	@Override
	public RejectionReasonDto exportDto(RejectionReasonDo entity) {

		RejectionReasonDto dto = new RejectionReasonDto();

		if (entity != null) {

			dto.setRejectReason(entity.getRejectReason());

		}

		return dto;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getListOfRejectionReason() {

		Query q = getSession().createQuery("select r.rejectReason from RejectionReasonDo r");

		List<String> listOfReason = q.list();

		return listOfReason;
	}

	@Override
	public void saveRejectionReason(RejectionReasonDto dto) {

		getSession().save(importDto(dto));

	}

	@Override
	public void deleteRejectionReason(RejectionReasonDto dto) {

		getSession().delete(importDto(dto));

	}

	@Override
	public List<RejectionReasonDo> importList(List<RejectionReasonDto> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RejectionReasonDto> exportList(List<RejectionReasonDo> list) {
		// TODO Auto-generated method stub
		return null;
	}

}
