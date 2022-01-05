package com.weboconnect.nurseify.screen.facility.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfferedNurse_Datum {

    @SerializedName("nurse_first_name")
    @Expose
    private String nurseFirstName;
    @SerializedName("nurse_last_name")
    @Expose
    private String nurseLastName;
    @SerializedName("nurse_image")
    @Expose
    private String nurseImage;
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
    private String preferredDaysOfTheWeekString = null;
    @SerializedName("offered_at")
    @Expose
    private String offeredAt;
    @SerializedName("rating")
    @Expose
    private String rating;

    public String getNurseFirstName() {
        return nurseFirstName;
    }

    public void setNurseFirstName(String nurseFirstName) {
        this.nurseFirstName = nurseFirstName;
    }

    public String getNurseLastName() {
        return nurseLastName;
    }

    public void setNurseLastName(String nurseLastName) {
        this.nurseLastName = nurseLastName;
    }

    public String getNurseImage() {
        return nurseImage;
    }

    public void setNurseImage(String nurseImage) {
        this.nurseImage = nurseImage;
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

    public String getOfferedAt() {
        return offeredAt;
    }

    public void setOfferedAt(String offeredAt) {
        this.offeredAt = offeredAt;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}
