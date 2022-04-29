package com.example.openevents.api;
import com.google.gson.JsonObject;

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
                .baseUrl("http://puigmal.salle.url.edu/api/v2")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.service = this.retrofit.create(JSONPlaceHolder.class);
    }

    public void signUp (JsonObject signupInfo, Callback<ArrayList<String>> callback) {
        this.service.signUp(signupInfo).enqueue(callback);
    }

}
