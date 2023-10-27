package com.arqui.integrador.service;

import static com.arqui.integrador.util.AccountMapper.entityToDto;
import static com.arqui.integrador.util.AccountMapper.dtoToEntity;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.dto.UserDto;
import com.arqui.integrador.exception.ItemNotFoundException;
import com.arqui.integrador.model.Account;
import com.arqui.integrador.repository.IAccountRepository;

@Service
public class AccountService implements IAccountService{
	
	private IAccountRepository accountRepository;
	private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);
	
	public AccountService(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public List<AccountDto> getAll() {
		List<AccountDto> response = this.accountRepository.findAll().stream().map(e -> entityToDto(e)).toList();
		
		LOG.info("Accounts: {}", response);
		
		return response;
	}

	@Override
	public AccountDto getById(Long id) {
		Account response = this.findById(id);
		
		LOG.info("Account : {}", response);
		
		return entityToDto(response);
	}

	@Override
	public AccountDto create(AccountDto accountDto) {
		Account response = this.accountRepository.save(dtoToEntity(accountDto));
		
		LOG.info("Account created: {}", response);
		
		return entityToDto(response);
	}

	@Override
	public AccountDto update(Long id, AccountDto accountDto) {
		Account account = this.findById(id);
		
		accountDto.setId(account.getId());
		
		this.accountRepository.save(dtoToEntity(accountDto));
		
		LOG.info("Account updated: {}", accountDto);
		
		return accountDto;
	}
	
	@Override
	public AccountDto addUser(Long id, UserDto userDto) {
		//Implements addUser()
		return null;
	}

	@Override
	public void delete(Long id) {
		Account account = this.findById(id);
		
		this.accountRepository.delete(account);
		
		LOG.info("Account deleted: {}", account);
		
	}
	
	@Override
	public void deleteUser(Long id, UserDto userDto) {
		//Implements deleteUser()
	}
	
	private Account findById(Long id) {
		return this.accountRepository.findById(id).orElseThrow(() -> 
			new ItemNotFoundException("Item not found", "Account with id: " + id + " not found."));
	}

}
