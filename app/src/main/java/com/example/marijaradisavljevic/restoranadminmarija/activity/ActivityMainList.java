package com.example.marijaradisavljevic.restoranadminmarija.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.Content;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_Add_Menu_Item;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_Add_User;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_List_Rezer_and_Selection;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_List_Users;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_Log_Out;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_User_Info;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_menu_Item_List;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.FreagmentAddOrder;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;

import java.util.List;

/**
 * Created by marija.radisavljevic on 6/9/2016.
 */

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {each itam in list has it own activity and coresponding fragment for nady and tablet  representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 *  /**
 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
 * device.
 */
public class ActivityMainList  extends AppCompatActivity  {



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation==1) {
            Intent intent;
            intent = new Intent(this, ActivityDetails.class);
           // intent.putExtra(ActivityDetails.ARG_ITEM_ID, holder.mItem.id);
            intent.putExtra(ActivityDetails.CHOOSEFRAGM , ActivityDetails.LOGOUT);
            startActivity(intent);

        }else{//mTwoPane == true;

        }
    }

    private boolean mTwoPane;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list);
         Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar.setNavigationIcon(R.drawable.back);
        //toolbar.setNavigationContentDescription(getResources().getString(R.string.nameOfApp));
        // toolbar.setLogo(R.drawable.help);
        toolbar.setTitle( getResources().getString(R.string.Logo_description));
        toolbar.setSubtitle(FireBase.getInstance().toolBarTypeNameSurnameString());
        setTitle("Restoran");
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(Content.ITEMSADMIN));
    }

    private static ActivityMainList instance = new ActivityMainList();

    public static ActivityMainList getInstance() {
        return instance;
    }


    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Content.MainItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<Content.MainItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
           // holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {

                        Bundle arguments = new Bundle();
                        switch (Integer.parseInt(holder.mItem.id)) {
                            case 1:
                                arguments.putString(Fragment_User_Info.ARG_ITEM_ID, holder.mItem.id);
                                Fragment_User_Info fragment1 = new Fragment_User_Info();
                                fragment1.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment1).commit();

                                break;
                            case 2:
                                arguments.putString(Fragment_Add_User.ARG_ITEM_ID, holder.mItem.id);
                                Fragment_Add_User fragment2 = new Fragment_Add_User();
                                fragment2.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment2).commit();

                                break;
                            case 3:
                                arguments.putString(Fragment_List_Users.ARG_ITEM_ID, holder.mItem.id);
                                Fragment_List_Users fragment3 = new Fragment_List_Users();
                                fragment3.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment3).commit();

                                break;
                            case 4:
                                arguments.putString(Fragment_List_Rezer_and_Selection.ARG_ITEM_ID, holder.mItem.id);
                                Fragment_List_Rezer_and_Selection fragment4 = new Fragment_List_Rezer_and_Selection();
                                fragment4.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment4).commit();

                                break;
                            case 5:
                               // arguments.putString(Fragment_Add_Menu_Item.ARG_ITEM_ID, holder.mItem.id);
                                Fragment_Add_Menu_Item fragment5 = new Fragment_Add_Menu_Item();
                               // fragment5.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment5).commit();

                                break;
                            case 6:
                                arguments.putString(Fragment_menu_Item_List.ARG_ITEM_ID, holder.mItem.id);
                                Fragment_menu_Item_List fragment6 = new Fragment_menu_Item_List();
                                fragment6.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment6).commit();

                                break;
                            case 7:
                                arguments.putString(Fragment_Log_Out.ARG_ITEM_ID, holder.mItem.id);
                                Fragment_Log_Out fragment7 = new Fragment_Log_Out();
                                fragment7.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment7).commit();

                                break;
                        }
                    } else {
                        Context context = v.getContext();
                        Intent intent;
                        switch (Integer.parseInt(holder.mItem.id)) {
                            case 1:
                                 intent = new Intent(context, ActivityDetails.class);
                                intent.putExtra(ActivityDetails.ARG_ITEM_ID, holder.mItem.id);
                                intent.putExtra(ActivityDetails.CHOOSEFRAGM , ActivityDetails.USER_INFO);
                                context.startActivity(intent);
                                break;
                            case 2:
                                 intent = new Intent(context, ActivityDetails.class);
                                intent.putExtra(ActivityDetails.ARG_ITEM_ID, holder.mItem.id);
                                intent.putExtra(ActivityDetails.CHOOSEFRAGM , ActivityDetails.ADD_NEW_USER);
                                context.startActivity(intent);
                                break;
                            case 3:
                                 intent = new Intent(context, ActivityDetails.class);
                                intent.putExtra(ActivityDetails.ARG_ITEM_ID, holder.mItem.id);
                                intent.putExtra(ActivityDetails.CHOOSEFRAGM , ActivityDetails.LIST_USERS);
                                context.startActivity(intent);
                                break;
                            case 4:
                                 intent = new Intent(context, ActivityDetails.class);
                                intent.putExtra(ActivityDetails.ARG_ITEM_ID, holder.mItem.id);
                                intent.putExtra(ActivityDetails.CHOOSEFRAGM , ActivityDetails.LIST_REZERV);
                                context.startActivity(intent);
                                break;
                            case 5:
                                 intent = new Intent(context, ActivityDetails.class);
                                intent.putExtra(ActivityDetails.ARG_ITEM_ID, holder.mItem.id);
                                intent.putExtra(ActivityDetails.CHOOSEFRAGM , ActivityDetails.ADD_ITEM_MENU) ;
                                context.startActivity(intent);
                                break;
                            case 6:
                                 intent = new Intent(context, ActivityDetails.class);
                                intent.putExtra(ActivityDetails.ARG_ITEM_ID, holder.mItem.id);
                                intent.putExtra(ActivityDetails.CHOOSEFRAGM , ActivityDetails.LIST_ITEMS_MENU);
                                context.startActivity(intent);
                                break;
                            case 7:
                                 intent = new Intent(context, ActivityDetails.class);
                                intent.putExtra(ActivityDetails.ARG_ITEM_ID, holder.mItem.id);
                                intent.putExtra(ActivityDetails.CHOOSEFRAGM , ActivityDetails.LOGOUT);
                                context.startActivity(intent);
                                break;
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            //public final TextView mIdView;
            public final TextView mContentView;
            public Content.MainItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
               // mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

    public void callFragmentAddOrderForEdit(int id){
        Bundle arguments = new Bundle();
        arguments.putString("name", "FreagmentAddOrder");
        arguments.putString("rezervationId", Integer.toString(id));
        arguments.putString("action", "onclick");
        FreagmentAddOrder fragment6 = new FreagmentAddOrder();
        fragment6.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment6).commit();

    }
    public void callUserInfoForEditUser(){
        Bundle arguments = new Bundle();

        Fragment_menu_Item_List fragment6 = new Fragment_menu_Item_List();
        fragment6.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment6).commit();

    }
    public void callAddMenuItemForEdtiItemMenu(String id){
        Bundle arguments = new Bundle();

        Fragment_Add_Menu_Item fragment6 = new Fragment_Add_Menu_Item();
        arguments.putString("foodItemId", id);
        fragment6.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment6).commit();

    }


    public void callUserInfoForEditUser(String username, String password, String keyFireBase) {
        Bundle arguments = new Bundle();

        Fragment_Add_User fragment6 = new Fragment_Add_User();
        arguments.putString("username", username);
        arguments.putString("password", password);
        arguments.putString("keyForFireBase",keyFireBase);
        fragment6.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment6).commit();

    }


    public void callFragmentListReserAndSelection() {
        Bundle arguments = new Bundle();

        Fragment_List_Rezer_and_Selection fragment6 = new Fragment_List_Rezer_and_Selection();
        fragment6.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment6).commit();

    }
}
