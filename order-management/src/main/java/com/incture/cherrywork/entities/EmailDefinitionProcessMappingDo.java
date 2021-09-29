package com.incture.cherrywork.entities;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "EMAIL_DEFINITION_PROCESS_MAPPING")
public class EmailDefinitionProcessMappingDo {
	@Id
	private EmailDefinitionProcessMappingDoPk id;
	@Column(name = "PROCESS_DESC", columnDefinition = "NVARCHAR(100)")
	private String processDesc;
}
