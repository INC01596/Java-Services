package com.incture.cherrywork.repositories;



import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dao.BaseDao;
import com.incture.cherrywork.dtos.PaymentTermDo;


@Repository("PaymentTermDao")
public interface PaymentRepo extends JpaRepository<PaymentTermDo, String> {
	//private static final Logger logger = LoggerFactory.getLogger(PaymentTermDao.class);

	
	
}

