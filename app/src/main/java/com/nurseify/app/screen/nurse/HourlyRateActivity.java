package com.nurseify.app.screen.nurse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nurseify.app.R;
import com.nurseify.app.databinding.ActivityHourlyRateBinding;
import com.nurseify.app.screen.nurse.model.UserProfileData;
import com.nurseify.app.utils.Constant;
import com.nurseify.app.utils.SessionManager;
import com.nurseify.app.utils.Utils;

import java.lang.reflect.Type;

public class HourlyRateActivity extends AppCompatActivity {

    ActivityHourlyRateBinding binding;

    UserProfileData userProfileData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(HourlyRateActivity.this, R.layout.activity_hourly_rate);

        userProfileData = new SessionManager(getApplicationContext()).get_User();
        setData();

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
}