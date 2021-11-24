package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incture.cherrywork.entities.BankNamesDo;
import com.incture.cherrywork.entities.BankNamesIdentity;

public interface BankRepo  extends JpaRepository<BankNamesDo,String>{

}
