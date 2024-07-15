package com.learning.employeemanagement.exception;

public class DuplicateEmailFoundException extends Exception {

	public DuplicateEmailFoundException(String message) {
		super(message);
	}
}
