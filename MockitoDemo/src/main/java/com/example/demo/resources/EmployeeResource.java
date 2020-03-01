package com.example.demo.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Employee;
import com.example.demo.services.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;

@RestController
@Api(consumes = "application/json", produces = "application/json", protocols = "HTTP", value = "CRUD for employee resource")
public class EmployeeResource {

	@Autowired
	private EmployeeService employeeService;
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeResource.class);

	@GetMapping(path = { "/employees/{empId}" }, headers = { "accept=application/json" })
	@ApiOperation(httpMethod = "GET", response = Employee.class, responseHeaders = {
			@ResponseHeader(name = "accept", description = "application/json") }, value = "find by user id")
	public Employee findEmployeeById(@PathVariable final Integer empId) {
		LOGGER.trace("Enter into method");
		LOGGER.info("fetch employee having id " + empId);
		final Employee user = employeeService.findEmployeeById(empId);
		LOGGER.trace("Exit from method");
		return user;
	}

	@GetMapping(path = { "/employees" }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", response = List.class, responseHeaders = {
			@ResponseHeader(name = "accept", description = "application/json") }, value = "find all  the users")
	public List<Employee> findAllEmployee() {
		LOGGER.trace("Enter into method");
		LOGGER.info("fetching employees from database");
		LOGGER.trace("Exit from method");
		return employeeService.findAllEmployee();
	}

	@PostMapping(path = { "/employees" }, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "POST", response = Employee.class, responseHeaders = {
			@ResponseHeader(name = "accept", description = "application/json"),
			@ResponseHeader(name = "content-type", description = "application/json") }, value = "save user")
	public Resource<Employee> addEmployee(@RequestBody final Employee employee) {
		LOGGER.trace("Enter into method");
		LOGGER.info("saving employee in database and adding relative links");

		final Link link = linkTo(methodOn(EmployeeResource.class).findAllEmployee()).withRel("all-employees");
		final Resource<Employee> resource = new Resource<Employee>(employeeService.addEmployee(employee));
		resource.add(link);

		LOGGER.trace("Exit from method");
		return resource;
	}

	@PutMapping(path = { "/employees/{empId}" })
	@ApiOperation(httpMethod = "PUT", response = Employee.class, responseHeaders = {
			@ResponseHeader(name = "accept", description = "application/json"),
			@ResponseHeader(name = "content-type", description = "application/json") }, value = "update user")
	public Employee updateEmployee(@PathVariable final Integer empId, @RequestBody final Employee employee) {
		LOGGER.trace("Enter into method");
		LOGGER.trace("Exit from method");
		return employeeService.updateEmployee(empId, employee);
	}

	@DeleteMapping(path = { "/employees/{empId}" })
	@ApiOperation(httpMethod = "DELETE", response = Employee.class, responseHeaders = {
			@ResponseHeader(name = "accept", description = "application/json") }, value = "delete user")
	public void deleteEmployee(@PathVariable final Integer empId) {
		LOGGER.trace("Enter into method");
		LOGGER.info("delete employee from database");
		employeeService.deleteEmployee(empId);
		LOGGER.trace("Exit from method");
	}

}
