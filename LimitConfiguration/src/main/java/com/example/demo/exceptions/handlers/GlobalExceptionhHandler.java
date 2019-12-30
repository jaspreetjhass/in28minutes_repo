package com.example.demo.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.exceptions.ApplicationException;
import com.example.demo.models.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionhHandler {

	@ExceptionHandler(value = Exception.class)
	public @ResponseBody ExceptionResponse handleException() {
		return new ExceptionResponse("exception handled");
	}

	@ExceptionHandler(value = ApplicationException.class)
	public @ResponseBody ResponseEntity<ExceptionResponse> handleRuntimeException(WebRequest request,
			Exception exception) {
		System.out.println("request uri is " + request.getDescription(false));
		System.out.println("exception thrown is " + exception);

		return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
				.body(new ExceptionResponse("error occur while calling server"));

	}
}
