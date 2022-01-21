package com.github.Elmicass.SFJTeam_Casotto.registration;

import java.time.LocalDateTime;

import com.github.Elmicass.SFJTeam_Casotto.email.EmailSender;
import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.ConfirmationToken;
import com.github.Elmicass.SFJTeam_Casotto.services.ConfirmationTokenServices;
import com.github.Elmicass.SFJTeam_Casotto.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    @Autowired
    private UserServices userServices;
    
    @Autowired
    private EmailValidatorService emailValidator;
    
    @Autowired
    private ConfirmationTokenServices confirmationTokenServices;
    
    @Autowired
    private EmailSender emailSender;

    public String register(RegistrationRequest request) throws AlreadyExistingException {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail)
            throw new IllegalArgumentException("The email is not valid.");
        String token = userServices.signUpUser(userServices.createUser(request));
        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                emailSender.getEmailBody(request.getFirstName(), link));
        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenServices.getToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found."));

        if (confirmationToken.getConfirmedAt() != null)
            throw new IllegalStateException("email already confirmed");

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now()))
            throw new IllegalStateException("Token expired.");

        confirmationTokenServices.setConfirmedAt(token);
        userServices.enableAppUser(confirmationToken.getUser().getEmail());
        return "Email confirmed! You can now proceed to login into the system.";
    }
}
