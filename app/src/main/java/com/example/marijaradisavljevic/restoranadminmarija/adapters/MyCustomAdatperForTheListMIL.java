package com.example.marijaradisavljevic.restoranadminmarija.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityDetails;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityMainList;
import com.example.marijaradisavljevic.restoranadminmarija.database.FoodMenuItem;
import com.example.marijaradisavljevic.restoranadminmarija.database.Rezervation;
import com.example.marijaradisavljevic.restoranadminmarija.database.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by marija on 14.2.17.
 */
public class MyCustomAdatperForTheListMIL extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private Context context;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mDatabaseReference;
    private List<FoodMenuItem> mFoodMeniItems = new ArrayList<>();
    private List<String> mFoodMeniItemsIds = new ArrayList<>();

    public MyCustomAdatperForTheListMIL(Context context,DatabaseReference ref) {

        mDatabaseReference = ref;
        this.context = context;
        refreshList();

    }
    public void cleanupListener() {
        mFoodMeniItems.clear();

        mFoodMeniItemsIds.clear();
        // notifyItemRangeRemoved(0,mFoodMeniItems.size()-1);
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

                mFoodMeniItemsIds.add(dataSnapshot.getKey());
                mFoodMeniItems.add(fmi);

            }catch (Exception e){
                e.printStackTrace();
            }

                notifyDataSetChanged();


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
        mDatabaseReference.child("foodMenu").addChildEventListener(childEventListener);
        // [END child_event_listener_recycler]

        // Store reference to listener so it can be removed on app stop
        mChildEventListener = childEventListener;


    }

    private static  class  RezervationsViewHolder extends RecyclerView.ViewHolder {

        TextView info,id;
        Button edit, remove;

        public RezervationsViewHolder(View convertView) {
            super(convertView);
            id = (TextView) convertView.findViewById(R.id.id);
            info = (TextView)convertView.findViewById(R.id.info);
            edit  = (Button)convertView.findViewById(R.id.edit);
            remove = (Button)convertView.findViewById(R.id.remove);
        }

    }


    @Override
    public MyCustomAdatperForTheListMIL.RezervationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.food_menu_item_layout, parent, false);
        return new MyCustomAdatperForTheListMIL.RezervationsViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        final FoodMenuItem fmi = mFoodMeniItems.get(position);
        //holder.authorView.setText(comment.author);
        //  holder.bodyView.setText(comment.text);
        final MyCustomAdatperForTheListMIL.RezervationsViewHolder holder = (MyCustomAdatperForTheListMIL.RezervationsViewHolder)h;

        holder.info.setVisibility(View.VISIBLE);
        holder.info.setText(fmi.getFood()+"  \ncena : "+fmi.getPrice());

        holder.edit.setVisibility(View.VISIBLE);
        holder.remove.setVisibility(View.VISIBLE);
        holder.id.setText(String.valueOf(fmi.getId()));
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (context.getClass().equals(ActivityMainList.getInstance().getClass())){

                    ((ActivityMainList)context).callAddMenuItemForEdtiItemMenu(Integer.toString(fmi.getId()));
                }else {
                    //otvori prozor fragment FreagmentAddOrder
                    Intent intent2 = new Intent(context.getApplicationContext(), ActivityDetails.class);
                    intent2.putExtra("foodItemId", Integer.toString(fmi.getId()));
                    intent2.putExtra(ActivityDetails.CHOOSEFRAGM, ActivityDetails.ADD_ITEM_MENU);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(intent2);
                }

            }
        });

        // remove.setOnClickListener(this);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FoodMenuItem foud = null;
                for(FoodMenuItem ui :mFoodMeniItems){
                    String strinf = holder.id.getText().toString();
                    if(ui.getId() == Integer.parseInt(strinf)){
                        foud = ui;
                        break;
                    }
                }

                int index = mFoodMeniItems.indexOf(foud);
                String key = mFoodMeniItemsIds.get(index);
                mDatabaseReference.child("foodMenu").child(key).removeValue();
                mFoodMeniItems.remove(index);
                mFoodMeniItemsIds.remove(index);
                notifyItemRemoved(index);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mFoodMeniItems.size();
    }


}
