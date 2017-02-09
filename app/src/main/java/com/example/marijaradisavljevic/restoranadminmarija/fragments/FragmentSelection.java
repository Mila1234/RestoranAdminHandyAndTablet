package com.example.marijaradisavljevic.restoranadminmarija.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.Spinner;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.data.UserData;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;
import com.example.marijaradisavljevic.restoranadminmarija.spiner.MySpinnerAdapter;


/**
 * Created by marija.radisavljevic on 5/13/2016.
 */
public class FragmentSelection extends Fragment implements AdapterView.OnItemSelectedListener {

    private static Fragment instance ;
    private Spinner number_of_table ;
    private Spinner isItPaid ;
    private Spinner kategory;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Restoran");
        View mRoot = inflater.inflate(R.layout.fragment_selector_layout,container,false);
        number_of_table = (Spinner)  mRoot.findViewById(R.id.numbreOfTable_spinner);
        isItPaid = (Spinner)  mRoot.findViewById(R.id.isItPaid_spinner);
        kategory = (Spinner)  mRoot.findViewById(R.id.kategory_spinner);

        //String[] value = getResources().getStringArray(R.array.numbers);
        ArrayAdapter<String> adapter_number_of_table = new MySpinnerAdapter(false,getActivity(),
                android.R.layout.simple_spinner_item, FireBase.getInstance().stringListofTables());



        // Specify the layout to use when the list of choices appears
        adapter_number_of_table.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        number_of_table.setAdapter(adapter_number_of_table);
        number_of_table.setSelection(0);
        number_of_table.setOnItemSelectedListener(this);


        String[] value = getResources().getStringArray(R.array.paidNotpaid);
        ArrayAdapter<String>  adapter_isItPaid = new MySpinnerAdapter(false,getActivity(),
                 android.R.layout.simple_spinner_item ,value);

        // Specify the layout to use when the list of choices appears
        adapter_isItPaid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        isItPaid.setAdapter(adapter_isItPaid);
        isItPaid.setSelection(0);
        isItPaid.setOnItemSelectedListener(this);

        // value = getResources().getStringArray(R.array.kategory_array);
        ArrayAdapter<String> adapter_kategory = new MySpinnerAdapter(false,getActivity(),
                android.R.layout.simple_spinner_item, FireBase.getInstance().stringListofFoodItems() );

        // Specify the layout to use when the list of choices appears
        adapter_kategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        kategory.setAdapter(adapter_kategory);
        kategory.setSelection(0);
        kategory.setOnItemSelectedListener(this);

       final CheckedTextView all =(CheckedTextView) mRoot.findViewById(R.id.all_checkedTextView);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(all.isChecked()){
                    all.setChecked(false);
                    UserData.getInstance().setAll(false);
                }else{
                    all.setChecked(true);
                    UserData.getInstance().setAll(true);
                }
            }
        });

        all.setChecked(true);
        UserData.getInstance().setAll(true);

        return mRoot;
    }

    public static Fragment getInstance() {


        if(instance == null){
            return new FragmentListReservations();
        }else return instance;

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_logout).setVisible(true);
        menu.findItem(R.id.action_user_info).setVisible(true);
        menu.findItem(R.id.action_add).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getId()) {
            case R.id.isItPaid_spinner:
                if(position == 0 ){
                    UserData.getInstance().setPaidOrNot_selected(false);
                }else {
                    UserData.getInstance().setPaidOrNot_selected(true);
                    UserData.getInstance().setPaidOrNot(Boolean.parseBoolean(isItPaid.getSelectedItem().toString()));
                }
                break;
            case R.id.kategory_spinner:
                if(position == 0 ){
                    UserData.getInstance().setKategory_selected(false);
                }else {
                    UserData.getInstance().setKategory_selected(true);
                    UserData.getInstance().setKategory(kategory.getSelectedItem().toString());
                }
                break;
            case R.id.numbreOfTable_spinner:
                if(position == 0 ){
                    UserData.getInstance().setNumberOfTable_selectied(false);
                }else {
                    UserData.getInstance().setNumberOfTable_selectied(true);
                    UserData.getInstance().setNumberOfTable(number_of_table.getSelectedItem().toString());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
