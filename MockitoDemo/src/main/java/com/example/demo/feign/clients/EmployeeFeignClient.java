package com.example.demo.feign.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.models.Employee;

@FeignClient(name = "EmployeeFeignClient", url = "http://localhost:8080/employees")
public interface EmployeeFeignClient {

	@GetMapping(path = { "/{empId}" }, headers = { "accept=application/json" })
	Employee findUserById(@PathVariable final Integer empId);

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	List<Employee> findAllEmployee();

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	Resource<Employee> addEmployee(@RequestBody final Employee employee);

	@PutMapping(path = { "/{empId}" })
	Employee updateUser(@PathVariable final Integer empId, @RequestBody final Employee employee);

	@DeleteMapping(path = { "/{empId}" })
	void deleteEmployee(@PathVariable final Integer empId);
}
