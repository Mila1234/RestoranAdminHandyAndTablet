package com.example.marijaradisavljevic.restoranadminmarija.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityDetails;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityMainList;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.HolderAdapterItem;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.MyCustomAdatperForTheList;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.MyCustomAdatperForTheListSR;
import com.example.marijaradisavljevic.restoranadminmarija.data.UserData;
import com.example.marijaradisavljevic.restoranadminmarija.database.FoodMenuItem;
import com.example.marijaradisavljevic.restoranadminmarija.database.Rezervation;
import com.example.marijaradisavljevic.restoranadminmarija.data.SelecionRegulations;
import com.example.marijaradisavljevic.restoranadminmarija.database.UserInfo;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;
import com.example.marijaradisavljevic.restoranadminmarija.spiner.MySpinnerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by marija on 24.1.17.
 */

public class Fragment_List_Rezer_and_Selection extends Fragment implements  AdapterView.OnItemSelectedListener {
    public static final String ARG_ITEM_ID = "item_id";
    Boolean has = false;
    RecyclerView lvDetail;
    private Spinner spinnerNumberOfTable;
    private Spinner spinnerIsItPaid;
    private Spinner spinnerKategory;
    private Spinner spinnerUser;
    private List<FoodMenuItem> mFoodMeniItems = new ArrayList<>();
    private List<String> mFoodMeniItemsIds = new ArrayList<>();

    private ArrayAdapter<String> adapter_number_of_table;
    private ArrayAdapter<String>  adapter_isItPaid ;
    private ArrayAdapter<String> adapter_kategory;
    private ArrayAdapter<String> adapterUser;

    private List<UserInfo> mUserInfos = new ArrayList<>();
    private List<String> mUserInfosIds = new ArrayList<>();

    MyCustomAdatperForTheListSR adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_list_rezervations_selection_layout,container, false);
        getActivity().setTitle("Restoran");
        lvDetail = (RecyclerView) mRoot.findViewById(R.id.list_reservations);



        spinnerNumberOfTable = (Spinner)  mRoot.findViewById(R.id.numbreOfTable_spinner);
        spinnerIsItPaid = (Spinner)  mRoot.findViewById(R.id.isItPaid_spinner);
        spinnerKategory = (Spinner)  mRoot.findViewById(R.id.kategory_spinner);
        spinnerUser = (Spinner) mRoot.findViewById(R.id.user_spiner);


        //String[] value = getResources().getStringArray(R.array.numbers);
        adapter_number_of_table = new MySpinnerAdapter(true,getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item, FireBase.getInstance().stringListofTables());



        // Specify the layout to use when the list of choices appears
        adapter_number_of_table.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerNumberOfTable.setAdapter(adapter_number_of_table);
        spinnerNumberOfTable.setSelection(((MySpinnerAdapter)adapter_number_of_table).getStartPosition());
        spinnerNumberOfTable.setOnItemSelectedListener(this);


        String[] value = getResources().getStringArray(R.array.paidNotpaid);
        adapter_isItPaid = new MySpinnerAdapter(false,getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item ,value);

        // Specify the layout to use when the list of choices appears
        adapter_isItPaid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerIsItPaid.setAdapter(adapter_isItPaid);
        spinnerIsItPaid.setSelection(((MySpinnerAdapter)adapter_isItPaid).getStartPosition());
        spinnerIsItPaid.setOnItemSelectedListener(this);

        // value = getResources().getStringArray(R.array.kategory_array);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                try {

                    HashMap<String, Object> foodMenuItemHM = (HashMap<String, Object>) dataSnapshot.getValue();
                    FoodMenuItem fmi = new FoodMenuItem();
                    FoodMenuItem nadstavka = new FoodMenuItem();
                    fmi.setId(Integer.valueOf(((Long) foodMenuItemHM.get("id")).intValue()));
                    fmi.setFood((String) foodMenuItemHM.get("food"));
                    fmi.setPrice(Integer.valueOf(((Long) foodMenuItemHM.get("price")).intValue()));

                    HashMap<String, Object> nadstavkaHM = (HashMap<String, Object>) foodMenuItemHM.get("nadstavka");
                    if (nadstavkaHM !=null) {
                        nadstavka.setId(Integer.valueOf(((Long) nadstavkaHM.get("id")).intValue()));
                        nadstavka.setFood((String) nadstavkaHM.get("food"));
                        nadstavka.setPrice(Integer.valueOf(((Long) nadstavkaHM.get("price")).intValue()));
                        fmi.setNadstavka(nadstavka);
                    }else{
                        fmi.setNadstavka(null);
                    }

                    fmi.setKey(dataSnapshot.getKey());

                    mFoodMeniItemsIds.add(dataSnapshot.getKey());
                    mFoodMeniItems.add(fmi);

                    adapter_kategory = new MySpinnerAdapter(true,getActivity().getBaseContext(),
                            android.R.layout.simple_spinner_item, napraviNiz (mFoodMeniItems) );

                    adapter_kategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    spinnerKategory.setAdapter(adapter_kategory);


                    spinnerKategory.setSelection(((MySpinnerAdapter)adapter_kategory).getStartPosition());

                    SelecionRegulations sr = new SelecionRegulations();
                    sr.setKategory((String)spinnerKategory.getSelectedItem());
                    if (adapter_kategory!=null) {
                        if (spinnerKategory.getSelectedItemPosition() != ((MySpinnerAdapter) adapter_kategory).getStartPosition()) {
                            sr.setKategory_selected(true);
                        } else {
                            sr.setKategory_selected(false);
                        }
                    }
                    UserData.getInstance().setSelectionRegulation(sr);


                }catch (Exception e){
                    e.printStackTrace();
                }

                // [END_EXCLUDE]
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());


                // [END_EXCLUDE]
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                //  mFoodMeniItems.remove(position);
                /// mFoodMeniItemsIds.remove(position);
                // notifyItemRangeRemoved(position);


                // [END_EXCLUDE]
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());

            }
        };
        mDatabase.child("foodMenu").addChildEventListener(childEventListener);
        // Specify the layout to use when the list of choices appears


        ChildEventListener childEventListener1 = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                Map<String, String> hmUserinfo = (Map<String, String>) dataSnapshot.getValue();
                //Collection<Object> colection = map.values();
                // Iterator iter =  colection.iterator();
                // Object userInfoObject = iter.next();
                // HashMap<String,String> hmUserinfo = (HashMap<String, String>) userInfoObject;
                UserInfo userInfo = new UserInfo(hmUserinfo.get("username"),hmUserinfo.get("name"),hmUserinfo.get("surname"),hmUserinfo.get("number"),hmUserinfo.get("email"),hmUserinfo.get("type"),hmUserinfo.get("password"));

                userInfo.setKey(dataSnapshot.getKey());

                mUserInfosIds.add(dataSnapshot.getKey());
                mUserInfos.add(userInfo);

                // value = getResources().getStringArray(R.array.kategory_array);
                adapterUser = new MySpinnerAdapter(true,getActivity().getBaseContext(),
                        android.R.layout.simple_spinner_item,napraviNizUser(mUserInfos) );

                // Specify the layout to use when the list of choices appears
                adapterUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinnerUser.setAdapter(adapterUser);
                spinnerUser.setSelection(((MySpinnerAdapter)adapterUser).getStartPosition());
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());


                // [END_EXCLUDE]
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                //  mUserInfos.remove(position);
                /// mUserInfosIds.remove(position);
                // notifyItemRangeRemoved(position);


                // [END_EXCLUDE]
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());

            }
        };
        mDatabase.child("users").addChildEventListener(childEventListener1);
        // Specify the layout to use when the list of choices appears

        spinnerKategory.setOnItemSelectedListener(this);


        spinnerUser.setOnItemSelectedListener(this);

        UserData.getInstance().setSelectionRegulation(new SelecionRegulations());
//////////list view
      //  MyCustomAdatperForTheList<Fragment_List_Rezer_and_Selection.ItemForRezervationsList> adapter = new MyCustomAdatperForTheList(getActivity().getBaseContext());
      /*  SelecionRegulations sr = new SelecionRegulations();


        sr.setNumberOfTable((String) spinnerNumberOfTable.getSelectedItem());
        if(spinnerNumberOfTable.getSelectedItemPosition()!= ((MySpinnerAdapter)adapter_number_of_table).getStartPosition()){
            sr.setNumberOfTable_selectied(true);
            sr.set
        }else{
            sr.setNumberOfTable_selectied(false);
        }


        sr.setPaidOrNotString((String)spinnerIsItPaid.getSelectedItem()) ;
        if(spinnerIsItPaid.getSelectedItemPosition()!= ((MySpinnerAdapter)adapter_isItPaid).getStartPosition()){
            sr.setPaidOrNot_selected(true) ;
        }else{
            sr.setPaidOrNot_selected(false) ;
        }

        sr.setKategory((String)spinnerKategory.getSelectedItem());
        if(spinnerKategory.getSelectedItemPosition()!= ((MySpinnerAdapter)adapter_kategory).getStartPosition()){
            sr.setKategory_selected(true) ;
        }else{
            sr.setKategory_selected(false) ;
        }


        sr.setUser((String)spinnerUser.getSelectedItem());
        if(spinnerUser.getSelectedItemPosition()!= ((MySpinnerAdapter)adapterUser).getStartPosition()){
            sr.setUser_selected(true); ;
        }else{
            sr.setUser_selected(false) ;
        }
*/

      //  ArrayList<Rezervation> myList = FireBase.getInstance().getRezervationsWithRegulationForAdmin(sr);
        //for(Rezervation rez:myList){
         //   adapter.addItem(new Fragment_List_Rezer_and_Selection.ItemForRezervationsList(rez));
        //}
        //lvDetail.setAdapter(adapter);

         mDatabase = FirebaseDatabase.getInstance().getReference();
         adapter = new MyCustomAdatperForTheListSR(getActivity(),mDatabase);
        lvDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        lvDetail.setAdapter(adapter);
        return mRoot;
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //updateListvView();
        SelecionRegulations sr = new SelecionRegulations();



        sr.setNumberOfTable((String) spinnerNumberOfTable.getSelectedItem());
        if(spinnerNumberOfTable.getSelectedItemPosition()!= ((MySpinnerAdapter)adapter_number_of_table).getStartPosition()){
            sr.setNumberOfTable_selectied(true);
            sr.setNumberOfTable(String.valueOf(spinnerNumberOfTable.getSelectedItemPosition()));
            has = true;
        }else{
            sr.setNumberOfTable_selectied(false);
        }


        sr.setPaidOrNotString((String)spinnerIsItPaid.getSelectedItem()) ;
        if(spinnerIsItPaid.getSelectedItemPosition()!= ((MySpinnerAdapter)adapter_isItPaid).getStartPosition()){
            sr.setPaidOrNot_selected(true) ;
            if (spinnerIsItPaid.getSelectedItemPosition()==1) {
                sr.setPaidOrNot(true);
            }else{
                sr.setPaidOrNot(false);
            }
            has = true;

        }else{
            sr.setPaidOrNot_selected(false) ;
        }

        sr.setKategory((String)spinnerKategory.getSelectedItem());
        if (adapter_kategory!=null) {
            if (spinnerKategory.getSelectedItemPosition() != ((MySpinnerAdapter) adapter_kategory).getStartPosition()) {
                sr.setKategory_selected(true);
                sr.setKategory((String) spinnerKategory.getSelectedItem());
                has = true;
            } else {
                sr.setKategory_selected(false);
            }
        }


        sr.setUser((String)spinnerUser.getSelectedItem());
        if (adapterUser!=null) {
            if (spinnerUser.getSelectedItemPosition() != ((MySpinnerAdapter) adapterUser).getStartPosition()) {
                sr.setUser_selected(true);
                sr.setUser((String) spinnerUser.getSelectedItem());
                has = true;
            } else {
                sr.setUser_selected(false);

            }
        }


        if (has) {
            UserData.getInstance().setSelectionRegulation(sr);
            if (adapter != null) {
                adapter.refreshList();
            }
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
/*

    public class ItemForRezervationsList extends HolderAdapterItem {
        Rezervation rezervation;

        public ItemForRezervationsList(Rezervation ld){

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
            return R.layout.rezervation;
        }

        @Override
        protected  IViewHolder createViewHolder() {
            return  new RezervationsViewHolder(this);
        }

        private class  RezervationsViewHolder implements IViewHolder<ItemForRezervationsList> {
            ItemForRezervationsList bla;
            TextView time, name_user, numberTable, price, itemsOrder, paidOrNot;
            Button edit, remove;


            public RezervationsViewHolder(ItemForRezervationsList bla) {
                this.bla = bla;
            }

            @Override
            public void findViews(View convertView) {
                time = (TextView)convertView.findViewById(R.id.time);
                name_user= (TextView)convertView.findViewById(R.id.name_user);
                numberTable= (TextView)convertView.findViewById(R.id.numberTable);
                price= (TextView)convertView.findViewById(R.id.price);
                itemsOrder = (TextView)convertView.findViewById(R.id.itemsOrder);
                paidOrNot = (TextView)convertView.findViewById(R.id.paidOrNot);
                edit  = (Button)convertView.findViewById(R.id.edit);
                remove = (Button)convertView.findViewById(R.id.remove);
            }
            @Override
            public void fillData(final ItemForRezervationsList adapterItem) {

                time.setVisibility(View.VISIBLE);
                time.setText(adapterItem.rezervation.getTime());
                name_user.setVisibility(View.VISIBLE);
                name_user.setText(adapterItem.rezervation.getNameType()+" : "+adapterItem.rezervation.getname_user());
                numberTable.setVisibility(View.VISIBLE);
                numberTable.setText("Broj stola je :" + adapterItem.rezervation.getnumberTable_string());
                price.setVisibility(View.VISIBLE);
                price.setText("Cena je :"+adapterItem.rezervation.getprice().toString());
                itemsOrder.setVisibility(View.VISIBLE);
                itemsOrder.setText(adapterItem.rezervation.getItemsOrdersInString());
                paidOrNot.setVisibility(View.VISIBLE);
                paidOrNot.setText(adapterItem.rezervation.getpaidOrNot_string());

                if (FireBase.getInstance().getUserInfo().getType().equals("Admin")){
                   edit.setVisibility(View.GONE);
                }else{
                    edit.setVisibility(View.VISIBLE);
               }
                remove.setVisibility(View.VISIBLE);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //otvori prozor fragment FreagmentAddOrder
                        if (getActivity().getClass().equals(ActivityMainList.getInstance().getClass())){
                            ((ActivityMainList)getActivity()).callFragmentAddOrderForEdit(rezervation.getId());
                        }else {
                            Intent intent2 = new Intent(getActivity().getApplicationContext(), ActivityDetails.class);
                            intent2.putExtra("name", "FreagmentAddOrder");
                            intent2.putExtra("rezervationId", Integer.toString(rezervation.getId()));
                            intent2.putExtra(ActivityDetails.CHOOSEFRAGM, ActivityDetails.addOrderForEdit);
                            intent2.putExtra("action", "onclick");
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getActivity().getApplicationContext().startActivity(intent2);
                        }
                    }
                });

                // remove.setOnClickListener(this);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FireBase.getInstance().removeRezer(rezervation.getId());
                        MyCustomAdatperForTheList<ItemForRezervationsList> adapter = new MyCustomAdatperForTheList(getActivity().getBaseContext());

                        updateListvView ();

                        /*ArrayList<Rezervation> myList = FireBase.getInstance().getRezervationsWithRegulationForAdmin(UserData.getInstance().getSelecionRegulation());
                        for(Rezervation rez:myList){
                            adapter.addItem(new ItemForRezervationsList(rez));
                        }
                        lvDetail.setAdapter(adapter);

                        //////////////////
                        lvDetail.invalidateViews();*/
/*

                    }
                });
            }
        }
    }*/


    private void updateListvView() {
       // MyCustomAdatperForTheList<ItemForRezervationsList> adapter = new MyCustomAdatperForTheList(getActivity().getBaseContext());
        SelecionRegulations sr = new SelecionRegulations();


        sr.setNumberOfTable((String) spinnerNumberOfTable.getSelectedItem());
        if(spinnerNumberOfTable.getSelectedItemPosition()!= ((MySpinnerAdapter)adapter_number_of_table).getStartPosition()){
            sr.setNumberOfTable_selectied(true);
        }else{
            sr.setNumberOfTable_selectied(false);
        }


        sr.setPaidOrNotString((String)spinnerIsItPaid.getSelectedItem()) ;
        if(spinnerIsItPaid.getSelectedItemPosition()!= ((MySpinnerAdapter)adapter_isItPaid).getStartPosition()){
            sr.setPaidOrNot_selected(true) ;
        }else{
            sr.setPaidOrNot_selected(false) ;
        }

        sr.setKategory((String)spinnerKategory.getSelectedItem());
        if (adapter_kategory!=null) {
            if (spinnerKategory.getSelectedItemPosition() != ((MySpinnerAdapter) adapter_kategory).getStartPosition()) {
                sr.setKategory_selected(true);
            } else {
                sr.setKategory_selected(false);
            }
        }


        sr.setUser((String)spinnerUser.getSelectedItem());
        if(spinnerUser.getSelectedItemPosition()!= ((MySpinnerAdapter)adapterUser).getStartPosition()){
            sr.setUser_selected(true); ;
        }else{
            sr.setUser_selected(false) ;
        }

        UserData.getInstance().setSelectionRegulation(sr);



       // ArrayList<Rezervation> myList = FireBase.getInstance().getRezervationsWithRegulationForAdmin(sr);
        //for(Rezervation rez:myList){
          //  adapter.addItem(new ItemForRezervationsList(rez));
       // }
        //lvDetail.setAdapter(adapter);
        //////////////////
        //lvDetail.invalidateViews();
    }
    private String[] napraviNiz(List<FoodMenuItem> mFoodMeniItems) {
        String [] retrunStringArray = new String[mFoodMeniItems.size()+1];
        retrunStringArray[0] = "kategorija";
        int i = 1;
        for(FoodMenuItem fmi: mFoodMeniItems){
            retrunStringArray[i] =  fmi.getFood();
            i++;
        }
        return  retrunStringArray;
    }

    private String[] napraviNizUser(List<UserInfo> mUserInfos) {
        String [] retrunStringArray = new String[mUserInfos.size()+1];
        retrunStringArray[0] = "korisnici";
        int i = 1;
        for(UserInfo ui: mUserInfos){
            retrunStringArray[i] =  ui.getUsername();
            i++;
        }
        return  retrunStringArray;
    }
}
