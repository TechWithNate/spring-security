package com.nate.spring.security.Spring.Security.Demo.registration;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String firstname;
    private final String lastname;
    private final String password;
    private final String email;
}
