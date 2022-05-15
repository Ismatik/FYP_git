package com.example.qrcodescanner;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Scanner extends AppCompatActivity {
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
                        Integer number = 1;
                        Toast.makeText(Scanner.this, result.getText(), Toast.LENGTH_SHORT).show();
                        text.setText(result.getText());

                        DocumentReference documentReference = firebaseFirestore.collection("Users").document(UserID);
                        String textField = "Scan #";
                        for(int i = 1; i < 15; i++) {
                            textField = textField + Integer.toString(i);
                            if((documentReference.get(Source.valueOf(textField))).equals("0")){
                                documentReference.update(textField, result.getText());
                            }
                        }
//                        UserID = firebaseAuth.getCurrentUser().getUid();
//                        DocumentReference documentReference = firebaseFirestore.collection("Users").document(UserID);
//                        //https://firebase.google.com/docs/firestore/manage-data/add-data#java_2
//                        Map<String , Object> User = new HashMap<>();
//                        User.put("Scan #1" , result.getText());
//                        documentReference.collection("Users").document(UserID).set(User, SetOptions.merge());

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


}