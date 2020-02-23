package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.ApplicationException;
import com.example.demo.models.Employee;
import com.example.demo.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

	public Employee findEmployeeById(final Integer empId) {
		LOGGER.trace("Enter into method");
		LOGGER.info("fetch employee having id " + empId);
		final Optional<Employee> optional = employeeRepository.findById(empId);
		if (!optional.isPresent()) {
			LOGGER.error("employee not found");
			throw new ApplicationException("employee not found");
		}
		LOGGER.trace("Exit from method");
		return optional.get();
	}

	public List<Employee> findAllEmployee() {
		LOGGER.trace("Enter into method");
		final List<Employee> list = new ArrayList<>();
		LOGGER.info("fetching employees from database");
		employeeRepository.findAll().forEach(list::add);
		LOGGER.trace("Exit from method");
		return list;
	}

	public Employee addEmployee(final Employee employee) {
		LOGGER.trace("Enter into method");
		LOGGER.info("saving employee in database");
		final Employee outputUser = employeeRepository.save(employee);
		LOGGER.trace("Exit from method");
		return outputUser;
	}

	public Employee updateEmployee(final Integer empId, final Employee employee) {
		LOGGER.trace("Enter into method");
		LOGGER.info("fetch employee with id " + empId);
		final Optional<Employee> optional = employeeRepository.findById(empId);
		if (!optional.isPresent()) {
			LOGGER.error("employee not found");
			throw new ApplicationException("employee not found");
		}
		LOGGER.info("updating employee in the database");
		final Employee outputEmployee = optional.get();
		outputEmployee.setEmpName(employee.getEmpName());
		outputEmployee.setAddress(employee.getAddress());
		employeeRepository.save(outputEmployee);
		LOGGER.trace("Exit from method");
		return outputEmployee;
	}

	public void deleteEmployee(final Integer employeeId) {
		LOGGER.trace("Enter into method");
		LOGGER.info("delete employee from database");
		employeeRepository.deleteById(employeeId);
		LOGGER.trace("Exit from method");
	}
}
