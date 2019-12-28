package com.example.demo.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ExchangeBean;
import com.example.demo.repositories.ExchangeRepository;

@RestController
public class ExchangeResource {

	@Autowired
	private Environment env;
	@Autowired
	private ExchangeRepository repo;
	
	@GetMapping(path = {"/from/{from}/to/{to}"})
	public ExchangeBean getExchangeRate(@PathVariable String from, @PathVariable String to) {
		ExchangeBean bean = repo.findByFromAndTo(from, to);
		bean.setPort(Long.parseLong(env.getProperty("server.port")));
		return bean;
	}
}
