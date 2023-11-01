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
@Table(name = "Travel")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Travel implements Serializable{

	private static final long serialVersionUID = -1680823192265785318L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	@Column(name="id_account")
	private int idAccount;
	@Column(name="id_user")
	private int idUser;
	@Column(name="id_scooter")
	private int idScooter;
	@Column(name="start_date", nullable = false, updatable = false)
	@CreationTimestamp
	private Timestamp startDate;
	@CreationTimestamp
	@Column(name="ending_date")
	private Timestamp endingDate;
	@Column(name="pause_time")
	private int pauseTime;
	@Column(name="km")
	private BigDecimal km;
	@Column(name="cost")
	private BigDecimal cost;
	@Column(name="paused")
	private boolean paused;	
}
