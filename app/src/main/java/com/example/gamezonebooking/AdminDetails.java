package com.example.gamezonebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class AdminDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_details);
//
//        SharedPreferences preferences=getSharedPreferences("PREFERENCE",MODE_PRIVATE);
//
//        String FirstTime=preferences.getString("FirstTimeInstall","");
//        if(FirstTime.equals("Yes")){
//            Intent intent=new Intent(AdminDetails.this,AdminHomeActivity.class);
//            startActivity(intent);
//        }else {
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putString("FirstTimeInstall", "yes");
//            editor.apply();
//        }
    }
}