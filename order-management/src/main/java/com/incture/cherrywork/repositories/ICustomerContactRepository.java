package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.CustomerContact;
import com.incture.cherrywork.entities.CustomerContactPk;

@Repository
public interface ICustomerContactRepository extends JpaRepository<CustomerContact, String> {

	@Query(value = "delete from Customer_Contact where visit_id=?1", nativeQuery = true)
	public int deleteByVisitId(String visitId);
}
