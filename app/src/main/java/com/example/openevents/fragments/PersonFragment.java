package com.example.openevents.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.openevents.R;
import com.example.openevents.api.APIClient;
import com.example.openevents.business.Statistic;
import com.example.openevents.business.User;
import com.example.openevents.business.UserEventRequest;
import com.example.openevents.recyclerView.ListAdapterPeople;
import com.example.openevents.recyclerView.MyOnClickListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonFragment extends Fragment implements MyOnClickListener, View.OnClickListener {

    private Bundle bundleFriend = new Bundle();
    private static ArrayList<String> bundle = new ArrayList<String>();
    private String token;
    private int id;
    private TextView name;
    private TextView lastName;
    private TextView email;
    private TextView score;
    private TextView comments;
    private TextView commentersAvg;

    private RecyclerView recyclerView;
    private ListAdapterPeople adapterFriends;
    private ArrayList<User> friendsArrayList = new ArrayList<User>();

    public PersonFragment() {
        // Required empty public constructor
    }

    public void getInfoScore() {
        APIClient.getInstance().showPersonInfo(token, id, new Callback<Statistic>() {
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

    public void getFriends() {
        APIClient.getInstance().showFriendPerson(token, id, new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {

                if (response.body() == null) {
                    Toast toast =
                            Toast.makeText(getContext(), "Not friends found", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0,0);
                    toast.show();

                } else {
                    Toast toast =
                            Toast.makeText(getContext(), "FRIENDS MAY APPEAR HERE", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0,10);
                    toast.show();

                    friendsArrayList.addAll(response.body());
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

    private void setUserInfo() {
        name.setText(bundle.get(2));
        lastName.setText(bundle.get(3));
        email.setText(bundle.get(4));
    }

    private void fillApiStats(Response<Statistic> response) {

        String scoreS = String.format(getString(R.string.score), response.body().getAvg_score());
        String commentsS = String.format(getString(R.string.comments), response.body().getNum_comments());
        String commentersAvgS = String.format(getString(R.string.commenters), response.body().getPercentage_commenters_below());

        score.setText(scoreS);
        comments.setText(commentsS);
        commentersAvg.setText(commentersAvgS);
    }

    public void addFriendApi() {
        APIClient.getInstance().addFriendApi(token, id, new Callback<UserEventRequest>() {

            @Override
            public void onResponse(Call<UserEventRequest> call, Response<UserEventRequest> response) {

                if (response.body() == null) {
                    Toast toast =
                            Toast.makeText(getContext(), "Not friend found", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0,0);
                    toast.show();

                } else {
                    Toast toast =
                            Toast.makeText(getContext(), "YOUR FRIENDSHIP REQUEST HAS BEEN SENT :)", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0,10);
                    toast.show();

                    Log.i("friend","FRIEND REQUEST WENT WELL!" + response.body());

                }
            }

            @Override
            public void onFailure(Call<UserEventRequest> call, Throwable t) {
                Log.i("friend","friend request KO!");
                Toast toast =
                        Toast.makeText(getContext(), "CONNECTION ERROR", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                toast.show();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_person, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        configView(v);

        bundle = getArguments().getStringArrayList("PEOPLE_INFO");

        token = bundle.get(0);

        id = Integer.parseInt (bundle.get(1));

        Log.i("friend", String.valueOf(id));
        Log.i("friend", token);

        adapterFriends = new ListAdapterPeople(getContext(), friendsArrayList);
        recyclerView.setAdapter(adapterFriends);
        adapterFriends.setListenerPeople(this);

        setUserInfo();

        getInfoScore();
        getFriends();

        onClick(v);


        return v;
    }

    private void configView(View v) {
        name = v.findViewById(R.id.profile_name);
        lastName = v.findViewById(R.id.profile_last_name);
        email = v.findViewById(R.id.email);
        score = v.findViewById(R.id.score);
        comments = v.findViewById(R.id.num_comments);
        commentersAvg = v.findViewById(R.id.comeneters);
    }

    @Override
    public void myOnClick(View view, int position) {
        this.id = friendsArrayList.get(position).getId();
        ArrayList<String> personInfo = new ArrayList<>();
        personInfo.add(token);
        personInfo.add(String.valueOf(id));
        personInfo.add(friendsArrayList.get(position).getName());
        personInfo.add(friendsArrayList.get(position).getLastName());
        personInfo.add(friendsArrayList.get(position).getEmail());

        bundleFriend.putStringArrayList("PEOPLE_INFO", personInfo);

        PersonFragment personFragment = new PersonFragment();
        personFragment.setArguments(bundleFriend);

        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.flFragment);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View view) {
        Button follow = view.findViewById(R.id.follow);
        follow.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      addFriendApi();
                  }
              }
        );
    }
}