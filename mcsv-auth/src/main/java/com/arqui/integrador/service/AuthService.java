package com.arqui.integrador.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.arqui.integrador.dto.TokenDto;
import com.arqui.integrador.dto.UserAuthRequestDto;
import com.arqui.integrador.model.UserAuth;
import com.arqui.integrador.repository.IAuthRepository;

@Service
public class AuthService implements IAuthService{
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);
	
	private IAuthRepository authRepository;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public AuthService(IAuthRepository authRepository) {
		this.authRepository = authRepository;
		this.bCryptPasswordEncoder =  new BCryptPasswordEncoder();
	}
	
	@Override
	public void register(UserAuthRequestDto user) {
		String bCryptedPassword = encodePassword(user.getPassword());
		
		this.authRepository.save(UserAuth.builder()
				.username(user.getUsername())
				.password(bCryptedPassword)
				.build());
		LOG.info("User registered: {}", user.getUsername());
	}

	@Override
	public TokenDto login(UserAuthRequestDto userDto) {
		UserAuth user = this.authRepository.findByUsername(userDto.getUsername()).orElseThrow(
				() -> new RuntimeException("User not found."));
		if(validatePassword(userDto.getPassword(), user.getPassword()))
			LOG.info("Logged in");
		LOG.error("Login failed");
		return new TokenDto("bearer token");
	}
	
	private String encodePassword(String password) {
		
		return bCryptPasswordEncoder.encode(password);
	}
	
	private boolean validatePassword(String password, String encodedPassword) {
		return bCryptPasswordEncoder.matches(password, encodedPassword);
	}
}
