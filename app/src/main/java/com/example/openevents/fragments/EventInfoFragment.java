package com.example.openevents.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.openevents.R;
import com.example.openevents.api.APIClient;
import com.example.openevents.business.Event;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventInfoFragment extends Fragment {

    private String token;
    private int id;
    private TextView name;
    private TextView description;
    private TextView location;
    private TextView startDate;
    private TextView endDate;
    private TextView participants;
    private TextView type;

    public EventInfoFragment() {
        //Required empty public constructor
    }

    public void getInfoAPI() {
        APIClient.getInstance().showEventInfo(token, id, new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {

                if (response.body() == null) {
                    Toast toast =
                            Toast.makeText(getContext(), "Not event found", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0,0);
                    toast.show();

                } else {
                    Log.i("GET","EVENT WENT WELL!" + response.body());
                    fillAPIInfo(response);
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Log.i("GET","EVENT INFO KO!");
                Toast toast =
                        Toast.makeText(getContext(), "CONNECTION ERROR", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                toast.show();
            }
        });
    }

    private void fillAPIInfo(Response<Event> response) {
        name.setText(response.body().getName());
        description.setText(response.body().getDescription());
        location.setText(response.body().getLocation());
        startDate.setText(response.body().getEventStart_date());
        endDate.setText(response.body().getEventEnd_date());
        participants.setText(response.body().getN_participators());
        type.setText(response.body().getType());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_event_info, container, false);

        configView(v);

        token = getArguments().getStringArrayList("EVENT_INFO").get(0);
        id = Integer.parseInt (getArguments().getStringArrayList("EVENT_INFO").get(1));

        Log.i("EVENT", token);
        Log.i("EVENT", String.valueOf(id));

        getInfoAPI();

        return v;
    }

    private void configView(View v) {
        name = v.findViewById(R.id.itemName);
        description = v.findViewById(R.id.description);
        location = v.findViewById(R.id.location);
        startDate = v.findViewById(R.id.start_date);
        endDate = v.findViewById(R.id.end_date);
        participants = v.findViewById(R.id.participants);
        type = v.findViewById(R.id.tag);
    }



}