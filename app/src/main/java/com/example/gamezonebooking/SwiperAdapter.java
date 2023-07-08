package com.example.gamezonebooking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SwiperAdapter extends BaseAdapter {
    private Context context;
    private List<games> list;
    ShapeableImageView sp;
    LinearLayout fadein_desc;
    Button bookgame;



    public SwiperAdapter(Context context, List<games> list) {
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

    @SuppressLint("Range")
    @Override
    public View getView(int i, View Convertview, ViewGroup parent) {
        View view;
        Log.d("swiperTag",list.get(0).img);
        if(Convertview == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_koloda,parent,false);
            sp=view.findViewById(R.id.games_Img);
            TextView gameName = view.findViewById(R.id.game_name);

            for(int j=0;j<list.size();j++){
                Log.d("swiperImg",list.get(i).img);
                Picasso.get().load(list.get(i).img).error(R.drawable.homepage_top_background).into(sp);
            }
            LinearLayout fadeInLayout = view.findViewById(R.id.fadeIn_desc);
            fadeInLayout.setAlpha(0f);
            fadeInLayout.setVisibility(View.VISIBLE);
            fadeInLayout.animate()
                    .translationY(0)
                    .alpha(0.5f)
                    .setDuration(1000)  // Set the desired duration for the animation (in milliseconds)
                    .setStartDelay(700) // Set a delay before the animation starts (in milliseconds)
                    .start();

            gameName.setText(list.get(i).name);


        }else {
            view = Convertview;
        }
        return view;
    }

}