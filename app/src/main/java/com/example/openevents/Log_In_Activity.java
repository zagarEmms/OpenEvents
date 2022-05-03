package com.example.openevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.openevents.api.APIClient;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Log_In_Activity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    private void changeActivitySignUp () {
        Intent intent = new Intent(this, Sign_Up_Activity.class);
        startActivity(intent);
    }

    private void changeActivityHome() {
        Intent intent = new Intent(Log_In_Activity.this, HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void setLogIn() {

        JSONObject loginInfo = new JSONObject();

        try {
            loginInfo.put("email", email.getText().toString());
            loginInfo.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIClient.getInstance().logIn(loginInfo, new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                Log.i("GET","LOG IN GET WENT WELL!" + response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Log.i("GET","LOG IN KO!");
            }
        });

    }

    private void setButton () {
        email = (EditText) findViewById(R.id.log_in_mail);
        password = (EditText) findViewById(R.id.log_in_password);

        Button logInButton = findViewById(R.id.log_in_button);
        logInButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     setLogIn();
                     changeActivityHome();

                 }
             }
        );

        Button signUpButton = findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     changeActivitySignUp();
                 }
            }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setButton();
    }
}