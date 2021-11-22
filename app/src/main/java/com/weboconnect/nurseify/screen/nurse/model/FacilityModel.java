package com.weboconnect.nurseify.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FacilityModel {

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
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("active")
        @Expose
        private Integer active;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("facility_phone")
        @Expose
        private String facilityPhone;
        @SerializedName("cno_message")
        @Expose
        private String cnoMessage;
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
        @SerializedName("f_bcheck_provider")
        @Expose
        private String fBcheckProvider;
        @SerializedName("nurse_cred_soft")
        @Expose
        private String nurseCredSoft;
        @SerializedName("nurse_scheduling_sys")
        @Expose
        private String nurseSchedulingSys;
        @SerializedName("time_attend_sys")
        @Expose
        private String timeAttendSys;
        @SerializedName("licensed_beds")
        @Expose
        private String licensedBeds;
        @SerializedName("trauma_designation")
        @Expose
        private String traumaDesignation;
        @SerializedName("preferred_specialty")
        @Expose
        private Integer preferredSpecialty;
        @SerializedName("total_jobs")
        @Expose
        private String totalJobs;
        @SerializedName("rating")
        @Expose
        private String rating;
        @SerializedName("is_follow")
        @Expose
        private boolean isFollow;
        @SerializedName("is_like")
        @Expose
        private boolean isLike;

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

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getActive() {
            return active;
        }

        public void setActive(Integer active) {
            this.active = active;
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

        public String getFacilityPhone() {
            return facilityPhone;
        }

        public void setFacilityPhone(String facilityPhone) {
            this.facilityPhone = facilityPhone;
        }

        public String getCnoMessage() {
            return cnoMessage;
        }

        public void setCnoMessage(String cnoMessage) {
            this.cnoMessage = cnoMessage;
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

        public String getfBcheckProvider() {
            return fBcheckProvider;
        }

        public void setfBcheckProvider(String fBcheckProvider) {
            this.fBcheckProvider = fBcheckProvider;
        }

        public String getNurseCredSoft() {
            return nurseCredSoft;
        }

        public void setNurseCredSoft(String nurseCredSoft) {
            this.nurseCredSoft = nurseCredSoft;
        }

        public String getNurseSchedulingSys() {
            return nurseSchedulingSys;
        }

        public void setNurseSchedulingSys(String nurseSchedulingSys) {
            this.nurseSchedulingSys = nurseSchedulingSys;
        }

        public String getTimeAttendSys() {
            return timeAttendSys;
        }

        public void setTimeAttendSys(String timeAttendSys) {
            this.timeAttendSys = timeAttendSys;
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

        public Integer getPreferredSpecialty() {
            return preferredSpecialty;
        }

        public void setPreferredSpecialty(Integer preferredSpecialty) {
            this.preferredSpecialty = preferredSpecialty;
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

        public boolean getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(boolean isFollow) {
            this.isFollow = isFollow;
        }

        public boolean getIsLike() {
            return isLike;
        }

        public void setIsLike(boolean isLike) {
            this.isLike = isLike;
        }

    }

}
