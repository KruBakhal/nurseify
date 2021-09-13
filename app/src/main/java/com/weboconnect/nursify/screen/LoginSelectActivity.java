package com.weboconnect.nursify.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.weboconnect.nursify.R;
import com.weboconnect.nursify.databinding.ActivityLoginSelectBinding;
import com.weboconnect.nursify.screen.nurse.LoginActivity;

public class LoginSelectActivity extends AppCompatActivity {

    ActivityLoginSelectBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(LoginSelectActivity.this, R.layout.activity_login_select);
        binding.clinical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSelectActivity.this, LoginActivity.class));
            }
        });
        binding.facility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSelectActivity.this,LoginActivity.class));
            }
        });
    }
}