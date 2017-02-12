package com.example.marijaradisavljevic.restoranadminmarija.database;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marija on 22.5.16.
 */

@IgnoreExtraProperties
public class Order implements Cloneable {

    private FoodMenuItem order;
    private int nuberOrder;
    private int id;

    //TODO id se dobija od backenda
    private static int ukid= 0;



    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("nuberOrder", nuberOrder);
        result.put("id", id);


        return result;
    }
    // [END post_to_map]


    public Order() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }
    @Override
    public Order clone() throws CloneNotSupportedException {

        Order clone  = new Order();
        clone.id = this.id;
        clone.nuberOrder = this.nuberOrder;

        FoodMenuItem cloneFMT = this.getOrder().clone();

        clone.order = cloneFMT;
        return clone;
    }


    public Order(int id, FoodMenuItem order, int nuberOrder) {
        this.order = order;
        this.nuberOrder = nuberOrder;
        this.id = ukid++;
    }

    @Override
    public boolean equals(Object o) {
        Order rez = (Order)o;
        if(this.getId()== (rez.getId())) {

            return true;
        }


        return false;
    }

    public int getNuberOrder() {
        return nuberOrder;
    }

    public void setNuberOrder(int nuberOrder) {
        this.nuberOrder = nuberOrder;
    }

    public FoodMenuItem getOrder() {
        return order;
    }

    public void setOrder(FoodMenuItem order) {
        this.order = order;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }
}
