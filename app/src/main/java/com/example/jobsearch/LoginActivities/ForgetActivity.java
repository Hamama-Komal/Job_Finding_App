package com.example.jobsearch.LoginActivities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.jobsearch.ProfileActivities.UserProfileActivity;
import com.example.jobsearch.R;
import com.example.jobsearch.databinding.ActivityForgetBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ForgetActivity extends AppCompatActivity {

    ActivityForgetBinding binding;

    private  FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.btnReset.setOnClickListener(view -> {

            getEmailAddress();

        });

    }

    private void getEmailAddress() {

        String email = binding.edtEmail.getText().toString().trim();


        if(TextUtils.isEmpty(email))
        {
            binding.edtEmail.setError("Email is required");
            binding.edtEmail.requestFocus();

        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            binding.edtEmail.setError("Enter valid email is required");
            binding.edtEmail.requestFocus();

        }
        else
        {
            binding.progressBar.setVisibility(View.VISIBLE);
            resetPassword(email);

        }

    }

    private void resetPassword(String email) {


        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Toast.makeText(ForgetActivity.this, "Please check your inbox for reset password link!", Toast.LENGTH_SHORT).show();

                    /*startActivity(new Intent(ForgetActivity.this, UserProfileActivity.class));
                    finish();
*/

                }
                else{

                    try{
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidUserException e)
                    {
                        binding.edtEmail.setError("User does not exists");
                    }
                    catch (Exception e)
                    {
                        Log.e(TAG,e.toString());
                        Toast.makeText(ForgetActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                    }
                    Toast.makeText(ForgetActivity.this, "Something went wrong try later!", Toast.LENGTH_SHORT).show();
                }

                binding.progressBar.setVisibility(View.GONE);
            }
        });

    }
}