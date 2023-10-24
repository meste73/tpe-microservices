package com.arqui.integrador.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.arqui.integrador.dto.UserDTO;

import jakarta.validation.Valid;

@RequestMapping("/users")
@Validated
public interface IUserController {
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<List<UserDTO>> getAll();
	
	@GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<UserDTO> getById(@PathVariable(name = "id") Long id);
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO user);
}
