package com.incture.cherrywork.sales.constants;

public enum EnOrderActionStatus {

DRAFTED, OPEN, CREATED, OBDCREATED, PGICREATED, INVCREATED,CANCELLED;
	
	EnOrderActionStatus(){
		
	}
	private int value;
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	private static EnOrderActionStatus[] allValues = values();

	public static EnOrderActionStatus fromOrdinal(int n) {
		return allValues[n];
	}
}

