package com.incture.cherrywork.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.CustomerMaster;

@Repository
public interface ICustomerMasterRepo extends JpaRepository<CustomerMaster, String> {

	@Query("SELECT c FROM CustomerMaster c WHERE c.custCode = (:customerCode)") // 2.
																				// Spring
																				// JPA
																				// In
																				// cause
																				// using
																				// @Query
	List<CustomerMaster> filterCustomerByFullAccess(@Param("customerCode") String customerCode);
}
