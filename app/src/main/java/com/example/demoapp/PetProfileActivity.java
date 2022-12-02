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
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class PetProfileActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
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
        setContentView(R.layout.activity_pet_profile);

        Bundle bundle = getIntent().getExtras();

        String username = bundle.getString("username", "DefaultUsername");
        String password = bundle.getString("password", "DefaultPassword");
        boolean memberCheckBox = bundle.getBoolean("memberCheckBox", true);
        Log.d("PerProfileActivity", "PerProfileActivity: username and pw is: " + username + " " + password);

        Button pet1 = findViewById(R.id.pet1);
        Button pet2 = findViewById(R.id.pet2);
        Button addPet = findViewById(R.id.addPet);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.menu_Open,R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pet1 = new Intent(PetProfileActivity.this,mikay.class);
                startActivity(pet1);
            }
        });
        pet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pet2 = new Intent(PetProfileActivity.this,doggy.class);

                startActivity(pet2);
            }
        });
        addPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPet = new Intent(PetProfileActivity.this,ActivityPetForm.class);
                Bundle bundle = new Bundle();
                bundle.putString("key1", "GFG :- Main Activity");

                bundle.putString("username", username);
                bundle.putString("password", password);
                bundle.putBoolean("memberCheckBox",memberCheckBox);
                addPet.putExtras(bundle);
                startActivity(addPet);
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_profile:
                        Log.i("MENU_DRAWER_TAG","Clicked Profile");
                        drawerLayout.closeDrawer(GravityCompat.START);

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

        bottomNavigationView.setSelectedItemId(R.id.petprofile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext()
                                ,SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.appointment:
                        startActivity(new Intent(getApplicationContext()
                                ,AppointmentMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.petprofile:
                        startActivity(new Intent(getApplicationContext()
                                ,PetProfileActivity.class));
                        overridePendingTransition(0,0);
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