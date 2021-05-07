package com.incture.cherrywork.dto.new_workflow;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class SubmitHelperDto {
	
	private SalesOrderLevelStatusDto salesOrderLevelStatusDto;
	
	private List<SalesOrderTaskStatusDto> salesOrderTaskStatusDtoList;

}
