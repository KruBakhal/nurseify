package com.weboconnect.nurseify.screen.nurse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.gson.Gson;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.PhotoFilesAdapter;
import com.weboconnect.nurseify.databinding.ActivityRoleBinding;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.screen.nurse.model.ResponseModel;
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

public class RoleActivity extends AppCompatActivity {

    ActivityRoleBinding binding;
    private UserProfile nurseProfileModel;
    private PhotoFilesAdapter photoFilesAdapter;
    private PhotoFilesAdapter filesAdapter;
    private List<String> list_photos = new ArrayList<>();
    private List<String> list_Files = new ArrayList<>();
    private Context context = RoleActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(RoleActivity.this, R.layout.activity_role);
        binding.progressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        getNurseProfile();

        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RoleActivity.this, RegisterActivity.class);
                i.putExtra(Constant.EDIT_MODE, true);
                i.putExtra(Constant.SECTION, Constant.Role_Interest1);
                i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(nurseProfileModel.getData()));
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
        if (!Utils.isNetworkAvailable(RoleActivity.this)) {
            Utils.displayToast(RoleActivity.this, getResources().getString(R.string.no_internet));
            return;
        }
        binding.progressBar.setVisibility(View.VISIBLE);

        String user_id = new SessionManager(getApplicationContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<UserProfile> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_nurse_profile(user_id1);

        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {

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
                    nurseProfileModel = response.body();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setData();
                        }
                    });

                } else {
                    Utils.displayToast(RoleActivity.this, "Data has not been updated");

                    binding.progressBar.setVisibility(View.GONE);
                }


                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Utils.displayToast(RoleActivity.this, "Failed to get updated data !");
                Log.e("TAG" + "getNurseProfile", t.toString());
            }
        });

    }

    private void setData() {
        binding.tv1.setText("" + nurseProfileModel.getData().getRoleInterest().getServingPreceptorDefinition());
        binding.tv2.setText("" + nurseProfileModel.getData().getRoleInterest().getServingInterimNurseLeaderDefinition());
        binding.tv3.setText("" + nurseProfileModel.getData().getRoleInterest().getLeadershipRolesDefinition());
        binding.tv4.setText("" + nurseProfileModel.getData().getRoleInterest().getClinicalEducatorDefinition());
        binding.tv5.setText("" + nurseProfileModel.getData().getRoleInterest().getIsDaisyAwardWinnerDefinition());
        binding.tv6.setText("" + nurseProfileModel.getData().getRoleInterest().getEmployeeOfTheMthQtrYrDefinition());
        binding.tv7.setText("" + nurseProfileModel.getData().getRoleInterest().getOtherNursingAwardsDefinition());
        binding.tv8.setText("" + nurseProfileModel.getData().getRoleInterest().getIsProfessionalPracticeCouncilDefinition());
        binding.tv9.setText("" + nurseProfileModel.getData().getRoleInterest().getIsResearchPublicationsDefinition());
        if (!TextUtils.isEmpty(nurseProfileModel.getData().getRoleInterest().getServingInterimNurseLeaderDefinition())
                && nurseProfileModel.getData().getRoleInterest().getServingInterimNurseLeaderDefinition().equals("yes")) {
            binding.layHide.setVisibility(View.VISIBLE);
        } else binding.layHide.setVisibility(View.GONE);
        String lang = "";
        for (int i = 0; i < nurseProfileModel.getData().getRoleInterest().getLanguages().size(); i++) {
            if (i == 0) {
                lang = nurseProfileModel.getData().getRoleInterest().getLanguages().get(i);
            } else
                lang = lang + ", " + nurseProfileModel.getData().getRoleInterest().getLanguages().get(i);
        }
        binding.tvLang.setText("" + lang);
        binding.tvIntro.setText("" + nurseProfileModel.getData().getRoleInterest().getSummary());
        binding.tvUrlLink.setText("" + nurseProfileModel.getData().getRoleInterest().getNuVideoEmbedUrl());
        List<UserProfileData.AdditionalPicture> sdsd =
                nurseProfileModel.getData().getRoleInterest().getAdditionalPictures();
        if (sdsd != null && sdsd.size() != 0) {
            list_photos.clear();
            for (UserProfileData.AdditionalPicture additionalPicture : sdsd) {
                list_photos.add(additionalPicture.getPhoto());
            }
            photoFilesAdapter = new PhotoFilesAdapter(list_photos, 3, new ItemCallback() {
                @Override
                public void onClick(int position) {
                    List<UserProfileData.AdditionalPicture> list = nurseProfileModel.getData().getRoleInterest().getAdditionalPictures();
                    if (list != null && list.size() != 0 && position < list.size()) {
                        removeImage_DOC(list.get(position).getAssetId());
                    } else {
                        Utils.displayToast(context, "Something When wrong empty data");
                    }
                }
            });
            binding.rvPhotos.setAdapter(photoFilesAdapter);
            binding.rvPhotos.setVisibility(View.VISIBLE);
            binding.tvBlank1.setVisibility(View.GONE);
        }
        List<UserProfileData.AdditionalFile> sdsd1 =
                nurseProfileModel.getData().getRoleInterest().getAdditionalFiles();
        list_Files.clear();
        if (sdsd1 != null && sdsd1.size() != 0) {
            for (UserProfileData.AdditionalFile additionalPicture : sdsd1) {
                list_Files.add(additionalPicture.getPhoto());
            }
            filesAdapter = new PhotoFilesAdapter(list_Files, 4, new ItemCallback() {
                @Override
                public void onClick(int position) {
                    List<UserProfileData.AdditionalFile> list = nurseProfileModel.getData().getRoleInterest().getAdditionalFiles();
                    if (list != null && list.size() != 0 && position < list.size()) {
                        removeImage_DOC(list.get(position).getAssetId());
                    } else {
                        Utils.displayToast(context, "Something When wrong empty data");
                    }
                }
            });
            binding.rvFiles.setAdapter(filesAdapter);
            binding.rvFiles.setVisibility(View.VISIBLE);
            binding.tvBlank2.setVisibility(View.GONE);

        }
    }

    private void removeImage_DOC(String id) {

        if (TextUtils.isEmpty(id)) {
            Utils.displayToast(RoleActivity.this, "fail to delete,retry !");
            return;
        }
        binding.progressBar.setVisibility(View.VISIBLE);

        String user_id = nurseProfileModel.getData().getId();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody c_id = RequestBody.create(MediaType.parse("multipart/form-data"), id);

        Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_remove_Asset_image(user_id1, c_id);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                assert response.body() != null;
                if (!response.body().getApiStatus().equals("1")) {
                    Utils.displayToast(RoleActivity.this, "" + response.body().getMessage());
                    binding.progressBar.setVisibility(View.GONE);
                    return;
                }
                if (response.isSuccessful()) {
                    binding.progressBar.setVisibility(View.GONE);
                    Utils.displayToast(RoleActivity.this, "Item data deleted !");
                    getNurseProfile();
                } else {

                    binding.progressBar.setVisibility(View.GONE);
                    Utils.displayToast(RoleActivity.this, "Failed to delete item data");
                }


                binding.progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e("TAG" + "getNurseProfile", t.toString());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {
                String data1 = data.getStringExtra(Constant.STR_RESPONSE_DATA);
//                SCROLL_TO = data.getIntExtra(Constant.SCROLL_TO, 0);
                if (!TextUtils.isEmpty(data1)) {
                    getNurseProfile();
                    setResult(RESULT_OK);
                } else
                    Utils.displayToast(context, "Empty Data on Result");
            }
        }
    }
}