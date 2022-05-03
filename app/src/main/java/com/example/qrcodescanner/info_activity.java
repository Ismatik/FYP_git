package com.example.qrcodescanner;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

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

}
