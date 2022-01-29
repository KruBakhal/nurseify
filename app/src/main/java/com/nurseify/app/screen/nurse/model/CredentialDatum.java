package com.nurseify.app.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CredentialDatum {
    public CredentialDatum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @SerializedName("id")
@Expose
private Integer id;
@SerializedName("name")
@Expose
private String name;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

}