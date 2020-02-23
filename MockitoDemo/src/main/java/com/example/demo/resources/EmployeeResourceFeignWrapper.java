package com.example.demo.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.AppConstant;
import com.example.demo.feign.clients.EmployeeFeignClient;
import com.example.demo.models.Employee;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;

@RestController
@RequestMapping("feignWrapper")
@Api(consumes = "application/json", produces = "application/json", protocols = "HTTP", value = "CRUD for employee resource")
public class EmployeeResourceFeignWrapper {

	@Autowired
	private EmployeeFeignClient employeeFeignClient;
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeResourceFeignWrapper.class);

	@GetMapping(path = { "/employees/{empId}" }, headers = { "accept=application/json" })
	@ApiOperation(httpMethod = "GET", response = Employee.class, responseHeaders = {
			@ResponseHeader(name = "accept", description = "application/json") }, value = "find by user id")
	public Employee findUserById(@PathVariable final Integer empId) {
		LOGGER.trace("Enter into method");
		LOGGER.info("fetch employee having id " + empId);
		final Employee employee = employeeFeignClient.findUserById(empId);
		LOGGER.info("address is: " + employee.getAddress());
		LOGGER.trace("Exit from method");
		return employee;
	}

	@GetMapping(path = { "/employees" }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", response = List.class, responseHeaders = {
			@ResponseHeader(name = "accept", description = "application/json") }, value = "find all  the users")
	public List<Employee> findAllEmployee() {
		LOGGER.trace("Enter into method");
		LOGGER.info("fetching employees from database");
		LOGGER.trace("Exit from method");
		return employeeFeignClient.findAllEmployee();
	}

	@PostMapping(path = { "/employees" }, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "POST", response = Employee.class, responseHeaders = {
			@ResponseHeader(name = "accept", description = "application/json"),
			@ResponseHeader(name = "content-type", description = "application/json") }, value = "save user")
	public Resource<Employee> addEmployee(@RequestBody final Employee employee) {
		LOGGER.trace("Enter into method");
		LOGGER.info("saving employee in database and adding relative links");

		final Link link = linkTo(methodOn(EmployeeResourceFeignWrapper.class).findAllEmployee())
				.withRel("all-employees");
		employeeFeignClient.addEmployee(employee);
		final Resource<Employee> resource = new Resource<Employee>(employee, Arrays.asList(link));

		LOGGER.trace("Exit from method");
		return resource;
	}

	@PutMapping(path = { "/employees/{empId}" })
	@ApiOperation(httpMethod = "PUT", response = Employee.class, responseHeaders = {
			@ResponseHeader(name = "accept", description = "application/json"),
			@ResponseHeader(name = "content-type", description = "application/json") }, value = "update user")
	public Employee updateUser(@PathVariable final Integer empId, @RequestBody final Employee employee) {
		LOGGER.trace("Enter into method");
		LOGGER.trace("Exit from method");
		StringJoiner joiner = new StringJoiner(AppConstant.BLANK);
		joiner.add(AppConstant.PRODUCER_URL).add(AppConstant.EMP_ID_PLACEHOLDER);
		employeeFeignClient.updateUser(empId, employee);
		return employee;
	}

	@DeleteMapping(path = { "/employees/{empId}" })
	@ApiOperation(httpMethod = "DELETE", response = Employee.class, responseHeaders = {
			@ResponseHeader(name = "accept", description = "application/json") }, value = "delete user")
	public void deleteEmployee(@PathVariable final Integer empId) {
		LOGGER.trace("Enter into method");
		LOGGER.info("delete employee from database");
		StringJoiner joiner = new StringJoiner(AppConstant.BLANK);
		joiner.add(AppConstant.PRODUCER_URL).add(AppConstant.EMP_ID_PLACEHOLDER);
		employeeFeignClient.deleteEmployee(empId);
		LOGGER.trace("Exit from method");
	}

}
