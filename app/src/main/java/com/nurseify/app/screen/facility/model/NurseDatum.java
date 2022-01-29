package com.nurseify.app.screen.facility.model;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NurseDatum {


    @SerializedName("nurse_id")
    @Expose
    private String nurseId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("nurse_logo")
    @Expose
    private String nurseLogo;
    @SerializedName("nurse_email")
    @Expose
    private String nurseEmail;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("fcm_token")
    @Expose
    private String fcmToken;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("new_mobile")
    @Expose
    private String newMobile;
    @SerializedName("email_notification")
    @Expose
    private String emailNotification;
    @SerializedName("sms_notification")
    @Expose
    private String smsNotification;
    @SerializedName("specialty")
    @Expose
    private String specialty;
    @SerializedName("specialty_definition")
    @Expose
    private List<SpecialtyDefinition> specialtyDefinition = null;
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
    private String servingPreceptor;
    @SerializedName("serving_interim_nurse_leader")
    @Expose
    private String servingInterimNurseLeader;
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
    @SerializedName("ehr_proficiency_cerner")
    @Expose
    private String ehrProficiencyCerner;
    @SerializedName("ehr_proficiency_cerner_definition")
    @Expose
    private String ehrProficiencyCernerDefinition;
    @SerializedName("ehr_proficiency_meditech")
    @Expose
    private String ehrProficiencyMeditech;
    @SerializedName("ehr_proficiency_meditech_definition")
    @Expose
    private String ehrProficiencyMeditechDefinition;
    @SerializedName("ehr_proficiency_epic")
    @Expose
    private String ehrProficiencyEpic;
    @SerializedName("ehr_proficiency_epic_definition")
    @Expose
    private String ehrProficiencyEpicDefinition;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("nurses_video")
    @Expose
    private String nursesVideo;
    @SerializedName("nurses_facebook")
    @Expose
    private String nursesFacebook;
    @SerializedName("nurses_twitter")
    @Expose
    private String nursesTwitter;
    @SerializedName("nurses_linkedin")
    @Expose
    private String nursesLinkedin;
    @SerializedName("nurses_instagram")
    @Expose
    private String nursesInstagram;
    @SerializedName("nurses_pinterest")
    @Expose
    private String nursesPinterest;
    @SerializedName("nurses_tiktok")
    @Expose
    private String nursesTiktok;
    @SerializedName("nurses_sanpchat")
    @Expose
    private String nursesSanpchat;
    @SerializedName("nurses_youtube")
    @Expose
    private String nursesYoutube;
    @SerializedName("clinical_educator")
    @Expose
    private String clinicalEducator;
    @SerializedName("is_daisy_award_winner")
    @Expose
    private String isDaisyAwardWinner;
    @SerializedName("employee_of_the_mth_qtr_yr")
    @Expose
    private String employeeOfTheMthQtrYr;
    @SerializedName("other_nursing_awards")
    @Expose
    private String otherNursingAwards;
    @SerializedName("is_professional_practice_council")
    @Expose
    private String isProfessionalPracticeCouncil;
    @SerializedName("is_research_publications")
    @Expose
    private String isResearchPublications;
    @SerializedName("credential_title")
    @Expose
    private String credentialTitle;
    @SerializedName("mu_specialty")
    @Expose
    private String muSpecialty;
    @SerializedName("additional_photos")
    @Expose
    private String additionalPhotos;
    @SerializedName("languages")
    @Expose
    private String languages;
    @SerializedName("additional_files")
    @Expose
    private String additionalFiles;
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
    private String resume;
    @SerializedName("nu_video")
    @Expose
    private String nuVideo;
    @SerializedName("nu_video_embed_url")
    @Expose
    private String nuVideoEmbedUrl;
    @SerializedName("is_verified")
    @Expose
    private String isVerified;
    @SerializedName("is_verified_nli")
    @Expose
    private String isVerifiedNli;
    @SerializedName("gig_account_id")
    @Expose
    private String gigAccountId;
    @SerializedName("is_gig_invite")
    @Expose
    private String isGigInvite;
    @SerializedName("gig_account_create_date")
    @Expose
    private String gigAccountCreateDate;
    @SerializedName("gig_account_invite_date")
    @Expose
    private String gigAccountInviteDate;
    @SerializedName("rating")
    @Expose
    private Rating rating;
    @SerializedName("work_experience")
    @Expose
    private String workExperience;

    public String getNurseId() {
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        this.nurseId = nurseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        if (TextUtils.isEmpty(languages))
            lastName = "";
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNurseLogo() {
        return nurseLogo;
    }

    public void setNurseLogo(String nurseLogo) {
        this.nurseLogo = nurseLogo;
    }

    public String getNurseEmail() {
        return nurseEmail;
    }

    public void setNurseEmail(String nurseEmail) {
        this.nurseEmail = nurseEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNewMobile() {
        return newMobile;
    }

    public void setNewMobile(String newMobile) {
        this.newMobile = newMobile;
    }

    public String getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(String emailNotification) {
        this.emailNotification = emailNotification;
    }

    public String getSmsNotification() {
        return smsNotification;
    }

    public void setSmsNotification(String smsNotification) {
        this.smsNotification = smsNotification;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public List<SpecialtyDefinition> getSpecialtyDefinition() {
        return specialtyDefinition;
    }

    public void setSpecialtyDefinition(List<SpecialtyDefinition> specialtyDefinition) {
        this.specialtyDefinition = specialtyDefinition;
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

    public String getServingPreceptor() {
        return servingPreceptor;
    }

    public void setServingPreceptor(String servingPreceptor) {
        this.servingPreceptor = servingPreceptor;
    }

    public String getServingInterimNurseLeader() {
        return servingInterimNurseLeader;
    }

    public void setServingInterimNurseLeader(String servingInterimNurseLeader) {
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

    public String getEhrProficiencyCerner() {
        return ehrProficiencyCerner;
    }

    public void setEhrProficiencyCerner(String ehrProficiencyCerner) {
        this.ehrProficiencyCerner = ehrProficiencyCerner;
    }

    public String getEhrProficiencyCernerDefinition() {
        return ehrProficiencyCernerDefinition;
    }

    public void setEhrProficiencyCernerDefinition(String ehrProficiencyCernerDefinition) {
        this.ehrProficiencyCernerDefinition = ehrProficiencyCernerDefinition;
    }

    public String getEhrProficiencyMeditech() {
        return ehrProficiencyMeditech;
    }

    public void setEhrProficiencyMeditech(String ehrProficiencyMeditech) {
        this.ehrProficiencyMeditech = ehrProficiencyMeditech;
    }

    public String getEhrProficiencyMeditechDefinition() {
        return ehrProficiencyMeditechDefinition;
    }

    public void setEhrProficiencyMeditechDefinition(String ehrProficiencyMeditechDefinition) {
        this.ehrProficiencyMeditechDefinition = ehrProficiencyMeditechDefinition;
    }

    public String getEhrProficiencyEpic() {
        return ehrProficiencyEpic;
    }

    public void setEhrProficiencyEpic(String ehrProficiencyEpic) {
        this.ehrProficiencyEpic = ehrProficiencyEpic;
    }

    public String getEhrProficiencyEpicDefinition() {
        return ehrProficiencyEpicDefinition;
    }

    public void setEhrProficiencyEpicDefinition(String ehrProficiencyEpicDefinition) {
        this.ehrProficiencyEpicDefinition = ehrProficiencyEpicDefinition;
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

    public String getNursesVideo() {
        return nursesVideo;
    }

    public void setNursesVideo(String nursesVideo) {
        this.nursesVideo = nursesVideo;
    }

    public String getNursesFacebook() {
        return nursesFacebook;
    }

    public void setNursesFacebook(String nursesFacebook) {
        this.nursesFacebook = nursesFacebook;
    }

    public String getNursesTwitter() {
        return nursesTwitter;
    }

    public void setNursesTwitter(String nursesTwitter) {
        this.nursesTwitter = nursesTwitter;
    }

    public String getNursesLinkedin() {
        return nursesLinkedin;
    }

    public void setNursesLinkedin(String nursesLinkedin) {
        this.nursesLinkedin = nursesLinkedin;
    }

    public String getNursesInstagram() {
        return nursesInstagram;
    }

    public void setNursesInstagram(String nursesInstagram) {
        this.nursesInstagram = nursesInstagram;
    }

    public String getNursesPinterest() {
        return nursesPinterest;
    }

    public void setNursesPinterest(String nursesPinterest) {
        this.nursesPinterest = nursesPinterest;
    }

    public String getNursesTiktok() {
        return nursesTiktok;
    }

    public void setNursesTiktok(String nursesTiktok) {
        this.nursesTiktok = nursesTiktok;
    }

    public String getNursesSanpchat() {
        return nursesSanpchat;
    }

    public void setNursesSanpchat(String nursesSanpchat) {
        this.nursesSanpchat = nursesSanpchat;
    }

    public String getNursesYoutube() {
        return nursesYoutube;
    }

    public void setNursesYoutube(String nursesYoutube) {
        this.nursesYoutube = nursesYoutube;
    }

    public String getClinicalEducator() {
        return clinicalEducator;
    }

    public void setClinicalEducator(String clinicalEducator) {
        this.clinicalEducator = clinicalEducator;
    }

    public String getIsDaisyAwardWinner() {
        return isDaisyAwardWinner;
    }

    public void setIsDaisyAwardWinner(String isDaisyAwardWinner) {
        this.isDaisyAwardWinner = isDaisyAwardWinner;
    }

    public String getEmployeeOfTheMthQtrYr() {
        return employeeOfTheMthQtrYr;
    }

    public void setEmployeeOfTheMthQtrYr(String employeeOfTheMthQtrYr) {
        this.employeeOfTheMthQtrYr = employeeOfTheMthQtrYr;
    }

    public String getOtherNursingAwards() {
        return otherNursingAwards;
    }

    public void setOtherNursingAwards(String otherNursingAwards) {
        this.otherNursingAwards = otherNursingAwards;
    }

    public String getIsProfessionalPracticeCouncil() {
        return isProfessionalPracticeCouncil;
    }

    public void setIsProfessionalPracticeCouncil(String isProfessionalPracticeCouncil) {
        this.isProfessionalPracticeCouncil = isProfessionalPracticeCouncil;
    }

    public String getIsResearchPublications() {
        return isResearchPublications;
    }

    public void setIsResearchPublications(String isResearchPublications) {
        this.isResearchPublications = isResearchPublications;
    }

    public String getCredentialTitle() {
        return credentialTitle;
    }

    public void setCredentialTitle(String credentialTitle) {
        this.credentialTitle = credentialTitle;
    }

    public String getMuSpecialty() {
        return muSpecialty;
    }

    public void setMuSpecialty(String muSpecialty) {
        this.muSpecialty = muSpecialty;
    }

    public String getAdditionalPhotos() {
        return additionalPhotos;
    }

    public void setAdditionalPhotos(String additionalPhotos) {
        this.additionalPhotos = additionalPhotos;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getAdditionalFiles() {
        return additionalFiles;
    }

    public void setAdditionalFiles(String additionalFiles) {
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

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getNuVideo() {
        return nuVideo;
    }

    public void setNuVideo(String nuVideo) {
        this.nuVideo = nuVideo;
    }

    public String getNuVideoEmbedUrl() {
        return nuVideoEmbedUrl;
    }

    public void setNuVideoEmbedUrl(String nuVideoEmbedUrl) {
        this.nuVideoEmbedUrl = nuVideoEmbedUrl;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getIsVerifiedNli() {
        return isVerifiedNli;
    }

    public void setIsVerifiedNli(String isVerifiedNli) {
        this.isVerifiedNli = isVerifiedNli;
    }

    public String getGigAccountId() {
        return gigAccountId;
    }

    public void setGigAccountId(String gigAccountId) {
        this.gigAccountId = gigAccountId;
    }

    public String getIsGigInvite() {
        return isGigInvite;
    }

    public void setIsGigInvite(String isGigInvite) {
        this.isGigInvite = isGigInvite;
    }

    public String getGigAccountCreateDate() {
        return gigAccountCreateDate;
    }

    public void setGigAccountCreateDate(String gigAccountCreateDate) {
        this.gigAccountCreateDate = gigAccountCreateDate;
    }

    public String getGigAccountInviteDate() {
        return gigAccountInviteDate;
    }

    public void setGigAccountInviteDate(String gigAccountInviteDate) {
        this.gigAccountInviteDate = gigAccountInviteDate;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }


    public class Rating {

        @SerializedName("over_all")
        @Expose
        private String overAll;
        @SerializedName("clinical_skills")
        @Expose
        private String clinicalSkills;
        @SerializedName("nurse_teamwork")
        @Expose
        private String nurseTeamwork;
        @SerializedName("interpersonal_skills")
        @Expose
        private String interpersonalSkills;
        @SerializedName("work_ethic")
        @Expose
        private String workEthic;

        public String getOverAll() {
            return overAll;
        }

        public void setOverAll(String overAll) {
            this.overAll = overAll;
        }

        public String getClinicalSkills() {
            return clinicalSkills;
        }

        public void setClinicalSkills(String clinicalSkills) {
            this.clinicalSkills = clinicalSkills;
        }

        public String getNurseTeamwork() {
            return nurseTeamwork;
        }

        public void setNurseTeamwork(String nurseTeamwork) {
            this.nurseTeamwork = nurseTeamwork;
        }

        public String getInterpersonalSkills() {
            return interpersonalSkills;
        }

        public void setInterpersonalSkills(String interpersonalSkills) {
            this.interpersonalSkills = interpersonalSkills;
        }

        public String getWorkEthic() {
            return workEthic;
        }

        public void setWorkEthic(String workEthic) {
            this.workEthic = workEthic;
        }

    }

    public class SpecialtyDefinition {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
