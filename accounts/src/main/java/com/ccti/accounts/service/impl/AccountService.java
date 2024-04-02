package com.ccti.accounts.service.impl;

import com.ccti.accounts.dto.CustomerAccountDto;
import com.ccti.accounts.dto.ResponseDto;
import com.ccti.accounts.entity.Account;
import com.ccti.accounts.entity.Customer;
import com.ccti.accounts.exception.CustomerAlreadyExists;
import com.ccti.accounts.exception.ResourceNoFound;
import com.ccti.accounts.mapper.AccountMapper;
import com.ccti.accounts.mapper.CustomerMapper;
import com.ccti.accounts.repository.AccountRepository;
import com.ccti.accounts.repository.CustomerRepository;
import com.ccti.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.ccti.accounts.dto.CustomerDto ;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService {

    private AccountRepository accountRepository ;
    private CustomerRepository customerRepository ;


    @Override
    public CustomerAccountDto fetchAccount(String email) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNoFound("cliente", "email" , email)
        );

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNoFound( "cuenta" , "customerId" , customer.getCustomerId().toString())
        ) ;

        CustomerAccountDto data = new CustomerAccountDto() ;

        data.setAccount(AccountMapper.mapAccountToDto(account));
        data.setCustomer(CustomerMapper.mapCustomerToDto(customer));

        return data ;

    }

    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = createCustomer(customerDto);

        long accNumber = 10000000000L + new Random().nextInt(900000000) ;

        Account account = new Account () ;

        account.setCustomerId(customer.getCustomerId());
        account.setAccountType("saving") ;
        account.setAccountNumber(accNumber) ;
        account.setBranchAddress("Main Branch") ;

      //account.setCreatedBy("System2");
      //account.setCreatedAt(LocalDateTime.now());


        accountRepository.save(account) ;

    }

    private Customer createCustomer(CustomerDto customerDto) {

        Optional<Customer> existsCustomer =  customerRepository.findByEmail(customerDto.getEmail()) ;
        if (existsCustomer.isPresent()){
            throw new CustomerAlreadyExists("El Correo Electronico: "+ customerDto.getEmail() + " Ya existe !!") ;
        }


        Customer customer = CustomerMapper.mapCustomerDtoToCustomer(customerDto) ;

      //customer.setCreatedBy("System") ;
      //customer.setCreatedAt(LocalDateTime.now());

        return customerRepository.save(customer) ;

    }

    @Override
    public boolean updateAccount(CustomerAccountDto data) {
        Customer customer = customerRepository.findByEmail(data.getCustomer().getEmail()).orElseThrow(
                () -> new ResourceNoFound("cliente", "email", data.getCustomer().getEmail())
        );

        Customer updatedCustomer = CustomerMapper.mapUpdatingDtoTOCustomer(data.getCustomer(), customer);

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNoFound("cuenta", "customerId", customer.getCustomerId().toString())
        );

        Account updatedAccount = AccountMapper.mapUpdatingToAccount(data.getAccount(), account);

        accountRepository.save(updatedAccount);
        customerRepository.save(updatedCustomer);
        return true;

    }

    @Override
    public boolean deleteAccountByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNoFound("cliente", "email", email)
        );

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true ;

    }

    @Override
    public ResponseDto deleteAccount(String email) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNoFound("cliente", "email" , email)
        );

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNoFound( "cuenta" , "customerId" , customer.getCustomerId().toString())
        ) ;

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteByEmail(email) ;

        ResponseDto responseDto = new ResponseDto("200", "Cuenta Anulada !!") ;

        return responseDto ;

    }

}
