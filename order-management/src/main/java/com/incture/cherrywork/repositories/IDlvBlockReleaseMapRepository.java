package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.DlvBlockReleaseMapDo;

@Repository
public interface IDlvBlockReleaseMapRepository extends JpaRepository<DlvBlockReleaseMapDo, String>{

}
