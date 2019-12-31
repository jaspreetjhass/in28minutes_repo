package com.example.demo.configurations;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;

@Configuration
public class AppTestConfig {

	public UserRepository getUserRepository() {
		return Mockito.mock(UserRepository.class);
	}
	
	public UserService getUserService() {
		return Mockito.mock(UserService.class);
	}
	
}
