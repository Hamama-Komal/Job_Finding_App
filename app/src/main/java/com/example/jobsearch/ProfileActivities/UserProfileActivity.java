package com.example.jobsearch.ProfileActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jobsearch.LoginActivities.IntroActivity;
import com.example.jobsearch.MainActivity;
import com.example.jobsearch.ModelClasses.ReadWriteUserData;
import com.example.jobsearch.R;
import com.example.jobsearch.databinding.ActivityUserProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    ActivityUserProfileBinding binding;

    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    Toolbar toolbar;

    String userName, userEmail, userGender, userNumber, userDOB;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* toolbar = new Toolbar(this);
        toolbar.setTitle("My Toolbar Title");
        setContentView(R.layout.activity_user_profile);
        // Set the toolbar as the action bar
        // setSupportActionBar(toolbar);
        // Set the title color
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
         // Inflate the menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_activity_menu, menu);
        */

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        if(firebaseUser == null){
            Toast.makeText(this, "No Data Found, Try Later!", Toast.LENGTH_SHORT).show();
        }
        else{
            checkEmail(firebaseUser);
            binding.progressBar.setVisibility(View.VISIBLE);
            showUserDetails(firebaseUser);
        }

        binding.CardCircleView.setOnClickListener(view -> {

           // startActivity(new Intent(UserProfileActivity.this, UploadProfilePicActivity.class));

        });

    }

    private void checkEmail(FirebaseUser firebaseUser) {

        if(!firebaseUser.isEmailVerified()){

            AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
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
            builder.create();

        }
    }

    private void showUserDetails(FirebaseUser firebaseUser) {

        String userID = firebaseUser.getUid();
        DatabaseReference  reference = FirebaseDatabase.getInstance().getReference("RegisterUsers");

         reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {

                 ReadWriteUserData userData = snapshot.getValue(ReadWriteUserData.class);
                if(userData != null){

                    userName = userData.name;
                    userEmail = userData.email;


                    binding.txtHello.setText("Hello! "+userName);
                    binding.txtName.setText(userName);
                    binding.txtEmail.setText(userEmail);


                    Uri uri = firebaseUser.getPhotoUrl();
                    Glide.with(getApplicationContext()).load(uri).into(binding.userImg);


                }
                else {

                    Toast.makeText(UserProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                binding.progressBar.setVisibility(View.GONE);

             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

                 Toast.makeText(UserProfileActivity.this, "Error! "+ error, Toast.LENGTH_SHORT).show();

             }
         });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id  = item.getItemId();

        if(id== R.id.op_refresh) {
            //Refresh
            startActivity(getIntent());
            finish();
        }

        else if(id == R.id.op_update_profile) {
            /*Intent intent = new Intent(UserProfileActivity.this,UpdateProfileActivity.class);
            startActivity(intent);*/
            Toast.makeText(this, "Update Profile", Toast.LENGTH_SHORT).show();
        }
        else if(id== R.id.op_update_email)
        {
            /*Intent intent = new Intent(UserProfileActivity.this,UpdateProfileActivity.class);
            startActivity(intent); */
            Toast.makeText(this, "Update Profile", Toast.LENGTH_SHORT).show();
        }
        else if(id== R.id.op_settings)
        {
            Toast.makeText(this, "Settings ", Toast.LENGTH_SHORT).show();

        }
        else if(id== R.id.op_delete_profile)
        {
           /* Intent intent = new Intent(UserProfileActivity.this,UpdateProfileActivity.class);
            startActivity(intent);*/
            Toast.makeText(this, "Delete Profile", Toast.LENGTH_SHORT).show();

        }
        else if(id== R.id.op_logout)
        {

            firebaseAuth.signOut();
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserProfileActivity.this, IntroActivity.class);
            startActivity(intent);
            finish();

            /*Intent intent = new Intent(UserProfileActivity.this,UpdateProfileActivity.class);
            //Clear stack to prevent user coming back to UserprofileActivity on pressing back button after Logging out
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); //Close UserProfile Activity*/
        }
        else
        {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);

    }

    }
