package com.example.marijaradisavljevic.restoranadminmarija.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.marijaradisavljevic.restoranadminmarija.activity.Activity_Selection_And_ListReservation;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.HolderAdapterItem;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.MyCustomAdatperForTheList;
import com.example.marijaradisavljevic.restoranadminmarija.database.Order;
import com.example.marijaradisavljevic.restoranadminmarija.servis.Servis;
import com.example.marijaradisavljevic.restoranadminmarija.spiner.MySpinnerAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by marija.radisavljevic on 5/13/2016.
 */
public class FreagmentAddOrder extends Fragment implements View.OnClickListener {
    public static final String ARG_ITEM_ID = "item_id";

    private Button split_order,make_order;
    private ImageButton new_item;
    private CheckedTextView paidOrNot;
    private static FreagmentAddOrder instance;
    String rezervationIdString = "";

    TextView time;
    Spinner numbreOfTable_spinner;


    ///////////////////////////
    private ListView listaAddOrder;
    MyCustomAdatperForTheList<ItemOrder> adapter;
    ArrayList<ItemOrder> ListOrdersForSplitAction ;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_logout).setVisible(true);
        menu.findItem(R.id.action_user_info).setVisible(true);
        menu.findItem(R.id.action_add).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRoot = inflater.inflate(R.layout.fragment_add_order_layout,container,false);
        ListOrdersForSplitAction = new ArrayList<ItemOrder>();

        Bundle bundle =  this.getArguments();
        String action = bundle.getString("action");

//TextView
        time = (TextView)mRoot.findViewById(R.id.time);


        if (action.equals("onclick")){

            rezervationIdString = getArguments().getString("rezervationId");
           // int rezeravtionid = Integer.parseInt(rezervationIdString);
            //rezervation = Servis.getInstance().getRezervationByID(rezeravtionid);
            time.setText(Servis.getInstance().getTimeForRezervation(rezervationIdString));


        }else if (action.equals("plusbutton")) {
            rezervationIdString = Servis.getInstance().newRezervation();

            Calendar calendar = Calendar.getInstance();
            int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
            int YEAR = calendar.get(Calendar.YEAR);
            int MONTH = calendar.get(Calendar.MONTH);
            int HOUR = calendar.get(Calendar.HOUR);
            int MINUT = calendar.get(Calendar.MINUTE);
            String date;
            date = DAY_OF_MONTH+"."+MONTH+"."+YEAR+" "+HOUR+":"+MINUT;


        } else {
            //TODO
        }

//buttons
        split_order = (Button) mRoot.findViewById(R.id.split_order);
        split_order.setOnClickListener(this);
        make_order = (Button) mRoot.findViewById(R.id.make_order);
        make_order.setOnClickListener(this);
        new_item  = (ImageButton) mRoot.findViewById(R.id.new_item);
        new_item.setOnClickListener(this);

//CheckedTextView
        paidOrNot  = (CheckedTextView) mRoot.findViewById(R.id.paidOrNot);
        paidOrNot.setChecked(Servis.getInstance().getPaidOrNot(rezervationIdString));
        paidOrNot.setOnClickListener(this);

//spiner
         numbreOfTable_spinner = (Spinner)  mRoot.findViewById(R.id.numbreOfTable_spinner);
        String[] value = getResources().getStringArray(R.array.numbers);
        ArrayAdapter<String> adapter_number_of_table = new MySpinnerAdapter(false,getActivity(),android.R.layout.simple_spinner_item,value);
        // Specify the layout to use when the list of choices appears
        adapter_number_of_table.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner



        numbreOfTable_spinner.setAdapter(adapter_number_of_table);
        if (action.equals("onclick")){
            int position = adapter_number_of_table.getPosition(String.valueOf(Servis.getInstance().getNumberOFtable(rezervationIdString)));
            numbreOfTable_spinner.setSelection(position);

        }else{
            numbreOfTable_spinner.setSelection(0);//
        }


//ListView

        listaAddOrder = (ListView) mRoot.findViewById(R.id.listaAddOrder);

        adapter = new MyCustomAdatperForTheList(getActivity());
            for (Order order:Servis.getInstance().getListOrders(rezervationIdString)) {
                adapter.addItem(new ItemOrder(order));
            }
        listaAddOrder.setAdapter(adapter);




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

                ArrayList<ItemOrder> listOfOrders = adapter.getMyList();
                ArrayList<ItemOrder> splitListaNew = new ArrayList<ItemOrder>();
                for(ItemOrder currOrder : listOfOrders) {
                    boolean find = false;
                    for (ItemOrder interCurOrder : ListOrdersForSplitAction) {
                        if (currOrder.getOrder().getId() == interCurOrder.getOrder().getId()) {
                            find = true;
                        }
                    }
                    if (!find) {
                        splitListaNew.add(currOrder);
                    }
                }
if (ListOrdersForSplitAction.isEmpty()){
    Toast.makeText(getActivity(), getString(R.string.nemanistazasplit), Toast.LENGTH_LONG).show();
}

                Servis.getInstance().AddRezervation( rezervationIdString,time.getText().toString(), numbreOfTable_spinner.getSelectedItem().toString(),paidOrNot.isChecked(),splitListaNew);
                rezervationIdString = Servis.getInstance().newRezervation();
                Servis.getInstance().AddRezervation( rezervationIdString,time.getText().toString(), numbreOfTable_spinner.getSelectedItem().toString(),paidOrNot.isChecked(),ListOrdersForSplitAction);



                 intent = new Intent(getActivity().getApplicationContext(), Activity_Selection_And_ListReservation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getActivity().getApplicationContext().startActivity(intent);

                //TODO
                break;
            case R.id.make_order: //TODO make, remake order !
                listOfOrders =adapter.getMyList();
                Servis.getInstance().AddRezervation(rezervationIdString,time.getText().toString(), numbreOfTable_spinner.getSelectedItem().toString(),paidOrNot.isChecked(),listOfOrders);

                intent = new Intent(getActivity().getApplicationContext(), Activity_Selection_And_ListReservation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);

                break;
            case R.id.new_item:
                listOfOrders =adapter.getMyList();
                Servis.getInstance().AddRezervation(rezervationIdString,time.getText().toString(), numbreOfTable_spinner.getSelectedItem().toString(),paidOrNot.isChecked(),listOfOrders);
//if something is changed in rezervation , becouse it is open to user to change it using userinterface

              //  intent = new Intent(getActivity().getApplicationContext(), Activity_Add_New_Item_toMenu.class);
              //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               // intent.putExtra("rezervation_id",rezervationIdString);

               // getActivity().getApplicationContext().startActivity(intent);

                ((ActivityHost)getActivity()).callFragmentAddMenuItem("rezervation_id",rezervationIdString);
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
    public class ItemOrder extends HolderAdapterItem {

        private Order order;

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        public ItemOrder(Order order){
            this.order = order;


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
            return R.layout.item_order;
        }

        @Override
        protected  IViewHolder createViewHolder() {
            return  new OrderViewHolder();
        }

        private class  OrderViewHolder implements IViewHolder<ItemOrder> {
            TextView  itemOrder,number_item_order;
            Button  remove;
            CheckedTextView innewRezervations;


            @Override
            public void findViews(View convertView) {
                itemOrder =(TextView) convertView.findViewById(R.id.item_order);
                number_item_order =(TextView) convertView.findViewById(R.id.number_item_order);
                remove = (Button)convertView.findViewById(R.id.remove);
                innewRezervations =(CheckedTextView) convertView.findViewById(R.id.in_new_order);

            }
            @Override
            public void fillData(final ItemOrder adapterItem) {
                number_item_order.setVisibility(View.VISIBLE);
                number_item_order.setText("komada : "+adapterItem.order.getNuberOrder());
                itemOrder.setVisibility(View.VISIBLE);
                itemOrder.setText(adapterItem.order.getOrder().getFood());
                remove.setVisibility(View.VISIBLE);

                innewRezervations.setVisibility(View.VISIBLE);
                innewRezervations.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(innewRezervations.isChecked()){
                            innewRezervations.setChecked(false);
                            ListOrdersForSplitAction.remove(ItemOrder.this);

                        }else{
                            innewRezervations.setChecked(true);
                            ListOrdersForSplitAction.add(ItemOrder.this);

                        }

                    }
                });

                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Servis.getInstance().removeorderForRezer(order,rezervationIdString);


                        adapter = new MyCustomAdatperForTheList(getActivity());
                        for (Order order:Servis.getInstance().getListOrders(rezervationIdString)) {
                            adapter.addItem(new ItemOrder(order));
                        }
                        listaAddOrder.setAdapter(adapter);
                        ((MyCustomAdatperForTheList<ItemOrder>) listaAddOrder.getAdapter()).notifyDataSetChanged();
                        FreagmentAddOrder.this.listaAddOrder.invalidate();


                    }
                });



            }
        }
    }

}
