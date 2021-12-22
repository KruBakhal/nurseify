package com.weboconnect.nurseify.webService;

import com.weboconnect.nurseify.screen.facility.model.DropdownModel;
import com.weboconnect.nurseify.screen.facility.model.FacilityLoginModel;
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
    Call<UserProfile> call_facility_profile(
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
            @Part MultipartBody.Part facility_logo,
            @Part MultipartBody.Part cno_image
    );

    @POST("facility-dropdown-getmedicalrecords")
    Observable<DropdownModel> call_dropdown_get_medical_records();

    @POST("facility-dropdown-getbcheckprovider")
    Observable<DropdownModel> call_dropdown_get_bcheck_provider();

    @POST("facility-dropdown-getncredentialingsoftware")
    Observable<DropdownModel> call_dropdown_get_ncredentialingsoftware();

    @POST("facility-dropdown-getnschedulingsystem")
    Observable<DropdownModel> call_dropdown_get_nscheduling_system();

    @POST("facility-dropdown-gettimeattendancesystem")
    Observable<DropdownModel> call_dropdown_get_time_attendance_system();

    @POST("facility-dropdown-gettraumadesignation")
    Observable<DropdownModel> call_dropdown_get_traumadesignation();


}
