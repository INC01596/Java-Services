package com.incture.cherrywork.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incture.cherrywork.entities.DomainTextDo;

public interface IDomainTextRepository extends JpaRepository<DomainTextDo, String> {

	List<DomainTextDo> findByDomainCodeIn(Set<String> domainCodes);
}
