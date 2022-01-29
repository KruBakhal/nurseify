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
import com.nurseify.app.databinding.ActivityFacilityDetails4Binding;
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

public class FacilityDetails4Activity extends AppCompatActivity {

    ActivityFacilityDetails4Binding binding;
    private Context context;
    private FacilityProfile model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(FacilityDetails4Activity.this, R.layout.activity_facility_details4);
        context = this;
        model = new SessionManager(context).get_facilityProfile();
        setData();
//        getProfileData();
        click();
    }

    private void getProfileData() {
        if (!Utils.isNetworkAvailable(FacilityDetails4Activity.this)) {
            Utils.displayToast(FacilityDetails4Activity.this, getResources().getString(R.string.no_internet));
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
                    if (model == null)
                        return;
                    if (TextUtils.isEmpty(model.getFacebook())) {
                        binding.edFb.setText("-");
                    } else {
                        binding.edFb.setText("" + model.getFacebook());
                    }
                    if (TextUtils.isEmpty(model.getTwitter())) {
                        binding.edTweet.setText("-");
                    } else {
                        binding.edTweet.setText("" + model.getTwitter());
                    }
                    if (TextUtils.isEmpty(model.getYoutube())) {
                        binding.edYoutube.setText("-");
                    } else {
                        binding.edYoutube.setText("" + model.getYoutube());
                    }
                    if (TextUtils.isEmpty(model.getTiktok())) {
                        binding.edTiktok.setText("-");
                    } else {
                        binding.edTiktok.setText("" + model.getTiktok());
                    }
                    if (TextUtils.isEmpty(model.getSanpchat())) {
                        binding.edSnap.setText("-");
                    } else {
                        binding.edSnap.setText("" + model.getSanpchat());
                    }
                    if (TextUtils.isEmpty(model.getLinkedin())) {
                        binding.edLinkin.setText("-");
                    } else {
                        binding.edLinkin.setText("" + model.getLinkedin());
                    }
                    if (TextUtils.isEmpty(model.getPinterest())) {
                        binding.edPinterest.setText("-");
                    } else {
                        binding.edPinterest.setText("" + model.getPinterest());
                    }
                    if (TextUtils.isEmpty(model.getInstagram())) {
                        binding.edInsta.setText("-");
                    } else {
                        binding.edInsta.setText("" + model.getInstagram());
                    }

                } else {
                    Utils.displayToast(FacilityDetails4Activity.this, "Data has not been updated");

                    binding.progressBar.setVisibility(View.GONE);
                }


                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<FacilityJobModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Utils.displayToast(FacilityDetails4Activity.this, "Failed to get updated data !");
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
        if (model == null || model.getFacilitySocial() == null)
            return;
        if (TextUtils.isEmpty(model.getFacilitySocial().getFacebook())) {
            binding.edFb.setText("-");
        } else {
            binding.edFb.setText("" + model.getFacilitySocial().getFacebook());
        }
        if (TextUtils.isEmpty(model.getFacilitySocial().getTwitter())) {
            binding.edTweet.setText("-");
        } else {
            binding.edTweet.setText("" + model.getFacilitySocial().getTwitter());
        }
        if (TextUtils.isEmpty(model.getFacilitySocial().getYoutube())) {
            binding.edYoutube.setText("-");
        } else {
            binding.edYoutube.setText("" + model.getFacilitySocial().getYoutube());
        }
        if (TextUtils.isEmpty(model.getFacilitySocial().getTiktok())) {
            binding.edTiktok.setText("-");
        } else {
            binding.edTiktok.setText("" + model.getFacilitySocial().getTiktok());
        }
        if (TextUtils.isEmpty(model.getFacilitySocial().getSanpchat())) {
            binding.edSnap.setText("-");
        } else {
            binding.edSnap.setText("" + model.getFacilitySocial().getSanpchat());
        }
        if (TextUtils.isEmpty(model.getFacilitySocial().getLinkedin())) {
            binding.edLinkin.setText("-");
        } else {
            binding.edLinkin.setText("" + model.getFacilitySocial().getLinkedin());
        }
        if (TextUtils.isEmpty(model.getFacilitySocial().getPinterest())) {
            binding.edPinterest.setText("-");
        } else {
            binding.edPinterest.setText("" + model.getFacilitySocial().getPinterest());
        }
        if (TextUtils.isEmpty(model.getFacilitySocial().getInstagram())) {
            binding.edInsta.setText("-");
        } else {
            binding.edInsta.setText("" + model.getFacilitySocial().getInstagram());
        }

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
                i.putExtra(Constant.SECTION, 4);
                i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(model));
                startActivityForResult(i, Constant.REQUEST_EDIT);

                Utils.onClickEvent(v);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {

                String data1 = data.getStringExtra(Constant.STR_RESPONSE_DATA);
                Type type = new TypeToken<FacilityProfile>() {
                }.getType();
                model = new Gson().fromJson(data1, type);
                if (model != null) {
                    setData();
                    setResult(RESULT_OK);
                } else
                    Utils.displayToast(getApplicationContext(), "Empty Data on Result");
            } else {


            }
        }
    }
}