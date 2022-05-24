package com.example.qrcodescanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Scan_History extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String UserID;


    List<String> scanHistoryList;
    ArrayAdapter<String>  arrayAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_history);

        scanHistoryList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.list_view_layout,scanHistoryList);
        listView = findViewById(R.id.id_list_view);

        listView.setAdapter(arrayAdapter);

        addScanInfo();
    }

    public void addScanInfo(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        UserID = firebaseAuth.getCurrentUser().getUid();

        DocumentReference  database = firebaseFirestore.collection("Users").document(UserID);

       database.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if(task.isSuccessful()){
                   DocumentSnapshot documentSnapshot = task.getResult();
                   Map<String, Object> abc = task.getResult().getData();
                   Log.d("Task Result", String.valueOf(abc));
                   if(documentSnapshot.exists()){
                       //User - friendly show.
                       String history_scan = documentSnapshot.getData().toString();
                       String[] arr_history_scan = history_scan.split("\\{");
                       history_scan = "";
                       String trial = "";
                       Integer counter = 0;
                       Integer counter1;
//                     Dividing by "{"
                       for(String args: arr_history_scan) {
                           //dividing by "}"
                           String [] new_arr = args.split("[}]");
                           for (String arg1:new_arr){
                               counter += 1;
                               Log.d("Argument" , arg1);
                               //dividing final part
                                if(counter >= 1){
                                    if(!arg1.contains("Email") && !arg1.contains("Full Name") && !arg1.contains("Phone")){
//                                        Log.d("History Scan" , history_scan);
                                        if(counter % 2 ==0){
                                            counter1 = counter / 2;
                                            history_scan = history_scan + Integer.toString(counter1) + "." + arg1;
                                            break;
                                        }
                                        history_scan = history_scan + arg1;
                                    }
                                }
                           }
                       }
                       history_scan = history_scan.replace("," , "\n");
                       history_scan = history_scan.replace("=" , "\n");
                       history_scan = history_scan.replace("\"" , "");
                       //Finished sting modification
                       scanHistoryList.add(history_scan);
                       arrayAdapter.notifyDataSetChanged();
                   }
                   else{
                       Log.d("Document" , "No data");
                   }
               }
           }
       });
    }
}
