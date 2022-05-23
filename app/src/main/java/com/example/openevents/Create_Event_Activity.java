package com.example.openevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.openevents.api.APIClient;
import com.example.openevents.business.Event;
import com.example.openevents.business.EventCreation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Create_Event_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button createButton;
    private EditText title;
    private EditText description;
    private String category;
    private EditText startDate;
    private EditText startTime;
    private EditText endDate;
    private EditText endTime;
    private EditText location;
    private EditText image;
    private EditText participants;
    private String eventStart_date;
    private String eventEnd_date;
    private Spinner spinner;

    public void changeActivity () {
        onBackPressed();
    }

    private String getToken() {

        SharedPreferences prefs = this.getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        return "Bearer " + prefs.getString("TOKEN","");
        
    }

    public void connectApi () {

        title = (EditText) findViewById(R.id.createTitle);
        description = (EditText) findViewById(R.id.createDescription);
        startDate = (EditText) findViewById(R.id.createStartDate);
        startTime = (EditText) findViewById(R.id.createStartTime);                                                  
        endDate = (EditText) findViewById(R.id.createEndDate);
        endTime =  (EditText) findViewById(R.id.createEndTime);
        location = (EditText) findViewById(R.id.createLocation);
        image = (EditText) findViewById(R.id.createImage);
        participants = (EditText) findViewById(R.id.createParticipants);

        eventStart_date = startDate.getText().toString() + ", " + startTime.getText().toString();
        eventEnd_date = endDate.getText().toString() + ", " + endTime.getText().toString();

        Log.i("GET","START SELECTED: " + eventStart_date);
        Log.i("GET","END SELECTED: " + eventEnd_date);

        if (!participants.getText().toString().equals("")) {

            EventCreation event = new EventCreation(title.getText().toString(), image.getText().toString(), location.getText().toString(), description.getText().toString(), eventStart_date, eventEnd_date, Integer.parseInt(participants.getText().toString()), category);

            APIClient.getInstance().createEvent(getToken(), event, new Callback<Event>() {
                @Override
                public void onResponse(Call<Event> call, Response<Event> response) {

                    if (response.body() == null) {
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "SOMETHING MISSING!", Toast.LENGTH_SHORT);

                        toast1.setGravity(Gravity.TOP, 0, 0);
                        toast1.show();
                    } else {
                        Log.i("GET","GET WENT WELL!\n" + response.body().getName());
                        changeActivity();
                    }
                }

                @Override
                public void onFailure(Call<Event> call, Throwable t) {
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
                            "SOMETHING MISSING!", Toast.LENGTH_SHORT);

            toast1.setGravity(Gravity.TOP, 0, 0);
            toast1.show();
        }
    }

    public void setButton () {

        createButton = (Button) findViewById(R.id.createEventButton);
        createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    getToken();
                    connectApi();

                }
            }
        );
    }

    public void setSpinner () {
        spinner = (Spinner) findViewById(R.id.createCategory);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        setButton();
        setSpinner();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        category = adapterView.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        category = null;
    }
}