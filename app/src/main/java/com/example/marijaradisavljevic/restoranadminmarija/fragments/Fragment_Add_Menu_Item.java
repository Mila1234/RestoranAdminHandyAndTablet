package com.example.marijaradisavljevic.restoranadminmarija.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityMainList;
import com.example.marijaradisavljevic.restoranadminmarija.database.FoodMenuItem;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;
import com.example.marijaradisavljevic.restoranadminmarija.spiner.MySpinnerAdapter;

/**
 * Created by marija on 24.1.17.
 */

public class Fragment_Add_Menu_Item extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";


    private EditText newItemName, price;
    private Button okButton;

    private Spinner kategory_spinner;

    private Bundle extras;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_add_menu_item,container, false);


        getActivity().setTitle("Stavka menija");
        kategory_spinner = (Spinner)mRoot.findViewById(R.id.kategorySpiner);
        // value = getResources().getStringArray(R.array.kategory_array);
        ArrayAdapter<String> adapter_kategory = new MySpinnerAdapter(false,getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item, FireBase.getInstance().stringListofFoodItems() );

        // Specify the layout to use when the list of choices appears
        adapter_kategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        kategory_spinner.setAdapter(adapter_kategory);
        kategory_spinner.setSelection(((MySpinnerAdapter)adapter_kategory).getStartPosition());
        // kategory_spinner.setOnItemSelectedListener(this);


        newItemName = (EditText) mRoot.findViewById(R.id.newItme);
        price = (EditText) mRoot.findViewById(R.id.price);
        okButton = (Button)mRoot.findViewById(R.id.ok_button);

        extras = getArguments();

        if ( (null != extras) && (!extras.isEmpty())) {//edit user
            //String foodItemId = extras.getString("foodItemId");
            String foodItemId = extras.getString("foodItemId");
            FoodMenuItem fmi = FireBase.getInstance().getFootMenuItem(foodItemId);
            newItemName.setText(fmi.getFood());
            price.setText(fmi.getPrice().toString());

            int position = adapter_kategory.getPosition(fmi.getNadstavka().getFood());
            kategory_spinner.setSelection(position);

        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kategoryString = (String) kategory_spinner.getSelectedItem();
                String nameString = newItemName.getText().toString();
                String priceString = price.getText().toString();

                if(kategoryString.length()==0 || nameString.length()==0 || priceString.length()==0){
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.obavezniparametri), Toast.LENGTH_LONG).show();
                    return;
                }
                if (!(priceString.matches("[0-9]+") && (priceString.length() >1 ))) {
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.loseunetparametar), Toast.LENGTH_LONG).show();
                    return;
                }

                if (( extras!= null) && (!extras.isEmpty())) {//edit user
                    String foodItemId =  extras.getString("foodItemId");
                    FireBase.getInstance().updateFoodMenuItem( foodItemId , kategoryString , nameString , priceString );
                }else {
                    FireBase.getInstance().makeNewFoodItem( kategoryString , nameString , priceString );
                }

                Toast.makeText(getActivity().getApplicationContext(), " Snimljeno ", Toast.LENGTH_LONG).show();


                Intent intent = new Intent(getActivity().getApplicationContext(), ActivityMainList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);


            }
        });
        return mRoot;
    }
}
