package com.learning.employeemanagement.exception;

public class CustomUsernameNotFoundException extends RuntimeException {

	public CustomUsernameNotFoundException(String message) {
		super(message);
	}
}
