package com.caller.exceptionMapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5628073344197900148L;

	public RecordNotFoundException(String exception) {
        super(exception);
    }
}
