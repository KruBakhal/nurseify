package com.weboconnect.nursify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.weboconnect.nursify.R;
import com.weboconnect.nursify.databinding.ActivityJobDetailsBinding;

public class JobDetailsActivity extends AppCompatActivity {

    ActivityJobDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(JobDetailsActivity.this,R.layout.activity_job_details);
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}