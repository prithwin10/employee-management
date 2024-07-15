package com.learning.employeemanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.learning.employeemanagement.dto.CommonResponse;
import com.learning.employeemanagement.exception.EmployeeNotFoundException;
import com.learning.employeemanagement.model.Employee;
import com.learning.employeemanagement.repository.EmployeeRepository;

class EmployeeServiceImplTest {

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Mock
	private EmployeeRepository employeeRepository;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		Employee emp1 = new Employee();
		emp1.setId(1L);
		emp1.setFirstName("Rohan");
		emp1.setLastName("AN");

		Employee emp2 = new Employee();
		emp2.setId(2L);
		emp2.setFirstName("Rahul");
		emp2.setEmail("Rahul@gmail.com");

		employees.add(emp1);
		employees.add(emp2);

		when(employeeRepository.findAll()).thenReturn(employees);

		List<Employee> result = employeeService.getAllEmployees();
		assertThat(result).hasSize(2);
		assertThat(result.get(0).getFirstName()).isEqualTo("Rohan");
		assertThat(result.get(0).getLastName()).isEqualTo("AN");
		assertThat(result.get(1).getFirstName()).isEqualTo("Rahul");
		assertThat(result.get(1).getEmail()).isEqualTo("Rahul@gmail.com");
	}

	@Test
	void testSaveEmployee() {
		Employee emp = new Employee();
		emp.setFirstName("Navin");

		when(employeeRepository.save(any(Employee.class))).thenReturn(emp);

		Employee result = employeeService.saveEmployee(emp);
		assertNotNull(result.getId());
		assertThat(result.getFirstName()).isEqualTo("Navin");
	}

	@Test
	void testGetEmployeeById() {
		Employee emp = new Employee();
		emp.setId(1L);
		emp.setFirstName("Daya");

		when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));

		Employee result = employeeService.getEmployeeById(1L);
		assertThat(result.getFirstName()).isEqualTo("Daya");
	}

	@Test
     void testGetEmployeeByIdNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(1L));
    }

	@Test
	 void testDeleteEmployeeById() {
		when(employeeRepository.existsById(1L)).thenReturn(true);
		doNothing().when(employeeRepository).deleteById(1L);

		CommonResponse response = employeeService.deleteEmployeeById(1L);
		assertThat(response.getStatus())
				.isEqualTo("Employee deleted successfully");
		
		when(employeeRepository.existsById(2L)).thenReturn(false);

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployeeById(2L));
	}

	@Test
	void testFindPaginated() {
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee());
		Page<Employee> page = new PageImpl<>(employees);

		when(employeeRepository.findAll(any(Pageable.class))).thenReturn(page);

		Page<Employee> result = employeeService.findPaginated(1, 5, "firstName",
				"asc");
		assertThat(result.getContent()).hasSize(1);

		result = employeeService.findPaginated(1, 5, "firstName", "DESC");
		assertThat(result.getContent()).hasSize(1);
	}
}
