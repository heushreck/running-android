package com.example.enseirb_neudecknicolas_satomidavid.DataClasses;

import java.io.Serializable;

public class User implements Serializable {

    private String first_name;
    private String last_name;
    private String gender;
    private int id;


    public User(String first_name, String last_name, String gender) {

        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
