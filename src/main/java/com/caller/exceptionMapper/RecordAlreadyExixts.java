package com.caller.exceptionMapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecordAlreadyExixts extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6718325529056821064L;

	public RecordAlreadyExixts(String exception) {
		super(exception);
	}

	
}
