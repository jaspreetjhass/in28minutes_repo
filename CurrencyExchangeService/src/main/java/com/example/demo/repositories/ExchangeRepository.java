package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.ExchangeBean;

public interface ExchangeRepository extends CrudRepository<ExchangeBean, Integer> {

	ExchangeBean findByFromAndTo(String from,String to);
}
