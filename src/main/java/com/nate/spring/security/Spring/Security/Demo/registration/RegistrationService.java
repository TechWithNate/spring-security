package com.nate.spring.security.Spring.Security.Demo.registration;

import com.nate.spring.security.Spring.Security.Demo.appuser.AppUser;
import com.nate.spring.security.Spring.Security.Demo.appuser.AppUserRole;
import com.nate.spring.security.Spring.Security.Demo.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail){
            throw new IllegalStateException("Invalid Email");
        }
        return appUserService.signUpUser(new AppUser(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER
        ));
    }
}
