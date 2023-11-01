package com.arqui.integrador.initializer;

import org.springframework.stereotype.Component;

import com.arqui.integrador.dto.UserDto;
import com.arqui.integrador.service.IUserService;

import jakarta.annotation.PostConstruct;

@Component
public class UserInitializer {
	
    private IUserService userService;
    private AccountInitializer accInit;
    
    public UserInitializer(IUserService userService, AccountInitializer accInit) {
    	this.userService = userService;
    	this.accInit = accInit;
    }

    @PostConstruct
    public void init(){
    	this.userService.create(UserDto.builder().name("meste73")
    			.cellphone(2494380393L).email("elmeste.88@gmail.com").firstname("Ezequiel").surname("Mestelan").build());
    	
    	this.userService.create(UserDto.builder().name("Frank")
    			.cellphone(2494554466L).email("fdeluccho@gmail.com").firstname("Franco").surname("Delucci").build());
    	
    	this.userService.create(UserDto.builder().name("Matt")
    			.cellphone(2494745475L).email("matt.s@gmail.com").firstname("Matias").surname("Sanchez Abrego").build());
    	
        this.userService.create(UserDto.builder().name("Jebu")
        		.cellphone(2494332456L).email("eljebu@gmail.com").firstname("Jesus").surname("Diaz").build());
        
        this.userService.create(UserDto.builder().name("Carlo")
        		.cellphone(2494252314L).email("elcharly@gmail.com").firstname("Carlos").surname("Garcia").build());
        
        this.userService.create(UserDto.builder().name("John")
        		.cellphone(2494313665L).email("john.rambo@gmail.com").firstname("Juan").surname("Rodriguez").build());

        this.accInit.init();
    }
}
