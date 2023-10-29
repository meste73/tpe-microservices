package com.arqui.integrador.mcsvmaintenance.service;

import static com.arqui.integrador.mcsvmaintenance.util.MaintenanceMapper.entityToDto;
import static com.arqui.integrador.mcsvmaintenance.util.MaintenanceMapper.dtoToEntity;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arqui.integrador.mcsvmaintenance.dto.MaintenanceDTO;
import com.arqui.integrador.mcsvmaintenance.exeption.ItemNotFoundException;
import com.arqui.integrador.mcsvmaintenance.model.Maintenance;
import com.arqui.integrador.mcsvmaintenance.repository.IMaintenanceRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MaintenanceService implements IMaintenanceService {

    private static final Logger LOG = LoggerFactory.getLogger(MaintenanceService.class);
    private IMaintenanceRepository maintenanceRepository;

    public MaintenanceService(IMaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
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
		return this.maintenanceRepository.findById(id).orElseThrow(() -> 
			new ItemNotFoundException("Item not found", "Maintenance with id: " + id + " not found."));
	}

}
