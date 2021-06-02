package com.incture.cherrywork.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.Address;

@Repository
public interface IAddressRepository extends   JpaRepository<Address,String>{

	
	public List<Address> findByReturnReqNum(String returnReqNum);
}
