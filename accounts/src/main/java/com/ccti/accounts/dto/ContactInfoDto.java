package com.ccti.accounts.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "contact")
@Setter
@Getter
public class ContactInfoDto{

private String message;
private Map<String, String> details;
private List<String> phoneNumbers;

}
