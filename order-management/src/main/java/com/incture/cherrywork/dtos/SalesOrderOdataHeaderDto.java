package com.incture.cherrywork.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;

public class SalesOrderOdataHeaderDto {

	@JsonProperty("temp_id")
	private String temp_id;

	@JsonProperty("DocID_6")
	private String DocID_6;

	@JsonProperty("DocID_2")
	private String DocID_2;

	@JsonProperty("Ref_Doc")
	private String Ref_Doc;

	@JsonProperty("Created_by")
	private String Created_by;

	@JsonProperty("DocType")
	private String DocType;

	@JsonProperty("Doc_Curr_SA")
	private String Doc_Curr_SA;

	@JsonProperty("OrdType")
	private String OrdType;

	@JsonProperty("SoldToParty")
	private String SoldToParty;

	@JsonProperty("ShipToParty")
	private String ShipToParty;

	@JsonProperty("Name")
	private String Name;

	@JsonProperty("EmailID")
	private String EmailID;

	@JsonProperty("City")
	private String City;

	@JsonProperty("DestCountry")
	private String DestCountry;

	@JsonProperty("ContactNo")
	private String ContactNo;

	@JsonProperty("Reference")
	private String Reference;

	@JsonProperty("DistChannel")
	private String DistChannel;

	@JsonProperty("Payment")
	private String Payment;

	@JsonProperty("REQ_DATE")
	private String REQ_DATE;

	@JsonProperty("VALID_F")
	private String VALID_F;

	@JsonProperty("VALID_T")
	private String VALID_T;

	@JsonProperty("Inco1")
	private String Inco1;

	@JsonProperty("Inco2")
	private String Inco2;

	@JsonProperty("Weight")
	private String Weight;

	@JsonProperty("country")
	private String country;

	@JsonProperty("salesG")
	private String salesG;

	@JsonProperty("reason")
	private String reason;

	@JsonProperty("countryText")
	private String countryText;

	@JsonProperty("salesText")
	private String salesText;

	@JsonProperty("reasonText")
	private String reasonText;

	@JsonProperty("PaymentText")
	private String PaymentText;

	@JsonProperty("WFStatus")
	private String WFStatus;

	@JsonProperty("WFMessage")
	private boolean WFMessage;

	@JsonProperty("DistChannelText")
	private String DistChannelText;

	@JsonProperty("Inco1Text")
	private String Inco1Text;

	@JsonProperty("weightAVG")
	private String weightAVG;

	@JsonProperty("BSTKD_E")
	private String BSTKD_E;

	@JsonProperty("OvdelTol")
	private String OvdelTol;

	@JsonProperty("UndelTol")
	private String UndelTol;

	@JsonProperty("Colorcoding")
	private String Colorcoding;

	@JsonProperty("OtherRemark")
	private String OtherRemark;

	@JsonProperty("Project")
	private String Project;

	@JsonProperty("ShType")
	private String ShType;

	@JsonProperty("SotoLi")
	private List<SalesOrderOdataLineItemDto> SotoLi;

	public String getTemp_id() {
		return temp_id;
	}

	public void setTemp_id(String temp_id) {
		this.temp_id = temp_id;
	}

	public String getDocID_6() {
		return DocID_6;
	}

	public void setDocID_6(String docID_6) {
		DocID_6 = docID_6;
	}

	public String getDocID_2() {
		return DocID_2;
	}

	public void setDocID_2(String docID_2) {
		DocID_2 = docID_2;
	}

	public String getRef_Doc() {
		return Ref_Doc;
	}

	public void setRef_Doc(String ref_Doc) {
		Ref_Doc = ref_Doc;
	}

	public String getCreated_by() {
		return Created_by;
	}

	public void setCreated_by(String created_by) {
		Created_by = created_by;
	}

	public String getDoc_Curr_SA() {
		return Doc_Curr_SA;
	}

	public void setDoc_Curr_SA(String doc_Curr_SA) {
		Doc_Curr_SA = doc_Curr_SA;
	}

	public String getDocType() {
		return DocType;
	}

	public void setDocType(String docType) {
		DocType = docType;
	}

	public String getOrdType() {
		return OrdType;
	}

	public void setOrdType(String ordType) {
		OrdType = ordType;
	}

	public String getShipToParty() {
		return ShipToParty;
	}

	public void setShipToParty(String shipToParty) {
		ShipToParty = shipToParty;
	}

	public String getSoldToParty() {
		return SoldToParty;
	}

	public void setSoldToParty(String soldToParty) {
		SoldToParty = soldToParty;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmailID() {
		return EmailID;
	}

	public void setEmailID(String emailID) {
		EmailID = emailID;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getDestCountry() {
		return DestCountry;
	}

	public void setDestCountry(String destCountry) {
		DestCountry = destCountry;
	}

	public String getContactNo() {
		return ContactNo;
	}

	public void setContactNo(String contactNo) {
		ContactNo = contactNo;
	}

	public String getReference() {
		return Reference;
	}

	public void setReference(String reference) {
		Reference = reference;
	}

	public String getDistChannel() {
		return DistChannel;
	}

	public void setDistChannel(String distChannel) {
		DistChannel = distChannel;
	}

	public String getPayment() {
		return Payment;
	}

	public void setPayment(String payment) {
		Payment = payment;
	}

	public String getREQ_DATE() {
		return REQ_DATE;
	}

	public void setREQ_DATE(String rEQ_DATE) {
		REQ_DATE = rEQ_DATE;
	}

	public String getVALID_F() {
		return VALID_F;
	}

	public void setVALID_F(String vALID_F) {
		VALID_F = vALID_F;
	}

	public String getVALID_T() {
		return VALID_T;
	}

	public void setVALID_T(String vALID_T) {
		VALID_T = vALID_T;
	}

	public String getInco1() {
		return Inco1;
	}

	public void setInco1(String inco1) {
		Inco1 = inco1;
	}

	public String getInco2() {
		return Inco2;
	}

	public void setInco2(String inco2) {
		Inco2 = inco2;
	}

	public String getWeight() {
		return Weight;
	}

	public void setWeight(String weight) {
		Weight = weight;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSalesG() {
		return salesG;
	}

	public void setSalesG(String salesG) {
		this.salesG = salesG;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCountryText() {
		return countryText;
	}

	public void setCountryText(String countryText) {
		this.countryText = countryText;
	}

	public String getSalesText() {
		return salesText;
	}

	public void setSalesText(String salesText) {
		this.salesText = salesText;
	}

	public String getReasonText() {
		return reasonText;
	}

	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}

	public String getPaymentText() {
		return PaymentText;
	}

	public void setPaymentText(String paymentText) {
		PaymentText = paymentText;
	}

	public String getWFStatus() {
		return WFStatus;
	}

	public void setWFStatus(String wFStatus) {
		WFStatus = wFStatus;
	}

	public boolean getWFMessage() {
		return WFMessage;
	}

	public void setWFMessage(boolean wFMessage) {
		WFMessage = wFMessage;
	}

	public String getDistChannelText() {
		return DistChannelText;
	}

	public void setDistChannelText(String distChannelText) {
		DistChannelText = distChannelText;
	}

	public String getInco1Text() {
		return Inco1Text;
	}

	public void setInco1Text(String inco1Text) {
		Inco1Text = inco1Text;
	}

	public String getWeightAVG() {
		return weightAVG;
	}

	public void setWeightAVG(String weightAVG) {
		this.weightAVG = weightAVG;
	}

	public String getBSTKD_E() {
		return BSTKD_E;
	}

	public void setBSTKD_E(String bSTKD_E) {
		BSTKD_E = bSTKD_E;
	}

	public String getOvdelTol() {
		return OvdelTol;
	}

	public void setOvdelTol(String ovdelTol) {
		OvdelTol = ovdelTol;
	}

	public String getUndelTol() {
		return UndelTol;
	}

	public void setUndelTol(String undelTol) {
		UndelTol = undelTol;
	}

	public String getColorcoding() {
		return Colorcoding;
	}

	public void setColorcoding(String colorcoding) {
		Colorcoding = colorcoding;
	}

	public String getOtherRemark() {
		return OtherRemark;
	}

	public void setOtherRemark(String otherRemark) {
		OtherRemark = otherRemark;
	}

	public String getProject() {
		return Project;
	}

	public void setProject(String project) {
		Project = project;
	}

	public String getShType() {
		return ShType;
	}

	public void setShType(String shType) {
		ShType = shType;
	}

	public List<SalesOrderOdataLineItemDto> getSotoLi() {
		return SotoLi;
	}

	public void setSotoLi(List<SalesOrderOdataLineItemDto> sotoLi) {
		SotoLi = sotoLi;
	}

	@Override
	public String toString() {
		return "OdataHeaderDto [temp_id=" + temp_id + ", DocID_6=" + DocID_6 + ", DocID_2=" + DocID_2 + ", Ref_Doc="
				+ Ref_Doc + ", Created_by=" + Created_by + ", DocType=" + DocType + ", Doc_Curr_SA=" + Doc_Curr_SA
				+ ", OrdType=" + OrdType + ", SoldToParty=" + SoldToParty + ", ShipToParty=" + ShipToParty + ", Name="
				+ Name + ", EmailID=" + EmailID + ", City=" + City + ", DestCountry=" + DestCountry + ", ContactNo="
				+ ContactNo + ", Reference=" + Reference + ", DistChannel=" + DistChannel + ", Payment=" + Payment
				+ ", REQ_DATE=" + REQ_DATE + ", VALID_F=" + VALID_F + ", VALID_T=" + VALID_T + ", Inco1=" + Inco1
				+ ", Inco2=" + Inco2 + ", Weight=" + Weight + ", country=" + country + ", salesG=" + salesG
				+ ", reason=" + reason + ", countryText=" + countryText + ", salesText=" + salesText + ", reasonText="
				+ reasonText + ", PaymentText=" + PaymentText + ", WFStatus=" + WFStatus + ", WFMessage=" + WFMessage
				+ ", DistChannelText=" + DistChannelText + ", Inco1Text=" + Inco1Text + ", weightAVG=" + weightAVG
				+ ", BSTKD_E=" + BSTKD_E + ", OvdelTol=" + OvdelTol + ", UndelTol=" + UndelTol + ", Colorcoding="
				+ Colorcoding + ", OtherRemark=" + OtherRemark + ", Project=" + Project + ", ShType=" + ShType
				+ ", SotoLi=" + SotoLi + "]";
	}
}
