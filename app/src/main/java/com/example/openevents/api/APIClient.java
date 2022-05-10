package com.example.openevents.api;

import com.example.openevents.business.Event;
import com.example.openevents.business.Token;
import com.example.openevents.business.User;

import org.json.JSONObject;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static APIClient shared;
    private Retrofit retrofit;
    private JSONPlaceHolder service;

    public static APIClient getInstance() {
        if (shared == null) {
            shared = new APIClient();
        }
        return shared;
    }

    public APIClient() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://puigmal.salle.url.edu/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.service = this.retrofit.create(JSONPlaceHolder.class);
    }

    public void signUp (User user, Callback<User> callback) {
        this.service.signUp(user).enqueue(callback);
    }

    public void logIn (User user, Callback<Token> callback) {
        this.service.logIn(user).enqueue(callback);
    }

    public void createEvent (String token, Event event, Callback<Event> callback) {
        this.service.createEvent(token, event).enqueue(callback);
    }

    public void showEvents(String token, Callback<ArrayList<Event>> callback) {
        this.service.showEvents(token).enqueue(callback);
    }

}
