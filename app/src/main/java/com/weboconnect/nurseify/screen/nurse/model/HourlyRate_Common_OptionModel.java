package com.weboconnect.nurseify.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourlyRate_Common_OptionModel {
    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<HourlyRate_Common_OptionDatum> data = null;

    public String getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HourlyRate_Common_OptionDatum> getData() {
        return data;
    }

    public void setData(List<HourlyRate_Common_OptionDatum> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "HourlyRate_Common_OptionModel{" +
                "apiStatus='" + apiStatus + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
