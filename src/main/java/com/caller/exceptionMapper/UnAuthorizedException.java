package com.caller.exceptionMapper;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends AuthorizationServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6144306152609549021L;

	public UnAuthorizedException(String exception) {
		super(exception);
	}

}
