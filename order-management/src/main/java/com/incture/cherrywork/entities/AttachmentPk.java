package com.incture.cherrywork.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class AttachmentPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String returnReqNum;

	private String docId;

}
