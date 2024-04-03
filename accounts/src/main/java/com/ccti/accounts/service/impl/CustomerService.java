package com.ccti.accounts.service.impl;

import com.ccti.accounts.dto.CardsDto;
import com.ccti.accounts.dto.FullCustomerDto;
import com.ccti.accounts.dto.LoansDto;
import com.ccti.accounts.entity.Account;
import com.ccti.accounts.entity.Customer;
import com.ccti.accounts.exception.ResourceNoFound;
import com.ccti.accounts.mapper.AccountMapper;
import com.ccti.accounts.mapper.CustomerMapper;
import com.ccti.accounts.repository.AccountRepository;
import com.ccti.accounts.repository.CustomerRepository;
import com.ccti.accounts.service.impl.ICustomerService;
import com.ccti.accounts.service.client.CardsFeignClient;
import com.ccti.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService implements ICustomerService {

    private AccountRepository accountsRepository;
    private CustomerRepository customerRepository;
    private LoansFeignClient loansFeignClient;
    private CardsFeignClient cardsFeignClient;

    @Override
    public FullCustomerDto fetchFullCustomer(String email) {

        FullCustomerDto fullCustomerDto = new FullCustomerDto();

        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNoFound("Customer", "email", email)
        );

        fullCustomerDto.setCustomer(CustomerMapper.mapCustomerToDto(customer));

        Account account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNoFound("Account", "customerId", customer.getCustomerId().toString())
        );

        fullCustomerDto.setAccount(AccountMapper.mapAccountToDto(account));

        ResponseEntity<LoansDto> loansDtoResponse = loansFeignClient.fetchLoanDetails(email);
        if (loansDtoResponse != null)
            fullCustomerDto.setLoan(loansDtoResponse.getBody());

        ResponseEntity<CardsDto> cardDtoResponse = cardsFeignClient.fetchCardDetails(email);
        if (cardDtoResponse != null)
            fullCustomerDto.setCards(cardDtoResponse.getBody());

        return fullCustomerDto;
    }

}
