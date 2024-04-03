package com.ccti.accounts;

import com.ccti.accounts.dto.ContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
		info = @Info(
				title = "Restrictive Bank System",
				description = "Proyecto Ejemplo para curso SpringBoot",
				version = "v1",
				contact = @Contact(
						name = "Marco Celis",
						email = "mcelis@globalhitss.com",
						url = "https://www.abc.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.globalhitss.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Proyecto para curso de micro servicios con Spring",
				url = "http://localhost:8080/swagger-ui/index.html"
		)

)
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@EnableConfigurationProperties(value = ContactInfoDto.class)
public class AccountsApplication {

	public static void main(String[] args) {

		SpringApplication.run(AccountsApplication.class, args);

	}

}
