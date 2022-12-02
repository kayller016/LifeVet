package com.example.demoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toolbar;
import android.widget.TextView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Query.Direction;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vo.MemberInfo;
import android.view.LayoutInflater;
import android.content.Context;
import android.view.View;

public class MainPage extends AppCompatActivity {
        DrawerLayout drawerLayout;
        NavigationView navigationView;
        Toolbar toolbar;
        ActionBarDrawerToggle actionBarDrawerToggle;
    Bundle bundle = null;
    private FirebaseFirestore fireBaseDB;

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            if(actionBarDrawerToggle.onOptionsItemSelected(item)){
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


        bundle = getIntent().getExtras();

        String username = bundle.getString("username", "DefaultUsername");
        String password = bundle.getString("password", "DefaultPassword");
        boolean memberCheckBox = bundle.getBoolean("memberCheckBox", true);
        Log.d("MainPage", "Main Activity: username and pw is: " + username + " " + password);
        Log.d("MainPage", "MemberCheckBox: " + memberCheckBox);
        if(memberCheckBox) {
            fireBaseDB = FirebaseFirestore.getInstance();
            fireBaseDB.collection("member")
                    .whereEqualTo("memberId", username)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        public MemberInfo memInfo = null;

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("MainPage", document.getId() + " => " + document.getData());
                                    memInfo = document.toObject(MemberInfo.class);

                                    Log.d("MainPage", "memInfo First Name: " + memInfo.getFirstName());
                                    Log.d("MainPage", "memInfo Address: " + memInfo.getAddress());
                                    if (memInfo != null) {
                                        Log.d("MainPage", "Inside memInfo check if null");
                                        TextView textViewFullName = (TextView) findViewById(R.id.textView3);
                                        textViewFullName.setText(memInfo.getFirstName() + " " + memInfo.getMiddleName() + " " + memInfo.getLastName());

                                        TextView textViewAddress = (TextView) findViewById(R.id.textView5);
                                        textViewAddress.setText(memInfo.getAddress());
                                    }
                                }
                            } else {
                                Log.d("MainPage", "Error getting Member Details: ", task.getException());
                            }
                        }
                    });
        }else{
            fireBaseDB = FirebaseFirestore.getInstance();
            fireBaseDB.collection("veterinarian")
                    .whereEqualTo("vetId", username)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        public MemberInfo vetInfo = null;

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("MainPage", document.getId() + " => " + document.getData());
                                    vetInfo = document.toObject(MemberInfo.class);

                                    Log.d("MainPage", "vetInfo First Name: " + vetInfo.getFirstName());
                                    Log.d("MainPage", "vetInfo Address: " + vetInfo.getAddress());
                                    if (vetInfo != null) {
                                        Log.d("MainPage", "Inside memInfo check if null");
                                        TextView textViewFullName = (TextView) findViewById(R.id.textView3);
                                        textViewFullName.setText(vetInfo.getFirstName() + " " + vetInfo.getMiddleName() + " " + vetInfo.getLastName());

                                        TextView textViewRole = (TextView) findViewById(R.id.textView4);
                                        textViewRole.setText(vetInfo.getRole());

                                        TextView textViewAddress = (TextView) findViewById(R.id.textView5);
                                        textViewAddress.setText(vetInfo.getAddress());
                                    }
                                }
                            } else {
                                Log.d("MainPage", "Error getting Vet Details: ", task.getException());
                            }
                        }
                    });

        }
            setContentView(R.layout.activity_home);

            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.navigation_view);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.menu_Open,R.string.close_menu);
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent myintent = null;
                switch (item.getItemId()){
                    case R.id.nav_profile:
                        myintent = new Intent(MainPage.this,HomeActivity.class);

                        myintent.putExtras(bundle);

                        overridePendingTransition(0,0);
                        startActivity(myintent);

                        return true;

                    case R.id.nav_settings:
                        Log.i("MENU_DRAWER_TAG","Clicked Profile");
                        drawerLayout.closeDrawer(GravityCompat.START);

                    case R.id.nav_logout:
                        Log.i("MENU_DRAWER_TAG","Clicked Profile");
                        drawerLayout.closeDrawer(GravityCompat.START);

                    case R.id.nav_share:
                        Log.i("MENU_DRAWER_TAG","Clicked Profile");
                        drawerLayout.closeDrawer(GravityCompat.START);

                    case R.id.nav_rate:
                        Log.i("MENU_DRAWER_TAG","Clicked Profile");
                        drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent myintent = null;
                switch (menuItem.getItemId()){
                    case R.id.home:

                        myintent = new Intent(MainPage.this
                                ,HomeActivity.class);

                        bundle.putString("username", username);
                        bundle.putString("password", password);
                        bundle.putBoolean("memberCheckBox",memberCheckBox);

                        overridePendingTransition(0,0);
                        myintent.putExtras(bundle);
                        startActivity(myintent);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext()
                                ,SearchActivity.class));

                        overridePendingTransition(0,0);
                        return true;
                    case R.id.appointment:
                        if(memberCheckBox) {
                            myintent = new Intent(MainPage.this, AppointmentMainActivity.class);
                        }else{
                            myintent = new Intent(MainPage.this, AppointmentDetails.class);
                        }
                        bundle = new Bundle();
                        bundle.putString("key1", "GFG :- Main Activity");

                        Log.d("MainPage", "Main Activity: username and pw is: " + username + " " + password);

                        bundle.putString("username", username);
                        bundle.putString("password", password);
                        bundle.putBoolean("memberCheckBox",memberCheckBox);
                        myintent.putExtras(bundle);

                        overridePendingTransition(0,0);
                        startActivity(myintent);
                        return true;

                    case R.id.petprofile:
                        myintent = new Intent(getApplicationContext(),PetMainActivity.class);
                        bundle = new Bundle();
                        bundle.putString("key1", "GFG :- Main Activity");

                        Log.d("MainPage-petprofile", "Main Activity: username and pw is: " + username + " " + password);

                        bundle.putString("username", username);
                        bundle.putString("password", password);
                        bundle.putBoolean("memberCheckBox",memberCheckBox);
                        myintent.putExtras(bundle);

                        overridePendingTransition(0,0);
                        startActivity(myintent);
                        return true;
                    case R.id.email:
                        startActivity(new Intent(getApplicationContext()
                                , ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


    }
}