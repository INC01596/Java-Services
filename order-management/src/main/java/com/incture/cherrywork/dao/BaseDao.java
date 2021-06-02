package com.incture.cherrywork.dao;



import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.BaseDto;
import com.incture.cherrywork.dtos.CommentDto;
import com.incture.cherrywork.entities.BaseDo;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.util.ServicesUtil;


/**
 * @author Yash
 *
 * @param <E>
 * @param <D>
 */
@Repository
public abstract class BaseDao<E extends BaseDo, D extends BaseDto> {

	@Autowired
	private SessionFactory sessionFactory;

	@Lazy
	@Autowired
	private CommentDaoImpl commentRepo;

	public final int BATCH_SIZE = 2;

	// Use logger of log4j
	public final Logger logger = Logger.getLogger(getClass().getName());

	public abstract E importDto(D dto);

	public abstract D exportDto(E entity);

	public abstract List<E> importList(List<D> list);

	public abstract List<D> exportList(List<E> list);

	public Session getSession() {
		try {
			return sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			return sessionFactory.openSession();
		}
	}

	public void saveCommentList(List<CommentDto> commentDtoList, String refDocNum) throws ExecutionFault {
		if (commentDtoList != null && !commentDtoList.isEmpty()) {
			for (CommentDto commentDto : commentDtoList) {
				commentDto.setRefDocNum(refDocNum);
				commentRepo.saveOrUpdateComment(commentDto);
			}
		}
	}

	public void persist(E entity) {
		getSession().persist(entity);
	}

	public String save(E entity) {
		String primaryKey = (String) getSession().save(entity);
		return primaryKey;
	}

	public void update(E entity) {
		getSession().update(entity);

	}

	public void delete(E entity) {
		getSession().delete(entity);

	}

	@SuppressWarnings("unchecked")
	public E get(E entity) {
		E result = (E) getSession().load(entity.getClass(), (Serializable) entity.getPrimaryKey());
		return result;
	}

	/*
	 * Batch create or update
	 *
	 * @param entitys the entities
	 * 
	 * @throws ExecutionFault the entity execution fail exception
	 */
	public void batchSaveOrUpdate(Collection<E> entities) throws ExecutionFault {
		if (ServicesUtil.isEmpty(entities)) {
			return;
		}
		try {
			int count = 0;
			for (E entity : entities) {
				count++;

				this.getSession().saveOrUpdate(entity);

				if (count > 0 && count % 50 == 0) {
					getSession().flush();
					getSession().clear();
				}
			}

		} catch (Exception ex) {
			throw new ExecutionFault("Error occoured while creating the record" + ex);
		}
	}

	/**
	 * Generate where condition with in operator
	 * 
	 * @param conditions
	 * @return where cluase
	 */
	public String formWhereClause(Collection<String> conditions) {
		if (ServicesUtil.isEmpty(conditions)) {
			return null;
		}
		StringBuilder condition = new StringBuilder("(");
		for (String data : conditions) {
			condition.append("'" + data + "', ");
		}
		condition.deleteCharAt(condition.length() - 2);
		condition.append(")");
		return condition.toString();
	}

}

