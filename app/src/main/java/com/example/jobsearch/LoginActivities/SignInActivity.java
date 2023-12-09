package com.example.jobsearch.LoginActivities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.jobsearch.MainActivity;
import com.example.jobsearch.ProfileActivities.SetupProfileActivity;
import com.example.jobsearch.ProfileActivities.UserProfileActivity;
import com.example.jobsearch.R;
import com.example.jobsearch.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;

    private FirebaseAuth firebaseAuth;
    String userEmail, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.btnNext.setOnClickListener(view -> {
            getInputValues();
        });

        binding.txtForgetPassword.setOnClickListener(view -> {
            startActivity(new Intent(SignInActivity.this, ForgetActivity.class));
            finish();

        });

        binding.txtRegister.setOnClickListener(view -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            finish();

        });


    }

    private void getInputValues() {


        userEmail = binding.edtEmail.getText().toString();
        userPassword = binding.edtPassword.getText().toString();


        if(TextUtils.isEmpty(userEmail))
        {
            binding.edtEmail.setError("Email is required");
            binding.edtEmail.requestFocus();
        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
        {

            binding.edtEmail.setError("Valid Email is required");
            binding.edtEmail.requestFocus();

        }
        else if (TextUtils.isEmpty(userPassword))
        {

            binding.edtPassword.setError("Password is required");
            binding.edtPassword.requestFocus();

        }
        else {

            binding.progressBar.setVisibility(View.VISIBLE);
            loginUser(userEmail, userPassword);
        }
    }

    private void loginUser(String userEmail, String userPassword) {

        firebaseAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(SignInActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this, UserProfileActivity.class));
                    finish();
                }
                else{
                    try{
                        task.getException();
                    }
                    catch (Exception e)
                    {
                        Log.e(TAG, e.toString());
                        Toast.makeText(SignInActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(SignInActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }

                binding.progressBar.setVisibility(View.GONE);
            }
        });


    }

   @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){

           // Toast.makeText(this, "Welcome Back!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();

        }
    }
}