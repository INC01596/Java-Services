package com.incture.cherrywork.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class VisitActivitiesResponseDto {

	private String visitId;

	private List<VisitActivityDto> salesOrderList = new ArrayList<>();
	private List<VisitActivityDto> returnOrderList = new ArrayList<>();
	private List<VisitActivityDto> enquiryList = new ArrayList<>();
	private List<VisitActivityDto> quotationList = new ArrayList<>();
	private List<SalesVisitAttachmentDto> attachList = new ArrayList<>();

}
