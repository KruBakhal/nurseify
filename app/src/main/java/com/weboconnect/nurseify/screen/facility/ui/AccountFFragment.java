package com.weboconnect.nurseify.screen.facility.ui;

import static android.app.Activity.RESULT_OK;
import static com.weboconnect.nurseify.utils.FileUtils.getPath;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.FragmentAccountFBinding;
import com.weboconnect.nurseify.screen.facility.FacilityDetails1Activity;
import com.weboconnect.nurseify.screen.facility.HomeFActivity;
import com.weboconnect.nurseify.screen.facility.model.FacilityProfile;
import com.weboconnect.nurseify.screen.nurse.SettingActivity;
import com.weboconnect.nurseify.screen.nurse.model.UserProfile;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.FacilityAPI;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFFragment extends Fragment {
    private static final String TAG = "tag";
    String id;
    FragmentAccountFBinding binding;
    View view;
    private FacilityProfile facilityProfile;
    private ProgressDialog progressDialog;
    private String user_id;

    public AccountFFragment() {
    }

    public AccountFFragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_f, null, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        facilityProfile = new SessionManager(getContext()).get_facilityProfile();

        binding.facilityName.setText(facilityProfile.getFacilityName());
        binding.layFacilityType.setText(facilityProfile.getFacilityTypeDefinition());
        binding.facilityAddress.setText(facilityProfile.getFacilityAddress() + ", " + facilityProfile.getFacilityCity()
                + ", " + facilityProfile.getFacilityState());
        loadProfile_Pic(false);
        click();
        return view = binding.getRoot();
    }

    private void click() {
        binding.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkReadExternal()) {
                    startForResultPermission.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
                    return;
                }
                System.gc();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpg", "image/png", "image/jpeg"});
                resultLauncherProfile.launch(intent);
                Utils.onClickEvent(v);
            }
        });

        binding.layoutPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FacilityDetails1Activity.class));
            }
        });

        binding.layoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingActivity.class));
            }
        });

    }

    private void loadProfile_Pic(boolean b) {
        if (!TextUtils.isEmpty(facilityProfile.getFacilityLogo())) {
            Glide.with((HomeFActivity) getActivity())
                    .load(facilityProfile.getFacilityLogo()).placeholder(R.drawable.person)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(binding.circleImageView2);
        }

    }

    private boolean checkReadExternal() {
        if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED)) {
            return false;
        }
        return true;
    }

    ActivityResultLauncher<Intent> resultLauncherProfile = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    File file = null;
                    try {
                        if (result.getData() == null || result.getData().getScheme() == null) {
                            Utils.displayToast(getContext(), "Select Proper Image Format. Only png, jpg, jpeg are allowed");
                            return;
                        }
                        file = new File(getPath(getContext(), data.getData()));
                        String str = Utils.getFileSize(Long.parseLong(String.valueOf(file.length())));
                        Log.d("TAG", file.length() + " size : " + str);
                        if (!(file.getName().endsWith(".png") || file.getName().endsWith(".jpg")
                                || file.getName().endsWith(".jpeg"))) {
                            Utils.displayToast(getContext(), "Select Proper Image Format. Only png, jpg, jpeg are allowed");
                            return;
                        }
                        double sis = Double.parseDouble(Utils.getFileSize_size(file.length()));
                        if ((sis > 1 && Utils.getFileSize_Unit(file.length()).equals("MB"))) {
                            Utils.displayToast(getContext(), "Select File less than equal to 1 MB");
                            return;
                        }
                    } catch (Exception e) {
                        Utils.displayToast(getContext(), "Select file is damage");
                        Log.d(TAG, "resultLauncherProfile: " + e.getMessage());
                        return;
                    }
                    if (file != null) {
                        String user_profile = file.getAbsolutePath();
                        Glide.with(getContext()).load(file.getAbsolutePath()).
                                into(binding.circleImageView2);
//                        binding.imgProfile.setVisibility(View.VISIBLE);
                        upload_profile_photo(user_profile);
                    }

                }
            });

    private void upload_profile_photo(String user_profile) {
        progressDialog.show();
        FacilityAPI backendApi = RetrofitClient.getInstance().getFacilityApi();
        RequestBody request_id = RequestBody.create(MediaType.parse("multipart/form-data")
                , new SessionManager(getContext()).get_user_register_Id());
        RequestBody request_key = RequestBody.create(MediaType.parse("multipart/form-data")
                , new SessionManager(getContext()).get_API_KEY());
        RequestBody request_img = RequestBody.create(MediaType.parse("image/*"),
                new File(user_profile));
        MultipartBody.Part multipartBodyCertifcate = MultipartBody.Part.createFormData("facility_logo",
                new File(user_profile).getName(), request_img);

        backendApi.call_Profile_Photos(request_key, request_id, multipartBodyCertifcate)
                .enqueue(new Callback<UserProfile>() {
                    @Override
                    public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                        assert response.body() != null;
                        if (!response.body().getApiStatus().equals("1")) {
                            progressDialog.dismiss();
                            Utils.displayToast(getContext(), "" + response.body().getMessage());
                            return;
                        }
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
//                            getNurseProfile();
                        } else {

                            progressDialog.dismiss();
                        }
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<UserProfile> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d("TAG", "call_sendData_For_PersonalDetail() onFailure: " + t.getMessage());
                    }
                });


    }

    ActivityResultLauncher<String[]> startForResultPermission = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                if (!result.containsValue(false)) {
//                    onClick(roleInterest2Binding.layAddPhotos);
//                    Utils.displayToast(context, "Important Permission Allowed !");
                } else {
                    Utils.displayToast(getContext(), "Important Permission Denied !");
                }
            });

    private void getSetting() {

      /*  binding.progressBar.setVisibility(View.VISIBLE);

        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<SettingModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_setting(user_id1);

        call.enqueue(new Callback<SettingModel>() {
            @Override
            public void onResponse(Call<SettingModel> call, Response<SettingModel> response) {
                Log.d(TAG, "getNotification ResCode" + response.code() + "");
                if (response.isSuccessful()) {

                    try {
                        SettingModel settingModel = response.body();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                binding.tvBill.setText("$ " + settingModel.getData().getBilRate());
                                binding.tvExperience.setText(settingModel.getData().getExperience());
                                binding.tvShift.setText(settingModel.getData().getShift());

                            }
                        });

                    } catch (Exception e) {
                        Log.e(TAG + "getNotification", e.toString());
                    }

                } else {
                    Log.e(TAG + "getNotification", response.message());
                    return;
                }
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SettingModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e(TAG + "getNotification", t.toString());
            }
        });*/

    }

    private void getNurseProfile() {
        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.displayToast(getContext(), getResources().getString(R.string.no_internet));
            return;
        }
        progressDialog.show();

        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<UserProfile> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_nurse_profile(user_id1);

        /*call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                Log.d(TAG, "getNurseProfile ResCode" + response.code() + "");

                if (response.body() == null) {
                    try {
                        progressDialog.dismiss();
                        Log.d("TAG", "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                if (response.body().getApiStatus().equals("1")) {
//                    Utils.displayToast(context, null);
//                    Utils.displayToast(context, response.message());

                    progressDialog.dismiss();

//                    Utils.displayToast(context, "" + response.body().getMessage());

                    facilityProfile = response.body().getData();
                    new SessionManager(getContext()).save_user(facilityProfile);
                    loadProfile_Pic(true);
                } else {
//                    Utils.displayToast(getContext(), "Data has not been updated");

                    progressDialog.dismiss();

                }


                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                progressDialog.dismiss();

                Utils.displayToast(getContext(), "Failed to get updated data !");
                Log.e(TAG + "getNurseProfile", t.toString());
            }
        });
*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {
//                String data1 = data.getStringExtra(Constant.STR_RESPONSE_DATA);
//                Type type = new TypeToken<UserProfileData>() {
//                }.getType();
//                userProfileData = new Gson().fromJson(data1, type);
                facilityProfile = new SessionManager(getContext()).get_facilityProfile();
                if (facilityProfile != null) {
                    binding.facilityName.setText(facilityProfile.getFacilityName());
                    binding.layFacilityType.setText(facilityProfile.getFacilityTypeDefinition());
                    binding.facilityAddress.setText(facilityProfile.getFacilityAddress() + ", " + facilityProfile.getFacilityCity()
                            + ", " + facilityProfile.getFacilityState());
                    loadProfile_Pic(true);
                } else
                    Utils.displayToast(getContext(), "Empty Data on Result");
            } else {

            }
        }
    }

}