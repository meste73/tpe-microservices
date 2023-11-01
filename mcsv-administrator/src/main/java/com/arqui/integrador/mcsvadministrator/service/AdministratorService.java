package com.arqui.integrador.mcsvadministrator.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.arqui.integrador.mcsvadministrator.dto.AdministratorDTO;
import com.arqui.integrador.mcsvadministrator.dto.TravelsByTotalBillingAmount;
import com.arqui.integrador.mcsvadministrator.dto.TravelsByYearsDTO;
import com.arqui.integrador.mcsvadministrator.model.Administrator;
import com.arqui.integrador.mcsvadministrator.repository.IAdministratorRepository;
import com.arqui.integrador.mcsvadministrator.exception.ItemNotFoundException;

import static com.arqui.integrador.mcsvadministrator.utils.AdministratorMapper.dtoToEntity;
import static com.arqui.integrador.mcsvadministrator.utils.AdministratorMapper.entityToDto;

@Service
public class AdministratorService implements IAdministratorService {

    private IAdministratorRepository administratorRepository;
    private RestTemplate restTemplate;
    private static final Logger LOG = LoggerFactory.getLogger(AdministratorService.class);

    public AdministratorService(IAdministratorRepository administratorRepository, RestTemplate restTemplate) {
        this.administratorRepository = administratorRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<AdministratorDTO> getAll() {

        List<AdministratorDTO> response = this.administratorRepository.findAll().stream().map(e -> entityToDto(e))
                .toList();
        LOG.info("Administrators: {}", response);
        return response;
    }

    @Override
    public AdministratorDTO getById(Long id) {
        Administrator response = this.findById(id);
        LOG.info("Administrator : {} ", response);
        return entityToDto(response);
    }

    @Override
    public AdministratorDTO create(AdministratorDTO administratorDto) {
        Administrator response = this.administratorRepository.save(dtoToEntity(administratorDto));
        LOG.info("Administrator created: {}", response);
        return entityToDto(response);
    }

    @Override
    public AdministratorDTO update(Long id, AdministratorDTO administratorDto) {
        Administrator administrator = this.findById(id);

        administratorDto.setId(administrator.getId());

        this.administratorRepository.save(dtoToEntity(administratorDto));

        LOG.info("Account updated: {}", administratorDto);

        return administratorDto;
    }

    @Override
    public void delete(Long id) {
        Administrator administrator = this.findById(id);

        this.administratorRepository.delete(administrator);

        LOG.info("Administrator deleted: {}", administrator);
    }

    private Administrator findById(Long id) {
        return this.administratorRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Item not found", "Administrator with id: " + id + " not found."));
    }

    @Override
    public void updateStatusAccount(Long id , String status) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<Void>> requestEntity = new HttpEntity<>(headers);

        restTemplate.exchange(
                "http://127.0.0.1:8006/accounts/" + id + "/" + status+ "",
                HttpMethod.PATCH,
                requestEntity,
                new ParameterizedTypeReference<List<Void>>() {
                });
    }

    @Override
    public List<TravelsByYearsDTO> getTravelsByYears(int year, int quantity) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity <Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity <List<TravelsByYearsDTO>> result = restTemplate.exchange(
                "http://127.0.0.1:8005//travels/filter?year="+year+"&quantity="+quantity,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<TravelsByYearsDTO>>() {
                });
                if(result.getStatusCode().is2xxSuccessful()){
                    return result.getBody();
                }
                else{
                    return new ArrayList<>() ; // TODO : Devolver un Error
                }
        }

    @Override
    public List<TravelsByTotalBillingAmount> getTravelsByTotalBillingAmounts(int year, int month1, int month2) {
       
        HttpHeaders headers = new HttpHeaders();
        HttpEntity <Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity <List<TravelsByTotalBillingAmount>> result = restTemplate.exchange(
                "http://0.0.0.0:8005/travels/billing?year="+year+"&month1="+month1+"&month2="+month2,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<TravelsByTotalBillingAmount>>() {
                });
                if(result.getStatusCode().is2xxSuccessful()){
                    return result.getBody();
                }
                else{
                    return new ArrayList<>() ; // TODO : Devolver un Error
                }
    }
        

}
