package com.ccti.accounts.controller;

import com.ccti.accounts.dto.*;
import com.ccti.accounts.exception.CustomerAlreadyExists;
import com.ccti.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Validated
//@AllArgsConstructor
@RequiredArgsConstructor
@RestController
@Tag(
        name = "Controlador para servicio de cuantas",
        description = "Endpoints para las operaciones CRUD del servicio"
)
@RequestMapping(path = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {

    @NonNull
    private IAccountService iAccountService ;

    @Value("${build.version}")
    private String buildVersion ;

    @NonNull
    private Environment environment ;

    @NonNull
    private ContactInfoDto contactInfoDto ;

    @GetMapping("hello")
    public String helloEndPoint() {
        return "Hola desde Microservicios !!!";
    }

    //@GetMapping("/build-version")
    @GetMapping(value = "/build-version", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @GetMapping(value = "/java-home" , produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getJavaHome(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<ContactInfoDto> getContactInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(contactInfoDto);
    }

    @GetMapping("dateTime")
    public String dateTime() {
        return LocalDateTime.now().toString();
    }

    @Operation(
            summary = "Crear Cuentas",
            description = "Crea un Cliente y una Cuenta en el Sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created",
                    content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        iAccountService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201" , "La Cuenta ha sido creada correctamente !!")) ;

    }

    @GetMapping("/consultar")
    public ResponseEntity<Object> consultarAccount(
        @RequestParam
        @Email(message = "Debe especificar un email valido")
        String email) {
        CustomerAccountDto customerAccountDto = iAccountService.fetchAccount(email);

        HttpStatus userStatus = HttpStatus.OK;
        if (email == null || email.isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto("400" , "Parametro: Id Errado !!")) ;
        }

        if (customerAccountDto.getAccount() == null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto("200" , "La Cuenta No Encontrada !!")) ;
        }

        return new ResponseEntity<Object>(customerAccountDto,userStatus) ;
   }

   @GetMapping("/fetchAccount")
   public ResponseEntity<CustomerAccountDto> fetchAccount(
         @RequestParam
         @Email(message = "Debe especificar un email valido")
         String email) {
        CustomerAccountDto customerAccountDto = iAccountService.fetchAccount(email) ;

       return ResponseEntity
               .status(HttpStatus.OK)
               .body(customerAccountDto) ;

   }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<ResponseDto> deleteAccount(
        @RequestParam
        @Email(message = "Debe especificar un email valido")
        String email) {
        ResponseDto responseDto = iAccountService.deleteAccount(email) ;

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto) ;

    }

    @DeleteMapping("/deleteAccountByEmail")
    public ResponseEntity<ResponseDto> deleteAccountByMail(
            @RequestParam
            @Email(message = "Debe especificar un email valido")
            String email) {
        boolean isDeleted = iAccountService.deleteAccountByEmail(email) ;

        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto("200", "La Cuenta se ha elimnado")) ;
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto("500", "Ha ocurrido un error eliminando la Cuenta")) ;

        }

    }

        @PatchMapping("/updateByMail")
        public ResponseEntity<ResponseDto> updateByMail(
                @Valid @RequestBody CustomerAccountDto customerAccountDto) {

            boolean isUpdated = iAccountService.updateAccount(customerAccountDto);

            if (isUpdated) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new ResponseDto("200", "La cuenta se ha actualizado"));
            } else {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDto("500", "Ha ocurrido un error actualizando la cuenta"));
            }
        }






}
