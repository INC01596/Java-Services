package com.incture.cherrywork.entities;


import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "CW_ONEAPP_VARIABLE_MASTER")
public class ApplicationVariablesMasterDo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ApplicationVariablesMasterDoPK id;


}

