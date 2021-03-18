package com.incture.cherrywork.dtos;

import java.util.List;

public class OutBoundDeliveryHeaderAndItemOutputDto {
	
	private OutBoundDeliveryDto outboundDeliveryDto;
	private List<OutBoundDeliveryItemDto> outBoundDeliveryItemDto;
	public OutBoundDeliveryDto getOutboundDeliveryDto() {
		return outboundDeliveryDto;
	}
	public void setOutboundDeliveryDto(OutBoundDeliveryDto outboundDeliveryDto) {
		this.outboundDeliveryDto = outboundDeliveryDto;
	}
	public List<OutBoundDeliveryItemDto> getOutBoundDeliveryItemDto() {
		return outBoundDeliveryItemDto;
	}
	public void setOutBoundDeliveryItemDto(List<OutBoundDeliveryItemDto> outBoundDeliveryItemDto) {
		this.outBoundDeliveryItemDto = outBoundDeliveryItemDto;
	}
	
	

}
