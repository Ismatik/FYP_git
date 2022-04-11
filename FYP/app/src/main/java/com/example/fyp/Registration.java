package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private EditText username, fullname, department, password;
    private ProgressBar progressBar;
    public FirebaseAuth fAuth;
    public FirebaseFirestore fStore;
    public String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Button registerButton = findViewById(R.id.registerGuestButton);
        username=findViewById(R.id.usernameRegisterPage);
        fullname=findViewById(R.id.fullnameRegisterPage);
        department=findViewById(R.id.DepartmentRegisterPage);
        password=findViewById(R.id.passwordRegisterPage);
        progressBar=findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),Login_Page.class));
            finish();
        }

        registerButton.setOnClickListener(view -> {
            String userNameRegister=username.getText().toString().trim();
            String fullNameRegister=fullname.getText().toString().trim();
            String passwordRegister=password.getText().toString().trim();
            String departmentRegister=department.getText().toString().trim();

            if(TextUtils.isEmpty(userNameRegister)){
                username.setError("User Name is required");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            fAuth.createUserWithEmailAndPassword(userNameRegister, passwordRegister).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Registration.this, "User is created", Toast.LENGTH_SHORT).show();
                        userID=fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference=fStore.collection("users").document(userID);
                        Map<String, Object> user=new HashMap<>();
                        user.put("fullname",fullNameRegister);
                        user.put("username",userNameRegister);
                        user.put("department", departmentRegister);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG","on Success: user profile is created for "+userID );
                            }
                        });
                        startActivity(new Intent(getApplicationContext(),Profile_Page.class));
                    }else{
                        Toast.makeText(Registration.this, "Error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);

                    }
                }
            });


        });
    }
}