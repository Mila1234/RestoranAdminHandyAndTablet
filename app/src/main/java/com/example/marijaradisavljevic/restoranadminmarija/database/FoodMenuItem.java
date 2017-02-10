package com.example.marijaradisavljevic.restoranadminmarija.database;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marija on 22.5.16.
 */
@IgnoreExtraProperties

public class FoodMenuItem implements  Cloneable{

    private String food;
    private Integer price;
    private int id;
    private FoodMenuItem nadstavka;


    //TODO dobija se od baceknda
    private static int ukid = 0;

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("food", food);
        result.put("price", price);
        result.put("id", id);
        result.put("nadstavka", nadstavka);

        return result;
    }
    // [END post_to_map]

    public FoodMenuItem getNadstavka() {
        return nadstavka;
    }

    public void setNadstavka(FoodMenuItem nadstavka) {
        this.nadstavka = nadstavka;
    }

    @Override
    public FoodMenuItem clone() throws CloneNotSupportedException {

        FoodMenuItem clone = new FoodMenuItem();
        clone.id = this.id;
        clone.food = this.food;
        clone.price = this.price;

        return clone;
    }

    public FoodMenuItem(FoodMenuItem nadstavka,String food, int price) {
        this.nadstavka = nadstavka;
        this.food = food;
        this.price = price;
        id = ukid++;
    }
    public FoodMenuItem() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
