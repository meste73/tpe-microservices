package com.arqui.integrador.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.dto.UserDto;
import com.arqui.integrador.exception.ItemNotFoundException;
import com.arqui.integrador.model.Account;
import com.arqui.integrador.model.User;
import com.arqui.integrador.repository.IUserRepository;
import com.arqui.integrador.util.AccountMapper;
import com.arqui.integrador.util.UserMapper;

@Service
public class UserService implements IUserService{
	
	private IUserRepository userRepository;
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	public UserService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<UserDto> getAll() {
		List<UserDto> response = this.userRepository.findAll().stream().map(e -> UserMapper.entityToDto(e)).toList();
		
		LOG.info("Users: {}", response);
		
		return response;
	}

	@Override
	public UserDto getById(Long id) {
		User response = this.findById(id);
		
		LOG.info("User: {}", response);
		
		return UserMapper.entityToDto(response);
	}

	@Override
	public UserDto create(UserDto userDto) {
		User response = this.userRepository.save(UserMapper.dtoToEntity(userDto));
		
		LOG.info("User: {}", response);
		
		return UserMapper.entityToDto(response);
	}

	@Override
	public UserDto update(Long id, UserDto userDto) {
		User user = this.findById(id);
		
		userDto.setId(user.getId());
		
		this.userRepository.save(UserMapper.dtoToEntity(userDto));
		
		LOG.info("User edited: {}", userDto);
		
		return userDto;
	}
	
	@Override
	public UserDto addAccount(Long userId, AccountDto accountDto) {
		User user = this.findById(userId);
		
		user.addAccount(AccountMapper.dtoToEntity(accountDto));
		
		this.userRepository.save(user);
		
		LOG.info("Account: {}, added in User: {}", user, accountDto);
		
		return UserMapper.entityToDto(user);
	}

	@Override
	public void delete(Long id) {
		this.userRepository.delete(this.findById(id));
		
		LOG.info("User with id: {}, successfuly deleted.", id);
		
	}

	@Override
	public void deleteAccount(Long userId, AccountDto accountDto) {
		User user = this.findById(userId);
		
		Account account = AccountMapper.dtoToEntity(accountDto);
		
		if(user.deleteAccount(account)) {
			this.userRepository.save(user);
			LOG.info("Account: {}, deleted from User: {}", account, user);
		}
		LOG.error("Account: {}, did not deleted from User: {}", account, user);
	}
	
	private User findById(Long userId) {
		return this.userRepository.findById(userId).orElseThrow(() ->
			new ItemNotFoundException("Item not found.", "Item with id: " + userId + "not found.")
		);
	}
}
