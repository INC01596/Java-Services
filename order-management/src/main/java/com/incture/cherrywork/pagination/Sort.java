package com.incture.cherrywork.pagination;

public class Sort{
    public boolean sorted;
    public boolean unsorted;
    public boolean empty;
	public boolean isSorted() {
		return sorted;
	}
	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}
	public boolean isUnsorted() {
		return unsorted;
	}
	public void setUnsorted(boolean unsorted) {
		this.unsorted = unsorted;
	}
	public boolean isEmpty() {
		return empty;
	}
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
}
