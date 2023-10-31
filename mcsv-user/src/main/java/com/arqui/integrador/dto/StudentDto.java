package com.arqui.integrador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
	
	private int id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String surname;
	
	@NotNull
	private int age;
	
	@NotBlank
	private String gender;
	
	@NotNull
	private long dni;
	
	@NotBlank
	private String city;
	
	@NotNull
	private int universityId;
}