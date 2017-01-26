package com.example.marijaradisavljevic.restoranadminmarija.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.marijaradisavljevic.restoranadminmarija.R;

import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_Add_Menu_Item;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_Add_New_Item_Rezerv;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_Add_User;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_List_Rezer_and_Selection;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_List_Users;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_Log_Out;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_User_Info;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_menu_Item_List;
import com.example.marijaradisavljevic.restoranadminmarija.servis.Servis;

/**
 * Created by marija.radisavljevic on 6/9/2016.
 */
public class ActivityDetails extends AppCompatActivity {
    public static final String ARG_ITEM_ID = "item_id";

    public static final int USER_INFO = 1 ;
    public static final int ADD_NEW_USER = 2;
    public static final int LIST_USERS = 3;
    public static final int LIST_REZERV = 4;
    public static final int ADD_ITEM_MENU = 5;
    public static final int LIST_ITEMS_MENU = 6;
    public static final int LOGOUT = 7;
    public static final String CHOOSEFRAGM = "fragment";

    private Fragment fragment = null;
    @Override
    protected void onSaveInstanceState(Bundle outState) {


        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_details_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar.setNavigationIcon(R.drawable.back);
        //toolbar.setNavigationContentDescription(getResources().getString(R.string.nameOfApp));
        // toolbar.setLogo(R.drawable.help);
        toolbar.setTitle(getResources().getString(R.string.Logo_description));
        toolbar.setSubtitle(Servis.getInstance().toolBarTypeNameSurnameString());

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            //ako nesto zelim da prosldim fragmentu
            // arguments.putString(Fragment_Add_Menu_Item.ARG_ITEM_ID,
            int fragmentChoose =  getIntent().getIntExtra(ActivityDetails.CHOOSEFRAGM,0);

            switch (fragmentChoose){
                case 1:fragment = new Fragment_User_Info();
                    break;
                case 2:fragment = new Fragment_Add_User();
                    break;
                case 3:fragment = new Fragment_List_Users();
                    break;
                case 4:fragment = new Fragment_List_Rezer_and_Selection();
                    break;
                case 5:fragment = new Fragment_Add_Menu_Item();
                    break;
                case 6:fragment = new Fragment_menu_Item_List();
                    break;
                case 7:fragment = new Fragment_Log_Out();
                    break;
            }

            if (fragment != null) {
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction().add(R.id.container_menu, fragment).commit();
            }
        }


    }

}
