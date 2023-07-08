package com.example.gamezonebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gamezonebooking.databinding.ActivityAdminloginBinding;
import com.example.gamezonebooking.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class Adminlogin extends AppCompatActivity {

    @NonNull
    ActivityAdminloginBinding binding;

    EditText email,password;
    Button registerBtn;
    ImageView googleBtn;
    ConstraintLayout register,login;




    FirebaseAuth mAuth;
    FirebaseFirestore db;

    String userType;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityAdminloginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation fade_in= AnimationUtils.loadAnimation(this,R.anim.fade_in);
        Animation bottom_down=AnimationUtils.loadAnimation(this,R.anim.bottom_down);


        email=findViewById(R.id.email1);
        password=findViewById(R.id.password1);
        registerBtn=findViewById(R.id.loginbtn);
        register=findViewById(R.id.registerText);
        login=findViewById(R.id.loginText);
        googleBtn=findViewById(R.id.googlebtn);


        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        binding.linearLayout3.setAnimation(bottom_down);
        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                binding.cardView.setAnimation(fade_in);
                binding.cardView2.setAnimation(fade_in);
                binding.cardView3.setAnimation(fade_in);
                binding.text.setAnimation(fade_in);
                binding.loginText.setAnimation(fade_in);
                binding.registerText.setAnimation(fade_in);
                binding.cardView.setAnimation(fade_in);
            }
        };

        handler.postDelayed(runnable,1000);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Adminlogin.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Adminlogin.this,RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String emails,passwords;
                emails=String.valueOf(email.getText());
                passwords=String.valueOf(password.getText());
                Log.d("clicked", String.valueOf(login));



                if(TextUtils.isEmpty(emails)){

                    Toast.makeText(Adminlogin.this,"Enter email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(passwords)){
                    Toast.makeText(Adminlogin.this,"Enter password",Toast.LENGTH_SHORT).show();
                    return;
                }



                CollectionReference cref=db.collection("admins");
                Query q1=cref.whereEqualTo("email",emails).whereEqualTo("password",passwords);
                Log.d("query", String.valueOf(q1));
                q1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                                   Intent i=new Intent(Adminlogin.this,AdminHomeActivity.class);
                                   startActivity(i);
                                   finish();
                                }



                });
            }
        });
    }
}