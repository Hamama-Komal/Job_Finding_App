package com.example.jobsearch.DashBoardFragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jobsearch.LoginActivities.IntroActivity;
import com.example.jobsearch.ModelClasses.ReadWriteUserData;
import com.example.jobsearch.ModelClasses.UserDataModelClass;
import com.example.jobsearch.ProfileActivities.UserProfileActivity;
import com.example.jobsearch.R;
import com.example.jobsearch.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    FragmentProfileBinding binding;
    private FirebaseAuth firebaseAuth;

    Toolbar toolbar;

    String name, email, imUrl, gender, phoneNo, dob, education, profession, description ,salary;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container,false);

       binding.toolbar.setTitle("My Profile");

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        if(firebaseUser == null){
            Toast.makeText(getContext(), "No Data Found, Try Later!", Toast.LENGTH_SHORT).show();
        }
        else{
            checkEmail(firebaseUser);
            binding.progressBar.setVisibility(View.VISIBLE);
            showUserDetails(firebaseUser);
        }




        return binding.getRoot();
    }

    private void showUserDetails(FirebaseUser firebaseUser) {

        String userID = firebaseUser.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("JobSeeker");

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserDataModelClass userData = snapshot.getValue(UserDataModelClass.class);

                if(userData != null){


                    name = userData.name;
                    email = userData.email;
                    gender = userData.gender;
                    phoneNo = userData.phoneNo;
                    dob = userData.dob;
                    profession = userData.profession;
                    salary = userData.salary;
                    education = userData.education;


                    binding.txtHello.setText("Hello! "+ name);
                    binding.txtName.setText(name);
                    binding.txtEmail.setText(email);
                    binding.txtDOB.setText(dob);
                    binding.txtGender.setText(gender);
                    binding.txtNumber.setText(phoneNo);
                    binding.txtEducation.setText(education);
                    binding.txtSalary.setText(salary);
                    binding.txtProfession.setText(profession);


                   Uri uri = firebaseUser.getPhotoUrl();
                   Glide.with(getContext()).load(uri).into(binding.userImg);


                }
                else {

                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                binding.progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "Error! "+ error, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void checkEmail(FirebaseUser firebaseUser) {
        if(!firebaseUser.isEmailVerified()){

           /* AlertDialog.Builder builder = new AlertDialog.Builder(ProfileFragment.get);
            builder.setTitle("Email Not Verified");
            builder.setMessage("Please verify your email now. You can not login without email verification next time");

            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent  = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            });
            builder.create();*/

            // Toast.makeText(getContext(), "Your Email is not verified", Toast.LENGTH_SHORT).show();

        }

    }

}