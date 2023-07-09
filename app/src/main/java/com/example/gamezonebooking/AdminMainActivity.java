package com.example.gamezonebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

public class AdminMainActivity extends AppCompatActivity {
    GridView adminGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main_activity);

        adminGrid = findViewById(R.id.admin_grid_view);
        ScreenAdapter screenAdapter = new ScreenAdapter(this, 10);
        adminGrid.setAdapter(screenAdapter);

    }
}