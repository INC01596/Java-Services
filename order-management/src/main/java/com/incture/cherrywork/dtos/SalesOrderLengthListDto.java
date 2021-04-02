package com.incture.cherrywork.dtos;

import java.math.BigDecimal;

public class SalesOrderLengthListDto {
	
	private BigDecimal length;

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "LengthListDto [length=" + length + "]";
	}


}
