package com.nurseify.app.screen.facility;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nurseify.app.R;
import com.nurseify.app.databinding.ActivityFacilityDetails3Binding;
import com.nurseify.app.screen.facility.model.FacilityProfile;
import com.nurseify.app.screen.facility.model.FacilitySocial;
import com.nurseify.app.screen.nurse.model.FacilityJobModel;
import com.nurseify.app.utils.Constant;
import com.nurseify.app.utils.SessionManager;
import com.nurseify.app.utils.Utils;
import com.nurseify.app.webService.RetrofitClient;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacilityDetails3Activity extends AppCompatActivity {

    ActivityFacilityDetails3Binding binding;
    private Context context;
    private FacilityProfile model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_details3);
        binding = DataBindingUtil.setContentView(FacilityDetails3Activity.this, R.layout.activity_facility_details3);
        context = this;
        model = new SessionManager(context).get_facilityProfile();
        setData();
//        getProfileData();
        click();
    }

    private void getProfileData() {
        if (!Utils.isNetworkAvailable(FacilityDetails3Activity.this)) {
            Utils.displayToast(FacilityDetails3Activity.this, getResources().getString(R.string.no_internet));
            return;
        }
        binding.progressBar.setVisibility(View.VISIBLE);

        String user_id = new SessionManager(getApplicationContext()).get_facilityProfile().getFacilityId();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<FacilityJobModel> call = RetrofitClient.getInstance().getFacilityApi()
                .call_facility_profile(user_id1);

        call.enqueue(new Callback<FacilityJobModel>() {
            @Override
            public void onResponse(Call<FacilityJobModel> call, Response<FacilityJobModel> response) {

                if (response.body() == null) {
                    try {
                        binding.progressBar.setVisibility(View.GONE);
                        Log.d("TAG", "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                if (response.body().getApiStatus().equals("1")) {
                    binding.progressBar.setVisibility(View.GONE);
                    FacilityJobModel.Facility model = response.body().getData().get(0);
                    create_facility_profile(model);
                    String tvEMR;
                    if (!TextUtils.isEmpty(model.getfEmr()) && model.getfEmr().equals("0")) {
                        binding.tvEmr.setText("Other");
                        binding.edEmr.setText("" + model.getfEmrDefinition());
                    } else {
                        binding.layEmr.setVisibility(View.GONE);
                        binding.tvEmr.setText("" + model.getfEmrDefinition());
                    }
                    if (!TextUtils.isEmpty(model.getfBcheckProvider()) && model.getfBcheckProvider().equals("0")) {
                        binding.tvBackground.setText("Other");
                        binding.edBackground.setText("" + model.getfBcheckProviderDefinition());
                    } else {
                        binding.layBackground.setVisibility(View.GONE);
                        binding.tvBackground.setText("" + model.getfBcheckProviderDefinition());
                    }
                    if (!TextUtils.isEmpty(model.getNurseCredSoft()) && model.getNurseCredSoft().equals("0")) {
                        binding.tvSoft.setText("Other");
                        binding.edOtherSoft.setText("" + model.getNurseCredSoftDefinition());
                    } else {
                        binding.layCredential.setVisibility(View.GONE);
                        binding.tvSoft.setText("" + model.getNurseCredSoftDefinition());
                    }
                    if (!TextUtils.isEmpty(model.getNurseSchedulingSys()) && model.getNurseSchedulingSys().equals("0")) {
                        binding.tvScheduling.setText("Other");
                        binding.edScheduling.setText("" + model.getNurseSchedulingSysDefinition());
                    } else {
                        binding.layScheduling.setVisibility(View.GONE);
                        binding.tvScheduling.setText("" + model.getNurseSchedulingSysDefinition());
                    }
                    if (!TextUtils.isEmpty(model.getTimeAttendSys()) && model.getTimeAttendSys().equals("0")) {
                        binding.tvAttendance.setText("Other");
                        binding.edAttendance.setText("" + model.getTimeAttendSysDefinition());
                    } else {
                        binding.layAttendance.setVisibility(View.GONE);
                        binding.tvAttendance.setText("" + model.getTimeAttendSysDefinition());
                    }

                    String edLicenseBed = model.getLicensedBeds();
                    String trauma = model.getTraumaDesignationDefinition();

                    if (TextUtils.isEmpty(edLicenseBed))
                        edLicenseBed = "-";
                    if (TextUtils.isEmpty(trauma))
                        trauma = "-";
                    binding.edLicenseBed.setText(edLicenseBed);
                    binding.tvTrauma.setText(trauma);

                } else {
                    Utils.displayToast(FacilityDetails3Activity.this, "Data has not been updated");

                    binding.progressBar.setVisibility(View.GONE);
                }


                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<FacilityJobModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Utils.displayToast(FacilityDetails3Activity.this, "Failed to get updated data !");
                Log.e("TAG" + "getNurseProfile", t.toString());
            }
        });
    }
    private void create_facility_profile(FacilityJobModel.Facility facility) {
        FacilityProfile facilityProfile = new FacilityProfile();
        facilityProfile.setUserId(facility.getId());
        facilityProfile.setFacilityName(facility.getName());
        facilityProfile.setFacilityType("" + facility.getFacilityType());
        facilityProfile.setFacilityEmail(facility.getFacilityEmail());
        facilityProfile.setFacilityPhone(facility.getFacilityPhone());
        facilityProfile.setFacilityAddress(facility.getAddress());
        facilityProfile.setFacilityCity(facility.getCity());
        facilityProfile.setFacilityState(facility.getState());
        facilityProfile.setFacilityPostcode(facility.getPostcode());

        facilityProfile.setCnoImage(facility.getCnoImage());
        facilityProfile.setFacilityWebsite(facility.getFacilityWebsite());
        facilityProfile.setVideoEmbedUrl(facility.getVideoEmbedUrl());
        facilityProfile.setCnoMessage(facility.getCnoMessage());
        facilityProfile.setAboutFacility(facility.getAboutFacility());

        facilityProfile.setFacilityEmr("" + facility.getfEmr());
        facilityProfile.setFacilityEmrDefinition("" + facility.getfEmrDefinition());
        facilityProfile.setFacilityEmr_Other("" + facility.getfEmrOther());
        facilityProfile.setFacilityBcheckProvider("" + facility.getfBcheckProvider());
        facilityProfile.setFacilityBcheckProviderDefinition("" + facility.getfBcheckProviderDefinition());
        facilityProfile.setFacilityBcheckProvider_Other("" + facility.getfBcheckProviderOther());
        facilityProfile.setNurseCredSoft("" + facility.getNurseCredSoft());
        facilityProfile.setNurseCredSoftDefinition("" + facility.getNurseCredSoftDefinition());
        facilityProfile.setNurseCredSoft_other("" + facility.getNurseCredSoftOther());
        facilityProfile.setNurseSchedulingSys("" + facility.getNurseSchedulingSys());
        facilityProfile.setNurseSchedulingSysDefinition("" + facility.getNurseSchedulingSysDefinition());
        facilityProfile.setNurseSchedulingSys_other("" + facility.getNurseSchedulingSysOther());
        facilityProfile.setTimeAttendSys("" + facility.getTimeAttendSys());
        facilityProfile.setTimeAttendSysDefinition("" + facility.getTimeAttendSysDefinition());
        facilityProfile.setTimeAttendSys_other("" + facility.getTimeAttendSysOther());
        facilityProfile.setLicensedBeds(facility.getLicensedBeds());
        facilityProfile.setTraumaDesignationDefinition(facility.getTraumaDesignationDefinition());
        facilityProfile.setTraumaDesignation(facility.getTraumaDesignation());

        FacilitySocial facilitySocial = new FacilitySocial();

        facilitySocial.setFacebook("" + facility.getFacebook());
        facilitySocial.setTwitter("" + facility.getTwitter());
        facilitySocial.setYoutube("" + facility.getYoutube());
        facilitySocial.setTiktok("" + facility.getTiktok());
        facilitySocial.setSanpchat("" + facility.getSanpchat());
        facilitySocial.setLinkedin("" + facility.getLinkedin());
        facilitySocial.setPinterest("" + facility.getPinterest());
        facilitySocial.setInstagram("" + facility.getInstagram());

        facilityProfile.setFacilitySocial(facilitySocial);

        this.model = facilityProfile;
    }

    private void setData() {

        String tvEMR;
        if (!TextUtils.isEmpty(model.getFacilityEmr()) && model.getFacilityEmr().equals("0")) {
            binding.tvEmr.setText("Other");
            binding.edEmr.setText("" + model.getFacilityEmrDefinition());
        } else {
            binding.layEmr.setVisibility(View.GONE);
            binding.tvEmr.setText("" + model.getFacilityEmrDefinition());
        }
        if (!TextUtils.isEmpty(model.getFacilityBcheckProvider()) && model.getFacilityBcheckProvider().equals("0")) {
            binding.tvBackground.setText("Other");
            binding.edBackground.setText("" + model.getFacilityBcheckProviderDefinition());
        } else {
            binding.layBackground.setVisibility(View.GONE);
            binding.tvBackground.setText("" + model.getFacilityBcheckProviderDefinition());
        }
        if (!TextUtils.isEmpty(model.getNurseCredSoft()) && model.getNurseCredSoft().equals("0")) {
            binding.tvSoft.setText("Other");
            binding.edOtherSoft.setText("" + model.getNurseCredSoftDefinition());
        } else {
            binding.layCredential.setVisibility(View.GONE);
            binding.tvSoft.setText("" + model.getNurseCredSoftDefinition());
        }
        if (!TextUtils.isEmpty(model.getNurseSchedulingSys()) && model.getNurseSchedulingSys().equals("0")) {
            binding.tvScheduling.setText("Other");
            binding.edScheduling.setText("" + model.getNurseSchedulingSysDefinition());
        } else {
            binding.layScheduling.setVisibility(View.GONE);
            binding.tvScheduling.setText("" + model.getNurseSchedulingSysDefinition());
        }
        if (!TextUtils.isEmpty(model.getTimeAttendSys()) && model.getTimeAttendSys().equals("0")) {
            binding.tvAttendance.setText("Other");
            binding.edAttendance.setText("" + model.getTimeAttendSysDefinition());
        } else {
            binding.layAttendance.setVisibility(View.GONE);
            binding.tvAttendance.setText("" + model.getTimeAttendSysDefinition());
        }

        String edLicenseBed = model.getLicensedBeds();
        String trauma = model.getTraumaDesignationDefinition();
        if (TextUtils.isEmpty(edLicenseBed))
            edLicenseBed = "-";
        if (TextUtils.isEmpty(trauma))
            trauma = "-";
        binding.edLicenseBed.setText(edLicenseBed);
        binding.tvTrauma.setText(trauma);


    }

    private void click() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Utils.onClickEvent(v);
            }
        });
        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RegistrationFActivity.class);
                i.putExtra(Constant.EDIT_MODE, true);
                i.putExtra(Constant.SECTION, 3);
//                i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(model));
                startActivityForResult(i, Constant.REQUEST_EDIT);

                Utils.onClickEvent(v);
            }
        });
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacilityDetails3Activity.this, FacilityDetails4Activity.class));
                Utils.onClickEvent(v);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {

                /*String data1 = data.getStringExtra(Constant.STR_RESPONSE_DATA);
                Type type = new TypeToken<FacilityProfile>() {
                }.getType();
                model = new Gson().fromJson(data1, type);
                if (model != null) {
                    setData();
                    setResult(RESULT_OK);
                } else
                    Utils.displayToast(getApplicationContext(), "Empty Data on Result");*/
                setData();
                getProfileData();
                setResult(RESULT_OK);
            } else {


            }
        }
    }
}