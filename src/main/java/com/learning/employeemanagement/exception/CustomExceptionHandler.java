package com.learning.employeemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(CustomUsernameNotFoundException.class)
	protected ResponseEntity<Object> handleCustomUsernameNotFoundException(
			CustomUsernameNotFoundException ex) {
		ErrorWrapper error = new ErrorWrapper(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	protected ResponseEntity<Object> handleEmployeeNotFoundException(
			EmployeeNotFoundException ex) {
		ErrorWrapper error = new ErrorWrapper(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(DuplicateEmailFoundException.class)
	protected ResponseEntity<Object> handleDuplicateEmailFoundException(
			DuplicateEmailFoundException ex) {
		ErrorWrapper error = new ErrorWrapper(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
