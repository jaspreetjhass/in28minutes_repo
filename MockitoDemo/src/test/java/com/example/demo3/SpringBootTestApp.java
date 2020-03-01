package com.example.demo3;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = { "com.example.demo.models" })
@EnableJpaRepositories(basePackages = { "com.example.demo.repositories" })
@AutoConfigureTestDatabase
@AutoConfigureDataJpa
@AutoConfigureTestEntityManager
public class SpringBootTestApp {

}
