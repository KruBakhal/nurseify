package com.weboconnect.nurseify;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.weboconnect.nurseify.common.CommonDatum;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_DayOfWeek_OptionModel;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_DayOfWeek_OptionModel.HourlyRate_DayOfWeek_OptionDatum;

import java.util.ArrayList;
import java.util.List;

public class AppController extends Application {

    public List<CommonDatum> list_assignment_durations = new ArrayList<>();
    public List<CommonDatum> list_senior_level = new ArrayList<>();
    public List<CommonDatum> list_job_funcs = new ArrayList<>();
    public List<CommonDatum> list_speciality = new ArrayList<>();
    public List<CommonDatum> list_preferred_shift = new ArrayList<>();
    public List<CommonDatum> list_work_loc = new ArrayList<>();
    public List<HourlyRate_DayOfWeek_OptionDatum> list_days_of_week = new ArrayList<>();
    private List<CommonDatum> list_cerner;
    private List<CommonDatum> list_medtech;
    private List<CommonDatum> list_epic;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public List<CommonDatum> getList_assignment_durations() {
        return list_assignment_durations;
    }

    public void setList_assignment_durations(List<CommonDatum> list_assignment_durations) {
        this.list_assignment_durations = list_assignment_durations;
    }

    public List<CommonDatum> getList_senior_level() {
        return list_senior_level;
    }

    public void setList_senior_level(List<CommonDatum> list_senior_level) {
        this.list_senior_level = list_senior_level;
    }

    public List<CommonDatum> getList_job_funcs() {
        return list_job_funcs;
    }

    public void setList_job_funcs(List<CommonDatum> list_job_funcs) {
        this.list_job_funcs = list_job_funcs;
    }

    public List<CommonDatum> getList_speciality() {
        return list_speciality;
    }

    public void setList_speciality(List<CommonDatum> list_speciality) {
        this.list_speciality = list_speciality;
    }

    public List<CommonDatum> getList_preferred_shift() {
        return list_preferred_shift;
    }

    public void setList_preferred_shift(List<CommonDatum> list_preferred_shift) {
        this.list_preferred_shift = list_preferred_shift;
    }

    public List<CommonDatum> getList_work_loc() {
        return list_work_loc;
    }

    public void setList_work_loc(List<CommonDatum> list_work_loc) {
        this.list_work_loc = list_work_loc;
    }

    public List<HourlyRate_DayOfWeek_OptionDatum> getList_days_of_week() {
        return list_days_of_week;
    }

    public void setList_days_of_week(List<HourlyRate_DayOfWeek_OptionDatum> list_days_of_week) {
        this.list_days_of_week = list_days_of_week;
    }

    public void setList_cerner(List<CommonDatum> list_cerner) {
        this.list_cerner = list_cerner;
    }

    public List<CommonDatum> getList_cerner() {
        return list_cerner;
    }

    public void setList_medtech(List<CommonDatum> list_medtech) {
        this.list_medtech = list_medtech;
    }

    public List<CommonDatum> getList_medtech() {
        return list_medtech;
    }

    public void setList_epic(List<CommonDatum> list_epic) {
        this.list_epic = list_epic;
    }

    public List<CommonDatum> getList_epic() {
        return list_epic;
    }
}
