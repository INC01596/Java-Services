package com.incture.cherrywork.dtos;



import java.math.BigDecimal;
import java.util.List;

import com.incture.cherrywork.sales.constants.EnOperation;
import com.incture.cherrywork.util.DB_Operation;
import com.incture.cherrywork.util.InvalidInputFault;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class CustomerDto extends BaseDto {

	private String custId;
	private String custName;
	private String custCity;
	private String custCountry;
	private String custPostalCode;
	private String custCategory;
	private String custCreditLimit;
	private String spCustId;
	private String spName;
	private String spCity;
	private String spPostalCode;
	private BigDecimal exposure;
	
	private String phoneNumber;

	private String teleExtension;

	private String telFax;

	private String faxExtension;

	private String email;

	private Boolean crFlag;
	
	private String spPhoneNumber;

	private String spTeleExtension;

	private String spTelFax;

	private String spFaxExtension;

	private String spEmail;

	private Boolean isPrimary;
	
	private String terms;
	
	private List<PendingInvoiceDto> pendingInvoiceDtoList;

	/*private List<ShippingAddressDto> listOfShippingAddressDto;

	private List<ControllingAreaDto> listOfControllingAreaDto;*/

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustCity() {
		return custCity;
	}

	public void setCustCity(String custCity) {
		this.custCity = custCity;
	}

	public String getCustCountry() {
		return custCountry;
	}

	public void setCustCountry(String custCountry) {
		this.custCountry = custCountry;
	}

	public String getCustPostalCode() {
		return custPostalCode;
	}

	public void setCustPostalCode(String custPostalCode) {
		this.custPostalCode = custPostalCode;
	}

	public String getCustCategory() {
		return custCategory;
	}

	public void setCustCategory(String custCategory) {
		this.custCategory = custCategory;
	}

	public String getCustCreditLimit() {
		return custCreditLimit;
	}

	public void setCustCreditLimit(String custCreditLimit) {
		this.custCreditLimit = custCreditLimit;
	}

	public String getSpCustId() {
		return spCustId;
	}

	public void setSpCustId(String spCustId) {
		this.spCustId = spCustId;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getSpCity() {
		return spCity;
	}

	public void setSpCity(String spCity) {
		this.spCity = spCity;
	}

	public String getSpPostalCode() {
		return spPostalCode;
	}

	public void setSpPostalCode(String spPostalCode) {
		this.spPostalCode = spPostalCode;
	}

	public BigDecimal getExposure() {
		return exposure;
	}

	public void setExposure(BigDecimal exposure) {
		this.exposure = exposure;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getTeleExtension() {
		return teleExtension;
	}

	public void setTeleExtension(String teleExtension) {
		this.teleExtension = teleExtension;
	}

	public String getTelFax() {
		return telFax;
	}

	public void setTelFax(String telFax) {
		this.telFax = telFax;
	}

	public String getFaxExtension() {
		return faxExtension;
	}

	public void setFaxExtension(String faxExtension) {
		this.faxExtension = faxExtension;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getCrFlag() {
		return crFlag;
	}

	public void setCrFlag(Boolean crFlag) {
		this.crFlag = crFlag;
	}

	public String getSpPhoneNumber() {
		return spPhoneNumber;
	}

	public void setSpPhoneNumber(String spPhoneNumber) {
		this.spPhoneNumber = spPhoneNumber;
	}

	public String getSpTeleExtension() {
		return spTeleExtension;
	}

	public void setSpTeleExtension(String spTeleExtension) {
		this.spTeleExtension = spTeleExtension;
	}

	public String getSpTelFax() {
		return spTelFax;
	}

	public void setSpTelFax(String spTelFax) {
		this.spTelFax = spTelFax;
	}

	public String getSpFaxExtension() {
		return spFaxExtension;
	}

	public void setSpFaxExtension(String spFaxExtension) {
		this.spFaxExtension = spFaxExtension;
	}

	public String getSpEmail() {
		return spEmail;
	}

	public void setSpEmail(String spEmail) {
		this.spEmail = spEmail;
	}

	public Boolean getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public List<PendingInvoiceDto> getPendingInvoiceDtoList() {
		return pendingInvoiceDtoList;
	}

	public void setPendingInvoiceDtoList(List<PendingInvoiceDto> pendingInvoiceDtoList) {
		this.pendingInvoiceDtoList = pendingInvoiceDtoList;
	}

	@Override
	public void validate(DB_Operation enOperation) throws InvalidInputFault {

	}

	@Override
	public Object getPrimaryKey() {
		return custId;
	}

	@Override
	public void validate(EnOperation enOperation) throws com.incture.cherrywork.exceptions.InvalidInputFault {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean getValidForUsage() {
		// TODO Auto-generated method stub
		return null;
	}

}
