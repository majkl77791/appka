package com.michal.appka.entity;

import javax.persistence.*;

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
 //   private String gender;

    public UserAccount() {
    }

    public UserAccount(String facebookId, String name) {
        this.facebookId = facebookId;
        this.name = name;
   //     this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfacebookId() {
        return facebookId;
    }

    public void setId(String facebookId) {
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
}
