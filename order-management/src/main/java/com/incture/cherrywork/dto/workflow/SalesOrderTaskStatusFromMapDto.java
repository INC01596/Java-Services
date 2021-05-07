package com.incture.cherrywork.dto.workflow;



import com.incture.cherrywork.dtos.BaseDto;
import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class SalesOrderTaskStatusFromMapDto extends BaseDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String level;

	private String approvalGroup;

	private String groupType;

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		throw new UnsupportedOperationException();
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getApprovalGroup() {
		return approvalGroup;
	}

	public void setApprovalGroup(String approvalGroup) {
		this.approvalGroup = approvalGroup;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
