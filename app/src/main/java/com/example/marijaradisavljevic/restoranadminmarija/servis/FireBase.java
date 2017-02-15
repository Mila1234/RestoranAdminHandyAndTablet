package com.example.marijaradisavljevic.restoranadminmarija.servis;


import android.support.annotation.NonNull;
import android.util.Log;

import com.example.marijaradisavljevic.restoranadminmarija.database.FoodMenuItem;
import com.example.marijaradisavljevic.restoranadminmarija.database.Order;
import com.example.marijaradisavljevic.restoranadminmarija.database.Rezervation;
import com.example.marijaradisavljevic.restoranadminmarija.data.SelecionRegulations;
import com.example.marijaradisavljevic.restoranadminmarija.database.UserInfo;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.FreagmentAddOrder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Semaphore;

import static android.content.ContentValues.TAG;

/**
 * Created by marija.radisavljevic on 6/3/2016.
 */
public class FireBase {
    private static FireBase instance = new FireBase();
    public static FireBase getInstance() {return instance; }

    private Semaphore mutex;

    private UserInfo userInfo; //current user
    String[] listUsersTypes;
    private String[] listaTable;//broj stolova
    private String[] numberItemssStrignList;//broj komada


    private ArrayList<UserInfo> listUsers;
    private ArrayList<FoodMenuItem> listFoodMenuItem;
    private ArrayList<Rezervation> listOfRezervations;



    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private FirebaseAuth mAuth;


    private void writeNewUser (final UserInfo ui){
        mAuth.createUserWithEmailAndPassword(ui.getEmail()  , ui.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());


                        if (task.isSuccessful()) {
                            onAuthSuccess(ui,task.getResult().getUser());
                        } else {
                            Log.d(TAG, "createUser:onComplete:" + task.getException().getMessage());
                        }
                    }
                });
    }

    private void onAuthSuccess(UserInfo ui, FirebaseUser user) {



        mDatabase.child("users").child(user.getUid()).setValue(ui);
        // Write new user


    }

    private void writeNewRezervation(Rezervation rez) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("listaRezervations").push().getKey();
       // String k = mDatabase.getKey();
       // Map<String, Object> postValues = rez.toMap();
       // Map<String, Object> childUpdates = new HashMap<>();


       // childUpdates.put("/listaRezervations/" + key, postValues);
       // childUpdates.put("/user-posts/" + userId + "/" + key, postValues);



        mDatabase.child("listaRezervations").child(key).setValue(rez);
        //mDatabase.updateChildren(childUpdates);
    }


    public FireBase() {
        mAuth = FirebaseAuth.getInstance();
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mutex = new Semaphore(1);
        listUsersTypes = new String[2];
        listUsersTypes[0]= "Konobar";
        listUsersTypes[1]= "Admin";
        listUsers = new ArrayList<UserInfo>();


        UserInfo userInfo1 = new UserInfo();
        userInfo1.setEmail("marijarad89@gmail.com");
        userInfo1.setName("Marija");
        userInfo1.setSurname("Radisavljevic");
        userInfo1.setNumber("060123789");
        userInfo1.setUsername("marijarad89@gmail.com");
        userInfo1.setType("Admin");
        userInfo1.setPassword("maricka71");
       // writeNewUser(userInfo1);
      userInfo = userInfo1;


        listUsers.add(userInfo1);
        userInfo1 = new UserInfo();
        userInfo1.setEmail("anailic@gmail.com");
        userInfo1.setName("Ana");
        userInfo1.setSurname("Ilic");
        userInfo1.setNumber("060123789");
        userInfo1.setUsername("anailic@gmail.com");
        userInfo1.setType("Konobar");
        userInfo1.setPassword("maricka71");

        listUsers.add(userInfo1);
       // writeNewUser(userInfo1);

        userInfo1 = new UserInfo();
        userInfo1.setEmail("paja@gmail.com");
        userInfo1.setName("Pavle");
        userInfo1.setSurname("Stojanovic");
        userInfo1.setNumber("060123789");
        userInfo1.setUsername("paja@gmail.com");
        userInfo1.setType("Konobar");
        userInfo1.setPassword("sifra!23456");
        listUsers.add(userInfo1);
       // writeNewUser(userInfo1);

        numberItemssStrignList = new String[7];
        numberItemssStrignList[0] = "1";
        numberItemssStrignList[1] ="2" ;
        numberItemssStrignList[2] ="3";
        numberItemssStrignList[3] ="4";
        numberItemssStrignList[4] ="5";
        numberItemssStrignList[5] = "6";
        numberItemssStrignList[6] = "broj komada";

        listaTable = new String[7];
        listaTable[6] = "6";
        listaTable[5] = "5";
        listaTable[4] ="4" ;
        listaTable[3] ="3";
        listaTable[2] ="2";
        listaTable[1] ="1";
        listaTable[0] = "broj stola";


        listFoodMenuItem = new ArrayList<FoodMenuItem>();
        FoodMenuItem nadtavka  = new FoodMenuItem(null,"pice", 100);
        listFoodMenuItem.add(nadtavka);
        String key = mDatabase.child("listaRezervations").push().getKey();
     //  mDatabase.child("foodMenu").child(key).setValue(nadtavka);

        FoodMenuItem fmt1 = new FoodMenuItem(nadtavka,"1 type food", 100);
        listFoodMenuItem.add(fmt1);
         key = mDatabase.child("listaRezervations").push().getKey();
        //mDatabase.child("foodMenu").child(key).setValue(fmt1);

        FoodMenuItem fmt2 = new FoodMenuItem(nadtavka,"2 type food", 100);
        listFoodMenuItem.add(fmt2);
         key = mDatabase.child("listaRezervations").push().getKey();
      /// mDatabase.child("foodMenu").child(key).setValue(fmt2);


        FoodMenuItem fmt3 = new FoodMenuItem(nadtavka,"3 type food", 100);
        listFoodMenuItem.add(fmt3);
         key = mDatabase.child("listaRezervations").push().getKey();
       // mDatabase.child("foodMenu").child(key).setValue(fmt3);


        FoodMenuItem fmt4 = new FoodMenuItem(nadtavka,"4 type food", 100);
        listFoodMenuItem.add(fmt4);
         key = mDatabase.child("listaRezervations").push().getKey();
       // mDatabase.child("foodMenu").child(key).setValue(fmt4);








        listOfRezervations = new ArrayList<Rezervation>();


        Rezervation ld = new Rezervation();
        ld.setUsername("marijarad89@gmail.com");
        ld.setNameType("Admin");
        ld.setName_user("marija radisavljevic");
        ///ld.setItemsOrder(new ArrayList(Arrays.asList("kapucino , truska kafa, lenja pita sa jabukama")));
        ArrayList<Order> listOrders = new ArrayList<Order>();
        listOrders.add(new Order(fmt1,1));
        listOrders.add(new Order(fmt2,1));
        listOrders.add(new Order(fmt3,1));

        ld.setOrders(listOrders);
        ld.setNumberTable(3);
        ld.setPaidOrNot(true);
        ld.setPassword("maricka71");
        ld.setTime("5.5.2016. 17:30 ");
        ld.setId(555);
        listOfRezervations.add(ld);
       //writeNewRezervation(ld);


        ld = new Rezervation();
        ld.setUsername("anailic@gmail.com");
        ld.setNameType("Konobar");
        ld.setName_user("Ana Ilic");
        //ld.setItemsOrder(new ArrayList(Arrays.asList("kapucino , truska kafa, lenja pita sa jabukama")));
        listOrders = new ArrayList<Order>();
        listOrders.add(new Order(fmt1,9));
        listOrders.add(new Order(fmt2,9));
        listOrders.add(new Order(fmt3,9));
        ld.setOrders(listOrders);
        ld.setNumberTable(2);
        ld.setPaidOrNot(true);
        ld.setPassword("sifra123456!");
        ld.setId(22);
        ld.setTime("5.5.2016. 18:30 ");
        listOfRezervations.add(ld);
       //writeNewRezervation(ld);


        ld = new Rezervation();
        ld.setNameType("Konobar");
        ld.setUsername("paja@gmail.com");
        ld.setName_user("pavle stojanovic");
        ld.setPassword("sifra!23456");
        //  ld.setItemsOrder(new ArrayList(Arrays.asList("jelen pivo ,crveno vino , lenja pita sa jabukama")));
        listOrders = new ArrayList<Order>();
        listOrders.add(new Order(fmt1,12));
        listOrders.add(new Order(fmt2,13));
        listOrders.add(new Order(fmt3,13));
        ld.setOrders(listOrders);
        ld.setNumberTable(1);
        ld.setId(7);
        ld.setPaidOrNot(true);

        ld.setTime("5.5.2016. 17:00 ");
        listOfRezervations.add(ld);
     // writeNewRezervation(ld);


    }


    public String toolBarTypeNameSurnameString(){
        String bla = userInfo.getType()+" : "+userInfo.getName()+" "+userInfo.getSurname();
        return bla;
    }


    //sluzida incijalizuje Firebase insace
    public  Boolean logIN(String username, String password) {
//postavi current username


        return true;

    }



    public ArrayList<Rezervation> getRezervationsWithRegulation(final SelecionRegulations selecionRegulation) {
        if(selecionRegulation.isAll()){

            return listOfRezervations;
        }

        final ArrayList<Rezervation> returnRezerList = new ArrayList<>();





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
           userInfo = ui;
    }




    public String[] stringListofFoodItems (){
        ArrayList<String> returnStringList = new ArrayList<String>();
        String[] stringList = null;
        try{
            mutex.acquire();
            returnStringList.add("kategory");


            for(FoodMenuItem foodMenuItem : listFoodMenuItem ){
                returnStringList.add(foodMenuItem.getFood());
            }

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
            returnStringList.add("users");
        for(UserInfo ui: listUsers){
            returnStringList.add(ui.getUsername());
        }

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
        bla[1] ="Konobar" ;
        bla[0] ="Admin";

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

                    value = rez.getTime();
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
/*
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
                    LinkedList<Order> lista = new LinkedList<Order>();
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

*/
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
        return userInfo.getType().equals("Admin");
    }

    public String getUserForRezervation(String rezervationIdString) {
        String value = "";

        try{
            mutex.acquire();

            for(Rezervation rez: listOfRezervations){
                if(rez.getId() == Integer.parseInt(rezervationIdString)){

                    value = rez.getNameType()+" : "+rez.getname_user();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            mutex.release();
        }

        return value;
    }
}
