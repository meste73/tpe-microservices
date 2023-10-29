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

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Travel")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Travel implements Serializable{

	private static final long serialVersionUID = -1680823192265785318L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_travel")
	private Long id_travel;
	@Column
	private int id_account;
	@Column
	private int id_user;
	@Column
	private int id_monopatin;
	@Column
	@CreationTimestamp
	private Timestamp start_date;
	@CreationTimestamp
	@Column
	private Timestamp ending_date;
	@Column
	private int pause_time;
	@Column
	private BigDecimal km;
	@Column
	private BigDecimal cost;
	@Column
	private boolean paused;	
}
