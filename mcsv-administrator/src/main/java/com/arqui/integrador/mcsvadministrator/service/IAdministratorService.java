package com.arqui.integrador.mcsvadministrator.service;

import java.util.List;

import com.arqui.integrador.mcsvadministrator.dto.AdministratorDTO;

public interface IAdministratorService {
    
     List<AdministratorDTO> getAll();

     AdministratorDTO getById(Long id);

     AdministratorDTO create(AdministratorDTO administratorDto);

     AdministratorDTO update (Long id, AdministratorDTO administratorDto);

     void delete(Long id);
     
    }
