package com.nurseify.app.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State_Datum {
    public State_Datum(String state_id, String name) {
        this.state_id = state_id;
        this.name = name;
    }

    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("state_id")
    @Expose
    private String state_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("iso_name")
    @Expose
    private String iso_name;

    public String getIso_name() {
        return iso_name;
    }

    public void setIso_name(String iso_name) {
        this.iso_name = iso_name;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNames() {
        return name;
    }

//    public void setState(String state) {
//        this.state = state;
//    }

}