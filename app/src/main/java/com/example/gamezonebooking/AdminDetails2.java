package com.example.gamezonebooking;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminDetails2 extends AppCompatActivity {

    EditText ps4count,ps5count,screencount,poolcount,xboxcount;
    ArrayList<String>gamesList;
    TextView nextBtn;

    CardView selectGamesCard;
    TextView games;
    boolean [] selectedGames;
    ArrayList<String> selectedGamesList=new ArrayList<>();
     String [] allGamesList;
     ArrayList<String> allGamesArrayList=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_details2);

        selectGamesCard=findViewById(R.id.selectGamesCard);
        games=findViewById(R.id.textView6);
        ps5count=findViewById(R.id.ps5count);
        ps4count=findViewById(R.id.ps4count);
        xboxcount=findViewById(R.id.xboxcount);
        screencount=findViewById(R.id.screencount);
        poolcount=findViewById(R.id.poolcount);
        nextBtn=findViewById(R.id.nextbtn);

        Intent intent = getIntent();
        allGamesArrayList = (ArrayList<String>) intent.getSerializableExtra("AllGamesList");
        allGamesList=allGamesArrayList.toArray(new String[0]);

        selectedGames=new boolean[allGamesList.length];



//        selectedGames =new boolean[allGamesList.length];
        selectGamesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAllGamesDialog();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ps5coun=ps5count.getText().toString();
                String ps4coun=ps4count.getText().toString();
                String xboxcoun=xboxcount.getText().toString();
                String poolcoun=poolcount.getText().toString();
                String screencoun=screencount.getText().toString();




                if(TextUtils.isEmpty(ps5coun)){

                    Toast.makeText(AdminDetails2.this,"Enter ps5count",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(ps4coun)){
                    Toast.makeText(AdminDetails2.this,"Enter ps4count",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(poolcoun)){
                    Toast.makeText(AdminDetails2.this,"Enter xboxcount",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(xboxcoun)){

                    Toast.makeText(AdminDetails2.this,"Enter poolcount",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(screencoun)){
                    Toast.makeText(AdminDetails2.this,"Enter screencount",Toast.LENGTH_SHORT).show();
                    return;
                }



                Intent intent = new Intent(AdminDetails2.this, AdminDetails3.class);
                intent.putExtra("ps5",ps5coun);
                intent.putExtra("ps4",ps4coun);
                intent.putExtra("xbox",xboxcoun);
                intent.putExtra("screen",screencoun);
                intent.putExtra("pool",poolcoun);
                intent.putExtra("gamesList",selectedGamesList);
                startActivity(intent);
                finish();

            }


        });
    }

    private void ShowAllGamesDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(AdminDetails2.this);
        builder.setTitle("Select Games");
        builder.setCancelable(false);
        builder.setMultiChoiceItems(allGamesList, selectedGames, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if(b){
                    selectedGamesList.add(allGamesList[i]);
                }else{
                    selectedGamesList.remove(allGamesList[i]);
                }
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                StringBuilder stringBuilder=new StringBuilder();
                for(int j=0;j<selectedGamesList.size();j++){
                    stringBuilder.append(selectedGamesList.get(j));
                    if(j!=selectedGamesList.size()-1){
                        stringBuilder.append(", ");
                    }
                    games.setText(stringBuilder.toString());
                }


            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();



    }
}