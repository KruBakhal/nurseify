package com.nurseify.app.screen.facility.ui;

import static android.app.Activity.RESULT_OK;
import static com.nurseify.app.utils.FileUtils.getPath;

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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nurseify.app.R;
import com.nurseify.app.databinding.FragmentAccountFBinding;
import com.nurseify.app.screen.facility.FacilityDetails1Activity;
import com.nurseify.app.screen.facility.HomeFActivity;
import com.nurseify.app.screen.facility.model.FacilityProfile;
import com.nurseify.app.screen.facility.model.FacilitySettingModel;
import com.nurseify.app.screen.nurse.SettingActivity;
import com.nurseify.app.screen.nurse.model.FacilityJobModel;
import com.nurseify.app.screen.nurse.model.UserProfile;
import com.nurseify.app.utils.Constant;
import com.nurseify.app.utils.SessionManager;
import com.nurseify.app.utils.Utils;
import com.nurseify.app.webService.FacilityAPI;
import com.nurseify.app.webService.RetrofitClient;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    private String user_id = "";
    private String str_facility_logo;

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
        str_facility_logo = facilityProfile.getFacilityLogo_base();
        binding.facilityName.setText(facilityProfile.getFacilityName());
        binding.layFacilityType.setText(facilityProfile.getFacilityTypeDefinition());
        binding.facilityAddress.setText(facilityProfile.getFacilityAddress() + ", " + facilityProfile.getFacilityCity()
                + ", " + facilityProfile.getFacilityState());
        loadProfile_Pic(false);
        getSetting();
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
                startActivityForResult(new Intent(getContext(), FacilityDetails1Activity.class), Constant.REQUEST_EDIT);
            }
        });

        binding.layoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent((HomeFActivity) getActivity(), SettingActivity.class), 223);
            }
        });

    }

    private void loadProfile_Pic(boolean b) {
        if (!TextUtils.isEmpty(str_facility_logo)) {
            byte[] decodeString = Utils.get_base_images(str_facility_logo);
            RequestOptions myOptions = new RequestOptions()
                    .override(100, 100);
            Glide.with((HomeFActivity) getActivity())
                    .load(decodeString).apply(myOptions).placeholder(R.drawable.person)
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
                , new SessionManager(getContext()).get_facilityProfile().getFacilityId());
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
                            getFacilityProfile();
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

        user_id = new SessionManager(getContext()).get_facilityProfile().getFacilityId();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<FacilitySettingModel> call = RetrofitClient.getInstance().getFacilityApi()
                .call_setting(user_id1);

        call.enqueue(new Callback<FacilitySettingModel>() {
            @Override
            public void onResponse(Call<FacilitySettingModel> call, Response<FacilitySettingModel> response) {
                if (response.isSuccessful()) {
                    try {
                        FacilitySettingModel settingModel = response.body();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                binding.tvRating.setText("" + settingModel.getData().getRating().getOverAll());
                                binding.tvFollower.setText(settingModel.getData().getFollowers());
                                binding.tvReview.setText(settingModel.getData().getReview());
                                binding.facilityName.setText(settingModel.getData().getFacilityName());
//                                binding.layFacilityType.setText(settingModel.getData().getFacilityTypeDefinition());
                                binding.facilityAddress.setText(settingModel.getData().getAddress() + ", " + settingModel.getData().getCity()
                                        + ", " + settingModel.getData().getState());
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
            public void onFailure(Call<FacilitySettingModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e(TAG + "getNotification", t.toString());
            }
        });

    }

    private void getFacilityProfile() {
        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.displayToast(getContext(), getResources().getString(R.string.no_internet));
            return;
        }
        progressDialog.show();

        user_id = new SessionManager(getContext()).get_facilityProfile().getFacilityId();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<FacilityJobModel> call = RetrofitClient.getInstance().getFacilityApi().call_facility_profile(user_id1);

        call.enqueue(new Callback<FacilityJobModel>() {
            @Override
            public void onResponse(Call<FacilityJobModel> call, Response<FacilityJobModel> response) {
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
                    progressDialog.dismiss();
                    FacilityJobModel.Facility model = response.body().getData().get(0);
                    str_facility_logo = model.getFacilityLogo_base();
                    send_profile_path_to_firebase(model.getId(), str_facility_logo);
                    loadProfile_Pic(true);
                } else {
                    progressDialog.dismiss();
                }

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<FacilityJobModel> call, Throwable t) {
                progressDialog.dismiss();

                Utils.displayToast(getContext(), "Failed to get updated data !");
                Log.e(TAG + "getNurseProfile", t.toString());
            }
        });

    }

    private void send_profile_path_to_firebase(String id, String image) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child(Constant.USER_NODE)
                .child(new SessionManager(getContext()).get_user_register_Id());

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot == null || snapshot.getValue() == null) {
//                        create_user_inside_firebase(new SessionManager(getContext()).get_User());
                    } else {
                        try {
                            Map<String, Object> objectMap = (Map<String, Object>) snapshot.getValue();
                            if (objectMap != null && !TextUtils.isEmpty(objectMap.get(Constant.EMAIL_ID)
                                    .toString())) {
                                try {
                                    String userid = new SessionManager(getContext()).get_user_register_Id();
                                    FirebaseDatabase.getInstance().getReference(Constant.USER_NODE)
                                            .child(userid).child("profile_path")
                                            .setValue(image);
                                } catch (Exception e) {
                                    Log.d("TAG", "update_user_status: " + e.getMessage());
                                }

                            }
                        } catch (Exception exception) {
                            try {
                                HashMap<String, Object> user_hashMap = get_User_Hash();
                                String userid = new SessionManager(getContext()).get_user_register_Id();
                                FirebaseDatabase.getInstance().getReference(Constant.USER_NODE)
                                        .child(userid).updateChildren(user_hashMap);
                            } catch (Exception e) {
                                Log.d("TAG", "update_user_status: " + e.getMessage());
                            }
                        }

                    }
                } catch (Exception exception) {
                    Log.d("TAG_check_User_exist", "onDataChange: " + exception.getMessage());
                }
            }

            private HashMap<String, Object> get_User_Hash() {
                SessionManager sessionManager = new SessionManager(getContext());
                String userData = sessionManager.get_TYPE();
                String email, full_name, id, profile, specialty, status, type;
                if (userData.equals(Constant.CONST_FACULTY_TYPE)) {
                    email = sessionManager.get_facilityProfile().getFacilityEmail();
                    profile = sessionManager.get_facilityProfile().getFacilityLogo();
                    full_name = sessionManager.get_facilityProfile().getFacilityName();
                    id = sessionManager.get_facilityProfile().getUserId();
                    specialty = " " + sessionManager.get_facilityProfile().getFacilityTypeDefinition();
                    status = "true";
                    type = "2";
                } else {
                    email = sessionManager.get_User().getEmail();
                    profile = sessionManager.get_User().getImage();
                    full_name = sessionManager.get_User().getFullName();
                    id = sessionManager.get_User().getId();
                    specialty = " " + sessionManager.get_facilityProfile().getFacilityTypeDefinition();
                    status = "true";
                    type = "1";
                }
                if (TextUtils.isEmpty(specialty))
                    specialty = " ";
                HashMap<String, Object> user_hashMap = new HashMap<>();
                user_hashMap.put(Constant.EMAIL_ID, email);
                user_hashMap.put(Constant.ID, id);
                user_hashMap.put(Constant.FULL_NAME, full_name);
                user_hashMap.put(Constant.PROFILE_PATH, profile);
                user_hashMap.put(Constant.ONLINE_STATUS, true);
                user_hashMap.put(Constant.TYPE, type);
                user_hashMap.put(Constant.SPECIALITY, specialty);
                return user_hashMap;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                    str_facility_logo = facilityProfile.getFacilityLogo_base();
                    binding.facilityName.setText(facilityProfile.getFacilityName());
                    binding.layFacilityType.setText(facilityProfile.getFacilityTypeDefinition());
                    binding.facilityAddress.setText(facilityProfile.getFacilityAddress() + ", " + facilityProfile.getFacilityCity()
                            + ", " + facilityProfile.getFacilityState());
                    loadProfile_Pic(true);
                    send_profile_path_to_firebase(facilityProfile.getUserId(), facilityProfile.getFacilityLogo());
                } else
                    Utils.displayToast(getContext(), "Empty Data on Result");
            } else {

            }
        }
    }

}