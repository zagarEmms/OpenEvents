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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_Up_Activity extends AppCompatActivity {

    EditText name;
    EditText last ;
    EditText email;
    EditText password;

    void changeActivity() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void setSignUp () {

        JSONObject signupInfo = new JSONObject();
        try {
            signupInfo.put("name", name.getText().toString());
            signupInfo.put("last_name", last.getText().toString());
            signupInfo.put("mail", email.getText().toString());
            signupInfo.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIClient.getInstance().signUp(signupInfo, new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                Log.i("GET","SIGN UP GET WENT WELL!" + response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Log.i("GET","SIGN UP KO!");
            }
        });
    }

    void setButton () {
        name = (EditText) findViewById(R.id.editName);
        last = (EditText) findViewById(R.id.editLast);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPass);

        Button signUpButton = findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSignUp();
                    changeActivity();
                }
            }
        );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setButton();
    }
}