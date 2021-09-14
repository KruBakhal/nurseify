package com.weboconnect.nurseify.screen.facility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityFacilityDetails2Binding;
import com.weboconnect.nurseify.databinding.ActivityFacilityDetails3Binding;

public class FacilityDetails3Activity extends AppCompatActivity {

    ActivityFacilityDetails3Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_details3);
        binding = DataBindingUtil.setContentView(FacilityDetails3Activity.this,R.layout.activity_facility_details3);
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacilityDetails3Activity.this,FacilityDetails4Activity.class));
            }
        });
    }
}