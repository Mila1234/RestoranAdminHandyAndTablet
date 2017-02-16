package com.example.marijaradisavljevic.restoranadminmarija.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityHost;
import com.example.marijaradisavljevic.restoranadminmarija.database.FoodMenuItem;
import com.example.marijaradisavljevic.restoranadminmarija.database.Order;
import com.example.marijaradisavljevic.restoranadminmarija.database.Rezervation;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;
import com.example.marijaradisavljevic.restoranadminmarija.spiner.MySpinnerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Created by marija on 22.5.16.
 */
public class Fragment_Add_New_Item_Rezerv extends Fragment {

    private static Fragment_Add_New_Item_Rezerv instance;
    private Spinner number_item_spiner;
    private Spinner menu_item_spiner;

    private Order order;
    private List<FoodMenuItem> mFoodMeniItems = new ArrayList<>();
    private List<String> mFoodMeniItemsIds = new ArrayList<>();

    private ProgressDialog mProgressDialog;
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(false);
            //mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_add_menu_item_rezerv, container, false);
       // getActivity().setTitle(R.string.nameOfApp);
        getActivity().setTitle("Naruzbina");
        ///////////////////////////////////////////////////////////////////////

        Button button_ok = (Button) mRoot.findViewById(R.id.ok_button);


        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                 String key = getActivity().getIntent().getExtras().getString("rezervationKeyFireBase");
                final String keyFireBase;
                if (key == null){
                    keyFireBase =  getArguments().getString("rezervationKeyFireBase");
                }else{
                    keyFireBase = key;
                }
              // FireBase.getInstance().addOrder(Integer.parseInt(keyFireBase), number_item_spiner.getSelectedItem().toString(), menu_item_spiner.getSelectedItem().toString());

                order = new Order();
                order.setNuberOrder(Integer.parseInt(number_item_spiner.getSelectedItem().toString()));

                String itemName = (String) menu_item_spiner.getSelectedItem();
                 if (itemName.equals("kategorija")){
                     Toast.makeText(getActivity(), "Morate odabrati stavku menija", Toast.LENGTH_LONG).show();
                     return;
                 }
                for(FoodMenuItem fmi :mFoodMeniItems){
                   if( fmi.getFood().equals(itemName) ){
                        order.setOrder(fmi);
                    }
                }

                if (order.getOrder()==null){return;}

                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                if (order!=null) {
                    showProgressDialog();
                    mDatabase.child("listaRezervations").child(keyFireBase).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    try {
                                        Rezervation rezervation = dataSnapshot.getValue(Rezervation.class);

                                        if (rezervation != null) {
                                            rezervation.getOrders().add(order);
                                            mDatabase.child("listaRezervations").child(keyFireBase).setValue(rezervation);
                                        }

                                        hideProgressDialog();
                                        Toast.makeText(getActivity(), " Snimljeno ", Toast.LENGTH_LONG).show();
                                        ((ActivityHost)getActivity()).callAddOrder("onclick",keyFireBase);


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                }
             /*   Intent intent2 = new Intent(getActivity().getApplicationContext(), ActivityHost.class);
                intent2.putExtra("name", "FreagmentAddOrder");
                intent2.putExtra("rezervationId", rezIdString);
                intent2.putExtra("action", "onclick");
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent2);*/



            }
        });

        Button cancel_button = (Button) mRoot.findViewById(R.id.cancel_button);


        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Odustali ste ", Toast.LENGTH_LONG).show();

                Bundle extras ;
                extras =  getArguments();

                String keyFireBase = extras.getString("rezervationKeyFireBase");
              /*  Intent intent2 = new Intent(getActivity().getApplicationContext(), ActivityHost.class);
                intent2.putExtra("name", "FreagmentAddOrder");
                intent2.putExtra("rezervationId", rezIdString);
                intent2.putExtra("action", "onclick");
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent2);*/

                ((ActivityHost)getActivity()).callAddOrder("onclick",keyFireBase);

            }
        });


        menu_item_spiner = (Spinner)  mRoot.findViewById(R.id.menu_item_spiner);
        //String[] value = getResources().getStringArray(R.array.kategory_array);


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


                    ArrayAdapter<String> menu_item_spiner_adapter = new MySpinnerAdapter(false,getActivity().getBaseContext(),
                            android.R.layout.simple_spinner_item, napraviNiz (mFoodMeniItems));
                    menu_item_spiner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    menu_item_spiner.setAdapter(menu_item_spiner_adapter);
                    menu_item_spiner.setSelection(0);

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




        number_item_spiner = (Spinner)  mRoot.findViewById(R.id.number_item_spiner);
        // String [] value = getResources().getStringArray(R.array.number_item_spiner);
        ArrayAdapter<String> number_item_spiner_adapter = new MySpinnerAdapter(false,getActivity(),
                android.R.layout.simple_spinner_item, FireBase.getInstance().getNumberItems());
        // Specify the layout to use when the list of choices appears
        number_item_spiner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        number_item_spiner.setAdapter(number_item_spiner_adapter);
        number_item_spiner.setSelection(0);

        return mRoot;
    }




    public static Fragment_Add_New_Item_Rezerv getInstance() {


        if(instance == null){
            return new Fragment_Add_New_Item_Rezerv();
        }else return instance;

    }
/*
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
       // menu.findItem(R.id.action_logout).setVisible(true);
       // menu.findItem(R.id.action_user_info).setVisible(true);
        //menu.findItem(R.id.action_add).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }*/
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
