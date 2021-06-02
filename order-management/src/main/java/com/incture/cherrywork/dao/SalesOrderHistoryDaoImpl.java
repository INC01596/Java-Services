package com.incture.cherrywork.dao;




import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.SalesOrderHistoryDto;
import com.incture.cherrywork.entities.SalesOrderHistoryDo;


@Repository
@Component
public class SalesOrderHistoryDaoImpl extends BaseDao<SalesOrderHistoryDo, SalesOrderHistoryDto>
		implements SalesOrderHistoryDao {

	@Override
	public SalesOrderHistoryDo importDto(SalesOrderHistoryDto fromDto) {
		SalesOrderHistoryDo salesOrderHistoryDo = null;

		if (fromDto != null) {

			salesOrderHistoryDo = new SalesOrderHistoryDo();

			// Setting Primary Key
			if (fromDto.getSerialId() != null) {
				salesOrderHistoryDo.setSerialId(fromDto.getSerialId());
			}
			salesOrderHistoryDo.setSalesDocNum(fromDto.getSalesDocNum());
			salesOrderHistoryDo.setSalesItemNum(fromDto.getSalesItemNum());
			salesOrderHistoryDo.setVersion(fromDto.getVersion());
			salesOrderHistoryDo.setBatchNum(fromDto.getBatchNum());
			salesOrderHistoryDo.setOrderedQtySales(fromDto.getOrderedQtySales());
			salesOrderHistoryDo.setSalesUnit(fromDto.getSalesUnit());
			salesOrderHistoryDo.setItemBillingBlock(fromDto.getItemBillingBlock());
			salesOrderHistoryDo.setItemDlvBlock(fromDto.getItemDlvBlock());
			salesOrderHistoryDo.setPlant(fromDto.getPlant());
			salesOrderHistoryDo.setStorageLoc(fromDto.getStorageLoc());
			salesOrderHistoryDo.setNetPrice(fromDto.getNetPrice());
			salesOrderHistoryDo.setNetWorth(fromDto.getNetWorth());
			salesOrderHistoryDo.setReasonOfRejection(fromDto.getReasonOfRejection());
			salesOrderHistoryDo.setUpdatedOn(fromDto.getUpdatedOn());
			salesOrderHistoryDo.setUpdatedBy(fromDto.getUpdatedBy());

		}
		return salesOrderHistoryDo;
	}

	@Override
	public SalesOrderHistoryDto exportDto(SalesOrderHistoryDo entity) {
		SalesOrderHistoryDto salesOrderHistoryDto = null;
		if (entity != null) {

			salesOrderHistoryDto = new SalesOrderHistoryDto();

			// Setting Primary Key
			if (entity.getSerialId() != null) {
				salesOrderHistoryDto.setSerialId(entity.getSerialId());
			}
			salesOrderHistoryDto.setSalesDocNum(entity.getSalesDocNum());
			salesOrderHistoryDto.setSalesItemNum(entity.getSalesItemNum());
			salesOrderHistoryDto.setVersion(entity.getVersion());
			salesOrderHistoryDto.setBatchNum(entity.getBatchNum());
			salesOrderHistoryDto.setOrderedQtySales(entity.getOrderedQtySales());
			salesOrderHistoryDto.setSalesUnit(entity.getSalesUnit());
			salesOrderHistoryDto.setItemBillingBlock(entity.getItemBillingBlock());
			salesOrderHistoryDto.setItemDlvBlock(entity.getItemDlvBlock());
			salesOrderHistoryDto.setPlant(entity.getPlant());
			salesOrderHistoryDto.setStorageLoc(entity.getStorageLoc());
			salesOrderHistoryDto.setNetPrice(entity.getNetPrice());
			salesOrderHistoryDto.setNetWorth(entity.getNetWorth());
			salesOrderHistoryDto.setReasonOfRejection(entity.getReasonOfRejection());
			salesOrderHistoryDto.setUpdatedOn(entity.getUpdatedOn());
			salesOrderHistoryDto.setUpdatedBy(entity.getUpdatedBy());

		}
		return salesOrderHistoryDto;
	}

	@Override
	public List<SalesOrderHistoryDo> importList(List<SalesOrderHistoryDto> list) {

		if (list != null && !list.isEmpty()) {
			List<SalesOrderHistoryDo> dtoList = new ArrayList<>();
			for (SalesOrderHistoryDto entity : list) {

				dtoList.add(importDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public List<SalesOrderHistoryDto> exportList(List<SalesOrderHistoryDo> list) {

		if (list != null && !list.isEmpty()) {
			List<SalesOrderHistoryDto> dtoList = new ArrayList<>();
			for (SalesOrderHistoryDo entity : list) {

				dtoList.add(exportDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();

	}

	@Override
	public String saveSalesOrder(SalesOrderHistoryDto salesOrderHistoryDto) {
		try {

			String id = (String) getSession().save(importDto(salesOrderHistoryDto));
			getSession().flush();
			getSession().clear();
			return "Sales Order is successfully created with ID = " + id;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public SalesOrderHistoryDto getSalesOrderByItemNum(String salesOrderHeaderNum, String salesOrderItemNum) {

		try {

			SalesOrderHistoryDo productDo = getSession()
					.createQuery(
							"from SalesOrderHistoryDo p where p.version = "
									+ "(select max(pp.version) from SalesOrderHistoryDo pp where pp.salesDocNum = :soHeaderNum and pp.salesItemNum = :soItemNum)",
							SalesOrderHistoryDo.class)
					.setParameter("soHeaderNum", salesOrderHeaderNum).setParameter("soItemNum", salesOrderItemNum)
					.getSingleResult();
			getSession().flush();
			getSession().clear();
			if (productDo != null) {
				return exportDto(productDo);
			} else {
				return null;
			}

		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public List<SalesOrderHistoryDto> listAllSalesOrderHistoryData() {

		return exportList(getSession().createQuery("from SalesOrderHistoryDo", SalesOrderHistoryDo.class).list());
	}

	@Override
	public List<SalesOrderHistoryDto> getSalesOrderHistoryByItemNum(String salesOrderHeaderNum,
			String salesOrderItemNum) {

		return exportList(
				getSession()
						.createQuery(
								"from SalesOrderHistoryDo salesOrder where salesOrder.salesDocNum = :soHeaderNum and "
										+ "salesOrder.salesItemNum = :soItemNum ORDER BY version",
								SalesOrderHistoryDo.class)
						.setParameter("soHeaderNum", salesOrderHeaderNum).setParameter("soItemNum", salesOrderItemNum)
						.list());

	}

}
