package com.learning.employeemanagement.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.learning.employeemanagement.model.Role;
import com.learning.employeemanagement.model.User;

public class UserMapper implements ResultSetExtractor<User> {

	@Override
	public User extractData(ResultSet rs)
			throws SQLException, DataAccessException {
		User user = null;
		while (rs.next()) {
			user = new User(rs.getString("name"), rs.getString("email"),
					rs.getString("password"),
					(List<Role>) rs.getObject("roles"));
		}
		return user;
	}

}
