package com.example.demo.exceptionhandling;

public class ExceptionResponse {

	private String message;

	public ExceptionResponse() {
	}

	public ExceptionResponse(final String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ExceptionResponse [message=" + message + "]";
	}

}
