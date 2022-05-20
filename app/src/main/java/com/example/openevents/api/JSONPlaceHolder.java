package com.example.openevents.api;

import com.example.openevents.business.Assistance;
import com.example.openevents.business.DeleteEvent;
import com.example.openevents.business.Event;
import com.example.openevents.business.EventCreation;
import com.example.openevents.business.Statistic;
import com.example.openevents.business.Token;
import com.example.openevents.business.User;
import com.example.openevents.business.UserCreation;
import com.example.openevents.business.UserEventRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceHolder {

    @POST("users/")
    Call<User> signUp(@Body UserCreation user);

    @POST("users/login/")
    Call<Token> logIn(@Body User user);

    @POST("events/")
    Call<Event> createEvent(@Header("Authorization") String token, @Body EventCreation event);

    @GET("events/")
    Call<ArrayList<Event>> showEvents (@Header("Authorization") String token);

    @GET("events/best")
    Call<ArrayList<Event>> showEventsScore (@Header("Authorization") String token);

    @GET("events/{id}")
    Call<ArrayList<Event>> showEventInfo(@Header("Authorization") String token, @Path("id") int id);

    @GET("users/")
    Call<ArrayList<User>> showPeople (@Header("Authorization") String token);

    @GET("users/search/")
    Call<ArrayList<User>> showPeopleSearch (@Header("Authorization") String token, @Query("s") String query);

    @GET("users/{id}/statistics")
    Call<Statistic> showPersonStatsInfo (@Header("Authorization") String token, @Path("id") int id);

    @GET("users/{id}/friends")
    Call<ArrayList<User>> showFriendPerson(@Header("Authorization") String token, @Path("id") int id);

    @POST("friends/{id}")
    Call<UserEventRequest> addFriendApi(@Header("Authorization") String token, @Path("id") int id);

    @POST("events/{id}/assistances")
    Call<UserEventRequest> joinEvent(@Header("Authorization") String token, @Path("id") int id);

    @PUT("assistances/{user_id}/{event_id}/")  //currently in development
    Call<ArrayList<User>> postComment (@Header("Authorization") String token, @Body Assistance assistance, @Path("user_id") int user_id, @Path("event_id") int event_id);

    @PUT("events/{id}")
    Call<Event> editEvent (@Header("Authorization") String token, @Body EventCreation event, @Path("id") int id);

    @DELETE("events/{id}")
    Call<DeleteEvent> deleteEvent (@Header("Authorization") String token, @Path("id") int id);

    @GET("users/{id}")
    Call<ArrayList<User>> getProfileInfo(@Header("Authorization") String token, @Path("id") int id);

    @GET("events/search/")
    Call<ArrayList<Event>> showEventsSearched(@Header("Authorization") String token, @Query("location") String location, @Query("keyword") String keyword, @Query("date") String date);

    @GET("assistances/{user_id}/{event_id}")
    Call<Assistance> isUserJoined(@Header("Authorization") String token, @Path("user_id") int user_id, @Path("event_id") int event_id);

    @GET("friends/")
    Call<ArrayList<User>> showMyFriends(@Header("Authorization") String token);

    @PUT("users")
    Call<User> editProfile(@Header("Authorization") String token, @Body UserCreation user);

    @DELETE("assistances/{user_id}/{event_id}")
    Call<UserEventRequest> unJoinEvent(@Header("Authorization") String token, @Path("user_id") int user_id, @Path("event_id") int event_id);

}
