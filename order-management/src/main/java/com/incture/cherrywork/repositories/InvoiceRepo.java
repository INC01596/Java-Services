package com.incture.cherrywork.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.incture.cherrywork.entities.InvoiceDo;




@Repository("InvoiceDao")
public interface InvoiceRepo extends JpaRepository<InvoiceDo,String>{
    
	
	
}
