package com.nate.spring.security.Spring.Security.Demo.registration;

import org.springframework.stereotype.Service;

@Service
public class RegistrationService {


    public String register(RegistrationRequest request) {
        return "This is working so far";
    }
}
