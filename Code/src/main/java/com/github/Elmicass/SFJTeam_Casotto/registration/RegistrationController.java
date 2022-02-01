package com.github.Elmicass.SFJTeam_Casotto.registration;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    public boolean register(RegistrationRequest userData) throws AlreadyExistingException {
        if (registrationService.register(userData) != null)
            return true;
        else return false;
    }

}
