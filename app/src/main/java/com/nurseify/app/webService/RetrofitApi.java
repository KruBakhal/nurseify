package com.nurseify.app.webService;


import com.nurseify.app.screen.nurse.model.ActiveModel;
import com.nurseify.app.screen.nurse.model.AddCredentialModel;
import com.nurseify.app.screen.nurse.model.CityModel;
import com.nurseify.app.screen.nurse.model.CompletedJobModel;
import com.nurseify.app.screen.nurse.model.CountryModel;
import com.nurseify.app.screen.nurse.model.CredentialModel;
import com.nurseify.app.screen.nurse.model.DegreeModel;
import com.nurseify.app.screen.nurse.model.FacilityJobModel;
import com.nurseify.app.screen.nurse.model.HourlyRate_Common_OptionModel;
import com.nurseify.app.screen.nurse.model.HourlyRate_DayOfWeek_OptionModel;
import com.nurseify.app.screen.nurse.model.JobModel;
import com.nurseify.app.screen.nurse.model.LanguageModel;
import com.nurseify.app.screen.nurse.model.LeaderRolesModel;
import com.nurseify.app.screen.nurse.model.MyJobModel;
import com.nurseify.app.screen.nurse.model.NotificationModel;
import com.nurseify.app.screen.nurse.model.OfferedJobModel;
import com.nurseify.app.screen.nurse.model.PrivacyPolicyModel;
import com.nurseify.app.screen.nurse.model.ResponseModel;
import com.nurseify.app.screen.nurse.model.RoleModel;
import com.nurseify.app.screen.nurse.model.RoleModel2;
import com.nurseify.app.screen.nurse.model.SettingModel;
import com.nurseify.app.screen.nurse.model.SpecialtyModel;
import com.nurseify.app.screen.nurse.model.StateModel;
import com.nurseify.app.screen.nurse.model.UserProfile;
import com.nurseify.app.screen.nurse.model.WorkHistorysModel;
import com.nurseify.app.screen.nurse.model.WorkLocationModel;
import com.nurseify.app.screen.nurse.sample.SampleModel;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitApi {

    @Multipart
    @POST("testing")
    Call<SampleModel> call_test(@Part("id") RequestBody aasd);

    @Multipart
    @POST("login")
    Call<UserProfile> call_login_check_user(
            @Part("email") RequestBody aasd,
            @Part("password") RequestBody sada,
            @Part("fcm_token") RequestBody sada1
    );

    @Multipart
    @POST("register")
    Call<UserProfile> call_Signup(
            @Part("first_name") RequestBody first_name,
            @Part("last_name") RequestBody last_name,
            @Part("email") RequestBody email,
            @Part("mobile") RequestBody mobile,
            @Part("password") RequestBody password,
            @Part("nursing_license_state") RequestBody nursing_license_state,
            @Part("nursing_license_number") RequestBody nursing_license_number,
            @Part("specialty") RequestBody specialty,
            @Part("work_location") RequestBody work_location,
            @Part("fcm_token") RequestBody sada1
    );

    @POST("get-work-location")
    Observable<WorkLocationModel> call_work_location();

    @POST("get-specialities")
    Observable<SpecialtyModel> call_specialty();

    @POST("get-specialities")
    Call<SpecialtyModel> call_specialty_single();

    @POST("state-list")
    Observable<StateModel> call_state();

    @Multipart
    @POST("get-states")
    Call<StateModel> call_state_ID(@Part("country_id") RequestBody id);

    @Multipart
    @POST("get-states")
    Observable<StateModel> call_state_ID_2(@Part("country_id") RequestBody id);

    @Multipart
    @POST("get-cities")
    Call<CityModel> call_city_ID(@Part("state_id") RequestBody id);

    @POST("get-countries")
    Observable<CountryModel> call_Country();

    @Multipart
    @POST("personal-detail")
    Call<UserProfile> call_PersonalDetail(
            @Part("id") RequestBody id,
            @Part("first_name") RequestBody first_name,
            @Part("last_name") RequestBody last_name,
            @Part("email") RequestBody email,
            @Part("mobile") RequestBody mobile,
            @Part("nursing_license_state") RequestBody nursing_license_state,
            @Part("nursing_license_number") RequestBody nursing_license_number,
            @Part("specialty") RequestBody specialty,
            @Part("address") RequestBody address,
            @Part("city") RequestBody city,
            @Part("state") RequestBody state,
            @Part("postcode") RequestBody postcode,
            @Part("country") RequestBody country,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("personal-detail")
    Call<UserProfile> call_PersonalDetail(
            @Part("id") RequestBody id,
            @Part("first_name") RequestBody first_name,
            @Part("last_name") RequestBody last_name,
            @Part("email") RequestBody email,
            @Part("mobile") RequestBody mobile,
            @Part("nursing_license_state") RequestBody nursing_license_state,
            @Part("nursing_license_number") RequestBody nursing_license_number,
            @Part("specialty") RequestBody specialty,
            @Part("address") RequestBody address,
            @Part("city") RequestBody city,
            @Part("state") RequestBody state,
            @Part("postcode") RequestBody postcode,
            @Part("country") RequestBody country
    );

    @POST("shift-duration")
    Observable<HourlyRate_Common_OptionModel> call_shift_duration();

    @POST("assignment-duration")
    Observable<HourlyRate_Common_OptionModel> call_assignment_duration();

    @POST("preferred-shifts")
    Observable<HourlyRate_Common_OptionModel> call_preferred_shifts();

    @POST("get-weekdays")
    Observable<HourlyRate_DayOfWeek_OptionModel> call_get_weekdays();

    @Multipart
    @POST("availability")
    Call<UserProfile> call_hourly_rates_availability(
            @Part("id") RequestBody id,
            @Part("hourly_pay_rate") RequestBody hourly_pay_rate,
            @Part("shift_duration") RequestBody shift_duration,
            @Part("assignment_duration") RequestBody assignment_duration,
            @Part("preferred_shift") RequestBody preferred_shift,
            @Part("days_of_the_week") RequestBody days_of_the_week,
            @Part("work_location") RequestBody work_location,
            @Part("earliest_start_date") RequestBody earliest_start_date
    );

    @POST("nursing-degrees-options")
    Observable<DegreeModel> call_nursing_degrees_options();

    @POST("get-cerner-medtech-epic-options")
    Observable<DegreeModel> call_cerner_medtech_epic_options();

    @Multipart
    @POST("experience")
    Call<UserProfile> call_send_WorkHistory_Experience(
            @Part("highest_nursing_degree") RequestBody highest_nursing_degree,
            @Part("college_uni_name") RequestBody college_uni_name,
            @Part("college_uni_city") RequestBody college_uni_city,
            @Part("college_uni_state") RequestBody college_uni_state,
            @Part("college_uni_country") RequestBody college_uni_country,
            @Part("experience_as_acute_care_facility") RequestBody experience_as_acute_care_facility,
            @Part("experience_as_ambulatory_care_facility") RequestBody experience_as_ambulatory_care_facility,
            @Part("ehr_proficiency_cerner") RequestBody ehr_proficiency_cerner,
            @Part("ehr_proficiency_meditech") RequestBody ehr_proficiency_meditech,
            @Part("ehr_proficiency_epic") RequestBody ehr_proficiency_epic,
            @Part("ehr_proficiency_other") RequestBody ehr_proficiency_other,
            @Part("id") RequestBody id
    );

    @FormUrlEncoded
    @POST("experience")
    Call<WorkHistorysModel> call_Field(
            @Field("highest_nursing_degree") String highest_nursing_degree,
            @Field("college_uni_name") String college_uni_name,
            @Field("college_uni_city") String college_uni_city,
            @Field("college_uni_state") String college_uni_state,
            @Field("college_uni_country") String college_uni_country,
            @Field("experience_as_acute_care_facility") String experience_as_acute_care_facility,
            @Field("experience_as_ambulatory_care_facility") String experience_as_ambulatory_care_facility,
            @Field("ehr_proficiency_cerner") String ehr_proficiency_cerner,
            @Field("ehr_proficiency_meditech") String ehr_proficiency_meditech,
            @Field("ehr_proficiency_epic") String ehr_proficiency_epic,
            @Field("ehr_proficiency_other") String ehr_proficiency_other,
            @Field("id") String id
    );

    /*  @FormUrlEncoded
      @POST("rudra_get_follower_list")
      Call<UserCompleteData> getFan(
              @Field("api_key") String api_key,
              @Field("build_version") String build_version,
              @Field("user_id") String user_id,
              @Field("page_no") String page_no
      );*/
    @Multipart
    @POST("add-credentials")
    Call<AddCredentialModel> call_send_WorkHistory_Certificate_add(
            @Part("id") RequestBody id,
            @Part("type") RequestBody highest_nursing_degree,
            @Part("effective_date") RequestBody college_uni_name,
            @Part("expiration_date") RequestBody college_uni_city,
            @Part MultipartBody.Part certificate_image

    );

    @Multipart
    @POST("edit-credentials")
    Call<AddCredentialModel> call_send_WorkHistory_Certificate(
            @Part("user_id") RequestBody id,
            @Part("certificate_id") RequestBody certificate_id,
            @Part("type") RequestBody highest_nursing_degree,
            @Part("effective_date") RequestBody college_uni_name,
            @Part("expiration_date") RequestBody college_uni_city,
            @Part MultipartBody.Part certificate_image

    );

    @Multipart
    @POST("edit-credentials")
    Call<AddCredentialModel> call_send_WorkHistory_Certificate(
            @Part("user_id") RequestBody id,
            @Part("certificate_id") RequestBody certificate_id,
            @Part("type") RequestBody highest_nursing_degree,
            @Part("effective_date") RequestBody college_uni_name,
            @Part("expiration_date") RequestBody college_uni_city

    );

    @POST("search-for-credentials-list")
    Call<CredentialModel> call_search_for_credentials_list();

    @POST("get-languages-list")
    Observable<LanguageModel> call_language();

    @POST("get-leadership-roles")
    Observable<LeaderRolesModel> call_leadership_roles();

    @Multipart
    @POST("role-and-interest/page-1")
    Call<RoleModel> call_role_interest_1(
            @Part("id") RequestBody id,
            @Part("serving_preceptor") RequestBody serving_preceptor,
            @Part("serving_interim_nurse_leader") RequestBody serving_interim_nurse_leader,
            @Part("leadership_roles") RequestBody leadership_roles,
            @Part("linical_educator") RequestBody linical_educator,
            @Part("is_daisy_award_winner") RequestBody is_daisy_award_winner,
            @Part("employee_of_the_mth_qtr_yr") RequestBody employee_of_the_mth_qtr_yr,
            @Part("other_nursing_awards") RequestBody other_nursing_awards,
            @Part("is_professional_practice_council") RequestBody is_professional_practice_council,
            @Part("is_research_publications") RequestBody is_research_publications,
            @Part("languages") RequestBody languages

    );

    @Multipart
    @POST("role-and-interest/page-2")
    Call<RoleModel2> call_role_interest_2(
            @Part("id") RequestBody id,
            @Part("summary") RequestBody serving_preceptor,
            @Part("nu_video") RequestBody serving_interim_nurse_leader,
            @Part MultipartBody.Part[] leadership_roles,
            @Part MultipartBody.Part[] linical_educator);

    @Multipart
    @POST("browse-jobs")
    Call<JobModel> call_browser_filter_job(@Query("page") String page,
                                           @Part("search_location") RequestBody search_location,
                                           @Part("open_assignment_type") RequestBody open_assignment_type,
                                           @Part("facility_type") RequestBody facility_type,
                                           @Part("electronic_medical_records") RequestBody electronic_medical_records,
                                           @Part("user_id") RequestBody user_id,
                                           @Part("hourly_range") RequestBody range
    );

    @Multipart
    @POST("browse-jobs")
    Call<JobModel> call_browser_job(@Query("page") String page, @Part("user_id") RequestBody user_id);

    @Multipart
    @POST("job-like")
    Call<ResponseModel> call_like_job(@Part("user_id") RequestBody user_id, @Part("job_id") RequestBody job_id,
                                      @Part("like") RequestBody like);

    @Multipart
    @POST("job-applied")
    Call<ResponseModel> call_job_applied(
            @Part("user_id") RequestBody user_id,
            @Part("job_id") RequestBody job_id,
            @Part("type") RequestBody type
    );

    @Multipart
    @POST("browse-facility")
    Call<FacilityJobModel> call_browser_facility(
            @Query("page") String page,
            @Part("user_id") RequestBody user_id
    );


    @Multipart
    @POST("facility-follow")
    Call<ResponseModel> call_follow_facility(
            @Part("user_id") RequestBody user_id,
            @Part("facility_id") RequestBody facility_id,
            @Part("type") RequestBody type
    );

    @Multipart
    @POST("facility-like")
    Call<ResponseModel> call_like_facility(
            @Part("user_id") RequestBody user_id,
            @Part("facility_id") RequestBody facility_id,
            @Part("like") RequestBody like
    );

    @Multipart
    @POST("job-offers")
    Call<OfferedJobModel> call_offered_job(
            @Query("page") String page,
            @Part("user_id") RequestBody user_id
    );

    @Multipart
    @POST("job-accept")
    Call<ResponseModel> call_offered_job_accept(
            @Part("user_id") RequestBody user_id,
            @Part("offer_id") RequestBody job_id
    );

    @Multipart
    @POST("job-reject")
    Call<ResponseModel> call_offered_job_reject(
            @Part("user_id") RequestBody user_id,
            @Part("offer_id") RequestBody job_id
    );

    @Multipart
    @POST("job-active")
    Call<ActiveModel> call_active_job(
            @Query("page") String page,
            @Part("user_id") RequestBody user_id
    );

    @Multipart
    @POST("job-completed")
    Call<CompletedJobModel> call_completed_job(
            @Query("page") String page,
            @Part("user_id") RequestBody user_id
    );

    @Multipart
    @POST("get-notification")
    Call<NotificationModel> call_notification(
            @Part("user_id") RequestBody user_id
    );

    @Multipart
    @POST("settings")
    Call<SettingModel> call_setting(
            @Part("user_id") RequestBody user_id
    );

    @Multipart
    @POST("get-nurse-profile")
    Call<UserProfile> call_nurse_profile(
            @Part("user_id") RequestBody user_id
   );

    @Multipart
    @POST("remove-credentials-image")
    Call<ResponseModel> call_remove_certificate_image(
            @Part("user_id") RequestBody user_id,
            @Part("certificate_id") RequestBody certificate_id
    );

    @Multipart
    @POST("remove-role-interest-doc")
    Call<ResponseModel> call_remove_Asset_image(
            @Part("user_id") RequestBody user_id,
            @Part("asset_id") RequestBody certificate_id
    );

    @POST("facility-types")
    Observable<SpecialtyModel> call_facility_types();

    @POST("get-emedical-records")
    Observable<SpecialtyModel> call_get_medica_records();

    @Multipart
    @POST("update-profile-picture")
    Call<UserProfile> call_Profile_Photos(@Part("user_id") RequestBody user_id,
                                          @Part MultipartBody.Part image);

    @Multipart
    @POST("nurse-resume")
    Call<UserProfile> call_Resume_upload(@Part("user_id") RequestBody user_id,
                                         @Part MultipartBody.Part image);

    @Multipart
    @POST("update-role-interest")
    Call<UserProfile> call_EDIT_role_interest(
            @Part("user_id") RequestBody id,
            @Part("serving_preceptor") RequestBody serving_preceptor,
            @Part("serving_interim_nurse_leader") RequestBody serving_interim_nurse_leader,
            @Part("leadership_roles") RequestBody leadership_roles,
            @Part("linical_educator") RequestBody linical_educator,
            @Part("is_daisy_award_winner") RequestBody is_daisy_award_winner,
            @Part("employee_of_the_mth_qtr_yr") RequestBody employee_of_the_mth_qtr_yr,
            @Part("other_nursing_awards") RequestBody other_nursing_awards,
            @Part("is_professional_practice_council") RequestBody is_professional_practice_council,
            @Part("is_research_publications") RequestBody is_research_publications,
            @Part("languages") RequestBody languages,
            @Part("summary") RequestBody serving_preceptor1,
            @Part("nu_video") RequestBody serving_interim_nurse_leader1,
            @Part MultipartBody.Part[] leadership_roles1,
            @Part MultipartBody.Part[] linical_educator1

    );

    @Multipart
    @POST("update-role-interest")
    Call<UserProfile> call_EDIT_role_interest(
            @Part("user_id") RequestBody id,
            @Part("serving_preceptor") RequestBody serving_preceptor,
            @Part("serving_interim_nurse_leader") RequestBody serving_interim_nurse_leader,
            @Part("leadership_roles") RequestBody leadership_roles,
            @Part("linical_educator") RequestBody linical_educator,
            @Part("is_daisy_award_winner") RequestBody is_daisy_award_winner,
            @Part("employee_of_the_mth_qtr_yr") RequestBody employee_of_the_mth_qtr_yr,
            @Part("other_nursing_awards") RequestBody other_nursing_awards,
            @Part("is_professional_practice_council") RequestBody is_professional_practice_council,
            @Part("is_research_publications") RequestBody is_research_publications,
            @Part("languages") RequestBody languages,
            @Part("summary") RequestBody serving_preceptor1,
            @Part("nu_video") RequestBody serving_interim_nurse_leader1,
            @Part MultipartBody.Part[] leadership_roles1
    );

    @Multipart
    @POST("update-role-interest")
    Call<UserProfile> call_EDIT_role_interest(
            @Part("user_id") RequestBody id,
            @Part("serving_preceptor") RequestBody serving_preceptor,
            @Part("serving_interim_nurse_leader") RequestBody serving_interim_nurse_leader,
            @Part("leadership_roles") RequestBody leadership_roles,
            @Part("linical_educator") RequestBody linical_educator,
            @Part("is_daisy_award_winner") RequestBody is_daisy_award_winner,
            @Part("employee_of_the_mth_qtr_yr") RequestBody employee_of_the_mth_qtr_yr,
            @Part("other_nursing_awards") RequestBody other_nursing_awards,
            @Part("is_professional_practice_council") RequestBody is_professional_practice_council,
            @Part("is_research_publications") RequestBody is_research_publications,
            @Part("languages") RequestBody languages,
            @Part("summary") RequestBody serving_preceptor1,
            @Part("nu_video") RequestBody serving_interim_nurse_leader1
    );

    @POST("privacy-policy")
    Call<PrivacyPolicyModel> call_privacy_policy();

    @POST("terms-conditions")
    Call<PrivacyPolicyModel> call_terms_conditions();

    @POST("about-app")
    Call<PrivacyPolicyModel> call_about_app();

    @Multipart
    @POST("forgot-password")
    Call<ResponseModel> call_forget_password(@Part("email") RequestBody email_id);

    @Multipart
    @POST("view-job-detail")
    Call<MyJobModel> call_view_job_detail(@Part("user_id") RequestBody user_id,
                                          @Part("offer_id") RequestBody offer_id);

    @Multipart
    @POST("facility-rating")
    Call<ResponseModel> call_facility_rating(@Part("user_id") RequestBody user_id,
                                             @Part("facility_id") RequestBody offer_id,
                                             @Part("overall") RequestBody overall,
                                             @Part("on_board") RequestBody on_board,
                                             @Part("nurse_team_work") RequestBody nurse_team_work,
                                             @Part("leadership_support") RequestBody leadership_support,
                                             @Part("tools_todo_my_job") RequestBody tools_todo_my_job,
                                             @Part("experience") RequestBody experience
    );

    @Multipart
    @POST("remove-notification")
    Call<ResponseModel> call_remove_notification(@Part("user_id") RequestBody user_id, @Part("notification_id")
            RequestBody notification_id);
}
