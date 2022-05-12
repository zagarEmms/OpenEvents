package com.example.openevents.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.openevents.R;
import com.example.openevents.api.APIClient;
import com.example.openevents.business.Event;
import com.example.openevents.recyclerView.ListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExploreEventsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Event> eventArrayList = new ArrayList<>();
    private String token;
    private ListAdapter adapter;

    public ExploreEventsFragment() {
        // Required empty public constructor
    }

    public void getEventsListAPI() {
        APIClient.getInstance().showEvents(token, new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                eventArrayList.clear();

                if (response.body() == null) {
                    Toast toast =
                            Toast.makeText(getContext(), "Not events found", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0,0);
                    toast.show();

                } else {
                    Log.i("GET","EVENTS WENT WELL!" + response.body());
                    eventArrayList.addAll(response.body());
                    Log.i("GET", eventArrayList.get(2).getName());

                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                Log.i("GET","LOG IN KO!");
                Toast toast =
                        Toast.makeText(getContext(), "CONNECTION ERROR", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0,0);
                toast.show();
            }
        });
    }

    public void fillArrayList() {
        eventArrayList.add(new Event("Drawing Meet Up of the month: January", "https://i.imgur.com/Ou3LVpb.jpg", "Barcelona",
                "Come paint with a group of artists. Bring your own materials and canvas.","2022-01-01T12:00:00.000Z", "2022-01-12T17:30:00.000Z",
                10, "Education"));
        eventArrayList.add(new Event("Drawing 101: Portraits", "https://i.imgur.com/JprpLyc.jpg", "Tarragona",
                "Learn the basics of portrait drawing with a professional artist with +10 years of experience.","2022-01-01T12:00:00.000Z", "2022-01-12T17:30:00.000Z",
                10, "Art"));
        eventArrayList.add(new Event("Cooking a Tortilla", "https://i.imgur.com/Ou3LVpb.jpg", "Soria",
                "We will discover the power of torilla de patatas","2022-01-01T12:00:00.000Z", "2022-01-12T17:30:00.000Z",
                10, "Cooking"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_explore_events, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fillArrayList();

        adapter = new ListAdapter(getContext(), eventArrayList);
        recyclerView.setAdapter(adapter);

        getEventsListAPI();

        return v;
    }
}