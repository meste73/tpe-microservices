package com.arqui.integrador.service;

import com.arqui.integrador.dto.TokenDto;
import com.arqui.integrador.dto.UserAuthRequestDto;

public interface IAuthService {
	
	void register(UserAuthRequestDto user);
	
	TokenDto login(UserAuthRequestDto user);
}
