package com.example.demoapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vo.AppointmentInfo;
import com.vo.PetInfo;

import java.util.ArrayList;

public class PetRVAdapter extends RecyclerView.Adapter<PetRVAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<PetInfo> petArrayList;
    private Context context;

    // creating constructor for our adapter class
    public PetRVAdapter(ArrayList<PetInfo> petArrayList, Context context) {
        this.petArrayList = petArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_pet_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        PetInfo petInfo = petArrayList.get(position);

        Log.d("AppointmentIRVAdapter","PetName: "+petInfo.getPetName());
        holder.petNameTV.setText(petInfo.getPetName());
        holder.ownerTV.setText(petInfo.getOwner());
        holder.ageTV.setText(petInfo.getAge());
        holder.genderTV.setText(petInfo.getGender());
        holder.dateofBirthTV.setText(petInfo.getDateOfBirth());
        holder.speciesTV.setText(petInfo.getSpecies());
        holder.breedTV.setText(petInfo.getBreed());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return petArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView petNameTV;
        private final TextView ownerTV;
        private final TextView ageTV;
        private final TextView genderTV;
        private final TextView dateofBirthTV;
        private final TextView speciesTV;
        private final TextView breedTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.

            petNameTV = itemView.findViewById(R.id.idTVPetName);
            ownerTV = itemView.findViewById(R.id.idTVOwnerName);
            ageTV = itemView.findViewById(R.id.idTVAge);
            genderTV = itemView.findViewById(R.id.idTVGender);
            dateofBirthTV = itemView.findViewById(R.id.idTVDateOfBirth);
            speciesTV = itemView.findViewById(R.id.idTVSpecies);
            breedTV = itemView.findViewById(R.id.idTVBreed);
        }
    }
}