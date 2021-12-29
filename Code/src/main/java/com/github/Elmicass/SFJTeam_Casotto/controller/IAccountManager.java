package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.model.Account;

public interface IAccountManager extends EntityManager<Account, String> {

    boolean createNewAccount(String name, String surname, String email);

}
