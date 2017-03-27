package com.aoe.umbrella.entity;

public class RestMessage {

	private String code;
	private String type;
	private String text;
	
	
	public RestMessage(String code, String type,
			String text) {
		super();
		this.code = code;
		this.type = type;
		this.text = text;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}
	
}
