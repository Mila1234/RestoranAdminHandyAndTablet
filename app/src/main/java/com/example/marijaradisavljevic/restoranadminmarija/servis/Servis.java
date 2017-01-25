package com.example.marijaradisavljevic.restoranadminmarija.servis;


import com.example.marijaradisavljevic.restoranadminmarija.database.FoodMenuItem;
import com.example.marijaradisavljevic.restoranadminmarija.database.Order;
import com.example.marijaradisavljevic.restoranadminmarija.database.Rezervation;
import com.example.marijaradisavljevic.restoranadminmarija.database.SelecionRegulations;
import com.example.marijaradisavljevic.restoranadminmarija.database.UserInfo;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.FreagmentAddOrder;

import java.util.ArrayList;

import java.util.concurrent.Semaphore;

/**
 * Created by marija.radisavljevic on 6/3/2016.
 */
public class Servis {
    private static Servis instance = new Servis();
    public static Servis getInstance() {return instance; }

    private Semaphore mutex;

    private UserInfo userInfo; //current user
    String[] listUsersTypes;
    private ArrayList<UserInfo> listUsers;
    private String[] listaTable;
    private String[] numberItemssStrignList;
    private ArrayList<FoodMenuItem> listFoodMenuItem;
    private ArrayList<Rezervation> listOfRezervations;


    public Servis() {

        mutex = new Semaphore(1);
        listUsersTypes = new String[2];
        listUsersTypes[0]= "konobar";
        listUsersTypes[1]= "admin";
        listUsers = new ArrayList<UserInfo>();


        UserInfo userInfo1 = new UserInfo();
        userInfo1.setEmail("marijarad89@gmail.com");
        userInfo1.setName("Marija");
        userInfo1.setSurname("Radisavljevic");
        userInfo1.setNumber("060123789");
        userInfo1.setUsername("marijarad89@gmail.com");
        userInfo1.setType("admin");
        userInfo1.setPassword("sifra");

        userInfo = userInfo1;

        listUsers.add(userInfo1);
        userInfo1 = new UserInfo();
        userInfo1.setEmail("anailic@gmail.com");
        userInfo1.setName("Ana");
        userInfo1.setSurname("Ilic");
        userInfo1.setNumber("060123789");
        userInfo1.setUsername("anailic@gmail.com");
        userInfo1.setType("konobar");
        userInfo1.setPassword("sifra");
        listUsers.add(userInfo1);
        userInfo1 = new UserInfo();
        userInfo1.setEmail("paja@gmail.com");
        userInfo1.setName("Pavle");
        userInfo1.setSurname("Stojanovic");
        userInfo1.setNumber("060123789");
        userInfo1.setUsername("paja@gmail.com");
        userInfo1.setType("konobar");
        userInfo1.setPassword("sifra");
        listUsers.add(userInfo1);

        numberItemssStrignList = new String[7];
        numberItemssStrignList[0] = "1";
        numberItemssStrignList[1] ="2" ;
        numberItemssStrignList[2] ="3";
        numberItemssStrignList[3] ="4";
        numberItemssStrignList[4] ="5";
        numberItemssStrignList[5] = "6";
        numberItemssStrignList[6] = "broj komada";

        listaTable = new String[6];
        listaTable[0] = "1";
        listaTable[1] ="2" ;
        listaTable[2] ="3";
        listaTable[3] ="4";
        listaTable[4] ="5";
        listaTable[5] = "broj stola";


        listFoodMenuItem = new ArrayList<FoodMenuItem>();
        FoodMenuItem nadtavka  = new FoodMenuItem(null,"pice", 100);
        listFoodMenuItem.add(nadtavka);
        FoodMenuItem fmt1 = new FoodMenuItem(nadtavka,"1 type food", 100);
        listFoodMenuItem.add(fmt1);
        FoodMenuItem fmt2 = new FoodMenuItem(nadtavka,"2 type food", 100);
        listFoodMenuItem.add(fmt2);

        FoodMenuItem fmt3 = new FoodMenuItem(nadtavka,"3 type food", 100);
        listFoodMenuItem.add(fmt3);

        FoodMenuItem fmt4 = new FoodMenuItem(nadtavka,"4 type food", 100);
        listFoodMenuItem.add(fmt4);







        listOfRezervations = new ArrayList<Rezervation>();


        Rezervation ld = new Rezervation();
        ld.setUsername("marijarad89@gmail.com");
        ld.setNameType("admin");
        ld.setName_user("marija radisavljevic");
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
        ld.setUsername("anailic@gmail.com");
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
        ld.setUsername("paja@gmail.com");
        ld.setName_user("pavle stojanovic");
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


    public String toolBarTypeNameSurnameString(){
        String bla = userInfo.getType()+" : "+userInfo.getName()+" "+userInfo.getSurname();
        return bla;
    }

    public  Boolean logIN(String username, String password) {
//make userinfo if user exists in database
        Boolean value = false;
        try{
            mutex.acquire();
            //for test
            for(UserInfo rez: listUsers){
                if(rez.getUsername().equals("marijarad89@gmail.com") && rez.getPassword().equals("sifra")){
                    userInfo = rez;
                    value = true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }

        return value;

    }



    public ArrayList<Rezervation> getRezervationsWithRegulation(SelecionRegulations selecionRegulation) {
        if(selecionRegulation.isAll()){

            return listOfRezervations;
        }

        ArrayList<Rezervation> returnRezerList = new ArrayList<>();


        try{
            mutex.acquire();

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


        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }

        return returnRezerList;

    }

    public UserInfo getUserInfofromList(String username, String password){
        UserInfo ui = new UserInfo();
        try{
            mutex.acquire();
            for(UserInfo rez: listUsers){
                if(rez.getUsername().equals(username) && rez.getPassword().equals(password)){
                    ui =  rez;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }
        return ui;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }



    public void setUserInfo(UserInfo ui){
            try{
                mutex.acquire();

                for(UserInfo rez: listUsers){
                    //if already exists indatabase update it
                    if(rez.getUsername().equals(ui.getUsername()) && rez.getPassword().equals(ui.getPassword())){
                        listUsers.remove(rez);
                        listUsers.add(ui);
                        break;
                    }
                }
            userInfo = ui;

        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }
    }




    public String[] stringListofFoodItems (){
        ArrayList<String> returnStringList = new ArrayList<String>();
        String[] stringList = null;
        try{
            mutex.acquire();



            for(FoodMenuItem foodMenuItem : listFoodMenuItem ){
                returnStringList.add(foodMenuItem.getFood());
            }
        returnStringList.add("kategory");
         stringList = new String[returnStringList.size()];
        stringList = returnStringList.toArray(stringList);


        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }
        return  stringList;
    }


    public void removeRezer(int id) {
        try{
            mutex.acquire();


        for(Rezervation rez: listOfRezervations){
            if(rez.getId() == id){
                listOfRezervations.remove(rez);
                break;
            }

        }

    } catch (Exception e) {
        e.printStackTrace();

    }finally{
        mutex.release();
    }
    }

    public void makeuserinfoIntoList(UserInfo ui) {
        try{
            mutex.acquire();

        listUsers.add(ui);
        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }

    }

    public ArrayList<UserInfo> getUserList() {
        return listUsers;
    }

    public void removeUser(String username, String password) {
        try{
            mutex.acquire();
        for(UserInfo rez: listUsers){
            if(rez.getUsername().equals(username) && rez.getPassword().equals(password)){
                listUsers.remove(rez);
                break;
            }

        }
        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }
    }

    public void updateUserInfoFromList(UserInfo ui, String usernameString, String passordSrting) {
        try{
            mutex.acquire();

        for(UserInfo rez: listUsers){
            if(rez.getUsername().equals(usernameString) && rez.getPassword().equals(passordSrting)){
                //rez = ui;
                listUsers.remove(rez);
                break;
            }

        }
        listUsers.add(ui);
        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }
    }

    public String[] stringlistUserNames() {
        ArrayList<String> returnStringList = new ArrayList<String>();
        String[] stringList = null;
        try{
            mutex.acquire();

         returnStringList = new ArrayList<String>();

        for(UserInfo ui: listUsers){
            returnStringList.add(ui.getUsername());
        }
        returnStringList.add("users");
        stringList = new String[returnStringList.size()];
        stringList = returnStringList.toArray(stringList);


        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }

        return  stringList;
    }

    public String[] stringListofTables (){
        return  listaTable;
    }


    public String[] strignListTypeOFUsers() {
        String[] bla = null ;
        try{
            mutex.acquire();


        bla = new String[3];
        bla[2] = "type of user";
        bla[1] ="konobar" ;
        bla[0] ="admin";

        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }
        return bla;
    }

    public String[] strignListTypeOFUsersForUserInfo() {
        String[] bla = null;
            try{
                mutex.acquire();


        bla = new String[2];
        bla[1] = "type of user";
        bla[0] = userInfo.getType() ;


            } catch (Exception e) {
                e.printStackTrace();

            }finally{
                mutex.release();
            }
        return bla;
    }

    public ArrayList<Rezervation> getRezervationsWithRegulationForAdmin(SelecionRegulations selecionRegulation) {


        if (selecionRegulation.somethingSelectedadminListRezervations()==false){
            return  listOfRezervations;
        }

        ArrayList<Rezervation> returnRezerList = new ArrayList<>();
            try{
                mutex.acquire();


            for (Rezervation currRezervation : listOfRezervations){

                if (selecionRegulation.isUser_selected()){
                    if (currRezervation.getUsername().equals(selecionRegulation.getUser())){
                        returnRezerList.add(currRezervation);
                    }
                }

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



            } catch (Exception e) {
                e.printStackTrace();

            }finally{
                mutex.release();
            }
        return returnRezerList;

    }

    public void makeNewFoodItem(String kategoryString, String nameString, String priceString) {
        try{
            mutex.acquire();

        FoodMenuItem nadstavka = null;
        FoodMenuItem newItem;
        for(FoodMenuItem currFMI : listFoodMenuItem){
            if(currFMI.getFood().equals(kategoryString)){
                nadstavka = currFMI;
                break;
            }
        }

        newItem = new FoodMenuItem( nadstavka,  nameString,  Integer.parseInt(priceString));
        listFoodMenuItem.add(newItem);

        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }
    }

    public ArrayList<FoodMenuItem> getfoodmenuitemslist() {
            return listFoodMenuItem;
    }

    public void removeFootMenuItem(int id) {
        try{
            mutex.acquire();

            for(FoodMenuItem fmi:listFoodMenuItem){
                if (fmi.getId()==id){
                    listFoodMenuItem.remove(fmi);
                    break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }
    }

    public FoodMenuItem getFootMenuItem(String foodItemId) {
        FoodMenuItem value = null;
        try{
            mutex.acquire();
        for(FoodMenuItem fmi : listFoodMenuItem){
            if (fmi.getId()==Integer.parseInt(foodItemId)){
                value =  fmi;
            }

        }

        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }

        return value;

    }
    public String getTimeForRezervation(String rezervationIdString) {
        String value = "";

        try{
            mutex.acquire();

            for(Rezervation rez: listOfRezervations){
                if(rez.getId() == Integer.parseInt(rezervationIdString)){

                    value = rez.gettime();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }

        return value;
    }

    public boolean getPaidOrNot(String rezervationIdString) {
        boolean value = false;

        try{
            mutex.acquire();

            for(Rezervation rez: listOfRezervations){
                if(rez.getId() == Integer.parseInt(rezervationIdString)){

                    value =  rez.isPaidOrNot();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }
        return  value;

    }
    public int getNumberOFtable(String rezervationIdString) {

        int value =-1;

        try{
            mutex.acquire();

            for(Rezervation rez: listOfRezervations){
                if(rez.getId() == Integer.parseInt(rezervationIdString)){

                    value = rez.getnumberTable();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }

        return value;
    }
    public ArrayList<Order> getListOrders(String rezervationIdString) {
        ArrayList<Order> value = null;
        try{
            mutex.acquire();

            for(Rezervation rez: listOfRezervations){
                if(rez.getId() == Integer.parseInt(rezervationIdString)){
                    value = rez.getOrders();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }
        return value;
    }

    public void AddRezervation(String id, String time,String nuberTable, boolean ispaidnOrnot,ArrayList<FreagmentAddOrder.ItemOrder> listaOrder ){
        try{
            mutex.acquire();
            for(Rezervation rez: listOfRezervations){
                if(rez.getId()== Integer.parseInt(id)){
                    //update info

                    rez.setPassword(userInfo.getPassword());
                    rez.setUsername(userInfo.getUsername());
                    rez.setTime(time);
                    rez.setPaidOrNot(ispaidnOrnot);
                    rez.setNumberTable(Integer.parseInt(nuberTable));
                    ArrayList<Order> lista = new ArrayList<Order>();
                    for(FreagmentAddOrder.ItemOrder curOrder : listaOrder){
                        lista.add(curOrder.getOrder());
                    }
                    rez.setOrders(lista);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }


    }


    public String[] getNumberItems() {
        return numberItemssStrignList;
    }

    public void addOrder(int id, String numberOfItems, String Kategory) {
        try{
            mutex.acquire();
            for(Rezervation rez: listOfRezervations){
                if(rez.getId()== id){

                          /*  boolean find = null;
                            for(Order curOrd: rez.getOrders()){
                                if(curOrd.getOrder().getFood().equals(Kategory)){
                                    curOrd.setNuberOrder(curOrd.getNuberOrder()+numberOfItems);
                                    find = true;
                                    break;

                                }
                            }*/
                    // if (find==null) {
                    Order ord = new Order();

                    ord.setNuberOrder(Integer.parseInt(numberOfItems));
                    for (FoodMenuItem fmi : listFoodMenuItem) {
                        if (fmi.getFood().equals(Kategory)) {
                            ord.setOrder(fmi);
                            break;
                        }

                    }
                    rez.getOrders().add(ord);
                    //}

                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }
    }

    public void removeorderForRezer(Order o,String rezervationIdString) {
        try{
            mutex.acquire();

            for(Rezervation rez: listOfRezervations){
                if(rez.getId() == Integer.parseInt(rezervationIdString) ){
                    for(Order currOrd:rez.getOrders()){
                        if(o.getId()==currOrd.getId()){
                            rez.getOrders().remove(currOrd);
                            break;
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }

    }

    public void updateFoodMenuItem(String foodItemId, String kategoryString, String nameString, String priceString) {

        for(FoodMenuItem fmi:listFoodMenuItem){
            if (fmi.getId()==Integer.parseInt(foodItemId)){
                for(FoodMenuItem nadstavkatek:listFoodMenuItem){
                  if(nadstavkatek.getFood().equals(kategoryString)){
                      fmi.setNadstavka(nadstavkatek);
                      break;
                  }

                }
                fmi.setFood(nameString);
                fmi.setPrice(Integer.parseInt(priceString));
            }

        }
    }

    public String newRezervation (){
        Integer i=-1;
        try{
            mutex.acquire();

            Rezervation r = new Rezervation();
            listOfRezervations.add(r);
            i = r.getId();


        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }
        return String.valueOf(i);
    }


    public boolean isUserAdmin() {
        return userInfo.getType().equals("admin");
    }
}
