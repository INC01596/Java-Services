package com.incture.cherrywork.OdataSe;

import java.util.Date;
import java.util.Map;

import org.json.JSONObject;

import com.incture.cherrywork.dtos.SalesDocHeaderDto;
import com.incture.cherrywork.sales.constants.DkshBlockConstant;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class ContextDto {

	private SalesDocHeaderDto salesDocHeaderDto;
	private String definitionId;
	private String salesOrderNo;
	private String salesOrderType;
	private String distributionChannel;
	private String division;
	private String requestId;
	private String country;
	private String salesOrg;
	private String customerPo;
	private String requestCategory;
	private String requestType;
	private String soldToParty;
	private String shipToParty;
	private String returnReason;
	private Date soCreatedECC;
	private Map<DkshBlockConstant, Object> data;
	private Object hdb;
	private Object idb;

	public SalesDocHeaderDto getSalesDocHeaderDto() {
		return salesDocHeaderDto;
	}

	public void setSalesDocHeaderDto(SalesDocHeaderDto salesDocHeaderDto) {
		this.salesDocHeaderDto = salesDocHeaderDto;
	}

	public String getDefinitionId() {
		return definitionId;
	}

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getSalesOrderNo() {
		return salesOrderNo;
	}

	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}

	public String getSalesOrderType() {
		return salesOrderType;
	}

	public void setSalesOrderType(String salesOrderType) {
		this.salesOrderType = salesOrderType;
	}

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	public String getCustomerPo() {
		return customerPo;
	}

	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}

	public String getRequestCategory() {
		return requestCategory;
	}

	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getSoldToParty() {
		return soldToParty;
	}

	public void setSoldToParty(String soldToParty) {
		this.soldToParty = soldToParty;
	}

	public String getShipToParty() {
		return shipToParty;
	}

	public void setShipToParty(String shipToParty) {
		this.shipToParty = shipToParty;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public Date getSoCreatedECC() {
		return soCreatedECC;
	}

	public void setSoCreatedECC(Date soCreatedECC) {
		this.soCreatedECC = soCreatedECC;
	}

	public Map<DkshBlockConstant, Object> getData() {
		return data;
	}

	public void setData(Map<DkshBlockConstant, Object> data) {
		this.data = data;
	}

	public Object getHdb() {
		return hdb;
	}

	public void setHdb(Object hdb) {
		this.hdb = hdb;
	}

	public Object getIdb() {
		return idb;
	}

	public void setIdb(Object idb) {
		this.idb = idb;
	}

	public ContextDto(String definitionId, SalesDocHeaderDto salesDocHeaderDto,
			Map<DkshBlockConstant, Object> dataFromBtd) {
		this.definitionId = definitionId;
		this.salesDocHeaderDto = salesDocHeaderDto;
		this.salesOrderNo = salesDocHeaderDto.getSalesOrderNum();
		this.salesOrderType = salesDocHeaderDto.getOrderType();
		this.customerPo = salesDocHeaderDto.getCustomerPo();
		if ((salesDocHeaderDto.getCustomerPo() != null) && salesDocHeaderDto.getCustomerPo().contains("CR")) {
			this.requestCategory = "PR";
			// this.requestType = "05";
		}
		this.distributionChannel = salesDocHeaderDto.getDistributionChannel();
		this.requestId = salesDocHeaderDto.getReqMasterId();
		if (salesDocHeaderDto.getSalesOrderDate() != null)
			this.soCreatedECC = new Date(salesDocHeaderDto.getSalesOrderDate().longValueExact());
		this.salesOrg = salesDocHeaderDto.getSalesOrg();
		this.country = "TH";
		this.returnReason = salesDocHeaderDto.getOrderReason();
		this.soldToParty = salesDocHeaderDto.getSoldToParty();
		this.shipToParty = salesDocHeaderDto.getShipToParty();
		this.division = salesDocHeaderDto.getDivision();
		this.requestType = salesDocHeaderDto.getCondGroup5();
		this.data = dataFromBtd;
		System.err.println("in ContextDto " +dataFromBtd.get(DkshBlockConstant.HDB));
		System.err.println("[ContextDto] "+dataFromBtd.get(DkshBlockConstant.IDB));
		this.hdb = dataFromBtd.get(DkshBlockConstant.HDB);
		this.idb = dataFromBtd.get(DkshBlockConstant.IDB);
	}

}
