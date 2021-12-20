package com.weboconnect.nurseify.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoleModel {


    @SerializedName("api_status")
    @Expose
    private String apiStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Role_Data")
    @Expose
    private Role_Data Role_Data;

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

    public Role_Data getData() {
        return Role_Data;
    }

    public void setData(Role_Data Role_Data) {
        this.Role_Data = Role_Data;
    }


    public class Role_Data {


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
        private Object nursingLicenseState;
        @SerializedName("nursing_license_number")
        @Expose
        private Object nursingLicenseNumber;
        @SerializedName("highest_nursing_degree")
        @Expose
        private Integer highestNursingDegree;
        @SerializedName("serving_preceptor")
        @Expose
        private Boolean servingPreceptor;
        @SerializedName("serving_interim_nurse_leader")
        @Expose
        private Boolean servingInterimNurseLeader;
        @SerializedName("leadership_roles")
        @Expose
        private String leadershipRoles;
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
        private Integer ehrProficiencyCerner;
        @SerializedName("ehr_proficiency_meditech")
        @Expose
        private Integer ehrProficiencyMeditech;
        @SerializedName("ehr_proficiency_epic")
        @Expose
        private Integer ehrProficiencyEpic;
        @SerializedName("ehr_proficiency_other")
        @Expose
        private String ehrProficiencyOther;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("summary")
        @Expose
        private String summary;
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
        private Boolean clinicalEducator;
        @SerializedName("is_daisy_award_winner")
        @Expose
        private Boolean isDaisyAwardWinner;
        @SerializedName("employee_of_the_mth_qtr_yr")
        @Expose
        private Boolean employeeOfTheMthQtrYr;
        @SerializedName("other_nursing_awards")
        @Expose
        private Boolean otherNursingAwards;
        @SerializedName("is_professional_practice_council")
        @Expose
        private Boolean isProfessionalPracticeCouncil;
        @SerializedName("is_research_publications")
        @Expose
        private Boolean isResearchPublications;
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
        private String nLat;
        @SerializedName("n_lang")
        @Expose
        private String nLang;
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
        @SerializedName("user")
        @Expose
        private User user;

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

        public Object getNursingLicenseState() {
            return nursingLicenseState;
        }

        public void setNursingLicenseState(Object nursingLicenseState) {
            this.nursingLicenseState = nursingLicenseState;
        }

        public Object getNursingLicenseNumber() {
            return nursingLicenseNumber;
        }

        public void setNursingLicenseNumber(Object nursingLicenseNumber) {
            this.nursingLicenseNumber = nursingLicenseNumber;
        }

        public Integer getHighestNursingDegree() {
            return highestNursingDegree;
        }

        public void setHighestNursingDegree(Integer highestNursingDegree) {
            this.highestNursingDegree = highestNursingDegree;
        }

        public Boolean getServingPreceptor() {
            return servingPreceptor;
        }

        public void setServingPreceptor(Boolean servingPreceptor) {
            this.servingPreceptor = servingPreceptor;
        }

        public Boolean getServingInterimNurseLeader() {
            return servingInterimNurseLeader;
        }

        public void setServingInterimNurseLeader(Boolean servingInterimNurseLeader) {
            this.servingInterimNurseLeader = servingInterimNurseLeader;
        }

        public String getLeadershipRoles() {
            return leadershipRoles;
        }

        public void setLeadershipRoles(String leadershipRoles) {
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

        public Integer getEhrProficiencyCerner() {
            return ehrProficiencyCerner;
        }

        public void setEhrProficiencyCerner(Integer ehrProficiencyCerner) {
            this.ehrProficiencyCerner = ehrProficiencyCerner;
        }

        public Integer getEhrProficiencyMeditech() {
            return ehrProficiencyMeditech;
        }

        public void setEhrProficiencyMeditech(Integer ehrProficiencyMeditech) {
            this.ehrProficiencyMeditech = ehrProficiencyMeditech;
        }

        public Integer getEhrProficiencyEpic() {
            return ehrProficiencyEpic;
        }

        public void setEhrProficiencyEpic(Integer ehrProficiencyEpic) {
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

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
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

        public Boolean getClinicalEducator() {
            return clinicalEducator;
        }

        public void setClinicalEducator(Boolean clinicalEducator) {
            this.clinicalEducator = clinicalEducator;
        }

        public Boolean getIsDaisyAwardWinner() {
            return isDaisyAwardWinner;
        }

        public void setIsDaisyAwardWinner(Boolean isDaisyAwardWinner) {
            this.isDaisyAwardWinner = isDaisyAwardWinner;
        }

        public Boolean getEmployeeOfTheMthQtrYr() {
            return employeeOfTheMthQtrYr;
        }

        public void setEmployeeOfTheMthQtrYr(Boolean employeeOfTheMthQtrYr) {
            this.employeeOfTheMthQtrYr = employeeOfTheMthQtrYr;
        }

        public Boolean getOtherNursingAwards() {
            return otherNursingAwards;
        }

        public void setOtherNursingAwards(Boolean otherNursingAwards) {
            this.otherNursingAwards = otherNursingAwards;
        }

        public Boolean getIsProfessionalPracticeCouncil() {
            return isProfessionalPracticeCouncil;
        }

        public void setIsProfessionalPracticeCouncil(Boolean isProfessionalPracticeCouncil) {
            this.isProfessionalPracticeCouncil = isProfessionalPracticeCouncil;
        }

        public Boolean getIsResearchPublications() {
            return isResearchPublications;
        }

        public void setIsResearchPublications(Boolean isResearchPublications) {
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

        public String getnLat() {
            return nLat;
        }

        public void setnLat(String nLat) {
            this.nLat = nLat;
        }

        public String getnLang() {
            return nLang;
        }

        public void setnLang(String nLang) {
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

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

    }

    public class User {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("fcm_token")
        @Expose
        private Object fcmToken;
        @SerializedName("email_verified_at")
        @Expose
        private Object emailVerifiedAt;
        @SerializedName("date_of_birth")
        @Expose
        private Object dateOfBirth;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("email_notification")
        @Expose
        private Integer emailNotification;
        @SerializedName("sms_notification")
        @Expose
        private Integer smsNotification;
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
        @SerializedName("banned_until")
        @Expose
        private Object bannedUntil;
        @SerializedName("last_login_at")
        @Expose
        private Object lastLoginAt;
        @SerializedName("last_login_ip")
        @Expose
        private Object lastLoginIp;
        @SerializedName("fullName")
        @Expose
        private String fullName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Object getFcmToken() {
            return fcmToken;
        }

        public void setFcmToken(Object fcmToken) {
            this.fcmToken = fcmToken;
        }

        public Object getEmailVerifiedAt() {
            return emailVerifiedAt;
        }

        public void setEmailVerifiedAt(Object emailVerifiedAt) {
            this.emailVerifiedAt = emailVerifiedAt;
        }

        public Object getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(Object dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Integer getEmailNotification() {
            return emailNotification;
        }

        public void setEmailNotification(Integer emailNotification) {
            this.emailNotification = emailNotification;
        }

        public Integer getSmsNotification() {
            return smsNotification;
        }

        public void setSmsNotification(Integer smsNotification) {
            this.smsNotification = smsNotification;
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

        public Object getBannedUntil() {
            return bannedUntil;
        }

        public void setBannedUntil(Object bannedUntil) {
            this.bannedUntil = bannedUntil;
        }

        public Object getLastLoginAt() {
            return lastLoginAt;
        }

        public void setLastLoginAt(Object lastLoginAt) {
            this.lastLoginAt = lastLoginAt;
        }

        public Object getLastLoginIp() {
            return lastLoginIp;
        }

        public void setLastLoginIp(Object lastLoginIp) {
            this.lastLoginIp = lastLoginIp;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

    }
}