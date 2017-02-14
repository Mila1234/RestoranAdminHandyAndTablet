package com.example.marijaradisavljevic.restoranadminmarija.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityDetails;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityHost;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.HolderAdapterItem;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.MyCustomAdatperForTheList;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.MyCustomAdatperForTheListSR;
import com.example.marijaradisavljevic.restoranadminmarija.data.UserData;
import com.example.marijaradisavljevic.restoranadminmarija.database.Rezervation;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//import android.app.Fragment;

/**
 * Created by marija.radisavljevic on 5/13/2016.
 */
public class FragmentListReservations extends Fragment {

    private RecyclerView lvDetail;


   private MyCustomAdatperForTheListSR adapter;

   // @Override
    //public void onResume() {
      //  super.onResume();
       // if (null != adapter) {
         //   adapter.refreshList();
        //}
    //}
    @Override
    public void onStop() {
        super.onStop();

        // Clean up comments listener
       // adapter.cleanupListener();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_list_rezervations_layout, container, false);
        getActivity().setTitle("Restoran");
        lvDetail = (RecyclerView)mRoot.findViewById(R.id.list_reservations);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

         adapter = new MyCustomAdatperForTheListSR(getActivity(),mDatabase);
       // ArrayList<Rezervation> myList = FireBase.getInstance().getRezervationsWithRegulation(UserData.getInstance().getSelecionRegulation());
        //for(Rezervation rez:myList){
          //  adapter.addItem(new ItemForRezervationsList(rez));
        //}
        lvDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        lvDetail.setAdapter(adapter);

        return mRoot;
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_logout).setVisible(true);
        menu.findItem(R.id.action_user_info).setVisible(true);
        menu.findItem(R.id.action_add).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }






}
