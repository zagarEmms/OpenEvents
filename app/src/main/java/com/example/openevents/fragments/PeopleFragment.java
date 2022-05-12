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
import com.example.openevents.business.User;
import com.example.openevents.recyclerView.ListAdapter;
import com.example.openevents.recyclerView.ListAdapterPeople;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PeopleFragment extends Fragment {

    private RecyclerView recyclerView;
    private String token;
    private ListAdapterPeople adapter;

    private ArrayList<User> peopleArrayList = new ArrayList<>();

    public PeopleFragment() {
        // Required empty public constructor
    }

    public void getPeopleListAPI() {

        APIClient.getInstance().showPeople(token, new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {

                peopleArrayList.clear();

                if (response.body() == null) {

                    Toast toast =
                            Toast.makeText(getContext(), "NO PEOPLE FOUND", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0,0);
                    toast.show();

                } else {

                    Log.i("GET","PEOPLE WENT WELL!" + response.body());
                    peopleArrayList.addAll(response.body());

                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.i("GET","LOG IN KO!");
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

        View v = inflater.inflate(R.layout.fragment_people, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        token = getArguments().getString("TOKEN");

        adapter = new ListAdapterPeople(getContext(), peopleArrayList);
        recyclerView.setAdapter(adapter);
        //adapter.setListener(this);

        getPeopleListAPI();

        return v;
    }

}