package com.learning.employeemanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.learning.employeemanagement.dto.CommonResponse;
import com.learning.employeemanagement.dto.UserRegistrationDto;
import com.learning.employeemanagement.model.Role;
import com.learning.employeemanagement.model.User;
import com.learning.employeemanagement.service.UserService;

class UserControllerTest {
	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddUser() throws Exception {
		User user = new User("Rohit", "rohit@gmail.com", "pwd",
				List.of(new Role("ROLE_ADMIN")));

		UserRegistrationDto userRequest = new UserRegistrationDto();
		userRequest.setName("Rohit");
		userRequest.setPassword("pwd");
		userRequest.setEmail("rohit@gmail.com");
		userRequest.setRoles(List.of("ROLE_ADMIN"));

		when(userService.saveUser(any(UserRegistrationDto.class)))
				.thenReturn(user);

		ResponseEntity<User> response = userController.addUser(userRequest);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void testGetAllUsers() throws Exception {
		List<User> users = new ArrayList<>();
		User user1 = new User("Rohit", "rohit@gmail.com", "pwd",
				List.of(new Role("ROLE_ADMIN")));

		User user2 = new User("Virat", "virat@gmail.com", "pwd",
				List.of(new Role("ROLE_USER")));

		users.add(user1);
		users.add(user2);

		when(userService.getAll()).thenReturn(users);

		ResponseEntity<List<User>> response = userController.getAllUser();
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(2, response.getBody().size());
	}

	@Test
	void testDeleteUser() {
		CommonResponse response = new CommonResponse(1L,
				"User deleted successfully");

		when(userService.deleteUser(1L)).thenReturn(response);

		ResponseEntity<CommonResponse> result = userController.deleteUser(1L);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals("User deleted successfully", result.getBody().getStatus());
	}

}
