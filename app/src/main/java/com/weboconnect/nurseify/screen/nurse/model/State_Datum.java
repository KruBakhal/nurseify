package com.weboconnect.nurseify.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State_Datum {

@SerializedName("state")
@Expose
private String state;

public String getState() {
return state;
}

public void setState(String state) {
this.state = state;
}

}