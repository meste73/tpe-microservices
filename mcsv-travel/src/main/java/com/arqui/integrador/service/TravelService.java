package com.arqui.integrador.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arqui.integrador.controller.TravelController;
import com.arqui.integrador.dto.PausedTimeResponseDto;
import com.arqui.integrador.dto.PriceDto;
import com.arqui.integrador.dto.TravelDto;
import com.arqui.integrador.model.Price;
import com.arqui.integrador.model.Travel;
import com.arqui.integrador.repository.PriceRepository;
import com.arqui.integrador.repository.TravelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TravelService {

	private TravelRepository repository;
	private PriceRepository priceRepository;

	private static final Logger LOG = LoggerFactory.getLogger(TravelService.class);

	private ObjectMapper mapper;

	public TravelService(	TravelRepository repository, 
							@Qualifier("mapper") ObjectMapper mapper,
							PriceRepository priceRepository
	) {
		this.repository = repository;
		this.priceRepository = priceRepository;
		this.mapper = mapper;
	}

	public TravelDto getById(String id) {
		Travel t = repository.findById(id).orElse(null);
		return mapper.convertValue(t, TravelDto.class);
	}

	public void createTravel(TravelDto t) {
		Travel t1 = mapper.convertValue(t, Travel.class);
		repository.save(t1);
	}

	public List<TravelDto> getAll() {

		List<TravelDto> list = new ArrayList<>();
		List<Travel> resp = repository.findAll();
		for (Travel travel : resp) {
			list.add(mapper.convertValue(travel, TravelDto.class));
		}
		return list;
	}

	@Transactional
	public void update(TravelDto t, String id) {
		Travel t1 = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
		t1 = mapper.convertValue(t, Travel.class);
		t1.setCost(t.getCost());
		t1.setEnding_date(t.getEnding_date());
		t1.setKm(t.getKm());

		repository.save(t1);
	}

	public void delete(String id) {
		repository.deleteById(id);
	}

	public List<PausedTimeResponseDto> getAllByPause() {
		List<PausedTimeResponseDto> result = repository.getAllByPause();
		LOG.info(result.toString());
		return result;
	}

	// public BillDto getBills(int year, int month1, int month2) {
	// return repository.getBillsByDate(year, month1, month2);
	// }

	// public List<TravelsScooterResponseDto> getAllByYearQuantity(int year, Long
	// quantity) {
	// return repository.getQuantityByYear(year, quantity);
	// }


	public void newPrice(PriceDto p) {
		Price p1 = mapper.convertValue(p, Price.class);
		LOG.info("========================newPrice==========================");
		LOG.info(p1.toString());
		priceRepository.save(p1);
	}
}
