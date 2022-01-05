package com.weboconnect.nurseify.screen.facility.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Facility_JobDatum {
    @SerializedName("job_id")
    @Expose
    private String job_id;
    @SerializedName("applied")
    @Expose
    private String applied;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    @SerializedName("facility_first_name")
    @Expose
    private String facilityFirstName;
    @SerializedName("facility_last_name")
    @Expose
    private String facilityLastName;
    @SerializedName("facility_image")
    @Expose
    private String facilityImage;
    @SerializedName("preferred_specialty")
    @Expose
    private String preferredSpecialty;
    @SerializedName("preferred_specialty_definition")
    @Expose
    private String preferredSpecialtyDefinition;
    @SerializedName("preferred_assignment_duration")
    @Expose
    private String preferredAssignmentDuration;
    @SerializedName("preferred_assignment_duration_definition")
    @Expose
    private String preferredAssignmentDurationDefinition;
    @SerializedName("preferred_hourly_pay_rate")
    @Expose
    private String preferredHourlyPayRate;
    @SerializedName("preferred_days_of_the_week")
    @Expose
    private String preferredDaysOfTheWeek;
    @SerializedName("preferred_days_of_the_week_array")
    @Expose
    private List<String> preferredDaysOfTheWeekArray = null;
    @SerializedName("preferred_days_of_the_week_string")
    @Expose
    private String preferredDaysOfTheWeekString;
    @SerializedName("start_date")
    @Expose
    private String startDate;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @SerializedName("end_date")
    @Expose
    private String endDate;

    public String getFacilityFirstName() {
        return facilityFirstName;
    }

    public void setFacilityFirstName(String facilityFirstName) {
        this.facilityFirstName = facilityFirstName;
    }

    public String getFacilityLastName() {
        return facilityLastName;
    }

    public void setFacilityLastName(String facilityLastName) {
        this.facilityLastName = facilityLastName;
    }

    public String getFacilityImage() {
        return facilityImage;
    }

    public void setFacilityImage(String facilityImage) {
        this.facilityImage = facilityImage;
    }

    public String getPreferredSpecialty() {
        return preferredSpecialty;
    }

    public void setPreferredSpecialty(String preferredSpecialty) {
        this.preferredSpecialty = preferredSpecialty;
    }

    public String getPreferredSpecialtyDefinition() {
        return preferredSpecialtyDefinition;
    }

    public void setPreferredSpecialtyDefinition(String preferredSpecialtyDefinition) {
        this.preferredSpecialtyDefinition = preferredSpecialtyDefinition;
    }

    public String getPreferredAssignmentDuration() {
        return preferredAssignmentDuration;
    }

    public void setPreferredAssignmentDuration(String preferredAssignmentDuration) {
        this.preferredAssignmentDuration = preferredAssignmentDuration;
    }

    public String getPreferredAssignmentDurationDefinition() {
        return preferredAssignmentDurationDefinition;
    }

    public void setPreferredAssignmentDurationDefinition(String preferredAssignmentDurationDefinition) {
        this.preferredAssignmentDurationDefinition = preferredAssignmentDurationDefinition;
    }

    public String getPreferredHourlyPayRate() {
        return preferredHourlyPayRate;
    }

    public void setPreferredHourlyPayRate(String preferredHourlyPayRate) {
        this.preferredHourlyPayRate = preferredHourlyPayRate;
    }

    public String getPreferredDaysOfTheWeek() {
        return preferredDaysOfTheWeek;
    }

    public void setPreferredDaysOfTheWeek(String preferredDaysOfTheWeek) {
        this.preferredDaysOfTheWeek = preferredDaysOfTheWeek;
    }

    public List<String> getPreferredDaysOfTheWeekArray() {
        return preferredDaysOfTheWeekArray;
    }

    public void setPreferredDaysOfTheWeekArray(List<String> preferredDaysOfTheWeekArray) {
        this.preferredDaysOfTheWeekArray = preferredDaysOfTheWeekArray;
    }

    public String getPreferredDaysOfTheWeekString() {
        return preferredDaysOfTheWeekString;
    }

    public void setPreferredDaysOfTheWeekString(String preferredDaysOfTheWeekString) {
        this.preferredDaysOfTheWeekString = preferredDaysOfTheWeekString;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getApplied() {
        return applied;
    }

    public void setApplied(String applied) {
        this.applied = applied;
    }
}
