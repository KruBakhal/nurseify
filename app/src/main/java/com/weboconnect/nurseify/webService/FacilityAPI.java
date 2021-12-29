package com.weboconnect.nurseify.webService;

import com.weboconnect.nurseify.common.CommonModel;
import com.weboconnect.nurseify.screen.facility.model.DropdownModel;
import com.weboconnect.nurseify.screen.facility.model.FacilityLoginModel;
import com.weboconnect.nurseify.screen.facility.model.FacilityProfile;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_Common_OptionModel;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_DayOfWeek_OptionModel;
import com.weboconnect.nurseify.screen.nurse.model.SpecialtyModel;
import com.weboconnect.nurseify.screen.nurse.model.StateModel;
import com.weboconnect.nurseify.screen.nurse.model.UserProfile;
import com.weboconnect.nurseify.screen.nurse.model.WorkLocationModel;

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


    Call<UserProfile> call_Profile_Photos(@Part("api_key") RequestBody api_key,
                                          @Part("facility_id") RequestBody facility_id,
                                          @Part("facility_logo") MultipartBody.Part facility_logo);

    @POST("get-work-location")
    Observable<CommonModel> call_work_location();

    @POST("get-specialities")
    Observable<CommonModel> call_specialty();

    @POST("shift-duration")
    Observable<CommonModel> call_shift_duration();

    @POST("assignment-duration")
    Observable<CommonModel> call_assignment_duration();



    @POST("get-weekdays")
    Observable<HourlyRate_DayOfWeek_OptionModel> call_get_weekdays();

    @POST("get-seniority-level")
    Observable<CommonModel> call_get_seniority_level();

    @POST("get-job-function")
    Observable<CommonModel> call_get_job_function();
}
