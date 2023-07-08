package com.example.gamezonebooking;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminHomeActivity extends AppCompatActivity {

    EditText storename,address,state,city,pincode,contact;
    TextView nextBtn;

    FirebaseFirestore db;
    ArrayList<String> AllGamesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        storename=findViewById(R.id.store_Name);
        address=findViewById(R.id.address);
        state=findViewById(R.id.state);
        city=findViewById(R.id.city);
        pincode=findViewById(R.id.pincode);
        nextBtn=findViewById(R.id.nextbtn);
        contact=findViewById(R.id.contact);
        AllGamesList=new ArrayList<>();
        db=FirebaseFirestore.getInstance();


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String store=storename.getText().toString();
                String contac=contact.getText().toString();
                String addres=address.getText().toString();
                String stat=state.getText().toString();
                String cit=city.getText().toString();
                String pincod=pincode.getText().toString();



                if(TextUtils.isEmpty(store)){

                    Toast.makeText(AdminHomeActivity.this,"Enter Store",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(addres)){
                    Toast.makeText(AdminHomeActivity.this,"Enter address",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(stat)){

                    Toast.makeText(AdminHomeActivity.this,"Enter state",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(cit)){
                    Toast.makeText(AdminHomeActivity.this,"Enter city",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(pincod)){
                    Toast.makeText(AdminHomeActivity.this,"Enter pincode",Toast.LENGTH_SHORT).show();
                    return;
                }





                SharedPreferences sharedPreferences =
                        getSharedPreferences("Your_app_name", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("store", store);
                editor.putString("contact",contac);
                editor.putString("address", addres);
                editor.putString("state", stat);
                editor.putString("city", cit);
                editor.putString("pincode", pincod);
                editor.apply();




                db.collection("games").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            Log.e("Firestore error",error.getMessage());return;
                        }

                        for(DocumentChange dc:value.getDocumentChanges()){
                            if(dc.getType()==DocumentChange.Type.ADDED){
                                Log.d("allgames",dc.getDocument().get("name").toString());
                                AllGamesList.add(dc.getDocument().get("name").toString());
                            }
                        }
                        Intent intent = new Intent(AdminHomeActivity.this, AdminDetails2.class);
                        intent.putExtra("AllGamesList",AllGamesList);
                        startActivity(intent);
                        finish();
                    }
                });
                for(int k=0;k<AllGamesList.size();k++){
                    Log.d("AllGames",AllGamesList.get(k));
                }


//                DocumentReference documentReference = db.collection("gamezonesList").document(store);
//                Map<String, Object> user = new HashMap<>();
//                user.put("storename", store);
//                user.put("address", addres);
//                user.put("state",stat);
//                user.put("city", cit);
//                user.put("pincode", pincod);
//                documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Log.d(TAG, "onSuccess: admin profile is created ");
//                        Intent intent = new Intent(AdminHomeActivity.this, AdminDetails2.class);
//                        intent.putExtra("AllGamesList",AllGamesList);
//                        startActivity(intent);
//                        finish();
//
//                    }
//
//                });
            }


        });


    }

}