package com.example.demo.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.example.demo.models.Employee;

@DataJpaTest(properties = {"spring.datasource.initialization-mode=NEVER"})
@Sql(value = {"EmployeeRepositoryTest.sql"})
public class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void findEmployeeById() {
		final Optional<Employee> optional = employeeRepository.findById(1);
		assertNotNull(optional.get());
		assertEquals(optional.get().getEmpName(), "empName1");
	}

	@Test
	public void findAllEmployee() {
		final List<Employee> list = new ArrayList<>();
		employeeRepository.findAll().forEach(list::add);
		assertEquals(5, list.size());
	}

	@Test
	public void addEmployee() {
		Employee employee = Employee.builder().empId(6).empName("empName6").address("address6").build();
		final Employee outputEmployee = employeeRepository.save(employee);
		assertEquals(employee.getAddress(), outputEmployee.getAddress());
	}

	@Test
	public void updateEmployee() {
		final Optional<Employee> optional = employeeRepository.findById(1);
		final Employee employee = Employee.builder().empName("updateName").address("updateAddress").build();
		final Employee outputEmployee = optional.get();
		outputEmployee.setEmpName(employee.getEmpName());
		outputEmployee.setAddress(employee.getAddress());
		final Employee output = employeeRepository.save(outputEmployee);
		assertEquals(output.getAddress(), outputEmployee.getAddress());
	}

	@Test
	public void deleteEmployee() {
		employeeRepository.deleteById(1);
	}
}
