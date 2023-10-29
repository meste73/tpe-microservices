package com.arqui.integrador.service;

import com.arqui.integrador.dto.TravelDto;

public interface ITravelService {
	
	public TravelDto getById(Long id);
	public void createTravel(TravelDto t);
}
