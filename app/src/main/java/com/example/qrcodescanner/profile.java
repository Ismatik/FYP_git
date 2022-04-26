package com.example.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profile extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button info_profile = findViewById(R.id.profileButton);

        info_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_info_profile();
            }
        });
    }

    public void open_info_profile(){
        Intent i = new Intent(this , info_activity.class);
        startActivity(i);
    }
}