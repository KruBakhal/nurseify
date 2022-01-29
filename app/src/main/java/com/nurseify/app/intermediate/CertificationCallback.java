package com.nurseify.app.intermediate;

import com.nurseify.app.screen.nurse.model.UserProfileData;

public interface CertificationCallback {

    void onEdit(int position);

    void onRemove(int position, UserProfileData.Certitficate certitficate);
}
