package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button guest = findViewById(R.id.buttonGuest);

        guest.setOnClickListener(v -> openLoginPage());
    }

    public void openLoginPage(){
        Intent intent = new Intent(this , Login_Page.class);
        startActivity(intent);
    }




}