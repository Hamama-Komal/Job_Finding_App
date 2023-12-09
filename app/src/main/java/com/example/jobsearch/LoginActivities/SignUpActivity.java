package com.example.jobsearch.LoginActivities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jobsearch.MainActivity;
import com.example.jobsearch.ModelClasses.ReadWriteUserData;
import com.example.jobsearch.ProfileActivities.SetupProfileActivity;
import com.example.jobsearch.ProfileActivities.UserProfileActivity;
import com.example.jobsearch.R;
import com.example.jobsearch.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;

    String userName, userEmail, userNumber, userPassword, userDOB , userGender;
    private DatePickerDialog picker;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth =FirebaseAuth.getInstance();

       /*binding.editTextDOB.setOnClickListener(view -> {
              showDatePicker();
        });*/


        binding.btnNext.setOnClickListener(view -> {

            checkInputValues();
        });


        binding.txtSignIn.setOnClickListener(view -> {
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            finish();
        });

    }

    private void showDatePicker() {

        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
//Date Picker Dialog


        picker = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

               // binding.editTextDOB.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        }, year ,month,day);

        picker.show();
    }

    private void checkInputValues() {

        userName = binding.editTextName.getText().toString().trim();
        userEmail = binding.editTextEmail.getText().toString().trim();
       /* userDOB = binding.editTextDOB.getText().toString().trim();
        userNumber = "92" + binding.editTextPhone.getText().toString().trim();*/
        userPassword = binding.editTextPassword.getText().toString().trim();
        String conPassword = binding.editTextConPassword.getText().toString().trim();

       // Toast.makeText(this, userNumber, Toast.LENGTH_SHORT).show();

      /* //  Mobile Number Check
        String mobileRegex = "[9][2]{10}"; // First no. can be {6,8,9} and rest 9 nos. can be any no.
        Matcher mobileMatcher;
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        mobileMatcher = mobilePattern.matcher(userNumber);
*/
        // Get Radio button values
      /*  if(binding.rbFemale.isChecked()){
            userGender = "Female";
        }
        if (binding.rbMale.isChecked()) {
            userGender = "Male";
        }*/

        // Checks

        if (TextUtils.isEmpty(userName)){
            binding.editTextName.setError("Please Enter Your Name!");
            binding.editTextName.requestFocus();
        }
        else if (TextUtils.isEmpty(userEmail)){
            binding.editTextEmail.setError("Email is required!");
            binding.editTextEmail.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            binding.editTextEmail.setError("Please Enter a Valid Email!");
            binding.editTextEmail.requestFocus();
        }
        /*else if (TextUtils.isEmpty(userDOB)){
            binding.editTextDOB.setError("Please Enter Your DOB!");
            binding.editTextDOB.requestFocus();
        }
        else if (TextUtils.isEmpty(userNumber)){
            binding.editTextPhone.setError("Contact Number is required!");
            binding.editTextPhone.requestFocus();
        }*/
       /* else if(!mobileMatcher.find()) {
            Toast.makeText(this, "Please re-enter your mobile no.", Toast.LENGTH_SHORT).show();
            binding.editTextPhone.setError("Mobile No. is not valid");
            binding.editTextPhone.requestFocus();
        }*/
       /* else if(userNumber.length() > 13)
        {
            binding.editTextPhone.setError("Contact Number should be of 12 digits");
            binding.editTextPhone.requestFocus();
        }*/
        else if (TextUtils.isEmpty(userPassword)){
            binding.editTextPassword.setError("Please Set a Password!");
            binding.editTextPassword.requestFocus();
        }
        else if (userPassword.length() < 7){
            binding.editTextPassword.setError("Password must contain at least 7-characters!");
            binding.editTextPassword.requestFocus();
        }
        else if (TextUtils.isEmpty(conPassword)){
            binding.editTextConPassword.setError("Please Confirm Your Password!");
            binding.editTextConPassword.requestFocus();
        }
        else if (!conPassword.equals(userPassword)){
            binding.editTextConPassword.setError("Password not match!");
            binding.editTextConPassword.requestFocus();
        }
       /* else if (TextUtils.isEmpty(userGender)) {
            Toast.makeText(this, "Please select the gender", Toast.LENGTH_SHORT).show();

        }*/ else {
            binding.progressBar.setVisibility(View.VISIBLE);
            registerUser(userName,userEmail,userDOB,userGender,userNumber,userPassword);

        }
    }


    private void registerUser(String userName, String userEmail, String userDob, String userGender, String userNumber, String userPassword) {



        //  create User Profile (sign in)
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


                    //Enter User Data into the Firebase Realtime Database.
                    ReadWriteUserData userdata = new ReadWriteUserData(userName, userEmail);

                    //Extracting User reference from Database for "Registered Users"
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("RegisterUsers");
                    referenceProfile.child(firebaseUser.getUid()).setValue(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                firebaseUser.sendEmailVerification();

                                Toast.makeText(SignUpActivity.this, "You SignUp successfully! \n Now Setup your profile", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(SignUpActivity.this, SetupProfileActivity.class);
                                intent.putExtra("name", userName);
                                intent.putExtra("email", userEmail);
                                startActivity(intent);
                                finish();

                            }

                            else {

                                Toast.makeText(SignUpActivity.this, "Registration failed,try again", Toast.LENGTH_SHORT).show();

                            }
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    });
                           //Send Verification Email
                             firebaseUser.sendEmailVerification();
                }
                else
                {

                    try{

                        throw task.getException();

                    }
                    catch (FirebaseAuthWeakPasswordException e)
                    {
                        binding.editTextPassword.setError("Your password is weak!");
                        binding.editTextPassword.requestFocus();

                    } catch (FirebaseAuthInvalidCredentialsException e)
                    {
                        binding.editTextEmail.setError("Your email is invalid!");
                        binding.editTextEmail.requestFocus();

                    }
                    catch (FirebaseAuthUserCollisionException e)
                    {

                        binding.editTextEmail.setError("This email already registered..");
                        binding.editTextEmail.requestFocus();

                    }
                    catch (Exception e)
                    {
                        Log.e(TAG,e.toString());
                        Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                    }

                    binding.progressBar.setVisibility(View.GONE);




                }
            }
        });

    }
}