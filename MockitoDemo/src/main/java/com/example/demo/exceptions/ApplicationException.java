package com.example.demo.exceptions;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 3191148374990576486L;

	public ApplicationException(final String message) {
		super(message);
	}

}
