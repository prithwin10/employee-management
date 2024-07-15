package com.learning.employeemanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learning.employeemanagement.dto.CommonResponse;
import com.learning.employeemanagement.dto.UserRegistrationDto;
import com.learning.employeemanagement.exception.DuplicateEmailFoundException;
import com.learning.employeemanagement.model.Role;
import com.learning.employeemanagement.model.User;
import com.learning.employeemanagement.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User saveUser(UserRegistrationDto registrationDto)
			throws DuplicateEmailFoundException {

		User user = new User(registrationDto.getName(),
				registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()),
				registrationDto.getRoles().stream().map(Role::new).toList());
		User savedUser = new User();
		try {
			savedUser = userRepository.save(user);
		} catch (Exception e) {
			throw new DuplicateEmailFoundException(e.getMessage());
		}

		return new User(savedUser.getId(), savedUser.getName(),
				savedUser.getEmail(), savedUser.getRoles());
	}

	public CommonResponse deleteUser(Long id) {
		userRepository.deleteById(id);
		return new CommonResponse(id, "User deleted successfully");
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

}
