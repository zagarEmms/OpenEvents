package com.example.openevents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class Sign_Up_Activity extends AppCompatActivity {

    public void setSignUp () {

        APIclient.getInstance().getTodo(new Callback<ArrayList<Task>>() {
            @Override
            public void onResponse(Call<ArrayList<Task>> call, Response<ArrayList<Task>> response) {
                Log.i("GET","GET WENT WELL!" + response.body());
                taskArrayList =  response.body();
                updateUI();
            }

            @Override
            public void onFailure(Call<ArrayList<Task>> call, Throwable t) {
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