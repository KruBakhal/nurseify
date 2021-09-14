package com.weboconnect.nurseify.screen.facility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityFacilityDetails1Binding;
import com.weboconnect.nurseify.databinding.ActivityFacilityDetails2Binding;

public class FacilityDetails2Activity extends AppCompatActivity {
    ActivityFacilityDetails2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(FacilityDetails2Activity.this,R.layout.activity_facility_details2);
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacilityDetails2Activity.this,FacilityDetails3Activity.class));
            }
        });
    }
}