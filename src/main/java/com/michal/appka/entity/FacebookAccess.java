package com.michal.appka.entity;

public class FacebookAccess {
    private String accessToken;
    private String facebookId;

    public FacebookAccess(){

    }

    public FacebookAccess(String accessToken, String facebookId) {
        this.accessToken = accessToken;
        this.facebookId = facebookId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }
}
