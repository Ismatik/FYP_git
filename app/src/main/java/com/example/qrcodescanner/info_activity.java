package com.example.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class info_activity extends AppCompatActivity {


    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        String name = firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();
        String phone = firebaseUser.getPhoneNumber();

//        boolean emailVerified = firebaseUser.isEmailVerified();
        final TextView textViewToChange = findViewById(R.id.textName);
        textViewToChange.setText(name);
        final TextView textViewToChange2 = findViewById(R.id.textEmail);
        textViewToChange2.setText(email);
        final TextView textViewToChange3 = findViewById(R.id.textPhone);
        textViewToChange3.setText(phone);

        if(firebaseUser != null){
            for(UserInfo profile : firebaseUser.getProviderData()){
                String providerID = profile.getProviderId();

//                String UserID = profile.getUid();

                String name1 = profile.getDisplayName();
                String email1 = profile.getEmail();
                String phone1 = profile.getPhoneNumber();

                textViewToChange.setText(name1);
                textViewToChange2.setText(email1);
                textViewToChange3.setText(phone1);
            }
        }
    }

}
