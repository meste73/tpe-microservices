package com.arqui.integrador.mcsvadministrator.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
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

import com.arqui.integrador.mcsvadministrator.dto.AdministratorDTO;
import com.arqui.integrador.mcsvadministrator.dto.TravelsByTotalBillingAmount;
import com.arqui.integrador.mcsvadministrator.dto.TravelsByYearsDTO;

import jakarta.validation.Valid;

@RequestMapping("/administrator")
public interface IAdministratorController {
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<List<AdministratorDTO>> getAll();

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<AdministratorDTO> getById(@PathVariable(name = "id") Long id);

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<AdministratorDTO> create(@Valid @RequestBody AdministratorDTO administratorDTO);

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<AdministratorDTO> update(@PathVariable(name = "id") Long id,
			@Valid @RequestBody AdministratorDTO administratorDTO);

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable(name = "id") Long id);

	// MESTE
	@PatchMapping(value = "/accounts/{id}/unauthorize")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void unauthorizeAccount(@PathVariable(name = "id") Long id);

	@PatchMapping(value = "/accounts/{id}/authorize")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void authorizeAccount(@PathVariable(name = "id") Long id);

	// FRAN 8005
	@GetMapping("/travels/filter?year={year}&quantity={quantity}")
	@ResponseStatus(HttpStatus.OK)
	List<TravelsByYearsDTO> getTravelsByYears( @PathVariable (name = "year") int year , @PathVariable (name = "quantity") int quantity );

	@GetMapping("/travels/billing?year=2023&month1=1&month2=12")
	@ResponseStatus(HttpStatus.OK)
	List<TravelsByTotalBillingAmount> getTravelsByTotalBillingAmounts( 	@PathVariable (name = "year") int year , 
																		@PathVariable (name = "month1") int month1,
																		@PathVariable (name = "month2") int month2);
}
