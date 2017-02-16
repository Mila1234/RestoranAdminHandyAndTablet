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
    public  Boolean inicijalizacija(String username, String password) {
//postavi current username
        return true;

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



    public String[] getNumberItems() {
        return numberItemssStrignList;
    }

    public boolean isUserAdmin() {
        return userInfo.getType().equals("Admin");
    }


}
