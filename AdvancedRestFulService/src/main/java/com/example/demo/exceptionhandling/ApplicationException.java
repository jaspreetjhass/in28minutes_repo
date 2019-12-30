package com.example.demo.exceptionhandling;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 2876504716392907125L;

	public ApplicationException(final String message) {
		super(message);
	}
}
