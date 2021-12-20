package com.weboconnect.nurseify.intermediate;

import com.weboconnect.nurseify.screen.nurse.model.FacilityModel;

public interface FacilityListCallback {
    void onFollow(int facilityId, String type, FacilityModel.Facility facility);
    void onLike(int facilityId, String like, FacilityModel.Facility facility);

    void onClick(int position, FacilityModel.Facility facility);
}