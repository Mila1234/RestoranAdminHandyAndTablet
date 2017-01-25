package com.example.marijaradisavljevic.restoranadminmarija.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.marijaradisavljevic.restoranadminmarija.R;

import com.example.marijaradisavljevic.restoranadminmarija.activity.LoginActivity;

/**
 * Created by marija on 24.1.17.
 */

public class Fragment_Log_Out extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_logout,container, false);

        Button ok =  (Button) mRoot.findViewById(R.id.ok_button);
        assert ok != null;
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Odjava", Toast.LENGTH_LONG).show();

                Intent intent2 = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
}
