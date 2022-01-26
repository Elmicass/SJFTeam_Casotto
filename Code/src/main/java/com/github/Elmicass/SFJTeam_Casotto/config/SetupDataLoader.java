package com.github.Elmicass.SFJTeam_Casotto.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.model.PriceList;
import com.github.Elmicass.SFJTeam_Casotto.model.Privilege;
import com.github.Elmicass.SFJTeam_Casotto.model.Role;
import com.github.Elmicass.SFJTeam_Casotto.repository.IPriceListRepository;
import com.github.Elmicass.SFJTeam_Casotto.repository.IPrivilegesRepository;
import com.github.Elmicass.SFJTeam_Casotto.repository.IRolesRepository;
import com.github.Elmicass.SFJTeam_Casotto.repository.IUsersRepository;
import com.github.Elmicass.SFJTeam_Casotto.security.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private IUsersRepository userRepository;

    @Autowired
    private IRolesRepository roleRepository;

    @Autowired
    private IPrivilegesRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IPriceListRepository priceListRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        Privilege everyonesReadPrivilege = createPrivilegeIfNotFound("EVERYONE_READ_PRIVILEGE");
        Privilege customersReadPrivilege = createPrivilegeIfNotFound("CUSTOMER_READ_PRIVILEGE");
        Privilege employeesReadPrivilege = createPrivilegeIfNotFound("EMPLOYEE_READ_PRIVILEGE");
        Privilege managersReadPrivilege = createPrivilegeIfNotFound("MANAGER_READ_PRIVILEGE");
        Privilege usersWritePrivilege = createPrivilegeIfNotFound("USER_WRITE_PRIVILEGE");
        Privilege customersWritePrivilege = createPrivilegeIfNotFound("CUSTOMER_WRITE_PRIVILEGE");
        Privilege employeesWritePrivilege = createPrivilegeIfNotFound("EMPLOYEE_WRITE_PRIVILEGE");
        Privilege managersWritePrivilege = createPrivilegeIfNotFound("MANAGER_WRITE_PRIVILEGE");
        Set<Privilege> usersPrivileges = new HashSet<>(Arrays.asList(everyonesReadPrivilege, usersWritePrivilege));
        Set<Privilege> customersPrivileges = new HashSet<>(
                Arrays.asList(everyonesReadPrivilege, usersWritePrivilege, customersWritePrivilege));
        Set<Privilege> employeesPrivileges = new HashSet<>(Arrays.asList(everyonesReadPrivilege, customersReadPrivilege,
                employeesReadPrivilege, employeesWritePrivilege));
        Set<Privilege> managersPrivileges = new HashSet<>(Arrays.asList(everyonesReadPrivilege, customersReadPrivilege,
                employeesReadPrivilege, managersReadPrivilege, employeesWritePrivilege, managersWritePrivilege));
        createRoleIfNotFound("ROLE_MANAGER", managersPrivileges);
        createRoleIfNotFound("ROLE_EMPLOYEE", employeesPrivileges);
        createRoleIfNotFound("ROLE_CUSTOMER", customersPrivileges);
        createRoleIfNotFound("ROLE_USER", usersPrivileges);
        Role managerRole = roleRepository.findByName("ROLE_MANAGER");
        User manager = new User("managerName","managerSurname","casottosmartchalet@gmail.com","IdSProject");
        manager.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(manager.getPassword()));
        manager.setRoles(new HashSet<>(Arrays.asList(managerRole)));
        manager.setEnabled(true);
        userRepository.save(manager);
        createDefaultPriceListIfNotFound("DEFAULT");
        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Set<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    PriceList createDefaultPriceListIfNotFound(String name) {
        PriceList defaultList = priceListRepository.findByName("DEFAULT");
        if (defaultList == null) {
            defaultList = new PriceList(name, 2.00, 2.50, 5.00, 10.00);
            priceListRepository.save(defaultList);
        }
        return defaultList;
    }

    
    
}
