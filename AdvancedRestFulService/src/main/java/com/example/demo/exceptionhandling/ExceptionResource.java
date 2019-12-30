package com.example.demo.exceptionhandling;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionResource {

	@GetMapping(path= {"/exception"})
	public SomeBean getResult()
	{
		throw new ApplicationException("exception occured");
	}
}
