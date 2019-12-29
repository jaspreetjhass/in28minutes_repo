package com.example.demo.resources;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.models.ConversionBean;
import com.example.demo.proxies.ExchangeProxy;

@RestController
public class ConversionResource {

	@Autowired
	private RestTemplate temp;
	@Autowired
	private ExchangeProxy proxy;

	@GetMapping(path = { "/from/{from}/to/{to}/quantity/{quantity}" })
	public ConversionBean getExchangeRate(@PathVariable String from, @PathVariable String to,
			@PathVariable Integer quantity) {
		Map<String,Object> uriVariables =  new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ConversionBean bean = temp.getForEntity("http://exchange-service:9090/from/{from}/to/{to}", ConversionBean.class,uriVariables).getBody();
		return bean;
	}
	
	
	@GetMapping(path = { "/feign/from/{from}/to/{to}/quantity/{quantity}" })
	public ConversionBean getFeignExchangeRate(@PathVariable String from, @PathVariable String to,
			@PathVariable Integer quantity) {
		ConversionBean bean = proxy.getExchangeRate(from, to);
		return bean;
	}
}
