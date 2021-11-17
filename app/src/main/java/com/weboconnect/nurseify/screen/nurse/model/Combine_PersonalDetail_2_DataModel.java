package com.weboconnect.nurseify.screen.nurse.model;

public class Combine_PersonalDetail_2_DataModel {

    public SpecialtyModel specialtyModel;
    public StateModel stateModel;

    public Combine_PersonalDetail_2_DataModel(SpecialtyModel specialtyModel, StateModel stateModel) {
        this.specialtyModel = specialtyModel;
        this.stateModel = stateModel;
    }

    public SpecialtyModel getSpecialtyModel() {
        return specialtyModel;
    }

    public void setSpecialtyModel(SpecialtyModel specialtyModel) {
        this.specialtyModel = specialtyModel;
    }

    public StateModel getStateModel() {
        return stateModel;
    }

    public void setStateModel(StateModel stateModel) {
        this.stateModel = stateModel;
    }
}
