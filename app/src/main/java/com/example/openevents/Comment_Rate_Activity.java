package com.example.openevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Comment_Rate_Activity extends AppCompatActivity {

    private TextView eventTitle;
    private EditText comment;
    private EditText rating;
    private Button submit;

    public void changeActivity () {
        onBackPressed();
    }

    private String getToken() {

        SharedPreferences prefs = this.getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        return "Bearer " + prefs.getString("TOKEN","");

    }

    private void setTitle (String title) {

        eventTitle = (TextView) findViewById(R.id.eventName);
        eventTitle.setText(title);

    }

    public void connectApi () {  //currently in development

        String token = getToken();

        comment = (EditText) findViewById(R.id.comment);
        rating = (EditText) findViewById(R.id.rating);

        //Assistance assistance = new Assistance(Integer.parseInt(rating.getText().toString()), comment.getText().toString());

    }

    public void setButton () {

        submit = (Button) findViewById(R.id.commentButton);
        submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //connectApi();  # currently in development
                    changeActivity();

                }
            }
        );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_rate);

        String title = getIntent().getStringExtra("COMMENT_INFO");

        setButton();
        setTitle(title);

    }



}