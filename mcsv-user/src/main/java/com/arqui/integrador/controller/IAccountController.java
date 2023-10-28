package com.arqui.integrador.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.dto.UserDto;

import jakarta.validation.Valid;

@RequestMapping("/accounts")
public interface IAccountController {
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<List<AccountDto>> getAll();
	
	@GetMapping(value = "/account/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<AccountDto> getById(@PathVariable(name = "id")Long id);
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<AccountDto> create(@Valid @RequestBody AccountDto accountDto);
	
	@PutMapping(value = "/account/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<AccountDto> update(@PathVariable(name = "id") Long id, @Valid @RequestBody AccountDto accountDto);
	
	@PutMapping(value = "/account/{account-id}/add-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<AccountDto> addUser(@PathVariable(name = "account-id") Long id, @Valid @RequestBody UserDto userDto);
	
	@PatchMapping(value = "/account/{account-id}/authorize")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<AccountDto> authorizeAccount(@PathVariable(name = "account-id") Long id);
	
	@PatchMapping(value = "/account/{account-id}/unauthorize")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<AccountDto> unauthorizeAccount(@PathVariable(name = "account-id") Long id);
	
	@DeleteMapping(value = "/account/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable(name = "id") Long id);
	
	@DeleteMapping(value = "/account/{account-id}/delete-user")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteUser(@PathVariable(name = "account-id") Long id, @Valid @RequestBody UserDto userDto);
	
}
