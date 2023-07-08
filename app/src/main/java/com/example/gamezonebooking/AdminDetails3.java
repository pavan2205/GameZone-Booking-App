package com.example.gamezonebooking;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdminDetails3 extends AppCompatActivity {

    private static final int REQUEST_CODE_SELECT_IMAGES = 1;
    RecyclerView imagesList;
    Button chooseImg;
    ArrayList<Uri>imageUriList = new ArrayList<>();
    ArrayList<String>imageList = new ArrayList<>();
    ArrayList<String>downloadImageUrl=new ArrayList<>();
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    CardView finishBtn;

    ProgressDialog progressDialog;
    String store,address,state,city,pincode,ps4count,ps5count,xboxcount,screencount,poolcount,contact;
    ArrayList<String> gamesList;

    FirebaseFirestore db;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_details3);

        chooseImg = findViewById(R.id.choose_Images);
        finishBtn=findViewById(R.id.finish_btn);
        db=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);


        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        if (data.getClipData() != null) {
                            // Multiple images are selected
                            int count = data.getClipData().getItemCount();
                            for (int i = 0; i < count; i++) {
                                Uri imageUri = data.getClipData().getItemAt(i).getUri();
                                imageUriList.add(imageUri);
                                String filename=getFileName(imageUri);
                                imageList.add(filename);
                            }
                        } else if (data.getData() != null) {
                            // Single image is selected
                            Uri imageUri = data.getData();
                            imageUriList.add(imageUri);
                        }
                        // Now you have the selected image URIs in the imageUriList ArrayList
                        // You can perform further operations with the selected images here
                        imagesList = findViewById(R.id.image_List);
                        imagesList.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                        ImageAdapter adapter = new ImageAdapter(imageUriList);
                        imagesList.setAdapter(adapter);



                        for(int i=0;i<imageUriList.size();i++) {
                            progressDialog.setTitle("uploading");
                            progressDialog.show();
                            String filename = getFileName(imageUriList.get(i));
                            Log.d("filename",filename);
                            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                            storageReference = storageRef.child("images/" + filename);

                            Log.d("imageuri", String.valueOf(imageUriList.get(i)));
                            UploadTask uploadTask = storageReference.putFile(imageUriList.get(i));
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            if(progressDialog.isShowing()){progressDialog.dismiss();};
                                            String url = String.valueOf(uri);
                                            downloadImageUrl.add(url);

                                            Toast.makeText(AdminDetails3.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AdminDetails3.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                    }
                });

        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

                // Start the intent using the ActivityResultLauncher
                imagePickerLauncher.launch(intent);
            }
        });

        imagesList = findViewById(R.id.image_List);
        imagesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ImageAdapter adapter = new ImageAdapter(imageUriList);
        imagesList.setAdapter(adapter);


        Intent i=getIntent();
        SharedPreferences sharedPreferences =getSharedPreferences("Your_app_name", 0);
        store=sharedPreferences.getString("store", "");
        contact=sharedPreferences.getString("contact","");
        address=sharedPreferences.getString("address", "");
        state=sharedPreferences.getString("state", "");
        city=sharedPreferences.getString("city", "");
        pincode=sharedPreferences.getString("pincode", "");
        ps5count=i.getStringExtra("ps5");
        ps4count=i.getStringExtra("ps4");
        xboxcount=i.getStringExtra("xbox");
        screencount=i.getStringExtra("screen");
        poolcount=i.getStringExtra("pool");
        gamesList=(ArrayList<String>) i.getSerializableExtra("gamesList");
        Log.d("gamesList", String.valueOf(gamesList));


        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Creating Profile");
                progressDialog.show();
//                progressDialog.setTitle("Uploading into firebase storage");
//                progressDialog.show();

                    AdminProfileModel adminProfileModel=new AdminProfileModel(store,contact,address,city,state,pincode,screencount,ps5count,ps4count,xboxcount,poolcount,gamesList,downloadImageUrl);
                    db.collection("gamezonesList").add(adminProfileModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            if(progressDialog.isShowing())progressDialog.dismiss();;
                            Log.d("adminprofile","Admin profile added with ID:${documentReference.id}");
                        }
                    });
                }
        });
    }
    @SuppressLint("Range")
    public String getFileName(Uri filepath){
        String result=null;
        if(filepath.getScheme().equals("content")){
            Cursor cursor=getContentResolver().query(filepath,null,null,null,null);
            try{
                if(cursor!=null && cursor.moveToFirst()){
                    result=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }
        if(result==null){
            result=filepath.getPath();int cut=result.lastIndexOf('/');
            if(cut!=-1){
                result=result.substring(cut+1);
            }
        }
        return result;
    }

}