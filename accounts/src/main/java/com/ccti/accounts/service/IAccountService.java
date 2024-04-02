package com.ccti.accounts.service;

import com.ccti.accounts.dto.CustomerAccountDto;
import com.ccti.accounts.dto.CustomerDto;
import com.ccti.accounts.dto.ResponseDto;

public interface IAccountService {

    boolean updateAccount(CustomerAccountDto data) ;
    boolean  deleteAccountByEmail(String email) ;

    CustomerAccountDto fetchAccount(String email) ;

    void createAccount(CustomerDto customerDto)  ;
    ResponseDto deleteAccount(String email) ;

}
