package com.example.jobsearch.DashBoardFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.jobsearch.AdapterClasses.HomeAdapterClass;
import com.example.jobsearch.ModelClasses.UserDataModelClass;
import com.example.jobsearch.ProfileActivities.UserProfileActivity;
import com.example.jobsearch.R;
import com.example.jobsearch.databinding.FragmentHomeBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;



    HomeAdapterClass adapterClass;

    List<UserDataModelClass> list;


    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

      // binding.progressBar2.setVisibility(View.VISIBLE);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("JobSeeker");
        mStorage = FirebaseStorage.getInstance();

        binding.recyclerview.setHasFixedSize(true);

        binding.recyclerview.setLayoutManager(new GridLayoutManager(getContext(),2));


        list = new ArrayList<UserDataModelClass>();
        adapterClass = new HomeAdapterClass((ArrayList<UserDataModelClass>) list, getContext());
        binding.recyclerview.setAdapter(adapterClass);
        //binding.progressBar2.setVisibility(View.GONE);

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserDataModelClass dataModelClass = snapshot.getValue(UserDataModelClass.class);
                list.add(dataModelClass);
                adapterClass.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();

    }
}