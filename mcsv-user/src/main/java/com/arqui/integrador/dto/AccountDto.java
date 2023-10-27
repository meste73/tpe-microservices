package com.arqui.integrador.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
	
	private Long id;
	
	private LocalDate dateOfSign;
	
	private double amount;
	
	private int mpAccount;
	
	private List<UserDto> users;
}