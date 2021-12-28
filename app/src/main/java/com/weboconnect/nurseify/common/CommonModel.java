package com.weboconnect.nurseify.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommonModel {
        @SerializedName("api_status")
        @Expose
        private String apiStatus;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<CommonDatum> data = null;

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

        public List<CommonDatum> getData() {
            return data;
        }

        public void setData(List<CommonDatum> data) {
            this.data = data;
        }


    }
