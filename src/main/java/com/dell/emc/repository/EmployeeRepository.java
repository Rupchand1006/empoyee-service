package com.dell.emc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dell.emc.model.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
