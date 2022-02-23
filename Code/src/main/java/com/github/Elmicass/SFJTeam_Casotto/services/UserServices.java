package com.github.Elmicass.SFJTeam_Casotto.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.ConfirmationToken;
import com.github.Elmicass.SFJTeam_Casotto.model.Privilege;
import com.github.Elmicass.SFJTeam_Casotto.model.Role;
import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.registration.RegistrationRequest;
import com.github.Elmicass.SFJTeam_Casotto.repository.IRolesRepository;
import com.github.Elmicass.SFJTeam_Casotto.repository.IUsersRepository;
import com.github.Elmicass.SFJTeam_Casotto.security.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;

@Service
@Transactional
public class UserServices implements IUserServices, UserDetailsService {

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IRolesRepository rolesRepository;

    @Autowired
    private ConfirmationTokenServices tokensServices;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public final static String USER_NOT_FOUND_MSG = "User with email %s not found.";

    @Override
    public User getInstance(Integer id) throws EntityNotFoundException {
        return usersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No users found with the given id: " + id));
    }

    @Override
    public List<User> getAll() {
        return usersRepository.findAll();
    }

    @Override
    public User save(User user) {
        return usersRepository.save(user);
    }

    @Override
    public User getUserByUsername(String email) throws UsernameNotFoundException {
        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));
        return user;
    }

    @Override
    public org.springframework.security.core.userdetails.User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), user.getPassword(), user.isEnabled(), true, true, 
            true, getAuthorities(user.getRoles()));
    }

	public Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
		return getGrantedAuthorities(getPrivileges(roles));
	}

    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    @Override
    public User createUser(@NonNull RegistrationRequest userData) throws AlreadyExistingException {
        if (existsByEmail(userData.getEmail()))
            throw new AlreadyExistingException("This email is already been used by someone else.");
        Role ROLE_USER = rolesRepository.findByName("ROLE_USER").get();
        User user = new User(userData.getFirstName(), userData.getLastName(), userData.getEmail(),
                userData.getPassword());
        user.addRole(ROLE_USER);
        return user;
    }

    public boolean delete(String email) throws UsernameNotFoundException {
        if (email.isBlank())
            throw new IllegalArgumentException("The username is empty");
        if (!(existsByEmail(email)))
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email));
        usersRepository.deleteByEmail(email);
        return !existsByEmail(email);
    }

    public boolean existsByEmail(String email) {
        if (email.isBlank())
            throw new IllegalArgumentException("The username is empty");
        return usersRepository.existsByEmail(email);
    }

    public String signUpUser(User user) throws AlreadyExistingException {
        String encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        usersRepository.save(user);
        ConfirmationToken confirmationToken = tokensServices.createConfirmationToken(user);
        tokensServices.save(confirmationToken);
        return confirmationToken.getToken();
    }

    public int enableAppUser(String email) {
        return usersRepository.enableUser(email);
    }

    @Override
    public boolean delete(Integer id) {
        if (id.toString().isBlank())
            throw new IllegalArgumentException("The user id is empty");
        if (!(exists(id)))
            throw new UsernameNotFoundException("User with id: " + id + " not found.");
        usersRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(Integer id) {
         if (id.toString().isBlank())
            throw new IllegalArgumentException("The user id is empty");
        return usersRepository.existsById(id);
    }


}
