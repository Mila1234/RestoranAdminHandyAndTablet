package com.example.marijaradisavljevic.restoranadminmarija.servis;



import com.example.marijaradisavljevic.restoranadminmarija.R;
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

    private UserInfo userInfo; //current user
    String[] listUsersTypes;
    private ArrayList<UserInfo> listUsers;
    private String[] listaTable;
    private String[] numberItemssStrignList;
    private ArrayList<FoodMenuItem> listFoodMenuItem;
    private ArrayList<Rezervation> listOfRezervations;


    public Servis() {
        listUsersTypes = new String[2];
        listUsersTypes[0]= "konobar";
        listUsersTypes[1]= "admin";
        listUsers = new ArrayList<UserInfo>();


        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("marijarad89@gmail.com");
        userInfo.setName("Marija");
        userInfo.setSurname("Radisavljevic");
        userInfo.setNumber("060123789");
        userInfo.setUsername("marijarad89@gmail.com");
        userInfo.setType("konobar");
        userInfo.setPassword("sifra");

        listUsers.add(userInfo);
         userInfo = new UserInfo();
        userInfo.setEmail("anailic@gmail.com");
        userInfo.setName("Ana");
        userInfo.setSurname("Ilic");
        userInfo.setNumber("060123789");
        userInfo.setUsername("anailic@gmail.com");
        userInfo.setType("konobar");
        userInfo.setPassword("sifra");
        listUsers.add(userInfo);
         userInfo = new UserInfo();
        userInfo.setEmail("paja@gmail.com");
        userInfo.setName("Pavle");
        userInfo.setSurname("Stojanovic");
        userInfo.setNumber("060123789");
        userInfo.setUsername("paja@gmail.com");
        userInfo.setType("konobar");
        userInfo.setPassword("sifra");
        listUsers.add(userInfo);

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

        //for test
        for(UserInfo rez: listUsers){
            if(rez.getUsername().equals("marijarad89@gmail.com") && rez.getPassword().equals("sifra")){
                userInfo = rez;
                return true;
            }

        }
        return false;

    }
    public UserInfo getUserInfofromList(String username, String password){
        for(UserInfo rez: listUsers){
            if(rez.getUsername().equals(username) && rez.getPassword().equals(password)){
                return rez;
            }

        }
        return new UserInfo();
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }



    public void setUserInfo(UserInfo ui){
        for(UserInfo rez: listUsers){
            //if already exists indatabase update it
            if(rez.getUsername().equals(ui.getUsername()) && rez.getPassword().equals(ui.getPassword())){
                listUsers.remove(rez);
                listUsers.add(ui);
            }
        }
        userInfo = ui;
    }




    public String[] stringListofFoodItems (){
        ArrayList<String> returnStringList = new ArrayList<String>();

        for(FoodMenuItem foodMenuItem : listFoodMenuItem ){
            returnStringList.add(foodMenuItem.getFood());
        }
        returnStringList.add("kategory");
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

    public void makeuserinfoIntoList(UserInfo ui) {
        listUsers.add(ui);

    }

    public ArrayList<UserInfo> getUserList() {
        return listUsers;
    }

    public void removeUser(String username, String password) {

        for(UserInfo rez: listUsers){
            if(rez.getUsername().equals(username) && rez.getPassword().equals(password)){
                listUsers.remove(rez);
                return;
            }

        }
    }

    public void updateUserInfoFromList(UserInfo ui, String usernameString, String passordSrting) {
        for(UserInfo rez: listUsers){
            if(rez.getUsername().equals(usernameString) && rez.getPassword().equals(passordSrting)){
                //rez = ui;
                listUsers.remove(rez);
                break;
            }

        }
        listUsers.add(ui);
    }

    public String[] stringlistUserNames() {
        ArrayList<String> returnStringList = new ArrayList<String>();

        for(UserInfo ui: listUsers){
            returnStringList.add(ui.getUsername());
        }
        returnStringList.add("users");
        String[] stringList = new String[returnStringList.size()];
        stringList = returnStringList.toArray(stringList);
        return  stringList;
    }

    public String[] stringListofTables (){
        return  listaTable;
    }


    public String[] strignListTypeOFUsers() {

        String[] bla;
        bla = new String[3];
        bla[2] = "type of user";
        bla[1] ="konobar" ;
        bla[0] ="admin";


        return bla;
    }

    public String[] strignListTypeOFUsersForUserInfo() {
        String[] bla;
        bla = new String[2];
        bla[1] = "type of user";
        bla[0] = userInfo.getType() ;



        return bla;
    }

    public ArrayList<Rezervation> getRezervationsWithRegulationForAdmin(SelecionRegulations selecionRegulation) {

           if (selecionRegulation.somethingSelectedadminListRezervations()==false){
               return  listOfRezervations;
           }


            ArrayList<Rezervation> returnRezerList = new ArrayList<>();
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


            return returnRezerList;


    }

    public void makeNewFoodItem(String kategoryString, String nameString, String priceString) {
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
    }

    public ArrayList<FoodMenuItem> getfoodmenuitemslist() {
            return listFoodMenuItem;
    }
}
