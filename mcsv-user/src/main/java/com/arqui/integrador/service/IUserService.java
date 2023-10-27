package com.arqui.integrador.service;

import java.util.List;

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.dto.UserDto;

public interface IUserService {

	List<UserDto> getAll();

	UserDto getById(Long id);

	UserDto create(UserDto user);

	UserDto update(Long id, UserDto user);
	
	UserDto addAccount(Long userId, AccountDto account);

	void delete(Long id);

	void deleteAccount(Long userId, AccountDto account);
}
