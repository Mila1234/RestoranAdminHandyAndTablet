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
}
