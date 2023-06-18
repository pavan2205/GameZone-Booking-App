package com.example.gamezonebooking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.yalantis.library.Koloda;
import com.yalantis.library.KolodaListener;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Play_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class listen implements KolodaListener {
    private Context context;
    private ArrayList<games> gamesDetails;

    public listen(Context context, ArrayList<games> gamesDetails) {
        this.context = context;
        this.gamesDetails = gamesDetails;
    }

    @Override
    public void onCardDoubleTap(int i) {

    }

    @Override
    public void onCardDrag(int i, @NonNull View view, float v) {

    }

    @Override
    public void onCardLongPress(int i) {

    }

    @Override
    public void onCardSingleTap(int i) {
        try{
            Log.d("position",String.valueOf(i));
            Intent intent =  new Intent(context,GamezonesList.class);
//            Bundle args = new Bundle();
//            args.putSerializable("ARRAYLIST",(Serializable)gamesDetails);
//            intent.putExtras(args);
            context.startActivity(intent);

        }catch (NullPointerException e){
            Log.d("null pointer", String.valueOf(e));
        }
    }

    @Override
    public void onCardSwipedLeft(int i) {

    }

    @Override
    public void onCardSwipedRight(int i) {

    }

    @Override
    public void onClickLeft(int i) {

    }

    @Override
    public void onClickRight(int i) {

    }

    @Override
    public void onEmptyDeck() {

    }

    @Override
    public void onNewTopCard(int i) {

    }
}
