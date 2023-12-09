package com.example.jobsearch.ProfileActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.jobsearch.R;
import com.example.jobsearch.databinding.ActivityDeleteProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteProfileActivity extends AppCompatActivity {

    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;
    ActivityDeleteProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnDelete.setEnabled(false);
        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();


        if (firebaseUser.equals("")) {
            Toast.makeText(this, "Something went wrong!" + "User Detail's are not available at the moment", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DeleteProfileActivity.this, UserProfileActivity.class);
            startActivity(intent);
            finish();
        } else {

            reAuthenticateUser(firebaseUser);


        }
    }

    private void reAuthenticateUser(FirebaseUser firebaseUser) {

        Toast.makeText(this, "Sorry, this function is under development.", Toast.LENGTH_SHORT).show();

    }}