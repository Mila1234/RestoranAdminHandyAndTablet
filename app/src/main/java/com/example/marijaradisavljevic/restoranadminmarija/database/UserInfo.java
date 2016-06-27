package com.example.marijaradisavljevic.restoranadminmarija.database;

/**
 * Created by marija.radisavljevic on 6/6/2016.
 */
public class UserInfo {
    private String username;
    private String name;
    private String surname ;
    private String number ;
    private String email ;
    private String type;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getnameAndSurname() {
        return name+" "+surname;
    }

    public String getNameSurnameType() {
        return type +" : "+name+" "+surname;
    }

    public String getStringTypeNameSurnameForListUsers() {
        String returnString = "";
        if (type.equals("konobar") || type.equals("admin")){
            returnString  = type;
        }

            returnString =returnString+" : "+ name +" " +surname;

        return returnString;
    }
}
