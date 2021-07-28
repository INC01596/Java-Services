package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.RequestMasterDo;
import com.incture.cherrywork.entities.SalesDocItemDo;
import com.incture.cherrywork.entities.SalesDocItemPrimaryKeyDo;
@Repository
public interface ISalesDocItemRepository extends JpaRepository<SalesDocItemDo, SalesDocItemPrimaryKeyDo>{

}
