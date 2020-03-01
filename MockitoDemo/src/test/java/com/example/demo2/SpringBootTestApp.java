package com.example.demo2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.example.demo"}) 
@EntityScan(basePackages = { "com.example.demo.models" })
@EnableJpaRepositories(basePackages = { "com.example.demo.repositories" })
public class SpringBootTestApp {

}
