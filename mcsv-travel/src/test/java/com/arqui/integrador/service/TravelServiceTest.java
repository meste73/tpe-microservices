package com.arqui.integrador.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.arqui.integrador.dto.TravelDto;
import com.arqui.integrador.exception.ItemNotFoundException;
import com.arqui.integrador.model.Travel;
import com.arqui.integrador.repository.TravelRepository;

@ExtendWith(MockitoExtension.class)
class TravelServiceTest {
	
	@InjectMocks
	private TravelService travelService;
	
	@Mock
	private TravelRepository travelRepository;
	
	@Mock
    private RestTemplate restTemplate;
	
	@Test
	void getAllTest(){
		List<Travel> travels = new ArrayList<>();
		travels.add(Travel.builder().id("a").id_account(1).id_user(123).id_scooter(1000).start_date(LocalDateTime.of(2023, 06, 15, 0, 0)).ending_date(null).pause_time(0).km(BigDecimal.valueOf(45)).cost(BigDecimal.valueOf(5000)).paused(false).build());
		travels.add(Travel.builder().id("b").id_account(2).id_user(1234).id_scooter(1001).start_date(LocalDateTime.of(2023, 06, 16, 0, 0)).ending_date(null).pause_time(0).km(BigDecimal.valueOf(12)).cost(BigDecimal.valueOf(1500)).paused(false).build());
		travels.add(Travel.builder().id("c").id_account(3).id_user(12345).id_scooter(1002).start_date(LocalDateTime.of(2023, 06, 17, 0, 0)).ending_date(null).pause_time(20).km(BigDecimal.valueOf(55)).cost(BigDecimal.valueOf(6000)).paused(false).build());
		travels.add(Travel.builder().id("d").id_account(4).id_user(456).id_scooter(1064).start_date(LocalDateTime.of(2023, 06, 25, 0, 0)).ending_date(LocalDateTime.of(2023, 07, 15, 0, 0)).pause_time(150).km(BigDecimal.valueOf(300)).cost(BigDecimal.valueOf(35000)).paused(false).build());
		
		Mockito.when(this.travelRepository.findAll()).thenReturn(travels);
		
		List<TravelDto> travelsDto = this.travelService.getAll();
		
		Mockito.verify(this.travelRepository, Mockito.times(1)).findAll();
		
		Assertions.assertEquals(4L, travelsDto.size());
		Assertions.assertEquals(1, travels.get(0).getId_account());
		Assertions.assertEquals(2, travels.get(1).getId_account());
		Assertions.assertEquals(3, travels.get(2).getId_account());
		Assertions.assertEquals(4, travels.get(3).getId_account());
		Assertions.assertEquals(123, travels.get(0).getId_user());
		Assertions.assertEquals(1234, travels.get(1).getId_user());
		Assertions.assertEquals(12345, travels.get(2).getId_user());
		Assertions.assertEquals(456, travels.get(3).getId_user());
		Assertions.assertEquals(1000, travels.get(0).getId_scooter());
		Assertions.assertEquals(1001, travels.get(1).getId_scooter());
		Assertions.assertEquals(1002, travels.get(2).getId_scooter());
		Assertions.assertEquals(1064, travels.get(3).getId_scooter());
		Assertions.assertEquals(LocalDateTime.of(2023, 06, 15, 0, 0), travels.get(0).getStart_date());
		Assertions.assertEquals(LocalDateTime.of(2023, 06, 16, 0, 0), travels.get(1).getStart_date());
		Assertions.assertEquals(LocalDateTime.of(2023, 06, 17, 0, 0), travels.get(2).getStart_date());
		Assertions.assertEquals(LocalDateTime.of(2023, 06, 25, 0, 0), travels.get(3).getStart_date());
		Assertions.assertEquals(null, travels.get(0).getEnding_date());
		Assertions.assertEquals(null, travels.get(1).getEnding_date());
		Assertions.assertEquals(null, travels.get(2).getEnding_date());
		Assertions.assertEquals(LocalDateTime.of(2023, 07, 15, 0, 0), travels.get(3).getEnding_date());
		Assertions.assertEquals(0, travels.get(0).getPause_time());
		Assertions.assertEquals(0, travels.get(1).getPause_time());
		Assertions.assertEquals(20, travels.get(2).getPause_time());
		Assertions.assertEquals(150, travels.get(3).getPause_time());
		Assertions.assertEquals(BigDecimal.valueOf(45), travels.get(0).getKm());
		Assertions.assertEquals(BigDecimal.valueOf(12), travels.get(1).getKm());
		Assertions.assertEquals(BigDecimal.valueOf(55), travels.get(2).getKm());
		Assertions.assertEquals(BigDecimal.valueOf(300), travels.get(3).getKm());
		Assertions.assertEquals(BigDecimal.valueOf(5000), travels.get(0).getCost());
		Assertions.assertEquals(BigDecimal.valueOf(1500), travels.get(1).getCost());
		Assertions.assertEquals(BigDecimal.valueOf(6000), travels.get(2).getCost());
		Assertions.assertEquals(BigDecimal.valueOf(35000), travels.get(3).getCost());
		Assertions.assertEquals(false, travels.get(0).isPaused());
		Assertions.assertEquals(false, travels.get(1).isPaused());
		Assertions.assertEquals(false, travels.get(2).isPaused());
		Assertions.assertEquals(false, travels.get(3).isPaused());
	}
	
	@Test
	void getByIdTest() {
		Travel travel = Travel.builder().id("a").id_account(1).id_user(123).id_scooter(1000).start_date(LocalDateTime.of(2023, 06, 15, 0, 0)).ending_date(null).pause_time(0).km(BigDecimal.valueOf(45)).cost(BigDecimal.valueOf(5000)).paused(false).build();
		
		Mockito.when(this.travelRepository.findById("a")).thenReturn(Optional.of(travel));
		
		TravelDto travelDto = this.travelService.getById("a");
		
		Mockito.verify(this.travelRepository, Mockito.times(1)).findById("a");
		
		Assertions.assertEquals(1, travelDto.getId_cuenta());
		Assertions.assertEquals(456, travelDto.getId_usuario());
		Assertions.assertEquals(1064, travelDto.getId_scooter());
		Assertions.assertEquals(LocalDateTime.of(2023, 06, 25, 0, 0), travelDto.getStart_date());
		Assertions.assertEquals(LocalDateTime.of(2023, 07, 15, 0, 0), travelDto.getEnding_date());
		Assertions.assertEquals(150, travelDto.getPause_time());
		Assertions.assertEquals(BigDecimal.valueOf(300), travelDto.getKm());
		Assertions.assertEquals(BigDecimal.valueOf(35000), travelDto.getCost());
		Assertions.assertEquals(false, travelDto.isPaused());
	}
	
	@Test
	void getByIdNotFoundTest() {		
		Mockito.when(this.travelRepository.findById("a")).thenReturn(Optional.empty());
		
		ItemNotFoundException expectedException = Assertions.assertThrows(
				ItemNotFoundException.class, () -> this.travelService.getById("a"));
		
		Assertions.assertEquals("Item not found.", expectedException.getMessage());
		
		Mockito.verify(this.travelRepository, Mockito.times(1)).findById("a");
	}
	
	@Test
	void getByNullIdTest() {		
		Mockito.when(this.travelRepository.findById(null)).thenThrow(IllegalArgumentException.class);
		
		IllegalArgumentException expectedException = Assertions.assertThrows(
				IllegalArgumentException.class, () -> this.travelService.getById(null));
		
		Assertions.assertEquals("java.lang.IllegalArgumentException", expectedException.getClass().getName());
		
		Mockito.verify(this.travelRepository, Mockito.times(1)).findById(null);
	}

	@Test
	void createTest() {
		Travel travel= Travel.builder()
				.id("a")
				.id_account(1)
				.id_user(123)
				.id_scooter(1000)
				.start_date(LocalDateTime.of(2023, 06, 15, 0, 0))
				.ending_date(null)
				.pause_time(0)
				.km(BigDecimal.valueOf(45))
				.cost(BigDecimal.valueOf(5000))
				.paused(false)
				.build();
				
		
		TravelDto travelDtoRequest = TravelDto.builder()
				.id("a")
				.id_cuenta(1)
				.id_usuario(123)
				.id_scooter(1000)
				.start_date(LocalDateTime.of(2023, 06, 15, 0, 0))
				.ending_date(null)
				.pause_time(0)
				.km(BigDecimal.valueOf(45))
				.cost(BigDecimal.valueOf(5000))
				.paused(false)
				.build();
		
		Mockito.when(this.travelRepository.save(travel)).thenReturn(travel);
		
		TravelDto travelDtoResponse = this.travelService.getById(travelDtoRequest.getId());
		
		Mockito.verify(this.travelRepository, Mockito.times(1)).save(travel);
		
		Assertions.assertEquals(1, travelDtoResponse.getId_cuenta());
		Assertions.assertEquals(456, travelDtoResponse.getId_usuario());
		Assertions.assertEquals(1064, travelDtoResponse.getId_scooter());
		Assertions.assertEquals(LocalDateTime.of(2023, 06, 25, 0, 0), travelDtoResponse.getStart_date());
		Assertions.assertEquals(LocalDateTime.of(2023, 07, 15, 0, 0), travelDtoResponse.getEnding_date());
		Assertions.assertEquals(150, travelDtoResponse.getPause_time());
		Assertions.assertEquals(BigDecimal.valueOf(300), travelDtoResponse.getKm());
		Assertions.assertEquals(BigDecimal.valueOf(35000), travelDtoResponse.getCost());
		Assertions.assertEquals(false, travelDtoResponse.isPaused());
	}
	
//	@Test
//	void updateTest() {
//		Travel travel = Travel.builder()
//				.id("a")
//				.id_account(1)
//				.id_user(123)
//				.id_scooter(1000)
//				.start_date(LocalDateTime.of(2023, 06, 15, 0, 0))
//				.ending_date(null)
//				.pause_time(0)
//				.km(BigDecimal.valueOf(45))
//				.cost(BigDecimal.valueOf(5000))
//				.paused(false)
//				.build();
//		
//		Travel travelEdited = Travel.builder()
//				.id("b")
//				.id_account(2)
//				.id_user(1234)
//				.id_scooter(1001)
//				.start_date(LocalDateTime.of(2023, 06, 16, 0, 0))
//				.ending_date(null)
//				.pause_time(0)
//				.km(BigDecimal.valueOf(12))
//				.cost(BigDecimal.valueOf(1500))
//				.paused(false)
//				.build();
//		
//		
//		TravelDto travelEditedDto = TravelDto.builder()
//				.id("b")
//				.id_cuenta(2)
//				.id_usuario(1234)
//				.id_scooter(1001)
//				.start_date(LocalDateTime.of(2023, 06, 16, 0, 0))
//				.ending_date(null)
//				.pause_time(0)
//				.km(BigDecimal.valueOf(12))
//				.cost(BigDecimal.valueOf(1500))
//				.paused(false)
//				.build();
//		
//		Mockito.when(this.travelRepository.findById("a")).thenReturn(Optional.of(travel));
//		Mockito.when(this.travelRepository.update(travelEditedDto, travel.getId())).thenReturn(travelEdited);
//		
//		TravelDto travelDto = this.travelService.update(travelEditedDto, "b");
//		
//		Mockito.verify(this.travelRepository, Mockito.times(1)).save(travelEdited);
//		
//		Assertions.assertEquals("meste", travelDto.getName());
//		Assertions.assertNotEquals("meste73", travelDto.getName());
//	}
	
	@Test
	void deleteTest() {
		Travel admin = Travel.builder()
				.id("b")
				.id_account(2)
				.id_user(1234)
				.id_scooter(1001)
				.start_date(LocalDateTime.of(2023, 06, 16, 0, 0))
				.ending_date(null)
				.pause_time(0)
				.km(BigDecimal.valueOf(12))
				.cost(BigDecimal.valueOf(1500))
				.paused(false)
				.build();
		
		Mockito.when(this.travelRepository.findById("b")).thenReturn(Optional.of(admin));
		
		this.travelService.delete("b");
		
		Mockito.verify(this.travelRepository, Mockito.times(1)).findById("b");
	}

}
