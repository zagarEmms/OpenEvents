package com.example.openevents.api;

import com.example.openevents.business.Token;
import com.example.openevents.business.User;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JSONPlaceHolder {

    @POST("users")
    Call<User> signUp(@Body JSONObject signupInfo);

    @POST("users/login/")
    Call<Token> logIn(@Body JSONObject loginInfo);

}
