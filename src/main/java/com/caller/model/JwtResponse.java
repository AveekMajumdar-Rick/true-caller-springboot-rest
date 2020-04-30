package com.caller.model;

import java.io.Serializable;
import java.util.Date;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final Date expirationDate;
	private final Date issueDate;
	private final String jwttoken;

	public JwtResponse(Date expirationDate,Date issueDate, String jwttoken) {
		this.expirationDate = expirationDate;
		this.issueDate = issueDate;
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
	
	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public Date getIssueDate() {
		return issueDate;
	}
}
