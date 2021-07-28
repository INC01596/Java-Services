package com.incture.cherrywork.rules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class ApproverDataOutputDto implements RuleOutputDto, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String approver;
	private String level;
	private String approvalType;
	
	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public List<ApproverDataOutputDto> convertFromJSonNode(String node) {
		List<ApproverDataOutputDto> approverList = new ArrayList<>();
		JSONObject jObj = new JSONObject(node);
		JSONArray arr = jObj.getJSONArray("Result");
		JSONArray innerArray = null;
		if(!arr.isNull(0))
			innerArray = arr.getJSONObject(0).getJSONArray("Approval_Matrix_Output");
		
		if(innerArray!=null) {
		for (int i = 0; i < innerArray.length(); i++) {
			ApproverDataOutputDto approverDto = new ApproverDataOutputDto();
			approverDto.setApprover(innerArray.getJSONObject(i).get("Approver").toString());
			approverDto.setLevel(innerArray.getJSONObject(i).get("Level").toString());
			approverDto.setApprovalType(innerArray.getJSONObject(i).get("ApproverType").toString());
			approverList.add(approverDto);

		}
		return approverList;
		}
		return null;

	}
	
	@Override
	public List<ApproverDataOutputDto> convertFromJSonNodeRo(String node){
		List<ApproverDataOutputDto> approverList = new ArrayList<>();
		JSONObject jObj = new JSONObject(node);
		JSONArray arr = jObj.getJSONArray("Result");
		JSONArray innerArray = null;
		if(!arr.isNull(0))
			innerArray = arr.getJSONObject(0).getJSONArray("ReturnOrderRuleOutput");
		
		if(innerArray!=null) {
		for (int i = 0; i < innerArray.length(); i++) {
			ApproverDataOutputDto approverDto = new ApproverDataOutputDto();
			approverDto.setApprover(innerArray.getJSONObject(i).get("Approver").toString());
			approverDto.setLevel(innerArray.getJSONObject(i).get("Level").toString());
			approverDto.setApprovalType(innerArray.getJSONObject(i).get("ApproveType").toString());
			approverList.add(approverDto);

		}
		System.err.println("[convertFromJSonNodeRo] approverList: "+approverList);
		return approverList;
		}
	
	return null;
}
}

