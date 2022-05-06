package com.example.openevents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.openevents.fragments.ExploreEventsFragment;
import com.example.openevents.fragments.MyEventsFragment;
import com.example.openevents.fragments.PeopleFragment;
import com.example.openevents.fragments.PersonFragment;
import com.example.openevents.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NavBar extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavView;

    ExploreEventsFragment exploreEventsFragment = new ExploreEventsFragment();
    MyEventsFragment myEventsFragment = new MyEventsFragment();
    SearchFragment searchFragment = new SearchFragment();
    PeopleFragment peopleFragment = new PeopleFragment();
    PersonFragment personFragment = new PersonFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_bar);

        bottomNavView = findViewById(R.id.container);

        bottomNavView.setOnItemSelectedListener(this);
        bottomNavView.setSelectedItemId(R.id.home);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, exploreEventsFragment).commit();
            return true;
        } else if (id == R.id.myevents) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, myEventsFragment).commit();
            return true;
        } else if (id == R.id.search) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
            return true;
        } else if (id == R.id.people) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, peopleFragment).commit();
            return true;
        } else if (id == R.id.person) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, personFragment).commit();
            return true;
        }

        return false;
    }
}