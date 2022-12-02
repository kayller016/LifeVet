package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vo.AppointmentInfo;

public class AppointmentMainActivity extends AppCompatActivity {

    // creating variables for our edit text
    private EditText petNameEdt, ownerNameEdt, vetNameEdt, appointmentDateEdt, appointmetTimeEdt;

    // creating variable for button
    private Button submitAppointmentBtn, viewAppointmentBtn;

    // creating a strings for storing
    // our values from edittext fields.
    private String petName, ownerName, vetName, appointmentDate, appointmetTime;

    // creating a variable
    // for firebasefirestore.
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_main);


        // getting our instance
        // from Firebase Firestore.
        db = FirebaseFirestore.getInstance();

        // initializing our edittext and buttons
        petNameEdt = findViewById(R.id.idEdtPetName);
        ownerNameEdt = findViewById(R.id.idEdtOwnerName);
        vetNameEdt = findViewById(R.id.idEdtVetName);
        appointmentDateEdt = findViewById(R.id.idEdtAppointmentDate);
        appointmetTimeEdt = findViewById(R.id.idEdtAppointmentTime);
        submitAppointmentBtn = findViewById(R.id.idBtnSubmitAppointment);
        viewAppointmentBtn = findViewById(R.id.idBtnViewAppointment);

        // adding onclick listener to view data in new activity
        viewAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity on button click
                Intent i = new Intent(AppointmentMainActivity.this, AppointmentDetails.class);
                startActivity(i);
            }
        });

        // adding on click listener for button
        submitAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting data from edittext fields.
                //private String petName, ownerName, vetName, appointmentDate, appointmetTime;
                //private EditText petNameEdt, ownerNameEdt, vetNameEdt, appointmentDateEdt, appointmetTimeEdt;
                petName = petNameEdt.getText().toString();
                ownerName = ownerNameEdt.getText().toString();
                vetName = vetNameEdt.getText().toString();
                appointmentDate = appointmentDateEdt.getText().toString();
                appointmetTime = appointmetTimeEdt.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(petName)) {
                    petNameEdt.setError("Please enter Pet Name");
                } else if (TextUtils.isEmpty(ownerName)) {
                    ownerNameEdt.setError("Please enter Owner Name");
                } else if (TextUtils.isEmpty(vetName)) {
                    vetNameEdt.setError("Please enter Vet Name");
                } else if (TextUtils.isEmpty(appointmentDate)) {
                    appointmentDateEdt.setError("Please enter Appointment Date");
                }else if (TextUtils.isEmpty(appointmetTime)) {
                    appointmetTimeEdt.setError("Please enter Appointment Time");
                } else {
                    // calling method to add data to Firebase Firestore.
                    addDataToFirestore(petName, ownerName, vetName, appointmentDate, appointmetTime);
                }
            }
        });
    }

    private void addDataToFirestore(String petName, String ownerName, String vetName, String appointmentDate, String appointmentTime) {

        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference dbAppointments = db.collection("appointment");

        // adding our data to our AppointmentInfo object class.
        AppointmentInfo appointments = new AppointmentInfo(petName, ownerName, vetName, appointmentDate, appointmentTime);


        // below method is use to add data to Firebase Firestore.
        dbAppointments.add(appointments).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(AppointmentMainActivity.this, "Your Appointment has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(AppointmentMainActivity.this, "Fail to add Appointment \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}