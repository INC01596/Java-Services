package com.incture.cherrywork.rules;


import lombok.Data;

public @Data class TH_IDBRuleInputDto implements RuleInputDto {

	private String materialGroup;
	private String materialGroup4;
	private Double amount;
	private String distributionChannel;
	private String salesOrg;

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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}
	
	@Override
	public String WorkRuleEnginePayload(String decisonTableId) {
		return "";
	}


	@Override
	public String toRuleInputString(String rulesServiceId) {
		return "{ \"RuleServiceId\" : \"" + rulesServiceId + "\", \"Vocabulary\" : [ {"
				+ "\"TH_IDB_PROC_Input\" : { \"DC\":" + "\"" + this.getDistributionChannel() + "\""
				+ ",\"MaterialGroup\":" + "\"" + this.getMaterialGroup() + "\"" + ",\"MaterialGroup4\":" + "\""
				+ this.getMaterialGroup4() + "\"" + ",\"SalesOrg\":" + "\"" + this.getSalesOrg() + "\"" + ",\"Amount\":" + "\"" + this.getAmount() + "\""
				+ "} } ] }";
	}

}

