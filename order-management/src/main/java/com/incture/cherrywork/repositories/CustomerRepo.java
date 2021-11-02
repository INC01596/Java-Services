package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.CustomerDo;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerDo,String> {

}
