package com.weboconnect.nurseify.screen.nurse.model;

public class Combine_WorkHistory_DataModel {
    public DegreeModel degreeModel;
    CernersModel cernersModel;

    public Combine_WorkHistory_DataModel(DegreeModel specialtyModel, CernersModel stateModel) {
        this.degreeModel = specialtyModel;
        this.cernersModel = stateModel;
    }

    public DegreeModel getDegreeModel() {
        return degreeModel;
    }

    public void setDegreeModel(DegreeModel degreeModel) {
        this.degreeModel = degreeModel;
    }

    public CernersModel getCernersModel() {
        return cernersModel;
    }

    public void setCernersModel(CernersModel cernersModel) {
        this.cernersModel = cernersModel;
    }
}
