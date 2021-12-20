package com.weboconnect.nurseify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.nurse.adapters.JobAdapter;
import com.weboconnect.nurseify.databinding.ActivityCreateProfileBinding;

public class CreateProfileActivity extends AppCompatActivity {

    ActivityCreateProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(CreateProfileActivity.this, R.layout.activity_create_profile);
        binding.recyclerViewJobs.setAdapter(new JobAdapter(CreateProfileActivity.this, 1));
    }
}