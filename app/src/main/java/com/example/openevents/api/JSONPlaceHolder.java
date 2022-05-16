package com.example.openevents.api;

import com.example.openevents.business.Assistance;
import com.example.openevents.business.Event;
import com.example.openevents.business.EventCreation;
import com.example.openevents.business.Statistic;
import com.example.openevents.business.Token;
import com.example.openevents.business.User;
import com.example.openevents.business.UserEventRequest;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceHolder {

    @POST("users/")
    Call<User> signUp(@Body User user);

    @POST("users/login/")
    Call<Token> logIn(@Body User user);

    @POST("events/")
    Call<Event> createEvent(@Header("Authorization") String token, @Body EventCreation event);

    @GET("events/")
    Call<ArrayList<Event>> showEvents (@Header("Authorization") String token);

    @GET("events/{id}")
    Call<ArrayList<Event>> showEventInfo(@Header("Authorization") String token, @Path("id") int id);

    @GET("users/")
    Call<ArrayList<User>> showPeople (@Header("Authorization") String token);

    @GET("users/search/")
    Call<ArrayList<User>> showPeopleSearch (@Header("Authorization") String token, @Query("s") String query);

    @GET("users/{id}/statistics")
    Call<Statistic> showPersonInfo (@Header("Authorization") String token, @Query("id") int id);

    @GET("users/{id}/friends")
    Call<ArrayList<User>> showFriendPerson(@Header("Authorization") String token, @Query("id") int id);

    @POST("friends/{id}")
    Call<UserEventRequest> addFriendApi(@Header("Authorization") String token, @Query("id") int id);

    @POST("users/{id}/assistances")
    Call<UserEventRequest> joinEvent(@Header("Authorization") String token, @Query("id") int id);

    @PUT("/assistances/{user_id}/{event_id}/")
    Call<ArrayList<User>> postComment (@Header("Authorization") String token, @Body Assistance assistance, @Path("user_id") int user_id, @Path("event_id") int event_id);

}
