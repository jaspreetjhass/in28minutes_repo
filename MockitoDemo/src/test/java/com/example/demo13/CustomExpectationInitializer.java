package com.example.demo13;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.mockserver.mock.Expectation;
import org.mockserver.model.MediaType;
import org.mockserver.server.initialize.ExpectationInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import com.example.demo.models.Employee;
import com.example.demo.resources.EmployeeResourceFeignWrapper;

@Component
public class CustomExpectationInitializer implements ExpectationInitializer {

	@Autowired
	private JacksonTester<Employee> jsonParser;
	@Autowired
	private JacksonTester<List<Employee>> jsonListParser;

	@Override
	public Expectation[] initializeExpectations() {
		final Expectation[] expectations = new Expectation[] { createGETALLExpectation(),
				createGETExpectation(), createDELETEExpectation(), createPOSTExpectation(),
				createPUTExpectation() };
		return expectations;
	}

	private Expectation createGETALLExpectation() {

		final Employee employee1 = Employee.builder().empId(1).empName("empName1").address("address1").build();
		final Employee employee2 = Employee.builder().empId(2).empName("empName2").address("address2").build();

		final List<Employee> expectedEmpList = Arrays.asList(employee1, employee2);
		String expectedString = null;
		try {
			expectedString = jsonListParser.write(expectedEmpList).getJson();
		} catch (final IOException exception) {
			System.out.println("exception occur");
		}
		final Expectation getAllExpectation = new Expectation(request().withMethod("GET").withPath("/employees"))
				.thenRespond(response().withBody(expectedString, MediaType.APPLICATION_JSON).withStatusCode(200));

		return getAllExpectation;
	}

	private Expectation createDELETEExpectation() {
		final Expectation deleteExpectation = new Expectation(request().withMethod("DELETE").withPath("/employees/1"))
				.thenRespond(response().withStatusCode(204));
		return deleteExpectation;
	}

	private Expectation createPUTExpectation() {
		Expectation putExpectation = null;
		try {
			final Employee expectedEmployee = Employee.builder().empId(1).empName("empName1").address("address1")
					.build();
			final String expectedEmpString = jsonParser.write(expectedEmployee).getJson();

			putExpectation = new Expectation(request().withMethod("PUT").withPath("/employees/1")
					.withHeader("content-type", "application/json;charset=UTF-8").withBody(expectedEmpString))
							.thenRespond(response().withBody(expectedEmpString, MediaType.APPLICATION_JSON));
		} catch (final IOException exception) {

		}
		return putExpectation;
	}

	private Expectation createPOSTExpectation() {

		Expectation postExpectation = null;
		try {
			final Employee testEmployee = Employee.builder().empName("empName1").address("address1").build();
			final String testString = jsonParser.write(testEmployee).getJson();

			final Employee expectedEmployee = Employee.builder().empId(1).empName("empName1").address("address1")
					.build();
			final String expectedString = jsonParser.write(expectedEmployee).getJson();
			final Link link = linkTo(methodOn(EmployeeResourceFeignWrapper.class).findAllEmployee())
					.withRel("all-employees");
			final Resource<Employee> resource = new Resource<Employee>(expectedEmployee);
			resource.add(link);

			postExpectation = new Expectation(
					request().withMethod("POST").withPath("/employees").withHeader("content-type", "application/json")
							.withHeader("accept", "application/json").withBody(testString))
									.thenRespond(response().withBody(expectedString, MediaType.APPLICATION_JSON)
											.withStatusCode(201));
		} catch (final IOException exception) {
			exception.printStackTrace();
		}
		return postExpectation;
	}

	private Expectation createGETExpectation() {
		final Employee employee = Employee.builder().empId(1).empName("empName1").address("address1").build();
		String body = null;
		try {
			body = jsonParser.write(employee).getJson();
		} catch (final IOException exception) {
			exception.printStackTrace();
		}
		final Expectation getExpectation = new Expectation(request().withMethod("GET").withPath("/employees/1"))
				.thenRespond(response().withBody(body, MediaType.APPLICATION_JSON).withStatusCode(200));
		return getExpectation;
	}

}
