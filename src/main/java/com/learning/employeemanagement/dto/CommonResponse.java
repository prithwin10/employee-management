package com.learning.employeemanagement.dto;

import lombok.Getter;

@Getter
public class CommonResponse {

	private Long id;
	private String status;

	public CommonResponse(Long id, String status) {
		super();
		this.id = id;
		this.status = status;
	}

}
