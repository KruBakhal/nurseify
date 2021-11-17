package com.weboconnect.nurseify.screen.nurse.model;

public class Combine_PersonalDetail_DataModel {

    public WorkLocationModel workLocationModel;
    public SpecialtyModel specialtyModel;

    public Combine_PersonalDetail_DataModel(WorkLocationModel workLocationModel, SpecialtyModel specialtyModel) {
        this.workLocationModel = workLocationModel;
        this.specialtyModel = specialtyModel;
    }

    public WorkLocationModel getWorkLocationModel() {
        return workLocationModel;
    }

    public void setWorkLocationModel(WorkLocationModel workLocationModel) {
        this.workLocationModel = workLocationModel;
    }

    public SpecialtyModel getSpecialtyModel() {
        return specialtyModel;
    }

    public void setSpecialtyModel(SpecialtyModel specialtyModel) {
        this.specialtyModel = specialtyModel;
    }
}
