package com.example.marijaradisavljevic.restoranadminmarija.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_Add_Menu_Item;
import com.example.marijaradisavljevic.restoranadminmarija.servis.Servis;

/**
 * Created by marija.radisavljevic on 6/9/2016.
 */
public class ActivityAddmenuItem  extends AppCompatActivity {
    public static final String ARG_ITEM_ID = "item_id";
    private EditText newItemName, price;
    private Button okButton;

    private Spinner kategory_spinner;

    private Bundle extras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_menu_item);
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
                  //  getIntent().getStringExtra(Fragment_Add_Menu_Item.ARG_ITEM_ID));
            Fragment_Add_Menu_Item fragment = new Fragment_Add_Menu_Item();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_menu, fragment)
                    .commit();
        }




    }
}
