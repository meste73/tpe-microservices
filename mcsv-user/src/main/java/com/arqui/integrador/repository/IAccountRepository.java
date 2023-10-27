package com.arqui.integrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arqui.integrador.model.Account;

public interface IAccountRepository extends JpaRepository<Account, Long>{

}
