package com.michal.appka.rest;


import com.michal.appka.entity.FacebookAccess;
import com.michal.appka.entity.UserAccount;
import com.michal.appka.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.MediaOperations;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class AppController {


    private IUserAccountService userAccountService;
    private Facebook facebook;

    @Autowired
    public AppController(IUserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }


    @GetMapping("/users")
    public List<UserAccount> getUsers() {

       return userAccountService.getUsers();

    }

    @PostMapping("/users")
    public UserAccount createUsers(@RequestBody FacebookAccess fbAccess) {

        Facebook facebook = new FacebookTemplate(fbAccess.getAccessToken());

        MediaOperations mediaOperations = facebook.mediaOperations();
        User me = facebook.fetchObject("me", User.class);

        UserAccount userAccount = new UserAccount(me.getId(),me.getName());


        if(userAccountService.saveUser(userAccount) == null){
            throw new RuntimeException("User already exist!");
        }
        return userAccount;
    }

    @GetMapping("/users/{user_fb_id}")
    public UserAccount getUser(@PathVariable String user_fb_id){
        UserAccount userAccount = userAccountService.getUser(user_fb_id);
        if (userAccount == null) {
            throw new RuntimeException("User " + user_fb_id + " not found!");
        }
        return userAccount;
    }

    @DeleteMapping("/users/{user_fb_id}")
    public void deleteUser(@PathVariable String user_fb_id){
        UserAccount userAccount = userAccountService.getUser(user_fb_id);
        if (userAccount == null) {
            throw new RuntimeException("User " + user_fb_id + " not found!");
        }
        userAccountService.deleteUser(user_fb_id);
    }


}
