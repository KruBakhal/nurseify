package com.weboconnect.nursify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.weboconnect.nursify.R;
import com.weboconnect.nursify.databinding.ActivitySignupDetailsBinding;

public class SignupDetailsActivity extends AppCompatActivity {

    ActivitySignupDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SignupDetailsActivity.this, R.layout.activity_signup_details);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupDetailsActivity.this,LoginActivity.class));
            }
        });
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupDetailsActivity.this,RegisterActivity.class));
            }
        });
    }
}