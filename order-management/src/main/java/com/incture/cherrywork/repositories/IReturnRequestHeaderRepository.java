package com.incture.cherrywork.repositories;





import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.incture.cherrywork.entities.ReturnRequestHeader;

@Repository
public interface IReturnRequestHeaderRepository extends JpaRepository<ReturnRequestHeader, String>
{

     @Query("from ReturnRequestHeader r where r.returnReqNum=:reqNo")
     List<ReturnRequestHeader>findAll1(@Param("reqNo") String reqNo);
     
     @Query("from ReturnRequestHeader r where r.division=:division")
     List<ReturnRequestHeader>findAll(@Param("division") String division);
     
     @Query("from ReturnRequestHeader r where r.division=:division and r.distributionChannel=:channel")
     List<ReturnRequestHeader>findAll(@Param("division")String division ,@Param("channel") String channel);
     
     
     
}
	
