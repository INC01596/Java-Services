package com.incture.cherrywork.sales.constants;

public enum EnLookUp {

	PAYMENT, INCOTERMS, QUALITYTEST, DELIVERYTOLERANCE, DISTRIBUTIONCHANNEL;

	private EnLookUp() {
	}

	private int value;

	private EnLookUp(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	private static EnLookUp[] allValues = values();

	public static EnLookUp fromOrdinal(int n) {
		return allValues[n];
	}
}
