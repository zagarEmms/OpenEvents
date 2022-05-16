package com.example.openevents.fragments;

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
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.openevents.R;
import com.example.openevents.api.APIClient;
import com.example.openevents.business.Event;
import com.example.openevents.recyclerView.ListAdapter;
import com.example.openevents.recyclerView.MyOnClickListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExploreEventsFragment extends Fragment implements MyOnClickListener {

    private final Bundle bundle = new Bundle();
    private RecyclerView recyclerView;
    private String token;
    private ListAdapter adapter;

    private Spinner spinner;

    private ArrayList<Event> eventArrayList = new ArrayList<>();

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
                            Toast.makeText(getContext(), "0 EVENTS FOUND", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0,0);
                    toast.show();

                } else {
                    Log.i("GET","EVENTS WENT WELL!" + response.body());
                    eventArrayList.addAll(response.body());

                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                Log.i("GET","EXPLORE EVENTS KO!");
                Toast toast =
                        Toast.makeText(getContext(), "CONNECTION ERROR", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0,0);
                toast.show();
            }
        });
    }

    public void fillTmpArrayList() {
        eventArrayList.add(new Event("---", "---", "---",
                "---","---", "---",
                10, "---"));
        eventArrayList.add(new Event("---", "---", "---",
                "---","---", "---",
                10, "---"));
        eventArrayList.add(new Event("---", "---", "-",
                "---","---", "---",
                10, "---"));
    }

    public void setSpinner () {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //Gets the position -> category
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_explore_events, container, false);

        token = getArguments().getString("TOKEN");

        spinner = v.findViewById(R.id.spinner_filter_category);
        setSpinner();

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        token = getArguments().getString("TOKEN");

        fillTmpArrayList();

        adapter = new ListAdapter(getContext(), eventArrayList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);

        getEventsListAPI();

        return v;
    }


    @Override
    public void myOnClick(View view, int position) {
        int id = eventArrayList.get(position).getId();
        ArrayList<String> eventInfo = new ArrayList<>();
        eventInfo.add(token);
        eventInfo.add(String.valueOf(id));

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