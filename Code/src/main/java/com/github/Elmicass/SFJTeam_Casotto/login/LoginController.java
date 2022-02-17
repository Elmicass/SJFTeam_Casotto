package com.github.Elmicass.SFJTeam_Casotto.login;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    @Autowired
    private LoginService service;

    public boolean login(LoginRequest request) throws LoginException {
        return service.login(request);
    }

    public void logout() {
        service.logout();
    }


    
}
