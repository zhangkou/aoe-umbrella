package com.aoe.umbrella.entity;

import java.util.Date;

public class ValidationEntity {
	private Date 	validationDate ;
	private String 	validationCode ;
	
	public ValidationEntity(Date validationDate, String validationCode) {
		super();
		this.validationDate = validationDate;
		this.validationCode = validationCode;
	}
	public Date getValidationDate() {
		return validationDate;
	}
	public void setValidationDate(Date validationDate) {
		this.validationDate = validationDate;
	}
	public String getValidationCode() {
		return validationCode;
	}
	public void setValidationCode(String validationCode) {
		this.validationCode = validationCode;
	}
}
