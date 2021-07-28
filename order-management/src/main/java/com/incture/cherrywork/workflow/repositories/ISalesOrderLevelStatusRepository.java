package com.incture.cherrywork.workflow.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.entities.RequestMasterDo;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;

@Repository
public interface ISalesOrderLevelStatusRepository extends JpaRepository<SalesOrderLevelStatusDo, String> {

	@Query(value = "select * from so_level_status where decision_set_id =?1 and level =?2", nativeQuery = true)
	public SalesOrderLevelStatusDo findByDecisionSetAndLevel(String decisionSetId, String level);
	
	@Query(value="from SalesOrderLevelStatusDo l where l.decisionSetId =?1 and l.level =?2")
	public SalesOrderLevelStatusDo getSalesOrderLevelStatusByDecisionSetAndLevel(String dsId, String level);
	
	@Query(value="from SalesOrderLevelStatusDo l where l.decisionSetId =?1")
	List<SalesOrderLevelStatusDo> getSalesOrderLevelStatusByDecisionSet(String dsId);

}
