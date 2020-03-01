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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.AppConstant;
import com.example.demo.models.Employee;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;

@RestController
@RequestMapping("wrapper")
@Api(consumes = "application/json", produces = "application/json", protocols = "HTTP", value = "CRUD for employee resource")
public class EmployeeResourceWrapper {

	@Autowired
	private RestTemplate restTemplate;
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeResourceWrapper.class);

	@GetMapping(path = { "/employees/{empId}" }, headers = { "accept=application/json"})
	@ApiOperation(httpMethod = "GET", response = Employee.class, responseHeaders = {
			@ResponseHeader(name = "accept", description = "application/json") }, value = "find by user id")
	public Employee findEmployeeById(@PathVariable final Integer empId) {
		LOGGER.trace("Enter into method");
		LOGGER.info("fetch employee having id " + empId);
		final StringJoiner joiner = new StringJoiner(AppConstant.BLANK);
		joiner.add(AppConstant.PRODUCER_URL).add(AppConstant.EMP_ID_PLACEHOLDER);
		final Employee employee = restTemplate.getForObject(joiner.toString(), Employee.class, new Object[] { empId });
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
		return Arrays.asList(restTemplate.getForObject(AppConstant.PRODUCER_URL, Employee[].class));
	}

	@PostMapping(path = { "/employees" }, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "POST", response = Employee.class, responseHeaders = {
			@ResponseHeader(name = "accept", description = "application/json"),
			@ResponseHeader(name = "content-type", description = "application/json") }, value = "save user")
	public Resource<Employee> addEmployee(@RequestBody final Employee employee) {
		LOGGER.trace("Enter into method");
		LOGGER.info("saving employee in database and adding relative links");

		final Link link = linkTo(methodOn(EmployeeResourceWrapper.class).findAllEmployee()).withRel("all-employees");
		final Resource<Employee> resource = new Resource<Employee>(
				restTemplate.postForObject(AppConstant.PRODUCER_URL, employee, Employee.class));
		resource.add(link);

		LOGGER.trace("Exit from method");
		return resource;
	}

	@PutMapping(path = { "/employees/{empId}" })
	@ApiOperation(httpMethod = "PUT", response = Employee.class, responseHeaders = {
			@ResponseHeader(name = "accept", description = "application/json"),
			@ResponseHeader(name = "content-type", description = "application/json") }, value = "update user")
	public ResponseEntity<Employee> updateEmployee(@PathVariable final Integer empId, @RequestBody final Employee employee) {
		LOGGER.trace("Enter into method");
		LOGGER.trace("Exit from method");
		final StringJoiner joiner = new StringJoiner(AppConstant.BLANK);
		joiner.add(AppConstant.PRODUCER_URL).add(AppConstant.EMP_ID_PLACEHOLDER);
		restTemplate.put(joiner.toString(), employee, new Object[] {"1"});
	
		return ResponseEntity.accepted().body(employee);
	}

	@DeleteMapping(path = { "/employees/{empId}" })
	@ApiOperation(httpMethod = "DELETE", value = "delete user")
	public void deleteEmployee(@PathVariable final Integer empId) {
		LOGGER.trace("Enter into method");
		LOGGER.info("delete employee from database");
		final StringJoiner joiner = new StringJoiner(AppConstant.BLANK);
		joiner.add(AppConstant.PRODUCER_URL).add(AppConstant.EMP_ID_PLACEHOLDER);
		restTemplate.delete(joiner.toString(), new Object[] { empId });
		LOGGER.trace("Exit from method");
	}

}
