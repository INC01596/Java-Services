package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.MaterialMaster;

@Repository
public interface IMaterialMasterNewRepo extends JpaRepository<MaterialMaster, String>{

}
