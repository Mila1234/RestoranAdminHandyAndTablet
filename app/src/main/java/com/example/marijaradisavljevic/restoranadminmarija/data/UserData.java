package com.example.marijaradisavljevic.restoranadminmarija.data;


import com.example.marijaradisavljevic.restoranadminmarija.database.SelecionRegulations;

/**
 * Created by marija.radisavljevic on 5/17/2016.
 */
public class UserData {

    private static UserData instance = new UserData();


    public static UserData getInstance() {return instance; }

    public UserData() {
         //userType = Servis.getInstance().UserType();
        //user = Servis.getInstance().NameOfUser();
    }





   // private String user;
   // private String userType;

    //selection
    private String numberOfTable;
    private boolean numberOfTable_selectied=false;

    private boolean paidOrNot;
    private boolean paidOrNot_selected=false;

    private String kategory = "";
    private boolean kategory_selected=false;

    private boolean all=false;

    public SelecionRegulations getSelecionRegulation (){
        SelecionRegulations sr = new SelecionRegulations();

        sr.setAll(all) ;
        sr.setNumberOfTable(numberOfTable);
        sr.setNumberOfTable_selectied(numberOfTable_selectied);
        sr.setPaidOrNot(paidOrNot) ;
        sr.setPaidOrNot_selected(paidOrNot_selected) ;
        sr.setKategory(kategory);
        sr.setKategory_selected(kategory_selected) ;

        return sr;
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public String getNumberOfTable() {
        return numberOfTable;
    }

    public void setNumberOfTable(String numberOfTable) {
        this.numberOfTable = numberOfTable;
    }

    public boolean isNumberOfTable_selectied() {
        return numberOfTable_selectied;
    }

    public void setNumberOfTable_selectied(boolean numberOfTable_selectied) {
        this.numberOfTable_selectied = numberOfTable_selectied;
    }

    public boolean isPaidOrNot() {
        return paidOrNot;
    }

    public void setPaidOrNot(boolean paidOrNot) {

        this.paidOrNot = paidOrNot;
    }

    public boolean isPaidOrNot_selected() {
        return paidOrNot_selected;
    }

    public void setPaidOrNot_selected(boolean paidOrNot_selected) {
        this.paidOrNot_selected = paidOrNot_selected;
    }

    public String getKategory() {
        return kategory;
    }

    public void setKategory(String kategory) {
        this.kategory = kategory;
    }

    public boolean isKategory_selected() {
        return kategory_selected;
    }

    public void setKategory_selected(boolean kategory_selected) {
        this.kategory_selected = kategory_selected;
    }

/*
    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserType() {
        return userType;
       // return "Konobar";
    }

    public String getUser() {
        return user;
       // return "marija radisavljevic";
    }*/
}
