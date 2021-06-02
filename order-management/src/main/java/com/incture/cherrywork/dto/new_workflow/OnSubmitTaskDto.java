package com.incture.cherrywork.dto.new_workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.incture.cherrywork.dtos.BaseDto;
import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;




@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class OnSubmitTaskDto extends BaseDto implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String taskId;
	private String workflowId;
	private String salesOrderNum;
	private Date salesOrderDate;
	private String orderType;
	private String orderTypeText;
	private String customerPoNum;
	private Date customerPoDate;
	private String soldToParty;
	private String soldToPartyText;
	private String shipToParty;
	private String shipToPartyText;
	private String headerDeliveryBlockCode;
	private String headerDeliveryBlockCodeText;
	private Double amount;
	private String decisionSetId;
	private String levelNum;
	private String headerMessage;
	private String loggedInUserName;
	private String requestId;
	private List<ListOfChangedItemData> listOfChangedItemData;
	private Boolean eccUpdateRequired;
	private String headerAcceptOrReject;

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {

	}

	@Override
	public OnSubmitTaskDto clone() throws CloneNotSupportedException {
		OnSubmitTaskDto onSubmitTaskDto = new OnSubmitTaskDto();

		List<ListOfChangedItemData> itemList = new ArrayList<>();

		for (ListOfChangedItemData item : listOfChangedItemData) {
			item = new ListOfChangedItemData();

			itemList.add(item);
		}

		onSubmitTaskDto.setListOfChangedItemData(new ArrayList<>());
		return onSubmitTaskDto;
	}
	public OnSubmitTaskDto() {
		super();
	}
	

	public OnSubmitTaskDto(OnSubmitTaskDto dto) {
		taskId = dto.getTaskId();
		salesOrderNum = dto.getSalesOrderNum();
		salesOrderDate = dto.getSalesOrderDate();
		orderType = dto.getOrderType();
		orderTypeText = dto.getOrderTypeText();
		customerPoNum = dto.getCustomerPoNum();
		customerPoDate = dto.getCustomerPoDate();
		soldToParty = dto.getSoldToParty();
		soldToPartyText = dto.getSoldToPartyText();
		shipToParty = dto.getShipToParty();
		shipToPartyText = dto.getShipToPartyText();
		headerDeliveryBlockCode = dto.getHeaderDeliveryBlockCode();
		headerDeliveryBlockCodeText = dto.getHeaderDeliveryBlockCodeText();
		amount = dto.getAmount();
		decisionSetId = dto.getDecisionSetId();
		levelNum = dto.getLevelNum();
		headerMessage = dto.getHeaderMessage();
		loggedInUserName = dto.getLoggedInUserName();
		requestId = dto.getRequestId();

		eccUpdateRequired = dto.getEccUpdateRequired();
		List<ListOfChangedItemData> itemList = new ArrayList<>();
		for (ListOfChangedItemData item : dto.getListOfChangedItemData()) {

			ListOfChangedItemData itemToList = new ListOfChangedItemData(item);
			itemList.add(itemToList);

		}

		listOfChangedItemData = itemList;
	}

	

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getSalesOrderNum() {
		return salesOrderNum;
	}

	public void setSalesOrderNum(String salesOrderNum) {
		this.salesOrderNum = salesOrderNum;
	}

	public Date getSalesOrderDate() {
		return salesOrderDate;
	}

	public void setSalesOrderDate(Date salesOrderDate) {
		this.salesOrderDate = salesOrderDate;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderTypeText() {
		return orderTypeText;
	}

	public void setOrderTypeText(String orderTypeText) {
		this.orderTypeText = orderTypeText;
	}

	public String getCustomerPoNum() {
		return customerPoNum;
	}

	public void setCustomerPoNum(String customerPoNum) {
		this.customerPoNum = customerPoNum;
	}

	public Date getCustomerPoDate() {
		return customerPoDate;
	}

	public void setCustomerPoDate(Date customerPoDate) {
		this.customerPoDate = customerPoDate;
	}

	public String getSoldToParty() {
		return soldToParty;
	}

	public void setSoldToParty(String soldToParty) {
		this.soldToParty = soldToParty;
	}

	public String getSoldToPartyText() {
		return soldToPartyText;
	}

	public void setSoldToPartyText(String soldToPartyText) {
		this.soldToPartyText = soldToPartyText;
	}

	public String getShipToParty() {
		return shipToParty;
	}

	public void setShipToParty(String shipToParty) {
		this.shipToParty = shipToParty;
	}

	public String getShipToPartyText() {
		return shipToPartyText;
	}

	public void setShipToPartyText(String shipToPartyText) {
		this.shipToPartyText = shipToPartyText;
	}

	public String getHeaderDeliveryBlockCode() {
		return headerDeliveryBlockCode;
	}

	public void setHeaderDeliveryBlockCode(String headerDeliveryBlockCode) {
		this.headerDeliveryBlockCode = headerDeliveryBlockCode;
	}

	public String getHeaderDeliveryBlockCodeText() {
		return headerDeliveryBlockCodeText;
	}

	public void setHeaderDeliveryBlockCodeText(String headerDeliveryBlockCodeText) {
		this.headerDeliveryBlockCodeText = headerDeliveryBlockCodeText;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDecisionSetId() {
		return decisionSetId;
	}

	public void setDecisionSetId(String decisionSetId) {
		this.decisionSetId = decisionSetId;
	}

	public String getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(String levelNum) {
		this.levelNum = levelNum;
	}

	public String getHeaderMessage() {
		return headerMessage;
	}

	public void setHeaderMessage(String headerMessage) {
		this.headerMessage = headerMessage;
	}

	public String getLoggedInUserName() {
		return loggedInUserName;
	}

	public void setLoggedInUserName(String loggedInUserName) {
		this.loggedInUserName = loggedInUserName;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public List<ListOfChangedItemData> getListOfChangedItemData() {
		return listOfChangedItemData;
	}

	public void setListOfChangedItemData(List<ListOfChangedItemData> listOfChangedItemData) {
		this.listOfChangedItemData = listOfChangedItemData;
	}

	public Boolean getEccUpdateRequired() {
		return eccUpdateRequired;
	}

	public void setEccUpdateRequired(Boolean eccUpdateRequired) {
		this.eccUpdateRequired = eccUpdateRequired;
	}

	public String getHeaderAcceptOrReject() {
		return headerAcceptOrReject;
	}

	public void setHeaderAcceptOrReject(String headerAcceptOrReject) {
		this.headerAcceptOrReject = headerAcceptOrReject;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

