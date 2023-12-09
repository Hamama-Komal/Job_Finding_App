package com.example.jobsearch.DashBoardFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jobsearch.LoginActivities.ChangePasswordActivity;
import com.example.jobsearch.LoginActivities.ForgetActivity;
import com.example.jobsearch.LoginActivities.IntroActivity;
import com.example.jobsearch.ProfileActivities.SetupProfileActivity;
import com.example.jobsearch.ProfileActivities.UserProfileActivity;
import com.example.jobsearch.R;
import com.example.jobsearch.databinding.FragmentSettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }


    FragmentSettingsBinding binding;

    FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment

       firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        binding.logout.setOnClickListener(view -> {

            firebaseAuth.signOut();
            Toast.makeText(getContext(), "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), IntroActivity.class);
            startActivity(intent);


        });

        binding.forgetPassword.setOnClickListener(view -> {

            Intent intent = new Intent(getContext(), ForgetActivity.class);
            startActivity(intent);
        });

        binding.resetPassword.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
            startActivity(intent);
        });


        binding.changeEmail.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Change Email", Toast.LENGTH_SHORT).show();
        });

         binding.updateProfile.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Update Profile", Toast.LENGTH_SHORT).show();
        });

         binding.deleteProfile.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Delete Profile", Toast.LENGTH_SHORT).show();
        });

         binding.viewProfile.setOnClickListener(view -> {
             Intent intent = new Intent(getContext(), UserProfileActivity.class);
             startActivity(intent);
            // Toast.makeText(getContext(), "Setup Profile", Toast.LENGTH_SHORT).show();
        });






        return binding.getRoot();
    }
}