package com.incture.cherrywork.dtos;

import java.util.List;

public class InvoiceDetailsDto {

	private List<InvoiceLineItemDTO> listOfInvoiceLineItems;
	private List<ParkingDto> listOfParkingDto;
	public List<InvoiceLineItemDTO> getListOfInvoiceLineItems() {
		return listOfInvoiceLineItems;
	}
	public void setListOfInvoiceLineItems(List<InvoiceLineItemDTO> listOfInvoiceLineItems) {
		this.listOfInvoiceLineItems = listOfInvoiceLineItems;
	}
	public List<ParkingDto> getListOfParkingDto() {
		return listOfParkingDto;
	}
	public void setListOfParkingDto(List<ParkingDto> listOfParkingDto) {
		this.listOfParkingDto = listOfParkingDto;
	}
	

}
