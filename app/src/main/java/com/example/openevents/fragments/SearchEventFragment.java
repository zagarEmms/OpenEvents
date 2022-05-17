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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.openevents.R;
import com.example.openevents.api.APIClient;
import com.example.openevents.business.Event;
import com.example.openevents.recyclerView.ListAdapter;
import com.example.openevents.recyclerView.ListAdapterPeople;
import com.example.openevents.recyclerView.MyOnClickListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchEventFragment extends Fragment implements MyOnClickListener {
    private final Bundle bundle = new Bundle();
    private RecyclerView recyclerView;
    private ArrayList<Event> eventArrayList = new ArrayList<>();
    private String token;
    private ListAdapter adapter;

    private EditText keywordSearch;
    private EditText locationSearch;
    private EditText dateSearch;

    private String keyword = null;
    private String location = null;
    private String date = null;
    private String dateFinal;

    private ImageButton searchIcon;

    public SearchEventFragment() {
        // Required empty public constructor
    }

    private void  checkInputs() {

        if (!keywordSearch.getText().toString().equals("")) {
            keyword = keywordSearch.getText().toString();
        }

        if (!locationSearch.getText().toString().equals("")) {
            location = locationSearch.getText().toString();
        }

        if (!dateSearch.getText().toString().equals("")) {
            date = dateSearch.getText().toString();

            String[] date_parts = date.split("/");
            String part1 = date_parts[0];
            String part2 = date_parts[1];
            String part3 = date_parts[2];
            dateFinal = part3 + "-" + part2 + "-" + part1;

            Log.i("date", ""+ dateFinal);
        }

        getSearchResults();
    }

    private void getSearchResults(){
        APIClient.getInstance().showEventsSearched(token, location, keyword, dateFinal, new Callback<ArrayList<Event>>() {
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

    private void setButton(View v) {
        searchIcon = v.findViewById(R.id.search_button);
        searchIcon.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  checkInputs();
              }
          }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search_event, container, false);

        configView(v);

        token = getArguments().getStringArrayList("EVENT_INFO").get(0);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setButton(v);

        adapter = new ListAdapter(getContext(), eventArrayList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);

        return v;

    }

    private void configView(View v) {
        keywordSearch = v.findViewById(R.id.keywordSearch);
        locationSearch= v.findViewById(R.id.location_search);
        dateSearch = v.findViewById(R.id.start_date_search);

    }

    @Override
    public void myOnClick(View view, int position) {
        int id = eventArrayList.get(position).getId();
        int owner_id = eventArrayList.get(position).getOwner_id();
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