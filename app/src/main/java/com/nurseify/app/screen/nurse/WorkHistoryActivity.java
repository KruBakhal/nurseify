package com.nurseify.app.screen.nurse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.gson.Gson;
import com.nurseify.app.R;
import com.nurseify.app.databinding.ActivityWorkHistoryBinding;
import com.nurseify.app.intermediate.CertificationCallback;
import com.nurseify.app.screen.nurse.adapters.CertificationsAdapter;
import com.nurseify.app.screen.nurse.model.ResponseModel;
import com.nurseify.app.screen.nurse.model.UserProfile;
import com.nurseify.app.screen.nurse.model.UserProfileData;
import com.nurseify.app.utils.Constant;
import com.nurseify.app.utils.SessionManager;
import com.nurseify.app.utils.Utils;
import com.nurseify.app.webService.RetrofitClient;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkHistoryActivity extends AppCompatActivity {

    ActivityWorkHistoryBinding binding;

    String user_id;
    String TAG = "WorkHistoryActivity ";


    UserProfile nurseProfileModel;
    private int SCROLL_TO = 0;
    private Context context = WorkHistoryActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(WorkHistoryActivity.this, R.layout.activity_work_history);

        binding.progressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        getNurseProfile();

        click();

    }

    private void click() {
        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WorkHistoryActivity.this, RegisterActivity.class);
                i.putExtra(Constant.EDIT_MODE, true);
                i.putExtra(Constant.SECTION, Constant.Work_History_Experience);
//                i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(nurseProfileModel.getData()));
                startActivityForResult(i, Constant.REQUEST_EDIT);
            }
        });
        binding.edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WorkHistoryActivity.this, RegisterActivity.class);
                i.putExtra(Constant.EDIT_MODE, true);
                i.putExtra(Constant.SECTION, Constant.Work_History_Resume);
//                i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(nurseProfileModel.getData()));
                startActivityForResult(i, Constant.REQUEST_EDIT);
            }
        });
        binding.edit31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WorkHistoryActivity.this, RegisterActivity.class);
                i.putExtra(Constant.EDIT_MODE, true);
                i.putExtra(Constant.SECTION, Constant.Work_History_Certifications);
                i.putExtra(Constant.ADD, true);
//                i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(nurseProfileModel.getData()));
                startActivityForResult(i, Constant.REQUEST_EDIT);
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getNurseProfile() {
        if (!Utils.isNetworkAvailable(WorkHistoryActivity.this)) {
            Utils.displayToast(WorkHistoryActivity.this, getResources().getString(R.string.no_internet));
            return;
        }
        binding.progressBar.setVisibility(View.VISIBLE);

        user_id = new SessionManager(getApplicationContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<UserProfile> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_nurse_profile(user_id1);

        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                Log.d(TAG + "getNurseProfile ResCode", response.code() + "");

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
//                    Utils.displayToast(context, null);
//                    Utils.displayToast(context, response.message());

                    binding.progressBar.setVisibility(View.GONE);
//                    Utils.displayToast(context, "" + response.body().getMessage());

                    nurseProfileModel = response.body();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setData();
                        }
                    });

                } else {
                    Utils.displayToast(context, "Data has not been updated");

                    binding.progressBar.setVisibility(View.GONE);
                }


                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Utils.displayToast(WorkHistoryActivity.this, "Failed to get updated data !");
                Log.e(TAG + "getNurseProfile", t.toString());
            }
        });

    }

    private void setData() {

        binding.tvHighestNurseDegree.setText(nurseProfileModel.getData().getExperience().getHighestNursingDegreeDefinition());
        binding.tvCollageName.setText(nurseProfileModel.getData().getExperience().getCollegeUniName());
        binding.tvFacilityExperience.setText(nurseProfileModel.getData().getExperience().getExperienceAsAcuteCareFacility());
        binding.tvNursingExperience.setText(nurseProfileModel.getData().getExperience().getExperienceAsAmbulatoryCareFacility());
        binding.tvCerner.setText(nurseProfileModel.getData().getExperience().getEhrProficiencyCernerDefinition());
        binding.tvMeditech.setText(nurseProfileModel.getData().getExperience().getEhrProficiencyMeditechDefinition());
        binding.tvEpic.setText(nurseProfileModel.getData().getExperience().getEhrProficiencyEpicDefinition());
        binding.tvOther.setText(nurseProfileModel.getData().getExperience().getEhrProficiencyOther());
//        binding.tvSearchForCredential.setText(nurseProfileModel.getData().get);
        if (TextUtils.isEmpty(nurseProfileModel.getData().getResume())) {
            binding.tvResume.setVisibility(View.VISIBLE);
            binding.edit3.setVisibility(View.GONE);
            binding.layResume.setVisibility(View.GONE);
        }

        binding.recyclerView.setAdapter(new CertificationsAdapter(WorkHistoryActivity.this,
                nurseProfileModel.getData().getCertitficate(), new CertificationCallback() {
            @Override
            public void onEdit(int position) {
                Intent i = new Intent(WorkHistoryActivity.this, RegisterActivity.class);
                i.putExtra(Constant.EDIT_MODE, true);
                i.putExtra(Constant.SECTION, Constant.Work_History_Certifications);
//                i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(nurseProfileModel.getData()));
                i.putExtra(Constant.POSITION, position);
                startActivityForResult(i, Constant.REQUEST_EDIT);

            }

            @Override
            public void onRemove(int position, UserProfileData.Certitficate certitficate) {

                removeImage(certitficate.getCertificateImage(), certitficate.getCertificateId());
            }
        }));

        if (nurseProfileModel.getData().getCertitficate() != null && nurseProfileModel.getData().getCertitficate().size() != 0)
            binding.recyclerView.scrollToPosition(SCROLL_TO);

        binding.downloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent viewIntent = new Intent("android.intent.action.VIEW",
                        Uri.parse(nurseProfileModel.getData().getResume()));
                startActivity(viewIntent);

            }
        });

    }

    private void removeImage(String url, String id) {

        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(id)) {
            Utils.displayToast(WorkHistoryActivity.this, "certificate data is empty retry to delete !");
            return;
        }
        binding.progressBar.setVisibility(View.VISIBLE);

        user_id = new SessionManager(getApplicationContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
//        RequestBody img_url = RequestBody.create(MediaType.parse("multipart/form-data"), url);
        RequestBody c_id = RequestBody.create(MediaType.parse("multipart/form-data"), id);

        Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_remove_certificate_image(user_id1, c_id);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                assert response.body() != null;
                if (!response.body().getApiStatus().equals("1")) {
                    Utils.displayToast(context, "" + response.body().getMessage());
                    binding.progressBar.setVisibility(View.GONE);
                    return;
                }
                if (response.isSuccessful()) {
                    binding.progressBar.setVisibility(View.GONE);
                    Utils.displayToast(WorkHistoryActivity.this, "Certificate deleted !");
                    getNurseProfile();
                } else {

                    binding.progressBar.setVisibility(View.GONE);
                    Utils.displayToast(context, "Failed to delete data");
                }


                binding.progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e(TAG + "getNurseProfile", t.toString());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {
                String data1 = data.getStringExtra(Constant.STR_RESPONSE_DATA);
                SCROLL_TO = data.getIntExtra(Constant.SCROLL_TO, 0);
                getNurseProfile();
                setResult(RESULT_OK);/*
                if (!TextUtils.isEmpty(data1)) {

                } else
                    Utils.displayToast(context, "Empty Data on Result");*/
            }
        }
    }

}