package com.weboconnect.nurseify.screen.facility.model;

import com.weboconnect.nurseify.common.CommonModel;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_DayOfWeek_OptionModel;

public class Combine_CommonModel_2 {
    CommonModel commonModel_assign;
    CommonModel commonModel_senior;

    public CommonModel getCommonModel_jobs_funcs() {
        return commonModel_jobs_funcs;
    }

    public void setCommonModel_jobs_funcs(CommonModel commonModel_jobs_funcs) {
        this.commonModel_jobs_funcs = commonModel_jobs_funcs;
    }

    public Combine_CommonModel_2(CommonModel commonModel_assign, CommonModel commonModel_senior,
                                 CommonModel commonModel_jobs_funcs, CommonModel commonModel_speciality,
                                 CommonModel commonModel_shift, CommonModel commonModel_workLoc,
                                 HourlyRate_DayOfWeek_OptionModel commonModel_week,
                                 CommonModel commonModel_epic) {
        this.commonModel_assign = commonModel_assign;
        this.commonModel_senior = commonModel_senior;
        this.commonModel_jobs_funcs = commonModel_jobs_funcs;
        this.commonModel_speciality = commonModel_speciality;
        this.commonModel_shift = commonModel_shift;
        this.commonModel_workLoc = commonModel_workLoc;
        this.commonModel_week = commonModel_week;
        commonModel_epic_medtech = commonModel_epic;
    }

    CommonModel commonModel_jobs_funcs;
    CommonModel commonModel_speciality;
    CommonModel commonModel_shift;
    CommonModel commonModel_workLoc;
    HourlyRate_DayOfWeek_OptionModel commonModel_week;
    CommonModel commonModel_epic_medtech;

    public CommonModel getCommonModel_epic_medtech() {
        return commonModel_epic_medtech;
    }

    public void setCommonModel_epic_medtech(CommonModel commonModel_epic_medtech) {
        this.commonModel_epic_medtech = commonModel_epic_medtech;
    }

    public CommonModel getCommonModel_assign() {
        return commonModel_assign;
    }

    public void setCommonModel_assign(CommonModel commonModel_assign) {
        this.commonModel_assign = commonModel_assign;
    }

    public CommonModel getCommonModel_senior() {
        return commonModel_senior;
    }

    public void setCommonModel_senior(CommonModel commonModel_senior) {
        this.commonModel_senior = commonModel_senior;
    }

    public CommonModel getCommonModel_speciality() {
        return commonModel_speciality;
    }

    public void setCommonModel_speciality(CommonModel commonModel_speciality) {
        this.commonModel_speciality = commonModel_speciality;
    }

    public CommonModel getCommonModel_shift() {
        return commonModel_shift;
    }

    public void setCommonModel_shift(CommonModel commonModel_shift) {
        this.commonModel_shift = commonModel_shift;
    }

    public CommonModel getCommonModel_workLoc() {
        return commonModel_workLoc;
    }

    public void setCommonModel_workLoc(CommonModel commonModel_workLoc) {
        this.commonModel_workLoc = commonModel_workLoc;
    }

    public HourlyRate_DayOfWeek_OptionModel getCommonModel_week() {
        return commonModel_week;
    }

    public void setCommonModel_week(HourlyRate_DayOfWeek_OptionModel commonModel_week) {
        this.commonModel_week = commonModel_week;
    }

}
