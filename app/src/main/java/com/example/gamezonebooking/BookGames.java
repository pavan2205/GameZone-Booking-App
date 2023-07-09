package com.example.gamezonebooking;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import static android.content.ContentValues.TAG;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;


public class BookGames extends AppCompatActivity implements PaymentResultListener {

    GridView gridView;
    String userEmail,selectedGame,consoleType,date,time,duration;
    Dialog dialog;
    TextView backbtn;
    double amt;
    boolean active = false;
    double controlleramt= 0.0;
    double totalamount = 0.0;
    CardView checkout_btn;
    TextView checkout_text;
    FirebaseFirestore db;
    String storename;
    String userId;
    FirebaseAuth auth;
    ArrayList<String>screens=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_games);
        String regex = "[^0-9.]";
        Bundle bundle = getIntent().getExtras();


        controlleramt = bundle.getDouble("camt");
         storename=bundle.getString("store");
        selectedGame=bundle.getString("games");
        consoleType=bundle.getString("consoleType");
        date=bundle.getString("date");
        time=bundle.getString("time");
        duration=bundle.getString("duration");
        checkout_btn  = findViewById(R.id.checkout_button);
        checkout_text = findViewById(R.id.checkout_text);
        Log.d("controlleramount", String.valueOf(controlleramt));
        gridView = findViewById(R.id.grid_view);
        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        userId=auth.getCurrentUser().getUid();
        ScreenAdapter screenAdapter = new ScreenAdapter(this, 10);
        gridView.setAdapter(screenAdapter);
        int color = getResources().getColorStateList(R.color.white).getDefaultColor();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click event
                View item = parent.getChildAt(position);
                item.setBackground(getDrawable(R.drawable.grid_card_item));
                showDialogBox(position,item);
            }
        });




Log.d("email",userId);
      db.collection("users").document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
          @Override
          public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
              if(error!=null){
                  Log.e("firestore error", error.getMessage());
                  return;
              }


             userEmail=value.getString("email");

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

        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bookingsModel bm=new bookingsModel(storename,totalamount, (int) ((int)totalamount/controlleramt),selectedGame,consoleType,date,duration,time,screens,userEmail);

                db.collection("bookings").add(bm).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        makePayment();
                    }
                });



            }
        });



    }
    int qnt = 0;
    double currentamt = 0.0;
    public void showDialogBox(int position,View item){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.book_console);
        dialog.setCancelable(false);
        dialog.show();
        screens.add(String.valueOf(position));
        Button ok = dialog.findViewById(R.id.ok);
        int color = getResources().getColorStateList(R.color.main2).getDefaultColor();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setBackgroundTintList(ColorStateList.valueOf(color));
                totalamount += currentamt;
                checkout_text.setText("checkout pay ₹"+totalamount);
                checkout_text.setTextSize(20);
                dialog.dismiss();
            }
        });

        TextView incribtn,decribtn,amount;
        TextView quantity;
        quantity = dialog.findViewById(R.id.quantity);
        incribtn = dialog.findViewById(R.id.increment_btn);
        decribtn = dialog.findViewById(R.id.decrement_btn);
        amount = dialog.findViewById(R.id.amount);
        amount.setText(String.valueOf(controlleramt));
        amount.append(" Rs");

        qnt = Integer.parseInt(quantity.getText().toString());

        incribtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qnt<4){
                    qnt++;
                    quantity.setText(String.valueOf(qnt));
                    currentamt = qnt*controlleramt;
                    amount.setText(String.valueOf(currentamt));
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
                currentamt = qnt*controlleramt;
                amount.setText(String.valueOf(currentamt));
                amount.append(" Rs");
            }
        });



    }
    @SuppressLint("RestrictedApi")
    private void makePayment() {

        Checkout checkout = new Checkout();

        checkout.setKeyID("rzp_test_Yqibtm3RGt8pkC");

        checkout.setImage(R.drawable.logo);
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Pavan G Naik");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", totalamount*100);//pass amount in currency subunits
            options.put("prefill.email", "pavannaik22005@gmail.com");
            options.put("prefill.contact","7349156554");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

    }

    @Override
    public void onPaymentError(int i, String s) {

    }

}
