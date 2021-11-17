package com.weboconnect.nurseify.intermediate;

import com.weboconnect.nurseify.screen.nurse.model.Combine_HourlyRate_DataModel;

public interface API_ResponseCallback {
    void onShowProgress();
    void onSucces(Object combineData);

    void onFailed();

    void onError();
}
