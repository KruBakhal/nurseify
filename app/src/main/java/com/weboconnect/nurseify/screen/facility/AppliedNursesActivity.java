package com.weboconnect.nurseify.screen.facility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.AppliedNursesAdapter;
import com.weboconnect.nurseify.databinding.ActivityAppliedNursesBinding;

public class AppliedNursesActivity extends AppCompatActivity {

    ActivityAppliedNursesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(AppliedNursesActivity.this,R.layout.activity_applied_nurses);
        binding.recyclerView.setAdapter(new AppliedNursesAdapter(AppliedNursesActivity.this));
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}