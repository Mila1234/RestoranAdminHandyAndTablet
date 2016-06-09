package com.example.marijaradisavljevic.restoranadminmarija.database;

/**
 * Created by marija on 22.5.16.
 */
public class Order implements Cloneable {

    private FoodMenuItem order;
    private int nuberOrder;
    private int id;

    //TODO id se dobija od backenda
    private static int ukid= 0;

    @Override
    public Order clone() throws CloneNotSupportedException {

        Order clone  = new Order();
        clone.id = this.id;
        clone.nuberOrder = this.nuberOrder;

        FoodMenuItem cloneFMT = this.getOrder().clone();

        clone.order = cloneFMT;
        return clone;
    }
    public Order(){

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
