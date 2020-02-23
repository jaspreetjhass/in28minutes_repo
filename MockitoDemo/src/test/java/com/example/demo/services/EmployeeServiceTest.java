package com.example.demo.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;

import com.example.demo.models.Employee;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Service.class))
public class EmployeeServiceTest {

	@Autowired
	private EmployeeService employeeService;

	@Test
	public void findEmployeeById() {
		Employee employee = employeeService.findEmployeeById(1);
		assertEquals("empName1", employee.getEmpName());
	}

	@Test
	public void findAllEmployee() {
		List<Employee> employeeList = employeeService.findAllEmployee();
		assertThat(employeeList).hasSize(5);
	}

	@Test
	public void addEmployee() {
		Employee employee = Employee.builder().empId(6).empName("empName6").address("address6").build();
		Employee output = employeeService.addEmployee(employee);
		assertEquals(employee.getEmpName(), output.getEmpName());
	}

	@Test
	public void updateEmployee() {
		Employee employee = Employee.builder().empName("empName6").address("address6").build();
		Employee output = employeeService.updateEmployee(1, employee);
		assertEquals(employee.getEmpName(), output.getEmpName());
	}

	@Test
	public void deleteEmployee() {
		List<Employee> employeeList = employeeService.findAllEmployee();
		employeeService.deleteEmployee(1);
		assertThat(employeeList).hasSize(5);
	}
}
