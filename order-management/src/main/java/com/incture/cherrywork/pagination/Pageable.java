package com.incture.cherrywork.pagination;

public class Pageable{
	 public Sort sort;
    public Pageable() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Pageable(Sort sort, int pageNumber, int pageSize, int offset, boolean unpaged, boolean paged) {
		super();
		this.sort = sort;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.offset = offset;
		this.unpaged = unpaged;
		this.paged = paged;
	}
	public Sort getSort() {
		return sort;
	}
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	public int pageNumber;
    public int pageSize;
    public int offset;
    public boolean unpaged;
    public boolean paged;
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public boolean isUnpaged() {
		return unpaged;
	}
	public void setUnpaged(boolean unpaged) {
		this.unpaged = unpaged;
	}
	public boolean isPaged() {
		return paged;
	}
	public void setPaged(boolean paged) {
		this.paged = paged;
	}
    
}