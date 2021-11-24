package com.weboconnect.nurseify.screen.nurse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.FragmentAccountBinding;
import com.weboconnect.nurseify.screen.nurse.HourlyRateActivity;
import com.weboconnect.nurseify.screen.nurse.PersonalDetailsActivity;
import com.weboconnect.nurseify.screen.nurse.RoleActivity;
import com.weboconnect.nurseify.screen.nurse.SettingActivity;
import com.weboconnect.nurseify.screen.nurse.WorkHistoryActivity;
import com.weboconnect.nurseify.screen.nurse.model.NotificationModel;
import com.weboconnect.nurseify.screen.nurse.model.SettingModel;
import com.weboconnect.nurseify.screen.nurse.model.SignupModel;
import com.weboconnect.nurseify.screen.nurse.model.UserProfileData;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.webService.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {
    String id;
    FragmentAccountBinding binding;
    View view;
    public AccountFragment(){ }
    public AccountFragment(String id) {
        this.id = id;
    }

    String user_id;
    String  TAG = "AccountFragment ";

    UserProfileData userProfileData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_account, null, false);

        userProfileData = new SessionManager(getContext()).get_User();

        binding.tvName.setText(userProfileData.getFullName());
        binding.tvLicenceNo.setText(userProfileData.getNursingLicenseNumber());
        binding.tvAddress.setText(userProfileData.getAddress()+", "+userProfileData.getCity()+", "+userProfileData.getCountry());


        getSetting();

        binding.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getContext(), PersonalDetailsActivity.class));
            }
        });
        binding.layoutPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PersonalDetailsActivity.class));
            }
        });
        binding.layoutHourly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HourlyRateActivity.class));
            }
        });
        binding.layoutRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RoleActivity.class));
            }
        });
        binding.layoutWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WorkHistoryActivity.class));
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

    private void getSetting(){

        binding.progressBar.setVisibility(View.VISIBLE);

        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<SettingModel> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_setting(user_id1);

        call.enqueue(new Callback<SettingModel>() {
            @Override
            public void onResponse(Call<SettingModel> call, Response<SettingModel> response) {
                Log.d(TAG+"getNotification ResCode",response.code()+"");
                if (response.isSuccessful()){

                    try {
                        SettingModel settingModel = response.body();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                binding.tvBill.setText("$ "+settingModel.getData().getBilRate());
                                binding.tvExperience.setText(settingModel.getData().getExperience());
                                binding.tvShift.setText(settingModel.getData().getShift());

                            }
                        });

                    }
                    catch (Exception e){
                        Log.e(TAG+"getNotification",e.toString());
                    }

                }else {
                    Log.e(TAG+"getNotification",response.message());
                    return;
                }
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SettingModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e(TAG+"getNotification",t.toString());
            }
        });

    }

}