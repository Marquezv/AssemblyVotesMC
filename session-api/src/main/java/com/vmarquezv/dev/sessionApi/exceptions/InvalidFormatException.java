package com.vmarquezv.dev.sessionApi.exceptions;

public class InvalidFormatException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidFormatException(String msg) {
		super(msg);
	}
}
