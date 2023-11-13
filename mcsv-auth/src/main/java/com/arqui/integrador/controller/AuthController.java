package com.arqui.integrador.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.arqui.integrador.dto.TokenDto;
import com.arqui.integrador.dto.UserAuthRequestDto;
import com.arqui.integrador.security.JwtTokenManager;
import com.arqui.integrador.service.AuthService;

import jakarta.validation.Valid;

@RestController
public class AuthController implements IAuthController{
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
	
	private AuthenticationManager authenticationManager;
	
	private JwtTokenManager jwtTokenManager;
	
	private AuthService authService;
	
	public AuthController(AuthenticationManager authenticationManager, AuthService authService, JwtTokenManager jwtTokenManager) {
		this.authenticationManager = authenticationManager;
		this.authService = authService;
		this.jwtTokenManager = jwtTokenManager;
	}
	
	@Override
	public ResponseEntity<TokenDto> login(UserAuthRequestDto user) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			
		} catch (DisabledException e) {
			throw new DisabledException("User with username: " + user.getUsername() + " is disabled");
			
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Username or password is incorrect");
		}
		
		final UserDetails userDetails = authService.loadUserByUsername(user.getUsername());
		
		final String jwtToken = jwtTokenManager.generateJwtToken(userDetails);
		
		return ResponseEntity.ok(TokenDto.builder().token(jwtToken).build());
	}

	@Override
	public ResponseEntity<HttpStatus> register(@Valid UserAuthRequestDto user) {
		LOG.info("Register user: {}", user.getUsername());
		
		this.authService.register(user);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
