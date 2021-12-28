package com.weboconnect.nurseify.intermediate;

import com.weboconnect.nurseify.screen.nurse.model.FacilityJobModel;

public interface FacilityListCallback {
    void onFollow(int facilityId, String type, FacilityJobModel.Facility facility);
    void onLike(int facilityId, String like, FacilityJobModel.Facility facility);

    void onClick(int position, FacilityJobModel.Facility facility);
}