package com.nurseify.app.screen.facility.model;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Facility_JobDatum {
    @SerializedName("job_id")
    @Expose
    private String jobId;
    @SerializedName("facility_first_name")
    @Expose
    private String facilityFirstName;
    @SerializedName("facility_last_name")
    @Expose
    private String facilityLastName;
    @SerializedName("facility_image")
    @Expose
    private String facilityImage;
    @SerializedName("preferred_shift")
    @Expose
    private String preferredShift;
    @SerializedName("preferred_shift_definition")
    @Expose
    private String preferredShiftDefinition;
    @SerializedName("preferred_specialty")
    @Expose
    private String preferredSpecialty;
    @SerializedName("preferred_specialty_definition")
    @Expose
    private String preferredSpecialtyDefinition;
    @SerializedName("preferred_assignment_duration")
    @Expose
    private String preferredAssignmentDuration;
    @SerializedName("preferred_assignment_duration_definition")
    @Expose
    private String preferredAssignmentDurationDefinition;
    @SerializedName("preferred_hourly_pay_rate")
    @Expose
    private String preferredHourlyPayRate;
    @SerializedName("preferred_days_of_the_week")
    @Expose
    private String preferredDaysOfTheWeek;
    @SerializedName("preferred_days_of_the_week_array")
    @Expose
    private List<String> preferredDaysOfTheWeekArray = null;
    @SerializedName("preferred_days_of_the_week_string")
    @Expose
    private String preferredDaysOfTheWeekString;
    @SerializedName("facility_id")
    @Expose
    private String facilityId;
    @SerializedName("seniority_level")
    @Expose
    private Integer seniorityLevel;
    @SerializedName("seniority_level_definition")
    @Expose
    private String seniorityLevelDefinition;
    @SerializedName("job_function")
    @Expose
    private String jobFunction;
    @SerializedName("job_function_definition")
    @Expose
    private String jobFunctionDefinition;
    @SerializedName("preferred_shift_duration")
    @Expose
    private String preferredShiftDuration;
    @SerializedName("preferred_shift_duration_definition")
    @Expose
    private String preferredShiftDurationDefinition;
    @SerializedName("preferred_work_location")
    @Expose
    private String preferredWorkLocation;
    @SerializedName("preferred_work_location_definition")
    @Expose
    private String preferredWorkLocationDefinition;
    @SerializedName("preferred_experience")
    @Expose
    private String preferredExperience;
    @SerializedName("preferred_experience_definition")
    @Expose
    private String preferredExperienceDefinition;
    @SerializedName("job_cerner_exp")
    @Expose
    private String jobCernerExp;
    @SerializedName("job_cerner_exp_definition")
    @Expose
    private String jobCernerExpDefinition;
    @SerializedName("job_meditech_exp")
    @Expose
    private String jobMeditechExp;
    @SerializedName("job_meditech_exp_definition")
    @Expose
    private String jobMeditechExpDefinition;
    @SerializedName("job_epic_exp")
    @Expose
    private String jobEpicExp;
    @SerializedName("job_epic_exp_definition")
    @Expose
    private String jobEpicExpDefinition;
    @SerializedName("job_other_exp")
    @Expose
    private String jobOtherExp;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("responsibilities")
    @Expose
    private String responsibilities;
    @SerializedName("qualifications")
    @Expose
    private String qualifications;
    @SerializedName("job_video")
    @Expose
    private String jobVideo;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("applied")
    @Expose
    private String applied;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("rating_flag")
    @Expose
    private String rating_flag;
    @SerializedName("offered_nurse_id")
    @Expose
    private String offered_nurse_id;

    public String getOffered_nurse_id() {
        return offered_nurse_id;
    }

    public void setOffered_nurse_id(String offered_nurse_id) {
        this.offered_nurse_id = offered_nurse_id;
    }

    @SerializedName("job_photos")
    @Expose
    private List<JobPhoto> jobPhotos = null;

    public List<JobPhoto> getJobPhotos() {
        return jobPhotos;
    }

    public void setJobPhotos(List<JobPhoto> jobPhotos) {
        this.jobPhotos = jobPhotos;
    }

    public String getRating_flag() {
        return rating_flag;
    }

    public void setRating_flag(String rating_flag) {
        this.rating_flag = rating_flag;
    }

    public String getStartDate() {
        if (TextUtils.isEmpty(startDate))
            startDate = "";
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        if (TextUtils.isEmpty(endDate))
            endDate = "";
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Facility_JobDatum(String facilityId, String facilityFirstName) {
        this.facilityFirstName = facilityFirstName;
        this.facilityId = facilityId;
    }

    public Facility_JobDatum() {

    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getFacilityFirstName() {
        return facilityFirstName;
    }

    public void setFacilityFirstName(String facilityFirstName) {
        this.facilityFirstName = facilityFirstName;
    }

    public String getFacilityLastName() {
        return facilityLastName;
    }

    public void setFacilityLastName(String facilityLastName) {
        this.facilityLastName = facilityLastName;
    }

    public String getFacilityImage() {
        return facilityImage;
    }

    public void setFacilityImage(String facilityImage) {
        this.facilityImage = facilityImage;
    }

    public String getPreferredShift() {
        return preferredShift;
    }

    public void setPreferredShift(String preferredShift) {
        this.preferredShift = preferredShift;
    }

    public String getPreferredShiftDefinition() {
        return preferredShiftDefinition;
    }

    public void setPreferredShiftDefinition(String preferredShiftDefinition) {
        this.preferredShiftDefinition = preferredShiftDefinition;
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

    public String getPreferredAssignmentDuration() {
        return preferredAssignmentDuration;
    }

    public void setPreferredAssignmentDuration(String preferredAssignmentDuration) {
        this.preferredAssignmentDuration = preferredAssignmentDuration;
    }

    public String getPreferredAssignmentDurationDefinition() {
        return preferredAssignmentDurationDefinition;
    }

    public void setPreferredAssignmentDurationDefinition(String preferredAssignmentDurationDefinition) {
        this.preferredAssignmentDurationDefinition = preferredAssignmentDurationDefinition;
    }

    public String getPreferredHourlyPayRate() {
        return preferredHourlyPayRate;
    }

    public void setPreferredHourlyPayRate(String preferredHourlyPayRate) {
        this.preferredHourlyPayRate = preferredHourlyPayRate;
    }

    public String getPreferredDaysOfTheWeek() {
        return preferredDaysOfTheWeek;
    }

    public void setPreferredDaysOfTheWeek(String preferredDaysOfTheWeek) {
        this.preferredDaysOfTheWeek = preferredDaysOfTheWeek;
    }

    public List<String> getPreferredDaysOfTheWeekArray() {
        return preferredDaysOfTheWeekArray;
    }

    public void setPreferredDaysOfTheWeekArray(List<String> preferredDaysOfTheWeekArray) {
        this.preferredDaysOfTheWeekArray = preferredDaysOfTheWeekArray;
    }

    public String getPreferredDaysOfTheWeekString() {
        return preferredDaysOfTheWeekString;
    }

    public void setPreferredDaysOfTheWeekString(String preferredDaysOfTheWeekString) {
        this.preferredDaysOfTheWeekString = preferredDaysOfTheWeekString;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
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

    public String getJobFunction() {
        return jobFunction;
    }

    public void setJobFunction(String jobFunction) {
        this.jobFunction = jobFunction;
    }

    public String getJobFunctionDefinition() {
        return jobFunctionDefinition;
    }

    public void setJobFunctionDefinition(String jobFunctionDefinition) {
        this.jobFunctionDefinition = jobFunctionDefinition;
    }

    public String getPreferredShiftDuration() {
        return preferredShiftDuration;
    }

    public void setPreferredShiftDuration(String preferredShiftDuration) {
        this.preferredShiftDuration = preferredShiftDuration;
    }

    public String getPreferredShiftDurationDefinition() {
        return preferredShiftDurationDefinition;
    }

    public void setPreferredShiftDurationDefinition(String preferredShiftDurationDefinition) {
        this.preferredShiftDurationDefinition = preferredShiftDurationDefinition;
    }

    public String getPreferredWorkLocation() {
        return preferredWorkLocation;
    }

    public void setPreferredWorkLocation(String preferredWorkLocation) {
        this.preferredWorkLocation = preferredWorkLocation;
    }

    public String getPreferredWorkLocationDefinition() {
        return preferredWorkLocationDefinition;
    }

    public void setPreferredWorkLocationDefinition(String preferredWorkLocationDefinition) {
        this.preferredWorkLocationDefinition = preferredWorkLocationDefinition;
    }

    public String getPreferredExperience() {
        return preferredExperience;
    }

    public void setPreferredExperience(String preferredExperience) {
        this.preferredExperience = preferredExperience;
    }

    public String getPreferredExperienceDefinition() {
        return preferredExperienceDefinition;
    }

    public void setPreferredExperienceDefinition(String preferredExperienceDefinition) {
        this.preferredExperienceDefinition = preferredExperienceDefinition;
    }

    public String getJobCernerExp() {
        return jobCernerExp;
    }

    public void setJobCernerExp(String jobCernerExp) {
        this.jobCernerExp = jobCernerExp;
    }

    public String getJobCernerExpDefinition() {
        return jobCernerExpDefinition;
    }

    public void setJobCernerExpDefinition(String jobCernerExpDefinition) {
        this.jobCernerExpDefinition = jobCernerExpDefinition;
    }

    public String getJobMeditechExp() {
        return jobMeditechExp;
    }

    public void setJobMeditechExp(String jobMeditechExp) {
        this.jobMeditechExp = jobMeditechExp;
    }

    public String getJobMeditechExpDefinition() {
        return jobMeditechExpDefinition;
    }

    public void setJobMeditechExpDefinition(String jobMeditechExpDefinition) {
        this.jobMeditechExpDefinition = jobMeditechExpDefinition;
    }

    public String getJobEpicExp() {
        return jobEpicExp;
    }

    public void setJobEpicExp(String jobEpicExp) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getJobVideo() {
        return jobVideo;
    }

    public void setJobVideo(String jobVideo) {
        this.jobVideo = jobVideo;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getApplied() {
        if (TextUtils.isEmpty(applied))
            applied = "";
        return applied;
    }

    public void setApplied(String applied) {
        this.applied = applied;
    }

    @SerializedName("rating_comment")
    @Expose
    private RatingComment ratingComment;

    public RatingComment getRatingComment() {
        return ratingComment;
    }

    public void setRatingComment(RatingComment ratingComment) {
        this.ratingComment = ratingComment;
    }
    public class RatingComment {


        @SerializedName("rating")
        @Expose
        private String rating;
        @SerializedName("experience")
        @Expose
        private String experience;
        @SerializedName("nurse_name")
        @Expose
        private String nurseName;
        @SerializedName("nurse_image")
        @Expose
        private String nurseImage;

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getNurseName() {
            return nurseName;
        }

        public void setNurseName(String nurseName) {
            this.nurseName = nurseName;
        }

        public String getNurseImage() {
            return nurseImage;
        }

        public void setNurseImage(String nurseImage) {
            this.nurseImage = nurseImage;
        }

    }
}
