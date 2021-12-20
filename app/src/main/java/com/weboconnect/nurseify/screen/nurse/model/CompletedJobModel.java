package com.weboconnect.nurseify.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompletedJobModel {

    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<CompletedDatum> data = null;

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

    public List<CompletedDatum> getData() {
        return data;
    }

    public void setData(List<CompletedDatum> data) {
        this.data = data;
    }

    public class CompletedDatum {
        public String getOffer_id() {
            return offer_id;
        }

        public void setOffer_id(String offer_id) {
            this.offer_id = offer_id;
        }

        @SerializedName("offer_id")
        @Expose
        private String offer_id;
        @SerializedName("facility_id")
        @Expose
        private String facilityId;
        @SerializedName("facility_logo")
        @Expose
        private String facilityLogo;
        @SerializedName("facility_name")
        @Expose
        private String facilityName;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("work_duration")
        @Expose
        private String workDuration;
        @SerializedName("work_duration_definition")
        @Expose
        private String workDurationDefinition;
        @SerializedName("shift")
        @Expose
        private String shift;
        @SerializedName("shift_definition")
        @Expose
        private String shiftDefinition;
        @SerializedName("work_days")
        @Expose
        private String workDays;
        @SerializedName("hourly_rate")
        @Expose
        private String hourlyRate;
        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("end_date")
        @Expose
        private String endDate;

        public String getFacilityId() {
            return facilityId;
        }

        public void setFacilityId(String facilityId) {
            this.facilityId = facilityId;
        }

        public String getFacilityLogo() {
            return facilityLogo;
        }

        public void setFacilityLogo(String facilityLogo) {
            this.facilityLogo = facilityLogo;
        }

        public String getFacilityName() {
            return facilityName;
        }

        public void setFacilityName(String facilityName) {
            this.facilityName = facilityName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getWorkDuration() {
            return workDuration;
        }

        public void setWorkDuration(String workDuration) {
            this.workDuration = workDuration;
        }

        public String getWorkDurationDefinition() {
            return workDurationDefinition;
        }

        public void setWorkDurationDefinition(String workDurationDefinition) {
            this.workDurationDefinition = workDurationDefinition;
        }

        public String getShift() {
            return shift;
        }

        public void setShift(String shift) {
            this.shift = shift;
        }

        public String getShiftDefinition() {
            return shiftDefinition;
        }

        public void setShiftDefinition(String shiftDefinition) {
            this.shiftDefinition = shiftDefinition;
        }

        public String getWorkDays() {
            return workDays;
        }

        public void setWorkDays(String workDays) {
            this.workDays = workDays;
        }

        public String getHourlyRate() {
            return hourlyRate;
        }

        public void setHourlyRate(String hourlyRate) {
            this.hourlyRate = hourlyRate;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

    }
}