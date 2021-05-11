package com.incture.cherrywork.sales.constants;

public enum EnOverallDocumentStatus {

	NOT_YET_PROCESSED, PARTIALLY_PROCESSED, COMPLETELY_PROCESSED;

	private EnOverallDocumentStatus() {
	}

	private int value;

	private EnOverallDocumentStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	private static EnOverallDocumentStatus[] allValues = values();

	public static EnOverallDocumentStatus fromOrdinal(int n) {
		return allValues[n];
	}
}