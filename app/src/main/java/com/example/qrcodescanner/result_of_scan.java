package com.example.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class result_of_scan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_of_scan);
        TextView textView_scan_result = findViewById(R.id.result_scan);
        Intent i = getIntent();
        String result_of_scan = i.getStringExtra("Info took");

        textView_scan_result.setText(result_of_scan);
//        Bundle extras = getIntent().getExtras();
//        if(extras != null){
//            String result_of_scan = extras.getString("Info got");
//            if(result_of_scan != null){
//                textView_scan_result.setText(result_of_scan);
//            }
//        }
    }
}