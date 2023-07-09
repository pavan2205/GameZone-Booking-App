package com.example.gamezonebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminMainActivity extends AppCompatActivity {
    GridView adminGrid;
    FirebaseFirestore db;
    String emailAdmin;
    String screenCount;


    @Override
    protected void onStart() {
        super.onStart();
        fetchZone();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main_activity);

        adminGrid = findViewById(R.id.admin_grid_view);
        db=FirebaseFirestore.getInstance();
        ScreenAdapter screenAdapter = new ScreenAdapter(this, 10);
        adminGrid.setAdapter(screenAdapter);

        emailAdmin=getIntent().getStringExtra("adminEmail");
        Log.d("Email",emailAdmin);


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

