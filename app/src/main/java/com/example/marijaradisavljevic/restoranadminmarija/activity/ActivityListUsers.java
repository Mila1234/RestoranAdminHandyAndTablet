package com.example.marijaradisavljevic.restoranadminmarija.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.HolderAdapterItem;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.MyCustomAdatperForTheList;
import com.example.marijaradisavljevic.restoranadminmarija.database.UserInfo;
import com.example.marijaradisavljevic.restoranadminmarija.servis.Servis;

import java.util.ArrayList;

/**
 * Created by marija.radisavljevic on 6/9/2016.
 */
public class ActivityListUsers  extends AppCompatActivity {

    ListView listOfUsers;



    @Override
    protected void onResume() {
        super.onResume();
/*
        MyCustomAdatperForTheList<ItemForUsersList> adapter = new MyCustomAdatperForTheList(this);
        ArrayList<UserInfo> myList = Servis.getInstance().getUserList(UserData.getInstance().getUsername(),UserData.getInstance().getPassword());
        for(UserInfo rez:myList){
            adapter.addItem(new ItemForUsersList(rez));
        }
        listOfUsers.setAdapter(adapter);

        //////////////////////////////////////////////////////////////
        ActivityListUsers.this.listOfUsers.invalidateViews();*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_users_layout);
        //setTitle(R.string.activity_list_users);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar.setNavigationIcon(R.drawable.back);
        //toolbar.setNavigationContentDescription(getResources().getString(R.string.nameOfApp));
        // toolbar.setLogo(R.drawable.help);
        toolbar.setTitle(getResources().getString(R.string.Logo_description));
        toolbar.setSubtitle(Servis.getInstance().toolBarTypeNameSurnameString());

        listOfUsers = (ListView) findViewById(R.id.listusers);
        MyCustomAdatperForTheList<ItemForUsersList> adapter = new MyCustomAdatperForTheList(this);
        ArrayList<UserInfo> myList = Servis.getInstance().getUserList();
        for(UserInfo ui:myList){
            adapter.addItem(new ItemForUsersList(ui));
        }
        listOfUsers.setAdapter(adapter);


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
            return  new UserInfoViewHolder(this);
        }

        private class UserInfoViewHolder implements IViewHolder<ItemForUsersList> {
            ItemForUsersList bla;
            TextView typeAndnameSurname,username ,password,email, number;
            Button edit, remove;


            public UserInfoViewHolder(ItemForUsersList bla) {
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
            public void fillData(final ItemForUsersList adapterItem) {

                typeAndnameSurname.setVisibility(View.VISIBLE);
                //String typeNameSurnameString = adapterItem.userinfo.getType() + " : " + adapterItem.userinfo.getName() + " " + adapterItem.userinfo.getSurname();
                String typeNameSurnameString = adapterItem.userinfo.getStringTypeNameSurnameForListUsers();

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
                        Intent intent2 = new Intent(getApplicationContext(), ActivityAddUser.class);
                        intent2.putExtra("username", adapterItem.userinfo.getUsername());
                        intent2.putExtra("password", adapterItem.userinfo.getPassword());
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent2);
                    }
                });


                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Servis.getInstance().removeUser(userinfo.getUsername(), userinfo.getPassword());

                        MyCustomAdatperForTheList<ItemForUsersList> adapter = new MyCustomAdatperForTheList(getBaseContext());
                        ArrayList<UserInfo> myList = Servis.getInstance().getUserList();
                        for(UserInfo rez:myList){
                            adapter.addItem(new ItemForUsersList(rez));
                        }
                        listOfUsers.setAdapter(adapter);

                        //////////////////////////////////////////////////////////////
                        ActivityListUsers.this.listOfUsers.invalidateViews();


                    }
                });
            }
        }
    }


}
