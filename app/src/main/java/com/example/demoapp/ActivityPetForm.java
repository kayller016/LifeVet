package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.vo.PetInfo;

import java.util.Random;

public class ActivityPetForm extends AppCompatActivity {

    EditText petName;
    EditText age;
    EditText gender;
    EditText dateOfBirth;
    EditText species;
    EditText breed;
    private  FirebaseFirestore fireBaseDB;
    String ownerId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();

        String username = bundle.getString("username", "DefaultUsername");
        String password = bundle.getString("password", "DefaultPassword");
        boolean memberCheckBox = bundle.getBoolean("memberCheckBox", true);
        Log.d("ActivityPetForm", "ActivityPetForm: username and pw is: " + username + " " + password);
        ownerId = username;

        setContentView(R.layout.activity_petform);
        Button savePet = findViewById(R.id.savePet);

        petName = findViewById(R.id.petName);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        species = findViewById(R.id.species);
        breed = findViewById(R.id.breed);


        savePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPet();
                Intent addPet = new Intent(ActivityPetForm.this,PetProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key1", "GFG :- Main Activity");

                bundle.putString("username", username);
                bundle.putString("password", password);
                bundle.putBoolean("memberCheckBox",memberCheckBox);
                addPet.putExtras(bundle);
                startActivity(addPet);
            }
        });

    }
    public void addPet(){
        Log.d("ActivityPetForm-addPet", "ActivityPetForm: ownerId : " + ownerId );
        String strPetName = petName.getText().toString();
        String strAge = age.getText().toString();
        String strGender = gender.getText().toString();
        String strDateOfBirth = dateOfBirth.getText().toString();
        String strSpecies = species.getText().toString();
        String strBreed = breed.getText().toString();

        Random rand = new Random();
        int n = rand.nextInt(1000);

        PetInfo petInfo = new PetInfo();
        petInfo.setPetId(String.valueOf(n));
        petInfo.setPetName(strPetName);
        petInfo.setAge(strAge);
        petInfo.setGender(strGender);
        petInfo.setDateOfBirth(strDateOfBirth);
        petInfo.setSpecies(strSpecies);
        petInfo.setBreed(strBreed);
        petInfo.setOwner(ownerId);


        fireBaseDB = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        fireBaseDB.collection("pet")
                .add(petInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.w("ActivityPetInfo", "Pet info is added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ActivityPetInfo", "Error adding Pet Info", e);
                    }
                });

    }
}
