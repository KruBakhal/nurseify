package com.nurseify.app.screen.nurse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nurseify.app.R;
import com.nurseify.app.databinding.ActivityHourlyRateBinding;
import com.nurseify.app.screen.nurse.model.UserProfile;
import com.nurseify.app.screen.nurse.model.UserProfileData;
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

public class HourlyRateActivity extends AppCompatActivity {

    ActivityHourlyRateBinding binding;

    UserProfileData userProfileData;
    private ProgressDialog progressDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(HourlyRateActivity.this, R.layout.activity_hourly_rate);
        context = this;
        userProfileData = new SessionManager(getApplicationContext()).get_User();
        setData();
        getNurseProfile();

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HourlyRateActivity.this, RegisterActivity.class);
                i.putExtra(Constant.EDIT_MODE, true);
                i.putExtra(Constant.SECTION, Constant.HOURLY_RATE_AVAILABILITY);
                i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(userProfileData));
                startActivityForResult(i, Constant.REQUEST_EDIT);
            }
        });
        binding.edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HourlyRateActivity.this, RegisterActivity.class);
                i.putExtra(Constant.EDIT_MODE, true);
                i.putExtra(Constant.SECTION, Constant.HOURLY_RATE_AVAILABILITY);
                i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(userProfileData));
                startActivityForResult(i, Constant.REQUEST_EDIT);
            }
        });

    }

    private void setData() {
        binding.tvHourlyRate.setText("$ " + userProfileData.getHourlyPayRate());
        binding.tvShiftDuration.setText(userProfileData.getShiftDurationDefinition());
        binding.tvAssignmentDuration.setText(userProfileData.getAssignmentDurationDefinition());
        binding.tvPreferredShift.setText(userProfileData.getPreferred_shift_definition());
        binding.tvEarliestStartDate.setText(userProfileData.getEarliestStartDate().replace("/", "-"));

        for (int i = 0; i < userProfileData.getDaysOfTheWeek().size(); i++) {
            if (i == 0)
                binding.tvPreferredDay.setText(userProfileData.getDaysOfTheWeek().get(i));
            else
                binding.tvPreferredDay.append(", " + userProfileData.getDaysOfTheWeek().get(i));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {

                String data1 = data.getStringExtra(Constant.STR_RESPONSE_DATA);
                Type type = new TypeToken<UserProfileData>() {
                }.getType();
                userProfileData = new Gson().fromJson(data1, type);
                if (userProfileData != null) {
                    setData();
                    setResult(RESULT_OK);
                } else
                    Utils.displayToast(getApplicationContext(), "Empty Data on Result");
            } else {


            }
        }
    }

    private void getNurseProfile() {
        if (!Utils.isNetworkAvailable(context)) {
            Utils.displayToast(context, getResources().getString(R.string.no_internet));
            return;
        }
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String user_id = new SessionManager(context).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<UserProfile> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_nurse_profile(user_id1);

        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
//                Log.d("TAG", response.code() + "");

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
                    userProfileData = response.body().getData();
                    new SessionManager(context).save_user(userProfileData);
                    setData();
                } else {
                    progressDialog.dismiss();
                }


                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                progressDialog.dismiss();

                Utils.displayToast(context, "Failed to get updated data !");
                Log.e("TAG" + "getNurseProfile", t.toString());
            }
        });

    }

}