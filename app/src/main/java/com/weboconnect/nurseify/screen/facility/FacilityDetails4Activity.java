package com.weboconnect.nurseify.screen.facility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityFacilityDetails1Binding;
import com.weboconnect.nurseify.databinding.ActivityFacilityDetails4Binding;

public class FacilityDetails4Activity extends AppCompatActivity {

    ActivityFacilityDetails4Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(FacilityDetails4Activity.this,R.layout.activity_facility_details4);
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}