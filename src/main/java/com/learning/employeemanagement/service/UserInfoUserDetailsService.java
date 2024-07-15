package com.learning.employeemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.learning.employeemanagement.exception.CustomUsernameNotFoundException;
import com.learning.employeemanagement.model.User;
import com.learning.employeemanagement.model.UserInfoUserDetails;
import com.learning.employeemanagement.repository.UserRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userRepository.findByName(username);
		if (user != null) {
			return new UserInfoUserDetails(user);
		} else {
			throw new CustomUsernameNotFoundException(
					"User Not Found with " + username);
		}
	}

}
