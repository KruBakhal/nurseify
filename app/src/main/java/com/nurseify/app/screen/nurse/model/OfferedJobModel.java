package com.nurseify.app.screen.nurse.model;

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
    private OfferedSubData data;

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

    public OfferedSubData getData() {
        return data;
    }

    public void setData(OfferedSubData data) {
        this.data = data;
    }

    public class OfferedSubData {

        @SerializedName("offer")
        @Expose
        private List<OfferedJob> offer = null;
        @SerializedName("total_pages_available")
        @Expose
        private String totalPagesAvailable;
        @SerializedName("current_page")
        @Expose
        private String currentPage;
        @SerializedName("results_per_page")
        @Expose
        private String resultsPerPage;

        public List<OfferedJob> getOffer() {
            return offer;
        }

        public void setOffer(List<OfferedJob> offer) {
            this.offer = offer;
        }

        public String getTotalPagesAvailable() {
            return totalPagesAvailable;
        }

        public void setTotalPagesAvailable(String totalPagesAvailable) {
            this.totalPagesAvailable = totalPagesAvailable;
        }

        public String getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(String currentPage) {
            this.currentPage = currentPage;
        }

        public String getResultsPerPage() {
            return resultsPerPage;
        }

        public void setResultsPerPage(String resultsPerPage) {
            this.resultsPerPage = resultsPerPage;
        }

    }

    public class OfferedJob {
        @SerializedName("offer_expiration")
        @Expose
        private String offerExpiration;
        @SerializedName("offer_id")
        @Expose
        private String offerId;
        @SerializedName("job_id")
        @Expose
        private String jobId;
        @SerializedName("facility_logo")
        @Expose
        private String facilityLogo;
        @SerializedName("facility_logo_base")
        @Expose
        private String facilityLogo_base;
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

        public String getFacilityLogo_base() {
            return facilityLogo_base;
        }

        public void setFacilityLogo_base(String facilityLogo_base) {
            this.facilityLogo_base = facilityLogo_base;
        }

        public String getOfferExpiration() {
            return offerExpiration;
        }

        public void setOfferExpiration(String offerExpiration) {
            this.offerExpiration = offerExpiration;
        }

        public String getOfferId() {
            return offerId;
        }

        public void setOfferId(String offerId) {
            this.offerId = offerId;
        }

        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
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
