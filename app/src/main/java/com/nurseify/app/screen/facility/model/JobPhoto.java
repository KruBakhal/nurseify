package com.nurseify.app.screen.facility.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobPhoto {
    public JobPhoto(String assetId, String name) {
        this.assetId = assetId;
        this.name = name;
    }

    @SerializedName("asset_id")
    @Expose
    private String assetId;
    @SerializedName("name")
    @Expose
    private String name;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
