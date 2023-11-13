package com.arqui.integrador.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.arqui.integrador.dto.UserAuthRequestDto;
import com.arqui.integrador.model.UserAuth;
import com.arqui.integrador.repository.IAuthRepository;

@Service
public class AuthService implements UserDetailsService{
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);
	
	private IAuthRepository authRepository;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public AuthService(IAuthRepository authRepository) {
		this.authRepository = authRepository;
		this.bCryptPasswordEncoder =  new BCryptPasswordEncoder();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return authRepository.findByUsername(username)
				.map(user -> new User(user.getUsername(), user.getPassword(), new ArrayList<>()))
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
	}
	
	public void register(UserAuthRequestDto user) {
		String bCryptedPassword = encodePassword(user.getPassword());
		
		this.authRepository.save(UserAuth.builder()
				.username(user.getUsername())
				.password(bCryptedPassword)
				.build());
		LOG.info("User registered: {}", user.getUsername());
	}
	
	private String encodePassword(String password) {
		
		return bCryptPasswordEncoder.encode(password);
	}

}
