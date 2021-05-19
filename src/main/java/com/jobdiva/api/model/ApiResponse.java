package com.jobdiva.api.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("serial")
public class ApiResponse implements java.io.Serializable {
	
	private Integer			code;
	//
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime	timestamp;
	//
	private String			message;
	//
	private Object			data;
	
	private ApiResponse() {
		timestamp = LocalDateTime.now();
	}
	
	public ApiResponse(Object data) {
		this();
		this.code = HttpStatus.OK.value();
		this.data = data;
	}
	
	public ApiResponse(String message, HttpStatus httpStatus) {
		this();
		this.message = message;
		this.code = httpStatus.value();
	}
	
	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
}