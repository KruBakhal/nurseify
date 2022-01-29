package com.nurseify.app.intermediate;

public interface OfferedJobCallback {
    void onAccept(String jobId);

    void onReject(String jobId);

    void onClick(int pos);
}
