package com.weboconnect.nurseify.screen.facility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.Nurse_CertificationsAdapter;
import com.weboconnect.nurseify.adapter.Nurse_FilesAdapter;
import com.weboconnect.nurseify.adapter.PhotoFilesAdapter;
import com.weboconnect.nurseify.databinding.ActivityNurseDetailsBinding;
import com.weboconnect.nurseify.databinding.DialogNurseAvailabilityBinding;
import com.weboconnect.nurseify.databinding.DialogNurseHistoryBinding;
import com.weboconnect.nurseify.databinding.DialogNurseRoleBinding;
import com.weboconnect.nurseify.intermediate.CertificationCallback;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.screen.facility.model.NurseDatum;
import com.weboconnect.nurseify.screen.facility.model.NurseModel;
import com.weboconnect.nurseify.screen.nurse.RegisterActivity;
import com.weboconnect.nurseify.screen.nurse.WorkHistoryActivity;
import com.weboconnect.nurseify.screen.nurse.adapters.CertificationsAdapter;
import com.weboconnect.nurseify.screen.nurse.model.UserProfile;
import com.weboconnect.nurseify.screen.nurse.model.UserProfileData;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NurseDetailsActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    ActivityNurseDetailsBinding binding;
    private NurseDatum model;
    Context context;
    private boolean isFetching = false;
    private UserProfile nurseProfileModel;
    private boolean isCalled = false;
    private View viewCalled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(NurseDetailsActivity.this, R.layout.activity_nurse_details);
        context = this;
        setData();
        click();
    }

    private void getNurseProfile(String nurseId) {
        if (!Utils.isNetworkAvailable(context)) {
            Utils.displayToast(context, getResources().getString(R.string.no_internet));
            isCalled = false;
            isFetching = false;
            viewCalled = null;
            return;
        }
        isFetching = true;
        if (isCalled)
            showProgress();

        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), nurseId);

        Call<UserProfile> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_nurse_profile(user_id1);

        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {

                if (response == null || response.body() == null) {
                    isFetching = false;
                    errorProgress(true);
                    return;
                }
                if (response.isSuccessful()) {
                    nurseProfileModel = response.body();
                    if (!nurseProfileModel.getApiStatus().equals("1")) {
                        errorProgress(true);
                    } else {
                        dismissProgress();
                        Log.d(TAG, "onResponse: " + nurseProfileModel.getData().toString());
                    }
                } else {
                    errorProgress(true);
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                errorProgress(true);
//                Utils.displayToast(context, "Failed to get updated data !");
                Log.e(TAG + "getNurseProfile", t.toString());
            }
        });

    }

    private void setData() {
        model = new Gson().fromJson(getIntent().getStringExtra(Constant.STR_RESPONSE_DATA), Utils.typeNurse);
        getNurseProfile(model.getUserId());
        Glide.with(context).load(model.getNurseLogo()).placeholder(R.drawable.person)
                .error(R.drawable.person).into(binding.circleImageView);
        binding.tvName.setText(model.getFirstName() + " " + model.getLastName());
        binding.tvDescription.setText(model.getSummary());
        binding.tvRating.setText(model.getRating().getOverAll());
        String rate = model.getHourlyPayRate();
        if (TextUtils.isEmpty(rate))
            rate = "0";
        binding.tvRate.setText("$ " + rate + "/Hr");
        binding.tvLicenceState.setText(model.getNursingLicenseState());
        binding.tvLicenceNo.setText(model.getNursingLicenseNumber());
        binding.tvPreferredGeography.setText("Preferred Geography");
        binding.tvAddress.setText(model.getAddress());
        binding.tvCity.setText(model.getCity());
        binding.tvState.setText(model.getState());
        binding.tvPostCode.setText(model.getPostcode());
        binding.tvCountry.setText(model.getCountry());


    }

    private void click() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Utils.onClickEvent(v);
            }
        });
        binding.availability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nurseProfileModel == null && nurseProfileModel.getData() == null) {
                    viewCalled = v;
                    getNurseProfile(model.getUserId());
                } else {
                    open_availability();
                }

                Utils.onClickEvent(v);
            }
        });
        binding.workHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nurseProfileModel == null && nurseProfileModel.getData() == null) {
                    viewCalled = v;
                    getNurseProfile(model.getUserId());
                } else {
                    open_workhistory();
                }

                Utils.onClickEvent(v);
            }
        });
        binding.roleInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nurseProfileModel == null && nurseProfileModel.getData() == null) {
                    viewCalled = v;
                    getNurseProfile(model.getUserId());
                } else {
                    open_role_insterest();
                }

                Utils.onClickEvent(v);
            }
        });

    }

    private void open_role_insterest() {
        if (nurseProfileModel == null) {
            Utils.displayToast(context, "Nurse profile data is empty !");
            return;
        }
        DialogNurseRoleBinding roleBinding = DialogNurseRoleBinding.inflate(getLayoutInflater(), null, false);
        final Dialog dialog = new Dialog(NurseDetailsActivity.this, R.style.AlertDialog);
        dialog.setContentView(roleBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        roleBinding.tv1.setText("" + nurseProfileModel.getData().getRoleInterest().getServingPreceptorDefinition());
        roleBinding.tv2.setText("" + nurseProfileModel.getData().getRoleInterest().getServingInterimNurseLeaderDefinition());
        roleBinding.tv3.setText("" + nurseProfileModel.getData().getRoleInterest().getLeadershipRolesDefinition());
        roleBinding.tv4.setText("" + nurseProfileModel.getData().getRoleInterest().getClinicalEducatorDefinition());
        roleBinding.tv5.setText("" + nurseProfileModel.getData().getRoleInterest().getIsDaisyAwardWinnerDefinition());
        roleBinding.tv6.setText("" + nurseProfileModel.getData().getRoleInterest().getEmployeeOfTheMthQtrYrDefinition());
        roleBinding.tv7.setText("" + nurseProfileModel.getData().getRoleInterest().getOtherNursingAwardsDefinition());
        roleBinding.tv8.setText("" + nurseProfileModel.getData().getRoleInterest().getIsProfessionalPracticeCouncilDefinition());
        roleBinding.tv9.setText("" + nurseProfileModel.getData().getRoleInterest().getIsResearchPublicationsDefinition());
        if (!TextUtils.isEmpty(nurseProfileModel.getData().getRoleInterest().getServingInterimNurseLeaderDefinition())
                && nurseProfileModel.getData().getRoleInterest().getServingInterimNurseLeaderDefinition().equals("yes")) {
            roleBinding.layHide.setVisibility(View.VISIBLE);
        } else roleBinding.layHide.setVisibility(View.GONE);
        String lang = "";
        for (int i = 0; i < nurseProfileModel.getData().getRoleInterest().getLanguages().size(); i++) {
            if (i == 0) {
                lang = nurseProfileModel.getData().getRoleInterest().getLanguages().get(i);
            } else
                lang = lang + ", " + nurseProfileModel.getData().getRoleInterest().getLanguages().get(i);
        }
        roleBinding.tvLang.setText("" + lang);
        roleBinding.tvIntro.setText("" + nurseProfileModel.getData().getRoleInterest().getSummary());
        roleBinding.tvUrlLink.setText("" + nurseProfileModel.getData().getRoleInterest().getNuVideoEmbedUrl());
        List<UserProfileData.AdditionalPicture> sdsd =
                nurseProfileModel.getData().getRoleInterest().getAdditionalPictures();
        List<String> list_photos = new ArrayList<>();
        List<String> list_Files = new ArrayList<>();

        if (sdsd != null && sdsd.size() != 0) {
            list_photos.clear();
            for (UserProfileData.AdditionalPicture additionalPicture : sdsd) {
                list_photos.add(additionalPicture.getPhoto());
            }
            PhotoFilesAdapter photoFilesAdapter = new PhotoFilesAdapter(list_photos, 11, new ItemCallback() {
                @Override
                public void onClick(int position) {

                }
            });
            roleBinding.rvPhotos.setAdapter(photoFilesAdapter);
        }
        List<UserProfileData.AdditionalFile> sdsd1 =
                nurseProfileModel.getData().getRoleInterest().getAdditionalFiles();
        list_Files.clear();
        if (sdsd1 != null && sdsd1.size() != 0) {
            for (UserProfileData.AdditionalFile additionalPicture : sdsd1) {
                list_Files.add(additionalPicture.getPhoto());
            }
            Nurse_FilesAdapter filesAdapter = new Nurse_FilesAdapter(list_Files, new Nurse_FilesAdapter.PhotoFilesListner() {
                @Override
                public void onCLick_View(int position) {
                    List<UserProfileData.AdditionalFile> list = nurseProfileModel.getData().getRoleInterest()
                            .getAdditionalFiles();
                    if (list != null && list.size() != 0 && position < list.size()) {
                        if (!TextUtils.isEmpty(list.get(position).getPhoto())) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getPhoto()));
                            startActivity(browserIntent);
                        } else {
                            Utils.displayToast(context, "Url is empty");
                        }
                    } else {
                        Utils.displayToast(context, "Something When wrong empty data");
                    }
                }

                @Override
                public void onCLick_Download(int position) {
                    List<UserProfileData.AdditionalFile> list = nurseProfileModel.getData().getRoleInterest()
                            .getAdditionalFiles();
                    if (list != null && list.size() != 0 && position < list.size()) {
                        if (!TextUtils.isEmpty(list.get(position).getPhoto())) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getPhoto()));
                            startActivity(browserIntent);
                        } else {
                            Utils.displayToast(context, "Url is empty");
                        }
                    } else {
                        Utils.displayToast(context, "Something When wrong empty data");
                    }
                }
            });
            roleBinding.rvFiles.setAdapter(filesAdapter);
        }
        roleBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Utils.onClickEvent(v);
            }
        });


        dialog.show();
    }

    private void open_workhistory() {
        if (nurseProfileModel == null || nurseProfileModel.getData().getExperience() == null) {
            Utils.displayToast(context, "Nurse profile data is empty !");
            return;
        }
        DialogNurseHistoryBinding historyBinding = DialogNurseHistoryBinding.inflate(getLayoutInflater(), null, false);
        final Dialog dialog = new Dialog(NurseDetailsActivity.this, R.style.AlertDialog);
        dialog.setContentView(historyBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);

        historyBinding.tvHighestNurseDegree.setText(nurseProfileModel.getData().getExperience().getHighestNursingDegreeDefinition());
        historyBinding.tvCollageName.setText(nurseProfileModel.getData().getExperience().getCollegeUniName());
        historyBinding.tvFacilityExperience.setText(nurseProfileModel.getData().getExperience().getExperienceAsAcuteCareFacility());
        historyBinding.tvNursingExperience.setText(nurseProfileModel.getData().getExperience().getExperienceAsAmbulatoryCareFacility());
        historyBinding.tvCerner.setText(nurseProfileModel.getData().getExperience().getEhrProficiencyCernerDefinition());
        historyBinding.tvMeditech.setText(nurseProfileModel.getData().getExperience().getEhrProficiencyMeditechDefinition());
        historyBinding.tvEpic.setText(nurseProfileModel.getData().getExperience().getEhrProficiencyEpicDefinition());
        historyBinding.tvOther.setText(nurseProfileModel.getData().getExperience().getEhrProficiencyOther());
        historyBinding.recyclerView.setAdapter(new Nurse_CertificationsAdapter(NurseDetailsActivity.this,
                nurseProfileModel.getData().getCertitficate()));
        if (!TextUtils.isEmpty(nurseProfileModel.getData().getResume())) {
            historyBinding.layResume.setVisibility(View.VISIBLE);
        } else {
            historyBinding.layResume.setVisibility(View.GONE);
        }
        historyBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Utils.onClickEvent(v);
            }
        });


        dialog.show();

    }

    private void open_availability() {
        if (nurseProfileModel == null || nurseProfileModel.getData() == null) {
            Utils.displayToast(context, "Nurse profile data is empty !");
            return;
        }
        DialogNurseAvailabilityBinding availabilityBinding = DialogNurseAvailabilityBinding.inflate(getLayoutInflater(), null, false);
        final Dialog dialog = new Dialog(NurseDetailsActivity.this, R.style.AlertDialog);
        dialog.setContentView(availabilityBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        availabilityBinding.tvShiftDuration.setText(nurseProfileModel.getData().getShiftDurationDefinition());
        availabilityBinding.tvAssignmentDuration.setText(nurseProfileModel.getData().getAssignmentDurationDefinition());
        availabilityBinding.tvPreferredShift.setText(nurseProfileModel.getData().getPreferred_shift_definition());
        if (!TextUtils.isEmpty(nurseProfileModel.getData().getEarliestStartDate()))
            availabilityBinding.tvEarliestStartDate.setText(nurseProfileModel.getData().getEarliestStartDate().replace("/", "-"));

        availabilityBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Utils.onClickEvent(v);
            }
        });


        dialog.show();

    }

    private void dismissProgress() {
        binding.layProgress.setVisibility(View.GONE);
        if (isCalled && viewCalled != null) {
            viewCalled.performClick();
        }
        isCalled = false;
        isFetching = false;
        viewCalled = null;
    }

    private void errorProgress(boolean status) {
        binding.layProgress.setVisibility(View.GONE);
        binding.pg.setVisibility(View.GONE);
        if (status)
            binding.tvMsg.setText(getString(R.string.something_when_wrong));
        else
            binding.tvMsg.setText(getString(R.string.no_internet));
        isCalled = false;
        isFetching = false;
        viewCalled = null;
    }

    private void showProgress() {
        if (isCalled) {
            binding.layProgress.setVisibility(View.VISIBLE);
            binding.pg.setVisibility(View.VISIBLE);
            binding.tvMsg.setText("Please Wait");
        }
    }
}