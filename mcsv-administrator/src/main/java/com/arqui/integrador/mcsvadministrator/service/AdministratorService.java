package com.arqui.integrador.mcsvadministrator.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arqui.integrador.mcsvadministrator.dto.AdministratorDTO;
import com.arqui.integrador.mcsvadministrator.model.Administrator;
import com.arqui.integrador.mcsvadministrator.repository.IAdministratorRepository;
import com.arqui.integrador.mcsvadministrator.exception.ItemNotFoundException;

import static com.arqui.integrador.mcsvadministrator.utils.AdministratorMapper.dtoToEntity;
import static com.arqui.integrador.mcsvadministrator.utils.AdministratorMapper.entityToDto;

@Service
public class AdministratorService implements IAdministratorService {

    private IAdministratorRepository administratorRepository;
    private static final Logger LOG = LoggerFactory.getLogger(AdministratorService.class);


    public AdministratorService (IAdministratorRepository administratorRepository){
        this.administratorRepository = administratorRepository;
    }


    @Override
    public List<AdministratorDTO> getAll() {

        List<AdministratorDTO> response = this.administratorRepository.findAll().stream().map(e -> entityToDto(e)).toList();
        LOG.info("Administrators: {}", response);
        return response;
    }

    @Override
    public AdministratorDTO getById(Long id) {
        Administrator response = this.findById(id);
        LOG.info("Administrator : {} " , response);
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

        this.administratorRepository.save (dtoToEntity(administratorDto));

        LOG.info("Account updated: {}", administratorDto);

        return administratorDto;
    }

    @Override
    public void delete(Long id) {
        Administrator administrator = this.findById(id);
		
		this.administratorRepository.delete(administrator);
		
		LOG.info("Administrator deleted: {}", administrator);
    }

    private Administrator findById(Long id){
        return this.administratorRepository.findById(id).orElseThrow(()-> 
        new ItemNotFoundException("Item not found", "Administrator with id: " + id + " not found."));
    }

   
}
