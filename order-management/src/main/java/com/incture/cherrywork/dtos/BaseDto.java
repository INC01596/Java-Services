package com.incture.cherrywork.dtos;

import java.math.BigDecimal;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.repositories.EnOperation;
import com.incture.cherrywork.repositories.ServicesUtils;


public abstract class BaseDto {

	protected boolean isNullable = true;

	public abstract void validate(EnOperation enOperation) throws InvalidInputFault;
	
	public abstract Boolean getValidForUsage();

	protected void enforceMandatory(String field, Object value) throws InvalidInputFault {
		if (ServicesUtils.isEmpty(value)) {
			String message = "Field=" + field + " can't be empty";
			throw new InvalidInputFault(message);
		}
	}

	protected String checkStringSize(String field, String value, int allowedSize) throws InvalidInputFault {
		if (!ServicesUtils.isEmpty(value)) {// check size
			value = value.trim();
			int sizeOfInput = value.length();
			if (sizeOfInput > allowedSize) {
				String message = "Exceeding size for[" + field + "] allowed size is[" + allowedSize + "], input value["
						+ value + "] is of size[ " + sizeOfInput + "]";
				throw new InvalidInputFault(message, null);
			}
			return value;
		}
		return null;
	}

	protected BigDecimal checkBigDecimalSize(String field, BigDecimal value, int allowedPrecision, int allowedScale)
			throws InvalidInputFault {
		if (value != null) {
			StringBuffer sb = new StringBuffer("1");
			while (allowedPrecision-- > 0) {
				sb.append("0");
			}
			if (value.compareTo(new BigDecimal(sb.toString())) > -1) {
				String message = "Exceeding size for field[" + field + "] of allowed size[" + allowedPrecision
						+ "] and allowed decimal points[" + allowedScale + "], input value[" + value + "]";
				throw new InvalidInputFault(message, null);
			}
		}
		return value;
	}

}
