package com.example.gamezonebooking;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class gamezonesAdapter extends RecyclerView.Adapter<gamezonesAdapter.ViewHolder> {

     ArrayList<games> gamezonesList;

    public gamezonesAdapter(ArrayList<games> gamezonesList) {
        this.gamezonesList=gamezonesList;
    }

    @NonNull
    @Override
    public gamezonesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.gamezonescard, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull gamezonesAdapter.ViewHolder holder, int position) {
         games list= (games) gamezonesList.get(position);
         holder.gamzoneName.setText(((games) gamezonesList.get(position)).name);

    }

    @Override
    public int getItemCount() {
        return gamezonesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView gamzoneName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gamzoneName=itemView.findViewById(R.id.textView);

        }

    }
}
