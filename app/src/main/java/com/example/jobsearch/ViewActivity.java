package com.example.jobsearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jobsearch.AdapterClasses.HomeAdapterClass;
import com.example.jobsearch.ModelClasses.UserDataModelClass;
import com.example.jobsearch.databinding.ActivityViewBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    ActivityViewBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressBar.setVisibility(View.VISIBLE);

        // Retrieve the data from the Bundle object
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String education = bundle.getString("education");
        String imageUrl = bundle.getString("imageUrl");
        String email = bundle.getString("email");
        String salary = bundle.getString("salary");
        String profession = bundle.getString("profession");
        String gender = bundle.getString("gender");
        String dob = bundle.getString("dob");
        String description = bundle.getString("description");
        String phoneNo = bundle.getString("phoneNo");
        String location = bundle.getString("location");
        String skills = bundle.getString("skills");
        String experience = bundle.getString("experience");


        binding.txtHello.setText(name);
        binding.txtName.setText(name);
        binding.txtDOB.setText(dob);
        binding.txtGender.setText(gender);
        binding.txtSalary.setText(salary);
        binding.txtEducation.setText(education);
        binding.txtDescription.setText(description);
        binding.txtEmail.setText(email);
        binding.txtNumber.setText(phoneNo);
        binding.txtExperience.setText(experience);
        binding.txtJobCompany.setText(profession);
        binding.txtLocation.setText(location);
        binding.txtSkill.setText(skills);

        binding.progressBar.setVisibility(View.GONE);

        Glide.with(this).load(imageUrl).placeholder(R.drawable.ic_person).into(binding.userImg);

          String number = "+" + phoneNo;

        binding.btnCall.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
        });

        binding.btnMail.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + email)); // Replace with the actual email address
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject"); // Optional subject line
            intent.putExtra(Intent.EXTRA_TEXT, "Message body"); // Optional message body
            startActivity(intent);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No email app is installed", Toast.LENGTH_SHORT).show();
            }

        });


        binding.btnWhatsapp.setOnClickListener(view -> {
            String url = "https://wa.me/" + number + "?text=Hello%20there!";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "WhatsApp is not installed or not configured correctly", Toast.LENGTH_SHORT).show();
            }

        });
    }
}