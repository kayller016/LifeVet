package com.example.demoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.vo.AppointmentInfo;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDetails extends AppCompatActivity {

    // creating variables for our recycler view,
    // array list, adapter, firebase firestore
    // and our progress bar.
    private RecyclerView appointmentRV;
    private ArrayList<AppointmentInfo> appointmentArrayList;
    private AppointmentRVAdapter appointmentRVAdapter;
    private FirebaseFirestore db;
    ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        appointmentRV = findViewById(R.id.idRVAppointment);
        loadingPB = findViewById(R.id.idProgressBar);

        db = FirebaseFirestore.getInstance();

        // creating our new array list
        appointmentArrayList = new ArrayList<>();
        appointmentRV.setHasFixedSize(true);
        appointmentRV.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        appointmentRVAdapter = new AppointmentRVAdapter(appointmentArrayList, this);

        // setting adapter to our recycler view.
        appointmentRV.setAdapter(appointmentRVAdapter);


        db.collection("appointment").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are
                            // hiding our progress bar and adding
                            // our data in a list.
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                AppointmentInfo appInfo = d.toObject(AppointmentInfo.class);
                                Log.d("AppointmentDetails","PetName:"+appInfo.getPetName());

                                appointmentArrayList.add(appInfo);
                            }

                            appointmentRVAdapter.notifyDataSetChanged();
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(AppointmentDetails.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                        Toast.makeText(AppointmentDetails.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}


