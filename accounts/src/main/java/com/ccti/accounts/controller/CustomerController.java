package com.ccti.accounts.controller;

import com.ccti.accounts.dto.CustomerAccountDto;
import com.ccti.accounts.dto.FullCustomerDto;
import com.ccti.accounts.service.impl.ICustomerService;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerController {

    private final ICustomerService customerService;

    @GetMapping("/fetchFullCustomer")
    public ResponseEntity<FullCustomerDto> fetchFullCustomer(
            @RequestParam
            @Email(message = "Debe especificar un email valido")
            String email) {

        FullCustomerDto fullCustomerDto = customerService.fetchFullCustomer(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fullCustomerDto);
    }
}
