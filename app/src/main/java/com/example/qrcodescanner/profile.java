package com.example.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profile extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button scanner;

        scanner = findViewById(R.id.scanButton);

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sta
            }
        });
    }
}