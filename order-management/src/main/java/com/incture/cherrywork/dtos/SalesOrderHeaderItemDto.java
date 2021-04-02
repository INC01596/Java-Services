package com.incture.cherrywork.dtos;



import java.util.List;


public class SalesOrderHeaderItemDto {

	private SalesOrderHeaderDto headerDto;
	private List<SalesOrderItemDto> lineItemList;
	public SalesOrderHeaderDto getHeaderDto() {
		return headerDto;
	}
	public void setHeaderDto(SalesOrderHeaderDto headerDto) {
		this.headerDto = headerDto;
	}
	public List<SalesOrderItemDto> getLineItemList() {
		return lineItemList;
	}
	public void setLineItemList(List<SalesOrderItemDto> lineItemList) {
		this.lineItemList = lineItemList;
	}
	@Override
	public String toString() {
		return "SalesHeaderItemDto [headerDto=" + headerDto + ", lineItemList=" + lineItemList + "]";
	}
}
