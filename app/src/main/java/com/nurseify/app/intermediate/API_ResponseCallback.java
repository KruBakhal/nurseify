package com.nurseify.app.intermediate;

public interface API_ResponseCallback {
    void onShowProgress();
    void onSucces(Object combineData);

    void onFailed(String s);

    void onError();
}
