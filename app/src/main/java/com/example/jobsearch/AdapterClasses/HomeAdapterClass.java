package com.example.jobsearch.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jobsearch.ModelClasses.UserDataModelClass;
import com.example.jobsearch.R;
import com.example.jobsearch.ViewActivity;
import com.example.jobsearch.databinding.RecyclerItemLayoutBinding;

import java.util.ArrayList;

public class HomeAdapterClass extends RecyclerView.Adapter<HomeAdapterClass.ViewHolder> {

    ArrayList<UserDataModelClass> arrayList;
    Context context;

    public HomeAdapterClass(ArrayList<UserDataModelClass> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDataModelClass data = arrayList.get(position);

        holder.binding.personName.setText(data.getName());
        holder.binding.personEducation.setText(data.getProfession());


       // String imageUrl= data.imUrl;
        Glide.with(context).load(data.getImUrl()).placeholder(R.drawable.ic_person).into(holder.binding.personImage);

        holder.itemView.setOnClickListener(view -> {

           //  Toast.makeText(context, "Detail feature under development", Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("name", data.getName());
            bundle.putString("education", data.getEducation());
            bundle.putString("imageUrl", data.getImUrl());
            bundle.putString("email", data.getEmail());
            bundle.putString("salary", data.getSalary());
            bundle.putString("profession", data.getProfession());
            bundle.putString("gender", data.getGender());
            bundle.putString("dob", data.getDob());
            bundle.putString("description", data.getDescription());
            bundle.putString("phoneNo", data.getPhoneNo());
            bundle.putString("location", data.getLocation());
            bundle.putString("skills", data.getSkills());
            bundle.putString("experience", data.getExperience());




            // Start the new fragment and pass the bundle
            Intent intent = new Intent(context, ViewActivity.class);
             intent.putExtras(bundle);
            context.startActivity(intent);

        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerItemLayoutBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RecyclerItemLayoutBinding.bind(itemView);
        }
    }
}
