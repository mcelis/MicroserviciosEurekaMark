package com.ccti.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNoFound extends RuntimeException{

    public ResourceNoFound(String resourceName, String fieldName, String fieldValue) {

        super(String.format("No se encontró ningún %s con el valor %s en el campo %s" ,
         resourceName, fieldName , fieldValue  )) ;
    }

}
