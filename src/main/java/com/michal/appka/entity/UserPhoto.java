package com.michal.appka.entity;


import javax.persistence.*;

@Entity
@Table(name = "user_photo")
public class UserPhoto {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="facebook_URL")
    private String facebookURL;
    @Column(name="image_URL")
    private String imageURL;
    @Column(name="album_name")
    private String albumName;

    public UserPhoto(){

    }

    public UserPhoto(String facebookURL, String imageURL, String albumName) {
        this.facebookURL = facebookURL;
        this.imageURL = imageURL;
        this.albumName = albumName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFacebookURL() {
        return facebookURL;
    }

    public void setFacebookURL(String facebookURL) {
        this.facebookURL = facebookURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}
