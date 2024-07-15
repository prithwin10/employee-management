package com.learning.employeemanagement.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.learning.employeemanagement.dto.CommonResponse;
import com.learning.employeemanagement.dto.UserRegistrationDto;
import com.learning.employeemanagement.exception.DuplicateEmailFoundException;
import com.learning.employeemanagement.model.Role;
import com.learning.employeemanagement.model.User;
import com.learning.employeemanagement.repository.UserRepository;

class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSaveUser_Success() throws DuplicateEmailFoundException {
		UserRegistrationDto registrationDto = new UserRegistrationDto();
		registrationDto.setName("Riya");
		registrationDto.setEmail("riya@yahoo.com");
		registrationDto.setPassword("password");
		registrationDto.setRoles(List.of("ROLE_USER"));

		when(userRepository.save(any(User.class))).thenReturn(new User(1L,
				"Riya", "riya@yahoo.com", List.of(new Role("ROLE_USER"))));
		when(passwordEncoder.encode(any(CharSequence.class)))
				.thenReturn("encodedPassword");

		User savedUser = userService.saveUser(registrationDto);
		assertEquals("Riya", savedUser.getName());
		assertEquals("riya@yahoo.com", savedUser.getEmail());
		assertEquals(1, savedUser.getId());
	}

	@Test
	void testSaveUser_DuplicateEmail() {
		UserRegistrationDto registrationDto = new UserRegistrationDto();
		registrationDto.setName("Dev");
		registrationDto.setEmail("dev@gmail.com");
		registrationDto.setPassword("password");
		registrationDto.setRoles(List.of("ROLE_ADMIN"));

		when(userRepository.save(any(User.class)))
				.thenThrow(new RuntimeException(
						"Duplicate entry 'dev@example.com' for key 'email_unique'"));

		assertThatThrownBy(() -> userService.saveUser(registrationDto))
				.isInstanceOf(DuplicateEmailFoundException.class);
	}

	@Test
	void testDeleteUser() {
		Long userId = 1L;

		CommonResponse expectedResponse = new CommonResponse(userId,
				"User deleted successfully");

		doNothing().when(userRepository).deleteById(userId);

		CommonResponse response = userService.deleteUser(userId);
		assertEquals(expectedResponse.getId(), response.getId());
		assertEquals(expectedResponse.getStatus(), response.getStatus());
	}

	@Test
	void testGetAllUsers() {
		List<User> users = new ArrayList<>();
		users.add(new User(1L, "Ganesh", "ganesh@gmail.com",
				List.of(new Role("ROLE_USER"))));
		users.add(new User(2L, "Gopi", "gopi@yahoo.com",
				List.of(new Role("ROLE_ADMIN"))));

		when(userRepository.findAll()).thenReturn(users);

		List<User> result = userService.getAll();
		assertEquals(2, result.size());
	}
}
