package com.example.gamezonebooking;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.HorzontalViewHolder>{
    private ArrayList<Uri> imageUriList = new ArrayList<>();

    public ImageAdapter(ArrayList<Uri> imageUriList){
        this.imageUriList = imageUriList;
    }
    @NonNull
    @Override
    public HorzontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout XML file
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        // Create and return a new instance of HorizontalViewHolder
        return new HorzontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorzontalViewHolder holder, int position) {
//        holder.uploadImage.setImageURI(imageUriList.get(position));
        Picasso.get().load(imageUriList.get(position)).into(holder.uploadImage);

    }

    @Override
    public int getItemCount() {
        return imageUriList.size();
    }

    public  class HorzontalViewHolder extends RecyclerView.ViewHolder {
        ImageView uploadImage;

        public HorzontalViewHolder(@NonNull View itemView) {
            super(itemView);
            uploadImage = (ImageView) itemView.findViewById(R.id.upload_image);

        }
    }
}