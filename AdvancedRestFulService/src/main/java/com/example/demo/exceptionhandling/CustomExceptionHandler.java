package com.example.demo.exceptionhandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.validations.UserNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = ApplicationException.class)
	public ResponseEntity<ExceptionResponse> handleCustomException(WebRequest request, Exception exception) {
		System.out.println("request is " + request);
		System.out.println("exception is " + exception);
		return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new ExceptionResponse(exception.getMessage()));
	}
	
	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleUserNotFoundException(WebRequest request, Exception exception) {
		System.out.println("request is " + request);
		System.out.println("exception is " + exception);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(exception.getMessage()));
	}

	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		System.out.println("request is " + request);
		System.out.println("exception is " + ex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(ex.getMessage()));
	}
	
}
