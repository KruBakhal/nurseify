package com.nurseify.app.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.nurseify.app.R;
import com.nurseify.app.databinding.ActivityOfferedJobsActivityBinding;

public class OfferedJobsActivityActivity extends AppCompatActivity {
    ActivityOfferedJobsActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(OfferedJobsActivityActivity.this,R.layout.activity_offered_jobs_activity);
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}