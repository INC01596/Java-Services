package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.BankNamesDo;

@Repository
public interface IBankRepo extends JpaRepository<BankNamesDo, String> {

}
