package com.incture.cherrywork.dtos;



import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreditLimitDto {

	private String custId;

	private BigDecimal exposure;

	private BigDecimal creditLimit;

}

