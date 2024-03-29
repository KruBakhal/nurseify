package com.nurseify.app.screen.facility;

import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nurseify.app.R;
import com.nurseify.app.databinding.ActivityAddJob1Binding;

public class AddJobActivity1 extends Activity {
    ActivityAddJob1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(AddJobActivity1.this, R.layout.activity_add_job1);

        click();
    }

    private void click() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddJobActivity1.this, AddJob2Activity.class));
            }
        });
    }
}