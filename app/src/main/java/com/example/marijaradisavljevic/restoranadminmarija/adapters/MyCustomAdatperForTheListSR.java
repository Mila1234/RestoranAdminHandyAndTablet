package com.example.marijaradisavljevic.restoranadminmarija.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import static com.example.marijaradisavljevic.restoranadminmarija.R.layout.rezervation;

/*
 * Created by marija on 12.2.17.
 */

public class MyCustomAdatperForTheListSR extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

private Context context;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mDatabaseReference;
    private List<Rezervation> mRezertavions = new ArrayList<>();
    private List<String> mRezertavionsIds = new ArrayList<>();

    public MyCustomAdatperForTheListSR(Context context,DatabaseReference ref) {

        mDatabaseReference = ref;
        this.context = context;
        refreshList();

    }
    public void cleanupListener() {
mRezertavions.clear();

       mRezertavionsIds.clear();
       // notifyItemRangeRemoved(0,mRezertavions.size()-1);
        if (mChildEventListener != null) {
            mDatabaseReference.removeEventListener(mChildEventListener);
        }
    }


    public void refreshList(){
        cleanupListener();

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

currRezervation.setKey(dataSnapshot.getKey());

                ArrayList<HashMap<String, Object>> orders = (ArrayList<HashMap<String, Object>>) rezHM.get("orders");
                currRezervation.ordersFormArrayList(orders);

                SelecionRegulations selecionRegulation = UserData.getInstance().getSelecionRegulation();

                int has = 0;
                if(selecionRegulation.isKategory_selected()) {
                    for (Order currorder : currRezervation.getOrders()) {
                        if (currorder.getOrder().getFood().equals(selecionRegulation.getKategory())) {
                            if (has == 0){
                                has = 1;
                            }else{
                                has = 99;
                            }
                            break;
                        }
                    }

                }

                if(selecionRegulation.isNumberOfTable_selectied()){
                    if(currRezervation.getnumberTable().toString().equals(selecionRegulation.getNumberOfTable())  ){
                        if (has == 1 || has ==0){
                            has = 2;
                        }else{
                            has = 99;
                        }
                    }

                }

                if(selecionRegulation.isPaidOrNot_selected() && selecionRegulation.isPaidOrNot()) {
                    //ubaci sve koji su placeni
                    if (currRezervation.isPaidOrNot() == true) {
                        if (has == 2 || has == 1 || has ==0){
                            has = 3;
                        }else{
                            has = 99;
                        }
                    }
                }

                if (selecionRegulation.isUser_selected() ){
                    if (currRezervation.getUsername().equals(selecionRegulation.getUser())){
                        if (has == 3 || has == 2 || has == 1 || has ==0){
                            has = 4;//to je uspesno stanje
                        }else{
                            has = 99;
                        }
                    }
                }

                if (selecionRegulation.isAll()){
                    has = 5;//to je uspesno stanje
                }

                if (FireBase.getInstance().getUserInfo().getType().equals("Admin")){
                    if (!selecionRegulation.isKategory_selected() && !selecionRegulation.isPaidOrNot_selected() && !selecionRegulation.isUser_selected() && !selecionRegulation.isNumberOfTable_selectied()){
                        has = 5;
                    }
                }
                // [START_EXCLUDE]
                // Update RecyclerView

                if (has <= 5 && has > 0) {
                    mRezertavionsIds.add(dataSnapshot.getKey());
                    mRezertavions.add(currRezervation);
                   // notifyItemInserted(mRezertavions.size() - 1);
                    notifyDataSetChanged();
                    // addItemBla(new ItemForRezervationsList(currRezervation));
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
                //  mRezertavions.remove(position);
                /// mRezertavionsIds.remove(position);
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
        mDatabaseReference.child("listaRezervations").addChildEventListener(childEventListener);
        // [END child_event_listener_recycler]

        // Store reference to listener so it can be removed on app stop
        mChildEventListener = childEventListener;


    }

          private static  class  RezervationsViewHolder extends RecyclerView.ViewHolder {

            TextView time, name_user, numberTable, price, itemsOrder, paidOrNot,id;
            Button edit, remove;

            public RezervationsViewHolder(View convertView) {
                super(convertView);
                time = (TextView) convertView.findViewById(R.id.time);
                name_user = (TextView)convertView.findViewById(R.id.name_user);
                numberTable = (TextView) convertView.findViewById(R.id.numberTable);
                price = (TextView) convertView.findViewById(R.id.price);
                itemsOrder = (TextView) convertView.findViewById(R.id.itemsOrder);
                paidOrNot = (TextView) convertView.findViewById(R.id.paidOrNot);
                edit = (Button) convertView.findViewById(R.id.edit);
                remove = (Button) convertView.findViewById(R.id.remove);
                id = (TextView) convertView.findViewById(R.id.id);
            }

        }


    @Override
    public RezervationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rezervation, parent, false);
        return new RezervationsViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        final Rezervation rezervation = mRezertavions.get(position);
        //holder.authorView.setText(comment.author);
      //  holder.bodyView.setText(comment.text);
        final RezervationsViewHolder holder = (RezervationsViewHolder)h;

        holder.time.setVisibility(View.VISIBLE);
        holder.time.setText(rezervation.getTime());
        holder.name_user.setText(rezervation.getUsername());
        holder.numberTable.setVisibility(View.VISIBLE);
        holder.numberTable.setText("Broj stola je : " + rezervation.getnumberTable_string());
        holder.price.setVisibility(View.VISIBLE);
        holder.price.setText("Cena je :   "+rezervation.getprice().toString());
        holder.itemsOrder.setVisibility(View.VISIBLE);
        holder.itemsOrder.setText(rezervation.getItemsOrdersInString());
        holder.paidOrNot.setVisibility(View.VISIBLE);
        holder.paidOrNot.setText(rezervation.getpaidOrNot_string());
        holder.id.setText(String.valueOf(rezervation.getId()));

        holder.edit.setVisibility(View.VISIBLE);
        holder.remove.setVisibility(View.VISIBLE);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //otvori prozor fragment FreagmentAddOrder
                Intent intent2 = new Intent(context.getApplicationContext(), ActivityHost.class);
                intent2.putExtra("name", "FreagmentAddOrder");
                Rezervation foud = null;
                for(Rezervation ui :mRezertavions){
                    String strinf = holder.id.getText().toString();
                    if(ui.getId() == Integer.parseInt(strinf)){
                        foud = ui;
                        break;
                    }
                }

                int index = mRezertavions.indexOf(foud);
                Rezervation rez = mRezertavions.get(index);
                intent2.putExtra("rezervationKeyFireBase", rez.getKey());
                intent2.putExtra(ActivityDetails.CHOOSEFRAGM , ActivityDetails.ADD_ITEM_MENU) ;
                intent2.putExtra("action", "onclick");
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent2);

            }
        });

        // remove.setOnClickListener(this);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AdapterDB.getInstance().deleteRezervation(rezervation.getId());

                Rezervation foud = null;
                for(Rezervation ui :mRezertavions){
                    String strinf = holder.id.getText().toString();
                    if(ui.getId() == Integer.parseInt(strinf)){
                        foud = ui;
                        break;
                    }
                }

                int index = mRezertavions.indexOf(foud);
                String key = mRezertavionsIds.get(index);
                mDatabaseReference.child("listaRezervations").child(key).removeValue();
                mRezertavions.remove(index);
                mRezertavionsIds.remove(index);
                notifyItemRemoved(index);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mRezertavions.size();
    }


}
