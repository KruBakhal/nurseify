package com.weboconnect.nurseify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityHourlyRateBinding;
import com.weboconnect.nurseify.screen.nurse.model.UserProfileData;
import com.weboconnect.nurseify.utils.SessionManager;

public class HourlyRateActivity extends AppCompatActivity {

    ActivityHourlyRateBinding binding;

    UserProfileData userProfileData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(HourlyRateActivity.this,R.layout.activity_hourly_rate);

        userProfileData = new SessionManager(getApplicationContext()).get_User();

        binding.tvHourlyRate.setText("$ "+userProfileData.getHourlyPayRate());
        binding.tvShiftDuration.setText(userProfileData.getShiftDuration());
        binding.tvAssignmentDuration.setText(userProfileData.getAssignmentDuration());
        binding.tvPreferredShift.setText(userProfileData.getPreferredShift());
        binding.tvEarliestStartDate.setText(userProfileData.getEarliestStartDate());

        for (int i=0; i<userProfileData.getDaysOfTheWeek().size(); i++){
            if (i==0)
                binding.tvPreferredDay.setText(userProfileData.getDaysOfTheWeek().get(i));
            else
                binding.tvPreferredDay.append(", "+userProfileData.getDaysOfTheWeek().get(i));
        }

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
                i.putExtra("state",2);
                startActivity(i);
            }
        });
    }
}