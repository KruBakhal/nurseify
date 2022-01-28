package com.weboconnect.nurseify.webService;

import com.weboconnect.nurseify.common.CommonModel;
import com.weboconnect.nurseify.screen.facility.model.AddJobModel;
import com.weboconnect.nurseify.screen.facility.model.AppliedNurseModel;
import com.weboconnect.nurseify.screen.facility.model.FacilityJobModel;
import com.weboconnect.nurseify.screen.facility.model.FacilityLoginModel;
import com.weboconnect.nurseify.screen.facility.model.FacilitySettingModel;
import com.weboconnect.nurseify.screen.facility.model.NurseModel;
import com.weboconnect.nurseify.screen.facility.model.OfferedJobNurseModel;
import com.weboconnect.nurseify.screen.facility.model.OfferedNurse_F_Model;
import com.weboconnect.nurseify.screen.facility.model.Offered_Job_F_Model;
import com.weboconnect.nurseify.screen.nurse.model.CityModel;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_Common_OptionModel;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_DayOfWeek_OptionModel;
import com.weboconnect.nurseify.screen.nurse.model.NotificationModel;
import com.weboconnect.nurseify.screen.nurse.model.ResponseModel;
import com.weboconnect.nurseify.screen.nurse.model.SpecialtyModel;
import com.weboconnect.nurseify.screen.nurse.model.StateModel;
import com.weboconnect.nurseify.screen.nurse.model.UserProfile;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FacilityAPI {


    @Multipart
    @POST("login")
    Call<FacilityLoginModel> call_login_check_user(
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("fcm_token") RequestBody fcm_token
    );

    @Multipart
    @POST("facility-profile")
    Call<FacilityLoginModel> call_facility_profile(
            @Part("user_id") RequestBody user_id,
            @Part("facility_id") RequestBody facility_id,
            @Part("name") RequestBody name,
            @Part("type") RequestBody type,
            @Part("facility_email") RequestBody facility_email,
            @Part("facility_phone") RequestBody facility_phone,
            @Part("address") RequestBody address,
            @Part("city") RequestBody city,
            @Part("state") RequestBody state,
            @Part("postcode") RequestBody postcode,
            @Part("video_link") RequestBody video_link,
            @Part("facebook") RequestBody facebook,
            @Part("twitter") RequestBody twitter,
            @Part("linkedin") RequestBody linkedin,
            @Part("instagram") RequestBody instagram,
            @Part("pinterest") RequestBody pinterest,
            @Part("tiktok") RequestBody tiktok,
            @Part("sanpchat") RequestBody sanpchat,
            @Part("youtube") RequestBody youtube,
            @Part("facility_website") RequestBody facility_website,
            @Part("f_emr") RequestBody f_emr,
            @Part("f_emr_other") RequestBody f_emr_other,
            @Part("f_bcheck_provider") RequestBody f_bcheck_provider,
            @Part("f_bcheck_provider_other") RequestBody f_bcheck_provider_other,
            @Part("nurse_cred_soft") RequestBody nurse_cred_soft,
            @Part("nurse_cred_soft_other") RequestBody nurse_cred_soft_other,
            @Part("nurse_scheduling_sys") RequestBody nurse_scheduling_sys,
            @Part("nurse_scheduling_sys_other") RequestBody nurse_scheduling_sys_other,
            @Part("time_attend_sys") RequestBody time_attend_sys,
            @Part("time_attend_sys_other") RequestBody time_attend_sys_other,
            @Part("licensed_beds") RequestBody licensed_beds,
            @Part("trauma") RequestBody trauma,
            @Part("senior_leader_message") RequestBody senior_leader_message,
            @Part("about_facility") RequestBody about_facility,
            @Part MultipartBody.Part facility_logo,
            @Part MultipartBody.Part cno_image
    );


    @POST("facility-dropdown-gettimeattendancesystem")
    Observable<CommonModel> call_dropdown_get_time_attendance_system();

    @POST("facility-dropdown-gettraumadesignation")
    Observable<CommonModel> call_dropdown_get_traumadesignation();

    @POST("facility-dropdown-getnschedulingsystem")
    Observable<CommonModel> call_dropdown_getnschedulingsystem();

    @POST("facility-types")
    Call<SpecialtyModel> call_facility_types();

    @POST("facility-types")
    Observable<CommonModel> call_facility_types_c();

    @POST("facility-dropdown-getmedicalrecords")
    Observable<CommonModel> call_facility_getmedicalrecords();

    @POST("facility-dropdown-getbcheckprovider")
    Observable<CommonModel> call_facility_getbcheckprovider();

    @POST("facility-dropdown-getncredentialingsoftware")
    Observable<CommonModel> call_facility_getncredentialingsoftware();

    @POST("get-work-location")
    Observable<CommonModel> call_work_location();

    @POST("get-specialities")
    Observable<CommonModel> call_specialty();

    @POST("shift-duration")
    Observable<CommonModel> call_shift_duration();

    @POST("assignment-duration")
    Observable<CommonModel> call_assignment_duration();

    @POST("preferred-shifts")
    Observable<CommonModel> call_preferred_shifts();

    @POST("get-weekdays")
    Observable<HourlyRate_DayOfWeek_OptionModel> call_get_weekdays();

    @POST("get-seniority-level")
    Observable<CommonModel> call_get_seniority_level();

    @POST("get-job-function")
    Observable<CommonModel> call_get_job_function();

    @POST("get-cerner-medtech-epic-options")
    Observable<CommonModel> call_cerner_medtech_epic_options();


    @Multipart
    @POST("job-create")
    Call<AddJobModel> call_create_add_job(
            @Part("user_id") RequestBody user_id,
            @Part("facility_id") RequestBody facility_id,
            @Part("preferred_assignment_duration") RequestBody facility_id1,
            @Part("seniority_level") RequestBody name,
            @Part("job_function") RequestBody type,
            @Part("preferred_specialty") RequestBody facility_email,
            @Part("preferred_shift_duration") RequestBody facility_phone,
            @Part("preferred_work_location") RequestBody address,
            @Part("preferred_days_of_the_week") RequestBody city,
            @Part("preferred_experience") RequestBody state,
            @Part("preferred_hourly_pay_rate") RequestBody postcode,
            @Part("job_cerner_exp") RequestBody video_link,
            @Part("job_meditech_exp") RequestBody facebook,
            @Part("job_epic_exp") RequestBody twitter,
            @Part("job_other_exp") RequestBody linkedin,
            @Part("description") RequestBody instagram,
            @Part("responsibilities") RequestBody pinterest,
            @Part("qualifications") RequestBody tiktok,
            @Part("job_video") RequestBody sanpchat,
            @Part("active") RequestBody active,
            @Part MultipartBody.Part[] job_photos,
            @Part("start_date") RequestBody start_date,
            @Part("end_date") RequestBody end_date,
            @Part("preferred_shift") RequestBody preferred_shift

    );
    @Multipart
    @POST("change-facility-logo")
    Call<UserProfile> call_Profile_Photos(@Part("api_key") RequestBody api_key,
                                          @Part("facility_id") RequestBody facility_id,
                                          @Part MultipartBody.Part facility_logo);

    @Multipart
    @POST("job-create")
    Call<AddJobModel> call_create_add_job(
            @Part("user_id") RequestBody user_id,
            @Part("facility_id") RequestBody facility_id1,
            @Part("preferred_assignment_duration") RequestBody facility_id,
            @Part("seniority_level") RequestBody name,
            @Part("job_function") RequestBody type,
            @Part("preferred_specialty") RequestBody facility_email,
            @Part("preferred_shift_duration") RequestBody facility_phone,
            @Part("preferred_work_location") RequestBody address,
            @Part("preferred_days_of_the_week") RequestBody city,
            @Part("preferred_experience") RequestBody state,
            @Part("preferred_hourly_pay_rate") RequestBody postcode,
            @Part("job_cerner_exp") RequestBody video_link,
            @Part("job_meditech_exp") RequestBody facebook,
            @Part("job_epic_exp") RequestBody twitter,
            @Part("job_other_exp") RequestBody linkedin,
            @Part("description") RequestBody instagram,
            @Part("responsibilities") RequestBody pinterest,
            @Part("qualifications") RequestBody tiktok,
            @Part("job_video") RequestBody sanpchat,
            @Part("active") RequestBody active,
            @Part("start_date") RequestBody start_date,
            @Part("end_date") RequestBody end_date,
            @Part("preferred_shift") RequestBody preferred_shift
    );

    @Multipart
    @POST("browse-nurses")
    Call<NurseModel> call_browse_nurse(@Part("page") RequestBody current_page1);

    @Multipart
    @POST("get-states")
    Observable<StateModel> call_state_ID_2(@Part("country_id") RequestBody id);

    @Multipart
    @POST("get-cities")
    Observable<CityModel> call_city_ID(@Part("state_id") RequestBody id);

    @POST("search-for-credentials-list")
    Observable<CommonModel> call_search_for_credentials_list();

    @Multipart
    @POST("browse-nurses")
    Call<NurseModel> call_apply_filter(
            @Part("specialty") RequestBody user_id,
            @Part("availability") RequestBody facility_id1,
            @Part("search_keyword") RequestBody facility_id,
            @Part("search_bill_rate_from") RequestBody name,
            @Part("search_bill_rate_to") RequestBody type,
            @Part("search_tenure_from") RequestBody search_tenure_from,
            @Part("search_tenure_to") RequestBody search_tenure_to,
            @Part("certification") RequestBody certification,
            @Part("page") RequestBody page,
            @Part("state") RequestBody state,
            @Part("city") RequestBody city,
            @Part("zipcode") RequestBody zipcode
    );

    @Multipart
    @POST("get-nurse-profile")
    Call<UserProfile> call_nurse_profile(
            @Part("user_id") RequestBody user_id
    );

    @Multipart
    @POST("job-offered-list")
    Call<OfferedNurse_F_Model> call_job_offered_list(@Part("user_id") RequestBody user_id,
                                                     @Part("page") RequestBody current_page1);

    @Multipart
    @POST("job-offered-active")
    Call<OfferedNurse_F_Model> call_job_active_list(@Part("user_id") RequestBody user_id,
                                                    @Part("page") RequestBody current_page1);

    @Multipart
    @POST("job-offered-completed")
    Call<OfferedNurse_F_Model> call_job_past_list(@Part("user_id") RequestBody user_id,
                                                  @Part("page") RequestBody current_page1);

    @Multipart
    @POST("my-jobs-posted")
    Call<FacilityJobModel> call_my_jobs_posted(@Part("user_id") RequestBody user_id,
                                               @Part("page") RequestBody current_page1);

    @Multipart
    @POST("offer-job-to-nurse-dropdown")
    Call<OfferedJobNurseModel> call_offer_job_to_nurse_dropdown(@Part("user_id") RequestBody user_id,// nurse id
                                                                @Part("facility_id") RequestBody current_page1);

    @Multipart
    @POST("my-jobs-active")
    Call<FacilityJobModel> call_my_jobs_active(@Part("user_id") RequestBody user_id,
                                               @Part("page") RequestBody current_page1);

    @Multipart
    @POST("my-jobs-completed")
    Call<FacilityJobModel> call_my_jobs_completed(@Part("user_id") RequestBody user_id,
                                                  @Part("page") RequestBody current_page1);

    @Multipart
    @POST("send-offer")
    Call<Offered_Job_F_Model> call_send_offer(@Part("nurse_id") RequestBody user_id,
                                              @Part("facility_id") RequestBody current_page1,
                                              @Part("job_id") RequestBody job_id);

    @Multipart
    @POST("nurses-applied-jobs")
    Call<AppliedNurseModel> call_applied_nurse(@Part("job_id") RequestBody user_id1);

    @Multipart
    @POST("remove-job-asset")
    Call<ResponseModel> call_remove_Asset_image(
            @Part("job_id") RequestBody user_id,
            @Part("asset_id") RequestBody certificate_id
    );

    @Multipart
    @POST("job-update")
    Call<AddJobModel> call_update_add_job(
            @Part("user_id") RequestBody user_id,
            @Part("facility_id") RequestBody facility_id,
            @Part("preferred_assignment_duration") RequestBody facility_id1,
            @Part("seniority_level") RequestBody name,
            @Part("job_function") RequestBody type,
            @Part("preferred_specialty") RequestBody facility_email,
            @Part("preferred_shift_duration") RequestBody facility_phone,
            @Part("preferred_work_location") RequestBody address,
            @Part("preferred_days_of_the_week") RequestBody city,
            @Part("preferred_experience") RequestBody state,
            @Part("preferred_hourly_pay_rate") RequestBody postcode,
            @Part("job_cerner_exp") RequestBody video_link,
            @Part("job_meditech_exp") RequestBody facebook,
            @Part("job_epic_exp") RequestBody twitter,
            @Part("job_other_exp") RequestBody linkedin,
            @Part("description") RequestBody instagram,
            @Part("responsibilities") RequestBody pinterest,
            @Part("qualifications") RequestBody tiktok,
            @Part("job_video") RequestBody sanpchat,
            @Part("active") RequestBody active,
            @Part MultipartBody.Part[] job_photos,
            @Part("start_date") RequestBody start_date,
            @Part("end_date") RequestBody end_date,
            @Part("preferred_shift") RequestBody preferred_shift,
            @Part("job_id") RequestBody job_id
    );

    @Multipart
    @POST("job-update")
    Call<AddJobModel> call_update_add_job(
            @Part("user_id") RequestBody user_id,
            @Part("facility_id") RequestBody facility_id1,
            @Part("preferred_assignment_duration") RequestBody facility_id,
            @Part("seniority_level") RequestBody name,
            @Part("job_function") RequestBody type,
            @Part("preferred_specialty") RequestBody facility_email,
            @Part("preferred_shift_duration") RequestBody facility_phone,
            @Part("preferred_work_location") RequestBody address,
            @Part("preferred_days_of_the_week") RequestBody city,
            @Part("preferred_experience") RequestBody state,
            @Part("preferred_hourly_pay_rate") RequestBody postcode,
            @Part("job_cerner_exp") RequestBody video_link,
            @Part("job_meditech_exp") RequestBody facebook,
            @Part("job_epic_exp") RequestBody twitter,
            @Part("job_other_exp") RequestBody linkedin,
            @Part("description") RequestBody instagram,
            @Part("responsibilities") RequestBody pinterest,
            @Part("qualifications") RequestBody tiktok,
            @Part("job_video") RequestBody sanpchat,
            @Part("active") RequestBody active,
            @Part("start_date") RequestBody start_date,
            @Part("end_date") RequestBody end_date,
            @Part("preferred_shift") RequestBody preferred_shift, @Part("job_id") RequestBody job_id

    );

    @Multipart
    @POST("browse-facility")
    Call<com.weboconnect.nurseify.screen.nurse.model.FacilityJobModel> call_facility_profile(@Part("facility_id") RequestBody facility_id);

    @Multipart
    @POST("facility-notifications")
    Call<NotificationModel> call_notification(@Part("user_id") RequestBody user_id1);

    @Multipart
    @POST("facility-settings")
    Call<FacilitySettingModel> call_setting(@Part("facility_id") RequestBody user_id1);

    @Multipart
    @POST("remove-notification")
    Call<ResponseModel> call_remove_notification(@Part("user_id") RequestBody user_id, @Part("notification_id")
            RequestBody notification_id);

    @Multipart
    @POST("nurse-rating")
    Call<ResponseModel> call_nurse_rating(@Part("user_id") RequestBody user_id,
                                          @Part("nurse_id") RequestBody offer_id,
                                          @Part("job_id") RequestBody job_id,
                                          @Part("overall") RequestBody overall,
                                          @Part("clinical_skills") RequestBody on_board,
                                          @Part("nurse_teamwork") RequestBody nurse_team_work,
                                          @Part("interpersonal_skills") RequestBody leadership_support,
                                          @Part("work_ethic") RequestBody tools_todo_my_job,
                                          @Part("experience") RequestBody experience
    );}
