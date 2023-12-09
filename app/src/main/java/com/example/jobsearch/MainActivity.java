package com.example.jobsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.jobsearch.DashBoardFragments.HomeFragment;
import com.example.jobsearch.DashBoardFragments.ProfileFragment;
import com.example.jobsearch.DashBoardFragments.SearchFragment;
import com.example.jobsearch.DashBoardFragments.SettingsFragment;
import com.example.jobsearch.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    BottomNavigationView bottomNavigationView;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Coding of Bottom Navigation Bar

        loadFrag(new HomeFragment());
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_profile){
                    loadFrag(new SettingsFragment());}

                else if(id == R.id.nav_community){
                    loadFrag(new SearchFragment());
                }
                else{
                    loadFrag(new HomeFragment());
                }


                return true;
            }
        });

    }

    private void loadFrag(Fragment fragment){
        FragmentManager frag = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = frag.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }





}