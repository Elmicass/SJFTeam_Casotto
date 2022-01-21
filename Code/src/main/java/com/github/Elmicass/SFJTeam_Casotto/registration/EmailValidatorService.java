package com.github.Elmicass.SFJTeam_Casotto.registration;

import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import org.apache.commons.validator.routines.EmailValidator;

@Service
public class EmailValidatorService implements Predicate<String> {
    
    @Override
    public boolean test(String email) {
        boolean valid = EmailValidator.getInstance().isValid(email);
        return valid;
    }

}