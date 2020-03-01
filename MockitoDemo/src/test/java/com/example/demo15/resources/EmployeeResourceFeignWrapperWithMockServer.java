package com.example.demo15.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.util.List;

import org.junit.AfterClass;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.hateoas.Resource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.models.Employee;
import com.example.demo15.SpringBootTestApp;

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
	@Autowired
	private ClientAndServer clientAndServer;

	@AfterClass
	public void afterTest() {
		clientAndServer.stop();
	}

	@Test
	public void findEmployeeById() throws Exception {
		final Employee employee = Employee.builder().empId(1).empName("empName1").address("address1").build();

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

		final RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/feignWrapper/employees/1")
				.content(expectedEmpString).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.accept(org.springframework.http.MediaType.APPLICATION_JSON);

		final MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		final String actualString = mvcResult.getResponse().getContentAsString();

		final Employee actualEmployee = jsonParser.parse(actualString).getObject();

		assertEquals(expectedEmployee.getEmpName(), actualEmployee.getEmpName());

	}

	@Test
	public void deleteEmployee() throws Exception {
		clientAndServer.when(request().withMethod("DELETE").withPath("/employees/1"))
				.respond(response().withStatusCode(204));

		final RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/feignWrapper/employees/1");
		mockMvc.perform(requestBuilder);

		clientAndServer.verify(request().withMethod("DELETE").withPath("/employees/1"), VerificationTimes.once());
	}

}