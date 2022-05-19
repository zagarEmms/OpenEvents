package com.example.openevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.openevents.api.APIClient;
import com.example.openevents.business.Event;
import com.example.openevents.business.EventCreation;
import com.example.openevents.business.User;
import com.example.openevents.business.UserCreation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Profile_Activity extends AppCompatActivity {

    private ArrayList<String> infoProfile = new ArrayList<>();
    private EditText name;
    private EditText last;
    private EditText email;
    private EditText password;
    private EditText image;

    private String nameOk;
    private String lastOk;
    private String emailOk;
    private String imageOk;

    public void changeActivity() {
        onBackPressed();
    }

    private String getToken() {

        SharedPreferences prefs = this.getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        return "Bearer " + prefs.getString("TOKEN","");

    }

    public void fillInfo () {

        name = (EditText) findViewById(R.id.editName);
        last = (EditText) findViewById(R.id.editLast);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPassword);
        image = (EditText) findViewById(R.id.editImage);

        nameOk = infoProfile.get(0);
        name.setHint(infoProfile.get(0));
        lastOk = infoProfile.get(1);
        last.setHint(infoProfile.get(1));
        emailOk = infoProfile.get(2);
        email.setHint(infoProfile.get(2));
        imageOk = infoProfile.get(3);
        image.setHint(infoProfile.get(3));

    }

    public void verifyChangedInfo () {

        if (!name.getText().toString().equals("")) {
            nameOk = name.getText().toString();
        }

        if (!last.getText().toString().equals("")) {
            lastOk = last.getText().toString();
        }

        if (!email.getText().toString().equals("")) {
            emailOk = email.getText().toString();
        }

        if (!image.getText().toString().equals("")) {
            imageOk = image.getText().toString();
        }
    }

    public void connectApi () {

        verifyChangedInfo();

        if (password.getText().toString().equals("")) {

            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "PLEASE ENTER YOUR PASSWORD", Toast.LENGTH_SHORT);

            toast1.setGravity(Gravity.TOP, 0, 0);
            toast1.show();

        } else {

            String passwordOk = password.getText().toString();
            UserCreation user = new UserCreation(nameOk, lastOk, passwordOk, emailOk, imageOk);

            APIClient.getInstance().editProfile(getToken(), user, new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    if (response.body() == null) {
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "SOMETHING MISSING!", Toast.LENGTH_SHORT);

                        toast1.setGravity(Gravity.TOP, 0, 0);
                        toast1.show();
                    } else {
                        changeActivity();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.i("GET","KO!" + t.getMessage());
                    Toast toast2 =
                            Toast.makeText(getApplicationContext(),
                                    "CONNECTION ERROR!", Toast.LENGTH_SHORT);

                    toast2.setGravity(Gravity.TOP, 0, 0);
                    toast2.show();
                }
            });

        }

    }

    public void setButton () {

        Button editProfileButton = (Button) findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  connectApi();

              }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        infoProfile = getIntent().getStringArrayListExtra("PROFILE_INFO");
        setButton();
        fillInfo();

    }
}