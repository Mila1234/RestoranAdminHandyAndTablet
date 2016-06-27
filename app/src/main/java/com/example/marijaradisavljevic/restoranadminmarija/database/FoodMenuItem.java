package com.example.marijaradisavljevic.restoranadminmarija.database;

/**
 * Created by marija on 22.5.16.
 */
public class FoodMenuItem implements  Cloneable{
    private String food;
    private Integer price;
    private int id;
    private FoodMenuItem nadstavka;


    //TODO dobija se od baceknda
    private static int ukid = 0;


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
