package com.example.gamezonebooking;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class gamezonesAdapter extends RecyclerView.Adapter<gamezonesAdapter.ViewHolder> {

     ArrayList<AdminProfileModel> gamezonesList;

    public gamezonesAdapter(ArrayList<AdminProfileModel> gamezonesList) {
        this.gamezonesList=gamezonesList;
    }

    @NonNull
    @Override
    public gamezonesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View listItem= layoutInflater.inflate(R.layout.gamezonescard, parent, false);
//        ViewHolder viewHolder = new ViewHolder(listItem);
//        return viewHolder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gamezonescard,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull gamezonesAdapter.ViewHolder holder, int position) {
         AdminProfileModel list= gamezonesList.get(position);
         holder.gamzoneName.setText(list.storename);
         Picasso.get().load(list.images.get(0)).into(holder.storeImage);
         holder.location.setText(list.getAddress());
         holder.status.setText("open");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BookScreen.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",list.storename);
                bundle.putStringArrayList("image",list.images);
                bundle.putStringArrayList("games",list.games);
                bundle.putString("address",list.address);
//                bundle.putString("openOrClose",store.openOrClose?"open":"closed");
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gamezonesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView gamzoneName,location;
        TextView status;
        ImageView storeImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            status=itemView.findViewById(R.id.storestatus);
            storeImage=itemView.findViewById(R.id.storeImage);
            gamzoneName=itemView.findViewById(R.id.storename);
            location=itemView.findViewById(R.id.storeaddress);
        }

    }
}
