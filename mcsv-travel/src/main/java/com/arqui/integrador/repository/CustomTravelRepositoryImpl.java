package com.arqui.integrador.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.arqui.integrador.dto.BillDto;

import java.util.List;

@Repository
public class CustomTravelRepositoryImpl implements CustomTravelRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomTravelRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public BillDto getBillsByDate(int year, int month1, int month2) {
        AggregationOperation match = Aggregation.match(
                Criteria.where("start_date").gte(month1).lte(month2)
                        .and("year").is(year)
        );
        AggregationOperation group = Aggregation.group().sum("cost").as("total_value");

        Aggregation aggregation = Aggregation.newAggregation(match, group);

        AggregationResults<BillDto> results = mongoTemplate.aggregate(aggregation, "travel", BillDto.class);

        // Obtener el primer resultado si existe
        return results.getUniqueMappedResult();
    }
}

