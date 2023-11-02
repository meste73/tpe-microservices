package com.arqui.integrador.mcsvmaintenance.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MaintenanceConfig {
    
    @Bean
	public RestTemplate restTemplateMaintenance() { 
        return new RestTemplate(); 
    } 
}
