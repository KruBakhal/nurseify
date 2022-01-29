package com.nurseify.app.screen.facility.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FacilityJobModel {

    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private FacilityJob_SubData data;

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

    public FacilityJob_SubData getData() {
        return data;
    }

    public void setData(FacilityJob_SubData data) {
        this.data = data;
    }

    public class FacilityJob_SubData {

        @SerializedName("total_pages_available")
        @Expose
        private String totalPagesAvailable;
        @SerializedName("current_page")
        @Expose
        private String currentPage;
        @SerializedName("results_per_page")
        @Expose
        private String resultsPerPage;
        @SerializedName("data")
        @Expose
        private List<Facility_JobDatum> data = null;

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

        public List<Facility_JobDatum> getData() {
            return data;
        }

        public void setData(List<Facility_JobDatum> data) {
            this.data = data;
        }

    }

}