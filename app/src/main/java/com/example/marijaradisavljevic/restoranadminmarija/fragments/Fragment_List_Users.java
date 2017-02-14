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
import com.example.marijaradisavljevic.restoranadminmarija.adapters.MyCustomAdatperForTheListUL;
import com.example.marijaradisavljevic.restoranadminmarija.database.UserInfo;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by marija on 24.1.17.
 */

public class Fragment_List_Users extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";

    RecyclerView listOfUsers;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_list_users_layout,container, false);
        getActivity().setTitle("Lista korisnika");
        listOfUsers = (RecyclerView) mRoot.findViewById(R.id.listusers);
        listOfUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        MyCustomAdatperForTheListUL adapter = new MyCustomAdatperForTheListUL(getActivity(),mDatabase);
       // ArrayList<UserInfo> myList = FireBase.getInstance().getUserList();
        //for(UserInfo ui:myList){
          //  adapter.addItem(new Fragment_List_Users.ItemForUsersList(ui));
        //}
        listOfUsers.setAdapter(adapter);

        return mRoot;
    }


}
