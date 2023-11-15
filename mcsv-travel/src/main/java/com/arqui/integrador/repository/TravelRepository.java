package com.arqui.integrador.repository;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.arqui.integrador.dto.PausedTimeResponseDto;
import com.arqui.integrador.model.Price;
import com.arqui.integrador.model.Travel;

@Repository
public interface TravelRepository extends MongoRepository<Travel, String> {


    @Aggregation({
        "{ $group: { _id: '$id_scooter', totalPauseTime: { $sum: '$pause_time' } } }",
        "{ $project: { _id: '$_id', id_scooter: '$_id', pause_time: '$totalPauseTime' } }"
    })
    List<PausedTimeResponseDto> getAllByPause();
    
    void save(Price p1);


    // @Query("SELECT new
    // com.arqui.integrador.dto.PausedTimeResponseDto(t.id_scooter,
    // SUM(t.pause_time)) FROM com.arqui.integrador.model.Travel t GROUP BY
    // t.id_scooter")

    // @Query("SELECT new
    // com.arqui.integrador.dto.TravelsScooterResponseDto(t.id_scooter, COUNT(*))
    // FROM com.arqui.integrador.model.Travel t WHERE year(t.start_date)=:year AND
    // year(t.ending_date)=:year GROUP BY t.id_scooter HAVING COUNT(*)>:quantity")
    // List<TravelsScooterResponseDto> getQuantityByYear(int year, Long quantity);
    //
    // @Query("SELECT new com.arqui.integrador.dto.BillDto (SUM (t.cost) AS
    // total_value) FROM com.arqui.integrador.model.Travel t WHERE
    // year(t.start_date)=:year AND month(t.start_date) BETWEEN :month1 AND
    // :month2")
    // BillDto getBillsByDate(int year, int month1, int month2);
    //
    
    
}
