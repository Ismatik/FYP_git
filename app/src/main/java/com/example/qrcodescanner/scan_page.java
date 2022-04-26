package com.example.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

public class scan_page extends AppCompatActivity implements View.OnClickListener {

    Button scanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_page);

        scanBtn = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);
    }

    public void onClick(View v) {

        scanCode();

    }

    private void scanCode(){

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.forSupportFragment(MyFragment.this).initiateScan();
        IntentIntegrator.setCaptureActivity();
    }
}