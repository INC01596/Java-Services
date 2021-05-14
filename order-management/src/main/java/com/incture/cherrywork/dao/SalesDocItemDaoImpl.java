package com.incture.cherrywork.dao;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.SalesDocItemDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderInput;
import com.incture.cherrywork.entities.SalesDocHeaderDo;
import com.incture.cherrywork.entities.SalesDocItemDo;
import com.incture.cherrywork.entities.SalesDocItemPrimaryKeyDo;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.repositories.ObjectMapperUtils;




@Repository
@Component
public class SalesDocItemDaoImpl extends BaseDao<SalesDocItemDo, SalesDocItemDto> implements SalesDocItemDao {

	
	@Autowired
	private ScheduleLineDaoImpl scheduleLineRepo;

	@Lazy
	@Autowired
	private SalesDocHeaderDaoImpl salesDocHeaderRepo;

	@Autowired
	private SessionFactory sessionfactory;

	
	public SalesDocItemDo importDto(SalesDocItemDto fromDto) {
		SalesDocItemDo salesDocItemDo = ObjectMapperUtils.map(fromDto, SalesDocItemDo.class);
		
		return salesDocItemDo;
	}


	public SalesDocItemDto exportDto(SalesDocItemDo entity) {
		SalesDocItemDto salesDocItemDto =ObjectMapperUtils.map(entity, SalesDocItemDto.class); 
		
		return salesDocItemDto;

	}

	
	public List<SalesDocItemDo> importList(List<SalesDocItemDto> list) {
		if (list != null && !list.isEmpty()) {
			List<SalesDocItemDo> dtoList = new ArrayList<>();
			for (SalesDocItemDto entity : list) {

				dtoList.add(importDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	
	public List<SalesDocItemDto> exportList(List<SalesDocItemDo> list) {
		if (list != null && !list.isEmpty()) {
			List<SalesDocItemDto> dtoList = new ArrayList<>();
			for (SalesDocItemDo entity : list) {

				dtoList.add(exportDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public String saveOrUpdateSalesDocItem(SalesDocItemDto salesDocItemDto) throws ExecutionFault {
		try {
			SalesDocItemDo salesDocItemDo = importDto(salesDocItemDto);
			getSession().merge(salesDocItemDo);
			getSession().flush();
			getSession().clear();
			return "Sales Document Item is successfully created with =" + salesDocItemDo.getSalesDocItemKey();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	// added for DS update - Lock issue // Mohit is also using this method for
	// update from save/edit functionality
	@Override
	public String saveOrUpdateSalesDocItemForDS(SalesDocItemDto salesDocItemDto) throws ExecutionFault {

		try {
			Session session = sessionfactory.openSession();
			Transaction tx = session.beginTransaction();

			SalesDocItemDo salesDocItemDo = importDto(salesDocItemDto);
			session.saveOrUpdate(salesDocItemDo);

			tx.commit();
			session.close();
			return "Sales Document Item is successfully created with =" + salesDocItemDo.getSalesDocItemKey();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SalesDocItemDto> listAllSalesDocItems() {
		return exportList(getSession().createQuery("from SalesDocItemDo", SalesDocItemDo.class).list());
	}

	@Override
	public SalesDocItemDto getSalesDocItemById(String salesItemId, String salesHeaderId) {
		// Creating Sales Header Entity For inserting in Composite PK
		SalesDocHeaderDo salesDocHeader = new SalesDocHeaderDo();
		// Setting Sales Header Primary Key
		salesDocHeader.setSalesOrderNum(salesHeaderId);
		return exportDto(
				getSession().get(SalesDocItemDo.class, new SalesDocItemPrimaryKeyDo(salesItemId, salesDocHeader)));
	}

	@Override
	public String deleteSalesDocItemById(String salesItemId, String salesHeaderId) throws ExecutionFault {
		try {
			// Creating Sales Header Entity For inserting in Composite PK
			SalesDocHeaderDo salesDocHeader = new SalesDocHeaderDo();
			// Setting Sales Header Primary Key
			salesDocHeader.setSalesOrderNum(salesHeaderId);
			SalesDocItemDo salesDocItemDo = getSession().byId(SalesDocItemDo.class)
					.load(new SalesDocItemPrimaryKeyDo(salesItemId, salesDocHeader));
			if (salesDocItemDo != null) {
				getSession().delete(salesDocItemDo);
				return "Sales Document Item is completedly removed";
			} else {
				return "Sales Document Item is not found on Item id : " + salesItemId;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	@Override
	public List<SalesDocItemDto> listOfItemsInSalesOrder(String salesHeaderId) {
		return exportList(getSession().createQuery(
				"select item from SalesDocItemDo item where item.salesDocItemKey.salesDocHeader.salesOrderNum =: salesHeaderId",
				SalesDocItemDo.class).setParameter("salesHeaderId", salesHeaderId).getResultList());
	}

	@Override
	public List<String> getDSBySalesHeaderID(String salesHeaderId) {

		return getSession().createQuery(
				"select distinct ds.decisionSetId from SalesDocItemDo ds where ds.salesDocItemKey.salesDocHeader.salesOrderNum = :salesOrderNum and ds.decisionSetId is not null",
				String.class).setParameter("salesOrderNum", salesHeaderId).getResultList();
	}

	@Override
	public List<SalesDocItemDto> listOfItemsFromMultiItemId(SalesOrderHeaderInput soInput) {
		List<String> salesDocItemIdList = soInput.getSalesOrderItemIdList();
		List<SalesDocItemDto> salesDocItemDtoList = new ArrayList<>();
		for (String itemId : salesDocItemIdList) {
			SalesDocItemDto item = getSalesDocItemById(itemId, soInput.getSalesOrderHeaderId());
			if (item != null) {
				salesDocItemDtoList.add(item);
			}
		}
		return salesDocItemDtoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	synchronized public String updateSalesDocItemWithDecisionSet(String decisionSet, String salesItemId,
			String salesHeaderId) throws ExecutionFault {
		Session session = sessionfactory.openSession();
		try {
			// Creating Sales Header Entity For inserting in Composite PK
			SalesDocHeaderDo salesDocHeader = salesDocHeaderRepo
					.importDto(salesDocHeaderRepo.getSalesDocHeaderById(salesHeaderId));

			SalesDocItemDo salesDocItemDo = getSession().byId(SalesDocItemDo.class)
					.load(new SalesDocItemPrimaryKeyDo(salesItemId, salesDocHeader));
			if (salesDocItemDo != null) {

				Transaction tx = session.beginTransaction();
				Query<String> query = session.createQuery("update SalesDocItemDo s set s.decisionSetId =: decisionSetId"
						+ " where s.salesDocItemKey.salesItemOrderNo =: salesItemOrderNo"
						+ " and s.salesDocItemKey.salesDocHeader.salesOrderNum =: salesOrderNum");

				query.setParameter("decisionSetId", decisionSet);
				query.setParameter("salesItemOrderNo", salesItemId);
				query.setParameter("salesOrderNum", salesHeaderId);

				query.executeUpdate();
				tx.commit();
				session.clear();
				session.close();

				return "Sales Document Item is updated with decision set : " + decisionSet;
			} else {

				session.close();
				return "Sales Document Item is not found on Item id : " + salesItemId + " and " + salesDocHeader;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	@Override
	public List<String> getItemListByDataSet(String dataSet) {
		if (dataSet != null) {

			return getSession().createQuery(
					"select item.salesDocItemKey.salesItemOrderNo from SalesDocItemDo item where item.decisionSetId = :decisionSetId",
					String.class).setParameter("decisionSetId", dataSet).getResultList();

		} else {
			return null;

		}

	}

	@Override
	public List<SalesDocItemDto> getSalesDocItemsByDecisionSetId(String decisionSetId) {
		return exportList(getSession()
				.createQuery("from SalesDocItemDo item where item.decisionSetId = :dsId", SalesDocItemDo.class)
				.setParameter("dsId", decisionSetId).getResultList());
	}

	
}

