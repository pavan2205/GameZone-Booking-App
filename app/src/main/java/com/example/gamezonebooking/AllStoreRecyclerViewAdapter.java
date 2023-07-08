package com.example.gamezonebooking;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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

public class AllStoreRecyclerViewAdapter extends RecyclerView.Adapter<AllStoreRecyclerViewAdapter.ViewHolder> {

     ArrayList<AdminProfileModel> storeDetails;
     Context context;
    public AllStoreRecyclerViewAdapter(ArrayList<AdminProfileModel> storeDetails, Context context){
        this.storeDetails = storeDetails;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_stores_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AdminProfileModel store = storeDetails.get(position);
        Picasso.get().load(store.images.get(0)).into(holder.allStoresImage);
        holder.allStoreName.setText(store.storename);
        holder.allStoreAddress.setText(store.address);
//        holder.StoreOpen.setText(store.openOrClose?"open":"closed");
//        if(!store.openOrClose){
//            holder.StoreOpen.setTextColor(Color.RED);
//        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BookScreen.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",store.storename);
                bundle.putString("image",store.images.get(0));
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
        ImageView allStoresImage;
        TextView allStoreName;
        TextView allStoreAddress;
        TextView StoreOpen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            allStoresImage = itemView.findViewById(R.id.all_store_item_image);
            allStoreName = itemView.findViewById(R.id.all_store_item_name);
            allStoreAddress = itemView.findViewById(R.id.store_address);
            StoreOpen = itemView.findViewById(R.id.store_open_or_close);
        }
    }



}
