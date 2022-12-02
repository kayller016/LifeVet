package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class doggy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doggy);

        ImageButton petdogrecord = findViewById(R.id.petdogrecord);


        petdogrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent petdogrecord = new Intent(doggy.this,petmedicalrecord2.class);
                startActivity(petdogrecord);
            }
        });
    }
}