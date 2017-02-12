package com.example.marijaradisavljevic.restoranadminmarija.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.marijaradisavljevic.restoranadminmarija.database.UserInfo;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;

import java.util.ArrayList;

/**
 * Created by marija on 24.1.17.
 */

public class Fragment_List_Users extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";

    ListView listOfUsers;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_list_users_layout,container, false);
        getActivity().setTitle("Lista korisnika");
        listOfUsers = (ListView) mRoot.findViewById(R.id.listusers);
        MyCustomAdatperForTheList<Fragment_List_Users.ItemForUsersList> adapter = new MyCustomAdatperForTheList(getActivity());
        ArrayList<UserInfo> myList = FireBase.getInstance().getUserList();
        for(UserInfo ui:myList){
            adapter.addItem(new Fragment_List_Users.ItemForUsersList(ui));
        }
        listOfUsers.setAdapter(adapter);

        return mRoot;
    }

    public class ItemForUsersList extends HolderAdapterItem {

        //private String time,name_user,numberTable,price,itemsOrder,paidOrNot;
        // int id;
        UserInfo userinfo;

        public ItemForUsersList(UserInfo ld){

            userinfo = ld;

        }
        @Override
        public boolean isEnabled() {//cemu sluzi
            return true;
        }

        @Override
        public View getView(Context context, View convertView, ViewGroup parent) {
            return super.getView(context, convertView, parent);
        }

        @Override
        protected int getViewLayoutResId() {
            return R.layout.user_info;
        }

        @Override
        protected  IViewHolder createViewHolder() {
            return  new Fragment_List_Users.ItemForUsersList.UserInfoViewHolder(this);
        }

        private class UserInfoViewHolder implements IViewHolder<Fragment_List_Users.ItemForUsersList> {
            Fragment_List_Users.ItemForUsersList bla;
            TextView typeAndnameSurname,username ,password,email, number;
            Button edit, remove;


            public UserInfoViewHolder(Fragment_List_Users.ItemForUsersList bla) {
                this.bla = bla;
            }

            @Override
            public void findViews(View convertView) {
                typeAndnameSurname = (TextView)convertView.findViewById(R.id.typAndName);
                username= (TextView)convertView.findViewById(R.id.user_name);
                password= (TextView)convertView.findViewById(R.id.password);
                email= (TextView)convertView.findViewById(R.id.email);
                number = (TextView)convertView.findViewById(R.id.number);

                edit  = (Button)convertView.findViewById(R.id.edit);
                remove = (Button)convertView.findViewById(R.id.remove);
            }
            @Override
            public void fillData(final Fragment_List_Users.ItemForUsersList adapterItem) {

                typeAndnameSurname.setVisibility(View.VISIBLE);
                //String typeNameSurnameString = adapterItem.userinfo.getType() + " : " + adapterItem.userinfo.getName() + " " + adapterItem.userinfo.getSurname();
                //String typeNameSurnameString = adapterItem.userinfo.getStringTypeNameSurnameForListUsers();
                String typeNameSurnameString = adapterItem.userinfo.getType() + " : "+adapterItem.userinfo.getName()+ " " +adapterItem.userinfo.getSurname();

                typeAndnameSurname.setText(typeNameSurnameString);
                username.setVisibility(View.VISIBLE);
                username.setText(getString(R.string.username) + " : " + adapterItem.userinfo.getUsername());
                password.setVisibility(View.VISIBLE);
                password.setText(getString(R.string.password) +" : "+adapterItem.userinfo.getPassword());
                email.setVisibility(View.VISIBLE);
                email.setText(getString (R.string.email)+" : "+adapterItem.userinfo.getEmail());
                number.setVisibility(View.VISIBLE);
                number.setText(getString (R.string.number)+" : "+adapterItem.userinfo.getNumber());

                edit.setVisibility(View.VISIBLE);
                remove.setVisibility(View.VISIBLE);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getActivity().getClass().equals(ActivityMainList.getInstance().getClass())){

                            ((ActivityMainList)getActivity()).callUserInfoForEditUser(adapterItem.userinfo.getUsername(),adapterItem.userinfo.getPassword());
                        }else {
                            Intent intent2 = new Intent(getActivity().getApplicationContext(), ActivityDetails.class);
                            intent2.putExtra(ActivityDetails.CHOOSEFRAGM, ActivityDetails.ADD_NEW_USER);
                            intent2.putExtra("username", adapterItem.userinfo.getUsername());
                            intent2.putExtra("password", adapterItem.userinfo.getPassword());
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getActivity().getApplicationContext().startActivity(intent2);
                        }
                    }
                });


                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        FireBase.getInstance().removeUser(userinfo.getUsername(), userinfo.getPassword());

                        MyCustomAdatperForTheList<Fragment_List_Users.ItemForUsersList> adapter = new MyCustomAdatperForTheList(getActivity().getBaseContext());
                        ArrayList<UserInfo> myList = FireBase.getInstance().getUserList();
                        for(UserInfo rez:myList){
                            adapter.addItem(new Fragment_List_Users.ItemForUsersList(rez));
                        }
                        listOfUsers.setAdapter(adapter);

                        //////////////////////////////////////////////////////////////
                        Fragment_List_Users.this.listOfUsers.invalidateViews();


                    }
                });
            }
        }
    }
}
