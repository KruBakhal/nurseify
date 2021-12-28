package com.weboconnect.nurseify.intermediate;

public interface API_ResponseCallback {
    void onShowProgress();
    void onSucces(Object combineData);

    void onFailed(String s);

    void onError();
}
