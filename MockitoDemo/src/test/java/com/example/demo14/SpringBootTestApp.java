package com.example.demo14;

import java.io.IOException;

import org.mockserver.configuration.ConfigurationProperties;
import org.mockserver.integration.ClientAndServer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import com.example.demo.feign.clients.EmployeeFeignClient;

/**
 * In this class, expectation is loaded into the mockserver from json file
 * present in src/test/resources package
 */
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

	@Bean
	public ClientAndServer createServer() throws IOException {
		/*
		 * ConfigurationProperties.initializationJsonPath(
		 * "C:/Users/hp/Documents/unitTestingdemo/in28minutes_repo/MockitoDemo/src/main/resources/initializerJson.json"
		 * );
		 */
		//ConfigurationProperties.initializationJsonPath("src/test/resources/initializerJson.json");
		ConfigurationProperties.initializationJsonPath("initializerJson.json");
		final ClientAndServer clientAndServer = new ClientAndServer(8080);
		return clientAndServer;
	}

}
