package com.example.marijaradisavljevic.restoranadminmarija.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityHost;
import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityMainList;
import com.example.marijaradisavljevic.restoranadminmarija.activity.Activity_Selection_And_ListReservation;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.MyCustomAdatperForTheList;
import com.example.marijaradisavljevic.restoranadminmarija.data.SelecionRegulations;
import com.example.marijaradisavljevic.restoranadminmarija.data.UserData;
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
 * Created by marija.radisavljevic on 5/13/2016.
 */
public class FreagmentAddOrder extends Fragment implements View.OnClickListener {
    public static final String ARG_ITEM_ID = "item_id";

    private Button split_order,make_order;
    private ImageButton new_item;
    private CheckedTextView paidOrNot;
    private static FreagmentAddOrder instance;
    private  String rezervationKeyFireBase = "";
    private String action;
    private TextView time;
    private Spinner numbreOfTable_spinner;
    String date;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    ///////////////////////////
    private RecyclerView listaAddOrder;
    private MyCustomAdatperForTheListOrder adapter;
    private ArrayList<Order> ListOrdersForSplitAction = new ArrayList<>();
    private ArrayAdapter<String> adapter_number_of_table;

    private Rezervation rezervation;
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


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (null != menu) {
            if (null != menu.findItem(R.id.action_logout)){
                menu.findItem(R.id.action_logout).setVisible(true);
            }
            if (null != menu.findItem(R.id.action_user_info)){
                menu.findItem(R.id.action_user_info).setVisible(true);
            }
            if (null != menu.findItem(R.id.action_add)){
                menu.findItem(R.id.action_add).setVisible(false);
            }


            super.onPrepareOptionsMenu(menu);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_add_order_layout,container,false);
        //ListOrdersForSplitAction = new ArrayList<ItemOrder>();
        getActivity().setTitle("Dodaj stavku");
        Bundle bundle =  this.getArguments();

        action = bundle.getString("action");

//TextView
        time = (TextView)mRoot.findViewById(R.id.time);


        if (action.equals("onclick")){

            rezervationKeyFireBase = getActivity().getIntent().getExtras().getString("rezervationKeyFireBase");
            if (rezervationKeyFireBase == null){
                rezervationKeyFireBase = getArguments().getString("rezervationKeyFireBase");
            }
           // int rezeravtionid = Integer.parseInt(rezervationKeyFireBase);
            //rezervation = FireBase.getInstance().getRezervationByID(rezeravtionid);
           // if (FireBase.getInstance().getUserInfo().getType().equals("Admin")){

             //   time.setText(rezervation.getTime() +""+rezervation.getname_user());

            //}else {
              //  time.setText(rezervation.getTime());
            //}




        }else if (action.equals("plusbutton")) {

            rezervationKeyFireBase = mDatabase.child("listaRezervations").push().getKey();
                    //FireBase.getInstance().newRezervation();



            //time.setText(date);//ovaj if se moze isvrsiti samo za konobara zato nema time verzije za admina


        }
        listaAddOrder = (RecyclerView) mRoot.findViewById(R.id.listaAddOrder);

        showProgressDialog();
        mDatabase.child("listaRezervations").child(rezervationKeyFireBase).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            rezervation = dataSnapshot.getValue(Rezervation.class);
                            if (rezervation == null){//plusbutton
                                rezervation = new Rezervation();
                                rezervation.setName_user(FireBase.getInstance().getUserInfo().getName() +" "+FireBase.getInstance().getUserInfo().getSurname() );
                                rezervation.setNameType(FireBase.getInstance().getUserInfo().getType());
                                rezervation.setPassword(FireBase.getInstance().getUserInfo().getPassword());
                                rezervation.setUsername(FireBase.getInstance().getUserInfo().getUsername());
                                //rezervation.setTime(date);

                            }
                            rezervation.setKey(rezervationKeyFireBase);

                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            adapter = new MyCustomAdatperForTheListOrder(getActivity(),rezervation,mDatabase);
                            // for (Order order: FireBase.getInstance().getListOrders(rezervationKeyFireBase)) {
                            //   adapter.addItem(new ItemOrder(order));
                            //}

                            listaAddOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
                            listaAddOrder.setAdapter(adapter);

                            if (action.equals("onclick")){
                                int position = adapter_number_of_table.getPosition(String.valueOf(rezervation.getnumberTable()));
                                numbreOfTable_spinner.setSelection(position);

                            }else{
                                numbreOfTable_spinner.setSelection(0);//
                            }


                            if (action.equals("onclick")){

                               if (rezervation.getNameType().equals("Admin")){

                                    time.setText(rezervation.getTime()+" "+rezervation.getNameType()+" "+rezervation.getname_user());

                                }else {
                                    time.setText(rezervation.getTime());
                                }

                            }else if (action.equals("plusbutton")) {

                                Calendar calendar = Calendar.getInstance();
                                int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
                                int YEAR = calendar.get(Calendar.YEAR);
                                int MONTH = calendar.get(Calendar.MONTH);
                                int HOUR = calendar.get(Calendar.HOUR);
                                int MINUT = calendar.get(Calendar.MINUTE);

                                date = DAY_OF_MONTH+"."+MONTH+"."+YEAR +" "+HOUR+"h "+MINUT+"min";

                                time.setText(date);//ovaj if se moze isvrsiti samo za konobara zato nema time verzije za admina
                                rezervation.setTime(time.getText().toString());


                            }
                            hideProgressDialog();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



//buttons
        split_order = (Button) mRoot.findViewById(R.id.split_order);
        split_order.setOnClickListener(this);
        if (FireBase.getInstance().getUserInfo().getType().equals("Admin")){
            split_order.setVisibility(View.GONE);
        }

        make_order = (Button) mRoot.findViewById(R.id.make_order);
        make_order.setOnClickListener(this);
        if (FireBase.getInstance().getUserInfo().getType().equals("Admin")){
            make_order.setVisibility(View.GONE);


        }
        new_item  = (ImageButton) mRoot.findViewById(R.id.new_item);
        new_item.setOnClickListener(this);

        if (FireBase.getInstance().getUserInfo().getType().equals("Admin")){
            new_item.setVisibility(View.GONE);
        }

//CheckedTextView
        paidOrNot  = (CheckedTextView) mRoot.findViewById(R.id.paidOrNot);
        paidOrNot.setChecked(FireBase.getInstance().getPaidOrNot(rezervationKeyFireBase));
        paidOrNot.setOnClickListener(this);

//spiner
        numbreOfTable_spinner = (Spinner)  mRoot.findViewById(R.id.numbreOfTable_spinner);
        String[] value = getResources().getStringArray(R.array.numbers);
         adapter_number_of_table = new MySpinnerAdapter(false,getActivity(),android.R.layout.simple_spinner_item,value);
        // Specify the layout to use when the list of choices appears
        adapter_number_of_table.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner



        numbreOfTable_spinner.setAdapter(adapter_number_of_table);



//ListView






        return mRoot;
    }



    public static FreagmentAddOrder getInstance() {
        if(instance == null){
            return new FreagmentAddOrder();
        }else return instance;
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.split_order:

              //  ArrayList<ItemOrder> listOfOrders = adapter.getMyList();
                //ArrayList<ItemOrder> stayInAllRezerv = new ArrayList<ItemOrder>();
                ArrayList stayInAllRezerv = new ArrayList();
                for(Order currOrder : rezervation.getOrders()) {
                    boolean find = false;
                    for (Order interCurOrder : ListOrdersForSplitAction) {
                        if (currOrder.getOrder().getId() == interCurOrder.getOrder().getId()) {
                            find = true;
                        }
                    }
                    if (!find) {
                        stayInAllRezerv.add(currOrder);
                    }
                }
                if (ListOrdersForSplitAction.isEmpty()){
                    Toast.makeText(getActivity(), getString(R.string.nemanistazasplit), Toast.LENGTH_LONG).show();
                }
                rezervation.setOrders(stayInAllRezerv);
                mDatabase.child("listaRezervations").child(rezervation.getKey()).setValue(rezervation);

                String key = mDatabase.child("listaRezervations").push().getKey();
                Rezervation newRez = new Rezervation(rezervation.getTime(),rezervation.getname_user(),rezervation.getnumberTable(),rezervation.isPaidOrNot(),rezervation.getPassword(),rezervation.getUsername(), rezervation.getNameType());
                newRez.setOrders(ListOrdersForSplitAction);
                newRez.setKey(key);
                mDatabase.child("listaRezervations").child(key).setValue(newRez);


               // FireBase.getInstance().AddRezervation(rezervationKeyFireBase,time.getText().toString(), numbreOfTable_spinner.getSelectedItem().toString(),paidOrNot.isChecked(),stayInAllRezerv);
                //rezervationKeyFireBase = FireBase.getInstance().newRezervation();
                //FireBase.getInstance().AddRezervation(rezervationKeyFireBase,time.getText().toString(), numbreOfTable_spinner.getSelectedItem().toString(),paidOrNot.isChecked(),ListOrdersForSplitAction);

                if (getActivity().getClass().equals(ActivityMainList.getInstance().getClass())){
                    ((ActivityMainList)getActivity()).callFragmentListReserAndSelection();
                }else {
                    intent = new Intent(getActivity().getApplicationContext(), Activity_Selection_And_ListReservation.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    getActivity().getApplicationContext().startActivity(intent);
                }
                //TODO
                break;
            case R.id.make_order: //TODO make, remake order !
                 //key = mDatabase.child("listaRezervations").push().getKey();

                rezervation.setNumberTable(Integer.parseInt(numbreOfTable_spinner.getSelectedItem().toString()));
                rezervation.setPaidOrNot(paidOrNot.isChecked());
               // rezervation.setTime(time.getText().toString());
                mDatabase.child("listaRezervations").child(rezervationKeyFireBase).setValue(rezervation);

               /*listOfOrders =adapter.getMyList();
                FireBase.getInstance().AddRezervation(rezervationKeyFireBase,time.getText().toString(), numbreOfTable_spinner.getSelectedItem().toString(),paidOrNot.isChecked(),listOfOrders);
*/
                intent = new Intent(getActivity().getApplicationContext(), Activity_Selection_And_ListReservation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);

                break;
            case R.id.new_item:

                rezervation.setNumberTable(Integer.parseInt(numbreOfTable_spinner.getSelectedItem().toString()));
                rezervation.setPaidOrNot(paidOrNot.isChecked());
              //  rezervation.setTime(time.getText().toString());

                mDatabase.child("listaRezervations").child(rezervationKeyFireBase).setValue(rezervation);
              //  listOfOrders =adapter.getMyList();
               // FireBase.getInstance().AddRezervation(rezervationKeyFireBase,time.getText().toString(), numbreOfTable_spinner.getSelectedItem().toString(),paidOrNot.isChecked(),listOfOrders);

//if something is changed in rezervation , becouse it is open to user to change it using userinterface
//ovo vec bilo neidljivo
              //  intent = new Intent(getActivity().getApplicationContext(), Activity_Add_New_Item_toMenu.class);
              //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               // intent.putExtra("rezervation_id",rezervationKeyFireBase);

               // getActivity().getApplicationContext().startActivity(intent);

                ((ActivityHost)getActivity()).callFragmentAddMenuItem("rezervationKeyFireBase", rezervationKeyFireBase);
                break;
            case R.id.paidOrNot:
                if(paidOrNot.isChecked()){
                    paidOrNot.setChecked(false);
                }else{
                    paidOrNot.setChecked(true);
                }
                break;
        }

    }



    private  class MyCustomAdatperForTheListOrder  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


        private Context context;
        private ChildEventListener mChildEventListener;
        private DatabaseReference mDatabaseReference;
        private List<Order> mOrders = new ArrayList<>();
        private List<String> mRezertavionsIds = new ArrayList<>();

        public MyCustomAdatperForTheListOrder(Context context, Rezervation rez,DatabaseReference ref) {
            mOrders = rez.getOrders();
            mDatabaseReference = ref;
            this.context = context;

        }


        private   class  RezervationsViewHolder extends RecyclerView.ViewHolder {

            TextView  itemOrder,number_item_order;
            Button  remove;
            CheckedTextView innewRezervations;

            public RezervationsViewHolder(View convertView) {
                super(convertView);
                itemOrder =(TextView) convertView.findViewById(R.id.item_order);
                number_item_order =(TextView) convertView.findViewById(R.id.number_item_order);
                remove = (Button)convertView.findViewById(R.id.remove);
                innewRezervations =(CheckedTextView) convertView.findViewById(R.id.in_new_order);
            }

        }


        @Override
        public RezervationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.item_order, parent, false);
            return new RezervationsViewHolder(view);
        }



        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
            final Order order = mOrders.get(position);
            //holder.authorView.setText(comment.author);
            //  holder.bodyView.setText(comment.text);
            final RezervationsViewHolder holder = (RezervationsViewHolder)h;

            holder.number_item_order.setVisibility(View.VISIBLE);
            holder.number_item_order.setText("komada : "+order.getNuberOrder());
            holder.itemOrder.setVisibility(View.VISIBLE);
            holder.itemOrder.setText(order.getOrder().getFood());
            holder.remove.setVisibility(View.VISIBLE);

            holder.innewRezervations.setVisibility(View.VISIBLE);
            holder.innewRezervations.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(holder.innewRezervations.isChecked()){
                        holder.innewRezervations.setChecked(false);
                       // ListOrdersForSplitAction.remove(FreagmentAddOrder.ItemOrder.this);
                        ListOrdersForSplitAction.remove(order);

                    }else{
                        holder.innewRezervations.setChecked(true);
                       // ListOrdersForSplitAction.add(FreagmentAddOrder.ItemOrder.this);
                        ListOrdersForSplitAction.add(order);
                    }

                }
            });

            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int index = mOrders.indexOf(order);
                    mOrders.remove(order);
                    notifyItemRemoved(index);


              /*  FireBase.getInstance().removeorderForRezer(order, rezervationKeyFireBase);


                adapter = new MyCustomAdatperForTheList(getActivity());
                for (Order order: FireBase.getInstance().getListOrders(rezervationKeyFireBase)) {
                    adapter.addItem(new FreagmentAddOrder.ItemOrder(order));
                }
                listaAddOrder.setAdapter(adapter);
                ((MyCustomAdatperForTheList<FreagmentAddOrder.ItemOrder>) listaAddOrder.getAdapter()).notifyDataSetChanged();
                FreagmentAddOrder.this.listaAddOrder.invalidate();
*/

                }
            });
        }

        @Override
        public int getItemCount() {
            return mOrders.size();
        }



    }

}
