package com.example.sportify.tools;

import android.net.Uri;

import java.net.URL;

public class Profile {
    String email;
    String name;
    String number;
    String address;
    private String url_image;

    public Profile() {

    }
    public Profile(String email, String name, String number, String image, String address) {
        this.email = email;
        this.name = name;
        this.number = number;
        this.url_image = image;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage_url() {
        return url_image;
    }

    public void setImage_url(String image_url) {
        this.url_image = image_url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
