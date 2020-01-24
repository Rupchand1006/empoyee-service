package com.dell.emc.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dell.emc.model.Employee;
import com.dell.emc.service.EmployeeService;

//@RestController -> @Service -> @Repository ->  EntityManager
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService empService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		// 
		logger.info("Entering into addEmployee service");
		empService.save(employee);
		if (logger.isDebugEnabled()) {
			logger.debug("Employee added successfully : " + employee);
		}
		logger.info("Exiting from addEmployee service");
		return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateEmployee(@RequestBody Employee employee) {

		logger.info("Entering into updateEmployee service");

		Optional<Employee> existingEmp = empService.getById(employee.getId());

		if (!existingEmp.isPresent()) {
			logger.debug("Employee with id " + employee.getId() + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			empService.save(employee);
			logger.info("Exiting from updateEmployee service");
			return new ResponseEntity<Void>(HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Optional<Employee>> getEmployee(@PathVariable("id") Long id) {

		Optional<Employee> employee = empService.getById(id);
		  // OR
		//Employee employee = empService.loadOne(id);

		if (!employee.isPresent()) {
			logger.debug("Employee with id " + id + " does not exists");
			return new ResponseEntity<Optional<Employee>>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found Employee : " + employee);
		return new ResponseEntity<Optional<Employee>>(employee, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = empService.getAll();
		if (employees.isEmpty()) {
			logger.debug("Employee(s) does not exists");
			return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Number of Employees found : " + employees.size() + " ");
			logger.debug(Arrays.toString(employees.toArray()));
		}
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
		Optional<Employee> employee = empService.getById(id);
		if (!employee.isPresent()) {
			logger.debug("Employee with id " + id + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			empService.delete(id);
			logger.debug("Employee with id " + id + " deleted");
			return new ResponseEntity<Void>(HttpStatus.GONE);
		}
	}
}
