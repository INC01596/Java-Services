package com.incture.cherrywork.repositories;



import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.DomainTextDo;



@Repository
public interface DomainTextRepository extends JpaRepository<DomainTextDo, String> {

	List<DomainTextDo> findByDomainCodeIn(Set<String> domainCodes);

}

