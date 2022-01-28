package com.weboconnect.nurseify.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chatlist {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("receiver")
    @Expose
    private String receiver;
    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("timestamp")
    @Expose
    public long timestamp;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("is_seen")
    @Expose
    private Integer is_seen;

    public Integer getIs_seen() {
        return is_seen;
    }

    public void setIs_seen(Integer is_seen) {
        this.is_seen = is_seen;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Long getTime_stamp() {
        return timestamp;
    }

    public void setTime_stamp(Long time_stamp) {
        this.timestamp = time_stamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
