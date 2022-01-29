package com.nurseify.app.screen.facility.model;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferedJobNurse_Datum {

    @SerializedName("job_id")
    @Expose
    private String jobId;
    @SerializedName("job")
    @Expose
    private String job;
    @SerializedName("content")
    @Expose
    private Content content;

    public OfferedJobNurse_Datum(String jobId, String job) {
        this.jobId = jobId;
        this.content = new Content(job);
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public class Content {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("specialty")
        @Expose
        private String specialty;
        @SerializedName("jobDetail")
        @Expose
        private JobDetail jobDetail;
        @SerializedName("terms")
        @Expose
        private String terms;

        public Content(String job) {
            name = job;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getSpecialty() {
            if (TextUtils.isEmpty(specialty))
                specialty = "";
            return specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public JobDetail getJobDetail() {
            return jobDetail;
        }

        public void setJobDetail(JobDetail jobDetail) {
            this.jobDetail = jobDetail;
        }

        public String getTerms() {
            return terms;
        }

        public void setTerms(String terms) {
            this.terms = terms;
        }

    }

    public class JobDetail {

        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("duration")
        @Expose
        private String duration;
        @SerializedName("shift")
        @Expose
        private String shift;
        @SerializedName("workdays")
        @Expose
        private String workdays;

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getShift() {
            return shift;
        }

        public void setShift(String shift) {
            this.shift = shift;
        }

        public String getWorkdays() {
            return workdays;
        }

        public void setWorkdays(String workdays) {
            this.workdays = workdays;
        }

    }

}
