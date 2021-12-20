package com.weboconnect.nurseify.screen.nurse.sample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SampleModel {

    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SampleModel{" +
                "apiStatus='" + apiStatus + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public class Response {

        @SerializedName("msg")
        @Expose
        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "msg='" + msg + '\'' +
                    '}';
        }
    }

    public class Data {

        @SerializedName("response")
        @Expose
        private Response response;

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "response=" + response +
                    '}';
        }
    }
}