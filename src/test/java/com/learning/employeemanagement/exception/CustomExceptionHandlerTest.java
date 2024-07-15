package com.learning.employeemanagement.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class CustomExceptionHandlerTest {

	@InjectMocks
	private CustomExceptionHandler customExceptionHandler;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testHandleCustomUsernameNotFoundException() {
		CustomUsernameNotFoundException ex = new CustomUsernameNotFoundException(
				"User not found");

		ResponseEntity<Object> response = customExceptionHandler
				.handleCustomUsernameNotFoundException(ex);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isInstanceOf(ErrorWrapper.class);
		ErrorWrapper errorWrapper = (ErrorWrapper) response.getBody();
		assertThat(errorWrapper.getMessage()).isEqualTo("User not found");
	}

	@Test
	void testHandleEmployeeNotFoundException() {
		EmployeeNotFoundException ex = new EmployeeNotFoundException(
				"Employee not found");

		ResponseEntity<Object> response = customExceptionHandler
				.handleEmployeeNotFoundException(ex);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isInstanceOf(ErrorWrapper.class);
		ErrorWrapper errorWrapper = (ErrorWrapper) response.getBody();
		assertThat(errorWrapper.getMessage()).isEqualTo("Employee not found");
	}

	@Test
	void testHandleDuplicateEmailFoundException() {
		DuplicateEmailFoundException ex = new DuplicateEmailFoundException(
				"Duplicate email found");

		ResponseEntity<Object> response = customExceptionHandler
				.handleDuplicateEmailFoundException(ex);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).isInstanceOf(ErrorWrapper.class);
		ErrorWrapper errorWrapper = (ErrorWrapper) response.getBody();
		assertThat(errorWrapper.getMessage())
				.isEqualTo("Duplicate email found");
	}

}
