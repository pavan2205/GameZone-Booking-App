package com.example.gamezonebooking;
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

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yalantis.library.Koloda;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Play_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Play_fragment extends Fragment {
    private SwiperAdapter swiperAdapter;
    private List<String> list;
    private ArrayList<games> gamesDetails;
    ShapeableImageView gamesImg;
    Koloda bookbtn;
    Koloda koloda;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    FirebaseFirestore db;

    public Play_fragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment play_game_fragment.
     */
    // TODO: Rename and change types and number of parameters


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

                swiperAdapter = new SwiperAdapter(context,list);
                koloda.setAdapter(swiperAdapter);
                listen l = new listen(context, gamesDetails);
                koloda.setKolodaListener(l);

            }
        });
    }


}