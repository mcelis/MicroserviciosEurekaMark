package com.ccti.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "FullCustomerDto",
        description = "Schema to hold All customer data including cards and loans"
)
@Data
public class FullCustomerDto {

    @Schema(
            description = "Details of the customer"
    )
    CustomerDto customer;

    @Schema(
            description = "Details of the Account"
    )
    AccountDto account;

    @Schema(
            description = "Details of the Account"
    )
    CardsDto cards;

    @Schema(
            description = "Details of the Loan"
    )
    LoansDto loan;

}
