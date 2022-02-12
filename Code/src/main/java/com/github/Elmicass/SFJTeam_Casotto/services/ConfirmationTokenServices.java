package com.github.Elmicass.SFJTeam_Casotto.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.ConfirmationToken;
import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.repository.IConfirmationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServices implements EntityServices<ConfirmationToken, String> {

    @Autowired
    private IConfirmationTokenRepository ctRepository;

    private final static String tokensLink = "http://localhost:8080/api/v1/registration/confirm?token="; 

    public String getTokensLink() {
        return tokensLink;    
    }

    public String generateFullTokenLink() {
        String token = UUID.randomUUID().toString();
        String link = tokensLink + token;
        return link;
    }

    public ConfirmationToken createConfirmationToken(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(20),
                user);
        return confirmationToken;
    }

    public void saveConfirmationToken(ConfirmationToken token) {
        ctRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return ctRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return ctRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

    @Override
    public ConfirmationToken getInstance(String token) throws EntityNotFoundException {
        return ctRepository.findByToken(token).get();
    }

    @Override
    public List<ConfirmationToken> getAll() {
        return ctRepository.findAll();
    }

    @Override
    public boolean delete(String token) {
        if (token.isBlank()) throw new IllegalArgumentException("The token is empty");
        if (!(exists(token))) throw new EntityNotFoundException("The token: " + token + "does not exist.");
        ctRepository.deleteByToken(token);
        return !exists(token);
    }

    @Override
    public boolean exists(String token) {
        if (token.isBlank()) throw new IllegalArgumentException("The token is empty");
        return ctRepository.existsByToken(token);
    }
    
}
