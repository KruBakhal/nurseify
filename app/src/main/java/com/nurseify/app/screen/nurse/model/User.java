package com.nurseify.app.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nurseify.app.utils.Constant;

import java.util.List;

public class User {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("full_name")
    @Expose
    private String full_name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("profile_path")
    @Expose
    private String profile_path;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("specialty")
    @Expose
    private String specialty;
    @SerializedName(Constant.CHAT_USERS_CHILD)
    @Expose
    private List<Chatlist> chat_users;
    private Chatlist chat_model;

    public Chatlist getChat_model() {
        return chat_model;
    }

    public void setChat_model(Chatlist chat_model) {
        this.chat_model = chat_model;
    }

    public List<Chatlist> getChat_users() {
        return chat_users;
    }

    public void setChat_users(List<Chatlist> chat_users) {
        this.chat_users = chat_users;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setChat_users(Chatlist chat_model) {
        this.chat_model=chat_model;
    }
}
