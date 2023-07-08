package com.example.gamezonebooking;
import static android.app.PendingIntent.getActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class BookScreen extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    List<SlideModel> store_images_carousel = new ArrayList<>();
    private ArrayList<posterUrlsList> posterurls=new ArrayList<>();
    ImageSlider imageSlider;
    FirebaseFirestore db;
    TextView storeName,storeAddress,status;
    ImageView storeImage;
    CardView setDate;
    CardView setTime;
    CardView continue_btn;
    TextView selectDate,selectTime;
    Dialog dialog;
    FloatingActionButton nextbtn;
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

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = selectDate.getText().toString();
                String time = selectTime.getText().toString();
                Intent intent = new Intent(BookScreen.this, BookGames.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("date",date);
                bundle1.putString("time",time);
                startActivity(intent);
            }
        });

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker mDatePickerDialogFragment;
                mDatePickerDialogFragment = new DatePicker();
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            View view;
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        Picasso.get().load(imageUrl).into(storeImage);
        storeName.setText(name);
        storeAddress.setText(address);
        status.setText(openOrClose);
        getNearByStoreImages();

    }
    String duration1,time1;
    private void showDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_time_slot);
        dialog.setCancelable(false);
        dialog.show();
        AutoCompleteTextView duration = dialog.findViewById(R.id.duration);
        AutoCompleteTextView timeSlot = dialog.findViewById(R.id.time_slot);
        String[] durations = {"1","2","3","4","5","6","7","8","9","10"};
        String[] timeslots = {"9-10","10-11","11-12","12-1","1-2","2-3","3-4","4-5","5-6","6-7"};
        ArrayAdapter<String> durationadapter = new ArrayAdapter<>(this,R.layout.list_item, durations);
        duration.setAdapter(durationadapter);
        ArrayAdapter<String> timeslotadapter = new ArrayAdapter<>(this,R.layout.list_item, timeslots);
        timeSlot.setAdapter(timeslotadapter);

        duration.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                duration1 = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"item",Toast.LENGTH_SHORT).show();
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


