package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.incture.cherrywork.entities.SalesOrderHeader;

public interface ISalesOrderHeaderRepository extends JpaRepository<SalesOrderHeader, String>{
    }
