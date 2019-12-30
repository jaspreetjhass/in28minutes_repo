package com.example.demo.staticFiltering;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeResource {

	@GetMapping(path= {"/someBean"})
	public SomeBean getSomeResponse() {
		return new SomeBean("value1", "value2", "value3");
	}
}
