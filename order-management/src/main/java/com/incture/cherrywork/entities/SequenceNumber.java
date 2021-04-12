package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "SEQ_NUMBER")
//@NamedQuery(name = "SequenceNumber.getAll", query = "SELECT seq FROM SequenceNumber seq")
public class SequenceNumber {

	@Id
	@Column(name = "REFERENCE_CODE")
	private String referenceCode;

	@Column(name = "RUNNING_NUMBER")
	private Integer runningNumber;

	public SequenceNumber() {

	}

	public SequenceNumber(String referenceCode, Integer runningNumber) {
		this.referenceCode = referenceCode;
		this.runningNumber = runningNumber;
	}

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public Integer getRunningNumber() {
		return runningNumber;
	}

	public void setRunningNumber(Integer runningNumber) {
		this.runningNumber = runningNumber;
	}
}
