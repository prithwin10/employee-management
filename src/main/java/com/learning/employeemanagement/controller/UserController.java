package com.learning.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.employeemanagement.dto.CommonResponse;
import com.learning.employeemanagement.dto.UserRegistrationDto;
import com.learning.employeemanagement.model.User;
import com.learning.employeemanagement.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/registration")
	public ResponseEntity<User> addUser(
			@RequestBody UserRegistrationDto userRequest) throws Exception {
		User user = userService.saveUser(userRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> users = userService.getAll();
		return ResponseEntity.status(HttpStatus.CREATED).body(users);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CommonResponse> deleteUser(@PathVariable Long id) {
		CommonResponse resp = userService.deleteUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}
}
