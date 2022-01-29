package com.nurseify.app.screen.nurse.model;

public class Combine_WorkHistory_DataModel {
    public DegreeModel degreeModel;
    DegreeModel cernersModel;
    StateModel stateModel;
    CountryModel countryModel;

    public Combine_WorkHistory_DataModel(DegreeModel degreeModel, DegreeModel cernersModel, CountryModel countryModel) {
        this.degreeModel = degreeModel;
        this.cernersModel = cernersModel;
        this.countryModel = countryModel;
    }

    public Combine_WorkHistory_DataModel(DegreeModel degreeModel, DegreeModel cernersModel, StateModel stateModel, CountryModel countryModel) {
        this.degreeModel = degreeModel;
        this.cernersModel = cernersModel;
        this.stateModel = stateModel;
        this.countryModel = countryModel;
    }

    public Combine_WorkHistory_DataModel(StateModel stateModel, CountryModel countryModel) {
        this.stateModel = stateModel;
        this.countryModel = countryModel;
    }

    public CountryModel getCountryModel() {
        return countryModel;
    }

    public void setCountryModel(CountryModel countryModel) {
        this.countryModel = countryModel;
    }

    public StateModel getStateModel() {
        return stateModel;
    }

    public void setStateModel(StateModel stateModel) {
        this.stateModel = stateModel;
    }

    public Combine_WorkHistory_DataModel(DegreeModel degreeModel, DegreeModel cernersModel, StateModel stateModel) {
        this.degreeModel = degreeModel;
        this.cernersModel = cernersModel;
        this.stateModel = stateModel;
    }

    public Combine_WorkHistory_DataModel(DegreeModel specialtyModel, DegreeModel stateModel) {
        this.degreeModel = specialtyModel;
        this.cernersModel = stateModel;
    }

    public DegreeModel getDegreeModel() {
        return degreeModel;
    }

    public void setDegreeModel(DegreeModel degreeModel) {
        this.degreeModel = degreeModel;
    }

    public DegreeModel getCernersModel() {
        return cernersModel;
    }

    public void setCernersModel(DegreeModel cernersModel) {
        this.cernersModel = cernersModel;
    }
}
