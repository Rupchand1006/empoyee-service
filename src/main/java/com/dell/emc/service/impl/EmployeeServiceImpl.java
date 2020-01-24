package com.dell.emc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dell.emc.model.Employee;
import com.dell.emc.repository.EmployeeRepository;
import com.dell.emc.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee save(Employee entity) {
		return employeeRepository.save(entity);
	}

	@Override
	public Optional<Employee> getById(Long id) {
		//return employeeRepository.getOne(id);
		return employeeRepository.findById(id);
	}

	@Override
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		employeeRepository.deleteById(id);
	}

}
