package com.weboconnect.nurseify.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourlyRate_DayOfWeek_OptionModel {
    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<HourlyRate_DayOfWeek_OptionDatum> data = null;

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

    public List<HourlyRate_DayOfWeek_OptionDatum> getData() {
        return data;
    }

    public void setData(List<HourlyRate_DayOfWeek_OptionDatum> data) {
        this.data = data;
    }

    public class HourlyRate_DayOfWeek_OptionDatum {

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

    @Override
    public String toString() {
        return "HourlyRate_DayOfWeek_OptionModel{" +
                "apiStatus='" + apiStatus + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
