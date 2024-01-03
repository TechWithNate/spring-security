package com.nate.spring.security.Spring.Security.Demo.registration;

import com.nate.spring.security.Spring.Security.Demo.appuser.AppUser;
import com.nate.spring.security.Spring.Security.Demo.appuser.AppUserRole;
import com.nate.spring.security.Spring.Security.Demo.appuser.AppUserService;
import com.nate.spring.security.Spring.Security.Demo.registration.token.ConfirmationToken;
import com.nate.spring.security.Spring.Security.Demo.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

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

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }


}
