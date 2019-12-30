package com.example.demo.models;

public class ExceptionResponse {

	private String message;

	public ExceptionResponse() {
		// TODO Auto-generated constructor stub
	}

	public ExceptionResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ExceptionResponse [message=" + message + "]";
	}

}
