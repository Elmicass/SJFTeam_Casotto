package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.model.User;

public interface IUserManager extends EntityManager<User, String> {

    boolean createNewAccount(String name, String surname, String email);

}
