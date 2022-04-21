package com.example.openevents.api;
import retrofit2.Retrofit;

public class APIClient {

    private static APIClient shared;

    private Retrofit retrofit;
    private JsonPlaceholderAPI service;

    public static APIClient getInstance(){
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
        this.service = this.retrofit.create(JsonPlaceholderAPI.class);
    }

}
