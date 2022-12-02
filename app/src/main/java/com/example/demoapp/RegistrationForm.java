package com.example.demoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.persistence.PersistenceStorageEngine;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.vo.MemberInfo;
import com.vo.VetInfo;

import android.content.Intent;
import android.net.Uri;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class RegistrationForm extends AppCompatActivity {

    EditText regMemberId;
    EditText regPassword;
    EditText regFirstName;
    EditText regMiddleName;
    EditText regLastName;
    EditText regAge;
    EditText regGender;
    EditText regAddress;
    EditText regContactNumber;


    EditText regVetId;
    EditText regSpecialty;
    EditText regPrice;

    Button register;

    FirebaseAuth mAuth;

    private  FirebaseFirestore fireBaseDB;
    private static final String TAG = "RegistrationForm";

    //Image Upload
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private EditText mEditFileName;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Log.d("RegistrationForm","ontActivityResult: ");
            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                //Picasso.with(this).load(mImageUri).into();
                mImageUri = result.getData().getData();
            }
        }
    });

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Bundle bundle = getIntent().getExtras();
       mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
       mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
       boolean memberCheckBox = bundle.getBoolean("memberCheckBox", true);
       Log.d("print values", "Is member checked: " + memberCheckBox);
        if(memberCheckBox) {
            setContentView(R.layout.activity_registrationform);

            mButtonChooseImage = findViewById(R.id.button_choose_image);
            mButtonUpload = findViewById(R.id.button_upload);
            mEditFileName = findViewById(R.id.edit_text_file_name);



            mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openFileChooser();
                }
            });

            mButtonUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadFile();
                }
            });

            regMemberId = findViewById(R.id.regemail);
            regPassword = findViewById(R.id.regpass);
            regFirstName = findViewById(R.id.firstName);
            regMiddleName = findViewById(R.id.middleName);
            regLastName = findViewById(R.id.lastName);
            regAge = findViewById(R.id.age);
            regGender = findViewById(R.id.gender);
            regAddress = findViewById(R.id.address);
            regContactNumber = findViewById(R.id.contactNumber);

            register = findViewById(R.id.register);

            mAuth = FirebaseAuth.getInstance();

            register.setOnClickListener(view -> {
                createUser();
            });
        }else{
            setContentView(R.layout.activity_registrationform_vet);

            regVetId = findViewById(R.id.regemail);
            regPassword = findViewById(R.id.regpass);
            regFirstName = findViewById(R.id.firstName);
            regMiddleName = findViewById(R.id.middleName);
            regLastName = findViewById(R.id.lastName);
            regAge = findViewById(R.id.age);
            regGender = findViewById(R.id.gender);
            regAddress = findViewById(R.id.address);
            regContactNumber = findViewById(R.id.contactNumber);
            regSpecialty = findViewById(R.id.contactNumber);
            regPrice = findViewById(R.id.contactNumber);

            register = findViewById(R.id.register);

            mAuth = FirebaseAuth.getInstance();

            register.setOnClickListener(view -> {
                createUserVet();
            });
        }

    }
    private void openFileChooser(){
       Intent intent = new Intent();
       intent.setType("image/*");
       intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(intent);

    }
    private String getFileExtention(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    private void uploadFile(){
        if(mImageUri != null){
            StorageReference fileReference = mStorageRef.child(mEditFileName.getText().toString()+"."+getFileExtention(mImageUri));
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(RegistrationForm.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                           // Upload upload = new Upload(mEditFileName.getText().toString().trim(),taskSnapshot.)
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegistrationForm.this, "No file selected", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            //compute progress for progress bar
                        }
                    });
        }else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void addMemberInfo(String memberId,String firstName,String middleName, String lastName, int age, String gender, String address, String contactNumber, int numberOfPet, String role){

        MemberInfo memInfo = new MemberInfo();
        memInfo.setMemberId(memberId);
        memInfo.setFirstName(firstName);
        memInfo.setMiddleName(middleName);
        memInfo.setLastName(lastName);
        memInfo.setAge(age);
        memInfo.setGender(gender);
        memInfo.setNumberOfPet(0);
        memInfo.setAddress(address);
        memInfo.setContactNumber(contactNumber);
        memInfo.setRole("Pet Owner");

        // Create a new user with a first and last name
        fireBaseDB = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        fireBaseDB.collection("member")
                .add(memInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Member info is added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding Member Info", e);
                    }
                });
    }
    private void addVetInfo(String vetId,String firstName,String middleName, String lastName, int age, String gender, String specialty, String address, String contactNumber, double price, String role){

        VetInfo vetInfo = new VetInfo();
        vetInfo.setVetId(vetId);
        vetInfo.setFirstName(firstName);
        vetInfo.setMiddleName(middleName);
        vetInfo.setLastName(lastName);
        vetInfo.setAge(age);
        vetInfo.setGender(gender);
        vetInfo.setSpecialty(specialty);
        vetInfo.setAddress(address);
        vetInfo.setContactNumber(contactNumber);
        vetInfo.setPrice(price);
        vetInfo.setRole(role);

        // Create a new user with a first and last name
        fireBaseDB = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        fireBaseDB.collection("veterinarian")
                .add(vetInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Veterinarian details is added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding Veterinarian details", e);
                    }
                });
    }
    private void createUser() {

        String email = regMemberId.getText().toString();
        String pass = regPassword.getText().toString();

        String memberId = regMemberId.getText().toString();
        String firstName = regFirstName.getText().toString();
        String middleName = regMiddleName.getText().toString();
        String lastName = regLastName.getText().toString();
        int age = Integer.parseInt(regAge.getText().toString());
        String gender = regGender.getText().toString();
        String address = regAddress.getText().toString();
        String contactNumber = regContactNumber.getText().toString();
        int numberOfPet = 0;
        String role = "";




        if (TextUtils.isEmpty(firstName)) {
            regFirstName.setError("First Name cannot be empty");
            regFirstName.requestFocus();

        }else if (TextUtils.isEmpty(middleName)){
            regLastName.setError("Middle Name cannot be empty");
            regLastName.requestFocus();

        }else if (TextUtils.isEmpty(lastName)){
            regLastName.setError("Last Name cannot be empty");
            regLastName.requestFocus();

        }else if (TextUtils.isEmpty(pass)){
            regPassword.setError("Password cannot be empty");
            regPassword.requestFocus();

        }else if (TextUtils.isEmpty(email)){
            regMemberId.setError("Email cannot be empty");
            regMemberId.requestFocus();


        }else{
            Log.d(TAG, "email:"+email);
            Log.d(TAG, "pass:"+pass);
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegistrationForm.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                        addMemberInfo(memberId, firstName, middleName,  lastName,  age,  gender,  address,  contactNumber,  numberOfPet, "Pet Owner");

                        startActivity(new Intent(RegistrationForm.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RegistrationForm.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }
    }
    private void createUserVet() {

        String email = regVetId.getText().toString();
        String pass = regPassword.getText().toString();

        String vetId = regVetId.getText().toString();
        String firstName = regFirstName.getText().toString();
        String middleName = regMiddleName.getText().toString();
        String lastName = regLastName.getText().toString();
        int age = Integer.parseInt(regAge.getText().toString());
        String gender = regGender.getText().toString();
        String address = regAddress.getText().toString();
        String contactNumber = regContactNumber.getText().toString();
        String role = "Veterinarian";
        String specialty = regSpecialty.getText().toString();
        double price = Double.parseDouble(regPrice.getText().toString());




        if (TextUtils.isEmpty(firstName)) {
            regFirstName.setError("First Name cannot be empty");
            regFirstName.requestFocus();

        }else if (TextUtils.isEmpty(middleName)){
            regLastName.setError("Middle Name cannot be empty");
            regLastName.requestFocus();

        }else if (TextUtils.isEmpty(lastName)){
            regLastName.setError("Last Name cannot be empty");
            regLastName.requestFocus();

        }else if (TextUtils.isEmpty(pass)){
            regPassword.setError("Password cannot be empty");
            regPassword.requestFocus();

        }else if (TextUtils.isEmpty(email)){
            regMemberId.setError("Email cannot be empty");
            regMemberId.requestFocus();


        }else{
            Log.d(TAG, "email:"+email);
            Log.d(TAG, "pass:"+pass);
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegistrationForm.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                        addVetInfo(vetId, firstName, middleName,  lastName,  age, gender, specialty,  address, contactNumber, price,  "Veterinarian");
                        startActivity(new Intent(RegistrationForm.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RegistrationForm.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }
    }
}

