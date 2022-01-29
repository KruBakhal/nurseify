package com.nurseify.app.screen.nurse.model;

public class Combine_HourlyRate_DataModel {

    public HourlyRate_Common_OptionModel shiftDuration;
    public HourlyRate_Common_OptionModel assignmentDuration;
    public HourlyRate_Common_OptionModel preferredShift;
    public HourlyRate_DayOfWeek_OptionModel preferredDayOfWeek;
    public WorkLocationModel workLocationModel;

    public WorkLocationModel getWorkLocationModel() {
        return workLocationModel;
    }

    public void setWorkLocationModel(WorkLocationModel workLocationModel) {
        this.workLocationModel = workLocationModel;
    }

    public Combine_HourlyRate_DataModel(HourlyRate_Common_OptionModel shiftDuration, HourlyRate_Common_OptionModel assignmentDuration, HourlyRate_Common_OptionModel preferredShift, HourlyRate_DayOfWeek_OptionModel preferredDayOfWeek, WorkLocationModel workLocationModel) {
        this.shiftDuration = shiftDuration;
        this.assignmentDuration = assignmentDuration;
        this.preferredShift = preferredShift;
        this.preferredDayOfWeek = preferredDayOfWeek;
        this.workLocationModel = workLocationModel;
    }

    public HourlyRate_Common_OptionModel getShiftDuration() {
        return shiftDuration;
    }

    public void setShiftDuration(HourlyRate_Common_OptionModel shiftDuration) {
        this.shiftDuration = shiftDuration;
    }

    public HourlyRate_Common_OptionModel getAssignmentDuration() {
        return assignmentDuration;
    }

    public void setAssignmentDuration(HourlyRate_Common_OptionModel assignmentDuration) {
        this.assignmentDuration = assignmentDuration;
    }

    public HourlyRate_Common_OptionModel getPreferredShift() {
        return preferredShift;
    }

    public void setPreferredShift(HourlyRate_Common_OptionModel preferredShift) {
        this.preferredShift = preferredShift;
    }

    public HourlyRate_DayOfWeek_OptionModel getPreferredDayOfWeek() {
        return preferredDayOfWeek;
    }

    public void setPreferredDayOfWeek(HourlyRate_DayOfWeek_OptionModel preferredDayOfWeek) {
        this.preferredDayOfWeek = preferredDayOfWeek;
    }

    @Override
    public String toString() {
        return "Combine_HourlyRate_DataModel{" +
                "shiftDuration=" + shiftDuration +
                ", assignmentDuration=" + assignmentDuration +
                ", preferredShift=" + preferredShift +
                ", perferredDayOfWeek=" + preferredDayOfWeek +
                '}';
    }
}
