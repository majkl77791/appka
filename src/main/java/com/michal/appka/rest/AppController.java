package com.michal.appka.rest;


import com.michal.appka.entity.FacebookAccess;
import com.michal.appka.entity.UserAccount;
import com.michal.appka.entity.UserPhoto;
import com.michal.appka.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.*;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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

        final PagedList<Photo> photos = mediaOperations.getPhotos(me.getId());
        for (Photo photo:photos) {
            userAccount.addPhoto(new UserPhoto(photo.getLink(), photo.getSource(), photo.getAlbum().getName()));
            String photoId = photo.getId();

            final byte[] photoImage = mediaOperations.getPhotoImage(photoId);
            final String property = System.getProperty("user.dir");
            String photoPath = property + "\\photos\\" + photoId + ".jpg";
            System.out.println(photoPath);

            try {

                ByteArrayInputStream bis = new ByteArrayInputStream(photoImage);
                BufferedImage bImage2 = ImageIO.read(bis);

                ImageIO.write(bImage2, "jpg", new File(photoPath) );
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

   //     final byte[] userProfileImageByte = facebook.userOperations().getUserProfileImage();

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
