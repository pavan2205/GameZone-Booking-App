package com.example.gamezonebooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {
    RecyclerView adminview;
    FirebaseFirestore db;
    String emailAdmin;
    String screenCount;
//    TextView backbtn;

    @Override
    protected void onStart() {
        super.onStart();
        fetchZone();
    }
    ArrayList<bookingsModel>bookedUsers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main_activity);

        adminview = findViewById(R.id.admin_recycler_view);
        db=FirebaseFirestore.getInstance();
//        backbtn = findViewById(R.id.back_button);
        ArrayList<String>screens = new ArrayList<>();
        screens.add("1");
        screens.add("2");
        screens.add("3");
        screens.add("4");

        for (int i=0;i<10;i++){
            bookedUsers.add(new bookingsModel("gamezone",100.0,5,"game","ps5","12-22-22","1hr","1-4",screens,"praneeth.p101@gmail.com"));
        }
        BookedUsersAdapter bookedUsersAdapter = new BookedUsersAdapter(this,bookedUsers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adminview.setLayoutManager(layoutManager);
        adminview.setAdapter(bookedUsersAdapter);

        emailAdmin=getIntent().getStringExtra("adminEmail");
        Log.d("Email",emailAdmin);
//        backbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AdminMainActivity.super.onBackPressed();
//                finish();
//            }
//        });

    }
    public void fetchZone(){
        db.collection("Allgamezones").whereEqualTo("email",emailAdmin).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    screenCount = documentSnapshot.getString("screencount");
                    Log.d("screencount",screenCount);
                }
            }
        });
    }
}

