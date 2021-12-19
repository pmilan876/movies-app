package com.movies.api.jwt;

import java.util.ArrayList;

import com.movies.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			return new User(username, "",
					new ArrayList<>());
	}

	public com.movies.db.entity.User  getUserByUsername(String username) throws UsernameNotFoundException {
		return userService.getByUserName(username);

	}
}