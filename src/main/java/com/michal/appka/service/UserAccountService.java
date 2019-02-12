package com.michal.appka.service;

import com.michal.appka.dao.IUserAccountDAO;
import com.michal.appka.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserAccountService implements IUserAccountService {

    private IUserAccountDAO userAccountDAO;

    @Autowired
    public UserAccountService(IUserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    @Override
    @Transactional
    public List<UserAccount> getUsers() {
        return userAccountDAO.getUsers();
    }

    @Override
    @Transactional
    public UserAccount saveUser(UserAccount theUser) {
        return userAccountDAO.saveUser(theUser);
    }

    @Override
    @Transactional
    public UserAccount getUser(String facebookId) {
        return userAccountDAO.getUser(facebookId);
    }

    @Override
    @Transactional
    public void deleteUser(String facebookId) {
        userAccountDAO.deleteUser(facebookId);

    }
}
