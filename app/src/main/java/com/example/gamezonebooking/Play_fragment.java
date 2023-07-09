package com.example.gamezonebooking;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yalantis.library.Koloda;

import java.util.ArrayList;
import java.util.List;


public class Play_fragment extends Fragment {
    private SwiperAdapter swiperAdapter;
    private List<String> list;
    Button reload;
    private ArrayList<games> gamesDetails;
    ShapeableImageView gamesImg;
    Koloda bookbtn;
    Koloda koloda;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    FirebaseFirestore db;

    public Play_fragment() {
        // Required empty public constructor
    }
    public static Play_fragment newInstance(String param1, String param2) {
        Play_fragment fragment = new Play_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_play_game_fragment, container, false);
        koloda = view.findViewById(R.id.koloda);
        reload = view.findViewById(R.id.reload);
        gamesImg=view.findViewById(R.id.games_Img);
        bookbtn=view.findViewById(R.id.koloda);
        db=FirebaseFirestore.getInstance();
        list = new ArrayList<String>();
        gamesDetails=new ArrayList<games>();
        fetchGames(this.getContext());
        Log.d("tag", String.valueOf(list.size()));
        bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clicked","clicked");
                Intent intent=new Intent(getContext(),AdminHomeActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                koloda.reloadAdapterData();
            }
        });
        return view;
    }
    private  void fetchGames(Context context){
        db.collection("games").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.e("Firestore error",error.getMessage());return;
                }

                for(DocumentChange dc:value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        gamesDetails.add(dc.getDocument().toObject(games.class));
                        list.add(dc.getDocument().toObject(games.class).img);
                    }
                }
                swiperAdapter = new SwiperAdapter(context,gamesDetails);
                koloda.setAdapter(swiperAdapter);
                listen l = new listen(context, gamesDetails);
                koloda.setKolodaListener(l);

            }
        });
    }
}