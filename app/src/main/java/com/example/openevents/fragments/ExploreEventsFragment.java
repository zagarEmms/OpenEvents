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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private int filtersStatus;
    private int owner_id;
    private ImageView arrow;
    private ImageButton scoreFilter;

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

    public void getEventsScore() {
        APIClient.getInstance().showEventsScore(token, new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                eventArrayList.clear();
                Log.i("TOKEN", ""+token);

                if (response.body() == null) {
                    Toast toast =
                            Toast.makeText(getContext(), "Not events best found", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0,0);
                    toast.show();

                } else {
                    Log.i("GET","SCORE FILTERS WENT WELL!" + response.body());
                    eventArrayList.addAll(response.body());
                    adapter.notifyDataSetChanged();

                    if (filtersStatus == 0){
                        filtersStatus = 1;
                    } else {
                        filtersStatus = 0;
                        getEventsListAPI();
                    }

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

    private void setButtonScore(View v) {
        scoreFilter = v.findViewById(R.id.filter_score);
        scoreFilter.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   getEventsScore();
               }
           }
        );
    }

    public void setSpinner () {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String category;
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 0:
                        category = null;
                        break;

                    case 1:
                        category = "Education";
                        break;

                    case 2:
                        category = "Food";
                        break;

                    case 3:
                        category = "Sports";
                        break;

                    case 4:
                        category = "Concerts";
                        break;

                    case 5:
                        category = "Museums";
                        break;

                    case 6:
                        category = "Games";
                        break;

                    case 7:
                        category = "Travel";
                        break;

                    case 8:
                        category = "Others";
                        break;
                }

                showSpinnerFilters(category);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void showSpinnerFilters(String category) {

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
                    eventArrayList.clear();

                    for (int i = 0; i < response.body().size(); i++) {
                        if(response.body().get(i).getType().equals(category)) {
                            eventArrayList.add(response.body().get(i));

                        } else if (category == null){
                            eventArrayList.addAll(response.body());
                        }
                    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_explore_events, container, false);

        token = getArguments().getStringArrayList("VIP").get(0);
        owner_id = Integer.parseInt(getArguments().getStringArrayList("VIP").get(1));
        spinner = v.findViewById(R.id.spinner_filter_category);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fillTmpArrayList();

        adapter = new ListAdapter(getContext(), eventArrayList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);

        setButtonScore(v);
        getEventsListAPI();
        setSpinner();

        return v;
    }


    @Override
    public void myOnClick(View view, int position) {
        int id = eventArrayList.get(position).getId();
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