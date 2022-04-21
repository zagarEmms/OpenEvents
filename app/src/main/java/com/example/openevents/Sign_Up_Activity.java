package com.example.openevents;

import androidx.appcompat.app.AppCompatActivity;

import com.example.openevents.api.APIClient;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_Up_Activity extends AppCompatActivity {



    public void setSignUp () {

        APIClient.getInstance().getTodo(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                Log.i("GET","GET WENT WELL!" + response.body());
                taskArrayList =  response.body();
                updateUI();
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Log.i("GET","KO!");
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setSignUp();
    }
}