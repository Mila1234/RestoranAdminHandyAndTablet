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
import com.example.marijaradisavljevic.restoranadminmarija.data.SelecionRegulations;
import com.example.marijaradisavljevic.restoranadminmarija.data.UserData;
import com.example.marijaradisavljevic.restoranadminmarija.database.Order;
import com.example.marijaradisavljevic.restoranadminmarija.database.Rezervation;
import com.example.marijaradisavljevic.restoranadminmarija.database.UserInfo;
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

import static android.content.ContentValues.TAG;

/**
 * Created by marija on 14.2.17.
 */

public class MyCustomAdatperForTheListUL extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mDatabaseReference;
    private List<UserInfo> mUserInfos = new ArrayList<>();
    private List<String> mUserInfosIds = new ArrayList<>();

    public MyCustomAdatperForTheListUL(Context context,DatabaseReference ref) {

        mDatabaseReference = ref;
        this.context = context;
        refreshList();

    }
    public void cleanupListener() {
        mUserInfos.clear();

        mUserInfosIds.clear();
        // notifyItemRangeRemoved(0,mUserInfos.size()-1);
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

                Map<String, String> hmUserinfo = (Map<String, String>) dataSnapshot.getValue();
                //Collection<Object> colection = map.values();
               // Iterator iter =  colection.iterator();
               // Object userInfoObject = iter.next();
               // HashMap<String,String> hmUserinfo = (HashMap<String, String>) userInfoObject;
                UserInfo userInfo = new UserInfo(hmUserinfo.get("username"),hmUserinfo.get("name"),hmUserinfo.get("surname"),hmUserinfo.get("number"),hmUserinfo.get("email"),hmUserinfo.get("type"),hmUserinfo.get("password"));

                userInfo.setKey(dataSnapshot.getKey());

                mUserInfosIds.add(dataSnapshot.getKey());
                mUserInfos.add(userInfo);
               // notifyItemInserted(mUserInfos.size() - 1);
                notifyDataSetChanged();
               // addItemBla(new ItemForRezervationsList(currRezervation));

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
        mDatabaseReference.child("users").addChildEventListener(childEventListener);
        // [END child_event_listener_recycler]

        // Store reference to listener so it can be removed on app stop
        mChildEventListener = childEventListener;


    }

    private static  class  RezervationsViewHolder extends RecyclerView.ViewHolder {

        TextView typeAndnameSurname,username ,password,email, number;
        Button edit, remove;

        public RezervationsViewHolder(View convertView) {
            super(convertView);
            typeAndnameSurname = (TextView)convertView.findViewById(R.id.typAndName);
            username= (TextView)convertView.findViewById(R.id.user_name);
            password= (TextView)convertView.findViewById(R.id.password);
            email= (TextView)convertView.findViewById(R.id.email);
            number = (TextView)convertView.findViewById(R.id.number);

            edit  = (Button)convertView.findViewById(R.id.edit);
            remove = (Button)convertView.findViewById(R.id.remove);
        }

    }


    @Override
    public RezervationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_info, parent, false);
        return new RezervationsViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        final UserInfo userinfo = mUserInfos.get(position);
        //holder.authorView.setText(comment.author);
        //  holder.bodyView.setText(comment.text);
        final RezervationsViewHolder holder = (RezervationsViewHolder)h;

        holder.typeAndnameSurname.setVisibility(View.VISIBLE);
        //String typeNameSurnameString = adapterItem.userinfo.getType() + " : " + adapterItem.userinfo.getName() + " " + adapterItem.userinfo.getSurname();
        //String typeNameSurnameString = adapterItem.userinfo.getStringTypeNameSurnameForListUsers();
        String typeNameSurnameString = userinfo.getType() + " : "+userinfo.getName()+ " " +userinfo.getSurname();

        holder.typeAndnameSurname.setText(typeNameSurnameString);
        holder.username.setVisibility(View.VISIBLE);
        holder.username.setText(context.getString(R.string.username) + " : " + userinfo.getUsername());
        holder.password.setVisibility(View.VISIBLE);
        holder.password.setText(context.getString(R.string.password) +" : "+userinfo.getPassword());
        holder.email.setVisibility(View.VISIBLE);
        holder.email.setText(context.getString (R.string.email)+" : "+userinfo.getEmail());
        holder.number.setVisibility(View.VISIBLE);
        holder.number.setText(context.getString (R.string.number)+" : "+userinfo.getNumber());

        holder.edit.setVisibility(View.VISIBLE);
        holder.remove.setVisibility(View.VISIBLE);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context.getClass().equals(ActivityMainList.getInstance().getClass())){

                    ((ActivityMainList)context).callUserInfoForEditUser(userinfo.getUsername(),userinfo.getPassword(),userinfo.getKey());
                }else {
                    Intent intent2 = new Intent(context.getApplicationContext(), ActivityDetails.class);
                    intent2.putExtra(ActivityDetails.CHOOSEFRAGM, ActivityDetails.ADD_NEW_USER);
                    intent2.putExtra("username", userinfo.getUsername());
                    intent2.putExtra("password", userinfo.getPassword());
                    intent2.putExtra("keyForFireBase",userinfo.getKey());
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent2);
                }
            }
        });


        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserInfo foud = null;
                for(UserInfo ui :mUserInfos){
                    String strinf = holder.email.getText().toString();
                    if(ui.getEmail().equals(strinf.substring(9))){
                        foud = ui;
                        break;
                    }
                }

                int index = mUserInfos.indexOf(foud);
                String key = mUserInfosIds.get(index);
                mDatabaseReference.child("users").child(key).removeValue();
                mUserInfos.remove(index);
                mUserInfosIds.remove(index);
                notifyItemRemoved(index);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserInfos.size();
    }


}
