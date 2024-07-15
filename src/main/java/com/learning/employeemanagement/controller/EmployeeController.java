package com.learning.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.employeemanagement.dto.CommonResponse;
import com.learning.employeemanagement.model.Employee;
import com.learning.employeemanagement.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/getAll")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Employee>> viewHomePage() {
		Page<Employee> page = employeeService.findPaginated(1, 5, "firstName",
				"asc");
		return ResponseEntity.ok(page.getContent());
	}

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Employee> saveEmployee(
			@RequestBody Employee employeeRequest) {
		Employee employee = employeeService.saveEmployee(employeeRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(employee);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Employee> getEmployee(
			@PathVariable(value = "id") long id) {
		return ResponseEntity.ok(employeeService.getEmployeeById(id));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<CommonResponse> deleteEmployee(
			@PathVariable(value = "id") long id) {
		CommonResponse resp = employeeService.deleteEmployeeById(id);
		return ResponseEntity.ok(resp);
	}
}
