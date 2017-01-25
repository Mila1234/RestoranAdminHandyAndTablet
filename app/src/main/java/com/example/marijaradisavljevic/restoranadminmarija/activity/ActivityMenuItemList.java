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
import com.example.marijaradisavljevic.restoranadminmarija.database.FoodMenuItem;

import com.example.marijaradisavljevic.restoranadminmarija.servis.Servis;

import java.util.ArrayList;

/**
 * Created by marija.radisavljevic on 6/9/2016.
 */
public class ActivityMenuItemList  extends AppCompatActivity {
    public static final String ARG_ITEM_ID = "item_id";
    ListView lvDetail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_item_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar.setNavigationIcon(R.drawable.back);
        //toolbar.setNavigationContentDescription(getResources().getString(R.string.nameOfApp));
        // toolbar.setLogo(R.drawable.help);
        toolbar.setTitle(getResources().getString(R.string.Logo_description));
        toolbar.setSubtitle(Servis.getInstance().toolBarTypeNameSurnameString());

        lvDetail = (ListView) findViewById(R.id.list_reservations);

        MyCustomAdatperForTheList<ItemForFoodMenuItemsList> adapter = new MyCustomAdatperForTheList(getBaseContext());
        ArrayList<FoodMenuItem> myList = Servis.getInstance().getfoodmenuitemslist();
        for(FoodMenuItem rez:myList){
            adapter.addItem(new ItemForFoodMenuItemsList(rez));
        }
        lvDetail.setAdapter(adapter);

    }



    public class ItemForFoodMenuItemsList extends HolderAdapterItem {
        FoodMenuItem rezervation;

        public ItemForFoodMenuItemsList(FoodMenuItem ld){

            rezervation = ld;

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
            return R.layout.food_menu_item_layout;
        }

        @Override
        protected  IViewHolder createViewHolder() {
            return  new FoodItemViewHolder(this);
        }

        private class  FoodItemViewHolder implements IViewHolder<ItemForFoodMenuItemsList> {
            ItemForFoodMenuItemsList bla;
            TextView info;
            Button edit, remove;


            public FoodItemViewHolder(ItemForFoodMenuItemsList bla) {
                this.bla = bla;
            }

            @Override
            public void findViews(View convertView) {
                info = (TextView)convertView.findViewById(R.id.info);
                edit  = (Button)convertView.findViewById(R.id.edit);
                remove = (Button)convertView.findViewById(R.id.remove);

            }
            @Override
            public void fillData(final ItemForFoodMenuItemsList adapterItem) {

                info.setVisibility(View.VISIBLE);
                info.setText(adapterItem.rezervation.getFood()+"  \ncena : "+adapterItem.rezervation.getPrice());

                edit.setVisibility(View.VISIBLE);
                remove.setVisibility(View.VISIBLE);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //otvori prozor fragment FreagmentAddOrder
                        Intent intent2 = new Intent(getApplicationContext(), ActivityDetails.class);
                        intent2.putExtra("foodItemId", Integer.toString(rezervation.getId()));
                        intent2.putExtra(ActivityDetails.CHOOSEFRAGM , ActivityDetails.ADD_ITEM_MENU);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent2);

                    }
                });

                // remove.setOnClickListener(this);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Servis.getInstance().removeFootMenuItem(rezervation.getId());

                        MyCustomAdatperForTheList<ItemForFoodMenuItemsList> adapter = new MyCustomAdatperForTheList(getBaseContext());
                        ArrayList<FoodMenuItem> myList = Servis.getInstance().getfoodmenuitemslist();
                        for(FoodMenuItem rez:myList){
                            adapter.addItem(new ItemForFoodMenuItemsList(rez));
                        }
                        lvDetail.setAdapter(adapter);

                        //////////////////
                        lvDetail.invalidateViews();


                    }
                });
            }
        }
    }
}
