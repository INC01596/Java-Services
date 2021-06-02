package com.incture.cherrywork.dao;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.ScheduleLineDto;
import com.incture.cherrywork.entities.SalesDocHeaderDo;
import com.incture.cherrywork.entities.SalesDocItemDo;
import com.incture.cherrywork.entities.SalesDocItemPrimaryKeyDo;
import com.incture.cherrywork.entities.ScheduleLineDo;
import com.incture.cherrywork.entities.ScheduleLinePrimaryKeyDo;
import com.incture.cherrywork.exceptions.ExecutionFault;


@Component
@Repository
public class ScheduleLineDaoImpl extends BaseDao<ScheduleLineDo, ScheduleLineDto> implements ScheduleLineDao {

	@Override
	public ScheduleLineDo importDto(ScheduleLineDto dto) {
		ScheduleLineDo scheduleLineDo = null;
		if (dto != null) {
			scheduleLineDo = new ScheduleLineDo();

			scheduleLineDo.setBaseUnit(dto.getBaseUnit());
			scheduleLineDo.setConfQtySales(dto.getConfQtySales());
			scheduleLineDo.setReqQtyBase(dto.getReqQtyBase());
			scheduleLineDo.setReqQtySales(dto.getReqQtySales());
			scheduleLineDo.setSalesUnit(dto.getSalesUnit());
			scheduleLineDo.setSchlineDeliveryBlock(dto.getSchlineDeliveryBlock());
			scheduleLineDo.setRelfordelText(dto.getRelfordelText());

			// Setting Foreign Keys
			SalesDocItemDo salesDocItemDo = new SalesDocItemDo();
			SalesDocHeaderDo salesDocHeaderDo = new SalesDocHeaderDo();
			salesDocHeaderDo.setSalesOrderNum(dto.getSalesHeaderNo());
			salesDocItemDo
					.setSalesDocItemKey(new SalesDocItemPrimaryKeyDo(dto.getSalesItemOrderNo(), salesDocHeaderDo));

			scheduleLineDo.setScheduleLineKey(new ScheduleLinePrimaryKeyDo(dto.getScheduleLineNum(), salesDocItemDo));
		}
		return scheduleLineDo;
	}

	@Override
	public ScheduleLineDto exportDto(ScheduleLineDo entity) {
		ScheduleLineDto scheduleLineDto = null;
		if (entity != null) {
			scheduleLineDto = new ScheduleLineDto();

			scheduleLineDto.setBaseUnit(entity.getBaseUnit());
			scheduleLineDto.setConfQtySales(entity.getConfQtySales());
			scheduleLineDto.setReqQtyBase(entity.getReqQtyBase());
			scheduleLineDto.setReqQtySales(entity.getReqQtySales());
			scheduleLineDto.setSalesUnit(entity.getSalesUnit());
			scheduleLineDto.setSchlineDeliveryBlock(entity.getSchlineDeliveryBlock());
			scheduleLineDto.setRelfordelText(entity.getRelfordelText());

			// <Setting Foreign Keys>
			// Setting Schedule Line Number
			scheduleLineDto.setScheduleLineNum(entity.getScheduleLineKey().getScheduleLineNum());
			// Setting Sales Header Number
			scheduleLineDto.setSalesHeaderNo(entity.getScheduleLineKey().getSoItem().getSalesDocItemKey()
					.getSalesDocHeader().getSalesOrderNum());
			// Setting Sales Item Number
			scheduleLineDto.setSalesItemOrderNo(
					entity.getScheduleLineKey().getSoItem().getSalesDocItemKey().getSalesItemOrderNo());
		}
		return scheduleLineDto;
	}

	@Override
	public List<ScheduleLineDo> importList(List<ScheduleLineDto> list) {
		if (list != null && !list.isEmpty()) {
			List<ScheduleLineDo> dtoList = new ArrayList<>();
			for (ScheduleLineDto entity : list) {

				dtoList.add(importDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public List<ScheduleLineDto> exportList(List<ScheduleLineDo> list) {
		if (list != null && !list.isEmpty()) {
			List<ScheduleLineDto> dtoList = new ArrayList<>();
			for (ScheduleLineDo entity : list) {

				dtoList.add(exportDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public String saveOrUpdateScheduleLine(ScheduleLineDto scheduleLineDto) throws ExecutionFault {
		try {
			ScheduleLineDo scheduleLineDo = importDto(scheduleLineDto);
			getSession().saveOrUpdate(scheduleLineDo);
			getSession().flush();
			getSession().clear();
			return "Schedule Line is successfully created with =" + scheduleLineDo.getScheduleLineKey();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<ScheduleLineDto> listAllScheduleLines() {
		return exportList(getSession().createQuery("from ScheduleLineDo", ScheduleLineDo.class).list());
	}

	@Override
	public ScheduleLineDto getScheduleLineById(String scheduleLineId, String soHeadNum, String soItemNum) {
		SalesDocItemDo salesDocItemDo = new SalesDocItemDo();
		SalesDocHeaderDo salesDocHeaderDo = new SalesDocHeaderDo();
		salesDocHeaderDo.setSalesOrderNum(soHeadNum);
		salesDocItemDo.setSalesDocItemKey(new SalesDocItemPrimaryKeyDo(soItemNum, salesDocHeaderDo));
		return exportDto(
				getSession().get(ScheduleLineDo.class, new ScheduleLinePrimaryKeyDo(scheduleLineId, salesDocItemDo)));
	}

	@Override
	public String deleteScheduleLineById(String scheduleLineId, String soHeadNum, String soItemNum)
			throws ExecutionFault {
		try {
			SalesDocItemDo salesDocItemDo = new SalesDocItemDo();
			SalesDocHeaderDo salesDocHeaderDo = new SalesDocHeaderDo();
			salesDocHeaderDo.setSalesOrderNum(soHeadNum);
			salesDocItemDo.setSalesDocItemKey(new SalesDocItemPrimaryKeyDo(soItemNum, salesDocHeaderDo));
			ScheduleLineDo scheduleLineDo = getSession().byId(ScheduleLineDo.class)
					.load(new ScheduleLinePrimaryKeyDo(scheduleLineId, salesDocItemDo));
			if (scheduleLineDo != null) {
				getSession().delete(scheduleLineDo);
				return "Schedule Line is completedly removed";
			} else {
				return "Schedule Line is not found on id : " + scheduleLineId;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

}

