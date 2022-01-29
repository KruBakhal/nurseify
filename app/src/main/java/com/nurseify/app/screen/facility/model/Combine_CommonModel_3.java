package com.nurseify.app.screen.facility.model;

import com.nurseify.app.common.CommonModel;
import com.nurseify.app.screen.nurse.model.HourlyRate_DayOfWeek_OptionModel;
import com.nurseify.app.screen.nurse.model.StateModel;

public class Combine_CommonModel_3 {
    StateModel stateModel;
    CommonModel commonModel_specialty;
    HourlyRate_DayOfWeek_OptionModel dayOfWeek_optionModel;
    CommonModel commonModel_certifcate;

    public Combine_CommonModel_3(StateModel stateModel, CommonModel commonModel_specialty, HourlyRate_DayOfWeek_OptionModel dayOfWeek_optionModel, CommonModel commonModel_certifcate) {
        this.stateModel = stateModel;
        this.commonModel_specialty = commonModel_specialty;
        this.dayOfWeek_optionModel = dayOfWeek_optionModel;
        this.commonModel_certifcate = commonModel_certifcate;
    }

    public StateModel getStateModel() {
        return stateModel;
    }

    public void setStateModel(StateModel stateModel) {
        this.stateModel = stateModel;
    }


    public CommonModel getCommonModel_specialty() {
        return commonModel_specialty;
    }

    public void setCommonModel_specialty(CommonModel commonModel_specialty) {
        this.commonModel_specialty = commonModel_specialty;
    }

    public HourlyRate_DayOfWeek_OptionModel getDayOfWeek_optionModel() {
        return dayOfWeek_optionModel;
    }

    public void setDayOfWeek_optionModel(HourlyRate_DayOfWeek_OptionModel dayOfWeek_optionModel) {
        this.dayOfWeek_optionModel = dayOfWeek_optionModel;
    }

    public CommonModel getCommonModel_certifcate() {
        return commonModel_certifcate;
    }

    public void setCommonModel_certifcate(CommonModel commonModel_certifcate) {
        this.commonModel_certifcate = commonModel_certifcate;
    }
}
