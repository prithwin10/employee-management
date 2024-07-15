package com.learning.employeemanagement.exception;

import lombok.Getter;

@Getter
public class ErrorWrapper {

	private String message;

	public ErrorWrapper(String message) {
		this.message = message;
	}

}
