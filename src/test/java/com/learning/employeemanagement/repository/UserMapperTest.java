package com.learning.employeemanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import com.learning.employeemanagement.model.Role;
import com.learning.employeemanagement.model.User;

public class UserMapperTest {
	@InjectMocks
	private UserMapper userMapper;

	@Mock
	private ResultSet resultSet;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
    void testExtractData() throws SQLException, DataAccessException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("name")).thenReturn("Varun");
        when(resultSet.getString("email")).thenReturn("varun@yahoo.com");
        when(resultSet.getString("password")).thenReturn("password");
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        when(resultSet.getObject("roles")).thenReturn(List.of(role));

        User user = userMapper.extractData(resultSet);

        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("Varun");
        assertThat(user.getEmail()).isEqualTo("varun@yahoo.com");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getRoles()).hasSize(1);
        assertThat(user.getRoles().get(0).getName()).isEqualTo("ROLE_USER");
        assertNotNull(user.getRoles().get(0).getId());
    }

	@Test
    void testExtractData_EmptyResultSet() throws SQLException, DataAccessException {
        when(resultSet.next()).thenReturn(false);

        User user = userMapper.extractData(resultSet);

        assertThat(user).isNull();

        verify(resultSet, times(1)).next();
        verify(resultSet, never()).getString("name");
        verify(resultSet, never()).getString("email");
        verify(resultSet, never()).getString("password");
        verify(resultSet, never()).getObject("roles");
    }
}
