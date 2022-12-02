package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class mikay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mikay);

        ImageButton record = findViewById(R.id.record);


        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent record = new Intent(mikay.this,petmedicalrecord.class);
                startActivity(record);
            }
        });
    }
}