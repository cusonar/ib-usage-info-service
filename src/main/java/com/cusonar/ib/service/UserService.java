package com.cusonar.ib.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService extends JdbcUserDetailsManager {
	
	@Autowired
	public UserService(DataSource dataSource) {
		setDataSource(dataSource);
	}
}
