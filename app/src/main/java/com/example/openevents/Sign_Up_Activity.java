package com.example.openevents;

import androidx.appcompat.app.AppCompatActivity;

import com.example.openevents.api.APIClient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.openevents.api.APIClient;
import com.example.openevents.business.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_Up_Activity extends AppCompatActivity {

    private EditText name;
    private EditText last;
    private EditText email;
    private EditText password;

    public void changeActivity() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void setSignUp () {

        name = (EditText) findViewById(R.id.editName);
        last = (EditText) findViewById(R.id.editLast);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPass);

        Log.i("INFO","name: " + name.getText().toString());
        Log.i("INFO","password: " + password.getText().toString());

        User user = new User(name.getText().toString(), last.getText().toString(), email.getText().toString(), password.getText().toString(), name.getText().toString());

        APIClient.getInstance().signUp(user, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("GET","GET WENT WELL!\n" + response.body().getEmail());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("GET","KO!");
            }
        });
    }

    public void setButton () {

        Button signUpButton = findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSignUp();
                    //changeActivity();
                }
            }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setButton();
        Log.i("GET","BUTTON ASSIGNED!");
    }
}