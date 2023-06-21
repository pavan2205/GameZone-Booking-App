package com.example.gamezonebooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class GamezonesList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<games>gamezonesList;


    public GamezonesList(){}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamezones_list);

        recyclerView=findViewById(R.id.recyclerView);

       // gamezonesList=getIntent().getParcelableArrayListExtra("values");

         Log.d("valuess", String.valueOf(gamezonesList.size()));
//        for(int i=0;i<gamezonesListing.size();i++){
//            gamezonesList.get(i).name=gamezonesListing.get(i);
//        }


        gamezonesAdapter adapter=new gamezonesAdapter(gamezonesList);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}