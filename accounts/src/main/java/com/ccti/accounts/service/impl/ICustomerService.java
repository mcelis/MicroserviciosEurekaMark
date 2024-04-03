package com.ccti.accounts.service.impl;

import com.ccti.accounts.dto.FullCustomerDto;

public interface ICustomerService {

    FullCustomerDto fetchFullCustomer(String email);
}
