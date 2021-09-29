package com.incture.cherrywork.entities;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class RecipientDoPK implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "EMAIL_DEFINITION_ID", columnDefinition = "NVARCHAR(36)")
	private String emailDefinitionId;
	@Column(name = "RECIPIENT_TYPE", columnDefinition = "NVARCHAR(5)")
	private String recipientType;
	@Column(name = "USER_EMAIL", columnDefinition = "NVARCHAR(100)")
	private String userEmail;

}
