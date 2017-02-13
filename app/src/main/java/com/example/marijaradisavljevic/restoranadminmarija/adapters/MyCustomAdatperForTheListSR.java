package com.example.marijaradisavljevic.restoranadminmarija.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityDetails;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityHost;
import com.example.marijaradisavljevic.restoranadminmarija.data.SelecionRegulations;
import com.example.marijaradisavljevic.restoranadminmarija.data.UserData;
import com.example.marijaradisavljevic.restoranadminmarija.database.Order;
import com.example.marijaradisavljevic.restoranadminmarija.database.Rezervation;
import com.example.marijaradisavljevic.restoranadminmarija.database.UserInfo;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.FragmentListReservations;
import com.example.marijaradisavljevic.restoranadminmarija.servis.FireBase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static android.content.ContentValues.TAG;
/*
 * Created by marija on 12.2.17.
 */
public class MyCustomAdatperForTheListSR extends MyCustomAdatperForTheList<MyCustomAdatperForTheListSR.ItemForRezervationsList> {

private Context context;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mDatabaseReference;
    private List<Rezervation> mRezertavions = new ArrayList<>();
    private List<String> mRezertavionsIds = new ArrayList<>();

    public MyCustomAdatperForTheListSR(Context context,DatabaseReference ref) {
        super(context);
        mDatabaseReference = ref;
        this.context = context;
        // Create child event listener
        // [START child_event_listener_recycler]
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                //Rezervation currRezervation = dataSnapshot.getValue(Rezervation.class);
                HashMap<String, Object> rezHM = (HashMap<String, Object>) dataSnapshot.getValue();
               // Collection<Object> colection = map.values();
               //Iterator iter =  colection.iterator();
               // Object rez = iter.next();
               // HashMap<String,Object> rezHM = (HashMap<String, Object>) rez;
                try {
                    Long b = (Long)rezHM.get("numberTable");
                }catch (Exception e){
                    e.printStackTrace();
                }
                Rezervation currRezervation = new Rezervation((String)rezHM.get("time"),
                        (String)rezHM.get("name_user"),
                        (Long) rezHM.get("numberTable"),
                        (Boolean) rezHM.get("paidOrNot"),
                        (String)rezHM.get("password"),
                        (String)  rezHM.get("username"),
                        (String)rezHM.get("nameType"),
                        (Long)rezHM.get("id")  );

                ArrayList<HashMap<String, Object>> orders = (ArrayList<HashMap<String, Object>>) rezHM.get("orders");
                currRezervation.ordersFormArrayList(orders);

                SelecionRegulations selecionRegulation = UserData.getInstance().getSelecionRegulation();

                Boolean has = false;
                if(selecionRegulation.isKategory_selected()){
                    for (Order currorder :currRezervation.getOrders()){
                        if(currorder.getOrder().getFood().equals(selecionRegulation.getKategory())){
                           has = true;
                            break;
                        }
                    }
                }else  if(selecionRegulation.isNumberOfTable_selectied()){
                    if(currRezervation.getnumberTable().toString().equals(selecionRegulation.getNumberOfTable())  ){
                        has = true;
                    }

                }else{

                } if(selecionRegulation.isPaidOrNot_selected() && selecionRegulation.isPaidOrNot()){
                    //ubaci sve koji su placeni
                    if(currRezervation.isPaidOrNot() == true ){
                        has = true;
                    }
                }else {
                    has = true;
                }

                // [START_EXCLUDE]
                // Update RecyclerView

                if (has) {
                    mRezertavionsIds.add(dataSnapshot.getKey());
                    mRezertavions.add(currRezervation);
                    addItemBla(new ItemForRezervationsList(currRezervation));
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
        ref.child("listaRezervations").addChildEventListener(childEventListener);
        // [END child_event_listener_recycler]

        // Store reference to listener so it can be removed on app stop
        mChildEventListener = childEventListener;

    }



   public void  addItemBla(ItemForRezervationsList itme){
       this.addItem(itme);
       notifyDataSetChanged();
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

                numberTable.setVisibility(View.VISIBLE);
                numberTable.setText("Broj stola je : " + adapterItem.rezervation.getnumberTable_string());
                price.setVisibility(View.VISIBLE);
                price.setText("Cena je :   "+adapterItem.rezervation.getprice().toString());
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
                        Intent intent2 = new Intent(context.getApplicationContext(), ActivityHost.class);
                        intent2.putExtra("name", "FreagmentAddOrder");
                        intent2.putExtra("rezervationId", Integer.toString(rezervation.getId()));
                        intent2.putExtra(ActivityDetails.CHOOSEFRAGM , ActivityDetails.ADD_ITEM_MENU) ;
                        intent2.putExtra("action", "onclick");
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.getApplicationContext().startActivity(intent2);

                    }
                });

                // remove.setOnClickListener(this);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // AdapterDB.getInstance().deleteRezervation(rezervation.getId());


                        String key = mRezertavionsIds.get(mRezertavions.indexOf(rezervation));
                        mDatabaseReference.child("listaRezervations").child(key).removeValue();

                        deleteItemWithIndex(mRezertavions.indexOf(rezervation));

                        /*
                        FireBase.getInstance().removeRezer(rezervation.getId());
                        MyCustomAdatperForTheList<ItemForRezervationsList> adapter = new MyCustomAdatperForTheList(context));
                        ArrayList<Rezervation> myList = FireBase.getInstance().getRezervationsWithRegulation(UserData.getInstance().getSelecionRegulation());
                        for(Rezervation rez:myList){
                            adapter.addItem(new ItemForRezervationsList(rez));
                        }
                        lvDetail.setAdapter(adapter);
*/
                        //////////////////

                     ///   FragmentListReservations.this.lvDetail.invalidateViews();


                    }
                });
            }
        }
    }


}
