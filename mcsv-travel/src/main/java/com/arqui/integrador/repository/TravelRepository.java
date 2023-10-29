package com.arqui.integrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arqui.integrador.model.Travel;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long>{

}
