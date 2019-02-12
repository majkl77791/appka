package com.michal.appka.rest;


import com.michal.appka.dao.IUserAccountDAO;
import com.michal.appka.entity.FacebookAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.michal.appka.entity.UserAccount;
import org.springframework.social.facebook.api.*;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

import java.util.List;

@RestController
@RequestMapping("/")
public class AppController {


    private IUserAccountDAO userAccountDAO;
    private Facebook facebook;
//    private ConnectionRepository connectionRepository;
//
//    private Users user;
    @Autowired
    public AppController(IUserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }


    @GetMapping("/users")
    public List<UserAccount> getUsers() {

       return null;

    }

    @PostMapping("/users")
    public UserAccount createUsers(@RequestBody FacebookAccess fbAccess) {

        Facebook facebook = new FacebookTemplate(fbAccess.getAccessToken());
        //   UserOperations userOperations = facebook.userOperations();
        //   UserAccount me = userOperations.getUserProfile();
        MediaOperations mediaOperations = facebook.mediaOperations();
        User me = facebook.fetchObject("me", User.class);
        UserAccount u = new UserAccount(me.getId(),me.getName());

        // return u;
        return userAccountDAO.saveUser(u);    }


}
