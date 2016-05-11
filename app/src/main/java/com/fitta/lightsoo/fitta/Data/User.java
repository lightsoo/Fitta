package com.fitta.lightsoo.fitta.Data;

/**
 * Created by LG on 2016-04-18.
 */
public class User {

    public String authToken;
    public String authType;

    public User(String authToken, String authType){
        this.authToken = authToken;
        this.authType = authType;
    }

    public void setToken(String authToken) {
        this.authToken = authToken;
    }

    //getter
    public String getToken() {
        return authToken;
    }

    public String getType() {
        return authType;
    }
}
