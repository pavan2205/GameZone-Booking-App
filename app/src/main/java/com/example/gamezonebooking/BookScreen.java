package com.example.gamezonebooking;
import static android.app.PendingIntent.getActivity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class BookScreen extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    List<SlideModel> store_images_carousel = new ArrayList<>();
    private ArrayList<posterUrlsList> posterurls=new ArrayList<>();
    ImageSlider imageSlider;
    FirebaseFirestore db;
    TextView storeName,storeAddress,status;
    ImageView storeImage;
    TextView controllers;
    TextView gamesChoosen;
    CardView setDate;
    CardView setTime;
    CardView continue_btn;
    CardView select_controller;
    CardView select_game;
    TextView selectDate,selectTime;
    Dialog dialog;
    FloatingActionButton nextbtn;
    boolean dateset,timeset,controllerset,gameset;
    ArrayList<String> durations = new ArrayList<>();
    ArrayList<ArrayList<String>> timeSlots = new ArrayList<ArrayList<String>>();

    int Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_screen);
        db=FirebaseFirestore.getInstance();
        imageSlider=findViewById(R.id.store_images_carousel);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String imageUrl = bundle.getString("image");
        String address = bundle.getString("address");
        String openOrClose = bundle.getString("openOrClose");
        storeImage = findViewById(R.id.store_image);
        storeName = findViewById(R.id.store_name);
        storeAddress = findViewById(R.id.store_address);
        status = findViewById(R.id.open_or_close);
        selectDate = findViewById(R.id.select_Date);
        selectTime = findViewById(R.id.select_time);
        setDate = findViewById(R.id.set_dateBtn);
        setTime = findViewById(R.id.set_timeBtn);
        nextbtn = findViewById(R.id.play_game_icon);
        select_controller = findViewById(R.id.select_controller);
        select_game = findViewById(R.id.select_game);
        gamesChoosen = findViewById(R.id.game);
        controllers = findViewById(R.id.controller);

        dateset = false;
        timeset = false;
        controllerset = false;
        gameset = false;
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = selectDate.getText().toString();
                String time = selectTime.getText().toString();
                Intent intent = new Intent(BookScreen.this, BookGames.class);
                Bundle bundle1 = new Bundle();
                if(dateset&&timeset&&controllerset){
                    bundle1.putString("date",date);
                    bundle1.putString("time",time);
                    startActivity(intent);
                }else{
                    Toast.makeText(getBaseContext(),"set the necessary details",Toast.LENGTH_SHORT).show();
                }
            }
        });

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog;

                // Get the current date
                Calendar today = Calendar.getInstance();

                // Create the DatePickerDialog
                datePickerDialog = new DatePickerDialog(BookScreen.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectDate.setText(dayOfMonth+"/"+month+"/"+year);
                        dateset = true;
                    }
                },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH),
                        today.get(Calendar.DAY_OF_MONTH)
                );

                // Set the mindatePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());imum date to today's date

                Calendar maxDate = Calendar.getInstance();
                maxDate.add(Calendar.DAY_OF_MONTH, 10);
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

                // Show the DatePickerDialog
                datePickerDialog.show();

            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            View view;
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });

        select_controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogController();

            }
        });
        select_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogGames();
                gameset = true;
            }
        });

        Picasso.get().load(imageUrl).into(storeImage);
        storeName.setText(name);
        storeAddress.setText(address);
        status.setText(openOrClose);
        getNearByStoreImages();

    }

    String duration1,time1;

    private void showDialogController(){
       Dialog controllerDialog = new Dialog(this);
       controllerDialog.setContentView(R.layout.controller_dialog);
       controllerDialog.show();
       RadioGroup radioGroup = controllerDialog.findViewById(R.id.controller_option);
       Button done  = controllerDialog.findViewById(R.id.done);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                controllerset = true;
                RadioButton radioButton = group.findViewById(checkedId);
                String selectedText = radioButton.getText().toString();
                controllers.setText(selectedText);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerDialog.dismiss();
            }
        });
    }

    private void showDialogGames(){

    }


    private void showDialog() {

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_time_slot);
        dialog.setCancelable(false);
        dialog.show();
        AutoCompleteTextView duration = dialog.findViewById(R.id.duration);
        AutoCompleteTextView timeSlot = dialog.findViewById(R.id.time_slot);

        timeSlots.add(new ArrayList<>(Arrays.asList("9-10","10-11","11-12","12-1","1-2","2-3","3-4","4-5","5-6","6-7")));
        timeSlots.add(new ArrayList<>(Arrays.asList("9-11","11-1","1-3","3-5","5-7","7-9")));
        timeSlots.add(new ArrayList<>(Arrays.asList("9-12","12-3","3-6","6-9")));
        timeSlots.add(new ArrayList<>(Arrays.asList("9-1","1-5","5-9")));

        durations.add("1");
        durations.add("2");
        durations.add("3");
        durations.add("4");
        ArrayAdapter<String> durationadapter = new ArrayAdapter<>(this,R.layout.list_item, durations);
        duration.setAdapter(durationadapter);

        duration.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedDuration = parent.getItemAtPosition(position).toString();
                duration1 = selectedDuration;

                int durationValue = Integer.parseInt(selectedDuration);
                int rowIndex = durationValue - 1;

                ArrayList<String> selectedRow = timeSlots.get(rowIndex);

                ArrayAdapter<String> timeslotAdapter = new ArrayAdapter<>(BookScreen.this, R.layout.list_item, selectedRow);
                timeSlot.setAdapter(timeslotAdapter);
            }
        });
        timeSlot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               time1 = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"item",Toast.LENGTH_SHORT).show();
            }
        });
        continue_btn=dialog.findViewById(R.id.continue_Btn);
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                selectTime.setText("dur:"+duration1+",slot:"+time1);
                timeset = true;
                dialog.dismiss();
            }
        });

    }





    private void getNearByStoreImages(){
        db.collection("gameposters").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.e("firestore error", error.getMessage());
                    return;
                }

                for(DocumentChange dc:value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        posterurls.add(dc.getDocument().toObject(posterUrlsList.class));
                    }
                }


                if(store_images_carousel.size()==0) {
                    for (posterUrlsList urls : posterurls) {
                        store_images_carousel.add(new SlideModel(urls.image));
                        Log.d("urls", urls.image);
                    }
                }
                imageSlider.setImageList(store_images_carousel, true);

            }
        });
    }
    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDateData = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        selectDate.setText(selectedDateData);
    }
}


