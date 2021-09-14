package com.weboconnect.nurseify.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityLoginSelectBinding;
import com.weboconnect.nurseify.screen.facility.LoginFacilityActivity;
import com.weboconnect.nurseify.screen.nurse.LoginActivity;

public class LoginSelectActivity extends AppCompatActivity {

    ActivityLoginSelectBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(LoginSelectActivity.this, R.layout.activity_login_select);
        binding.nurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSelectActivity.this, LoginActivity.class));
            }
        });
        binding.facility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSelectActivity.this, LoginFacilityActivity.class));
            }
        });
    }
}