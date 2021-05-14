<<<<<<< HEAD
package com.incture.cherrywork.dto.workflow;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.incture.cherrywork.dtos.OdataBatchOnsubmitItem;

import lombok.Data;



@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderToItems {
	List<OdataBatchOnsubmitItem> results;


	public OrderToItems(List<OdataBatchOnsubmitItem> returnItemList) {
		super();
		this.results = returnItemList;
	}

	public OrderToItems() {
		super();
	}
}

=======
//package com.incture.cherrywork.dto.workflow;
//
//import java.util.List;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//
//import lombok.Data;
//
//
//
//@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class OrderToItems {
//	List<OdataBatchOnsubmitItem> results;
//
//
//	public OrderToItems(List<OdataBatchOnsubmitItem> returnItemList) {
//		super();
//		this.results = returnItemList;
//	}
//
//	public OrderToItems() {
//		super();
//	}
//}
//
>>>>>>> refs/remotes/origin/master
