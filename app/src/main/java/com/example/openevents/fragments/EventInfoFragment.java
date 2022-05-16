package com.example.openevents.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.openevents.Create_Event_Activity;
import com.example.openevents.Edit_Event_Activity;
import com.example.openevents.R;
import com.example.openevents.api.APIClient;
import com.example.openevents.business.Event;
import com.example.openevents.business.UserEventRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventInfoFragment extends Fragment {

    private String token;
    private int id;
    private int owner_id;
    private int event_owner_id;
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

    public void editEvent () {

        Intent i = new Intent(getActivity(), Edit_Event_Activity.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);

    }

    public void getInfoAPI(View v) {

        APIClient.getInstance().showEventInfo(token, id, new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {

                if (response.body() == null) {
                    Toast toast =
                            Toast.makeText(getContext(), "Not event found", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0,0);
                    toast.show();

                } else {
                    Log.i("GET","EVENT WENT WELL!" + response.body());
                    fillAPIInfo(response);
                    event_owner_id = response.body().get(0).getOwner_id();
                    setButton(v);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                Log.i("GET","EVENT INFO KO!");
                Toast toast =
                        Toast.makeText(getContext(), "CONNECTION ERROR", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                toast.show();
            }
        });
    }

    private void fillAPIInfo(Response<ArrayList<Event>> response) {
        name.setText(response.body().get(0).getName());
        description.setText(response.body().get(0).getDescription());
        startDate.setText(response.body().get(0).getEventStart_date());
        endDate.setText(response.body().get(0).getEventEnd_date());
        participants.setText(String.valueOf(response.body().get(0).getN_participators()));
        location.setText(response.body().get(0).getLocation());
        type.setText(response.body().get(0).getType());
    }

    private void joinEventApi () {

        APIClient.getInstance().joinEvent(token, id, new Callback<UserEventRequest>() {
            @Override
            public void onResponse(Call<UserEventRequest> call, Response<UserEventRequest> response) {

                if (response.body() == null) {
                    Log.i("GET","JOIN WENT WELL!" + response.body());
                    Toast toast =
                            Toast.makeText(getContext(), "Not event found", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0,0);
                    toast.show();

                } else {
                    Log.i("GET","JOIN WENT WELL!" + response.body());
                    Toast toast =
                            Toast.makeText(getContext(), "YOU HAVE JOINED THE EVENT :)", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0,10);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<UserEventRequest> call, Throwable t) {
                Log.i("GET","JOIN KO!");
                Toast toast =
                        Toast.makeText(getContext(), "CONNECTION ERROR", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                toast.show();
            }
        });
    }

    public void setButton (View view) {

        Button join = view.findViewById(R.id.join);

        Log.i("OWNER", "Onwer_id: " + owner_id);
        Log.i("OWNER", "Id: " + event_owner_id);


        if (owner_id == event_owner_id) {

            join.setText(getString(R.string.edit_event));
            join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editEvent();
                    }
                }
            );

        } else {

            join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        joinEventApi();
                    }
                }
            );

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_event_info, container, false);

        configView(v);

        token = getArguments().getStringArrayList("EVENT_INFO").get(0);
        id = Integer.parseInt (getArguments().getStringArrayList("EVENT_INFO").get(1));
        owner_id = Integer.parseInt (getArguments().getStringArrayList("EVENT_INFO").get(2));

        getInfoAPI(v);

        return v;
    }

    private void fillTempInfo() {
        name.setText("---");
        description.setText("---");
        startDate.setText("---");
        endDate.setText("---");
        participants.setText(String.valueOf(0));
        location.setText("---");
        type.setText("---");
    }

    private void configView(View v) {
        name = v.findViewById(R.id.itemName);
        description = v.findViewById(R.id.description);
        startDate = v.findViewById(R.id.start_date);
        endDate = v.findViewById(R.id.end_date);
        participants = v.findViewById(R.id.participants);
        location = v.findViewById(R.id.location);
        type = v.findViewById(R.id.tag);
        fillTempInfo();
    }
}