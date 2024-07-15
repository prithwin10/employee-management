package com.learning.employeemanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.learning.employeemanagement.dto.CommonResponse;
import com.learning.employeemanagement.exception.EmployeeNotFoundException;
import com.learning.employeemanagement.model.Employee;
import com.learning.employeemanagement.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);

	}

	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		Employee employee = null;
		if (optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new EmployeeNotFoundException(
					" Employee not found for id : " + id);
		}
		return employee;
	}

	@Override
	public CommonResponse deleteEmployeeById(long id) {
		if (employeeRepository.existsById(id)) {
			employeeRepository.deleteById(id);
			return new CommonResponse(id, "Employee deleted successfully");
		}
		throw new EmployeeNotFoundException(
				" Employee not found for id : " + id);
	}

	@Override
	public Page<Employee> findPaginated(int pageNo, int pageSize,
			String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
				? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.employeeRepository.findAll(pageable);
	}

}
