package com.incture.cherrywork.repositories;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.incture.cherrywork.entities.ApplicationVariablesMasterDo;
import com.incture.cherrywork.entities.ApplicationVariablesMasterDoPK;


public interface ApplicationVariablesMasterRepo extends JpaRepository<ApplicationVariablesMasterDo, ApplicationVariablesMasterDoPK>{

	@Query("select DISTINCT p.id.variable from ApplicationVariablesMasterDo p where p.id.applicationName=:application and (:entity is null or p.id.entity"
			+ "=:entity) and (:process is null or p.id.process = :process)")
	public List<String> getVariablesByApplicationName(@Param("application") String application ,
		@Param("entity") String entity,@Param("process") String process);

}
