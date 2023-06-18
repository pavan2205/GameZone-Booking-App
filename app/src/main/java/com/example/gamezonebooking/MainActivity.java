
package com.example.gamezonebooking;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView current_user;
    Button getCurrentUser;

    GoogleSignInClient gsc;
    GoogleSignInOptions gso;

    FirebaseUser user;
    FirebaseAuth auth;

    BottomNavigationView bottomNavigationView;
    BottomAppBar bottomAppBar;
    FloatingActionButton floatingActionButton;

    Home_fragment home_fragment = new Home_fragment();
    Play_fragment play_fragment = new Play_fragment();
    Profile_fragment profile_fragment = new Profile_fragment();
    private boolean playing = false;

    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        current_user=findViewById(R.id.text1);
//        getCurrentUser=findViewById(R.id.getuserbtn);
//        auth=FirebaseAuth.getInstance();
//        user=auth.getCurrentUser();


        bottomAppBar = findViewById(R.id.bottom_app_bar);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setBackground(null);

        Animation openAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_open_anim);
        Animation closeAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_close_anim);
        MenuItem homeItem,profileItem,helper,helper2;
        homeItem =bottomNavigationView.getMenu().findItem(R.id.home_icon);
        profileItem =bottomNavigationView.getMenu().findItem(R.id.profile_icon);
        helper2 = bottomNavigationView.getMenu().findItem(R.id.games_icon);
        helper = bottomNavigationView.getMenu().findItem(R.id.profile_icon4);
        floatingActionButton = findViewById(R.id.play_game_icon);
        ColorStateList colorStateList = getResources().getColorStateList(R.color.main2);
        bottomNavigationView.setItemActiveIndicatorColor(colorStateList);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_view,home_fragment).commit();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                System.out.println("play games");
                getSupportFragmentManager().beginTransaction().replace(R.id.main_view,play_fragment).commit();
                bottomNavigationView.setItemActiveIndicatorColor(null);
                floatingActionButton.setForeground(getDrawable(R.drawable.floating_action_btn));
                view.startAnimation(openAnimation);
                playing = true;

            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item == homeItem){
                    System.out.println("home");
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_view,home_fragment).commit();
                    bottomNavigationView.setItemActiveIndicatorColor(colorStateList);
                    floatingActionButton.setForeground(getDrawable(R.drawable.game_pad));
                    if(playing){
                        floatingActionButton.startAnimation(closeAnimation);
                        playing = false;
                    }
                    return true;
                } else if (item == profileItem) {
                    System.out.println("profile");
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_view,profile_fragment).commit();
                    bottomNavigationView.setItemActiveIndicatorColor(colorStateList);
                    floatingActionButton.setForeground(getDrawable(R.drawable.game_pad));
                    if(playing){
                        floatingActionButton.startAnimation(openAnimation);
                        playing = false;
                    }
                    return true;
                }
                return false;
            }
        });






//        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        gsc= GoogleSignIn.getClient(this,gso);
//
//        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
//        getLocation();
//
//        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
//        if(account!=null){
//            String Name=account.getDisplayName();
//            String Mail=account.getEmail();
//
//            current_user.setText(Name);
//        }
//
//        if(user==null){
//            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        else{current_user.setText(user.getEmail());
//        }
//
//        getCurrentUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
//                startActivity(intent);
//
//            }
//        });
    }

    private void getLocation() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null){
                        Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
                        List<Address> addresses= null;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d("cityname",addresses.get(0).getAddressLine(0));
                    }
                }
            });
        }
        else{
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getLocation();
            }
            else{
                Toast.makeText(MainActivity.this,"Please provide the required permission ",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    private void SignOut(){
//        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                finish();
//                startActivity(new Intent(MainActivity.this,LoginActivity.class));
//            }
//        });
//    }
}