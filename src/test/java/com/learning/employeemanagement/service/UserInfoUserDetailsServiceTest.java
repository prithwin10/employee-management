package com.learning.employeemanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import com.learning.employeemanagement.exception.CustomUsernameNotFoundException;
import com.learning.employeemanagement.model.Role;
import com.learning.employeemanagement.model.User;
import com.learning.employeemanagement.repository.UserRepository;

class UserInfoUserDetailsServiceTest {
	@InjectMocks
	private UserInfoUserDetailsService userDetailsService;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testLoadUserByUsername_UserFound() {
		User user = new User();
		user.setId(1L);
		user.setName("Tej");
		user.setPassword("password");
		user.setEmail("tej@hotmail.com");
		user.setRoles(List.of(new Role("ROLE_USER")));

		when(userRepository.findByName(anyString())).thenReturn(user);

		UserDetails userDetails = userDetailsService
				.loadUserByUsername("Tej");
		assertThat(userDetails).isNotNull();
		assertThat(userDetails.getUsername()).isEqualTo("Tej");
		assertNotNull(userDetails.getAuthorities());
		assertNotNull(userDetails.getPassword());
	}

	@Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByName(anyString())).thenReturn(null);

        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("invalidUser")).isInstanceOf(CustomUsernameNotFoundException.class);
    }
}
