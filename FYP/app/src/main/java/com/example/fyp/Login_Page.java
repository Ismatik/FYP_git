package com.example.fyp;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Page extends AppCompatActivity {

    private EditText usernameLoginPage, passwordLoginPage;
    Button loginButton;
    FirebaseAuth fAuth;
    ProgressBar progressBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        TextView register = findViewById(R.id.register);
        usernameLoginPage=findViewById(R.id.emailaddress);
        passwordLoginPage=findViewById(R.id.password);
        loginButton=findViewById(R.id.loginGuestButton);
        progressBarLogin=findViewById(R.id.progressBar2);

        fAuth=FirebaseAuth.getInstance();

        loginButton.setOnClickListener(view -> {
            String userNameLogin=usernameLoginPage.getText().toString().trim();
            String passwordLogin=passwordLoginPage.getText().toString().trim();

            if(TextUtils.isEmpty(userNameLogin)){
                usernameLoginPage.setError("User Name is required");
                return;
            }
            if(TextUtils.isEmpty(passwordLogin)){
                passwordLoginPage.setError("User Name is required");
                return;
            }
            progressBarLogin.setVisibility(View.VISIBLE);
            //authenticate user
            fAuth.signInWithEmailAndPassword(userNameLogin,passwordLogin).addOnCompleteListener(Login_Page.this, task -> {
                progressBarLogin.setVisibility(View.GONE);
                if(!task.isSuccessful()){
                    //there was an error
                    if(passwordLogin.length()<6){
                        passwordLoginPage.setError("Error");
                    }else {
                        Toast.makeText(Login_Page.this,"Error",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Intent intent = new Intent(Login_Page.this,Profile_Page.class);
                    startActivity(intent);
                    finish();
                }

            });




        });
        register.setOnClickListener(view -> startActivity(new Intent (getApplicationContext(), Registration.class)));
    }
}