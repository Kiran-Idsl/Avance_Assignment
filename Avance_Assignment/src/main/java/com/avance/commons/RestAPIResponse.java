package com.avance.commons;

import java.time.LocalDateTime;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RestAPIResponse {
	public String status;
	public String message;
	public Object data;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "hi_IN", timezone = "IST")
	private LocalDateTime timeStamp;

	private RestAPIResponse() {
		timeStamp = LocalDateTime.now();
	}

	public RestAPIResponse(String status) {
		this();
		this.status = status;
	}

	public RestAPIResponse(String status, String message) {
		this();
		this.status = status;
		this.message = message;
	}
	

	public RestAPIResponse(String status, String message, Object data) {
		this();
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	


}
