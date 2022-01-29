package com.nurseify.app.screen.nurse.model;

public class Combine_RoleIneterest_DataModel {
    LanguageModel languageModel;
    LeaderRolesModel leaderRolesModel;

    public Combine_RoleIneterest_DataModel(LanguageModel languageModel, LeaderRolesModel leaderRolesModel) {
        this.languageModel = languageModel;
        this.leaderRolesModel = leaderRolesModel;
    }

    public LanguageModel getLanguageModel() {
        return languageModel;
    }

    public void setLanguageModel(LanguageModel languageModel) {
        this.languageModel = languageModel;
    }

    public LeaderRolesModel getLeaderRolesModel() {
        return leaderRolesModel;
    }

    public void setLeaderRolesModel(LeaderRolesModel leaderRolesModel) {
        this.leaderRolesModel = leaderRolesModel;
    }
}
