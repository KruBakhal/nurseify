package com.weboconnect.nurseify.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HourlyRate_DayOfWeek_OptionDatum {
    public HourlyRate_DayOfWeek_OptionDatum(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HourlyRate_DayOfWeek_data_OptionModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
