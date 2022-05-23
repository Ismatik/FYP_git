package com.example.qrcodescanner;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.apphosting.datastore.testing.DatastoreTestTrace;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.auth.User;
import com.google.zxing.Result;
//Date time
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner extends AppCompatActivity {

//    String[] permissions = {
//            Manifest.permission.CAMERA
//    };

//    int PERM_CODE = 111;


    private CodeScanner mCodeScanner;
    private TextView text;

    private String UserID;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Date date_and_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scanner2);
        //Saving results that have been scanned in database
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        //date and time
        date_and_time = Calendar.getInstance().getTime();

        //QR Code Scanning
        text = findViewById(R.id.textViewscan);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast toast = Toast.makeText(Scanner.this, result.getText(), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER , 0 , -10);
                        toast.show();
                        text.setText(result.getText());

                        UserID = firebaseAuth.getCurrentUser().getUid();

                        DocumentReference database = firebaseFirestore.collection("Users").document(UserID);
                        String textField = "Scan " + date_and_time;
                        Map<String, Object> data = new HashMap<>();
                        data.put(textField, result.getText());
                        open_result_page(result.getText());
//                        database.collection("Users").document(UserID).set(textField , SetOptions.merge());

                        database.update(textField , result.getText()).
                                addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG" , "Added");
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG" , "Error ", e);
                                    }
                                });
                    }
                });
            }
        });



        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });


    }

    private void open_result_page(String result) {
        Intent intent = new Intent(getApplicationContext() , result_of_scan.class);
        intent.putExtra("Info took" , result);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestCamera();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void requestCamera(){
        mCodeScanner.startPreview();
    }














//    private boolean checkpermissions(){
//        List<String> listofpermissions = new ArrayList<>();
//        for (String permission: permissions){
//            if(ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED){
//                listofpermissions.add(permission);
//            }
//        }
//        if(!listofpermissions.isEmpty()){
//            ActivityCompat.requestPermissions(this, listofpermissions.toArray(new String[listofpermissions.size()]), 11);
//            return false;
//        }
//        return true;
//    }

//                        DocumentReference documentReference = firebaseFirestore.collection("Users").document(UserID);
//                        String textField = "Scan " + date_and_time;
//                        Map<String, Object> data = new HashMap<>();
//                        data.put(textField, result.getText());
//
//                        documentReference.collection("Users").document(UserID).set(textField , SetOptions.merge());
//                        DocumentReference documentReference = firebaseFirestore.collection("Users").document(UserID);
//                        String textField = "Scan #";
//                        for(int i = 1; i < 15; i++) {
//                            textField = textField + Integer.toString(i);
//                            if((documentReference.get(Source.valueOf(textField))).equals("0")){
//                                documentReference.update(textField, result.getText());
//                            }
//                        }

}
