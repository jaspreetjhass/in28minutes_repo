package com.example.demo.exceptions;

public class ApplicationException extends RuntimeException {
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 3191148374990576486L;

	public ApplicationException(String message) {
		super(message);
	}

}
