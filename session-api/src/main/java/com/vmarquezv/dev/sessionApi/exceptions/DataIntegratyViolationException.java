package com.vmarquezv.dev.sessionApi.exceptions;

public class DataIntegratyViolationException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public DataIntegratyViolationException(String msg) {
		super(msg);
	}
	
}
