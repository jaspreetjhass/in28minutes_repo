package com.example.demo15;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.mock.action.ExpectationResponseCallback;
import org.mockserver.model.HttpResponse;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.example.demo.feign.clients.EmployeeFeignClient;
import com.example.demo.models.Employee;
import com.example.demo.resources.EmployeeResourceFeignWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * In this class, only one request object is created with partial path and
 * dynamic response will be generated based on method type and request path
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
	public ClientAndServer createServer(JacksonTester<Employee> jsonParser,
			JacksonTester<List<Employee>> jsonListParser) {
		final ClientAndServer clientAndServer = new ClientAndServer(8080);
		clientAndServer.when(request().withPath("/employees.*"))
				.respond(getResponseCallback(jsonParser, jsonListParser));
		return clientAndServer;
	}

	private ExpectationResponseCallback getResponseCallback(JacksonTester<Employee> jsonParser,
			JacksonTester<List<Employee>> jsonListParser) {
		return (request) -> {
			final Employee employee1 = Employee.builder().empId(1).empName("empName1").address("address1").build();
			final Employee employee2 = Employee.builder().empId(2).empName("empName2").address("address2").build();

			HttpResponse response = null;
			if (request.getMethod().equals(RequestMethod.GET.toString()) && request.getPath().equals("/employees/1")) {

				String body = jsonParser.write(employee1).getJson();
				response = response().withBody(body, MediaType.APPLICATION_JSON).withStatusCode(200);

			} else if (request.getMethod().equals(RequestMethod.GET.toString())
					&& request.getPath().equals("/employees")) {

				final List<Employee> expectedEmpList = Arrays.asList(employee1, employee2);
				String expectedString = jsonListParser.write(expectedEmpList).getJson();
				response = response().withBody(expectedString, MediaType.APPLICATION_JSON).withStatusCode(200);

			} else if (request.getMethod().equals(RequestMethod.POST.toString())
					&& request.getPath().equals("/employees")) {

				final Employee expectedEmployee = Employee.builder().empId(1).empName("empName1").address("address1")
						.build();
				final String expectedString = jsonParser.write(expectedEmployee).getJson();
				final Link link = linkTo(methodOn(EmployeeResourceFeignWrapper.class).findAllEmployee())
						.withRel("all-employees");
				final Resource<Employee> resource = new Resource<Employee>(expectedEmployee);
				resource.add(link);
				response = response().withBody(expectedString, MediaType.APPLICATION_JSON).withStatusCode(201);

			} else if (request.getMethod().equals(RequestMethod.PUT.toString())
					&& request.getPath().equals("/employees/1")) {

				final String expectedEmpString = jsonParser.write(employee1).getJson();
				response = response().withBody(expectedEmpString, MediaType.APPLICATION_JSON);

			} else if (request.getMethod().equals(RequestMethod.DELETE.toString())
					&& request.getPath().equals("/employees/1")) {

				response = response().withStatusCode(204);

			}
			return response;
		};

	}

}