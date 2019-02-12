package com.michal.appka.dao;

import com.michal.appka.entity.UserAccount;


import java.util.List;

public interface IUserAccountDAO {

    public List<UserAccount> getUsers();

    public UserAccount saveUser(UserAccount theUser);

    public UserAccount getUser(int theId);

    public void deleteUser(int theId);
}
