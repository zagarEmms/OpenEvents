package com.example.openevents.business;

public class UserCreation {

    private String name;
    private String last_name;
    private String password;
    private String email;
    private String image;

    public UserCreation(String name, String last_name, String password, String email, String image) {
        this.name = name;
        this.last_name = last_name;
        this.password = password;
        this.email = email;
        this.image = image;
    }

    public String getName() {
        return name;
    }
}
