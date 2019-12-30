package com.example.demo.validations;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8707091260620378482L;

	public UserNotFoundException(final String message) {
		super(message);
	}
}
