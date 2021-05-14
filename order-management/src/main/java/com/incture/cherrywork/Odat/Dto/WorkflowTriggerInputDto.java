package com.incture.cherrywork.Odat.Dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import lombok.Data;

public @Data class WorkflowTriggerInputDto {

	private List<String> salesOrderNumbers;

	private Date schedulerStartDate;

	private Date schedulerEndDate;

	public List<String> getSalesOrderNumbers() {
		return salesOrderNumbers;
	}

	public void setSalesOrderNumbers(List<String> salesOrderNumbers) {
		this.salesOrderNumbers = salesOrderNumbers;
	}

	public Date getSchedulerStartDate() {
		return schedulerStartDate;
	}

	public void setSchedulerStartDate(Date schedulerStartDate) {
		this.schedulerStartDate = schedulerStartDate;
	}

	public Date getSchedulerEndDate() {
		return schedulerEndDate;
	}

	public void setSchedulerEndDate(Date schedulerEndDate) {
		this.schedulerEndDate = schedulerEndDate;
	}

	public static void main(String[] args) {

		LocalDateTime ldt = Instant.ofEpochMilli(1601283479950l).atZone(ZoneId.systemDefault()).toLocalDateTime();
		System.out.println(ldt);
	}

}
