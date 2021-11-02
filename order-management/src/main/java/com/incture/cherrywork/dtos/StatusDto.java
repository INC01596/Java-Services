package com.incture.cherrywork.dtos;



import java.util.Date;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;
import com.incture.cherrywork.util.DB_Operation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class StatusDto extends BaseDto {

	private Long statusId;

	private String updatedBy;

	private Date updateDate;

	private String approverComments;

	private String pendingWith;

	private String status;

	private TransactionDto transaction;

	private String rejectionReason;
	
	private String approverName;

	
	@Override
	public Object getPrimaryKey() {
		return statusId;
	}


	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Boolean getValidForUsage() {
		// TODO Auto-generated method stub
		return null;
	}

}
