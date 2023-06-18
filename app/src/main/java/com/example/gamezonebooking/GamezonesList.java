package com.example.gamezonebooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class GamezonesList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<gamezones> gamezonesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamezones_list);

        recyclerView=findViewById(R.id.recyclerView);


//        Bundle intent = getIntent().getExtras();
//        gamezonesList = (ArrayList<gamezones>) intent.getSerializable("ARRAYLIST");
//
//        Log.d("arrayList",gamezonesList.get(0).name);
//
//        gamezonesAdapter adapter=new gamezonesAdapter(gamezonesList);
//        recyclerView.setHasFixedSize(true);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);

    }
}