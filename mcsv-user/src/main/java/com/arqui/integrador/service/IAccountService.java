package com.arqui.integrador.service;

import java.util.List;

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.dto.UserDto;

public interface IAccountService {
	
	List<AccountDto> getAll();
	
	AccountDto getById(Long id);
	
	AccountDto create(AccountDto accountDto);
	
	AccountDto update(Long id, AccountDto accountDto);
	
	AccountDto addUser(Long id, UserDto userDto);
	
	void delete(Long id);
	
	void deleteUser(Long id, UserDto userDto);
}
