package com.example.openevents.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.example.openevents.business.Event;
import com.example.openevents.business.Statistic;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonFragment extends Fragment {

    private static ArrayList<String> bundle = new ArrayList<String>();
    private String token;
    private int id;
    private TextView name;
    private TextView lastName;
    private TextView email;
    private TextView score;
    private TextView comments;
    private TextView commentersAvg;
    private Button follow;

    public PersonFragment() {
        // Required empty public constructor
    }

    public void getInfoScore() {
        APIClient.getInstance().showPersonInfo(token, id, new Callback<Statistic>() {
            @Override
            public void onResponse(Call<Statistic> call, Response<Statistic> response) {

                if (response.body() == null) {
                    Toast toast =
                            Toast.makeText(getContext(), "Not event found", Toast.LENGTH_LONG);
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

    /*public void getFriends() {
        APIClient.getInstance().showFriendPerson(token, id, new Callback<Statistic>() {
            @Override
            public void onResponse(Call<Statistic> call, Response<Statistic> response) {

                if (response.body() == null) {
                    Toast toast =
                            Toast.makeText(getContext(), "Not event found", Toast.LENGTH_LONG);
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
    }*/

    private void setUserInfo() {
        name.setText(bundle.get(2));
        lastName.setText(bundle.get(3));
        email.setText(bundle.get(4));
    }

    private void fillApiStats(Response<Statistic> response) {
        score.setText("Score: "+ response.body().getAvg_score());
        comments.setText("Num comments " + String.valueOf(response.body().getNum_comments()));
        commentersAvg.setText("% Commenters "+ response.body().getPercentage_commenters_below());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_person, container, false);

        configView(v);

        bundle = getArguments().getStringArrayList("PEOPLE_INFO");

        token = bundle.get(0);
        id = Integer.parseInt (bundle.get(1));

        setUserInfo();

        getInfoScore();
       // getFriends();

        return v;
    }

    private void configView(View v) {
        name = v.findViewById(R.id.profile_name);
        lastName = v.findViewById(R.id.profile_last_name);
        email = v.findViewById(R.id.email);
        score = v.findViewById(R.id.score);
        comments = v.findViewById(R.id.num_comments);
        commentersAvg = v.findViewById(R.id.comeneters);
        follow = v.findViewById(R.id.follow);
    }
}