package com.weboconnect.nurseify.screen.facility.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacilityLoginModel {

    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private FacilityProfile data;

    public String getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FacilityProfile getData() {
        return data;
    }

    public void setData(FacilityProfile data) {
        this.data = data;
    }

    public class FacilityProfile {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("facility_id")
        @Expose
        private String facilityId;
        @SerializedName("facility_name")
        @Expose
        private String facilityName;
        @SerializedName("facility_address")
        @Expose
        private String facilityAddress;
        @SerializedName("facility_city")
        @Expose
        private String facilityCity;
        @SerializedName("facility_state")
        @Expose
        private String facilityState;
        @SerializedName("facility_postcode")
        @Expose
        private String facilityPostcode;
        @SerializedName("facility_logo")
        @Expose
        private String facilityLogo;
        @SerializedName("facility_email")
        @Expose
        private String facilityEmail;
        @SerializedName("facility_phone")
        @Expose
        private String facilityPhone;
        @SerializedName("specialty_need")
        @Expose
        private String specialtyNeed;
        @SerializedName("facility_type")
        @Expose
        private String facilityType;
        @SerializedName("facility_type_definition")
        @Expose
        private String facilityTypeDefinition;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("cno_message")
        @Expose
        private String cnoMessage;
        @SerializedName("cno_image")
        @Expose
        private String cnoImage;
        @SerializedName("gallary_images")
        @Expose
        private String gallaryImages;
        @SerializedName("video")
        @Expose
        private String video;
        @SerializedName("about_facility")
        @Expose
        private String aboutFacility;
        @SerializedName("facility_website")
        @Expose
        private String facilityWebsite;
        @SerializedName("video_embed_url")
        @Expose
        private String videoEmbedUrl;
        @SerializedName("facility_emr")
        @Expose
        private String facilityEmr;
        @SerializedName("facility_emr_definition")
        @Expose
        private String facilityEmrDefinition;
        @SerializedName("facility_bcheck_provider")
        @Expose
        private String facilityBcheckProvider;
        @SerializedName("facility_bcheck_provider_definition")
        @Expose
        private String facilityBcheckProviderDefinition;
        @SerializedName("nurse_cred_soft")
        @Expose
        private String nurseCredSoft;
        @SerializedName("nurse_cred_soft_definition")
        @Expose
        private String nurseCredSoftDefinition;
        @SerializedName("nurse_scheduling_sys")
        @Expose
        private String nurseSchedulingSys;
        @SerializedName("nurse_scheduling_sys_definition")
        @Expose
        private String nurseSchedulingSysDefinition;
        @SerializedName("time_attend_sys")
        @Expose
        private String timeAttendSys;
        @SerializedName("time_attend_sys_definition")
        @Expose
        private String timeAttendSysDefinition;
        @SerializedName("licensed_beds")
        @Expose
        private String licensedBeds;
        @SerializedName("trauma_designation")
        @Expose
        private String traumaDesignation;
        @SerializedName("trauma_designation_definition")
        @Expose
        private String traumaDesignationDefinition;
        @SerializedName("facility_social")
        @Expose
        private FacilitySocial facilitySocial;
        @SerializedName("facility_profile_flag")
        @Expose
        private String facilityProfileFlag;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFacilityId() {
            return facilityId;
        }

        public void setFacilityId(String facilityId) {
            this.facilityId = facilityId;
        }

        public String getFacilityName() {
            return facilityName;
        }

        public void setFacilityName(String facilityName) {
            this.facilityName = facilityName;
        }

        public String getFacilityAddress() {
            return facilityAddress;
        }

        public void setFacilityAddress(String facilityAddress) {
            this.facilityAddress = facilityAddress;
        }

        public String getFacilityCity() {
            return facilityCity;
        }

        public void setFacilityCity(String facilityCity) {
            this.facilityCity = facilityCity;
        }

        public String getFacilityState() {
            return facilityState;
        }

        public void setFacilityState(String facilityState) {
            this.facilityState = facilityState;
        }

        public String getFacilityPostcode() {
            return facilityPostcode;
        }

        public void setFacilityPostcode(String facilityPostcode) {
            this.facilityPostcode = facilityPostcode;
        }

        public String getFacilityLogo() {
            return facilityLogo;
        }

        public void setFacilityLogo(String facilityLogo) {
            this.facilityLogo = facilityLogo;
        }

        public String getFacilityEmail() {
            return facilityEmail;
        }

        public void setFacilityEmail(String facilityEmail) {
            this.facilityEmail = facilityEmail;
        }

        public String getFacilityPhone() {
            return facilityPhone;
        }

        public void setFacilityPhone(String facilityPhone) {
            this.facilityPhone = facilityPhone;
        }

        public String getSpecialtyNeed() {
            return specialtyNeed;
        }

        public void setSpecialtyNeed(String specialtyNeed) {
            this.specialtyNeed = specialtyNeed;
        }

        public String getFacilityType() {
            return facilityType;
        }

        public void setFacilityType(String facilityType) {
            this.facilityType = facilityType;
        }

        public String getFacilityTypeDefinition() {
            return facilityTypeDefinition;
        }

        public void setFacilityTypeDefinition(String facilityTypeDefinition) {
            this.facilityTypeDefinition = facilityTypeDefinition;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getCnoMessage() {
            return cnoMessage;
        }

        public void setCnoMessage(String cnoMessage) {
            this.cnoMessage = cnoMessage;
        }

        public String getCnoImage() {
            return cnoImage;
        }

        public void setCnoImage(String cnoImage) {
            this.cnoImage = cnoImage;
        }

        public String getGallaryImages() {
            return gallaryImages;
        }

        public void setGallaryImages(String gallaryImages) {
            this.gallaryImages = gallaryImages;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getAboutFacility() {
            return aboutFacility;
        }

        public void setAboutFacility(String aboutFacility) {
            this.aboutFacility = aboutFacility;
        }

        public String getFacilityWebsite() {
            return facilityWebsite;
        }

        public void setFacilityWebsite(String facilityWebsite) {
            this.facilityWebsite = facilityWebsite;
        }

        public String getVideoEmbedUrl() {
            return videoEmbedUrl;
        }

        public void setVideoEmbedUrl(String videoEmbedUrl) {
            this.videoEmbedUrl = videoEmbedUrl;
        }

        public String getFacilityEmr() {
            return facilityEmr;
        }

        public void setFacilityEmr(String facilityEmr) {
            this.facilityEmr = facilityEmr;
        }

        public String getFacilityEmrDefinition() {
            return facilityEmrDefinition;
        }

        public void setFacilityEmrDefinition(String facilityEmrDefinition) {
            this.facilityEmrDefinition = facilityEmrDefinition;
        }

        public String getFacilityBcheckProvider() {
            return facilityBcheckProvider;
        }

        public void setFacilityBcheckProvider(String facilityBcheckProvider) {
            this.facilityBcheckProvider = facilityBcheckProvider;
        }

        public String getFacilityBcheckProviderDefinition() {
            return facilityBcheckProviderDefinition;
        }

        public void setFacilityBcheckProviderDefinition(String facilityBcheckProviderDefinition) {
            this.facilityBcheckProviderDefinition = facilityBcheckProviderDefinition;
        }

        public String getNurseCredSoft() {
            return nurseCredSoft;
        }

        public void setNurseCredSoft(String nurseCredSoft) {
            this.nurseCredSoft = nurseCredSoft;
        }

        public String getNurseCredSoftDefinition() {
            return nurseCredSoftDefinition;
        }

        public void setNurseCredSoftDefinition(String nurseCredSoftDefinition) {
            this.nurseCredSoftDefinition = nurseCredSoftDefinition;
        }

        public String getNurseSchedulingSys() {
            return nurseSchedulingSys;
        }

        public void setNurseSchedulingSys(String nurseSchedulingSys) {
            this.nurseSchedulingSys = nurseSchedulingSys;
        }

        public String getNurseSchedulingSysDefinition() {
            return nurseSchedulingSysDefinition;
        }

        public void setNurseSchedulingSysDefinition(String nurseSchedulingSysDefinition) {
            this.nurseSchedulingSysDefinition = nurseSchedulingSysDefinition;
        }

        public String getTimeAttendSys() {
            return timeAttendSys;
        }

        public void setTimeAttendSys(String timeAttendSys) {
            this.timeAttendSys = timeAttendSys;
        }

        public String getTimeAttendSysDefinition() {
            return timeAttendSysDefinition;
        }

        public void setTimeAttendSysDefinition(String timeAttendSysDefinition) {
            this.timeAttendSysDefinition = timeAttendSysDefinition;
        }

        public String getLicensedBeds() {
            return licensedBeds;
        }

        public void setLicensedBeds(String licensedBeds) {
            this.licensedBeds = licensedBeds;
        }

        public String getTraumaDesignation() {
            return traumaDesignation;
        }

        public void setTraumaDesignation(String traumaDesignation) {
            this.traumaDesignation = traumaDesignation;
        }

        public String getTraumaDesignationDefinition() {
            return traumaDesignationDefinition;
        }

        public void setTraumaDesignationDefinition(String traumaDesignationDefinition) {
            this.traumaDesignationDefinition = traumaDesignationDefinition;
        }

        public FacilitySocial getFacilitySocial() {
            return facilitySocial;
        }

        public void setFacilitySocial(FacilitySocial facilitySocial) {
            this.facilitySocial = facilitySocial;
        }

        public String getFacilityProfileFlag() {
            return facilityProfileFlag;
        }

        public void setFacilityProfileFlag(String facilityProfileFlag) {
            this.facilityProfileFlag = facilityProfileFlag;
        }

    }

    public class FacilitySocial {

        @SerializedName("facebook")
        @Expose
        private String facebook;
        @SerializedName("twitter")
        @Expose
        private String twitter;
        @SerializedName("linkedin")
        @Expose
        private String linkedin;
        @SerializedName("instagram")
        @Expose
        private String instagram;
        @SerializedName("pinterest")
        @Expose
        private String pinterest;
        @SerializedName("tiktok")
        @Expose
        private String tiktok;
        @SerializedName("sanpchat")
        @Expose
        private String sanpchat;
        @SerializedName("youtube")
        @Expose
        private String youtube;

        public String getFacebook() {
            return facebook;
        }

        public void setFacebook(String facebook) {
            this.facebook = facebook;
        }

        public String getTwitter() {
            return twitter;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        public String getLinkedin() {
            return linkedin;
        }

        public void setLinkedin(String linkedin) {
            this.linkedin = linkedin;
        }

        public String getInstagram() {
            return instagram;
        }

        public void setInstagram(String instagram) {
            this.instagram = instagram;
        }

        public String getPinterest() {
            return pinterest;
        }

        public void setPinterest(String pinterest) {
            this.pinterest = pinterest;
        }

        public String getTiktok() {
            return tiktok;
        }

        public void setTiktok(String tiktok) {
            this.tiktok = tiktok;
        }

        public String getSanpchat() {
            return sanpchat;
        }

        public void setSanpchat(String sanpchat) {
            this.sanpchat = sanpchat;
        }

        public String getYoutube() {
            return youtube;
        }

        public void setYoutube(String youtube) {
            this.youtube = youtube;
        }

    }

}
