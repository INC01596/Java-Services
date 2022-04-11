package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.CustomerContact;

@Repository
public interface ICustomerContactRepository extends JpaRepository<CustomerContact, String> {

}
