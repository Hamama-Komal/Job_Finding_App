package com.example.jobsearch.ProfileActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jobsearch.LoginActivities.SignUpActivity;
import com.example.jobsearch.MainActivity;
import com.example.jobsearch.ModelClasses.ReadWriteUserData;
import com.example.jobsearch.ModelClasses.UserDataModelClass;
import com.example.jobsearch.R;
import com.example.jobsearch.ViewActivity;
import com.example.jobsearch.databinding.ActivitySetuoProfileBinding;
import com.example.jobsearch.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;
import java.util.Calendar;

public class SetupProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ActivitySetuoProfileBinding binding;
    String name, email, imUrl, gender, phoneNo, dob, education, profession, description, salary, location, skills, experience;
    private DatePickerDialog picker;
    private FirebaseAuth firebaseAuth;

    int role = 0;

    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetuoProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        String userName = getIntent().getStringExtra("name");
        String userEmail = getIntent().getStringExtra("email");

        binding.editTextName.setText(userName);
        binding.editTextEmail.setText(userEmail);




/*

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapter.addAll(Arrays.asList("Apple", "Banana", "Orange", "Grape"));
        // Set the adapter to the AutoCompleteTextView
        binding.edtSalary.setAdapter(adapter);
        // Set the threshold for displaying suggestions
        binding.edtSalary.setThreshold(1);
        // Handle the item selection event
        binding.edtSalary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override


            public

            void

            onItemClick(AdapterView<?> parent, View view, int position, long id)

            {
                String selectedItem = binding.edtSalary.getText().toString();
                Toast.makeText(SetupProfileActivity.this, "You selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });


*/





         //  imUrl =  getIntent().getStringExtra("url");
        //  Glide.with(this).load(imUrl).into(binding.profilePicture);


        binding.profilePicture.setOnClickListener(view -> {

            //  startActivity(new Intent(SetupProfileActivity.this, UploadProfilePicActivity.class));
           // showImage();


            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 45);



        });


        binding.editTextDOB.setOnClickListener(view -> {
            showDatePicker();
        });


        binding.btnNext.setOnClickListener(view -> {

            checkInputValues();
        });

    }

    private void showImage() {

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();
        DatabaseReference  reference = FirebaseDatabase.getInstance().getReference("RegisterUsers");
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserData userData = snapshot.getValue(ReadWriteUserData.class);
                if(userData != null) {

                    Uri uri = firebaseUser.getPhotoUrl();
                    Glide.with(getApplicationContext()).load(uri).into(binding.profilePicture);
                }
                else{
                    Toast.makeText(SetupProfileActivity.this, "No image found", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void checkInputValues() {

        name = binding.editTextName.getText().toString().trim();
        email = binding.editTextEmail.getText().toString().trim();
        dob = binding.editTextDOB.getText().toString().trim();
        phoneNo = "92" + binding.editTextPhone.getText().toString().trim();
        education = binding.editEducation.getText().toString().trim();
        profession = binding.editProfession.getText().toString().trim();
        description = binding.edtDescription.getText().toString().trim();
        salary = binding.editSalary.getText().toString().trim();
        location = binding.editTextLocation.getText().toString().trim();
        skills = binding.editSkill.getText().toString().trim();
        experience = binding.editExperience.getText().toString().trim();



        if(binding.rbJobSeeker.isChecked()) {
            role = 1;
        }
        else if(binding.rbHiring.isChecked()){
            role = 2;

        } else if (role == 0) {
            Toast.makeText(this, "Please select your role", Toast.LENGTH_SHORT).show();
        }

        if (binding.rbFemale.isChecked()) {
            gender = "Female";
        }
        if (binding.rbMale.isChecked()) {
            gender = "Male";
        }
        // Checks

        if (TextUtils.isEmpty(name)) {
            binding.editTextName.setError("Please Enter Your Name!");
            binding.editTextName.requestFocus();
        }
        else if (TextUtils.isEmpty(email)) {
            binding.editTextEmail.setError("Email is required!");
            binding.editTextEmail.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.setError("Please Enter a Valid Email!");
            binding.editTextEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(phoneNo)) {
            binding.editTextPhone.setError("Contact Number is required!");
            binding.editTextPhone.requestFocus();
        }
        else if (phoneNo.length() > 13) {
            binding.editTextPhone.setError("Contact Number should be of 12 digits");
            binding.editTextPhone.requestFocus();
        }
        else if (TextUtils.isEmpty(dob)) {
            binding.editTextDOB.setError("Please Enter Your DOB!");
            binding.editTextDOB.requestFocus();
        }
        else if (TextUtils.isEmpty(gender)) {
            Toast.makeText(this, "Please select the gender", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(education)) {
            binding.editEducation.setError("Please Enter Your Details");
            binding.editEducation.requestFocus();
        }
        else if (TextUtils.isEmpty(profession)) {
            binding.editProfession.setError("Please Enter Your Details");
            binding.editProfession.requestFocus();
        }/* else if (TextUtils.isEmpty(salary)) {
            binding.editSalary.setError("Please Enter Your Details");
            binding.editSalary.requestFocus();
        }*/
        else {
            binding.progressBar.setVisibility(View.VISIBLE);
            InputDataIntoDatabase(name, email, imUrl, gender, phoneNo, dob, education, profession, description, salary, location, skills, experience);

        }


    }

    private void InputDataIntoDatabase(String name, String email, String imUrl, String gender, String phoneNo, String dob, String education, String profession, String description, String salary,String location, String skills, String experience) {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        //Enter User Data into the Firebase Realtime Database.
        UserDataModelClass userdata = new UserDataModelClass(name, email, imUrl, gender, phoneNo, dob, education, profession, description, salary, location, skills, experience);

        if(role == 1) {
            //Extracting User reference from Database for "Registered Users"
            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("JobSeeker");
            referenceProfile.child(firebaseUser.getUid()).setValue(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        firebaseUser.sendEmailVerification();

                        Toast.makeText(SetupProfileActivity.this, "Your profile is setup up successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SetupProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();

                    } else {

                        Toast.makeText(SetupProfileActivity.this, "Try again", Toast.LENGTH_SHORT).show();

                    }
                    binding.progressBar.setVisibility(View.GONE);

                }

            });

        } else if (role == 2 ) {

            //Extracting User reference from Database for "Registered Users"
            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("HiringPersonnel");
            referenceProfile.child(firebaseUser.getUid()).setValue(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        firebaseUser.sendEmailVerification();

                        Toast.makeText(SetupProfileActivity.this, "Your profile is setup up successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SetupProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();

                    } else {

                        Toast.makeText(SetupProfileActivity.this, "Try again", Toast.LENGTH_SHORT).show();

                    }
                    binding.progressBar.setVisibility(View.GONE);

                }

            });

        }

        else{
            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Something try later", Toast.LENGTH_SHORT).show();

        }

    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
       //Date Picker Dialog


        picker = new DatePickerDialog(SetupProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                binding.editTextDOB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);

        picker.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            uri = data.getData();
            binding.profilePicture.setImageURI(uri);

            getImageUrl(uri);
        }
    }

    private void getImageUrl(Uri uri) {

        FirebaseStorage.getInstance().getReference().child("Pictures").child(firebaseAuth.getUid()).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                imUrl = uriTask.getResult().toString();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SetupProfileActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}