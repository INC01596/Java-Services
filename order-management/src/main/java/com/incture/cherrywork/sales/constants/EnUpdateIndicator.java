package com.incture.cherrywork.sales.constants;


public enum EnUpdateIndicator {
	INSERT, UPDATE, DELETE;

	private EnUpdateIndicator() {
	}

	private int value;

	private EnUpdateIndicator(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	private static EnUpdateIndicator[] allValues = values();

	public static EnUpdateIndicator fromOrdinal(int n) {
		return allValues[n];
	}
}
	