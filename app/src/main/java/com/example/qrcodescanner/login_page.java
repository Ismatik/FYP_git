package com.example.qrcodescanner;

//import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.os.PersistableBundle;
//import android.view.View;
//import android.view.View;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import android.widget.EditText;

public class login_page extends AppCompatActivity {

    EditText email,password;
    Button loginButton;
    FirebaseAuth fAuth;

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

        email = findViewById(R.id.textEmailAddress);
        password = findViewById(R.id.textPassword);
        fAuth  = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString().trim();
                String pas = password.getText().toString().trim();
                if(TextUtils.isEmpty(em)){
                    email.setError("Please enter your email address!");
                }

                if(TextUtils.isEmpty(pas)){
                    password.setError("Password is required!");
                }

                if(pas.length() < 6){
                    password.setError("Password can not be less than 6 characters");
                }


                fAuth.signInWithEmailAndPassword(em,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(login_page.this, "Logged in Successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext() , profile.class));
                        }
                        else{
                            Toast.makeText(login_page.this , "Error occured!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    public void openSigninPage() {
        Intent i = new Intent(this,signin_page.class);
        startActivity(i);
    }



}