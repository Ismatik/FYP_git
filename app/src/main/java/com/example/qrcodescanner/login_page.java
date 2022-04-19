package com.example.qrcodescanner;

//import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.os.PersistableBundle;
//import android.view.View;
//import android.view.View;
import android.widget.Button;
//import android.widget.EditText;

public class login_page extends AppCompatActivity {

//    EditText emailText = (EditText) findViewById(R.id.textEmailAddress);
//    String email = emailText.getText().toString();

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        Button SignIn;
//        SignIn = findViewById(R.id.signinButton);
//        SignIn.setOnClickListener(view -> openSignInPage());
//
//    }
//
//    public void openSignInPage(){
//        Intent i = new Intent(this , signin_page.class);
//        startActivity(i);
//    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Button signin = findViewById(R.id.signinButton);
        signin.setOnClickListener(v -> openSigninPage());

        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(v1 -> openLoginPage());

    }

    public void openSigninPage() {
        Intent i = new Intent(this, signin_page.class);
        startActivity(i);
    }

    public void openLoginPage(){
        Intent i = new Intent(this , profile.class);
        startActivity(i);
    }

}