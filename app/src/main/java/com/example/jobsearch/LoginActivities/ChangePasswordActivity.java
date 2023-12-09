package com.example.jobsearch.LoginActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.jobsearch.MainActivity;
import com.example.jobsearch.R;
import com.example.jobsearch.databinding.ActivityChangePasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    ActivityChangePasswordBinding binding;
    FirebaseAuth firebaseAuth;

    String oldPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.newPassword.setEnabled(false);
        binding.btnReset.setEnabled(false);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            authenicateUser(firebaseUser);
        } else {
            Toast.makeText(this, "Your password can not be reset first verify your email address", Toast.LENGTH_SHORT).show();
        }


    }

    private void authenicateUser(FirebaseUser firebaseUser) {

        binding.btnCheck.setOnClickListener(view -> {

        oldPassword = binding.oldPassword.getText().toString();
        if (TextUtils.isEmpty(oldPassword)) {
            Toast.makeText(ChangePasswordActivity.this, "Password is needed", Toast.LENGTH_SHORT).show();
            binding.oldPassword.setError("Please enter your current password tu authenticate");
            binding.oldPassword.requestFocus();
        } else {

            binding.progressBar.setVisibility(View.VISIBLE);


            //ReAuthenticate User Now

            AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), oldPassword);
            firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        binding.progressBar.setVisibility(View.GONE);

                        //Disable editText for Current Password. Enable EditText for New Password and Confirm New Password

                        binding.oldPassword.setEnabled(false);
                        binding.newPassword.setEnabled(true);

                        //  editTextPwdConfirmNew.setEnabled(true);
                        //Enable Change Pwd Button. Disable Authenticate Button

                        binding.btnCheck.setEnabled(false);
                        binding.btnReset.setEnabled(true);


                        //Set TextView to show User is authenticated/verified
                        binding.txt.setText("You are authenticated/verified. " + "You can change password now!");
                        Toast.makeText(ChangePasswordActivity.this, "Password has been verified." + "Change Password now", Toast.LENGTH_SHORT).show();


                        //Update color of Change Password Button

                        binding.btnReset.setBackgroundTintList(ContextCompat.getColorStateList(ChangePasswordActivity.this, R.color.light));


                        binding.btnReset.setOnClickListener(v1 -> {
                            changePassword(firebaseUser);
                        });


                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e) {
                            Toast.makeText(ChangePasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    binding.progressBar.setVisibility(View.GONE);


                }

            });
        }
        });
    }

    private void changePassword(FirebaseUser firebaseUser) {

        String userPwdNew = binding.newPassword.getText().toString();

        if (TextUtils.isEmpty(userPwdNew)) {
            Toast.makeText(ChangePasswordActivity.this, "New Password is needed", Toast.LENGTH_SHORT).show();
            binding.newPassword.setError("Please enter your new password");
            binding.newPassword.requestFocus();

        } else {
            binding.progressBar.setVisibility(View.VISIBLE);

            firebaseUser.updatePassword(userPwdNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(ChangePasswordActivity.this, "Password has been changed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e) {
                            Toast.makeText(ChangePasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                    binding.progressBar.setVisibility(View.GONE);
                }
            });
        }
    }


}



