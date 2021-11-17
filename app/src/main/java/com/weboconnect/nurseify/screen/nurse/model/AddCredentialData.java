package com.weboconnect.nurseify.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCredentialData {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("nurse_id")
        @Expose
        private String nurseId;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("license_number")
        @Expose
        private Object licenseNumber;
        @SerializedName("effective_date")
        @Expose
        private String effectiveDate;
        @SerializedName("expiration_date")
        @Expose
        private String expirationDate;
        @SerializedName("certificate_image")
        @Expose
        private String certificateImage;
        @SerializedName("active")
        @Expose
        private Integer active;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
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

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Object getLicenseNumber() {
            return licenseNumber;
        }

        public void setLicenseNumber(Object licenseNumber) {
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

        public Integer getActive() {
            return active;
        }

        public void setActive(Integer active) {
            this.active = active;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getResume() {
            return resume;
        }

        public void setResume(String resume) {
            this.resume = resume;
        }

    }
