package com.incture.cherrywork.sales.constants;

public enum EnOrderActionStatus {

DRAFTED, OPEN, CREATED, OBDCREATED, PGICREATED, INVCREATED,CANCELLED,PENDING,PROCESSING,DELIVERY_IN_TRANSIT;
	
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



