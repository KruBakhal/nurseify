package com.weboconnect.nurseify.screen.nurse.ui;

import static android.app.Activity.RESULT_OK;
import static com.weboconnect.nurseify.utils.FileUtils.getPath;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.FragmentAccountBinding;
import com.weboconnect.nurseify.screen.nurse.HomeActivity;
import com.weboconnect.nurseify.screen.nurse.HourlyRateActivity;
import com.weboconnect.nurseify.screen.nurse.PersonalDetailsActivity;
import com.weboconnect.nurseify.screen.nurse.RoleActivity;
import com.weboconnect.nurseify.screen.nurse.SettingActivity;
import com.weboconnect.nurseify.screen.nurse.WorkHistoryActivity;
import com.weboconnect.nurseify.screen.nurse.model.SettingModel;
import com.weboconnect.nurseify.screen.nurse.model.UserProfile;
import com.weboconnect.nurseify.screen.nurse.model.UserProfileData;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitApi;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {
    String id;
    FragmentAccountBinding binding;
    View view;

    public AccountFragment() {
    }

    public AccountFragment(String id) {
        this.id = id;
    }

    String user_id;
    String TAG = "AccountFragment ";
    ProgressDialog progressDialog;
    UserProfileData userProfileData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, null, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        userProfileData = new SessionManager(getContext()).get_User();

        binding.tvName.setText(userProfileData.getFullName());
        binding.tvLicenceNo.setText(userProfileData.getNursingLicenseNumber());
        binding.tvAddress.setText(userProfileData.getAddress() + ", " + userProfileData.getCity() + ", " + userProfileData.getCountry());

        loadProfile_Pic(false);
        getSetting();
        binding.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getContext(), PersonalDetailsActivity.class));

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
                startActivityForResult(new Intent(getContext(), PersonalDetailsActivity.class), Constant.REQUEST_EDIT);
            }
        });
        binding.layoutHourly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), HourlyRateActivity.class),
                        Constant.REQUEST_EDIT);

            }
        });
        binding.layoutRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), RoleActivity.class), Constant.REQUEST_EDIT);
            }
        });
        binding.layoutWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), WorkHistoryActivity.class), Constant.REQUEST_EDIT);
            }
        });
        binding.layoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingActivity.class));
            }
        });

        return view = binding.getRoot();

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
                userProfileData = new SessionManager(getContext()).get_User();
                if (userProfileData != null) {
                    binding.tvName.setText(userProfileData.getFullName());
                    binding.tvLicenceNo.setText(userProfileData.getNursingLicenseNumber());
                    binding.tvAddress.setText(userProfileData.getAddress() + ", " + userProfileData.getCity() + ", " + userProfileData.getCountry());
                    binding.tvBill.setText("$ " + userProfileData.getHourlyPayRate());
                    double s1 = ((Double.parseDouble(userProfileData.getExperience().getExperienceAsAcuteCareFacility())));
                    double s2 = ((Double.parseDouble(userProfileData.getExperience().getExperienceAsAmbulatoryCareFacility())));
                    binding.tvExperience.setText("" + (s1 + s2));
                    loadProfile_Pic(true);
                } else
                    Utils.displayToast(getContext(), "Empty Data on Result");
            } else {

            }
        }
    }

    private void loadProfile_Pic(boolean b) {
        if (!TextUtils.isEmpty(userProfileData.getImage())) {
            Glide.with((HomeActivity) getActivity())
                    .load(userProfileData.getImage()).placeholder(R.drawable.person)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    Log.d(TAG, "onLoadFailed: 1 " + e.getMessage());
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into(binding.circleImageView2);
            if (((HomeActivity) getActivity()).binding != null & b)
                Glide.with(getContext()).load(userProfileData.getImage()).placeholder(R.drawable.person)
                        .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.d(TAG, "onLoadFailed: 1 " + e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(((HomeActivity) getActivity()).binding.accountIcon);

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
                        if ((sis > 5 && Utils.getFileSize_Unit(file.length()).equals("MB"))) {
                            Utils.displayToast(getContext(), "Select File less than equal to 5 MB");
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
        RetrofitApi backendApi = RetrofitClient.getInstance().getRetrofitApi();
        RequestBody certificate_img = RequestBody.create(MediaType.parse("multipart/form-data"),
                new File(user_profile));
        RequestBody request_id
                = RequestBody.create(MediaType.parse("image/*"),
                new SessionManager(getContext()).get_user_register_Id());
        MultipartBody.Part multipartBodyCertifcate = MultipartBody.Part.createFormData("profile_image",
                new File(user_profile).getName(), certificate_img);

        backendApi.call_Profile_Photos(request_id, multipartBodyCertifcate).enqueue(new Callback<UserProfile>() {
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
                    getNurseProfile();
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

        binding.progressBar.setVisibility(View.VISIBLE);

        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<SettingModel> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_setting(user_id1);

        call.enqueue(new Callback<SettingModel>() {
            @Override
            public void onResponse(Call<SettingModel> call, Response<SettingModel> response) {
                Log.d(TAG + "getNotification ResCode", response.code() + "");
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
        });

    }

    private void getNurseProfile() {
        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.displayToast(getContext(), getResources().getString(R.string.no_internet));
            return;
        }
        progressDialog.show();

        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<UserProfile> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_nurse_profile(user_id1);

        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                Log.d(TAG + "getNurseProfile ResCode", response.code() + "");

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

                    userProfileData = response.body().getData();
                    new SessionManager(getContext()).save_user(userProfileData);
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

    }

}