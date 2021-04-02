package com.incture.cherrywork.repositories;

public enum SalesOrderEnUpdateIndicator {
	
	INSERT, UPDATE, DELETE;

	private SalesOrderEnUpdateIndicator() {
	}

	private int value;

	private SalesOrderEnUpdateIndicator(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	private static SalesOrderEnUpdateIndicator[] allValues = values();

	public static SalesOrderEnUpdateIndicator fromOrdinal(int n) {
		return allValues[n];
	}


}
