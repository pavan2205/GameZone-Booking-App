package com.example.gamezonebooking;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<String> storeNames = new ArrayList<>();
    private ArrayList<String> storeImageurls = new ArrayList<>();
    private static final String TAG = "MainActivity";
    private  View view;
    public Home_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Home_fragment newInstance(String param1, String param2) {
        Home_fragment fragment = new Home_fragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        return view;
    }

    private void getImages(){

        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        storeImageurls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        storeNames.add("Havasu Falls");

        storeImageurls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        storeNames.add("Trondheim");

        storeImageurls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        storeNames.add("Portugal");

        storeImageurls.add("https://i.redd.it/j6myfqglup501.jpg");
        storeNames.add("Rocky Mountain National Park");

        storeImageurls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        storeNames.add("Mahahual");

        storeImageurls.add("https://i.redd.it/k98uzl68eh501.jpg");
        storeNames.add("Frozen Lake");

        storeImageurls.add("https://i.redd.it/glin0nwndo501.jpg");
        storeNames.add("White Sands Desert");

        storeImageurls.add("https://i.redd.it/obx4zydshg601.jpg");
        storeNames.add("Austrailia");

        storeImageurls.add("https://i.imgur.com/ZcLLrkY.jpg");
        storeNames.add("Washington");

        initRecyclerView(view);

    }
    private void initRecyclerView(@NonNull View view){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.nearest_store_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewAdapter adapter = new RecycleViewAdapter(storeNames,storeImageurls,view.getContext());
        recyclerView.setAdapter(adapter);
    }
}