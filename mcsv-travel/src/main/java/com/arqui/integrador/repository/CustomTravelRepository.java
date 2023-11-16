package com.arqui.integrador.repository;

import java.util.List;

import com.arqui.integrador.dto.BillDto;

public interface CustomTravelRepository {

    BillDto getBillsByDate(int year, int month1, int month2);
    
} 
