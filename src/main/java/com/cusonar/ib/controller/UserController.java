package com.cusonar.ib.controller;

import java.security.Principal;
import java.util.Arrays;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cusonar.ib.core.security.JwtTokenUtil;
import com.cusonar.ib.domain.AuthenticationRequest;
import com.cusonar.ib.domain.SignupRequest;
import com.cusonar.ib.response.JwtTokenResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final JdbcUserDetailsManager userDetailsManager;
	private final PasswordEncoder passwordEncoder; 
	private final AuthenticationManager am;
	private final JwtTokenUtil jwtTokenUtil;
	
	@PostMapping("signup")
	public JwtTokenResponse signup(@RequestBody SignupRequest request) {
		User user = new User(
				request.getUsername(), passwordEncoder.encode(request.getPassword()), 
				Arrays.asList(new SimpleGrantedAuthority("USER")));
		userDetailsManager.createUser(user);
		
		return signIn(new AuthenticationRequest(request.getUsername(), request.getPassword()));
	}
	
	@PostMapping("signin")
	public JwtTokenResponse signIn(@RequestBody AuthenticationRequest request) {
		am.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		UserDetails userDetails = userDetailsManager
				.loadUserByUsername(request.getUsername());
		String token = jwtTokenUtil.generateToken(userDetails);
		return new JwtTokenResponse(token);
	}
	
	@GetMapping("refresh")
	public JwtTokenResponse refreshToken(Principal principal) {
		UserDetails userDetails = userDetailsManager
				.loadUserByUsername(principal.getName());
		String token = jwtTokenUtil.generateToken(userDetails);
		return new JwtTokenResponse(token);
	}
}
