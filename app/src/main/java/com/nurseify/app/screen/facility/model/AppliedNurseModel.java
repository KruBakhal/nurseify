package com.nurseify.app.screen.facility.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppliedNurseModel {

    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<AppliedNurseDatum> data = null;

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

    public List<AppliedNurseDatum> getData() {
        return data;
    }

    public void setData(List<AppliedNurseDatum> data) {
        this.data = data;
    }

    public class AppliedNurseDatum {

        @SerializedName("nurse_id")
        @Expose
        private String nurseId;
        @SerializedName("nurse_user_id")
        @Expose
        private String userId;

        public String getNurseId() {
            return nurseId;
        }

        public void setNurseId(String nurseId) {
            this.nurseId = nurseId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("profile")
        @Expose
        private String profile;
        @SerializedName("profile_base")
        @Expose
        private String profile_base;
        @SerializedName("rating")
        @Expose
        private String rating;

        public String getProfile_base() {
            return profile_base;
        }

        public void setProfile_base(String profile_base) {
            this.profile_base = profile_base;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

    }
}