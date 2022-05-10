package com.example.openevents.api;

import com.example.openevents.business.Event;
import com.example.openevents.business.Token;
import com.example.openevents.business.User;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JSONPlaceHolder {

    @POST("users/")
    Call<User> signUp(@Body User user);

    @POST("users/login/")
    Call<Token> logIn(@Body User user);

    @POST("events/")
    Call<Event> createEvent(@Header("Bearer") Token token, @Body Event event);

    @GET("events/")
    Call<ArrayList<Event>> showEvents();
}
