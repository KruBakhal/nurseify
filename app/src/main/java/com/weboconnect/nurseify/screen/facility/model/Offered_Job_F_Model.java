package com.weboconnect.nurseify.screen.facility.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offered_Job_F_Model {

    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private OfferedJobResponse_Data data;

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

    public OfferedJobResponse_Data getData() {
        return data;
    }

    public void setData(OfferedJobResponse_Data data) {
        this.data = data;
    }

    public class OfferedJobResponse_Data {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("nurse_id")
        @Expose
        private String nurseId;
        @SerializedName("facility")
        @Expose
        private String facility;
        @SerializedName("job_id")
        @Expose
        private String jobId;
        @SerializedName("expiration")
        @Expose
        private String expiration;

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

        public String getFacility() {
            return facility;
        }

        public void setFacility(String facility) {
            this.facility = facility;
        }

        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public String getExpiration() {
            return expiration;
        }

        public void setExpiration(String expiration) {
            this.expiration = expiration;
        }

    }
}