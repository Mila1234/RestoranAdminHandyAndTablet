package com.example.marijaradisavljevic.restoranadminmarija.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.marijaradisavljevic.restoranadminmarija.R;

/**
 * Created by marija.radisavljevic on 6/9/2016.
 */
public class ActivityMainList  extends AppCompatActivity implements View.OnClickListener {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manu_gui, menu);
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list);
        setTitle("Restoran");

        TextView userinfo = (TextView) findViewById(R.id.userinfo);
        TextView addnewUser = (TextView) findViewById(R.id.addUser);
        TextView listUsers = (TextView) findViewById(R.id.listusers);
        TextView listrezervations = (TextView) findViewById(R.id.listrezervations);
        TextView addItem  = (TextView) findViewById(R.id.additem);
        TextView listItems = (TextView) findViewById(R.id.listitems);
        TextView logOut = (TextView) findViewById(R.id.logout);

        userinfo.setOnClickListener(this);
        addnewUser.setOnClickListener(this);
        listUsers.setOnClickListener(this);
        listrezervations.setOnClickListener(this);
        addItem.setOnClickListener(this);
        listItems.setOnClickListener(this);
        logOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.userinfo:
                 intent = new Intent(getApplicationContext(), ActivityUserInfo.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                break;
            case R.id.addUser:
                 intent = new Intent(getApplicationContext(), ActivityAddUser.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                break;
            case R.id.listusers:
                 intent = new Intent(getApplicationContext(), ActivityListUsers.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                break;
            case R.id.listrezervations:
                 intent = new Intent(getApplicationContext(), ActivityListRezer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                break;
            case R.id.additem:
                 intent = new Intent(getApplicationContext(), ActivityAddmenuItem.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                break;
            case R.id.listitems:
                 intent = new Intent(getApplicationContext(), ActivityMenuItemList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                break;
            case R.id.logout:
                 intent = new Intent(getApplicationContext(), ActivityLogout.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                break;
        }
    }
}
