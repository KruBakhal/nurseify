package com.weboconnect.nurseify.screen.nurse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserProfileData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nurse_id")
    @Expose
    private String nurseId;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("fcm_token")
    @Expose
    private String fcmToken;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("email_notification")
    @Expose
    private Integer emailNotification;
    @SerializedName("sms_notification")
    @Expose
    private Integer smsNotification;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("nursing_license_state")
    @Expose
    private String nursingLicenseState;
    @SerializedName("nursing_license_number")
    @Expose
    private String nursingLicenseNumber;
    @SerializedName("specialty")
    @Expose
    private List<Specialty> specialty = null;
    @SerializedName("work_location")
    @Expose
    private Integer workLocation;
    @SerializedName("work_location_definition")
    @Expose
    private String workLocationDefinition;
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
    @SerializedName("shift_duration")
    @Expose
    private String shiftDuration;
    @SerializedName("shift_duration_definition")
    @Expose
    private String shiftDurationDefinition;
    @SerializedName("assignment_duration")
    @Expose
    private String assignmentDuration;
    @SerializedName("assignment_duration_definition")
    @Expose
    private String assignmentDurationDefinition;
    @SerializedName("preferred_shift")
    @Expose
    private String preferredShift;
    @SerializedName("preferred_shift_definition")
    @Expose
    private String preferred_shift_definition;


    @SerializedName("days_of_the_week")
    @Expose
    private List<String> daysOfTheWeek = null;
    @SerializedName("earliest_start_date")
    @Expose
    private String earliestStartDate;
    @SerializedName("profile_detail_flag")
    @Expose
    private String profileDetailFlag;
    @SerializedName("hourly_rate_and_avail_flag")
    @Expose
    private String hourlyRateAndAvailFlag;
    @SerializedName("experience")
    @Expose
    private Experience experience;
    @SerializedName("certitficate")
    @Expose
    private List<Certitficate> certitficate = null;
    @SerializedName("resume")
    @Expose
    private String resume;
    @SerializedName("role_interest")
    @Expose
    private RoleInterest roleInterest;

    public String getPreferred_shift_definition() {
        return preferred_shift_definition;
    }

    public void setPreferred_shift_definition(String preferred_shift_definition) {
        this.preferred_shift_definition = preferred_shift_definition;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public List<Specialty> getSpecialty() {
        return specialty;
    }

    public void setSpecialty(List<Specialty> specialty) {
        this.specialty = specialty;
    }

    public Integer getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(Integer workLocation) {
        this.workLocation = workLocation;
    }

    public String getWorkLocationDefinition() {
        return workLocationDefinition;
    }

    public void setWorkLocationDefinition(String workLocationDefinition) {
        this.workLocationDefinition = workLocationDefinition;
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

    public String getShiftDuration() {
        return shiftDuration;
    }

    public void setShiftDuration(String shiftDuration) {
        this.shiftDuration = shiftDuration;
    }

    public String getShiftDurationDefinition() {
        return shiftDurationDefinition;
    }

    public void setShiftDurationDefinition(String shiftDurationDefinition) {
        this.shiftDurationDefinition = shiftDurationDefinition;
    }

    public String getAssignmentDuration() {
        return assignmentDuration;
    }

    public void setAssignmentDuration(String assignmentDuration) {
        this.assignmentDuration = assignmentDuration;
    }

    public String getAssignmentDurationDefinition() {
        return assignmentDurationDefinition;
    }

    public void setAssignmentDurationDefinition(String assignmentDurationDefinition) {
        this.assignmentDurationDefinition = assignmentDurationDefinition;
    }

    public String getPreferredShift() {
        return preferredShift;
    }

    public void setPreferredShift(String preferredShift) {
        this.preferredShift = preferredShift;
    }

    public List<String> getDaysOfTheWeek() {
        return daysOfTheWeek;
    }

    public void setDaysOfTheWeek(List<String> daysOfTheWeek) {
        this.daysOfTheWeek = daysOfTheWeek;
    }

    public String getEarliestStartDate() {
        return earliestStartDate;
    }

    public void setEarliestStartDate(String earliestStartDate) {
        this.earliestStartDate = earliestStartDate;
    }

    public String getProfileDetailFlag() {
        return profileDetailFlag;
    }

    public void setProfileDetailFlag(String profileDetailFlag) {
        this.profileDetailFlag = profileDetailFlag;
    }

    public String getHourlyRateAndAvailFlag() {
        return hourlyRateAndAvailFlag;
    }

    public void setHourlyRateAndAvailFlag(String hourlyRateAndAvailFlag) {
        this.hourlyRateAndAvailFlag = hourlyRateAndAvailFlag;
    }

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public List<Certitficate> getCertitficate() {
        return certitficate;
    }

    public void setCertitficate(List<Certitficate> certitficate) {
        this.certitficate = certitficate;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public RoleInterest getRoleInterest() {
        return roleInterest;
    }

    public void setRoleInterest(RoleInterest roleInterest) {
        this.roleInterest = roleInterest;
    }


    public class Experience {

        @SerializedName("highest_nursing_degree")
        @Expose
        private String highestNursingDegree;
        @SerializedName("highest_nursing_degree_definition")
        @Expose
        private String highestNursingDegreeDefinition;
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
        @SerializedName("ehr_proficiency_other")
        @Expose
        private String ehrProficiencyOther;

        public String getHighestNursingDegree() {
            return highestNursingDegree;
        }

        public void setHighestNursingDegree(String highestNursingDegree) {
            this.highestNursingDegree = highestNursingDegree;
        }

        public String getHighestNursingDegreeDefinition() {
            return highestNursingDegreeDefinition;
        }

        public void setHighestNursingDegreeDefinition(String highestNursingDegreeDefinition) {
            this.highestNursingDegreeDefinition = highestNursingDegreeDefinition;
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

        public String getEhrProficiencyOther() {
            return ehrProficiencyOther;
        }

        public void setEhrProficiencyOther(String ehrProficiencyOther) {
            this.ehrProficiencyOther = ehrProficiencyOther;
        }

    }

    public class RoleInterest {

        @SerializedName("nu_video_embed_url")
        @Expose
        private String nuVideoEmbedUrl;
        @SerializedName("nu_video_embed_url_definition")
        @Expose
        private String nuVideoEmbedUrlDefinition;
        @SerializedName("serving_preceptor")
        @Expose
        private String servingPreceptor;
        @SerializedName("serving_preceptor_definition")
        @Expose
        private String servingPreceptorDefinition;
        @SerializedName("serving_interim_nurse_leader")
        @Expose
        private String servingInterimNurseLeader;
        @SerializedName("serving_interim_nurse_leader_definition")
        @Expose
        private String servingInterimNurseLeaderDefinition;
        @SerializedName("clinical_educator")
        @Expose
        private String clinicalEducator;
        @SerializedName("clinical_educator_definition")
        @Expose
        private String clinicalEducatorDefinition;
        @SerializedName("is_daisy_award_winner")
        @Expose
        private String isDaisyAwardWinner;
        @SerializedName("is_daisy_award_winner_definition")
        @Expose
        private String isDaisyAwardWinnerDefinition;
        @SerializedName("employee_of_the_mth_qtr_yr")
        @Expose
        private String employeeOfTheMthQtrYr;
        @SerializedName("employee_of_the_mth_qtr_yr_definition")
        @Expose
        private String employeeOfTheMthQtrYrDefinition;
        @SerializedName("other_nursing_awards")
        @Expose
        private String otherNursingAwards;
        @SerializedName("other_nursing_awards_definition")
        @Expose
        private String otherNursingAwardsDefinition;
        @SerializedName("is_professional_practice_council")
        @Expose
        private String isProfessionalPracticeCouncil;
        @SerializedName("is_professional_practice_council_definition")
        @Expose
        private String isProfessionalPracticeCouncilDefinition;
        @SerializedName("is_research_publications")
        @Expose
        private String isResearchPublications;
        @SerializedName("is_research_publications_definition")
        @Expose
        private String isResearchPublicationsDefinition;
        @SerializedName("leadership_roles")
        @Expose
        private String leadershipRoles;
        @SerializedName("leadership_roles_definition")
        @Expose
        private String leadershipRolesDefinition;
        @SerializedName("summary")
        @Expose
        private String summary;
        @SerializedName("languages")
        @Expose
        private List<String> languages = null;
        @SerializedName("additional_files")
        @Expose
        private List<AdditionalFile> additionalFiles = null;
        @SerializedName("additional_pictures")
        @Expose
        private List<AdditionalPicture> additionalPictures = null;

        public String getNuVideoEmbedUrl() {
            return nuVideoEmbedUrl;
        }

        public void setNuVideoEmbedUrl(String nuVideoEmbedUrl) {
            this.nuVideoEmbedUrl = nuVideoEmbedUrl;
        }

        public String getNuVideoEmbedUrlDefinition() {
            return nuVideoEmbedUrlDefinition;
        }

        public void setNuVideoEmbedUrlDefinition(String nuVideoEmbedUrlDefinition) {
            this.nuVideoEmbedUrlDefinition = nuVideoEmbedUrlDefinition;
        }

        public String getServingPreceptor() {
            return servingPreceptor;
        }

        public void setServingPreceptor(String servingPreceptor) {
            this.servingPreceptor = servingPreceptor;
        }

        public String getServingPreceptorDefinition() {
            return servingPreceptorDefinition;
        }

        public void setServingPreceptorDefinition(String servingPreceptorDefinition) {
            this.servingPreceptorDefinition = servingPreceptorDefinition;
        }

        public String getServingInterimNurseLeader() {
            return servingInterimNurseLeader;
        }

        public void setServingInterimNurseLeader(String servingInterimNurseLeader) {
            this.servingInterimNurseLeader = servingInterimNurseLeader;
        }

        public String getServingInterimNurseLeaderDefinition() {
            return servingInterimNurseLeaderDefinition;
        }

        public void setServingInterimNurseLeaderDefinition(String servingInterimNurseLeaderDefinition) {
            this.servingInterimNurseLeaderDefinition = servingInterimNurseLeaderDefinition;
        }

        public String getClinicalEducator() {
            return clinicalEducator;
        }

        public void setClinicalEducator(String clinicalEducator) {
            this.clinicalEducator = clinicalEducator;
        }

        public String getClinicalEducatorDefinition() {
            return clinicalEducatorDefinition;
        }

        public void setClinicalEducatorDefinition(String clinicalEducatorDefinition) {
            this.clinicalEducatorDefinition = clinicalEducatorDefinition;
        }

        public String getIsDaisyAwardWinner() {
            return isDaisyAwardWinner;
        }

        public void setIsDaisyAwardWinner(String isDaisyAwardWinner) {
            this.isDaisyAwardWinner = isDaisyAwardWinner;
        }

        public String getIsDaisyAwardWinnerDefinition() {
            return isDaisyAwardWinnerDefinition;
        }

        public void setIsDaisyAwardWinnerDefinition(String isDaisyAwardWinnerDefinition) {
            this.isDaisyAwardWinnerDefinition = isDaisyAwardWinnerDefinition;
        }

        public String getEmployeeOfTheMthQtrYr() {
            return employeeOfTheMthQtrYr;
        }

        public void setEmployeeOfTheMthQtrYr(String employeeOfTheMthQtrYr) {
            this.employeeOfTheMthQtrYr = employeeOfTheMthQtrYr;
        }

        public String getEmployeeOfTheMthQtrYrDefinition() {
            return employeeOfTheMthQtrYrDefinition;
        }

        public void setEmployeeOfTheMthQtrYrDefinition(String employeeOfTheMthQtrYrDefinition) {
            this.employeeOfTheMthQtrYrDefinition = employeeOfTheMthQtrYrDefinition;
        }

        public String getOtherNursingAwards() {
            return otherNursingAwards;
        }

        public void setOtherNursingAwards(String otherNursingAwards) {
            this.otherNursingAwards = otherNursingAwards;
        }

        public String getOtherNursingAwardsDefinition() {
            return otherNursingAwardsDefinition;
        }

        public void setOtherNursingAwardsDefinition(String otherNursingAwardsDefinition) {
            this.otherNursingAwardsDefinition = otherNursingAwardsDefinition;
        }

        public String getIsProfessionalPracticeCouncil() {
            return isProfessionalPracticeCouncil;
        }

        public void setIsProfessionalPracticeCouncil(String isProfessionalPracticeCouncil) {
            this.isProfessionalPracticeCouncil = isProfessionalPracticeCouncil;
        }

        public String getIsProfessionalPracticeCouncilDefinition() {
            return isProfessionalPracticeCouncilDefinition;
        }

        public void setIsProfessionalPracticeCouncilDefinition(String isProfessionalPracticeCouncilDefinition) {
            this.isProfessionalPracticeCouncilDefinition = isProfessionalPracticeCouncilDefinition;
        }

        public String getIsResearchPublications() {
            return isResearchPublications;
        }

        public void setIsResearchPublications(String isResearchPublications) {
            this.isResearchPublications = isResearchPublications;
        }

        public String getIsResearchPublicationsDefinition() {
            return isResearchPublicationsDefinition;
        }

        public void setIsResearchPublicationsDefinition(String isResearchPublicationsDefinition) {
            this.isResearchPublicationsDefinition = isResearchPublicationsDefinition;
        }

        public String getLeadershipRoles() {
            return leadershipRoles;
        }

        public void setLeadershipRoles(String leadershipRoles) {
            this.leadershipRoles = leadershipRoles;
        }

        public String getLeadershipRolesDefinition() {
            return leadershipRolesDefinition;
        }

        public void setLeadershipRolesDefinition(String leadershipRolesDefinition) {
            this.leadershipRolesDefinition = leadershipRolesDefinition;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<String> getLanguages() {
            return languages;
        }

        public void setLanguages(List<String> languages) {
            this.languages = languages;
        }

        public List<AdditionalFile> getAdditionalFiles() {
            return additionalFiles;
        }

        public void setAdditionalFiles(List<AdditionalFile> additionalFiles) {
            this.additionalFiles = additionalFiles;
        }

        public List<AdditionalPicture> getAdditionalPictures() {
            return additionalPictures;
        }

        public void setAdditionalPictures(List<AdditionalPicture> additionalPictures) {
            this.additionalPictures = additionalPictures;
        }

    }

    public class Specialty {

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

    public class Certitficate {

        @SerializedName("certificate_id")
        @Expose
        private String certificateId;
        @SerializedName("search_for_credential")
        @Expose
        private Integer searchForCredential;
        @SerializedName("search_for_credential_definition")
        @Expose
        private String searchForCredentialDefinition;
        @SerializedName("effective_date")
        @Expose
        private String effectiveDate;
        @SerializedName("expiration_date")
        @Expose
        private String expirationDate;
        @SerializedName("certificate_image")
        @Expose
        private String certificateImage;

        public String getCertificateId() {
            return certificateId;
        }

        public void setCertificateId(String certificateId) {
            this.certificateId = certificateId;
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

    }

    public class AdditionalPicture {

        @SerializedName("asset_id")
        @Expose
        private String assetId;
        @SerializedName("photo")
        @Expose
        private String photo;

        public String getAssetId() {
            return assetId;
        }

        public void setAssetId(String assetId) {
            this.assetId = assetId;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

    }

    public class AdditionalFile {

        @SerializedName("asset_id")
        @Expose
        private String assetId;
        @SerializedName("photo")
        @Expose
        private String photo;

        public String getAssetId() {
            return assetId;
        }

        public void setAssetId(String assetId) {
            this.assetId = assetId;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

    }
}