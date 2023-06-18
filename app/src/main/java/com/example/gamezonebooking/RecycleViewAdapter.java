package com.example.gamezonebooking;

import android.annotation.SuppressLint;
import android.content.Context;
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
import java.util.zip.Inflater;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private ArrayList<String> StoreNames= new ArrayList<>();
    private ArrayList<String>StoreImageUrls = new ArrayList<>();
    private Context context;
    public RecycleViewAdapter(ArrayList<String> StoreNames,ArrayList<String>StoreImageUrls,Context context){
        this.StoreNames = StoreNames;
        this.StoreImageUrls = StoreImageUrls;
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
        Picasso.get().load(StoreImageUrls.get(position)).into(holder.storeImage);
        holder.storeName.setText(StoreNames.get(position));
        holder.storeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,StoreNames.get(position),Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return StoreImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView storeImage;
        TextView storeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storeImage = itemView.findViewById(R.id.nearest_store_image);
            storeName = itemView.findViewById(R.id.nearest_store_name);
        }
    }

}
