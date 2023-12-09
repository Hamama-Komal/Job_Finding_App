package com.example.jobsearch.LoginActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.jobsearch.R;
import com.example.jobsearch.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {

    ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(IntroActivity.this, SignInActivity.class));


        });

        binding.btnSignUp.setOnClickListener(view -> {
            startActivity(new Intent(IntroActivity.this, SignUpActivity.class));

        });

    }
}