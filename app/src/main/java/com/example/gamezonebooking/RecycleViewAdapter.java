package com.example.gamezonebooking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    ArrayList<AdminProfileModel> storeDetails;
    private Context context;




    public RecycleViewAdapter(ArrayList<AdminProfileModel> storeDetails, Context context){
        this.storeDetails = storeDetails;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearest_store_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AdminProfileModel store = storeDetails.get(position);
        Picasso.get().load(store.images.get(0)).into(holder.storeImage);
        holder.storeName.setText(store.storename);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BookScreen.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",store.storename);
                bundle.putStringArrayList("image",store.images);
                bundle.putStringArrayList("games",store.games);
                bundle.putString("address",store.address);
//                bundle.putString("openOrClose",store.openOrClose?"open":"closed");
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeDetails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView storeImage;
        TextView storeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storeImage = itemView.findViewById(R.id.nearest_store_image);
            storeName = itemView.findViewById(R.id.nearest_store_name);
        }
    }

}


