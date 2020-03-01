package com.example.demo11.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.models.Employee;
import com.example.demo.resources.EmployeeResourceFeignWrapper;
import com.example.demo11.SpringBootTestApp;

@SpringBootTest(classes = { SpringBootTestApp.class })
@EnableWebMvc
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class EmployeeResourceFeignWrapperWithMockServer {

	@Autowired
	private JacksonTester<Employee> jsonParser;
	@Autowired
	private JacksonTester<Resource<Employee>> jsonResourceParser;
	@Autowired
	private JacksonTester<List<Employee>> jsonListParser;
	@Autowired
	private MockMvc mockMvc;
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

		final RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/feignWrapper/employees/{empId}", new Object[] { "1" })
				.accept(org.springframework.http.MediaType.APPLICATION_JSON);
		final MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		final String actualString = mvcResult.getResponse().getContentAsString();

		final Employee empOut = jsonParser.parse(actualString).getObject();
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

		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/feignWrapper/employees")
				.accept(org.springframework.http.MediaType.APPLICATION_JSON);
		final MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		final String actualString = mvcResult.getResponse().getContentAsString();

		final List<Employee> actualEmpList = jsonListParser.parse(actualString).getObject();

		assertThat(actualEmpList).hasSize(2);
	}

	@Test
	public void addEmployee() throws Exception {

		final Employee testEmployee = Employee.builder().empName("empName1").address("address1").build();
		final String testString = jsonParser.write(testEmployee).getJson();

		final Employee expectedEmployee = Employee.builder().empId(1).empName("empName1").address("address1").build();
		final String expectedString = jsonParser.write(expectedEmployee).getJson();
		final Link link = linkTo(methodOn(EmployeeResourceFeignWrapper.class).findAllEmployee())
				.withRel("all-employees");
		final Resource<Employee> resource = new Resource<Employee>(expectedEmployee);
		resource.add(link);

		clientAndServer
				.when(request().withMethod("POST").withPath("/employees").withHeader("content-type", "application/json")
						.withHeader("accept", "application/json").withBody(testString))
				.respond(response().withBody(expectedString, MediaType.APPLICATION_JSON).withStatusCode(201));

		final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/feignWrapper/employees")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(testString)
				.accept(org.springframework.http.MediaType.APPLICATION_JSON);

		final MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		final String actualString = mvcResult.getResponse().getContentAsString();

		final Resource<Employee> actualEmployee = jsonResourceParser.parse(actualString).getObject();

		assertEquals(expectedEmployee.getEmpId(), actualEmployee.getContent().getEmpId());

	}

	@Test
	public void updateEmployee() throws Exception {

		final Employee expectedEmployee = Employee.builder().empId(1).empName("empName1").address("address1").build();
		final String expectedEmpString = jsonParser.write(expectedEmployee).getJson();

		clientAndServer
				.when(request().withMethod("PUT").withPath("/employees/1")
						.withHeader("content-type", "application/json;charset=UTF-8").withBody(expectedEmpString))
				.respond(response().withBody(expectedEmpString, MediaType.APPLICATION_JSON));
		Thread.sleep(10000);

		final RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/feignWrapper/employees/1")
				.content(expectedEmpString).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.accept(org.springframework.http.MediaType.APPLICATION_JSON);

		final MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		final String actualString = mvcResult.getResponse().getContentAsString();

		final Employee actualEmployee = jsonParser.parse(actualString).getObject();

		assertEquals(expectedEmployee.getEmpName() , actualEmployee.getEmpName());

	}

	@Test
	public void deleteEmployee() throws Exception {
		clientAndServer.reset();
		clientAndServer.when(request().withMethod("DELETE").withPath("/employees/1"))
				.respond(response().withStatusCode(204));

		final RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/feignWrapper/employees/1");
		mockMvc.perform(requestBuilder);

		clientAndServer.verify(request().withMethod("DELETE").withPath("/employees/1"), VerificationTimes.once());
	}

}