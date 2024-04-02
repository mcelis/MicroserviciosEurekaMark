package com.ccti.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Account",
        description = "Esquema que almacena datos de Cuenta"
)
public class AccountDto {

    @Schema(
            description = "Número de la Cuenta en el Sistema"
    )
    @NotNull(message = "El numero de cuenta es requerido")
    //@Pattern(regexp = "(^$|[0-9]{10})", message = "El número de cuenta debe contener 10 digitos")
    private Long accountNumber ;

    @Schema(
            description = "Tipo de cuenta: 'Savings' , 'Checking'"
    )
    @NotEmpty(message = "El Tipo de cuenta es requerido")
    private String accountType ;

    @Schema(
            description = "Dirección de la Sucursal Bancaria"
    )
    @NotEmpty(message = "La direccion del scursal no puede estar en blanco")
    private String branchAddress ;


}
