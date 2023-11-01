package com.arqui.integrador.initializer;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.arqui.integrador.dto.AccountDto;
import com.arqui.integrador.service.IAccountService;

@Component
public class AccountInitializer {
	
    private IAccountService accountService;
    
    public AccountInitializer(IAccountService accountService) {
    	this.accountService = accountService;
    }

    public void init(){
    	this.accountService.create(AccountDto.builder().amount(12500)
    			.dateOfSign(LocalDate.of(2023,7,10)).mpAccount(10012456).isAvailable(true).build());
    	
    	this.accountService.create(AccountDto.builder().amount(14700)
    			.dateOfSign(LocalDate.of(2023,4,22)).mpAccount(10013578).isAvailable(true).build());
    	
    	this.accountService.create(AccountDto.builder().amount(13200)
    			.dateOfSign(LocalDate.of(2023,3,14)).mpAccount(10014101).isAvailable(true).build());
    	
    	this.accountService.create(AccountDto.builder().amount(16000)
    			.dateOfSign(LocalDate.of(2023,8,7)).mpAccount(10017594).isAvailable(true).build());
    	
    	this.accountService.create(AccountDto.builder().amount(18000)
    			.dateOfSign(LocalDate.of(2023,2,1)).mpAccount(10013365).isAvailable(true).build());
    	
    	this.accountService.create(AccountDto.builder().amount(9100)
    			.dateOfSign(LocalDate.of(2023,9,28)).mpAccount(10013142).isAvailable(true).build());
    }
}
