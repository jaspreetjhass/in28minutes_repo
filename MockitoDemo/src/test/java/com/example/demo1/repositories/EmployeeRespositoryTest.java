package com.example.demo1.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.demo.models.Employee;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo1.SpringBootTestApp;

@SpringJUnitConfig(classes = { SpringBootTestApp.class })
//@SpringBootTest(classes = {SpringBootTestApp.class})
//@Transactional
@DataJpaTest
//@ContextConfiguration(classes = SpringBootTest.class)
public class EmployeeRespositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private TestEntityManager testEntityManager;

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
		Employee employee = Employee.builder().empId(null).empName("empName6").address("address6").build();
		final Employee outputEmployee = testEntityManager.persist(employee);
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
