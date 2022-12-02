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

import java.util.ArrayList;

public class AppointmentRVAdapter extends RecyclerView.Adapter<AppointmentRVAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<AppointmentInfo> appointmentArrayList;
    private Context context;

    // creating constructor for our adapter class
    public AppointmentRVAdapter(ArrayList<AppointmentInfo> appointmentArrayList, Context context) {
        this.appointmentArrayList = appointmentArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_appointment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        AppointmentInfo appInfo = appointmentArrayList.get(position);
        Log.d("AppointmentIRVAdapter","PetName: "+appInfo.getPetName());
        holder.petNameTV.setText(appInfo.getPetName());
        holder.vetNameTV.setText(appInfo.getVetName());
        holder.memberNameTV.setText(appInfo.getMemberName());
        holder.appointmentDateTV.setText(appInfo.getScheduleDate());
        holder.appointmentTimeTV.setText(appInfo.getScheduleTime());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return appointmentArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView petNameTV;
        private final TextView vetNameTV;
        private final TextView memberNameTV;
        private final TextView appointmentDateTV;
        private final TextView appointmentTimeTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            petNameTV = itemView.findViewById(R.id.idTVPetName);
            vetNameTV = itemView.findViewById(R.id.idTVVetName);
            memberNameTV = itemView.findViewById(R.id.idTVOwnerName);
            appointmentDateTV = itemView.findViewById(R.id.idTVAppointmentDate);
            appointmentTimeTV = itemView.findViewById(R.id.idTVAppointmentTime);
        }
    }
}