package com.example.demo11;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import com.example.demo.feign.clients.EmployeeFeignClient;

/** In this class, resource classes are tested using mock server */
@Configuration
@ComponentScan(basePackages = { "com.example.demo.resources", "com.example.demo.services" })
@EnableFeignClients(basePackageClasses = EmployeeFeignClient.class)
@EnableAutoConfiguration
@EntityScan(basePackages = { "com.example.demo.models" })
@EnableJpaRepositories(basePackages = { "com.example.demo.repositories" })
public class SpringBootTestApp {

	@Bean
	public RestTemplate createRestTemplate() {
		return new RestTemplate();
	}

}