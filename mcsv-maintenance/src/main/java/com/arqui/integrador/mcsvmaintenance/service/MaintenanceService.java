package com.arqui.integrador.mcsvmaintenance.service;

import static com.arqui.integrador.mcsvmaintenance.util.MaintenanceMapper.entityToDto;
import static com.arqui.integrador.mcsvmaintenance.util.MaintenanceMapper.dtoToEntity;

import java.time.LocalDate;
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

import com.arqui.integrador.mcsvmaintenance.config.MaintenanceConfig;
import com.arqui.integrador.mcsvmaintenance.dto.MaintenanceDTO;
import com.arqui.integrador.mcsvmaintenance.dto.ScooterForMaintenanceDTO;
import com.arqui.integrador.mcsvmaintenance.exeption.ItemNotFoundException;
import com.arqui.integrador.mcsvmaintenance.model.Maintenance;
import com.arqui.integrador.mcsvmaintenance.repository.IMaintenanceRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MaintenanceService implements IMaintenanceService {

    private static final Logger LOG = LoggerFactory.getLogger(MaintenanceService.class);
    private IMaintenanceRepository maintenanceRepository;
    private RestTemplate restTemplate;

    public MaintenanceService(IMaintenanceRepository maintenanceRepository, RestTemplate restTemplate) {
        this.maintenanceRepository = maintenanceRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<MaintenanceDTO> getAll() {
        List<MaintenanceDTO> response = this.maintenanceRepository.findAll().stream().map(e -> entityToDto(e)).toList();
        return response;
    }

    @Override
    public MaintenanceDTO getById(Long id) {
        Maintenance response = this.findById(id);

        LOG.info("Maintenance : {}", response);

        return entityToDto(response);
    }

    @Override
    public MaintenanceDTO create(MaintenanceDTO maintenanceDTO) {
        Maintenance response = this.maintenanceRepository.save(dtoToEntity(maintenanceDTO));

        LOG.info("Maintenance created: {}", response);

        return entityToDto(response);
    }

    @Override
    public MaintenanceDTO update(Long id, MaintenanceDTO maintenanceDTO) {
        Maintenance maintenance = this.findById(id);

        maintenanceDTO.setId_maintenance(maintenance.getId_maintenance());

        this.maintenanceRepository.save(dtoToEntity(maintenanceDTO));

        LOG.info("Maintenance updated: {}", maintenanceDTO);

        return maintenanceDTO;
    }

    @Override
    public void delete(Long id) {
        Maintenance maintenance = this.findById(id);

        this.maintenanceRepository.delete(maintenance);

        LOG.info("Maintenance deleted: {}", maintenance);
    }

    private Maintenance findById(Long id) {
        return this.maintenanceRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Item not found", "Maintenance with id: " + id + " not found."));
    }

    @Override
    public List<ScooterForMaintenanceDTO> getScootersForMaintenance() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<Void>> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<ScooterForMaintenanceDTO>> response = restTemplate.exchange(
                "http://127.0.0.1:8004/scooters?available=true",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<ScooterForMaintenanceDTO>>() {
                });

        if (response.getStatusCode().is2xxSuccessful()) {

            return getIdsForMaintenance(response.getBody());
        } else {
            return new ArrayList<>(); // TODO Arrojar exception
        }
    }

    private List<ScooterForMaintenanceDTO> getIdsForMaintenance(List<ScooterForMaintenanceDTO> listForFilter) {
        List<Long> result = new ArrayList<>();
        listForFilter.forEach(e -> {
            if (e.getKmsTraveled() >= 100) {
                Maintenance m = Maintenance.builder().start_date(LocalDate.now()).id_scooter(e.getId())
                        .scooter_km(e.getKmsTraveled()).build();
                this.maintenanceRepository.save(m);
                result.add(e.getId());
            } else {
                listForFilter.remove(e);
            }
        });

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<Long>> requestEntity = new HttpEntity<>( result , headers );

        restTemplate.exchange(
                "http://127.0.0.1:8004/scooters", // TODO Cambiar la URL
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<List<Long>>() {});

        return listForFilter;
    }
}
