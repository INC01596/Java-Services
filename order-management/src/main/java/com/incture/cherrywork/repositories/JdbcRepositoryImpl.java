package com.incture.cherrywork.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRepositoryImpl implements JdbcRepository {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Long getVisitSequence() {
		return jdbcTemplate.queryForObject("SELECT visitSequence.NEXTVAL FROM DUMMY", Long.class);
	}

}
