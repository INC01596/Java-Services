package com.incture.cherrywork.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class FilterOnReturnHeaderDto {

	private String returnType; // H (RoType from task)
	private String customerPo; // H
	private String orderNumber; // H
	private List<String> distributionChannelList; // H DAC
	private List<String> divisionList; // H DAC
	private List<String> salesOrgList; // H DAC
	private List<String> orderTypeList; // H DAC
	private List<String> soldToPartyList; // H DAC (Customer)
	private List<String> shipToPartyList; // H
	private List<String> returnReasonList; // H
	private List<String> headerDeliveryBlockList; // H
	private String loginInUserId; // UI
	private String projectCode; // UI
	private Integer indexNum; // UI
	private Integer count; // UI

}
