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

    public void signUp (UserCreation user, Callback<User> callback) {
        this.service.signUp(user).enqueue(callback);
    }

    public void logIn (User user, Callback<Token> callback) {
        this.service.logIn(user).enqueue(callback);
    }

    public void createEvent (String token, EventCreation event, Callback<Event> callback) {
        this.service.createEvent(token, event).enqueue(callback);
    }

    public void showEvents (String token, Callback<ArrayList<Event>> callback) {
        this.service.showEvents(token).enqueue(callback);
    }

    public void showPeople (String token, Callback<ArrayList<User>> callback) {
        this.service.showPeople(token).enqueue(callback);
    }

    public void showPeopleSearch (String token, String query,Callback<ArrayList<User>> callback) {
        this.service.showPeopleSearch(token, query).enqueue(callback);
    }

    public void showEventInfo(String token, int id, Callback<ArrayList<Event>> callback) {
        this.service.showEventInfo(token, id).enqueue(callback);
    }

    public void showPersonStatsInfo(String token, int id, Callback<Statistic> callback) {
        this.service.showPersonStatsInfo(token, id).enqueue(callback);
    }

    public void showFriendPerson(String token, int id, Callback<ArrayList<User>> callback) {
        this.service.showFriendPerson(token, id).enqueue(callback);
    }

    public void addFriendApi(String token, int id, Callback<UserEventRequest> callback) {
        this.service.addFriendApi(token, id).enqueue(callback);
    }

    public void joinEvent(String token, int id, Callback<UserEventRequest> callback) {
        this.service.joinEvent(token, id).enqueue(callback);
    }

    public void showEventsScore(String token, Callback<ArrayList<Event>> callback) {
        this.service.showEventsScore(token).enqueue(callback);
    }

    public void editEvent(String token, EventCreation event, int id, Callback<Event> callback) {
        this.service.editEvent(token, event, id).enqueue(callback);
    }

    public void deleteEvent(String token, int id, Callback<DeleteEvent> callback) {
        this.service.deleteEvent(token, id).enqueue(callback);
    }

    public void getProfileInfo(String token, int id, Callback<ArrayList<User>> callback){
        this.service.getProfileInfo(token, id).enqueue(callback);
    }

    public void showEventsSearched(String token, String keyword, String location, String date, Callback<ArrayList<Event>> callback) {
        this.service.showEventsSearched(token, keyword, location, date).enqueue(callback);
    }

    public void showMyFriends(String token, Callback<ArrayList<User>> callback) {
        this.service.showMyFriends(token).enqueue(callback);
    }

    public void isUserJoined(String token, int user_id, int event_id, Callback<Assistance> callback) {
        this.service.isUserJoined(token, user_id, event_id).enqueue(callback);
    }

    public void editProfile(String token, UserCreation user, Callback<User> callback) {
        this.service.editProfile(token, user).enqueue(callback);
    }

}
