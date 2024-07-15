package com.learning.employeemanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.learning.employeemanagement.dto.CommonResponse;
import com.learning.employeemanagement.model.Employee;

public interface EmployeeService {

	List<Employee> getAllEmployees();

	Employee saveEmployee(Employee employee);

	Employee getEmployeeById(long id);

	CommonResponse deleteEmployeeById(long id);

	Page<Employee> findPaginated(int pageNo, int pageSize, String sortField,
			String sortDirection);
}
