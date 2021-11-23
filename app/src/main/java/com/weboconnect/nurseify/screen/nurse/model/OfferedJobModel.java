package com.weboconnect.nurseify.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfferedJobModel {

    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    public List<OfferedJob> offeredJob = null;

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

    public List<OfferedJob> getOfferedJob() {
        return offeredJob;
    }

    public void setOfferedJob(List<OfferedJob> data) {
        this.offeredJob = data;
    }

    public class OfferedJob{

        @SerializedName("offer_id")
        @Expose
        private String offerId;
        @SerializedName("facility_logo")
        @Expose
        private String facilityLogo;
        @SerializedName("facility_name")
        @Expose
        private String facilityName;
        @SerializedName("job_title")
        @Expose
        private String jobTitle;
        @SerializedName("assignment_duration")
        @Expose
        private Integer assignmentDuration;
        @SerializedName("assignment_duration_definition")
        @Expose
        private String assignmentDurationDefinition;
        @SerializedName("shift_definition")
        @Expose
        private String shiftDefinition;
        @SerializedName("working_days")
        @Expose
        private String workingDays;
        @SerializedName("working_days_definition")
        @Expose
        private List<String> workingDaysDefinition = null;
        @SerializedName("hourly_pay_rate")
        @Expose
        private String hourlyPayRate;
        @SerializedName("status")
        @Expose
        private String status;

        public String getOfferId() {
            return offerId;
        }

        public void setOfferId(String offerId) {
            this.offerId = offerId;
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

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public Integer getAssignmentDuration() {
            return assignmentDuration;
        }

        public void setAssignmentDuration(Integer assignmentDuration) {
            this.assignmentDuration = assignmentDuration;
        }

        public String getAssignmentDurationDefinition() {
            return assignmentDurationDefinition;
        }

        public void setAssignmentDurationDefinition(String assignmentDurationDefinition) {
            this.assignmentDurationDefinition = assignmentDurationDefinition;
        }

        public String getShiftDefinition() {
            return shiftDefinition;
        }

        public void setShiftDefinition(String shiftDefinition) {
            this.shiftDefinition = shiftDefinition;
        }

        public String getWorkingDays() {
            return workingDays;
        }

        public void setWorkingDays(String workingDays) {
            this.workingDays = workingDays;
        }

        public List<String> getWorkingDaysDefinition() {
            return workingDaysDefinition;
        }

        public void setWorkingDaysDefinition(List<String> workingDaysDefinition) {
            this.workingDaysDefinition = workingDaysDefinition;
        }

        public String getHourlyPayRate() {
            return hourlyPayRate;
        }

        public void setHourlyPayRate(String hourlyPayRate) {
            this.hourlyPayRate = hourlyPayRate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

}
