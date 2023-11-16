package com.arqui.integrador.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.arqui.integrador.dto.BillDto;
import com.arqui.integrador.dto.PausedTimeResponseDto;
import com.arqui.integrador.dto.TravelsScooterResponseDto;
import com.arqui.integrador.model.Price;
import com.arqui.integrador.model.Travel;

@Repository
public interface TravelRepository extends MongoRepository<Travel, String> {

    
    @Aggregation({
        "{ $match: { $expr: { $and: [ { $eq: [ { $year: { $toDate: '$start_date' } }, ?0 ] }, { $gte: [ { $month: { $toDate: '$start_date' } }, ?1 ] }, { $lte: [ { $month: { $toDate: '$start_date' } }, ?2 ] } ] } } }",
        "{ $group: { _id: { year: { $year: { $toDate: '$start_date' } }, month: { $month: { $toDate: '$start_date' } } }, total: { $sum: '$cost' } } }",
        "{ $project: { _id: 0, year: '$_id.year', month: '$_id.month', total: 1 } }"
    })
    BillDto getBillsByDate(int year, int month1, int month2);

    
    // @Aggregation({"{ $match: { $expr: { $and: [ { $eq: [ { $year: { $toDate: '$start_date' } },?0 ] }, { $gte: [ { $month: { $toDate: '$start_date' } }, ?1 ] }, { $lte: [ {$month: { $toDate: '$start_date' } }, ?2 ] } ] } } }",
    // "{ $group: { _id: null, total: { $sum: '$cost' } } }",
    // "{ $project: { _id: 0, total: 1 } }"
    // })
    // BillDto getBillsByDate(int year, int month1, int month2);


    @Aggregation({
            "{ $group: { _id: '$id_scooter', totalPauseTime: { $sum: '$pause_time' } } }",
            "{ $project: { _id: '$_id', id_scooter: '$_id', pause_time: '$totalPauseTime' } }"
    })
    List<PausedTimeResponseDto> getAllByPause();

    void save(Price p1);

    @Aggregation({
            "{ $match: { $expr: { $and: [ { $eq: [ { $year: '$start_date' }, ?0 ] }, { $eq: [ { $year: '$ending_date' }, ?0 ] } ] } } }",
            "{ $group: { _id: '$id_scooter', count: { $sum: 1 } } }",
            "{ $match: { count: { $gte: ?1 } } }",
            "{ $project: { _id: '$_id', id_scooter: '$_id', travel_quantity: '$count' } }"
    })
    List<TravelsScooterResponseDto> getQuantityByYear(int year, Long quantity);
    // "{ $match: { count: { $gt: ?1 } } }", // Si queremos que sea Solo mayor y no
    // mayor o igual, es $gt en vez de $gte

    // @Aggregation({
    // "{ $match: { $expr: { $and: [ { $eq: [ { $year: '$start_date' }, ?0 ] },
    // {$gte: [ { $month: '$start_date' }, ?1 ] }, { $lte: [ { $month:
    // '$start_date'}, ?2 ] } ] } } }",
    // "{ $group: { _id: null, total: { $sum: '$cost' } } }",
    // "{ $project: { _id: 0, total: 1 } }"
    // })
    // BillDto getBillsByDate(int year, int month1, int month2);

    // @Query("SELECT new com.arqui.integrador.dto.BillDto (SUM (t.cost) AS
    // total_value) FROM com.arqui.integrador.model.Travel t WHERE
    // year(t.start_date)=:year AND month(t.start_date) BETWEEN :month1 AND
    // :month2")
    // BillDto getBillsByDate(int year, int month1, int month2);

}
