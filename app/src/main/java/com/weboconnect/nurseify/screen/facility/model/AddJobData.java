package com.weboconnect.nurseify.screen.facility.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddJobData {

    @SerializedName("facility_id")
    @Expose
    private String facilityId;
    @SerializedName("preferred_assignment_duration")
    @Expose
    private String preferredAssignmentDuration;
    @SerializedName("seniority_level")
    @Expose
    private String seniorityLevel;
    @SerializedName("job_function")
    @Expose
    private String jobFunction;
    @SerializedName("preferred_specialty")
    @Expose
    private String preferredSpecialty;
    @SerializedName("preferred_shift_duration")
    @Expose
    private String preferredShiftDuration;
    @SerializedName("preferred_work_location")
    @Expose
    private String preferredWorkLocation;
    @SerializedName("preferred_days_of_the_week")
    @Expose
    private String preferredDaysOfTheWeek;
    @SerializedName("preferred_experience")
    @Expose
    private String preferredExperience;
    @SerializedName("preferred_hourly_pay_rate")
    @Expose
    private String preferredHourlyPayRate;
    @SerializedName("job_cerner_exp")
    @Expose
    private String jobCernerExp;
    @SerializedName("job_meditech_exp")
    @Expose
    private String jobMeditechExp;
    @SerializedName("job_epic_exp")
    @Expose
    private String jobEpicExp;
    @SerializedName("job_other_exp")
    @Expose
    private String jobOtherExp;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("responsibilities")
    @Expose
    private String responsibilities;
    @SerializedName("qualifications")
    @Expose
    private String qualifications;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    private String jobVideo;
    private String activeStatus;

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getPreferredAssignmentDuration() {
        return preferredAssignmentDuration;
    }

    public void setPreferredAssignmentDuration(String preferredAssignmentDuration) {
        this.preferredAssignmentDuration = preferredAssignmentDuration;
    }

    public String getSeniorityLevel() {
        return seniorityLevel;
    }

    public void setSeniorityLevel(String seniorityLevel) {
        this.seniorityLevel = seniorityLevel;
    }

    public String getJobFunction() {
        return jobFunction;
    }

    public void setJobFunction(String jobFunction) {
        this.jobFunction = jobFunction;
    }

    public String getPreferredSpecialty() {
        return preferredSpecialty;
    }

    public void setPreferredSpecialty(String preferredSpecialty) {
        this.preferredSpecialty = preferredSpecialty;
    }

    public String getPreferredShiftDuration() {
        return preferredShiftDuration;
    }

    public void setPreferredShiftDuration(String preferredShiftDuration) {
        this.preferredShiftDuration = preferredShiftDuration;
    }

    public String getPreferredWorkLocation() {
        return preferredWorkLocation;
    }

    public void setPreferredWorkLocation(String preferredWorkLocation) {
        this.preferredWorkLocation = preferredWorkLocation;
    }

    public String getPreferredDaysOfTheWeek() {
        return preferredDaysOfTheWeek;
    }

    public void setPreferredDaysOfTheWeek(String preferredDaysOfTheWeek) {
        this.preferredDaysOfTheWeek = preferredDaysOfTheWeek;
    }

    public String getPreferredExperience() {
        return preferredExperience;
    }

    public void setPreferredExperience(String preferredExperience) {
        this.preferredExperience = preferredExperience;
    }

    public String getPreferredHourlyPayRate() {
        return preferredHourlyPayRate;
    }

    public void setPreferredHourlyPayRate(String preferredHourlyPayRate) {
        this.preferredHourlyPayRate = preferredHourlyPayRate;
    }

    public String getJobCernerExp() {
        return jobCernerExp;
    }

    public void setJobCernerExp(String jobCernerExp) {
        this.jobCernerExp = jobCernerExp;
    }

    public String getJobMeditechExp() {
        return jobMeditechExp;
    }

    public void setJobMeditechExp(String jobMeditechExp) {
        this.jobMeditechExp = jobMeditechExp;
    }

    public String getJobEpicExp() {
        return jobEpicExp;
    }

    public void setJobEpicExp(String jobEpicExp) {
        this.jobEpicExp = jobEpicExp;
    }

    public String getJobOtherExp() {
        return jobOtherExp;
    }

    public void setJobOtherExp(String jobOtherExp) {
        this.jobOtherExp = jobOtherExp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setJobVideo(String jobVideo) {
        this.jobVideo = jobVideo;
    }

    public String getJobVideo() {
        return jobVideo;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getActiveStatus() {
        return activeStatus;
    }
}
