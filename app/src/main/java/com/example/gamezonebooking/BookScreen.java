package com.example.gamezonebooking;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class BookScreen extends AppCompatActivity {
    TextView storeName,storeAddress,status;
    ImageView storeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_screen);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String imageUrl = bundle.getString("image");
        String address = bundle.getString("address");
        String openOrClose = bundle.getString("openOrClose");
        storeImage = findViewById(R.id.store_image);
        storeName = findViewById(R.id.store_name);
        storeAddress = findViewById(R.id.store_address);
        status = findViewById(R.id.open_or_close);

        Picasso.get().load(imageUrl).into(storeImage);
        storeName.setText(name);
        storeAddress.setText(address);
        status.setText(openOrClose);

    }
}


