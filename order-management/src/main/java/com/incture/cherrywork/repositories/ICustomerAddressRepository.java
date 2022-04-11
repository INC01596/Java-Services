package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.CustomerAddress;

@Repository
public interface ICustomerAddressRepository extends JpaRepository<CustomerAddress, String> {

}
