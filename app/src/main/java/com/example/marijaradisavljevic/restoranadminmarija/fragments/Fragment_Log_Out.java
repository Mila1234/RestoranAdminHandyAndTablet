package com.example.marijaradisavljevic.restoranadminmarija.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.marijaradisavljevic.restoranadminmarija.R;

import com.example.marijaradisavljevic.restoranadminmarija.activity.LoginActivity;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by marija on 24.1.17.
 */

public class Fragment_Log_Out extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";


    private static Fragment_Log_Out instance;
    public static Fragment_Log_Out getInstance() {


        if(instance == null){
            return new Fragment_Log_Out();
        }else return instance;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_logout,container, false);
        getActivity().setTitle("Odjava");
        Button ok =  (Button) mRoot.findViewById(R.id.ok_button);
        assert ok != null;
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Odjava", Toast.LENGTH_LONG).show();

                Intent intent2 = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|FLAG_ACTIVITY_NEW_TASK);

                getActivity().getApplicationContext().startActivity(intent2);

            }
        });
        Button cancel =  (Button)mRoot.findViewById(R.id.cancel_button);

        assert cancel != null;
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Odustajem", Toast.LENGTH_LONG).show();

                getActivity().onBackPressed();


            }
        });
        return mRoot;
    }

/*
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if(!FireBase.getInstance().isUserAdmin()) {
            menu.findItem(R.id.action_logout).setVisible(true);
            menu.findItem(R.id.action_user_info).setVisible(true);
            menu.findItem(R.id.action_add).setVisible(true);
        }
        super.onPrepareOptionsMenu(menu);
    }*/
}
