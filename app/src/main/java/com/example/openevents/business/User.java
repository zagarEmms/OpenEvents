package com.example.openevents.business;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String last_name;
    private String password;
    private String email;
    private String image;

    public User(String name, String lastName, String email, String imageUrl) {
        this.name = name;
        this.last_name = lastName;
        this.email = email;
        this.image = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImageUrl(String imageUrl) {
        this.image = imageUrl;
    }
}
