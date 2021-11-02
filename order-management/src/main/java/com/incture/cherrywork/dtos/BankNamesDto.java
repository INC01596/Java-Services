package com.incture.cherrywork.dtos;


import com.incture.cherrywork.sales.constants.EnOperation;
import com.incture.cherrywork.util.DB_Operation;
import com.incture.cherrywork.util.InvalidInputFault;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BankNamesDto extends BaseDto {

	private String controllingArea;
	private String bankName;

	@Override
	public void validate(DB_Operation enOperation) throws InvalidInputFault {

	}

	@Override
	public Object getPrimaryKey() {

		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws com.incture.cherrywork.exceptions.InvalidInputFault {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean getValidForUsage() {
		// TODO Auto-generated method stub
		return null;
	}

}
