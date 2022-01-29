package com.nurseify.app.screen.nurse.model;

public class Combine_PersonalDetail_2_DataModel {

    public SpecialtyModel specialtyModel;
    public StateModel stateModel;
    public CountryModel countryModel;

    public Combine_PersonalDetail_2_DataModel(SpecialtyModel specialtyModel, StateModel stateModel, CountryModel countryModel) {
        this.specialtyModel = specialtyModel;
        this.stateModel = stateModel;
        this.countryModel = countryModel;
    }

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
