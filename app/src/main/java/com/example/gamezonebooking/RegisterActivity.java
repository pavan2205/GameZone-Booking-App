package com.example.gamezonebooking;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText email,password,username,confirmPassword;
    Button registerBtn;
    ConstraintLayout loginbutton;
    FirebaseFirestore db;
    String userId;
    androidx.appcompat.widget.SwitchCompat userSwitch;


    FirebaseAuth mAuth;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser!=null){
            String userId=mAuth.getCurrentUser().getUid();
            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
//            DocumentReference documentReference=db.collection("users").document(userId);
//            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                    String userType=value.getString("userType");
//                    Log.d("userType",userType);
//                    if(userType.equals("admin")){
//                        Intent intent=new Intent(RegisterActivity.this,AdminHomeActivity.class);
//                        startActivity(intent);
//                    }
//                    else if(userType.equals("user")){
//                        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        registerBtn=findViewById(R.id.registerBtn);
        loginbutton=findViewById(R.id.login);
        username=findViewById(R.id.username);
        confirmPassword=findViewById(R.id.confirmpassword);
        userSwitch=findViewById(R.id.switchuser);

        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });






        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emails, passwords, usernames, userType;
                emails = String.valueOf(email.getText());
                passwords = String.valueOf(password.getText());
                usernames = String.valueOf(username.getText());
                if (userSwitch.isChecked()) {
                    userType = "admin";
                } else {
                    userType = "user";
                }


                Log.d("user", String.valueOf(userSwitch.isChecked()));

                if (TextUtils.isEmpty(emails)) {
                    Toast.makeText(RegisterActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwords)) {
                    Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (userType.equals("admin")) {


                    DocumentReference documentReference = db.collection("admins").document(emails);
                    Map<String, Object> user = new HashMap<>();
                    user.put("username", usernames);
                    user.put("email", emails);
                    user.put("password",passwords);
                    user.put("userType", userType);
                    documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "onSuccess: admin profile is created ");
                            Intent intent = new Intent(RegisterActivity.this, Adminlogin.class);
                            startActivity(intent);
                            finish();

                        }

                    });

                }else{


                mAuth.createUserWithEmailAndPassword(emails, passwords)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(RegisterActivity.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();

                                    userId = mAuth.getCurrentUser().getUid();


                                    DocumentReference documentReference = db.collection("users").document(userId);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("username", usernames);
                                    user.put("email", emails);
                                    user.put("userType", userType);
                                    documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.d(TAG, "onSuccess: user profile is created for " + userId);
                                        }
                                    });


                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
            }
        });

    }
}