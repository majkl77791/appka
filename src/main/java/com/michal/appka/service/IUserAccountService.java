package com.michal.appka.service;

import com.michal.appka.entity.UserAccount;

import java.util.List;

public interface IUserAccountService {

    public List<UserAccount> getUsers();

    public UserAccount saveUser(UserAccount theUser);

    public UserAccount getUser(String facebookId);

    public void deleteUser(String facebookId);
}
