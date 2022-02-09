package com.nurseify.app.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingModel {

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


    public class Data {

        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("full_name")
        @Expose
        private String fullName;
        @SerializedName("profile_picture")
        @Expose
        private String profilePicture;
        @SerializedName("profile_picture_base")
        @Expose
        private String profile_picture_base;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("postcode")
        @Expose
        private String postcode;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("nursing_license_number")
        @Expose
        private String nursingLicenseNumber;
        @SerializedName("bil_rate")
        @Expose
        private String bilRate;
        @SerializedName("experience")
        @Expose
        private String experience;
        @SerializedName("shift_definition")
        @Expose
        private String shiftDefinition;
        @SerializedName("shift")
        @Expose
        private String shift;

        public String getProfile_picture_base() {
            return profile_picture_base;
        }

        public void setProfile_picture_base(String profile_picture_base) {
            this.profile_picture_base = profile_picture_base;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getNursingLicenseNumber() {
            return nursingLicenseNumber;
        }

        public void setNursingLicenseNumber(String nursingLicenseNumber) {
            this.nursingLicenseNumber = nursingLicenseNumber;
        }

        public String getBilRate() {
            return bilRate;
        }

        public void setBilRate(String bilRate) {
            this.bilRate = bilRate;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getShiftDefinition() {
            return shiftDefinition;
        }

        public void setShiftDefinition(String shiftDefinition) {
            this.shiftDefinition = shiftDefinition;
        }

        public String getShift() {
            return shift;
        }

        public void setShift(String shift) {
            this.shift = shift;
        }

    }

}
