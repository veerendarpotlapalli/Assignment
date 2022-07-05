package com.example.assignment;

import com.google.gson.annotations.SerializedName;

public class model {
    public model(String name, String username, String email, String street, String city, int zipcode) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }

    String name,username,email,street,city;
    int zipcode;

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public int getZipcode() {
        return zipcode;
    }
}//model
