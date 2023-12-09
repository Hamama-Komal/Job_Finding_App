package com.example.jobsearch.ProfileActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jobsearch.R;
import com.example.jobsearch.databinding.ActivityUploadProfilePicBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadProfilePicActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUREST = 1;
    ActivityUploadProfilePicBinding binding;
    private FirebaseAuth firebaseAuth;

    private FirebaseUser firebaseUser;

    private Uri imageUri;

    String url;

    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadProfilePicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("UsersPictures");
        Uri uri = firebaseUser.getPhotoUrl();


        Glide.with(this).load(uri).into(binding.profileImg);


        binding.butSelect.setOnClickListener(v -> {
            openGalleryToSelectImage();
        });
        binding.btnUpload.setOnClickListener(v -> {

            binding.progressBar.setVisibility(View.VISIBLE);
            UploadImage();

        });



    }

    private void UploadImage() {

        if (imageUri != null) {


            //Save the image with uid of the currently logged user
            StorageReference fileReference = storageReference.child(firebaseAuth.getCurrentUser().getUid() + "." + getFileExtension(imageUri));

            //Upload image to Storage
            fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUri = uri;
                            firebaseUser = firebaseAuth.getCurrentUser();

                            //Finally set the display image of the user after upload
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setPhotoUri(downloadUri).build();
                            firebaseUser.updateProfile(profileUpdate);

                        }
                    });

                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(UploadProfilePicActivity.this, "Upload Successful!", Toast.LENGTH_SHORT).show();

                    /*Intent intent = new Intent(UploadProfilePicActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                    finish();*/

                    Intent intent = new Intent(UploadProfilePicActivity.this, SetupProfileActivity.class);
                    intent.putExtra("url", imageUri);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(UploadProfilePicActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "No File Selected!", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openGalleryToSelectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUREST);
    }
    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUREST && resultCode == RESULT_OK && data!=null && data.getData() !=null)
        {
            imageUri = data.getData();
            binding.profileImg.setImageURI(imageUri);
           // Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
            // Send the Image Uri to realtimedata base





        }

    }


}