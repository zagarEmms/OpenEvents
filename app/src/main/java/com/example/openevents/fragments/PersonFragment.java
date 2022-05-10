package com.example.openevents.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.openevents.R;


public class PersonFragment extends Fragment {

    //private TextView tx (which is on the fragment and at first is null)

    public PersonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person, container, false);

        //return v
        //configView(v) -> all the necessary findbyId
        //setupInititalView() -> sets Response tmp object with info __> call function //drawInfo() -> with all the necessary setText
        //fetchUser(Response response) -> api petition __> call function drawInfo() again with the new info


        //setName and all the information from the callback response
    }
}