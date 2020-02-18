package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

}
