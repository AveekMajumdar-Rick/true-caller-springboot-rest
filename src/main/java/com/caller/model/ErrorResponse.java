package com.caller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "status", "errorCode", "userMessage"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

	
	private String errorCode;
	private String userMessage;
	
	
	
	public ErrorResponse( String errorCode, String userMessage) {
		super();

		this.errorCode = errorCode;
		this.userMessage = userMessage;
	}
	
	
	@JsonProperty("error_code")
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	@JsonProperty("user_message")
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}


	
}
