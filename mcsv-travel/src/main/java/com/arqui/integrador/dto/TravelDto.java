package com.arqui.integrador.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class TravelDto implements Serializable {
	
	private static final long serialVersionUID = 5534018998179223152L;

	@JsonProperty("id")
	private int id;
	@JsonProperty("idAccount")
	private int id_cuenta;
	@JsonProperty("idUser")
	private int id_usuario;
	@JsonProperty("idScooter")
	private int id_scooter;
	@JsonProperty("startDate")
	private Timestamp start_date;
	@JsonProperty("endingDate")
	private Timestamp ending_date;
	@JsonProperty("km")
	private BigDecimal km;
	@JsonProperty("cost")
	private BigDecimal cost;
	@JsonProperty("paused")
	private boolean paused;
	@JsonProperty("pauseTime")
	private int pause_time;
}
