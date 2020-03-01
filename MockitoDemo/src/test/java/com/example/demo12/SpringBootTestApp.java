package com.example.demo12;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.web.client.RestTemplate;

import com.example.demo.feign.clients.EmployeeFeignClient;
import com.example.demo.models.Employee;
import com.example.demo.resources.EmployeeResourceFeignWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * In this class, expectations are defined as a private method and are loaded
 * into the mock server
 */
@Configuration
@ComponentScan(basePackages = { "com.example.demo.resources", "com.example.demo.services" })
@EnableFeignClients(basePackageClasses = EmployeeFeignClient.class)
@EnableAutoConfiguration
@EntityScan(basePackages = { "com.example.demo.models" })
@EnableJpaRepositories(basePackages = { "com.example.demo.repositories" })
public class SpringBootTestApp {

	@Bean
	public RestTemplate createRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public JacksonTester<Employee> createJacksonTester() {
		final ObjectMapper mapper = new ObjectMapper();
		return new JacksonTester<>(Employee.class, ResolvableType.forClass(Employee.class), mapper);
	}

	@Bean
	public JacksonTester<List<Employee>> createJacksonTesterList() {
		final ObjectMapper mapper = new ObjectMapper();
		return new JacksonTester<>(List.class, ResolvableType.forClass(List.class), mapper);
	}

	@Bean
	public ClientAndServer createServer(final JacksonTester<Employee> jsonParser,
			final JacksonTester<List<Employee>> jsonListParser) throws IOException {
		final ClientAndServer clientAndServer = new ClientAndServer(8080);
		createGETExpectation(clientAndServer, jsonParser);
		createGETALLExpectation(clientAndServer, jsonListParser);
		createPOSTExpectation(clientAndServer, jsonParser);
		createPUTExpectation(clientAndServer, jsonParser);
		createDELETEExpectation(clientAndServer);
		return clientAndServer;
	}

	private void createGETALLExpectation(final ClientAndServer clientAndServer,
			final JacksonTester<List<Employee>> jsonListParser) throws IOException {

		final Employee employee1 = Employee.builder().empId(1).empName("empName1").address("address1").build();
		final Employee employee2 = Employee.builder().empId(2).empName("empName2").address("address2").build();

		final List<Employee> expectedEmpList = Arrays.asList(employee1, employee2);
		final String expectedString = jsonListParser.write(expectedEmpList).getJson();

		clientAndServer.when(request().withMethod("GET").withPath("/employees"))
				.respond(response().withBody(expectedString, MediaType.APPLICATION_JSON).withStatusCode(200));

	}

	private void createDELETEExpectation(final ClientAndServer clientAndServer) {
		clientAndServer.when(request().withMethod("DELETE").withPath("/employees/1"))
				.respond(response().withStatusCode(204));
	}

	private void createPUTExpectation(final ClientAndServer clientAndServer, final JacksonTester<Employee> jsonParser)
			throws IOException {
		final Employee expectedEmployee = Employee.builder().empId(1).empName("empName1").address("address1").build();
		final String expectedEmpString = jsonParser.write(expectedEmployee).getJson();

		clientAndServer
				.when(request().withMethod("PUT").withPath("/employees/1")
						.withHeader("content-type", "application/json;charset=UTF-8").withBody(expectedEmpString))
				.respond(response().withBody(expectedEmpString, MediaType.APPLICATION_JSON));
	}

	private void createPOSTExpectation(final ClientAndServer clientAndServer, final JacksonTester<Employee> jsonParser)
			throws IOException {

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

	}

	private void createGETExpectation(final ClientAndServer clientAndServer, final JacksonTester<Employee> jsonParser)
			throws IOException {
		final Employee employee = Employee.builder().empId(1).empName("empName1").address("address1").build();
		final String body = jsonParser.write(employee).getJson();
		clientAndServer.when(request().withMethod("GET").withPath("/employees/1"))
				.respond(response().withBody(body, MediaType.APPLICATION_JSON).withStatusCode(200));
	}

}
