package com.nurseify.app.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyJobModel {

    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<MyJobDatum> data = null;

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

    public List<MyJobDatum> getData() {
        return data;
    }

    public void setData(List<MyJobDatum> data) {
        this.data = data;
    }

    public class MyJobDatum {

        @SerializedName("offer_id")
        @Expose
        private String offerId;
        @SerializedName("job_id")
        @Expose
        private String jobId;
        @SerializedName("facility_logo")
        @Expose
        private String facilityLogo;
        @SerializedName("facility_name")
        @Expose
        private String facilityName;
        @SerializedName("preferred_work_location")
        @Expose
        private String preferredWorkLocation;
        @SerializedName("preferred_work_location_definition")
        @Expose
        private String preferredWorkLocationDefinition;
        @SerializedName("job_title")
        @Expose
        private String jobTitle;
        @SerializedName("job_description")
        @Expose
        private String jobDescription;
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
        @SerializedName("about_job")
        @Expose
        private AboutJob aboutJob;
        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("end_date")
        @Expose
        private String endDate;
        @SerializedName("rating_flag")
        @Expose
        private String rating_flag;


        @SerializedName("rating_comment")
        @Expose
        private RatingComment ratingComment;

        public RatingComment getRatingComment() {
            return ratingComment;
        }

        public void setRatingComment(RatingComment ratingComment) {
            this.ratingComment = ratingComment;
        }

        public class RatingComment {


            @SerializedName("rating")
            @Expose
            private String rating;
            @SerializedName("experience")
            @Expose
            private String experience;
            @SerializedName("nurse_name")
            @Expose
            private String nurseName;
            @SerializedName("nurse_image")
            @Expose
            private String nurseImage;

            public String getRating() {
                return rating;
            }

            public void setRating(String rating) {
                this.rating = rating;
            }

            public String getExperience() {
                return experience;
            }

            public void setExperience(String experience) {
                this.experience = experience;
            }

            public String getNurseName() {
                return nurseName;
            }

            public void setNurseName(String nurseName) {
                this.nurseName = nurseName;
            }

            public String getNurseImage() {
                return nurseImage;
            }

            public void setNurseImage(String nurseImage) {
                this.nurseImage = nurseImage;
            }

        }

        public String getOfferId() {
            return offerId;
        }

        public void setOfferId(String offerId) {
            this.offerId = offerId;
        }

        public String getRating_flag() {
            return rating_flag;
        }

        public void setRating_flag(String rating_flag) {
            this.rating_flag = rating_flag;
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

        public String getPreferredWorkLocation() {
            return preferredWorkLocation;
        }

        public void setPreferredWorkLocation(String preferredWorkLocation) {
            this.preferredWorkLocation = preferredWorkLocation;
        }

        public String getPreferredWorkLocationDefinition() {
            return preferredWorkLocationDefinition;
        }

        public void setPreferredWorkLocationDefinition(String preferredWorkLocationDefinition) {
            this.preferredWorkLocationDefinition = preferredWorkLocationDefinition;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public String getJobDescription() {
            return jobDescription;
        }

        public void setJobDescription(String jobDescription) {
            this.jobDescription = jobDescription;
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

        public AboutJob getAboutJob() {
            return aboutJob;
        }

        public void setAboutJob(AboutJob aboutJob) {
            this.aboutJob = aboutJob;
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

        public RatingComment getRating() {
            return ratingComment;
        }

        public void setRating(RatingComment rating) {
            this.ratingComment = rating;
        }

    }

    public class AboutJob {

        @SerializedName("seniority_level")
        @Expose
        private String seniorityLevel;
        @SerializedName("seniority_level_definition")
        @Expose
        private String seniorityLevelDefinition;
        @SerializedName("preferred_shift_duration")
        @Expose
        private String preferredShiftDuration;
        @SerializedName("preferred_shift_duration_definition")
        @Expose
        private String preferredShiftDurationDefinition;
        @SerializedName("preferred_experience")
        @Expose
        private String preferredExperience;
        @SerializedName("cerner")
        @Expose
        private String cerner;
        @SerializedName("cerner_definition")
        @Expose
        private String cernerDefinition;
        @SerializedName("meditech")
        @Expose
        private String meditech;
        @SerializedName("meditech_definition")
        @Expose
        private String meditechDefinition;
        @SerializedName("epic")
        @Expose
        private String epic;
        @SerializedName("epic_definition")
        @Expose
        private String epicDefinition;

        public String getSeniorityLevel() {
            return seniorityLevel;
        }

        public void setSeniorityLevel(String seniorityLevel) {
            this.seniorityLevel = seniorityLevel;
        }

        public String getSeniorityLevelDefinition() {
            return seniorityLevelDefinition;
        }

        public void setSeniorityLevelDefinition(String seniorityLevelDefinition) {
            this.seniorityLevelDefinition = seniorityLevelDefinition;
        }

        public String getPreferredShiftDuration() {
            return preferredShiftDuration;
        }

        public void setPreferredShiftDuration(String preferredShiftDuration) {
            this.preferredShiftDuration = preferredShiftDuration;
        }

        public String getPreferredShiftDurationDefinition() {
            return preferredShiftDurationDefinition;
        }

        public void setPreferredShiftDurationDefinition(String preferredShiftDurationDefinition) {
            this.preferredShiftDurationDefinition = preferredShiftDurationDefinition;
        }

        public String getPreferredExperience() {
            return preferredExperience;
        }

        public void setPreferredExperience(String preferredExperience) {
            this.preferredExperience = preferredExperience;
        }

        public String getCerner() {
            return cerner;
        }

        public void setCerner(String cerner) {
            this.cerner = cerner;
        }

        public String getCernerDefinition() {
            return cernerDefinition;
        }

        public void setCernerDefinition(String cernerDefinition) {
            this.cernerDefinition = cernerDefinition;
        }

        public String getMeditech() {
            return meditech;
        }

        public void setMeditech(String meditech) {
            this.meditech = meditech;
        }

        public String getMeditechDefinition() {
            return meditechDefinition;
        }

        public void setMeditechDefinition(String meditechDefinition) {
            this.meditechDefinition = meditechDefinition;
        }

        public String getEpic() {
            return epic;
        }

        public void setEpic(String epic) {
            this.epic = epic;
        }

        public String getEpicDefinition() {
            return epicDefinition;
        }

        public void setEpicDefinition(String epicDefinition) {
            this.epicDefinition = epicDefinition;
        }

    }
}