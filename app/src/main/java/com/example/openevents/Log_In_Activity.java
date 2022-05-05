package com.example.openevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.openevents.api.APIClient;
import com.example.openevents.business.Token;
import com.example.openevents.business.User;

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
        Intent intent = new Intent(Log_In_Activity.this, NavBar.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void setLogIn() {
        User user = new User(email.getText().toString(), password.getText().toString());

        APIClient.getInstance().logIn(user, new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Log.i("GET","LOG IN GET WENT WELL!" + response.body());
                if (response.body() == null) {
                    Toast toast =
                        Toast.makeText(getApplicationContext(), "Not user found", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0,0);
                        toast.show();

                } else {
                    changeActivityHome();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.i("GET","LOG IN KO!");
                Toast toast =
                        Toast.makeText(getApplicationContext(), "CONNECTION ERROR", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0,0);
                toast.show();
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