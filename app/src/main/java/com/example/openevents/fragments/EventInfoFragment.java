package com.example.openevents.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.openevents.Comment_Rate_Activity;
import com.example.openevents.Edit_Event_Activity;
import com.example.openevents.R;
import com.example.openevents.api.APIClient;
import com.example.openevents.business.Assistance;
import com.example.openevents.business.DeleteEvent;
import com.example.openevents.business.Event;
import com.example.openevents.business.UserEventRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.DELETE;


public class EventInfoFragment extends Fragment {

    private final Bundle bundle = new Bundle();
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
    private ImageView imageView;
    private ArrayList<String> eventInfo = new ArrayList<>();
    private ArrayList<String> vipArrayList = new ArrayList<>();

    public EventInfoFragment() {
        //Required empty public constructor
    }

    public void editEvent () {

        Intent i = new Intent(getActivity(), Edit_Event_Activity.class);
        i.putStringArrayListExtra("EDIT_INFO", eventInfo);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);

    }

    public void changeFragment () {

        vipArrayList.add(token);
        vipArrayList.add(Integer.toString(owner_id));
        bundle.putStringArrayList("VIP", vipArrayList);

        MyEventsFragment myEventsFragment = new MyEventsFragment();
        myEventsFragment.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, myEventsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void commentEvent () {

        Intent i = new Intent(getActivity(), Comment_Rate_Activity.class);
        i.putExtra("COMMENT_INFO", eventInfo.get(1));
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);

    }

    public void deleteApi () {

        APIClient.getInstance().deleteEvent(token, id, new Callback<DeleteEvent>() {
            @Override
            public void onResponse(Call<DeleteEvent> call, Response<DeleteEvent> response) {

                if (response.body() == null) {

                    Toast toast =
                            Toast.makeText(getContext(),
                                    "ERROR DELETING!", Toast.LENGTH_SHORT);

                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();

                } else {
                    Log.i("GET","GET WENT WELL!\n" + response.body().getMensaje());
                    changeFragment();
                }
            }

            @Override
            public void onFailure(Call<DeleteEvent> call, Throwable t) {
                Log.i("GET","KO!");
                Toast toast2 =
                        Toast.makeText(getContext(),
                                "CONNECTION ERROR!", Toast.LENGTH_SHORT);

                toast2.setGravity(Gravity.TOP, 0, 0);
                toast2.show();
            }
        });
    }

    public void getInfoAPI(View v) {

        APIClient.getInstance().showEventInfo(token, id, new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {

                if (response.body() == null) {
                    Toast toast =
                            Toast.makeText(getContext(), "NOT EVENT FOUND", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0,0);
                    toast.show();

                } else {
                    Log.i("GET","EVENT WENT WELL!" + response.body());
                    Log.i("GET","ID" + response.body().get(0).getId());
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

        try {
            Picasso.get()
                    .load(response.body().get(0).getImage())
                    .into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            imageView.setImageResource(R.drawable.santamonica_photo);
                        }
                    });
        } catch (IllegalArgumentException iae) {
            imageView.setImageResource(R.drawable.sofia);
        }

        eventInfo.add(String.valueOf(id));
        eventInfo.add(response.body().get(0).getName());
        eventInfo.add(response.body().get(0).getDescription());
        eventInfo.add(response.body().get(0).getEventStart_date());
        eventInfo.add(response.body().get(0).getEventEnd_date());
        eventInfo.add(Integer.toString(response.body().get(0).getN_participators()));
        eventInfo.add(response.body().get(0).getLocation());
        eventInfo.add(response.body().get(0).getImage());
        eventInfo.add(response.body().get(0).getType());

    }

    private void joinEventApi () {

        APIClient.getInstance().joinEvent(token, id, new Callback<UserEventRequest>() {
            @Override
            public void onResponse(Call<UserEventRequest> call, Response<UserEventRequest> response) {

                if (response.body() == null) {
                    Toast toast =
                            Toast.makeText(getContext(), "NOT EVENT FOUND", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0,300);
                    toast.show();

                } else {
                    Log.i("GET","JOIN WENT WELL!" + response.body());
                    Toast toast =
                            Toast.makeText(getContext(), "YOU HAVE JOINED THE EVENT :)", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0,300);
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

    public void unJoinEvent () {

        APIClient.getInstance().unJoinEvent(token, id, owner_id, new Callback<UserEventRequest>() {
            @Override
            public void onResponse(Call<UserEventRequest> call, Response<UserEventRequest> response) {

                if (response.body() == null) {
                    Toast toast =
                            Toast.makeText(getContext(), "NOT EVENT FOUND", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0,300);
                    toast.show();
                } else {
                    Log.i("GET","JOIN WENT WELL!" + response.body());
                    Toast toast =
                            Toast.makeText(getContext(), "YOU HAVE UNJOINED THE EVENT :(", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0,300);
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

    public void setCommentButton (boolean isJoined, View v) {

        Button join = v.findViewById(R.id.join);
        Button deleteButton = v.findViewById(R.id.delete);
        deleteButton.setVisibility(View.GONE);

        if(isJoined) {

            deleteButton.setText(R.string.info_event_unjoin);
            deleteButton.setVisibility(View.VISIBLE);
            join.setText(getString(R.string.comment_comment));
            join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        commentEvent();
                    }
                }
            );

            deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        unJoinEvent();
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

    public void isUserJoined (View v) {
        APIClient.getInstance().isUserJoined(token, owner_id, id, new Callback<Assistance>() {
            @Override
            public void onResponse(Call<Assistance> call, Response<Assistance> response) {

                Log.i("GET", "" + response.body().getEvent_id());
                Log.i("GET", "" + response.body().getComentary());
                Log.i("GET", "" + response.body().getPuntuation());
                Log.i("GET", "" + response.body().getUser_id());

                if (response.body().getEvent_id() == 0) {
                    setCommentButton(false, v);
                } else {
                    setCommentButton(true, v);
                }
            }

            @Override
            public void onFailure(Call<Assistance> call, Throwable t) {
                Log.i("GET","JOIN KO!" + t.getMessage());
                Toast toast =
                        Toast.makeText(getContext(), "CONNECTION ERROR", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                toast.show();
            }
        });
    }

    public void setButton (View view) {

        Button join = view.findViewById(R.id.join);
        Button deleteButton = view.findViewById(R.id.delete);
        deleteButton.setVisibility(View.GONE);

        if (owner_id == event_owner_id) {

            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteApi();
                    }
                }
            );

            join.setText(getString(R.string.edit_event));
            join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editEvent();
                    }
                }
            );

        } else {
            isUserJoined(view);
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
        imageView = v.findViewById(R.id.itemImg);
        fillTempInfo();
    }
}