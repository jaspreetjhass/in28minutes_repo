package com.example.demo.dynamicFiltering;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class DResource {

	@GetMapping(path = { "/DBean" })
	public MappingJacksonValue getSomeResponse() {

		DBean bean = new DBean("value1", "value2", "value3");

		SimpleBeanPropertyFilter beanProperty = SimpleBeanPropertyFilter.filterOutAllExcept("value1");
		
		SimpleFilterProvider provider = new SimpleFilterProvider();
		provider.addFilter("filter1", beanProperty);

		MappingJacksonValue mjv = new MappingJacksonValue(bean);

		mjv.setFilters(provider);

		return mjv;

	}
	
	@GetMapping(path = {"/DBean2"})
	public MappingJacksonValue getSomeResponse2() {
		DBean bean =  new DBean("value1", "value2", "value3");
		
		SimpleBeanPropertyFilter filter =  SimpleBeanPropertyFilter.serializeAllExcept("value2");
		SimpleFilterProvider provider = new SimpleFilterProvider();
		provider.addFilter("filter1", filter);
		MappingJacksonValue mjv =  new MappingJacksonValue(bean);
		mjv.setFilters(provider);
		
		return mjv;
	}
}
