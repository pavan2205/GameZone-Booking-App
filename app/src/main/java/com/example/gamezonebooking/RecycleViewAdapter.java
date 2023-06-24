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
    ArrayList<AllStoresDetailsList> storeDetails;
    private Context context;




    public RecycleViewAdapter(ArrayList<AllStoresDetailsList> storeDetails,Context context){
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
        AllStoresDetailsList store = storeDetails.get(position);
        Picasso.get().load(store.image).into(holder.storeImage);
        holder.storeName.setText(store.name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BookScreen.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",store.name);
                bundle.putString("image",store.image);
                bundle.putString("address",store.Address);
                bundle.putString("openOrClose",store.openOrClose?"open":"closed");
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


