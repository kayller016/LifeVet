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
import com.vo.PetInfo;

public class PetMainActivity extends AppCompatActivity {

    // creating variables for our edit text


    private EditText petNameEdt, ownerNameEdt, ageEdt, genderEdt, dateOfBirthEdt, speciesEdt, breedEdt;

    // creating variable for button
    private Button submitPetBtn, viewPetBtn;

    // creating a strings for storing
    // our values from edittext fields.
    private String petName, ownerName, age, gender, dateOfBirth, species, breed;

    // creating a variable
    // for firebasefirestore.
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_main);


        // getting our instance
        // from Firebase Firestore.
        db = FirebaseFirestore.getInstance();

        // initializing our edittext and buttons
        petNameEdt = findViewById(R.id.idEdtPetName);
        ownerNameEdt = findViewById(R.id.idEdtOwnerName);
        ageEdt = findViewById(R.id.idEdtAge);
        genderEdt = findViewById(R.id.idEdtGender);
        dateOfBirthEdt = findViewById(R.id.idEdtDateOfBirth);
        speciesEdt = findViewById(R.id.idEdtSpecies);
        breedEdt = findViewById(R.id.idEdtBreed);
        viewPetBtn = findViewById(R.id.idBtnViewPet);
        submitPetBtn = findViewById(R.id.idBtnSubmitPet);

        // adding onclick listener to view data in new activity
        viewPetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity on button click
                Intent i = new Intent(PetMainActivity.this, PetDetails.class);
                startActivity(i);
            }
        });

        // adding on click listener for button
        submitPetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting data from edittext fields.
                //private String petName, ownerName, vetName, appointmentDate, appointmetTime;
                //private EditText petNameEdt, ownerNameEdt, vetNameEdt, appointmentDateEdt, appointmetTimeEdt;
                petName = petNameEdt.getText().toString();
                ownerName = ownerNameEdt.getText().toString();
                age = ageEdt.getText().toString();
                gender = genderEdt.getText().toString();
                dateOfBirth = dateOfBirthEdt.getText().toString();
                species = speciesEdt.getText().toString();
                breed = breedEdt.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(petName)) {
                    petNameEdt.setError("Please enter Pet Name");
                } else if (TextUtils.isEmpty(ownerName)) {
                    ownerNameEdt.setError("Please enter Owner Name");
                } else if (TextUtils.isEmpty(age)) {
                    ageEdt.setError("Please enter Age");
                } else if (TextUtils.isEmpty(gender)) {
                    genderEdt.setError("Please enter Gender");
                }else if (TextUtils.isEmpty(dateOfBirth)) {
                    dateOfBirthEdt.setError("Please enter Date of Birth");
                }else if (TextUtils.isEmpty(species)) {
                    speciesEdt.setError("Please enter Species");
                }else if (TextUtils.isEmpty(breed)) {
                    breedEdt.setError("Please enter Breed");
                } else {
                    // calling method to add data to Firebase Firestore.
                    addDataToFirestore(petName, ownerName, age, gender, dateOfBirth, species, breed);
                }
            }
        });
    }

    private void addDataToFirestore(String petName, String ownerName, String age, String gender, String dateOfBirth, String species, String breed) {

        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference dbAppointments = db.collection("appointment");

        // adding our data to our PetInfo object class.
        PetInfo pets = new PetInfo(petName, ownerName, age, gender, dateOfBirth, species, breed);


        // below method is use to add data to Firebase Firestore.
        dbAppointments.add(pets).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(PetMainActivity.this, "Your Pet has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(PetMainActivity.this, "Fail to add pet \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}