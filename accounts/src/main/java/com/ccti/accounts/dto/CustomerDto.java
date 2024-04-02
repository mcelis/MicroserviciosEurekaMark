package com.ccti.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "El Nombre del Cliente es Requerido")
    @Size (min = 5 , max = 50, message = "El nombre debe contener entre 5 y 50 Caracteres")
    private String name ;

    @NotEmpty(message = "El Email  del Cliente es Requerido")
    @Email(message = "Debe especificar un email valido")
    private String email ;

    @NotEmpty(message = "El Numero de Telefono es requerido")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "El número Mobil debe contener 10 digitos")
    private String mobileNumber ;

}
