package com.example.openevents.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.openevents.Create_Event_Activity;
import com.example.openevents.R;
import com.example.openevents.api.APIClient;
import com.example.openevents.business.Event;
import com.example.openevents.recyclerView.ListAdapter;
import com.example.openevents.recyclerView.MyOnClickListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyEventsFragment extends Fragment implements MyOnClickListener {

    private RecyclerView recyclerView;
    private ArrayList<Event> eventArrayList = new ArrayList<>();
    private ArrayList<Event> myEventArrayList = new ArrayList<>();
    private String token;
    private ListAdapter adapter;
    private ImageView createIcon;
    private int owner_id;
    private Bundle bundle = new Bundle();

    public MyEventsFragment() {
        //Required empty public constructor
    }

    public void changeActivity () {
        Intent i = new Intent(getActivity(), Create_Event_Activity.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);
    }

    public void getEventsListAPI() {
        APIClient.getInstance().showEvents(token, new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                eventArrayList.clear();

                if (response.body() == null) {
                    Toast toast =
                            Toast.makeText(getContext(), "0 EVENTS FOUND" + owner_id, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0,0);
                    toast.show();

                } else {

                    Log.i("GET","EVENTS WENT WELL!" + owner_id);
                    eventArrayList.addAll(response.body());

                    for (int i = 0; i < eventArrayList.size(); i++) {  //check which events are created by us
                        if (eventArrayList.get(i).getOwner_id() == owner_id) {
                            myEventArrayList.add(eventArrayList.get(i));
                        }
                    }

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

    public void setButton (View v) {

        createIcon = (ImageView) v.findViewById(R.id.createEvent);
        createIcon.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                      changeActivity();

                  }
              }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_events, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        token = getArguments().getStringArrayList("VIP").get(0);
        owner_id = Integer.parseInt(getArguments().getStringArrayList("VIP").get(1));

        adapter = new ListAdapter(getContext(), myEventArrayList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);

        setButton(v);
        getEventsListAPI();

        return v;
    }

    @Override
    public void myOnClick(View view, int position) {

        int id = myEventArrayList.get(position).getId();
        ArrayList<String> eventInfo = new ArrayList<>();
        eventInfo.add(token);
        eventInfo.add(String.valueOf(id));
        eventInfo.add(String.valueOf(owner_id));

        bundle.putStringArrayList("EVENT_INFO",eventInfo);

        EventInfoFragment eventFragment = new EventInfoFragment();
        eventFragment.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, eventFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}