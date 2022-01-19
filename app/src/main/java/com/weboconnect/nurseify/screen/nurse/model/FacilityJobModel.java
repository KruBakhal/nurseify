package com.weboconnect.nurseify.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FacilityJobModel {

    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Facility> data = null;

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

    public List<Facility> getData() {
        return data;
    }

    public void setData(List<Facility> data) {
        this.data = data;
    }


    public class Facility {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("facility_logo")
        @Expose
        private String facilityLogo;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("postcode")
        @Expose
        private String postcode;
        @SerializedName("facility_type")
        @Expose
        private String facilityType;
        @SerializedName("facility_type_definition")
        @Expose
        private String facilityTypeDefinition;
        @SerializedName("active")
        @Expose
        private Integer active;
        @SerializedName("deleted_at")
        @Expose
        private String deletedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("facility_email")
        @Expose
        private String facilityEmail;
        @SerializedName("facility_phone")
        @Expose
        private String facilityPhone;
        @SerializedName("specialty_need")
        @Expose
        private String specialtyNeed;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("cno_message")
        @Expose
        private String cnoMessage;
        @SerializedName("cno_image")
        @Expose
        private String cnoImage;
        @SerializedName("gallery_images")
        @Expose
        private String galleryImages;
        @SerializedName("video")
        @Expose
        private String video;
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
        @SerializedName("about_facility")
        @Expose
        private String aboutFacility;
        @SerializedName("facility_website")
        @Expose
        private String facilityWebsite;
        @SerializedName("video_embed_url")
        @Expose
        private String videoEmbedUrl;
        @SerializedName("f_lat")
        @Expose
        private String fLat;
        @SerializedName("f_lang")
        @Expose
        private String fLang;
        @SerializedName("f_emr")
        @Expose
        private String fEmr;
        @SerializedName("f_emr_definition")
        @Expose
        private String fEmrDefinition;
        @SerializedName("f_emr_other")
        @Expose
        private String fEmrOther;
        @SerializedName("f_bcheck_provider")
        @Expose
        private String fBcheckProvider;
        @SerializedName("f_bcheck_provider_definition")
        @Expose
        private String fBcheckProviderDefinition;
        @SerializedName("f_bcheck_provider_other")
        @Expose
        private String fBcheckProviderOther;
        @SerializedName("nurse_cred_soft")
        @Expose
        private String nurseCredSoft;
        @SerializedName("nurse_cred_soft_definition")
        @Expose
        private String nurseCredSoftDefinition;
        @SerializedName("nurse_cred_soft_other")
        @Expose
        private String nurseCredSoftOther;
        @SerializedName("nurse_scheduling_sys")
        @Expose
        private String nurseSchedulingSys;
        @SerializedName("nurse_scheduling_sys_definition")
        @Expose
        private String nurseSchedulingSysDefinition;
        @SerializedName("nurse_scheduling_sys_other")
        @Expose
        private String nurseSchedulingSysOther;
        @SerializedName("time_attend_sys")
        @Expose
        private String timeAttendSys;
        @SerializedName("time_attend_sys_definition")
        @Expose
        private String timeAttendSysDefinition;
        @SerializedName("time_attend_sys_other")
        @Expose
        private String timeAttendSysOther;
        @SerializedName("licensed_beds")
        @Expose
        private String licensedBeds;
        @SerializedName("licensed_beds_definition")
        @Expose
        private String licensedBedsDefinition;
        @SerializedName("trauma_designation")
        @Expose
        private String traumaDesignation;
        @SerializedName("trauma_designation_definition")
        @Expose
        private String traumaDesignationDefinition;
        @SerializedName("preferred_specialty")
        @Expose
        private String preferredSpecialty;
        @SerializedName("preferred_specialty_definition")
        @Expose
        private String preferredSpecialtyDefinition;
        @SerializedName("total_jobs")
        @Expose
        private String totalJobs;
        @SerializedName("rating")
        @Expose
        private String rating;
        @SerializedName("is_follow")
        @Expose
        private String isFollow;
        @SerializedName("is_like")
        @Expose
        private String isLike;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFacilityLogo() {
            return facilityLogo;
        }

        public void setFacilityLogo(String facilityLogo) {
            this.facilityLogo = facilityLogo;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
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

        public Integer getActive() {
            return active;
        }

        public void setActive(Integer active) {
            this.active = active;
        }

        public String getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(String deletedAt) {
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

        public String getGalleryImages() {
            return galleryImages;
        }

        public void setGalleryImages(String galleryImages) {
            this.galleryImages = galleryImages;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

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

        public String getfLat() {
            return fLat;
        }

        public void setfLat(String fLat) {
            this.fLat = fLat;
        }

        public String getfLang() {
            return fLang;
        }

        public void setfLang(String fLang) {
            this.fLang = fLang;
        }

        public String getfEmr() {
            return fEmr;
        }

        public void setfEmr(String fEmr) {
            this.fEmr = fEmr;
        }

        public String getfEmrDefinition() {
            return fEmrDefinition;
        }

        public void setfEmrDefinition(String fEmrDefinition) {
            this.fEmrDefinition = fEmrDefinition;
        }

        public String getfEmrOther() {
            return fEmrOther;
        }

        public void setfEmrOther(String fEmrOther) {
            this.fEmrOther = fEmrOther;
        }

        public String getfBcheckProvider() {
            return fBcheckProvider;
        }

        public void setfBcheckProvider(String fBcheckProvider) {
            this.fBcheckProvider = fBcheckProvider;
        }

        public String getfBcheckProviderDefinition() {
            return fBcheckProviderDefinition;
        }

        public void setfBcheckProviderDefinition(String fBcheckProviderDefinition) {
            this.fBcheckProviderDefinition = fBcheckProviderDefinition;
        }

        public String getfBcheckProviderOther() {
            return fBcheckProviderOther;
        }

        public void setfBcheckProviderOther(String fBcheckProviderOther) {
            this.fBcheckProviderOther = fBcheckProviderOther;
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

        public String getNurseCredSoftOther() {
            return nurseCredSoftOther;
        }

        public void setNurseCredSoftOther(String nurseCredSoftOther) {
            this.nurseCredSoftOther = nurseCredSoftOther;
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

        public String getNurseSchedulingSysOther() {
            return nurseSchedulingSysOther;
        }

        public void setNurseSchedulingSysOther(String nurseSchedulingSysOther) {
            this.nurseSchedulingSysOther = nurseSchedulingSysOther;
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

        public String getTimeAttendSysOther() {
            return timeAttendSysOther;
        }

        public void setTimeAttendSysOther(String timeAttendSysOther) {
            this.timeAttendSysOther = timeAttendSysOther;
        }

        public String getLicensedBeds() {
            return licensedBeds;
        }

        public void setLicensedBeds(String licensedBeds) {
            this.licensedBeds = licensedBeds;
        }

        public String getLicensedBedsDefinition() {
            return licensedBedsDefinition;
        }

        public void setLicensedBedsDefinition(String licensedBedsDefinition) {
            this.licensedBedsDefinition = licensedBedsDefinition;
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

        public String getPreferredSpecialty() {
            return preferredSpecialty;
        }

        public void setPreferredSpecialty(String preferredSpecialty) {
            this.preferredSpecialty = preferredSpecialty;
        }

        public String getPreferredSpecialtyDefinition() {
            return preferredSpecialtyDefinition;
        }

        public void setPreferredSpecialtyDefinition(String preferredSpecialtyDefinition) {
            this.preferredSpecialtyDefinition = preferredSpecialtyDefinition;
        }

        public String getTotalJobs() {
            return totalJobs;
        }

        public void setTotalJobs(String totalJobs) {
            this.totalJobs = totalJobs;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(String isFollow) {
            this.isFollow = isFollow;
        }

        public String getIsLike() {
            return isLike;
        }

        public void setIsLike(String isLike) {
            this.isLike = isLike;
        }
    }
}
