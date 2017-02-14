package com.example.marijaradisavljevic.restoranadminmarija.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.example.marijaradisavljevic.restoranadminmarija.database.UserInfo;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;
import com.example.marijaradisavljevic.restoranadminmarija.spiner.MySpinnerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by marija on 24.1.17.
 */

public class Fragment_Add_Menu_Item extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";


    private EditText newItemName, price;
    private Button okButton;

    private Spinner kategory_spinner;
     ArrayAdapter<String> adapter_kategory;
    private Bundle extras;
    private List<FoodMenuItem> mFoodMeniItems = new ArrayList<>();
    private List<String> mFoodMeniItemsIds = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_add_menu_item,container, false);


        getActivity().setTitle("Stavka menija");
        kategory_spinner = (Spinner)mRoot.findViewById(R.id.kategorySpiner);
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
                   adapter_kategory = new MySpinnerAdapter(false,getActivity().getBaseContext(),
                            android.R.layout.simple_spinner_item,  napraviNiz (mFoodMeniItems));
                    kategory_spinner.setAdapter(adapter_kategory);

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

        adapter_kategory = new MySpinnerAdapter(false,getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item,  napraviNiz (mFoodMeniItems));

        // Specify the layout to use when the list of choices appears
        adapter_kategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        kategory_spinner.setAdapter(adapter_kategory);
        kategory_spinner.setSelection(((MySpinnerAdapter)adapter_kategory).getStartPosition());
        // kategory_spinner.setOnItemSelectedListener(this);


        newItemName = (EditText) mRoot.findViewById(R.id.newItme);
        price = (EditText) mRoot.findViewById(R.id.price);
        okButton = (Button)mRoot.findViewById(R.id.ok_button);

        extras = getActivity().getIntent().getExtras();

        if ( (null != extras) && (!extras.isEmpty())) {//edit user
            //String foodItemId = extras.getString("foodItemId");
            String foodItemId = extras.getString("foodItemId");
            String key = extras.getString("keyFireBase");


            mDatabase.child("foodMenu").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FoodMenuItem fmi = dataSnapshot.getValue(FoodMenuItem.class);
                    newItemName.setText(fmi.getFood());
                    price.setText(fmi.getPrice().toString());

                    int position = adapter_kategory.getPosition(fmi.getNadstavka().getFood());
                    kategory_spinner.setSelection(position);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

          //  FoodMenuItem fmi = FireBase.getInstance().getFootMenuItem(foodItemId);


        }else        if ( (null != getArguments()) && (!getArguments().isEmpty())) {//edit user
            //String foodItemId = extras.getString("foodItemId");
            String foodItemId = getArguments().getString("foodItemId");
            String key = getArguments().getString("keyFireBase");


            mDatabase.child("foodMenu").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FoodMenuItem fmi = dataSnapshot.getValue(FoodMenuItem.class);
                    newItemName.setText(fmi.getFood());
                    price.setText(fmi.getPrice().toString());

                    int position = adapter_kategory.getPosition(fmi.getNadstavka().getFood());
                    kategory_spinner.setSelection(position);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            //  FoodMenuItem fmi = FireBase.getInstance().getFootMenuItem(foodItemId);


        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kategoryString = (String) kategory_spinner.getSelectedItem();
                int index = kategory_spinner.getSelectedItemPosition();
               FoodMenuItem nadstavka =  mFoodMeniItems.get(index);
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
                    String key = extras.getString("keyFireBase");
                    //FireBase.getInstance().updateFoodMenuItem( foodItemId , kategoryString , nameString , priceString );
                    FoodMenuItem fmi = new FoodMenuItem(nadstavka,nameString,Integer.parseInt(priceString),Integer.parseInt(foodItemId));
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("foodMenu").child(key).setValue(fmi);
                }else if ( (null != getArguments()) && (!getArguments().isEmpty())) {//edit user
                    String foodItemId =  getArguments().getString("foodItemId");
                    String key = getArguments().getString("keyFireBase");
                    //FireBase.getInstance().updateFoodMenuItem( foodItemId , kategoryString , nameString , priceString );
                    FoodMenuItem fmi = new FoodMenuItem(nadstavka,nameString,Integer.parseInt(priceString),Integer.parseInt(foodItemId));
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("foodMenu").child(key).setValue(fmi);

                }
                else {
                    //FireBase.getInstance().makeNewFoodItem( kategoryString , nameString , priceString );
                    FoodMenuItem fmi = new FoodMenuItem(nadstavka,nameString,Integer.parseInt(priceString));

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    String key = mDatabase.child("foodMenu").push().getKey();
                    mDatabase.child("foodMenu").child(key).setValue(fmi);
                }

                Toast.makeText(getActivity().getApplicationContext(), " Snimljeno ", Toast.LENGTH_LONG).show();


                Intent intent = new Intent(getActivity().getApplicationContext(), ActivityMainList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);


            }
        });
        return mRoot;
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
}
