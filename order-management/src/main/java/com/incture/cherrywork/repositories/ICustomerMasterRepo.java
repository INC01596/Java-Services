package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incture.cherrywork.entities.CustomerMasterEntity;

public interface ICustomerMasterRepo extends JpaRepository<CustomerMasterEntity, String>{

}
