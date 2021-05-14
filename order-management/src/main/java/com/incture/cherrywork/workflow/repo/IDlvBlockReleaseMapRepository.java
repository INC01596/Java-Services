package com.incture.cherrywork.workflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.workflow.DlvBlockReleaseMapDo;
import com.incture.cherrywork.entities.workflow.SalesDocItemDo;

@Repository
public interface IDlvBlockReleaseMapRepository extends JpaRepository<DlvBlockReleaseMapDo, String>,
QuerydslPredicateExecutor<DlvBlockReleaseMapDo>, IDlvBlockReleaseMapCustomRepository{

}
