package com.nurseify.app.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCredentialData {
    public AddCredentialData(String id, String searchForCredentialDefinition) {
        this.id = id;
        this.searchForCredentialDefinition = searchForCredentialDefinition;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nurse_id")
    @Expose
    private String nurseId;
    @SerializedName("search_for_credential")
    @Expose
    private Integer searchForCredential;
    @SerializedName("search_for_credential_definition")
    @Expose
    private String searchForCredentialDefinition;
    @SerializedName("license_number")
    @Expose
    private String licenseNumber;
    @SerializedName("effective_date")
    @Expose
    private String effectiveDate;
    @SerializedName("expiration_date")
    @Expose
    private String expirationDate;
    @SerializedName("certificate_image")
    @Expose
    private String certificateImage;
    @SerializedName("resume")
    @Expose
    private String resume;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNurseId() {
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        this.nurseId = nurseId;
    }

    public Integer getSearchForCredential() {
        return searchForCredential;
    }

    public void setSearchForCredential(Integer searchForCredential) {
        this.searchForCredential = searchForCredential;
    }

    public String getSearchForCredentialDefinition() {
        return searchForCredentialDefinition;
    }

    public void setSearchForCredentialDefinition(String searchForCredentialDefinition) {
        this.searchForCredentialDefinition = searchForCredentialDefinition;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCertificateImage() {
        return certificateImage;
    }

    public void setCertificateImage(String certificateImage) {
        this.certificateImage = certificateImage;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

}