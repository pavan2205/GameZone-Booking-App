package com.example.gamezonebooking;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GamezonesList extends AppCompatActivity {

    RecyclerView recyclerView;
    String id;

    ArrayList<AdminProfileModel> gamezone;

    public GamezonesList() {

    }

    FirebaseFirestore db;


    @Override
    protected void onStart() {
        super.onStart();

        db = FirebaseFirestore.getInstance();
        gamezone=new ArrayList<>();

        id = getIntent().getStringExtra("id");
        Log.d("idlool",id);
        fetchGameZoneDetails(id, getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamezones_list);

        recyclerView = findViewById(R.id.recyclerView10);





    }

    public void fetchGameZoneDetails(String id,Context context) {
//
        //TextView view1;
//        view1 = findViewById(R.id.textview11);
        db.collection("games").document(id).collection("gamezones").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.e("Firestore error",error.getMessage());return;
                }

                for(DocumentChange dc:value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        gamezone.add(dc.getDocument().toObject(AdminProfileModel.class));

                        //Log.d("subcoll", String.valueOf(gamezone.get(0).getName()));
                    }
                }
//                view1.setText(gamezone.get(0).getName().toString());
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                gamezonesAdapter adapter = new gamezonesAdapter(gamezone);
                recyclerView.setAdapter(adapter);

            }
        });

    }
}