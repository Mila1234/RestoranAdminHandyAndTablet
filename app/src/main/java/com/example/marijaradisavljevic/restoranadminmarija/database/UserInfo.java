package com.example.marijaradisavljevic.restoranadminmarija.database;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;




/**
 * Created by marija.radisavljevic on 6/6/2016.
 */

@IgnoreExtraProperties
public class UserInfo {
    public String username;
    public String name;
    public String surname ;
    public String number ;
    public String email ;
    public String type;
    public String password;

    @Exclude
    public String key;

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public UserInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }


    public UserInfo (String username, String name,String surname, String number, String email, String type, String password){
        this.name = name;
        this.username = username;
        this.surname = surname;
        this.number = number;
        this.email = email;
        this.type = type;
        this.password = password;

    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("name", name);
        result.put("surname", surname);
        result.put("number", number);
        result.put("email", email);
        result.put("type", type);
        result.put("password", password);
        return result;
    }
    // [END post_to_map]


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   /* @Exclude
    public String getStringTypeNameSurnameForListUsers() {
        String returnString = "";
        if (type.equals("Konobar") || type.equals("Admin")){
            returnString  = type;
        }

            returnString =returnString+" : "+ name +" " +surname;

        return returnString;
    }*/
}
