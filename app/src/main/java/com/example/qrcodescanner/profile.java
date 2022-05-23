package com.example.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity {

    String[] permissions = {
            Manifest.permission.CAMERA
    };

    int PERM_CODE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button info_profile = findViewById(R.id.profileButton);

        Button scanbutton = findViewById(R.id.scanButton);

        info_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_info_profile();
            }
        });


        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_scan_activity();
            }
        });

    }

    private boolean checkpermissions(){
        List<String> listofpermissions = new ArrayList<>();
        for (String permission: permissions){
            if(ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED){
                listofpermissions.add(permission);
            }
        }
        if(!listofpermissions.isEmpty()){
            ActivityCompat.requestPermissions(this, listofpermissions.toArray(new String[listofpermissions.size()]), 11);
            return false;
        }
        return true;
    }

    public void open_info_profile(){
        Intent i = new Intent(this , info_activity.class);
        startActivity(i);
    }

    public void open_scan_activity(){
        Intent i = new Intent(this , Scanner.class);
        startActivity(i);
    }

}