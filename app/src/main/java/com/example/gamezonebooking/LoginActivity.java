package com.example.gamezonebooking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.gamezonebooking.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    @NonNull ActivityLoginBinding binding;

    EditText email,password;
    Button registerBtn;
    ImageView googleBtn;
    ConstraintLayout register,admin;

    androidx.appcompat.widget.SwitchCompat switchuser;

    //google authentication
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    String userType;
    String userId;




    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        Log.d("currentUser", String.valueOf(currentUser));
        if(currentUser!=null){
//            String userId=mAuth.getCurrentUser().getUid();
//            DocumentReference documentReference=db.collection("users").document(userId);
//            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                    String userType=value.getString("userType");
//                    Log.d("userType",userType);
//                    if(userType.equals("admin")){
//                        Intent intent=new Intent(LoginActivity.this,AdminHomeActivity.class);
//                        startActivity(intent);
//                    }
//                    else if(userType.equals("user")){
//                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//            });
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation fade_in=AnimationUtils.loadAnimation(this,R.anim.fade_in);
        Animation bottom_down=AnimationUtils.loadAnimation(this,R.anim.bottom_down);

        email=findViewById(R.id.email1);
        password=findViewById(R.id.password1);
        registerBtn=findViewById(R.id.loginbtn);
        register=findViewById(R.id.registerText);
        admin=findViewById(R.id.adminText);
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
                binding.registerText.setAnimation(fade_in);
                binding.adminText.setAnimation(fade_in);
                binding.cardView.setAnimation(fade_in);
            }
        };

        handler.postDelayed(runnable,1000);


        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);


        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });


    admin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i=new Intent(LoginActivity.this,Adminlogin.class);
            startActivity(i);
            finish();
        }
    });

        if (mAuth.getCurrentUser() != null) {
            try {
                String userId=mAuth.getCurrentUser().getUid();
//                DocumentReference documentReference=db.collection("users").document(userId);
//                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                        String userType = value.getString("userType");
//
//                        if (userType.equals("admin")) {
//                            Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
//                            startActivity(intent);
//                        } else if (userType.equals("user")) {
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    }
//                });
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
            }catch(Exception e){
                e.printStackTrace();
            }
            // or do some other stuff that you want to do
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emails,passwords;
                emails=String.valueOf(email.getText());
                passwords=String.valueOf(password.getText());







                if(TextUtils.isEmpty(emails)){
                    Toast.makeText(LoginActivity.this,"Enter email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(passwords)){
                    Toast.makeText(LoginActivity.this,"Enter password",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(emails, passwords)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                        try {
                                            userId = mAuth.getCurrentUser().getUid();
//                                            DocumentReference documentReference = db.collection("users").document(userId);
//                                            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                                                @Override
//                                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                                                    userType = value.getString("userType");
//                                                    if (userType.equals("admin")) {
//                                                        Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
//                                                        startActivity(intent);
//                                                        finish();
//                                                    } else if (userType.equals("user")) {
//                                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                                        startActivity(intent);
//                                                        finish();
//                                                    }
//                                                }
//                                            });
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }







                                    Toast.makeText(LoginActivity.this, "Login Successful.",
                                            Toast.LENGTH_SHORT).show();


                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }

    private void SignIn() {
        Intent intent=gsc.getSignInIntent();
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Task<GoogleSignInAccount>task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                HomeActivity();
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void HomeActivity() {
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }
}