package com.example.jobsearch.DashBoardFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jobsearch.AdapterClasses.CommunityAdapter;
import com.example.jobsearch.AdapterClasses.HomeAdapterClass;
import com.example.jobsearch.ModelClasses.UserDataModelClass;
import com.example.jobsearch.R;
import com.example.jobsearch.databinding.FragmentSearchBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {


    public SearchFragment() {

    }
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;



    CommunityAdapter adapterClass;

    List<UserDataModelClass> list;

    FragmentSearchBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater,container, false);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("HiringPersonnel");
        mStorage = FirebaseStorage.getInstance();

        binding.recyclerCommunity.setHasFixedSize(true);

        binding.recyclerCommunity.setLayoutManager(new LinearLayoutManager(getContext()));


        list = new ArrayList<UserDataModelClass>();
        adapterClass = new CommunityAdapter((ArrayList<UserDataModelClass>) list, getContext());
        binding.recyclerCommunity.setAdapter(adapterClass);
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