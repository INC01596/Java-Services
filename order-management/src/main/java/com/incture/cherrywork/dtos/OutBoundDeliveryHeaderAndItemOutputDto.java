package com.incture.cherrywork.dtos;

public class OutBoundDeliveryHeaderAndItemOutputDto {
	
	private OutBoundDeliveryDto outboundDeliveryDto;
	private OutBoundDeliveryItemDto outBoundDeliveryItemDto;
	public OutBoundDeliveryDto getOutboundDeliveryDto() {
		return outboundDeliveryDto;
	}
	public void setOutboundDeliveryDto(OutBoundDeliveryDto outboundDeliveryDto) {
		this.outboundDeliveryDto = outboundDeliveryDto;
	}
	public OutBoundDeliveryItemDto getOutBoundDeliveryItemDto() {
		return outBoundDeliveryItemDto;
	}
	public void setOutBoundDeliveryItemDto(OutBoundDeliveryItemDto outBoundDeliveryItemDto) {
		this.outBoundDeliveryItemDto = outBoundDeliveryItemDto;
	}
	

}
