package com.example.gamezonebooking;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SwiperAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    ShapeableImageView sp;
    Button bookgame;



    public SwiperAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View Convertview, ViewGroup parent) {
        View view;
        Log.d("swiperTag",list.get(0));
        if(Convertview == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_koloda,parent,false);
            sp=view.findViewById(R.id.games_Img);
            bookgame=view.findViewById(R.id.book_game);

            for(int j=0;j<list.size();j++){
                Log.d("swiperImg",list.get(i));
                Picasso.get().load(list.get(i)).into(sp);
            }






        }else {
            view = Convertview;
        }
        return view;
    }

}