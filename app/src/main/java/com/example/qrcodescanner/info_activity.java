package com.example.qrcodescanner;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class info_activity extends AppCompatActivity {

    TextView name;
    TextView email;
    TextView phone;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String UserID;

    //Button Scan History
    Button scanHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        scanHistory = findViewById(R.id.scanHistory);

        scanHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_scan_history();
            }
        });

        //Retrieving info from FireStore
        name = findViewById(R.id.textName);
        email = findViewById(R.id.textEmail);
        phone = findViewById(R.id.textPhone);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        //Getting User's ID to get info from FireStore
        UserID = firebaseAuth.getCurrentUser().getUid();

        //Refering to the collection of FireStore
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(UserID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.getString("Full Name"));
                phone.setText(value.getString("Phone"));
                email.setText(value.getString("Email"));
            }
        });
    }

    //Set onClick in xml without creating a button
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();//Logging Out
        startActivity(new Intent(getApplicationContext() , login_page.class));
        finish();
    }

    //function to open Scan History page
    public void open_scan_history(){
        Intent scan_intent = new Intent(this , Scan_History.class);
        startActivity(scan_intent);
    }
}
