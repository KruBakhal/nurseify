package com.weboconnect.nurseify.screen.facility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.JobsAdapter;
import com.weboconnect.nurseify.adapter.Nurse_CertificationsAdapter;
import com.weboconnect.nurseify.adapter.Nurse_FilesAdapter;
import com.weboconnect.nurseify.adapter.PhotoFilesAdapter;
import com.weboconnect.nurseify.databinding.ActivityNurseDetailsBinding;
import com.weboconnect.nurseify.databinding.DialogInviteNurseBinding;
import com.weboconnect.nurseify.databinding.DialogNurseAvailabilityBinding;
import com.weboconnect.nurseify.databinding.DialogNurseHistoryBinding;
import com.weboconnect.nurseify.databinding.DialogNurseRoleBinding;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.screen.facility.model.FacilityJobModel;
import com.weboconnect.nurseify.screen.facility.model.Facility_JobDatum;
import com.weboconnect.nurseify.screen.facility.model.NurseDatum;
import com.weboconnect.nurseify.screen.facility.model.Offered_Job_F_Model;
import com.weboconnect.nurseify.screen.nurse.model.UserProfile;
import com.weboconnect.nurseify.screen.nurse.model.UserProfileData;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitClient;

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
    private ProgressDialog progressDialog;
    public int selected_job = 0;
    List<Facility_JobDatum> listPostedJobs = new ArrayList<>();

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
        binding.layoutApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                check_open_inviteBox();
                Utils.onClickEvent(v);
            }
        });
        binding.layMesg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
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

    private void check_open_inviteBox() {
        if (!Utils.isNetworkAvailable(context)) {
            Utils.displayToast(context, getResources().getString(R.string.no_internet));
            return;
        }
        Utils.displayToast(context, null); // to cancel toast if showing on screen
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String user_id = new SessionManager(context).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody user_id2 = RequestBody.create(MediaType.parse("multipart/form-data"), "1");

        Call<FacilityJobModel> call = RetrofitClient.getInstance().getFacilityApi()
                .call_my_jobs_posted(user_id1, user_id2);

        call.enqueue(new Callback<FacilityJobModel>() {
            @Override
            public void onResponse(Call<FacilityJobModel> call, Response<FacilityJobModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        Utils.displayToast(context, response.body().getMessage());
                        progressDialog.dismiss();
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        FacilityJobModel jobModel = response.body();
                        Utils.displayToast(context, jobModel.getMessage());
                        open_Invite_dialog(jobModel.getData());

                    } else {
                        Utils.displayToast(context, "Yet, no jobs created !");
                        progressDialog.dismiss();
                    }

                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<FacilityJobModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("TAG", "onFailure: " + t.getMessage());
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

    private void open_Invite_dialog(FacilityJobModel.FacilityJob_SubData data) {
        listPostedJobs.clear();
        listPostedJobs.addAll(data.getData());
        DialogInviteNurseBinding inviteNurseBinding = DialogInviteNurseBinding.inflate(getLayoutInflater(), null, false);
        final Dialog dialog = new Dialog(NurseDetailsActivity.this, R.style.AlertDialog);
        dialog.setContentView(inviteNurseBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        inviteNurseBinding.tvTitle.setText("Make an offer to " + model.getFirstName() + " " + model.getLastName());
        inviteNurseBinding.tv1.setText(new SessionManager(context).get_facility().getFacilityName());
        inviteNurseBinding.layJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionPopup(context, inviteNurseBinding.view2, 2, inviteNurseBinding.img2
                        , inviteNurseBinding.tv2, listPostedJobs, selected_job, new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                selected_job = position;
                                inviteNurseBinding.tv2
                                        .setText("" + listPostedJobs.get(position).getFacilityFirstName() + " "
                                                + listPostedJobs.get(position).getFacilityLastName()
                                                + " - " + listPostedJobs.get(position).getPreferredSpecialtyDefinition());
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        inviteNurseBinding.layDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_job == 0) {
                    Utils.displayToast(context, "First Select Job/Assignment !");
                    return;
                }
                make_offer(dialog, listPostedJobs.get(selected_job), inviteNurseBinding);

                Utils.onClickEvent(v);
            }
        });
        inviteNurseBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Utils.onClickEvent(v);
            }
        });


        dialog.show();

    }

    private void make_offer(Dialog dialog, Facility_JobDatum facility_jobDatum, DialogInviteNurseBinding inviteNurseBinding) {
        if (!Utils.isNetworkAvailable(context)) {
            Utils.displayToast(context, getResources().getString(R.string.no_internet));
            return;
        }
//        showProgress();
        inviteNurseBinding.layProgress.setVisibility(View.VISIBLE);
        inviteNurseBinding.progressBar.setVisibility(View.VISIBLE);
        inviteNurseBinding.tvMsg.setText("Please Wait");
        String user_id = new SessionManager(context).get_facility_Id();
        RequestBody api_key = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody mechId1 = RequestBody.create(MediaType.parse("multipart/form-data"), model.getNurseId());
        RequestBody quoted_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), "" + facility_jobDatum.getJob_id());


        Call<Offered_Job_F_Model> call = RetrofitClient.getInstance().getFacilityApi()
                .call_send_offer(api_key, mechId1, quoted_id1);

        call.enqueue(new Callback<Offered_Job_F_Model>() {
            @Override
            public void onResponse(Call<Offered_Job_F_Model> call, Response<Offered_Job_F_Model> response) {
                if (response == null || response.body() == null) {
                    inviteNurseBinding.layProgress.setVisibility(View.GONE);
                    inviteNurseBinding.progressBar.setVisibility(View.GONE);
                    inviteNurseBinding.tvMsg.setText("Done");
                    Utils.displayToast(context, "" + response.message());
                    return;
                }
                if (!response.body().getData().equals("1")) {
                    inviteNurseBinding.layProgress.setVisibility(View.GONE);
                    inviteNurseBinding.progressBar.setVisibility(View.GONE);
                    inviteNurseBinding.tvMsg.setText("Done");
                    Utils.displayToast(context, "" + response.body().getMessage());
                    return;
                }
                if (response.isSuccessful()) {
                    binding.tvMsg.setVisibility(View.GONE);
                    dismissProgress();
                    Log.d("TAG", "onResponse: " + response.body().getData().toString());

                } else {
                    inviteNurseBinding.layProgress.setVisibility(View.GONE);
                    inviteNurseBinding.progressBar.setVisibility(View.GONE);
                    inviteNurseBinding.tvMsg.setText("Done");
                    Utils.displayToast(context, getString(R.string.something_when_wrong));
                    dialog.dismiss();
                }

                dismissProgress();
            }

            @Override
            public void onFailure(Call<Offered_Job_F_Model> call, Throwable t) {
                inviteNurseBinding.layProgress.setVisibility(View.GONE);
                inviteNurseBinding.progressBar.setVisibility(View.GONE);
                inviteNurseBinding.tvMsg.setText("Done");
                Utils.displayToast(context, getString(R.string.something_when_wrong));
            }
        });
    }

    private void showOptionPopup(Context context, View v, int type, ImageView img1,
                                 TextView tvState, List<Facility_JobDatum> cityData, int selected_City, ItemCallback
                                         itemCallback) {
        if (cityData == null || cityData.size() == 0) {
            Utils.displayToast(context, "data empty");
            return;
        }
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_dropdown, null);

        PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int height = 193;
        popup.setWidth(v.getWidth());
        popup.setHeight(getWindowManager().getDefaultDisplay().getHeight() * height / 1080);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.rv_pop);
        img1.setRotation(-180);
        View finalImg = img1;
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finalImg.setRotation(0);
            }
        });

        JobsAdapter parentChildAdapter = new JobsAdapter(NurseDetailsActivity.this, type
                , selected_City, cityData, new JobsAdapter.JobsAdapterInterface() {
            @Override
            public void onCLickItem(int i, int position) {
                itemCallback.onClick(position);
                popup.dismiss();
            }
        });
        recyclerView.setAdapter(parentChildAdapter);
        recyclerView.scrollToPosition(selected_City);
        popup.showAsDropDown(v, 0, 0);
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