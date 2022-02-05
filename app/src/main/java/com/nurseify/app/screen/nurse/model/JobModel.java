package com.nurseify.app.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class JobModel {

    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<JobDatum> data = null;

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

    public List<JobDatum> getData() {
        return data;
    }

    public void setData(List<JobDatum> data) {
        this.data = data;
    }


    public class JobDatum {




        @SerializedName("job_id")
        @Expose
        private String jobId;
        @SerializedName("preferred_specialty")
        @Expose
        private Integer preferredSpecialty;
        @SerializedName("preferred_specialty_definition")
        @Expose
        private String preferredSpecialtyDefinition;
        @SerializedName("preferred_assignment_duration")
        @Expose
        private Integer preferredAssignmentDuration;
        @SerializedName("preferred_assignment_duration_definition")
        @Expose
        private String preferredAssignmentDurationDefinition;
        @SerializedName("preferred_shift_duration")
        @Expose
        private Integer preferredShiftDuration;
        @SerializedName("preferred_shift_duration_definition")
        @Expose
        private String preferredShiftDurationDefinition;
        @SerializedName("preferred_work_location")
        @Expose
        private Integer preferredWorkLocation;
        @SerializedName("preferred_work_location_definition")
        @Expose
        private String preferredWorkLocationDefinition;
        @SerializedName("preferred_work_area")
        @Expose
        private String preferredWorkArea;
        @SerializedName("preferred_days_of_the_week")
        @Expose
        private List<String> preferredDaysOfTheWeek = null;
        @SerializedName("preferred_hourly_pay_rate")
        @Expose
        private String preferredHourlyPayRate;
        @SerializedName("preferred_experience")
        @Expose
        private String preferredExperience;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("created_at_definition")
        @Expose
        private String createdAtDefinition;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private String deletedAt;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("active")
        @Expose
        private Integer active;
        @SerializedName("facility_id")
        @Expose
        private String facilityId;
        @SerializedName("job_video")
        @Expose
        private String jobVideo;
        @SerializedName("seniority_level")
        @Expose
        private Integer seniorityLevel;
        @SerializedName("seniority_level_definition")
        @Expose
        private String seniorityLevelDefinition;
        @SerializedName("job_function")
        @Expose
        private Integer jobFunction;
        @SerializedName("job_function_definition")
        @Expose
        private String jobFunctionDefinition;
        @SerializedName("responsibilities")
        @Expose
        private String responsibilities;
        @SerializedName("qualifications")
        @Expose
        private String qualifications;
        @SerializedName("job_cerner_exp")
        @Expose
        private Integer jobCernerExp;
        @SerializedName("job_cerner_exp_definition")
        @Expose
        private String jobCernerExpDefinition;
        @SerializedName("job_meditech_exp")
        @Expose
        private Integer jobMeditechExp;
        @SerializedName("job_meditech_exp_definition")
        @Expose
        private String jobMeditechExpDefinition;
        @SerializedName("job_epic_exp")
        @Expose
        private Integer jobEpicExp;
        @SerializedName("job_epic_exp_definition")
        @Expose
        private String jobEpicExpDefinition;
        @SerializedName("job_other_exp")
        @Expose
        private String jobOtherExp;
        @SerializedName("video_embed_url")
        @Expose
        private String videoEmbedUrl;
        @SerializedName("is_open")
        @Expose
        private Integer isOpen;
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
        @SerializedName("cno_message")
        @Expose
        private String cnoMessage;
        @SerializedName("cno_image")
        @Expose
        private String cnoImage;
        @SerializedName("gallary _images")
        @Expose
        private String gallaryImages;
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
        @SerializedName("f_lat")
        @Expose
        private String fLat;
        @SerializedName("f_lang")
        @Expose
        private String fLang;
        @SerializedName("f_emr")
        @Expose
        private String fEmr;
        @SerializedName("f_emr_other")
        @Expose
        private String fEmrOther;
        @SerializedName("f_bcheck_provider")
        @Expose
        private String fBcheckProvider;
        @SerializedName("f_bcheck_provider_other")
        @Expose
        private String fBcheckProviderOther;
        @SerializedName("nurse_cred_soft")
        @Expose
        private String nurseCredSoft;
        @SerializedName("nurse_cred_soft_other")
        @Expose
        private String nurseCredSoftOther;
        @SerializedName("nurse_scheduling_sys")
        @Expose
        private String nurseSchedulingSys;
        @SerializedName("nurse_scheduling_sys_other")
        @Expose
        private String nurseSchedulingSysOther;
        @SerializedName("time_attend_sys")
        @Expose
        private String timeAttendSys;
        @SerializedName("time_attend_sys_other")
        @Expose
        private String timeAttendSysOther;
        @SerializedName("licensed_beds")
        @Expose
        private String licensedBeds;
        @SerializedName("trauma_designation")
        @Expose
        private String traumaDesignation;
        @SerializedName("total_applied")
        @Expose
        private String totalApplied;
        @SerializedName("is_applied")
        @Expose
        private String isApplied;
        @SerializedName("is_liked")
        @Expose
        private String isLiked;
        @SerializedName("shift")
        @Expose
        private String shift;
        @SerializedName("start_date")
        @Expose
        private String startDate;

        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public Integer getPreferredSpecialty() {
            return preferredSpecialty;
        }

        public void setPreferredSpecialty(Integer preferredSpecialty) {
            this.preferredSpecialty = preferredSpecialty;
        }

        public String getPreferredSpecialtyDefinition() {
            return preferredSpecialtyDefinition;
        }

        public void setPreferredSpecialtyDefinition(String preferredSpecialtyDefinition) {
            this.preferredSpecialtyDefinition = preferredSpecialtyDefinition;
        }

        public Integer getPreferredAssignmentDuration() {
            return preferredAssignmentDuration;
        }

        public void setPreferredAssignmentDuration(Integer preferredAssignmentDuration) {
            this.preferredAssignmentDuration = preferredAssignmentDuration;
        }

        public String getPreferredAssignmentDurationDefinition() {
            return preferredAssignmentDurationDefinition;
        }

        public void setPreferredAssignmentDurationDefinition(String preferredAssignmentDurationDefinition) {
            this.preferredAssignmentDurationDefinition = preferredAssignmentDurationDefinition;
        }

        public Integer getPreferredShiftDuration() {
            return preferredShiftDuration;
        }

        public void setPreferredShiftDuration(Integer preferredShiftDuration) {
            this.preferredShiftDuration = preferredShiftDuration;
        }

        public String getPreferredShiftDurationDefinition() {
            return preferredShiftDurationDefinition;
        }

        public void setPreferredShiftDurationDefinition(String preferredShiftDurationDefinition) {
            this.preferredShiftDurationDefinition = preferredShiftDurationDefinition;
        }

        public Integer getPreferredWorkLocation() {
            return preferredWorkLocation;
        }

        public void setPreferredWorkLocation(Integer preferredWorkLocation) {
            this.preferredWorkLocation = preferredWorkLocation;
        }

        public String getPreferredWorkLocationDefinition() {
            return preferredWorkLocationDefinition;
        }

        public void setPreferredWorkLocationDefinition(String preferredWorkLocationDefinition) {
            this.preferredWorkLocationDefinition = preferredWorkLocationDefinition;
        }

        public String getPreferredWorkArea() {
            return preferredWorkArea;
        }

        public void setPreferredWorkArea(String preferredWorkArea) {
            this.preferredWorkArea = preferredWorkArea;
        }

        public List<String> getPreferredDaysOfTheWeek() {
            return preferredDaysOfTheWeek;
        }

        public void setPreferredDaysOfTheWeek(List<String> preferredDaysOfTheWeek) {
            this.preferredDaysOfTheWeek = preferredDaysOfTheWeek;
        }

        public String getPreferredHourlyPayRate() {
            return preferredHourlyPayRate;
        }

        public void setPreferredHourlyPayRate(String preferredHourlyPayRate) {
            this.preferredHourlyPayRate = preferredHourlyPayRate;
        }

        public String getPreferredExperience() {
            return preferredExperience;
        }

        public void setPreferredExperience(String preferredExperience) {
            this.preferredExperience = preferredExperience;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getCreatedAtDefinition() {
            return createdAtDefinition;
        }

        public void setCreatedAtDefinition(String createdAtDefinition) {
            this.createdAtDefinition = createdAtDefinition;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(String deletedAt) {
            this.deletedAt = deletedAt;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public Integer getActive() {
            return active;
        }

        public void setActive(Integer active) {
            this.active = active;
        }

        public String getFacilityId() {
            return facilityId;
        }

        public void setFacilityId(String facilityId) {
            this.facilityId = facilityId;
        }

        public String getJobVideo() {
            return jobVideo;
        }

        public void setJobVideo(String jobVideo) {
            this.jobVideo = jobVideo;
        }

        public Integer getSeniorityLevel() {
            return seniorityLevel;
        }

        public void setSeniorityLevel(Integer seniorityLevel) {
            this.seniorityLevel = seniorityLevel;
        }

        public String getSeniorityLevelDefinition() {
            return seniorityLevelDefinition;
        }

        public void setSeniorityLevelDefinition(String seniorityLevelDefinition) {
            this.seniorityLevelDefinition = seniorityLevelDefinition;
        }

        public Integer getJobFunction() {
            return jobFunction;
        }

        public void setJobFunction(Integer jobFunction) {
            this.jobFunction = jobFunction;
        }

        public String getJobFunctionDefinition() {
            return jobFunctionDefinition;
        }

        public void setJobFunctionDefinition(String jobFunctionDefinition) {
            this.jobFunctionDefinition = jobFunctionDefinition;
        }

        public String getResponsibilities() {
            return responsibilities;
        }

        public void setResponsibilities(String responsibilities) {
            this.responsibilities = responsibilities;
        }

        public String getQualifications() {
            return qualifications;
        }

        public void setQualifications(String qualifications) {
            this.qualifications = qualifications;
        }

        public Integer getJobCernerExp() {
            return jobCernerExp;
        }

        public void setJobCernerExp(Integer jobCernerExp) {
            this.jobCernerExp = jobCernerExp;
        }

        public String getJobCernerExpDefinition() {
            return jobCernerExpDefinition;
        }

        public void setJobCernerExpDefinition(String jobCernerExpDefinition) {
            this.jobCernerExpDefinition = jobCernerExpDefinition;
        }

        public Integer getJobMeditechExp() {
            return jobMeditechExp;
        }

        public void setJobMeditechExp(Integer jobMeditechExp) {
            this.jobMeditechExp = jobMeditechExp;
        }

        public String getJobMeditechExpDefinition() {
            return jobMeditechExpDefinition;
        }

        public void setJobMeditechExpDefinition(String jobMeditechExpDefinition) {
            this.jobMeditechExpDefinition = jobMeditechExpDefinition;
        }

        public Integer getJobEpicExp() {
            return jobEpicExp;
        }

        public void setJobEpicExp(Integer jobEpicExp) {
            this.jobEpicExp = jobEpicExp;
        }

        public String getJobEpicExpDefinition() {
            return jobEpicExpDefinition;
        }

        public void setJobEpicExpDefinition(String jobEpicExpDefinition) {
            this.jobEpicExpDefinition = jobEpicExpDefinition;
        }

        public String getJobOtherExp() {
            return jobOtherExp;
        }

        public void setJobOtherExp(String jobOtherExp) {
            this.jobOtherExp = jobOtherExp;
        }

        public String getVideoEmbedUrl() {
            return videoEmbedUrl;
        }

        public void setVideoEmbedUrl(String videoEmbedUrl) {
            this.videoEmbedUrl = videoEmbedUrl;
        }

        public Integer getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(Integer isOpen) {
            this.isOpen = isOpen;
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

        public String getTraumaDesignation() {
            return traumaDesignation;
        }

        public void setTraumaDesignation(String traumaDesignation) {
            this.traumaDesignation = traumaDesignation;
        }

        public String getTotalApplied() {
            return totalApplied;
        }

        public void setTotalApplied(String totalApplied) {
            this.totalApplied = totalApplied;
        }

        public String getIsApplied() {
            return isApplied;
        }

        public void setIsApplied(String isApplied) {
            this.isApplied = isApplied;
        }

        public String getIsLiked() {
            return isLiked;
        }

        public void setIsLiked(String isLiked) {
            this.isLiked = isLiked;
        }

        public String getShift() {
            return shift;
        }

        public void setShift(String shift) {
            this.shift = shift;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }


    }

}