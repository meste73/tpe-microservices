package com.arqui.integrador.mcsvmaintenance.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.arqui.integrador.mcsvmaintenance.dto.MaintenanceDTO;

import jakarta.validation.Valid;


@RequestMapping("/maintenance")
public interface IMaintenanceController {
    
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)    
	@ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<MaintenanceDTO>> getAll();
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<MaintenanceDTO> getById(@PathVariable(name = "id") Long id);

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<MaintenanceDTO> create(@Valid @RequestBody MaintenanceDTO maintenenceDto);

	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<MaintenanceDTO> update(@PathVariable(name = "id") Long id, @Valid @RequestBody MaintenanceDTO maintenenceDto);

	@DeleteMapping (value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete (@PathVariable(name = "id") Long id);

	











	// @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	// @ResponseStatus(HttpStatus.OK)
	// ResponseEntity<UserDto> getById(@PathVariable(name = "id") Long id);
	
	// @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	// @ResponseStatus(HttpStatus.CREATED)
	// ResponseEntity<UserDto> create(@Valid @RequestBody UserDto user);
	
	// @PutMapping(value = "/user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	// @ResponseStatus(HttpStatus.OK)
	// ResponseEntity<UserDto> update(@PathVariable(name = "id") Long id, @Valid @RequestBody UserDto user);
	
	// @PutMapping(value = "/user/add-account/{user-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	// @ResponseStatus(HttpStatus.OK)
	// ResponseEntity<UserDto> addAccount(@PathVariable(name = "user-id") Long userId, @Valid @RequestBody AccountDto account);
	
	// @DeleteMapping(value = "/user/{id}")
	// @ResponseStatus(HttpStatus.NO_CONTENT)
	// void delete(@PathVariable(name = "id") Long id);
	
	// @DeleteMapping(value = "/user/delete-account/{user-id}")
	// @ResponseStatus(HttpStatus.NO_CONTENT)
	// void deleteAccount(@PathVariable(name = "user-id") Long userId, @Valid @RequestBody AccountDto account);
    
}
