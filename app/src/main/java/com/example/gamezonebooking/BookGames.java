package com.example.gamezonebooking;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


public class BookGames extends AppCompatActivity {

    GridView gridView;
    Dialog dialog;
    TextView backbtn;
    boolean active = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_games);

        gridView = findViewById(R.id.grid_view);
        ScreenAdapter screenAdapter = new ScreenAdapter(this, 10);
        gridView.setAdapter(screenAdapter);
        int color = getResources().getColorStateList(R.color.main2).getDefaultColor();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click event
                View item = parent.getChildAt(position);
                showDialogBox(position);

            }
        });
        backbtn = findViewById(R.id.back_button);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookGames.super.onBackPressed();
                finish();
            }
        });


    }
    int qnt = 0;
    double amt = 60.00000;
    public void showDialogBox(int position){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.book_console);
        dialog.setCancelable(false);
        dialog.show();
        Button ok = dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();
            }
        });

        TextView incribtn,decribtn,amount;
        TextView quantity;
        quantity = dialog.findViewById(R.id.quantity);
        incribtn = dialog.findViewById(R.id.increment_btn);
        decribtn = dialog.findViewById(R.id.decrement_btn);
        amount = dialog.findViewById(R.id.amount);
        amount.setText(String.valueOf(amt));
        amount.append(" Rs");

        qnt = Integer.parseInt(quantity.getText().toString());

        incribtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qnt<4){
                    qnt++;
                    quantity.setText(String.valueOf(qnt));
                    amount.setText(String.valueOf(qnt*amt));
                    amount.append(" Rs");
                }
            }
        });
        decribtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qnt>0){
                    qnt--;
                }else{
                    qnt = 0;
                }
                quantity.setText(String.valueOf(qnt));
                amount.setText(String.valueOf(qnt*amt));
                amount.append(" Rs");
            }
        });

    }

}
