
package com.incture.cherrywork.dtos;

import java.util.List;

public class TrackSOUIDto {

    private SalesOrderHeaderDto headerDto;
	private List<SalesOrderItemDto> lineItemList;
    private String obdStatus;
    private String pgiStatus;
    private String invoiceStatus;
    private String headerStatus;
    private String deliveryStatus;
    private List<DocDto>results;
    
    public List<DocDto> getResults() {
        return results;
    }
    public void setResults(List<DocDto> results) {
        this.results = results;
    }
    public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public String getHeaderStatus() {
		return headerStatus;
	}
	public void setHeaderStatus(String headerStatus) {
		this.headerStatus = headerStatus;
	}
	public String getObdStatus() {
		return obdStatus;
	}
	public void setObdStatus(String obdStatus) {
		this.obdStatus = obdStatus;
	}
	public String getPgiStatus() {
		return pgiStatus;
	}
	public void setPgiStatus(String pgiStatus) {
		this.pgiStatus = pgiStatus;
	}
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	private int level;
	
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
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
	
	
	
	
}

