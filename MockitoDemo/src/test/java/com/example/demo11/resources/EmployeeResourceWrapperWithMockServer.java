package com.example.demo11.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;

import com.example.demo.models.Employee;
import com.example.demo.resources.EmployeeResourceWrapper;
import com.example.demo11.SpringBootTestApp;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = { SpringBootTestApp.class })
@AutoConfigureJsonTesters
class EmployeeResourceWrapperWithMockServer {

	@Autowired
	private EmployeeResourceWrapper resourceWrapper;
	@Autowired
	private JacksonTester<Employee> jsonParser;
	@Autowired
	private JacksonTester<List<Employee>> jsonListParser;

	private ClientAndServer clientAndServer;

	@BeforeEach
	public void beforeTest() {
		clientAndServer = new ClientAndServer(8080);
	}

	@AfterEach
	public void afterTest() {
		clientAndServer.stop();
	}

	@Test
	public void findEmployeeById() throws Exception {
		final Employee employee = Employee.builder().empId(1).empName("empName1").address("address1").build();
		final String body = jsonParser.write(employee).getJson();

		clientAndServer.when(request().withMethod("GET").withPath("/employees/1"))
				.respond(response().withBody(body, MediaType.APPLICATION_JSON).withStatusCode(200));

		final Employee empOut = resourceWrapper.findEmployeeById(1);
		assertEquals(employee.getEmpName(), empOut.getEmpName());
	}

	@Test
	public void findAllEmployee() throws Exception {
		final Employee employee1 = Employee.builder().empId(1).empName("empName1").address("address1").build();
		final Employee employee2 = Employee.builder().empId(2).empName("empName2").address("address2").build();

		final List<Employee> expectedEmpList = Arrays.asList(employee1, employee2);
		final String expectedString = jsonListParser.write(expectedEmpList).getJson();

		clientAndServer.when(request().withMethod("GET").withPath("/employees"))
				.respond(response().withBody(expectedString, MediaType.APPLICATION_JSON).withStatusCode(200));

		final List<Employee> actualEmpList = resourceWrapper.findAllEmployee();

		assertThat(actualEmpList).hasSize(2);
	}

	@Test
	public void addEmployee() throws IOException {

		final Employee testEmployee = Employee.builder().empName("empName1").address("address1").build();
		final String testString = jsonParser.write(testEmployee).getJson();

		final Employee expectedEmployee = Employee.builder().empId(1).empName("empName1").address("address1").build();
		final String expectedString = jsonParser.write(expectedEmployee).getJson();

		clientAndServer
				.when(request().withMethod("POST").withPath("/employees")
						.withHeader("content-type", "application/json;charset=UTF-8").withBody(testString))
				.respond(response().withBody(expectedString, MediaType.APPLICATION_JSON).withStatusCode(201));

		final Employee actualEmployee = resourceWrapper.addEmployee(testEmployee).getContent();

		assertEquals(expectedEmployee.getEmpId(), actualEmployee.getEmpId());

	}

	@Test
	public void updateEmployee() throws IOException, InterruptedException {

		final Employee expectedEmployee = Employee.builder().empId(1).empName("empName1").address("address1").build();
		final ObjectMapper mapper = new ObjectMapper();
		final String expectedString = mapper.writeValueAsString(expectedEmployee);
		clientAndServer
				.when(request().withMethod("PUT").withPath("/employees/1")
						.withHeader("content-type", "application/json;charset=UTF-8").withBody(expectedString))
				.respond(response().withBody(expectedString).withHeader("content-type",
						"application/json;charset=UTF-8"));
		Thread.sleep(10000);
		final Employee actualEmployee = resourceWrapper.updateEmployee(1, expectedEmployee).getBody();

		assertEquals(expectedEmployee.getEmpName(), actualEmployee.getEmpName());

	}

	@Test
	public void deleteEmployee() {
		clientAndServer.when(request().withMethod("DELETE").withPath("/employees/1"))
				.respond(response().withStatusCode(204));
		resourceWrapper.deleteEmployee(1);
		clientAndServer.verify(request().withMethod("DELETE").withPath("/employees/1"), VerificationTimes.once());
	}

}