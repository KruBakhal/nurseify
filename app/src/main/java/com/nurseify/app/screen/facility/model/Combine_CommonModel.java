package com.nurseify.app.screen.facility.model;

import com.nurseify.app.common.CommonModel;

public class Combine_CommonModel {
    CommonModel commonModel_ENR;
    CommonModel commonModel_BG;
    CommonModel commonModel_SOFT;

    public Combine_CommonModel(CommonModel commonModel_ENR, CommonModel commonModel_BG, CommonModel commonModel_SOFT) {
        this.commonModel_ENR = commonModel_ENR;
        this.commonModel_BG = commonModel_BG;
        this.commonModel_SOFT = commonModel_SOFT;
    }

    public CommonModel getCommonModel_ENR() {
        return commonModel_ENR;
    }

    public void setCommonModel_ENR(CommonModel commonModel_ENR) {
        this.commonModel_ENR = commonModel_ENR;
    }

    public CommonModel getCommonModel_BG() {
        return commonModel_BG;
    }

    public void setCommonModel_BG(CommonModel commonModel_BG) {
        this.commonModel_BG = commonModel_BG;
    }

    public CommonModel getCommonModel_SOFT() {
        return commonModel_SOFT;
    }

    public void setCommonModel_SOFT(CommonModel commonModel_SOFT) {
        this.commonModel_SOFT = commonModel_SOFT;
    }
}
