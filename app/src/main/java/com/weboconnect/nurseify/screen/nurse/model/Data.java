package com.weboconnect.nurseify.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("specialty")
    @Expose
    private String specialty;
    @SerializedName("nursing_license_state")
    @Expose
    private String nursingLicenseState;
    @SerializedName("nursing_license_number")
    @Expose
    private String nursingLicenseNumber;
    @SerializedName("highest_nursing_degree")
    @Expose
    private String highestNursingDegree;
    @SerializedName("serving_preceptor")
    @Expose
    private Integer servingPreceptor;
    @SerializedName("serving_interim_nurse_leader")
    @Expose
    private Integer servingInterimNurseLeader;
    @SerializedName("leadership_roles")
    @Expose
    private Object leadershipRoles;
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
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("hourly_pay_rate")
    @Expose
    private String hourlyPayRate;
    @SerializedName("experience_as_acute_care_facility")
    @Expose
    private String experienceAsAcuteCareFacility;
    @SerializedName("experience_as_ambulatory_care_facility")
    @Expose
    private String experienceAsAmbulatoryCareFacility;
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
    @SerializedName("ehr_proficiency_cerner")
    @Expose
    private String ehrProficiencyCerner;
    @SerializedName("ehr_proficiency_meditech")
    @Expose
    private String ehrProficiencyMeditech;
    @SerializedName("ehr_proficiency_epic")
    @Expose
    private String ehrProficiencyEpic;
    @SerializedName("ehr_proficiency_other")
    @Expose
    private String ehrProficiencyOther;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("summary")
    @Expose
    private Object summary;
    @SerializedName("nurses_video")
    @Expose
    private Object nursesVideo;
    @SerializedName("nurses_facebook")
    @Expose
    private Object nursesFacebook;
    @SerializedName("nurses_twitter")
    @Expose
    private Object nursesTwitter;
    @SerializedName("nurses_linkedin")
    @Expose
    private Object nursesLinkedin;
    @SerializedName("nurses_instagram")
    @Expose
    private Object nursesInstagram;
    @SerializedName("nurses_pinterest")
    @Expose
    private Object nursesPinterest;
    @SerializedName("nurses_tiktok")
    @Expose
    private Object nursesTiktok;
    @SerializedName("nurses_sanpchat")
    @Expose
    private Object nursesSanpchat;
    @SerializedName("nurses_youtube")
    @Expose
    private Object nursesYoutube;
    @SerializedName("clinical_educator")
    @Expose
    private Integer clinicalEducator;
    @SerializedName("is_daisy_award_winner")
    @Expose
    private Integer isDaisyAwardWinner;
    @SerializedName("employee_of_the_mth_qtr_yr")
    @Expose
    private Integer employeeOfTheMthQtrYr;
    @SerializedName("other_nursing_awards")
    @Expose
    private Integer otherNursingAwards;
    @SerializedName("is_professional_practice_council")
    @Expose
    private Integer isProfessionalPracticeCouncil;
    @SerializedName("is_research_publications")
    @Expose
    private Integer isResearchPublications;
    @SerializedName("credential_title")
    @Expose
    private Object credentialTitle;
    @SerializedName("mu_specialty")
    @Expose
    private Object muSpecialty;
    @SerializedName("additional_photos")
    @Expose
    private Object additionalPhotos;
    @SerializedName("languages")
    @Expose
    private String languages;
    @SerializedName("additional_files")
    @Expose
    private Object additionalFiles;
    @SerializedName("college_uni_name")
    @Expose
    private String collegeUniName;
    @SerializedName("college_uni_city")
    @Expose
    private String collegeUniCity;
    @SerializedName("college_uni_state")
    @Expose
    private String collegeUniState;
    @SerializedName("college_uni_country")
    @Expose
    private String collegeUniCountry;
    @SerializedName("facility_hourly_pay_rate")
    @Expose
    private String facilityHourlyPayRate;
    @SerializedName("n_lat")
    @Expose
    private Object nLat;
    @SerializedName("n_lang")
    @Expose
    private Object nLang;
    @SerializedName("resume")
    @Expose
    private Object resume;
    @SerializedName("nu_video")
    @Expose
    private Object nuVideo;
    @SerializedName("nu_video_embed_url")
    @Expose
    private Object nuVideoEmbedUrl;
    @SerializedName("is_verified")
    @Expose
    private Integer isVerified;
    @SerializedName("gig_account_id")
    @Expose
    private Object gigAccountId;
    @SerializedName("is_gig_invite")
    @Expose
    private Integer isGigInvite;
    @SerializedName("gig_account_create_date")
    @Expose
    private Object gigAccountCreateDate;
    @SerializedName("gig_account_invite_date")
    @Expose
    private Object gigAccountInviteDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getNursingLicenseState() {
        return nursingLicenseState;
    }

    public void setNursingLicenseState(String nursingLicenseState) {
        this.nursingLicenseState = nursingLicenseState;
    }

    public String getNursingLicenseNumber() {
        return nursingLicenseNumber;
    }

    public void setNursingLicenseNumber(String nursingLicenseNumber) {
        this.nursingLicenseNumber = nursingLicenseNumber;
    }

    public String getHighestNursingDegree() {
        return highestNursingDegree;
    }

    public void setHighestNursingDegree(String highestNursingDegree) {
        this.highestNursingDegree = highestNursingDegree;
    }

    public Integer getServingPreceptor() {
        return servingPreceptor;
    }

    public void setServingPreceptor(Integer servingPreceptor) {
        this.servingPreceptor = servingPreceptor;
    }

    public Integer getServingInterimNurseLeader() {
        return servingInterimNurseLeader;
    }

    public void setServingInterimNurseLeader(Integer servingInterimNurseLeader) {
        this.servingInterimNurseLeader = servingInterimNurseLeader;
    }

    public Object getLeadershipRoles() {
        return leadershipRoles;
    }

    public void setLeadershipRoles(Object leadershipRoles) {
        this.leadershipRoles = leadershipRoles;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHourlyPayRate() {
        return hourlyPayRate;
    }

    public void setHourlyPayRate(String hourlyPayRate) {
        this.hourlyPayRate = hourlyPayRate;
    }

    public String getExperienceAsAcuteCareFacility() {
        return experienceAsAcuteCareFacility;
    }

    public void setExperienceAsAcuteCareFacility(String experienceAsAcuteCareFacility) {
        this.experienceAsAcuteCareFacility = experienceAsAcuteCareFacility;
    }

    public String getExperienceAsAmbulatoryCareFacility() {
        return experienceAsAmbulatoryCareFacility;
    }

    public void setExperienceAsAmbulatoryCareFacility(String experienceAsAmbulatoryCareFacility) {
        this.experienceAsAmbulatoryCareFacility = experienceAsAmbulatoryCareFacility;
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

    public String getEhrProficiencyCerner() {
        return ehrProficiencyCerner;
    }

    public void setEhrProficiencyCerner(String ehrProficiencyCerner) {
        this.ehrProficiencyCerner = ehrProficiencyCerner;
    }

    public String getEhrProficiencyMeditech() {
        return ehrProficiencyMeditech;
    }

    public void setEhrProficiencyMeditech(String ehrProficiencyMeditech) {
        this.ehrProficiencyMeditech = ehrProficiencyMeditech;
    }

    public String getEhrProficiencyEpic() {
        return ehrProficiencyEpic;
    }

    public void setEhrProficiencyEpic(String ehrProficiencyEpic) {
        this.ehrProficiencyEpic = ehrProficiencyEpic;
    }

    public String getEhrProficiencyOther() {
        return ehrProficiencyOther;
    }

    public void setEhrProficiencyOther(String ehrProficiencyOther) {
        this.ehrProficiencyOther = ehrProficiencyOther;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Object getSummary() {
        return summary;
    }

    public void setSummary(Object summary) {
        this.summary = summary;
    }

    public Object getNursesVideo() {
        return nursesVideo;
    }

    public void setNursesVideo(Object nursesVideo) {
        this.nursesVideo = nursesVideo;
    }

    public Object getNursesFacebook() {
        return nursesFacebook;
    }

    public void setNursesFacebook(Object nursesFacebook) {
        this.nursesFacebook = nursesFacebook;
    }

    public Object getNursesTwitter() {
        return nursesTwitter;
    }

    public void setNursesTwitter(Object nursesTwitter) {
        this.nursesTwitter = nursesTwitter;
    }

    public Object getNursesLinkedin() {
        return nursesLinkedin;
    }

    public void setNursesLinkedin(Object nursesLinkedin) {
        this.nursesLinkedin = nursesLinkedin;
    }

    public Object getNursesInstagram() {
        return nursesInstagram;
    }

    public void setNursesInstagram(Object nursesInstagram) {
        this.nursesInstagram = nursesInstagram;
    }

    public Object getNursesPinterest() {
        return nursesPinterest;
    }

    public void setNursesPinterest(Object nursesPinterest) {
        this.nursesPinterest = nursesPinterest;
    }

    public Object getNursesTiktok() {
        return nursesTiktok;
    }

    public void setNursesTiktok(Object nursesTiktok) {
        this.nursesTiktok = nursesTiktok;
    }

    public Object getNursesSanpchat() {
        return nursesSanpchat;
    }

    public void setNursesSanpchat(Object nursesSanpchat) {
        this.nursesSanpchat = nursesSanpchat;
    }

    public Object getNursesYoutube() {
        return nursesYoutube;
    }

    public void setNursesYoutube(Object nursesYoutube) {
        this.nursesYoutube = nursesYoutube;
    }

    public Integer getClinicalEducator() {
        return clinicalEducator;
    }

    public void setClinicalEducator(Integer clinicalEducator) {
        this.clinicalEducator = clinicalEducator;
    }

    public Integer getIsDaisyAwardWinner() {
        return isDaisyAwardWinner;
    }

    public void setIsDaisyAwardWinner(Integer isDaisyAwardWinner) {
        this.isDaisyAwardWinner = isDaisyAwardWinner;
    }

    public Integer getEmployeeOfTheMthQtrYr() {
        return employeeOfTheMthQtrYr;
    }

    public void setEmployeeOfTheMthQtrYr(Integer employeeOfTheMthQtrYr) {
        this.employeeOfTheMthQtrYr = employeeOfTheMthQtrYr;
    }

    public Integer getOtherNursingAwards() {
        return otherNursingAwards;
    }

    public void setOtherNursingAwards(Integer otherNursingAwards) {
        this.otherNursingAwards = otherNursingAwards;
    }

    public Integer getIsProfessionalPracticeCouncil() {
        return isProfessionalPracticeCouncil;
    }

    public void setIsProfessionalPracticeCouncil(Integer isProfessionalPracticeCouncil) {
        this.isProfessionalPracticeCouncil = isProfessionalPracticeCouncil;
    }

    public Integer getIsResearchPublications() {
        return isResearchPublications;
    }

    public void setIsResearchPublications(Integer isResearchPublications) {
        this.isResearchPublications = isResearchPublications;
    }

    public Object getCredentialTitle() {
        return credentialTitle;
    }

    public void setCredentialTitle(Object credentialTitle) {
        this.credentialTitle = credentialTitle;
    }

    public Object getMuSpecialty() {
        return muSpecialty;
    }

    public void setMuSpecialty(Object muSpecialty) {
        this.muSpecialty = muSpecialty;
    }

    public Object getAdditionalPhotos() {
        return additionalPhotos;
    }

    public void setAdditionalPhotos(Object additionalPhotos) {
        this.additionalPhotos = additionalPhotos;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Object getAdditionalFiles() {
        return additionalFiles;
    }

    public void setAdditionalFiles(Object additionalFiles) {
        this.additionalFiles = additionalFiles;
    }

    public String getCollegeUniName() {
        return collegeUniName;
    }

    public void setCollegeUniName(String collegeUniName) {
        this.collegeUniName = collegeUniName;
    }

    public String getCollegeUniCity() {
        return collegeUniCity;
    }

    public void setCollegeUniCity(String collegeUniCity) {
        this.collegeUniCity = collegeUniCity;
    }

    public String getCollegeUniState() {
        return collegeUniState;
    }

    public void setCollegeUniState(String collegeUniState) {
        this.collegeUniState = collegeUniState;
    }

    public String getCollegeUniCountry() {
        return collegeUniCountry;
    }

    public void setCollegeUniCountry(String collegeUniCountry) {
        this.collegeUniCountry = collegeUniCountry;
    }

    public String getFacilityHourlyPayRate() {
        return facilityHourlyPayRate;
    }

    public void setFacilityHourlyPayRate(String facilityHourlyPayRate) {
        this.facilityHourlyPayRate = facilityHourlyPayRate;
    }

    public Object getnLat() {
        return nLat;
    }

    public void setnLat(Object nLat) {
        this.nLat = nLat;
    }

    public Object getnLang() {
        return nLang;
    }

    public void setnLang(Object nLang) {
        this.nLang = nLang;
    }

    public Object getResume() {
        return resume;
    }

    public void setResume(Object resume) {
        this.resume = resume;
    }

    public Object getNuVideo() {
        return nuVideo;
    }

    public void setNuVideo(Object nuVideo) {
        this.nuVideo = nuVideo;
    }

    public Object getNuVideoEmbedUrl() {
        return nuVideoEmbedUrl;
    }

    public void setNuVideoEmbedUrl(Object nuVideoEmbedUrl) {
        this.nuVideoEmbedUrl = nuVideoEmbedUrl;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public Object getGigAccountId() {
        return gigAccountId;
    }

    public void setGigAccountId(Object gigAccountId) {
        this.gigAccountId = gigAccountId;
    }

    public Integer getIsGigInvite() {
        return isGigInvite;
    }

    public void setIsGigInvite(Integer isGigInvite) {
        this.isGigInvite = isGigInvite;
    }

    public Object getGigAccountCreateDate() {
        return gigAccountCreateDate;
    }

    public void setGigAccountCreateDate(Object gigAccountCreateDate) {
        this.gigAccountCreateDate = gigAccountCreateDate;
    }

    public Object getGigAccountInviteDate() {
        return gigAccountInviteDate;
    }

    public void setGigAccountInviteDate(Object gigAccountInviteDate) {
        this.gigAccountInviteDate = gigAccountInviteDate;
    }

}