package com.example.openevents;

import androidx.appcompat.app.AppCompatActivity;

import com.example.openevents.api.APIClient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.openevents.business.User;
import com.example.openevents.business.UserCreation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_Up_Activity extends AppCompatActivity {

    private EditText name;
    private EditText last;
    private EditText email;
    private EditText password;
    private EditText password2;

    public void changeActivity() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void setSignUp () {

        UserCreation user = new UserCreation(name.getText().toString(), last.getText().toString(), password.getText().toString(), email.getText().toString(), "imageTest");

        if (password.getText().toString().equals(password2.getText().toString())) {

            APIClient.getInstance().signUp(user, new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.body() == null) {
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "INCORRECT SIGNUP!", Toast.LENGTH_SHORT);

                        toast1.setGravity(Gravity.TOP, 0, 0);
                        toast1.show();
                    } else {
                        Log.i("GET","GET WENT WELL!\n" + response.body().getEmail());
                        changeActivity();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.i("GET","KO!");
                    Toast toast2 =
                            Toast.makeText(getApplicationContext(),
                                    "CONNECTION ERROR!", Toast.LENGTH_SHORT);

                    toast2.setGravity(Gravity.TOP, 0, 0);
                    toast2.show();
                }
            });

        } else {

            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "INCORRECT PASSWORDS!", Toast.LENGTH_LONG);

            toast1.setGravity(Gravity.TOP, 0, 0);
            toast1.show();

        }
    }

    public void setButton () {

        name = (EditText) findViewById(R.id.editName);
        last = (EditText) findViewById(R.id.editLast);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPass);
        password2 = (EditText) findViewById(R.id.editPass2);

        Button signUpButton = findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSignUp();
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