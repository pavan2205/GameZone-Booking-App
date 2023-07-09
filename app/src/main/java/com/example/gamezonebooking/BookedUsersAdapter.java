package com.example.gamezonebooking;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookedUsersAdapter extends RecyclerView.Adapter<BookedUsersAdapter.ViewHolder>{

    ArrayList<bookingsModel> bookingDetails;
    Context context;
    public BookedUsersAdapter(Context context,ArrayList<bookingsModel>bookingDetails){
        this.context = context;
        this.bookingDetails = bookingDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_user_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        bookingsModel item = bookingDetails.get(position);
        holder.consoleCount.setText(item.getConsoleType()+":"+item.getConsoles());
        holder.ScreenCount.setText(String.valueOf(item.getScreens()));
        holder.duration.setText(item.getDuration());
        holder.time_slot.setText(item.getTimeslot());
        holder.email.setText(item.getUseremail());
        holder.price.setText(String.valueOf(item.getPrice()));
        holder.date.setText(item.getDate());
        holder.gameName.setText(item.getGame());
        holder.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                View view = LayoutInflater.from(context).inflate(R.layout.areyousure,null);
                dialog.setContentView(view);
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookingDetails.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView gameName;
        TextView email;
        TextView consoleCount;
        TextView price;
        TextView date;
        TextView duration;
        TextView time_slot;
        TextView ScreenCount;
        Button doneBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameName = itemView.findViewById(R.id.gameName);
            email = itemView.findViewById(R.id.email_book);
            price = itemView.findViewById(R.id.price);
            time_slot = itemView.findViewById(R.id.time_slot1);
            duration = itemView.findViewById(R.id.duration1);
            date = itemView.findViewById(R.id.booking_date);
            ScreenCount = itemView.findViewById(R.id.screen_count);
            consoleCount = itemView.findViewById(R.id.console_det);
            doneBtn = itemView.findViewById(R.id.doneBtn);
        }
    }
}
