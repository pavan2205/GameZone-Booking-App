package com.example.gamezonebooking;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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

    Button yesBtn,noBtn;
    ImageButton signoutBtn;


    ImageSlider imageSlider;

    private ArrayList<String> storeNames = new ArrayList<>();
    private ArrayList<String> storeImageurls = new ArrayList<>();

    private ArrayList<AllStoresDetailsList> allStores = new ArrayList<>();
    private ArrayList<posterUrlsList> posterurls=new ArrayList<>();
    FirebaseFirestore db;

    List<SlideModel> slideModelList = new ArrayList<>();

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

       imageSlider=view.findViewById(R.id.carousel_card_view);
        signoutBtn=(ImageButton) view.findViewById(R.id.signoutBtn);


        db=FirebaseFirestore.getInstance();


        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ShowCustomDialogBox(getView());
                Dialog dialog=new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                 view  = getActivity().getLayoutInflater().inflate(R.layout.layout_custom_dialog, null);
                dialog.setContentView(view);

                yesBtn=view.findViewById(R.id.btnyes);
                noBtn=view.findViewById(R.id.btnno);

                yesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(getContext(),LoginActivity.class);
                        intent.putExtra("signout",0);
                        startActivity(intent);
                    }
                });

                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        getNearByStoreImages();
        getAllStoreImages();

//        slideModelList.add(new SlideModel("https://www.heypoorplayer.com/wp-content/uploads/2023/03/WWE-2k23.png"));
//        slideModelList.add(new SlideModel("https://www.global-esports.news/wp-content/uploads/2022/09/FIFA-23.png"));
//        slideModelList.add(new SlideModel("https://deadline.com/wp-content/uploads/2022/03/EGS_GodofWar_SantaMonicaStudio_S2_1200x1600-fbdf3cbc2980749091d52751ffabb7b7_1200x1600-fbdf3cbc2980749091d52751ffabb7b7-e1646683029138.jpeg"));
//        slideModelList.add(new SlideModel("https://assets.mspimages.in/gear/wp-content/uploads/2022/11/GTA_Rockstar_Explained.png"));
//        slideModelList.add(new SlideModel("https://www.adrenaline.com.br/wp-content/plugins/seox-image-magick/imagick_convert.php?width=1200&height=545&format=webp&quality=91&imagick=/wp-content/uploads/2022/08/the-last-of-us-part-i_2.jpg"));


        return view;
    }

    private void ShowCustomDialogBox(View view) {

    }

    private void getNearByStoreImages(){



        db.collection("gameposters").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.e("firestore error", error.getMessage());
                    return;
                }

                for(DocumentChange dc:value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        posterurls.add(dc.getDocument().toObject(posterUrlsList.class));
                    }
                }


if(slideModelList.size()==0) {
    for (posterUrlsList urls : posterurls) {


        slideModelList.add(new SlideModel(urls.image));

        Log.d("urls", urls.image);
    }
}
    imageSlider.setImageList(slideModelList, true);

            }
        });
    }

    public void getAllStoreImages(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        if(allStores.size()==0) {
            allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg", "gameBot", "Bunts hostel", false));
            allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg", "gameBot", "Bunts hostel", true));
            allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg", "gameBot", "Bunts hostel", true));
            allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg", "gameBot", "Bunts hostel", false));
            allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg", "gameBot", "Bunts hostel", false));
            allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg", "gameBot", "Bunts hostel", true));
            allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg", "gameBot", "Bunts hostel", false));
            allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg", "gameBot", "Bunts hostel", true));
            allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg", "gameBot", "Bunts hostel", false));
            allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg", "gameBot", "Bunts hostel", true));
            allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg", "gameBot", "Bunts hostel", false));
            allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg", "gameBot", "Bunts hostel", false));
            allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg", "gameBot", "Bunts hostel", false));
            allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg", "gameBot", "Bunts hostel", true));
            allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg", "gameBot", "Bunts hostel", false));
            allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg", "gameBot", "Bunts hostel", false));
        }
            initRecyclerView(view);

//
//        allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg","gameBot","Bunts hostel",false));
//        allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg","gameBot","Bunts hostel",true));
//        allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg","gameBot","Bunts hostel",true));
//        allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg","gameBot","Bunts hostel",false));
//        allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg","gameBot","Bunts hostel",false));
//        allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg","gameBot","Bunts hostel",true));
//        allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg","gameBot","Bunts hostel",false));
//        allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg","gameBot","Bunts hostel",true));
//        allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg","gameBot","Bunts hostel",false));
//        allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg","gameBot","Bunts hostel",true));
//        allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg","gameBot","Bunts hostel",false));
//        allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg","gameBot","Bunts hostel",false));
//        allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg","gameBot","Bunts hostel",false));
//        allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg","gameBot","Bunts hostel",true));
//        allStores.add(new AllStoresDetailsList("https://i.imgur.com/ZcLLrkY.jpg","gameBot","Bunts hostel",false));
//        allStores.add(new AllStoresDetailsList("https://i.redd.it/k98uzl68eh501.jpg","gameBot","Bunts hostel",false));
//        initRecyclerView(view);

    }


    private void initRecyclerView(@NonNull View view){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.nearest_store_recycler_view);
        RecyclerView allStoreRecyclerview = view.findViewById(R.id.all_store_list);
        recyclerView.setLayoutManager(layoutManager);

        allStoreRecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));

        //nearest store
        RecycleViewAdapter adapter = new RecycleViewAdapter(allStores,view.getContext());
        recyclerView.setAdapter(adapter);
        AllStoreRecyclerViewAdapter allStoreRecyclerViewAdapter = new AllStoreRecyclerViewAdapter(allStores,view.getContext());
        //all stores

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        Log.d("screen Size", String.valueOf(height));

        // Get the screen height in pixels
        allStoreRecyclerview.setMinimumHeight(allStores.size() * (height)/2);
        allStoreRecyclerview.setAdapter(allStoreRecyclerViewAdapter);
        allStoreRecyclerview.setNestedScrollingEnabled(false);
        allStoreRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    recyclerView.stopScroll();
                }
            }
        });
    }
}