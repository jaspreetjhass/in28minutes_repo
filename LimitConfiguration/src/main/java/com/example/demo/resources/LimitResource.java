package com.example.demo.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.LimitConfig;
import com.example.demo.models.LimitResult;

@RestController
public class LimitResource {

	@Autowired
	private LimitConfig config;
	@Autowired
	private Environment env;

	@GetMapping(path = { "/limits" })
	public LimitResult getResult() {

		return new LimitResult(config.getMinimum(), config.getMaximum(),
				Long.parseLong(env.getProperty("server.port")));
	}
}
