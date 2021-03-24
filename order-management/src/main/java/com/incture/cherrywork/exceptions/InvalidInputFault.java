package com.incture.cherrywork.exceptions;

/**
 * <code>InvalidInputFault</code> is to indicate application that the parameters
 * passed to the method is invalid w.r.t its implementation.
 * 
 * @author VINU
 * @version 2.0, 21-June-2012
 */

public class InvalidInputFault extends Exception {
	private static final long serialVersionUID = 2680366978983580640L;

	public InvalidInputFault() {
		// TODO Auto-generated constructor stub
	}

	public InvalidInputFault(String faultMessage) {
		super(faultMessage);
	}

	public InvalidInputFault(String message, Throwable cause) {
		super(message, cause);
	}

}
