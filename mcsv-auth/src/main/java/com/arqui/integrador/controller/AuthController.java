package com.arqui.integrador.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.arqui.integrador.dto.TokenDto;
import com.arqui.integrador.dto.UserAuthRequestDto;
import com.arqui.integrador.service.IAuthService;

import jakarta.validation.Valid;

@RestController
public class AuthController implements IAuthController{
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
	
	private IAuthService authService;
	
	public AuthController(IAuthService authService) {
		this.authService = authService;
	}
	
	@Override
	public ResponseEntity<TokenDto> login(@Valid UserAuthRequestDto user) {
		LOG.info("Login user: {}", user.getUsername());
		
		TokenDto token = this.authService.login(user);
		
		return ResponseEntity.ok(token);
	}

	@Override
	public ResponseEntity<HttpStatus> register(@Valid UserAuthRequestDto user) {
		LOG.info("Register user: {}", user.getUsername());
		
		this.authService.register(user);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
