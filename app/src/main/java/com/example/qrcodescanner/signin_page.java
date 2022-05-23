package com.example.qrcodescanner;

//import androidx.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class signin_page extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText bFullName , bEmail , bPassword , bPhone;
    Button bRegisterBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore firebaseFirestore;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_page);

        bFullName = findViewById(R.id.fullName);
        bEmail = findViewById(R.id.Email);
        bPassword = findViewById(R.id.password);
        bPhone = findViewById(R.id.phone);
        bRegisterBtn = findViewById(R.id.registerButton);


        fAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        //if logged in - we need to check
//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext() , profile.class));
//            finish();
//        }


        bRegisterBtn.setOnClickListener(view -> {
            String email = bEmail.getText().toString().trim();
            String password = bPassword.getText().toString().trim();
            String name = bFullName.getText().toString().trim();
            String phone = bPhone.getText().toString();

            if(TextUtils.isEmpty(email)){
                bEmail.setError("Email is Required");
                return;
            }

            if (TextUtils.isEmpty(name)){
                bFullName.setError("Correct name is required");
                return;
            }
            if(TextUtils.isEmpty(password)){
                bPassword.setError("Password is Required");
                return;
            }

            if(password.length() < 6){
                bPassword.setError("Password should be more than 5 characters.");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            // register the user in Firebase

            fAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //display if created, otherwise it wont show
                    if(task.isSuccessful()){
                        Toast.makeText(signin_page.this, "User Created.", Toast.LENGTH_SHORT).show();
                        //getting ID to add in collection
                        UserID = fAuth.getCurrentUser().getUid();
                        //adding to Firestore
                        DocumentReference documentReference = firebaseFirestore.collection("Users").document(UserID);
                        Map<String,Object> User = new HashMap<>();
                        User.put("Full Name" , name);
                        User.put("Email" , email);
                        User.put("Phone" , phone);
                        //Fields for scan
//                        String empty = "0";
//                        for(int i = 1; i < 10; i++){
//                            String textField = "Scan #";
//                            textField = textField + Integer.toString(i);
//                            User.put(textField , empty);
//                        }
                        //Inserting to FireStore
                        documentReference.set(User).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG , "onSuccess: User Profile is created for " + UserID);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG , "onFailure: " + e.toString());
                            }
                        });
                        startActivity(new Intent(getApplicationContext() , profile.class));
                    }
                    else {
                        Toast.makeText(signin_page.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        });
    }

}