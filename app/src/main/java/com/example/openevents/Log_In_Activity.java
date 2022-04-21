package com.example.openevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class Log_In_Activity extends AppCompatActivity {

    void changeActivityCreate () {
        Intent intent = new Intent(this, Sign_Up_Activity.class);
        startActivity(intent);
    }

    private void setButton () {

        Button logInButton = findViewById(R.id.log_in_button);
        logInButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     changeActivityCreate();
                 }
             }
        );

        Button SignUpButton = findViewById(R.id.button_sign_up);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     changeActivityCreate();
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