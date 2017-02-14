package com.example.marijaradisavljevic.restoranadminmarija.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityDetails;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityMainList;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.HolderAdapterItem;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.MyCustomAdatperForTheList;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.MyCustomAdatperForTheListMIL;
import com.example.marijaradisavljevic.restoranadminmarija.database.FoodMenuItem;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by marija on 24.1.17.
 */

public class Fragment_menu_Item_List extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";

    RecyclerView lvDetail;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_menu_item_list,container, false);
        lvDetail = (RecyclerView) mRoot.findViewById(R.id.list_reservations);
        getActivity().setTitle("Restoran meni");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        MyCustomAdatperForTheListMIL adapter = new MyCustomAdatperForTheListMIL(getActivity().getBaseContext(),mDatabase);
       // ArrayList<FoodMenuItem> myList = FireBase.getInstance().getfoodmenuitemslist();
      //  for(FoodMenuItem rez:myList){
         //   adapter.addItem(new Fragment_menu_Item_List.ItemForFoodMenuItemsList(rez));
        //}
        lvDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        lvDetail.setAdapter(adapter);

        return mRoot;
    }


}
