package com.example.openevents.api;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JSONPlaceHolder {

    @POST("users")
    Call<ArrayList<String>> signUp();

}
