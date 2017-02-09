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
import com.example.marijaradisavljevic.restoranadminmarija.database.FoodMenuItem;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;

import java.util.ArrayList;

/**
 * Created by marija on 24.1.17.
 */

public class Fragment_menu_Item_List extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";

    ListView lvDetail;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_menu_item_list,container, false);
        lvDetail = (ListView) mRoot.findViewById(R.id.list_reservations);
        getActivity().setTitle("Restoran meni");
        MyCustomAdatperForTheList<Fragment_menu_Item_List.ItemForFoodMenuItemsList> adapter = new MyCustomAdatperForTheList(getActivity().getBaseContext());
        ArrayList<FoodMenuItem> myList = FireBase.getInstance().getfoodmenuitemslist();
        for(FoodMenuItem rez:myList){
            adapter.addItem(new Fragment_menu_Item_List.ItemForFoodMenuItemsList(rez));
        }
        lvDetail.setAdapter(adapter);

        return mRoot;
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

                        if (getActivity().getClass().equals(ActivityMainList.getInstance().getClass())){

                            ((ActivityMainList)getActivity()).callAddMenuItemForEdtiItemMenu(Integer.toString(rezervation.getId()));
                        }else {
                            //otvori prozor fragment FreagmentAddOrder
                            Intent intent2 = new Intent(getActivity().getApplicationContext(), ActivityDetails.class);
                            intent2.putExtra("foodItemId", Integer.toString(rezervation.getId()));
                            intent2.putExtra(ActivityDetails.CHOOSEFRAGM, ActivityDetails.ADD_ITEM_MENU);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getActivity().getApplicationContext().startActivity(intent2);
                        }

                    }
                });

                // remove.setOnClickListener(this);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FireBase.getInstance().removeFootMenuItem(rezervation.getId());

                        MyCustomAdatperForTheList<ItemForFoodMenuItemsList> adapter = new MyCustomAdatperForTheList(getActivity().getBaseContext());
                        ArrayList<FoodMenuItem> myList = FireBase.getInstance().getfoodmenuitemslist();
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
