package com.github.Elmicass.SFJTeam_Casotto.services;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.email.EmailSender;
import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.ConfirmationToken;
import com.github.Elmicass.SFJTeam_Casotto.model.Role;
import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.registration.RegistrationRequest;
import com.github.Elmicass.SFJTeam_Casotto.repository.IConfirmationTokenRepository;
import com.github.Elmicass.SFJTeam_Casotto.repository.IRolesRepository;
import com.github.Elmicass.SFJTeam_Casotto.repository.IUsersRepository;
import com.github.Elmicass.SFJTeam_Casotto.security.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.NonNull;

@Service
public class UserServices implements IUserServices, UserDetailsService {

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IRolesRepository rolesRepository;

    @Autowired
    private IConfirmationTokenRepository tokensRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSender emailSender;

    public final static String USER_NOT_FOUND_MSG = "User with email %s not found.";

    @Override
    public User getInstance(String id) throws EntityNotFoundException {
        return usersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No users found with the given id: " + id));
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));
        return user;
    }

    @Override
    public User createUser(@NonNull RegistrationRequest userData) throws AlreadyExistingException {
        if (exists(userData.getEmail()))
            throw new AlreadyExistingException("This email is already been used by someone else.");
        Role ROLE_USER = rolesRepository.findByName("ROLE_USER");
        User user = new User(userData.getFirstName(), userData.getLastName(), userData.getEmail(),
                userData.getPassword());
        user.addRole(ROLE_USER);
        return usersRepository.save(user);
    }

    @Override
    public boolean delete(String email) throws UsernameNotFoundException {
        if (email.isBlank())
            throw new IllegalArgumentException("The username is empty");
        if (!(exists(email)))
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email));
        usersRepository.deleteByEmail(email);
        return !exists(email);
    }

    @Override
    public boolean exists(String email) {
        if (email.isBlank())
            throw new IllegalArgumentException("The username is empty");
        return usersRepository.existsByEmail(email);
    }

    public String signUpUser(User user) throws AlreadyExistingException {
        boolean userExists = exists(user.getEmail());
        if (userExists) {
            if (usersRepository.findByEmail(user.getEmail()).get().getName().equals(user.getName()))
                if (usersRepository.findByEmail(user.getEmail()).get().getSurname().equals(user.getSurname()))
                    if (!(usersRepository.findByEmail(user.getEmail()).get().isEnabled())) {
                        String token = UUID.randomUUID().toString();
                        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
                        emailSender.send(user.getEmail(), emailSender.getEmailBody(user.getName(), link));
                    }
            throw new AlreadyExistingException(
                    "There is already an account with that email address: " + user.getEmail());
        }
        String encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        usersRepository.save(user);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(20),
                user);
        tokensRepository.save(confirmationToken);
        return token;
    }

    public int enableAppUser(String email) {
        return usersRepository.enableUser(email);
    }


}
