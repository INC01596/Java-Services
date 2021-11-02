package com.incture.cherrywork.util;

import com.incture.cherrywork.dtos.ResponseDto;

public class InvalidInputFault extends Exception {
	private static final long serialVersionUID = 2680366978983580640L;
	private ResponseDto faultInfo;

	public InvalidInputFault() {
	}

	public InvalidInputFault(String faultMessage) {
		super(faultMessage);
		faultInfo = new ResponseDto();
		faultInfo.setMessage(faultMessage);
	}

	public InvalidInputFault(String message, ResponseDto faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	public InvalidInputFault(String message, ResponseDto faultInfo, Throwable cause) {
		super(message, cause);
		this.faultInfo = faultInfo;
	}

	public ResponseDto getFaultInfo() {
		return faultInfo;
	}
}
