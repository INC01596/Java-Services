package com.incture.cherrywork.dtos;

import com.incture.cherrywork.rules.RuleInputDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TH_IDB_RO_RuleInputDto implements RuleInputDto {

	private String matrialNumber;
	private String salesTerritory;
	private String salesTeam;
	private String distributionChannel;
	private String returnType;
	private double amount;
	private String salesArea;
	private String requestType;
	private String country;
	private String materialGroup;
	private String materialGroup4;
	private String salesOrg;

	public String getMatrialNumber() {
		return matrialNumber;
	}

	public void setMatrialNumber(String matrialNumber) {
		this.matrialNumber = matrialNumber;
	}

	public String getSalesTerritory() {
		return salesTerritory;
	}

	public void setSalesTerritory(String salesTerritory) {
		this.salesTerritory = salesTerritory;
	}

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getSalesArea() {
		return salesArea;
	}

	public void setSalesArea(String salesArea) {
		this.salesArea = salesArea;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMaterialGroup() {
		return materialGroup;
	}

	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}

	public String getMaterialGroup4() {
		return materialGroup4;
	}

	public void setMaterialGroup4(String materialGroup4) {
		this.materialGroup4 = materialGroup4;
	}

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	@Override
	public String WorkRuleEnginePayload(String decisonTableId) {
		if(this.getAmount()>0)
			this.setAmount(1);
		return "{\"decisionTableId\": \"" + decisonTableId
				+ "\",\"conditions\": [{\"ReturnOrderRuleInputDto.Country\": \"" + this.getCountry()
				+ "\",\"ReturnOrderRuleInputDto.Request_Type\": \"" + this.getRequestType()
				+ "\",\"ReturnOrderRuleInputDto.Sales_Team\": \"" + this.getSalesTeam()
				+ "\",\"ReturnOrderRuleInputDto.Sales_Area\": \"" + this.getSalesArea()
				+ "\",\"ReturnOrderRuleInputDto.Material_Group\": \"" + this.getMaterialGroup()
				+ "\",\"ReturnOrderRuleInputDto.Material_Group_4\": \"" + this.getMaterialGroup4()
				+ "\",\"ReturnOrderRuleInputDto.Amount\":\"" + this.getAmount()
				+ "\",\"ReturnOrderRuleInputDto.Material_Number\": \"" + this.getMatrialNumber()
				+ "\",\"ReturnOrderRuleInputDto.Return_Type\": \"" + this.getReturnType()
				+ "\",\"ReturnOrderRuleInputDto.Sales_Org\": \"" + this.getSalesOrg()
				+ "\",\"ReturnOrderRuleInputDto.Distribution_Channel\": \"" + this.getDistributionChannel()
				+ "\"}],\"executionStartsFromRight\":false}";

	}

	@Override
	public String toRuleInputString(String rulesServiceId) {
		return "{ \"RuleServiceId\" : \"" + rulesServiceId + "\", \"Vocabulary\" : [ {"
				+ "\"ReturnOrderRuleInputDto\" : { \"Distribution_Channel\":" + "\"" + this.getDistributionChannel()
				+ "\"" + ",\"Material_Group\":" + "\"" + this.getMaterialGroup() + "\"" + ",\"Material_Group_4\":"
				+ "\"" + this.getMaterialGroup4() + "\"" + ",\"Material_Number\":" + "\"" + this.matrialNumber + "\""
				+ ",\"Sales_Team\":" + "\"" + this.getSalesTerritory() + "\"" + ",\"Sales_Area\":" + "\""
				+ this.getSalesArea() + "\"" + ",\"Return_Type\":" + "\"" + this.getReturnType() + "\""
				+ ",\"Request_Type\":" + "\"" + this.getRequestType() + "\"" + ",\"Country\":" + "\""
				+ this.getCountry() + "\"" + ",\"Amount\":" + "\"" + this.getAmount() + "\"" + ",\"Sales_Org\":" + "\""
				+ this.getSalesOrg() + "\"" + "} } ] }";

	}

}
