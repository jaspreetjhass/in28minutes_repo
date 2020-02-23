package com.example.demo11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.configurations.ApplicationConfig;
import com.example.demo.models.Employee;
import com.example.demo.resources.EmployeeResourceFeignWrapper;
import com.example.demo.services.EmployeeService;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ApplicationConfig.class })
@EnableWebMvc
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MockitoDemoApplicationTests {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployeeResourceFeignWrapper employeeResource;
	@Autowired
	private JacksonTester<Employee> json;
	@Autowired
	private RestTemplate restTemplate;

	private ClientAndServer clientAndServer;

	// private MockRestServiceServer server;
	/*
	 * @Autowired private TestRestTemplate testRest;
	 */
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void testBefore() {
		// server = MockRestServiceServer.createServer(restTemplate);
		clientAndServer = startClientAndServer(8080);
	}

	@AfterEach
	public void testAfter() {
		// server = MockRestServiceServer.createServer(restTemplate);
		clientAndServer.stop();
	}
	/*
	 * @Test void contextLoads() { Employee employee =
	 * Employee.builder().empId(1).empName("empName1").address("address1").build();
	 * when(employeeService.findEmployeeById(1)).thenReturn(employee);
	 * ResponseEntity<Employee> response = testRest.getForEntity(
	 * "/employees/{empId}", Employee.class, new Object[] { "1" });
	 * assertNotNull(response.getBody()); }
	 */

	@Test
	@Disabled
	void contextLoads() throws Exception {
		Employee employee = Employee.builder().empId(1).empName("empName1").address("address1").build();
		// when(employeeService.findEmployeeById(1)).thenReturn(employee);
		/*
		 * RequestBuilder resquestBuider =
		 * MockMvcRequestBuilders.get("/employees/{empid}", new Object[] {1}); MvcResult
		 * result = mockMvc.perform(resquestBuider).andReturn(); String output =
		 * result.getResponse().getContentAsString(); Employee empOut =
		 * json.parseObject(output); String a = json.write(employee).getJson();
		 * System.out.println(a);
		 */
		/*
		 * String body = json.write(employee).getJson(); server.expect(times(1),
		 * requestTo("http://localhost:8080/employees/1")).andRespond(withSuccess(body,
		 * MediaType.APPLICATION_JSON));
		 */
		// Employee empOut = employeeResource.findUserById(1);
		// server.verify();
		// assertEquals(employee.getEmpName(), empOut.getEmpName());
	}

	@Test
	void contextLoadsDup() throws Exception {
		Employee employee = Employee.builder().empId(1).empName("empName1").address("address1").build();
		// when(employeeService.findEmployeeById(1)).thenReturn(employee);
		String body = json.write(employee).getJson();
		clientAndServer
				.when(request().withMethod("GET").withPath("/employees/1").withHeader("accept", "application/json"))
				.respond(response().withBody(body, MediaType.APPLICATION_JSON));

		RequestBuilder resquestBuider = MockMvcRequestBuilders
				.get("http://localhost:8080/employees/{empid}", new Object[] { 1 })
				.accept(org.springframework.http.MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(resquestBuider).andReturn();
		String output = result.getResponse().getContentAsString();
		Employee empOut = json.parseObject(output);
		// Employee empOut = employeeResource.findUserById(1);
		// server.verify();
		assertEquals(employee.getEmpName(), empOut.getEmpName());
	}

	@Test
	void contextLoadsDupRest() throws Exception {
		Employee employee = Employee.builder().empId(1).empName("empName1").address("address1").build();
		// when(employeeService.findEmployeeById(1)).thenReturn(employee);
		String body = json.write(employee).getJson();
		clientAndServer
				.when(request().withMethod("GET").withPath("/employees/1").withHeader("accept", "application/json"))
				.respond(response().withBody(body, MediaType.APPLICATION_JSON));
		/*
		 * ResponseEntity<Employee> empOut =
		 * restTemplate.getForEntity("http://localhost:8080/employees/1",
		 * Employee.class, map);
		 */
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(org.springframework.http.MediaType.APPLICATION_JSON));
		HttpEntity entity = new HttpEntity<>(headers);
		ResponseEntity<Employee> empOut = restTemplate.exchange("http://localhost:8080/employees/{empId}", HttpMethod.GET, entity, Employee.class,
				new Object[] { "1" });
		assertEquals(employee.getEmpName(), empOut.getBody().getEmpName());
	}

	@Test
	@Disabled
	void testExecute() throws IOException {
		Employee employee = Employee.builder().empId(1).empName("empName1").address("address1").build();
		String body = json.write(employee).getJson();
		clientAndServer
				.when(request().withMethod("GET").withPath("/employees/1").withHeader("accept", "application/json"))
				.respond(response().withBody(body, MediaType.APPLICATION_JSON));
		Employee empOut = employeeResource.findUserById(1);
		// server.verify();
		assertEquals(employee.getEmpName(), empOut.getEmpName());
	}
}
