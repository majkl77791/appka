package com.michal.appka.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="facebook_id")
    private String facebookId;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    private List<UserPhoto> userPhotos;

    //private String gender;

    public UserAccount() {
    }

    public UserAccount(String facebookId, String name) {
        this.facebookId = facebookId;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getGender() { return gender; }
//
//    public void setGender(String gender) { this.gender = gender; }


    public List<UserPhoto> getUserPhotos() {
        return userPhotos;
    }

    public void setUserPhotos(List<UserPhoto> userPhotos) {
        this.userPhotos = userPhotos;
    }

    public void addPhoto(UserPhoto userPhoto){
        if (userPhotos == null){
            userPhotos = new ArrayList<>();
        }

        userPhotos.add(userPhoto);
    }
}
