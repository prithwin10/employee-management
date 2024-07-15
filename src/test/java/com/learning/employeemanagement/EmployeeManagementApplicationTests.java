package com.learning.employeemanagement;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class EmployeeManagementApplicationTests {
	@Test
	void contextLoads() {
	}

	@Test
	void testMainMethod() {
		assertDoesNotThrow(
				() -> EmployeeManagementApplication.main(new String[] {}));
	}
}
