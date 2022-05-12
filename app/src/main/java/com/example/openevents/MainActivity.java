package com.example.openevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public boolean checkLogIn () {

        SharedPreferences prefs = this.getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        if (prefs.getString("TOKEN", "").equals("")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                if (!checkLogIn()) {

                    Intent intent = new Intent(MainActivity.this, Log_In_Activity.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();

                } else {

                    Intent intent = new Intent(MainActivity.this, NavBar.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();

                }
            }
        }, 1800);
    }

}