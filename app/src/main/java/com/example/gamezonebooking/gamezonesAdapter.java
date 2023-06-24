package com.example.gamezonebooking;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
         holder.location.setText(list.getLocation());
    }

    @Override
    public int getItemCount() {
        return gamezonesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView gamzoneName,location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gamzoneName=itemView.findViewById(R.id.name);
            location=itemView.findViewById(R.id.location);
        }

    }
}
