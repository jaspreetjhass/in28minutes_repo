package com.example.demo.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import com.example.demo.models.Employee;
import com.example.demo.services.EmployeeService;

@SpringJUnitWebConfig(classes = { EmployeeResource.class })
public class EmployeeResourceTestWithSpringJunitWebConfig {

	@MockBean
	private EmployeeService employeeService;

	@Autowired
	private EmployeeResource employeeResource;

	@Test
	public void findEmployeeById() throws Exception {

		Employee expectedEmployee = Employee.builder().empId(1).empName("empName1").address("address1").build();

		when(employeeService.findEmployeeById(1)).thenReturn(expectedEmployee);

		Employee actualEmployee = employeeResource.findEmployeeById(1);
		assertEquals(expectedEmployee.getEmpName(), actualEmployee.getEmpName());
	}

	@Test
	public void findAllEmployee() throws Exception {

		Employee expectedEmployee = Employee.builder().empId(1).empName("empName1").address("address1").build();

		final List<Employee> expectedEmployeeList = Arrays.asList(expectedEmployee);

		when(employeeService.findAllEmployee()).thenReturn(expectedEmployeeList);

		List<Employee> actualEmployeeList = employeeResource.findAllEmployee();
		assertThat(actualEmployeeList).hasSize(1);
	}

	@Test
	public void addEmployee() throws Exception {

		Employee expectedEmployee = Employee.builder().empId(1).empName("empName1").address("address1").build();

		when(employeeService.addEmployee(any())).thenReturn(expectedEmployee);

		Employee actualEmployee = employeeResource.addEmployee(expectedEmployee).getContent();
		assertEquals(expectedEmployee.getEmpId(), actualEmployee.getEmpId());
	}

	@Test
	public void updateEmployee() throws Exception {

		Employee expectedEmployee = Employee.builder().empId(1).empName("empName1").address("address1").build();

		when(employeeService.updateEmployee(anyInt(), any())).thenReturn(expectedEmployee);

		Employee actualEmployee = employeeResource.updateEmployee(1, expectedEmployee);
		assertEquals(expectedEmployee.getEmpName(), actualEmployee.getEmpName());
	}

	@Test
	public void deleteEmployee() throws Exception {

		employeeResource.deleteEmployee(1);
		verify(employeeService, times(1)).deleteEmployee(1);

	}

}
