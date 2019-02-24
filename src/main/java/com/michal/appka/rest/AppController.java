package com.michal.appka.rest;


import com.michal.appka.entity.FacebookAccess;
import com.michal.appka.entity.UserAccount;
import com.michal.appka.entity.UserPhoto;
import com.michal.appka.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.*;
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
    public void createUsers(@RequestBody FacebookAccess fbAccess) {


        Facebook facebook = new FacebookTemplate(fbAccess.getAccessToken());
        User me = facebook.fetchObject(fbAccess.getFacebookId(), User.class);

        String pictureURL = facebook.getBaseGraphApiUrl()+me.getId()+"/picture?type=square";
        UserAccount userAccount = new UserAccount(me.getId(),me.getName(), pictureURL);

        final MediaOperations mediaOperations = facebook.mediaOperations();
        final LikeOperations likeOperations = facebook.likeOperations();

        final PagedList<Photo> photos = mediaOperations.getPhotos(me.getId());
        for (Photo photo:photos) {
            userAccount.addPhoto(new UserPhoto(photo.getLink(), photo.getSource(), photo.getAlbum().getName()));

//      FOLLOWING LINE RETURN 0 FOR EVERY PHOTO, PROBABLY DOESNT RECOGNIZE OBJECT ID AS PHOTO ID
//      System.out.println(likeOperations.getLikes(photo.getId()).size());

//      FOLLOWING LINE RETURNS NULL, IN THIS VERSION OF SPRING SOCIAL API YOU CANT GET GENDER OF USER
//      System.out.println(me.getGender());

//      UNCOMMENT FOLLOWING LINES TO GET PHOTOS FROM USER AND SAVE IT TO YOUR LOCAL DISK IN THIS JAVA PROJECT FOLDER\PHOTOS
/*
            String photoId = photo.getId();
            final byte[] photoImage = mediaOperations.getPhotoImage(photoId);
            final String property = System.getProperty("user.dir");
            String photoPath = property + "\\photos\\" + photoId + ".jpg";
            try {

                ByteArrayInputStream bis = new ByteArrayInputStream(photoImage);
                BufferedImage bImage2 = ImageIO.read(bis);
                ImageIO.write(bImage2, "jpg", new File(photoPath) );
            } catch (IOException e) {
                e.printStackTrace();
            }
*/
        }


        if(userAccountService.saveUser(userAccount) == null){
            throw new RuntimeException("User already exist!");
        }

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
