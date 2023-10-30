package com.arqui.integrador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.arqui.integrador.dto.PausedTimeResponseDto;
import com.arqui.integrador.dto.TravelsScooterResponseDto;
import com.arqui.integrador.model.Travel;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {

	@Query("SELECT new com.arqui.integrador.dto.PausedTimeResponseDto(t.id_scooter, SUM(t.pause_time)) FROM com.arqui.integrador.model.Travel t GROUP BY t.id_scooter")
	List<PausedTimeResponseDto> getAllByPause();

	@Query("SELECT new com.arqui.integrador.dto.TravelsScooterResponseDto(t.id_scooter, COUNT(*)) FROM com.arqui.integrador.model.Travel t WHERE year(t.start_date)=:year AND year(t.ending_date)=:year GROUP BY t.id_scooter HAVING COUNT(*)>:quantity")
	List<TravelsScooterResponseDto> getQuantityByYear(int year, Long quantity);
}
