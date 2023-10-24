package com.arqui.integrador.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "date_of_sign", updatable = false)
	private LocalDate dateOfSign;
	
	private double amount;
	
	@Column(nullable = false)
	private int mp_account;
	
	@ManyToMany(mappedBy = "accounts")
	private List<User> users;
}
