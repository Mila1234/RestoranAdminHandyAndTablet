package com.example.marijaradisavljevic.restoranadminmarija.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.marijaradisavljevic.restoranadminmarija.database.Rezervation;
import com.example.marijaradisavljevic.restoranadminmarija.data.SelecionRegulations;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;
import com.example.marijaradisavljevic.restoranadminmarija.spiner.MySpinnerAdapter;

import java.util.ArrayList;

/**
 * Created by marija on 24.1.17.
 */

public class Fragment_List_Rezer_and_Selection extends Fragment implements  AdapterView.OnItemSelectedListener {
    public static final String ARG_ITEM_ID = "item_id";

    ListView lvDetail;
    private Spinner spinnerNumberOfTable;
    private Spinner spinnerIsItPaid;
    private Spinner spinnerKategory;
    private Spinner spinnerUser;


    private ArrayAdapter<String> adapter_number_of_table;
    private ArrayAdapter<String>  adapter_isItPaid ;
    private ArrayAdapter<String> adapter_kategory;
    private ArrayAdapter<String> adapterUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_list_rezervations_selection_layout,container, false);
        getActivity().setTitle("Restoran");
        lvDetail = (ListView) mRoot.findViewById(R.id.list_reservations);



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
        adapter_isItPaid = new MySpinnerAdapter(true,getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item ,value);

        // Specify the layout to use when the list of choices appears
        adapter_isItPaid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerIsItPaid.setAdapter(adapter_isItPaid);
        spinnerIsItPaid.setSelection(((MySpinnerAdapter)adapter_isItPaid).getStartPosition());
        spinnerIsItPaid.setOnItemSelectedListener(this);

        // value = getResources().getStringArray(R.array.kategory_array);
        adapter_kategory = new MySpinnerAdapter(true,getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item, FireBase.getInstance().stringListofFoodItems() );

        // Specify the layout to use when the list of choices appears
        adapter_kategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerKategory.setAdapter(adapter_kategory);
        spinnerKategory.setSelection(((MySpinnerAdapter)adapter_kategory).getStartPosition());
        spinnerKategory.setOnItemSelectedListener(this);

        // value = getResources().getStringArray(R.array.kategory_array);
        adapterUser = new MySpinnerAdapter(true,getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item, FireBase.getInstance().stringlistUserNames() );

        // Specify the layout to use when the list of choices appears
        adapterUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerUser.setAdapter(adapterUser);
        spinnerUser.setSelection(((MySpinnerAdapter)adapterUser).getStartPosition());
        spinnerUser.setOnItemSelectedListener(this);

//////////list view
        MyCustomAdatperForTheList<Fragment_List_Rezer_and_Selection.ItemForRezervationsList> adapter = new MyCustomAdatperForTheList(getActivity().getBaseContext());
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


        ArrayList<Rezervation> myList = FireBase.getInstance().getRezervationsWithRegulationForAdmin(sr);
        for(Rezervation rez:myList){
            adapter.addItem(new Fragment_List_Rezer_and_Selection.ItemForRezervationsList(rez));
        }
        lvDetail.setAdapter(adapter);

        return mRoot;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateListvView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


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
                time.setText(adapterItem.rezervation.gettime());
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


                    }
                });
            }
        }
    }

    private void updateListvView() {
        MyCustomAdatperForTheList<ItemForRezervationsList> adapter = new MyCustomAdatperForTheList(getActivity().getBaseContext());
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


        ArrayList<Rezervation> myList = FireBase.getInstance().getRezervationsWithRegulationForAdmin(sr);
        for(Rezervation rez:myList){
            adapter.addItem(new ItemForRezervationsList(rez));
        }
        lvDetail.setAdapter(adapter);
        //////////////////
        lvDetail.invalidateViews();
    }
}
