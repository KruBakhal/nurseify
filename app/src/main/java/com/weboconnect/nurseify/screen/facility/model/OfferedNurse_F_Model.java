package com.weboconnect.nurseify.screen.facility.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfferedNurse_F_Model {

    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private OfferedNurseSubData data;

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

    public OfferedNurseSubData getData() {
        return data;
    }

    public void setData(OfferedNurseSubData data) {
        this.data = data;
    }

    public class OfferedNurseSubData {

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
        private List<OfferedNurse_Datum> data = null;

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

        public List<OfferedNurse_Datum> getData() {
            return data;
        }

        public void setData(List<OfferedNurse_Datum> data) {
            this.data = data;
        }

    }

}