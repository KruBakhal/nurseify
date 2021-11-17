package com.weboconnect.nurseify.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CernersModel {

@SerializedName("api_status")
@Expose
private String apiStatus;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private List<CernersDatum> data = null;

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

public List<CernersDatum> getData() {
return data;
}

public void setData(List<CernersDatum> data) {
this.data = data;
}

}