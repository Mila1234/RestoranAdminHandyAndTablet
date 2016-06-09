package com.example.marijaradisavljevic.restoranadminmarija.servis;

import com.example.marijaradisavljevic.restoranadminmarija.activity.ActivityAddOrder;
import com.example.marijaradisavljevic.restoranadminmarija.database.FoodMenuItem;
import com.example.marijaradisavljevic.restoranadminmarija.database.Order;
import com.example.marijaradisavljevic.restoranadminmarija.database.Rezervation;
import com.example.marijaradisavljevic.restoranadminmarija.database.SelecionRegulations;
import com.example.marijaradisavljevic.restoranadminmarija.database.UserInfo;


import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by marija.radisavljevic on 6/3/2016.
 */
public class Servis {
    private static Servis instance = new Servis();
    public static Servis getInstance() {return instance; }

    private UserInfo userInfo;
    private String[] listaTable;
    private String[] numberItemssStrignList;
    private ArrayList<FoodMenuItem> listFoodMenuItem;
    private ArrayList<Rezervation> listOfRezervations;


    public Servis() {

        userInfo = new UserInfo();
        userInfo.setEmail("marijarad89@gmail.com");
        userInfo.setName("Marija");
        userInfo.setSurname("Radisavljevic");
        userInfo.setNumber("060123789");

        numberItemssStrignList = new String[7];
        numberItemssStrignList[0] = "broj komada";
        numberItemssStrignList[1] ="1" ;
        numberItemssStrignList[2] ="2";
        numberItemssStrignList[3] ="3";
        numberItemssStrignList[4] ="4";
        numberItemssStrignList[5] = "5";
        numberItemssStrignList[6] = "6";

        listaTable = new String[6];
        listaTable[0] = "broj stola";
        listaTable[1] ="1" ;
        listaTable[2] ="2";
        listaTable[3] ="3";
        listaTable[4] ="4";
        listaTable[5] = "5";


        listFoodMenuItem = new ArrayList<FoodMenuItem>();

        FoodMenuItem fmt1 = new FoodMenuItem("1 ", 100);
        listFoodMenuItem.add(fmt1);
        FoodMenuItem fmt2 = new FoodMenuItem("2 ", 100);
        listFoodMenuItem.add(fmt2);

        FoodMenuItem fmt3 = new FoodMenuItem("3 ", 100);
        listFoodMenuItem.add(fmt3);

        FoodMenuItem fmt4 = new FoodMenuItem("4 ", 100);
        listFoodMenuItem.add(fmt4);







        listOfRezervations = new ArrayList<Rezervation>();


        Rezervation ld = new Rezervation();
        ld.setNameType("konobar");
        ld.setName_user("milica jelic");
        // ld.setItemsOrder(new ArrayList(Arrays.asList("kapucino , truska kafa, lenja pita sa jabukama")));
        ArrayList<Order>listOrders = new ArrayList<Order>();
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
        ld.setNameType("konobar");
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
        ld.setNameType("konobar");
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
        ld.setNameType("konobar");
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

    public  Boolean logIN(String mEmail, String mPassword) {
        return true;
    }

    public String UserType(){
        return "konobar";
    }
    public String NameOfUser (){
        return "Pera Peric";
    }

    public UserInfo getUserInfo(){
        return userInfo;
    }
    public void setUserInfo(UserInfo ui){
        userInfo = ui;
    }

    public String[] stringListofTables (){
        return  listaTable;
    }

    public String[] stringListofFoodItems (){
        ArrayList<String> returnStringList = new ArrayList<String>();
        returnStringList.add("kategory");
        for(FoodMenuItem foodMenuItem : listFoodMenuItem ){
            returnStringList.add(foodMenuItem.getFood());
        }
        String[] stringList = new String[returnStringList.size()];
        stringList = returnStringList.toArray(stringList);
        return  stringList;
    }

    public ArrayList<Rezervation> getListOfRezervations(){
        return listOfRezervations;
    }

    public ArrayList<Rezervation> getRezervationsWithRegulation(SelecionRegulations selecionRegulation) {
        if(selecionRegulation.isAll()){

            return listOfRezervations;
        }

        ArrayList<Rezervation> returnRezerList = new ArrayList<>();
        for (Rezervation currRezervation : listOfRezervations){

            if(selecionRegulation.isKategory_selected()){
                for (Order currorder :currRezervation.getOrders()){
                    if(currorder.getOrder().getFood().equals(selecionRegulation.getKategory())){
                        returnRezerList.add(currRezervation);
                        break;
                    }
                }
            }

            if(selecionRegulation.isNumberOfTable_selectied()){
                if(currRezervation.getnumberTable().toString().equals(selecionRegulation.getNumberOfTable())  ){
                    returnRezerList.add(currRezervation);
                }

            }
            if(selecionRegulation.isPaidOrNot_selected() && selecionRegulation.isPaidOrNot()){
                //ubaci sve koji su placeni
                if(currRezervation.isPaidOrNot() == true ){
                    returnRezerList.add(currRezervation);
                }
            }
        }
        return returnRezerList;

    }

    public Rezervation getRezervationByID(int id) {

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

    public String newRezervation (){
        Rezervation r = new Rezervation();
        listOfRezervations.add(r);
        Integer i = r.getId();
        return String.valueOf(i);
    }
/*
    public void AddRezervation(String id,String typeAndName, String time,String nuberTable, boolean ispaidnOrnot,ArrayList<ActivityAddOrder.ItemOrder> listaOrder ){

        for(Rezervation rez: listOfRezervations){
            if(rez.getId()== Integer.parseInt(id)){
                //update info
                int index = typeAndName.indexOf(":");


                rez.setNameType(typeAndName.substring(0,index));
                rez.setName_user(typeAndName.substring(index + 1, typeAndName.length()));
                rez.setTime(time);
                rez.setPaidOrNot(ispaidnOrnot);
                rez.setNumberTable(Integer.parseInt(nuberTable));
                ArrayList<Order> lista = new ArrayList<Order>();
                for(ActivityAddOrder.ItemOrder curOrder : listaOrder){
                    lista.add(curOrder.getOrder());
                }
                rez.setOrders(lista);

            }

        }


    }*/

    public void addOrder(int id, String numberOfItems, String Kategory) {

        for(Rezervation rez: listOfRezervations){
            if(rez.getId()== id){
                Order o = new Order();
                if (!numberOfItems.equals(numberItemssStrignList[0])  ){
                    o.setNuberOrder(Integer.parseInt(numberOfItems));
                    if (!stringListofFoodItems()[0].equals(Kategory)){
                        o.setOrder(getGoodmenuItem(Kategory));
                        rez.getOrders().add(o);
                    }
                }

            }

        }
    }

    private FoodMenuItem getGoodmenuItem(String kategory) {
        for(FoodMenuItem f:listFoodMenuItem){
            if (f.getFood().equals(kategory)){
                return  f;
            }
        }
        return null;
    }

    public String getTimeForRezervation(String rezervationIdString) {
        for(Rezervation rez: listOfRezervations){
            if(rez.getId() == Integer.parseInt(rezervationIdString)){

                return rez.gettime();
            }

        }
        return "";
    }

    public boolean getPaidOrNot(String rezervationIdString) {
        for(Rezervation rez: listOfRezervations){
            if(rez.getId() == Integer.parseInt(rezervationIdString)){

                return rez.isPaidOrNot();
            }

        }
        return false;
    }

    public int getNumberOFtable(String rezervationIdString) {
        for(Rezervation rez: listOfRezervations){
            if(rez.getId() == Integer.parseInt(rezervationIdString)){

                return rez.getnumberTable();
            }

        }
        return -1;
    }

    public ArrayList<Order> getListOrders(String rezervationIdString) {
        for(Rezervation rez: listOfRezervations){
            if(rez.getId() == Integer.parseInt(rezervationIdString)){

                return rez.getOrders();
            }

        }
        return null;
    }

    public void setUserInfoForRezervation(String id,String userType, String user) {
        for(Rezervation rez: listOfRezervations){
            if(rez.getId() == Integer.parseInt(id)){
               rez.setName_user(user);
                rez.setNameType(userType);
            }

        }
    }
    public String getUserAndTypeForRezervation(String id) {
        for(Rezervation rez: listOfRezervations){
            if(rez.getId() == Integer.parseInt(id)){
                return rez.getNameType()+" : "+ rez.getname_user();
            }

        }
        return "";

    }

    public String[] getNumberItems() {
        return numberItemssStrignList;
    }

    public void removeorderForRezer(Order o,String rezervationIdString) {
        for(Rezervation rez: listOfRezervations){
            if(rez.getId() == Integer.parseInt(rezervationIdString) ){
               for(Order currOrd:rez.getOrders()){
                   if(o.getId()==currOrd.getId()){
                       rez.getOrders().remove(currOrd);
                       return;
                   }
               }
            }

        }

    }

    public void removeRezer(int id) {

        for(Rezervation rez: listOfRezervations){
            if(rez.getId() == id){
                listOfRezervations.remove(rez);
                return;
            }

        }
    }
}
