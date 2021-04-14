package com.incture.cherrywork.sales.constants;

public enum EnPaymentChequeStatus {

	APPROVED, BLOCKED, PARTIALLY_BLOCKED;
	
	private EnPaymentChequeStatus(){
		
	}
	
	private int value;

	private EnPaymentChequeStatus(int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	private static EnPaymentChequeStatus[] allValues = values();
	
	public static EnPaymentChequeStatus fromOrdinal(int n) {
		return allValues[n];
	}
}