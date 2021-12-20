package com.weboconnect.nurseify.intermediate;

import com.weboconnect.nurseify.screen.nurse.model.UserProfileData;

public interface CertificationCallback {

    void onEdit(int position);

    void onRemove(int position, UserProfileData.Certitficate certitficate);
}
