package com.learning.employeemanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.learning.employeemanagement.dto.CommonResponse;
import com.learning.employeemanagement.model.Employee;
import com.learning.employeemanagement.service.EmployeeService;

class EmployeeControllerTest {

	@InjectMocks
	private EmployeeController employeeController;

	@Mock
	private EmployeeService employeeService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testViewHomePage() {
		List<Employee> employees = new ArrayList<>();
		Employee emp1 = new Employee();
		emp1.setId(1L);
		emp1.setFirstName("Rahul");

		Employee emp2 = new Employee();
		emp2.setId(2L);
		emp2.setFirstName("Rohan");

		employees.add(emp1);
		employees.add(emp2);

		Page<Employee> page = new PageImpl<>(employees,
				PageRequest.of(1, 5, Sort.by("firstName").ascending()),
				employees.size());

		when(employeeService.findPaginated(1, 5, "firstName", "asc"))
				.thenReturn(page);

		ResponseEntity<List<Employee>> response = employeeController
				.viewHomePage();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
		assertEquals("Rahul", response.getBody().get(0).getFirstName());
		assertEquals("Rohan", response.getBody().get(1).getFirstName());
	}

	@Test
	void testSaveEmployee() {
		Employee empRequest = new Employee();
		empRequest.setFirstName("John");

		Employee emp = new Employee();
		emp.setId(1L);
		emp.setFirstName("John");

		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(emp);

		ResponseEntity<Employee> response = employeeController
				.saveEmployee(empRequest);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("John", response.getBody().getFirstName());
	}

	@Test
	void testgetEmployee() {
		Employee emp = new Employee();
		emp.setId(1L);
		emp.setFirstName("Rahul");

		when(employeeService.getEmployeeById(1L)).thenReturn(emp);

		ResponseEntity<Employee> response = employeeController.getEmployee(1);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Rahul", response.getBody().getFirstName());
	}

	@Test
	void testDeleteEmployee() {
		CommonResponse response = new CommonResponse(1L,
				"Employee deleted successfully");

		when(employeeService.deleteEmployeeById(1L)).thenReturn(response);

		ResponseEntity<CommonResponse> result = employeeController
				.deleteEmployee(1L);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals("Employee deleted successfully",
				result.getBody().getStatus());
	}
}
