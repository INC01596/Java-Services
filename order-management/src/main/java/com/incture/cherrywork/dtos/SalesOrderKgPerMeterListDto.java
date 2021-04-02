package com.incture.cherrywork.dtos;

import java.math.BigDecimal;

public class SalesOrderKgPerMeterListDto {
	
	private BigDecimal kgPerMeter;

	public BigDecimal getKgPerMeter() {
		return kgPerMeter;
	}

	public void setKgPerMeter(BigDecimal kgPerMeter) {
		this.kgPerMeter = kgPerMeter;
	}

	@Override
	public String toString() {
		return "KgPerMeterListDto [kgPerMeter=" + kgPerMeter + "]";
	}


}
