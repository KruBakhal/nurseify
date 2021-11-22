package com.weboconnect.nurseify.intermediate;

public interface FacilityListCallback {
    void onFollow(String facilityId, String type);
    void onLike(String facilityId, String like);
}