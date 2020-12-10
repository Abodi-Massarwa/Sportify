package com.example.sportify;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String lastName;
    private String area;
    private String number;
    private String email;
    private String password;

    //private String passWord;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    // In SignUp
    public User(String name, String lastname, String area, String number, String email) {
        this.name = name;
        this.email = email;
        this.lastName = lastname;
        this.area = area;
        this.number = number;
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFullName() { return this.getName() + " " + this.getLastName(); }
    public void setPassowrd(String password){this.password = password;}
    public String getPassword(){ return this.password;}
    public String toString()
    {
        return this.name+", "+this.lastName +", "+this.number;
    }
}