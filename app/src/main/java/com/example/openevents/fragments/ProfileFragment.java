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
import android.widget.TextView;
import android.widget.Toast;

import com.example.openevents.R;
import com.example.openevents.api.APIClient;
import com.example.openevents.business.Statistic;
import com.example.openevents.business.User;
import com.example.openevents.recyclerView.ListAdapterPeople;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private String token;
    private int owner_id;
    private TextView mainName;
    private TextView name;
    private TextView lastName;
    private TextView email;
    private TextView score;
    private TextView comments;
    private TextView commentersAvg;

    private RecyclerView recyclerView;
    private ListAdapterPeople adapterFriends;
    private ArrayList<User> friendsArrayList = new ArrayList<User>();

    public ProfileFragment() {
        // Required empty public constructor
    }

    private void getPersonalInfoApi() {
        APIClient.getInstance().getProfileInfo(token, owner_id, new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {

                if (response.body() == null) {
                    Toast toast =
                            Toast.makeText(getContext(), "Not information found", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0,0);
                    toast.show();

                } else {
                    fillPersonalInfo(response);

                    Log.i("GET","PEOPLE WENT WELL!" + response.body());

                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.i("GET","FRIENDS LIST KO!");
                Toast toast =
                        Toast.makeText(getContext(), "CONNECTION ERROR", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                toast.show();
            }
        });
    }

    private void fillPersonalInfo (Response<ArrayList<User>> response) {
        mainName.setText(response.body().get(0).getName());
        name.setText(response.body().get(0).getName());
        lastName.setText(response.body().get(0).getLastName());
        email.setText(response.body().get(0).getEmail());
    }

    public void getInfoScore() {
        APIClient.getInstance().showPersonStatsInfo(token, owner_id, new Callback<Statistic>() {
            @Override
            public void onResponse(Call<Statistic> call, Response<Statistic> response) {

                if (response.body() == null) {
                    Toast toast =
                            Toast.makeText(getContext(), "Not statistics found", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0,0);
                    toast.show();

                } else {
                    Log.i("GET","PEOPLE WENT WELL!" + response.body());
                    fillApiStats(response);
                }
            }

            @Override
            public void onFailure(Call<Statistic> call, Throwable t) {
                Log.i("GET","PERSON INFO KO!");
                Toast toast =
                        Toast.makeText(getContext(), "CONNECTION ERROR", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                toast.show();
            }
        });
    }

    private void fillApiStats (Response<Statistic> response) {
        String scoreS = String.format(getString(R.string.score), response.body().getAvg_score());
        String commentsS = String.format(getString(R.string.comments), response.body().getNum_comments());
        String commentersAvgS = String.format(getString(R.string.commenters), response.body().getPercentage_commenters_below());

        score.setText(scoreS);
        comments.setText(commentsS);
        commentersAvg.setText(commentersAvgS);
    }

    private void fillApiFriends() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        configView(v);

        token = getArguments().getStringArrayList("VIP").get(0);
        owner_id = Integer.parseInt(getArguments().getStringArrayList("VIP").get(1));

        getPersonalInfoApi();
        getInfoScore();

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //ListAdapterPeople adapterFriends = new ListAdapterPeople(getContext(), adapterFriends);
        recyclerView.setAdapter(adapterFriends);
        //adapterFriends.setListenerPeople(this);



        return v;
    }

    private void configView(View v) {
        mainName = v.findViewById(R.id.main_title_name);
        name = v.findViewById(R.id.profile_name);
        lastName = v.findViewById(R.id.profile_last_name);
        email = v.findViewById(R.id.email);
        score = v.findViewById(R.id.score);
        comments = v.findViewById(R.id.num_comments);
        commentersAvg = v.findViewById(R.id.comeneters);
    }
}