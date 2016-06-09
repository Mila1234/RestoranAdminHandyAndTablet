package com.example.marijaradisavljevic.restoranadminmarija.database;


import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by marija.radisavljevic on 5/20/2016.
 */
public class AdapterDB {

    private static AdapterDB instance = new AdapterDB();
    public static AdapterDB getInstance() {return instance; }



    private ArrayList<FoodMenuItem> listFoodMenuItem;
    //i get list of reservatins from back end
    private ArrayList<Rezervation> listOfRezervations;

    public AdapterDB() {


        listFoodMenuItem = new ArrayList<FoodMenuItem>();

        FoodMenuItem fmt1 = new FoodMenuItem("koka kolabla bla bla bla bla", 100);
        listFoodMenuItem.add(fmt1);
        FoodMenuItem fmt2 = new FoodMenuItem("koka kola", 100);
        listFoodMenuItem.add(fmt2);

        FoodMenuItem fmt3 = new FoodMenuItem("turska kafa", 100);
        listFoodMenuItem.add(fmt3);

        FoodMenuItem fmt4 = new FoodMenuItem("espreso", 100);
        listFoodMenuItem.add(fmt4);







        listOfRezervations = new ArrayList<Rezervation>();


        Rezervation ld = new Rezervation();

        ld.setName_user("milica jelic");
       // ld.setItemsOrder(new ArrayList(Arrays.asList("kapucino , truska kafa, lenja pita sa jabukama")));
        ArrayList<Order>listOrders = new ArrayList<Order>();
        listOrders.add(new Order(1,fmt1,1));
        listOrders.add(new Order(3,fmt2,1));
        listOrders.add(new Order(4,fmt3,1));
        listOrders.add(new Order(1,fmt1,1));
        listOrders.add(new Order(3,fmt2,1));
        listOrders.add(new Order(4,fmt3,1));
        listOrders.add(new Order(1,fmt1,1));
        listOrders.add(new Order(3,fmt2,1));
        listOrders.add(new Order(4,fmt3,1));
        ld.setOrders(listOrders);
        ld.setNumberTable(3);
        ld.setPaidOrNot(true);

        ld.setTime("5.5.2016. 17:30 ");
        ld.setId(555);
        listOfRezervations.add(ld);

         ld = new Rezervation();

        ld.setName_user("Ana Ilic");
        //ld.setItemsOrder(new ArrayList(Arrays.asList("kapucino , truska kafa, lenja pita sa jabukama")));
        listOrders = new ArrayList<Order>();
        listOrders.add(new Order(1,fmt1,9));
        listOrders.add(new Order(3,fmt2,9));
        listOrders.add(new Order(4,fmt3,9));
        ld.setOrders(listOrders);
        ld.setNumberTable(2);
        ld.setPaidOrNot(true);
ld.setId(22);
        ld.setTime("5.5.2016. 18:30 ");
        listOfRezervations.add(ld);

        ld = new Rezervation();

        ld.setName_user("milanka rajicic");
       // ld.setItemsOrder(new ArrayList(Arrays.asList("koka kola , koka kola, lenja pita sa jabukama")));
        listOrders = new ArrayList<Order>();
        listOrders.add(new Order(1,fmt1,71));
        listOrders.add(new Order(3,fmt2,17));
        listOrders.add(new Order(4,fmt3,17));
        ld.setOrders(listOrders);
        ld.setNumberTable(2);
        ld.setId(40);
        ld.setPaidOrNot(true);
        ld.setTime("5.5.2016. 18:00");
        listOfRezervations.add(ld);


        ld = new Rezervation();
        ld.setName_user("novak stojanovic");
      //  ld.setItemsOrder(new ArrayList(Arrays.asList("jelen pivo ,crveno vino , lenja pita sa jabukama")));
        listOrders = new ArrayList<Order>();
        listOrders.add(new Order(1,fmt1,12));
        listOrders.add(new Order(3,fmt2,13));
        listOrders.add(new Order(4,fmt3,13));
        ld.setOrders(listOrders);
        ld.setNumberTable(1);
ld.setId(7);
        ld.setPaidOrNot(true);

        ld.setTime("5.5.2016. 17:00 ");
        listOfRezervations.add(ld);



    }



    //TODO 5/19/2016  this  is read from DB
    public ArrayList<Rezervation> getRezervations() {
        return  listOfRezervations;
    }

    public void updateRezervations (int id){


    }

    public void  deleteRezervation(int id){

        Iterator<Rezervation> iter = listOfRezervations.iterator();
        while (iter.hasNext()){
            Rezervation tek;
            tek = iter.next();
            if(tek.getId() == id){
               listOfRezervations.remove(tek);

                return;
            }
        }



    }



    public Rezervation getRezervationByID(int id){


        Iterator<Rezervation> iter = listOfRezervations.iterator();
        while (iter.hasNext()){
            Rezervation tek;
            tek = iter.next();
            if(tek.getId() == id){
                return tek;

            }
        }


        return  null;//todo  mora da postoji taj id
    }

    public Rezervation getNewRezervation() {
        Rezervation r = new Rezervation();
        listOfRezervations.add(r);
        return r;
    }
    public void  putRezervation(Rezervation r) {
        Rezervation rez = getRezervationByID(r.getId());

        rez = r;

    }
}
