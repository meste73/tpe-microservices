package com.arqui.integrador.mcsvmaintenance.dto;

import java.security.Timestamp;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MaintenanceDTO {
    

    
    private long id_maintenance;

    @NotNull
	private LocalDate start_date;

	private LocalDate end_date;

    @NotNull
    private Long id_scooter;
    
    @NotNull
    private float scooter_km;
    
    // @NotNull
    // private Timestamp scooter_usage_time; 

    // // Constructor con "id"
    // public MaintenanceDTO(long id_maintenance, LocalDate start_date, LocalDate end_date, Long id_scooter, int scooter_km, Timestamp scooter_usage_time) {
    //     this.id_maintenance = id_maintenance;
    //     this.start_date = start_date;
    //     this.end_date = end_date;
    //     this.id_scooter = id_scooter;
    //     this.scooter_km = scooter_km;
    //     this.scooter_usage_time = scooter_usage_time;
    // }

    // // Constructor sin "id"
    // public MaintenanceDTO(LocalDate start_date, LocalDate end_date, Long id_scooter, int scooter_km, Timestamp scooter_usage_time) {
    //     this.start_date = start_date;
    //     this.end_date = end_date;
    //     this.id_scooter = id_scooter;
    //     this.scooter_km = scooter_km;
    //     this.scooter_usage_time = scooter_usage_time;
    // }
}

