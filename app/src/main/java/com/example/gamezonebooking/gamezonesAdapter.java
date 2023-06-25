package com.example.gamezonebooking;
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

     ArrayList<gamezones> gamezonesList;

    public gamezonesAdapter(ArrayList<gamezones> gamezonesList) {
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
         gamezones list= gamezonesList.get(position);
         holder.gamzoneName.setText(list.name);
         Picasso.get().load(list.getImage()).into(holder.storeImage);
         holder.location.setText(list.getAddress());
         holder.status.setText(list.getStatus());
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
