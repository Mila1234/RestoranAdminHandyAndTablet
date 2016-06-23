package com.example.marijaradisavljevic.restoranadminmarija.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.example.marijaradisavljevic.restoranadminmarija.adapters.HolderAdapterItem;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.MyCustomAdatperForTheList;
import com.example.marijaradisavljevic.restoranadminmarija.data.UserData;
import com.example.marijaradisavljevic.restoranadminmarija.database.Rezervation;
import com.example.marijaradisavljevic.restoranadminmarija.database.SelecionRegulations;
import com.example.marijaradisavljevic.restoranadminmarija.servis.Servis;
import com.example.marijaradisavljevic.restoranadminmarija.spiner.MySpinnerAdapter;

import java.util.ArrayList;

/**
 * Created by marija.radisavljevic on 6/9/2016.
 */
public class ActivityListRezer  extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    ListView lvDetail;
    private Spinner number_of_table ;
    private Spinner isItPaid ;
    private Spinner kategory;
    private Spinner user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_list_rezervations_layout);

        lvDetail = (ListView) findViewById(R.id.list_reservations);

        MyCustomAdatperForTheList<ItemForRezervationsList> adapter = new MyCustomAdatperForTheList(getApplicationContext());
        SelecionRegulations sr = new SelecionRegulations();

        /*sr.setAll(all) ;
        sr.setNumberOfTable(numberOfTable);
        sr.setNumberOfTable_selectied(numberOfTable_selectied);
        sr.setPaidOrNot(paidOrNot) ;
        sr.setPaidOrNot_selected(paidOrNot_selected) ;
        sr.setKategory(kategory);
        sr.setKategory_selected(kategory_selected) ;
        sr.setUser(user);*/
        ArrayList<Rezervation> myList = Servis.getInstance().getRezervationsWithRegulation(sr);
        for(Rezervation rez:myList){
            adapter.addItem(new ItemForRezervationsList(rez));
        }
        lvDetail.setAdapter(adapter);

        number_of_table = (Spinner)  findViewById(R.id.numbreOfTable_spinner);
        isItPaid = (Spinner)  findViewById(R.id.isItPaid_spinner);
        kategory = (Spinner)  findViewById(R.id.kategory_spinner);
        user = (Spinner) findViewById(R.id.user_spiner);


        //String[] value = getResources().getStringArray(R.array.numbers);
        ArrayAdapter<String> adapter_number_of_table = new MySpinnerAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, Servis.getInstance().stringListofTables());



        // Specify the layout to use when the list of choices appears
        adapter_number_of_table.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        number_of_table.setAdapter(adapter_number_of_table);
        number_of_table.setSelection(0);
        number_of_table.setOnItemSelectedListener(this);


        String[] value = getResources().getStringArray(R.array.paidNotpaid);
        ArrayAdapter<String>  adapter_isItPaid = new MySpinnerAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item ,value);

        // Specify the layout to use when the list of choices appears
        adapter_isItPaid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        isItPaid.setAdapter(adapter_isItPaid);
        isItPaid.setSelection(0);
        isItPaid.setOnItemSelectedListener(this);

        // value = getResources().getStringArray(R.array.kategory_array);
        ArrayAdapter<String> adapter_kategory = new MySpinnerAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item,Servis.getInstance().stringListofFoodItems() );

        // Specify the layout to use when the list of choices appears
        adapter_kategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        kategory.setAdapter(adapter_kategory);
        kategory.setSelection(0);
        kategory.setOnItemSelectedListener(this);

        // value = getResources().getStringArray(R.array.kategory_array);
        ArrayAdapter<String> adapterUser = new MySpinnerAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item,Servis.getInstance().stringlistUserNames() );

        // Specify the layout to use when the list of choices appears
        adapterUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        user.setAdapter(adapterUser);
        user.setSelection(0);
        user.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getId()) {
            case R.id.isItPaid_spinner:
                if(position == 0 ){

                }else {

                }
                break;
            case R.id.kategory_spinner:
                if(position == 0 ){

                }else {

                }
                break;
            case R.id.numbreOfTable_spinner:
                if(position == 0 ){

                }else {

                }
                break;
            case R.id.user_spiner:
                if(position == 0 ){

                }else {

                }
                break;
            default:
                break;
        }
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


                edit.setVisibility(View.VISIBLE);
                remove.setVisibility(View.VISIBLE);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //otvori prozor fragment FreagmentAddOrder
                        Intent intent2 = new Intent(getApplicationContext(), ActivityListRezer.class);
                        intent2.putExtra("name", "FreagmentAddOrder");
                        intent2.putExtra("rezervationId", Integer.toString(rezervation.getId()));

                        intent2.putExtra("action", "onclick");
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent2);

                    }
                });

                // remove.setOnClickListener(this);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Servis.getInstance().removeRezer(rezervation.getId());
                        MyCustomAdatperForTheList<ItemForRezervationsList> adapter = new MyCustomAdatperForTheList(getApplicationContext());
                        ArrayList<Rezervation> myList = Servis.getInstance().getRezervationsWithRegulation(UserData.getInstance().getSelecionRegulation());
                        for(Rezervation rez:myList){
                            adapter.addItem(new ItemForRezervationsList(rez));
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
