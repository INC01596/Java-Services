package com.incture.cherrywork.entities;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "OCR_TRANSACTION_DETAILS")
@ToString
@Getter
@Setter
public class OcrTranscationDetailsDo {
	@Id
	@Column(name="REQUEST_ID")
	private String requestId;
	
	@Column(name="FILE_ID")
	private String fileId;
	
	@Column(name="TRANSCATION_ID")
	private String transcrtionId;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CHECK_COUNT")
	private Integer checkCount;
	
	@Column(name="OCR_ENGINE")
	private String ocrEngine;
	
	@Column(name="FILE_START_TIME")
	private Timestamp fileProcessingStartTimestamp;
	
	@Column(name="FILE_END_TIME")
	private Timestamp fileProcessingEndTimestamp;
}
