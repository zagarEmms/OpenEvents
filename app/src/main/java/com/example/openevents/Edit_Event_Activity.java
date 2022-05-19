package com.example.openevents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.openevents.api.APIClient;
import com.example.openevents.business.Event;
import com.example.openevents.business.EventCreation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Event_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button editButton;
    private EditText title;
    private EditText description;
    private String category;
    private EditText startDate;
    private EditText startTime;
    private EditText endDate;
    private EditText endTime;
    private EditText location;
    private EditText image;
    private int n_participators;
    private String eventStart_date;
    private String eventEnd_date;
    private int id;
    private Spinner spinner;
    private ArrayList<String> infoEvent = new ArrayList<>();

    private String titleOk;
    private String descriptionOk;
    private String locationOk;
    private String categoryOk;
    private String imageOk;

    public void changeActivity () {
        onBackPressed();
    }

    private String getToken() {

        SharedPreferences prefs = this.getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        return "Bearer " + prefs.getString("TOKEN","");

    }

    public void fillInfo () {

        title = (EditText) findViewById(R.id.editTitle);
        description = (EditText) findViewById(R.id.editDescription);
        startDate = (EditText) findViewById(R.id.editStartDate);
        startTime = (EditText) findViewById(R.id.editStartTime);
        endDate = (EditText) findViewById(R.id.editEndDate);
        endTime =  (EditText) findViewById(R.id.editEndTime);
        location = (EditText) findViewById(R.id.editLocation);
        image = (EditText) findViewById(R.id.editImageEvent);

        id = Integer.parseInt(infoEvent.get(0));
        title.setHint(infoEvent.get(1));
        titleOk = infoEvent.get(1);
        description.setHint(infoEvent.get(2));
        descriptionOk = infoEvent.get(2);
        eventStart_date = infoEvent.get(3);
        eventEnd_date = infoEvent.get(4);
        n_participators = Integer.parseInt(infoEvent.get(5));
        location.setHint(infoEvent.get(6));
        locationOk = infoEvent.get(6);
        imageOk = infoEvent.get(7);
        image.setHint(infoEvent.get(7));
        categoryOk = infoEvent.get(8);

    }

    public void verifyChangedInfo () {

        if (!title.getText().toString().equals("")) {
            titleOk = title.getText().toString();
        }

        if (!location.getText().toString().equals("")) {
            locationOk = location.getText().toString();
        }

        if (!description.getText().toString().equals("")) {
            descriptionOk = description.getText().toString();
        }

        if (!image.getText().toString().equals("")) {
            imageOk = image.getText().toString();
        }

        if (!category.equals("Categories")) {
            categoryOk = title.getText().toString();
        }

        if (!startDate.getText().toString().equals("") && !startTime.getText().toString().equals("")) {
            eventStart_date = startDate.getText().toString() + ", " + startTime.getText().toString();
        }

        if (!endDate.getText().toString().equals("") && !endTime.getText().toString().equals("")) {
            eventEnd_date = endDate.getText().toString() + ", " + endTime.getText().toString();
        }
    }

    public void connectApi () {

        verifyChangedInfo();
        EventCreation event = new EventCreation(titleOk, imageOk, locationOk, descriptionOk, eventStart_date, eventEnd_date, n_participators, categoryOk);

        APIClient.getInstance().editEvent(getToken(), event, id, new Callback<Event>() {
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
    }

    public void setButton () {

        editButton = (Button) findViewById(R.id.editEventButton);
        editButton.setOnClickListener(new View.OnClickListener() {
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
        setContentView(R.layout.activity_edit_event);

        infoEvent = getIntent().getStringArrayListExtra("EDIT_INFO");

        fillInfo();
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