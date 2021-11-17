package com.weboconnect.nurseify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivitySignupBinding;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.Utils;

import okhttp3.internal.Util;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SignupActivity.this, R.layout.activity_signup);
        context = this;
        click();
    }

    private void click() {
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_validation()) {
                    String edFirstName = binding.edFirstName.getText().toString();
                    String edLastName = binding.edLastName.getText().toString();
                    String email = binding.edEmail.getText().toString();
                    String mobile = binding.edPhone.getText().toString();
                    Utils.displayToast(context, null);
                    if (!Utils.isNetworkAvailable(context)) {
                        Utils.displayToast(context, getResources().getString(R.string.no_internet));
                        return;
                    }
                    Utils.displayToast(context, null);
                    startActivity(new Intent(SignupActivity.this,
                            SignupDetailsActivity.class)
                            .putExtra(Constant.FIRST_NAME, edFirstName)
                            .putExtra(Constant.LAST_NAME, edLastName)
                            .putExtra(Constant.MOBILE, mobile)
                            .putExtra(Constant.EMAIL_ID, email)
                    );
                }
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean check_validation() {

        String edFirstName = binding.edFirstName.getText().toString();
        String edLastName = binding.edLastName.getText().toString();
        String email = binding.edEmail.getText().toString();
        String mobile = binding.edPhone.getText().toString();

        if (TextUtils.isEmpty(edFirstName)) {
            Utils.displayToast(context, "Enter First Name !");
            return false;
        }
        if (TextUtils.isEmpty(edLastName)) {
            Utils.displayToast(context, "Enter Last Name !");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            Utils.displayToast(context, "Enter Email ID");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Utils.displayToast(context, "Enter Proper Email Id");
            return false;
        }

        if (TextUtils.isEmpty(mobile) ||
                (mobile.contains("+") && (mobile.length() < 13 || mobile.length() > 13)) ||
                (!mobile.contains("+") && (mobile.length() < 10 || mobile.length() > 10))) {
            Utils.displayToast(SignupActivity.this, "Enter Valid Mobile Nos");
            return false;
        }

        return true;
    }
}