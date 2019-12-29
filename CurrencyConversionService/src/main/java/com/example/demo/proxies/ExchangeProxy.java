package com.example.demo.proxies;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.models.ConversionBean;

@FeignClient(name = "exchange-service")
@RibbonClient(name = "exchange-service")
public interface ExchangeProxy {

	@GetMapping(path = { "/from/{from}/to/{to}" })
	public ConversionBean getExchangeRate(@PathVariable("from") String from, @PathVariable("to") String to);

}
