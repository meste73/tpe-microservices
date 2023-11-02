package com.arqui.integrador.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Price")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price implements Serializable {

	private static final long serialVersionUID = -1680823192265785318L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "price_by_hour")
	private BigDecimal price_by_hour;
	@Column(name = "rate_of_increase")
	private BigDecimal rate_of_increase;
	@Column(name = "actual_date")
	private Timestamp actual_date;
}
