package com.example.marijaradisavljevic.restoranadminmarija.database;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by marija.radisavljevic on 5/18/2016.
 */

@IgnoreExtraProperties
public class Rezervation implements  Cloneable{//TODO DB komunication

    private String time,name_user;
    private Integer price;
    private  Integer numberTable;
    private boolean paidOrNot;
    private String password;
    private String username;

    private String nameType;
        //private LinkedList<Order> listaO;
    private ArrayList<Order> orders;
    private Integer id;

    //TODO ovaj id treba da se dobija od backenda
    private static int ukid = 0;

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("time", time);
        result.put("name_user", name_user);
        result.put("numberTable", numberTable);
        result.put("paidOrNot", paidOrNot);
        result.put("password", username);
        result.put("nameType", nameType);


///////////////////////
       /* FoodMenuItem nadtavka  = new FoodMenuItem(null,"pice", 100);
        FoodMenuItem fmt1 = new FoodMenuItem(nadtavka,"2 type food", 100);
        FoodMenuItem fmt2 = new FoodMenuItem(nadtavka,"3 type food", 100);
        FoodMenuItem fmt3 = new FoodMenuItem(nadtavka,"4 type food", 100);
        listaO = new LinkedList<Order>();
        listaO.add(new Order(1,fmt1,1));
        listaO.add(new Order(3,fmt2,1));
        listaO.add(new Order(4,fmt3,1));

        result.put("listaO", listaO);
        //result.put("orders", orders);*/
        ///////////////////////////
        result.put("id", id);

        return result;
    }
    // [END post_to_map]


    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }

    public Integer getNumberTable() {
        return numberTable;
    }

    public void setNumberTable(Integer numberTable) {
        this.numberTable = numberTable;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    @Override
    public Rezervation clone() throws CloneNotSupportedException {
        Rezervation clone = new Rezervation();
         clone.setPaidOrNot(this.isPaidOrNot());
        clone.setTime(this.gettime());
        clone.setName_user(this.getname_user());
        clone.setNumberTable(this.getnumberTable());
        clone.id = this.id;
/*
        Iterator<Order> iter = orders.iterator();
        ArrayList<Order>  cloneOrdersList = clone.getOrders();
        while (iter.hasNext()) {
            Order cloneOrder;
            Order tek = iter.next();


            cloneOrder = tek.clone();

            cloneOrdersList.add(cloneOrder);

        }
*/

        return clone;
    }

    @Override
    public boolean equals(Object o) {
        Rezervation rez = (Rezervation)o;
        if(this.getId()== (rez.getId())) {
             return true;
        }
        return false;
    }

    public Rezervation() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
         price = 0;
         numberTable = 0;
         paidOrNot = false;



         //orders = new ArrayList<Order>();

         //itemsOrder  = new ArrayList<String>();
         id = ukid++;

    }

    public String getnumberTable_string(){
        return Integer.toString(numberTable);
    }

    public boolean isPaidOrNot() {
        return paidOrNot;
    }

    public String getpaidOrNot_string(){
        if (paidOrNot) {
            return "Plaćeno.";
        }else{
            return "Nije plaćeno.";
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public void setNumberTable(int numberTable) {
        this.numberTable = numberTable;
    }

    private void setPrice(int price) {
        this.price = price;
    }


    public void setPaidOrNot(boolean paidOrNot) {
        this.paidOrNot = paidOrNot;
    }

    public String gettime() {
        return time;
    }

    public String getname_user() {
        return name_user;
    }

    public Integer getnumberTable() {
        return numberTable;
    }

    public Integer getprice() {


            Iterator<Order> iter = orders.iterator();
            price = 0;
            while (iter.hasNext()) {
                price = price + iter.next().getOrder().getPrice();
            }



            return price;

    }

    public String getPrice_toString(){
        String bla = String.valueOf(getprice());
        return bla;
    }

   /* public ArrayList<String > getitemsOrder() {
        return itemsOrder;
    }
    public void setItemsOrder(ArrayList<String > itemsOrder) {
        this.itemsOrder = itemsOrder;
    }
*/
    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public String getItemsOrdersInString(){

            Iterator<Order> iter = orders.iterator();
            StringBuilder builder = new StringBuilder();
            String prefix = "";
            while (iter.hasNext()) {
                builder.append(prefix);
                prefix = " ,";
                builder.append(iter.next().getOrder().getFood());

            }

            return builder.toString();

    }
    public boolean ispaidOrNot() {
        return paidOrNot;
    }

    public void removeOrders(ArrayList<Order> listOrdersForSplitAction) {

        for(Order currOrder: orders){
            for(Order orderForErase: listOrdersForSplitAction){
                if (currOrder.getId()== orderForErase.getId()){
                    orders.remove(currOrder);
                }
            }

        }
    }
}
