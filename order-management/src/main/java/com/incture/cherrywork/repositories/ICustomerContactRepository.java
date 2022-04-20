package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.CustomerContact;
import com.incture.cherrywork.entities.CustomerContactPk;

@Repository
public interface ICustomerContactRepository extends JpaRepository<CustomerContact, CustomerContactPk> {

}
