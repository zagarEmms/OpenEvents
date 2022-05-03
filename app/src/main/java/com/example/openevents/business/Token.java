package com.example.openevents.business;

import java.io.Serializable;

public class Token implements Serializable {
    private String accessToken;

    public Token(String token_id) {
        this.accessToken = token_id;
    }

    public String getToken_id() {
        return accessToken;
    }

    public void setToken_id(String token_id) {
        this.accessToken = token_id;
    }
}
