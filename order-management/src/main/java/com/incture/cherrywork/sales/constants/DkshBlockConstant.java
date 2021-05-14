package com.incture.cherrywork.sales.constants;

public enum DkshBlockConstant {

	HCB("Header Credit Block"), HDB("Header Delivery Block"), HBB("Header Billing Block"), IDB(
			"Item Delivery Block"), IBB("Item Billing Block"), INP("Item number present");

	private String value;

	private DkshBlockConstant(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

