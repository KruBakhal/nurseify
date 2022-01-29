package com.nurseify.app.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignupModel {


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

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("nurse_id")
        @Expose
        private String nurseId;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("fcm_token")
        @Expose
        private String fcmToken;
        @SerializedName("fullName")
        @Expose
        private String fullName;
        @SerializedName("date_of_birth")
        @Expose
        private String dateOfBirth;
        @SerializedName("email_notification")
        @Expose
        private Integer emailNotification;
        @SerializedName("sms_notification")
        @Expose
        private Integer smsNotification;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("nursing_license_state")
        @Expose
        private String nursingLicenseState;
        @SerializedName("nursing_license_number")
        @Expose
        private String nursingLicenseNumber;
        @SerializedName("specialty")
        @Expose
        private List<String> specialty = null;
        @SerializedName("work_location")
        @Expose
        private Integer workLocation;
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
        @SerializedName("hourly_pay_rate")
        @Expose
        private String hourlyPayRate;
        @SerializedName("shift_duration")
        @Expose
        private String shiftDuration;
        @SerializedName("assignment_duration")
        @Expose
        private String assignmentDuration;
        @SerializedName("preferred_shift")
        @Expose
        private String preferredShift;
        @SerializedName("days_of_the_week")
        @Expose
        private List<Object> daysOfTheWeek = null;
        @SerializedName("earliest_start_date")
        @Expose
        private String earliestStartDate;
        @SerializedName("profile_detail_flag")
        @Expose
        private String profileDetailFlag;
        @SerializedName("hourly_rate_and_avail_flag")
        @Expose
        private String hourlyRateAndAvailFlag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNurseId() {
            return nurseId;
        }

        public void setNurseId(String nurseId) {
            this.nurseId = nurseId;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getFcmToken() {
            return fcmToken;
        }

        public void setFcmToken(String fcmToken) {
            this.fcmToken = fcmToken;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Integer getEmailNotification() {
            return emailNotification;
        }

        public void setEmailNotification(Integer emailNotification) {
            this.emailNotification = emailNotification;
        }

        public Integer getSmsNotification() {
            return smsNotification;
        }

        public void setSmsNotification(Integer smsNotification) {
            this.smsNotification = smsNotification;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNursingLicenseState() {
            return nursingLicenseState;
        }

        public void setNursingLicenseState(String nursingLicenseState) {
            this.nursingLicenseState = nursingLicenseState;
        }

        public String getNursingLicenseNumber() {
            return nursingLicenseNumber;
        }

        public void setNursingLicenseNumber(String nursingLicenseNumber) {
            this.nursingLicenseNumber = nursingLicenseNumber;
        }

        public List<String> getSpecialty() {
            return specialty;
        }

        public void setSpecialty(List<String> specialty) {
            this.specialty = specialty;
        }

        public Integer getWorkLocation() {
            return workLocation;
        }

        public void setWorkLocation(Integer workLocation) {
            this.workLocation = workLocation;
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

        public String getHourlyPayRate() {
            return hourlyPayRate;
        }

        public void setHourlyPayRate(String hourlyPayRate) {
            this.hourlyPayRate = hourlyPayRate;
        }

        public String getShiftDuration() {
            return shiftDuration;
        }

        public void setShiftDuration(String shiftDuration) {
            this.shiftDuration = shiftDuration;
        }

        public String getAssignmentDuration() {
            return assignmentDuration;
        }

        public void setAssignmentDuration(String assignmentDuration) {
            this.assignmentDuration = assignmentDuration;
        }

        public String getPreferredShift() {
            return preferredShift;
        }

        public void setPreferredShift(String preferredShift) {
            this.preferredShift = preferredShift;
        }

        public List<Object> getDaysOfTheWeek() {
            return daysOfTheWeek;
        }

        public void setDaysOfTheWeek(List<Object> daysOfTheWeek) {
            this.daysOfTheWeek = daysOfTheWeek;
        }

        public String getEarliestStartDate() {
            return earliestStartDate;
        }

        public void setEarliestStartDate(String earliestStartDate) {
            this.earliestStartDate = earliestStartDate;
        }

        public String getProfileDetailFlag() {
            return profileDetailFlag;
        }

        public void setProfileDetailFlag(String profileDetailFlag) {
            this.profileDetailFlag = profileDetailFlag;
        }

        public String getHourlyRateAndAvailFlag() {
            return hourlyRateAndAvailFlag;
        }

        public void setHourlyRateAndAvailFlag(String hourlyRateAndAvailFlag) {
            this.hourlyRateAndAvailFlag = hourlyRateAndAvailFlag;
        }

    }

}
