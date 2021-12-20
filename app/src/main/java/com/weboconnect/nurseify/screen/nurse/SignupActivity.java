package com.weboconnect.nurseify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.View;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivitySignupBinding;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.internal.Util;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    Context context;
    Pattern pattern = Pattern.compile("^[A-z][A-z]*$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SignupActivity.this, R.layout.activity_signup);
        context = this;
        setResult(RESULT_OK);
        click();
    }

    private void click() {

        binding.edFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    binding.edFirstName.setError(null);
                else if (!pattern.matcher(s.toString()).find() || s.toString().length() < 3) {
                    String estring = "First Name Can Contain Only Alphabet & No Space and Min 3 Letter Required!";
                    binding.edFirstName.setError(estring);
                } else {
                    binding.edFirstName.setError(null);
                }
            }
        });
        binding.edLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    binding.edLastName.setError(null);
                else if (!pattern.matcher(s.toString()).find() || s.toString().length() < 3) {
                    String estring = "Last Name Can Contain Only Alphabet & No Space and Min 3 Letter Required!";
                    binding.edLastName.setError(estring);
                } else {
                    binding.edLastName.setError(null);
                }
            }
        });
        binding.edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    binding.edEmail.setError(null);
                else if (!isValidEmail(s.toString())) {
                    binding.edEmail.setError("Enter Email Id In Proper Format !");
                } else {
                    binding.edEmail.setError(null);
                }
            }
        });
        binding.edPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    binding.edPhone.setError(null);
                else if (s.toString().length() < 10) {
                    binding.edPhone.setError("Enter Phone Nos Properly. Min 10 Digits Are Required");
                } else {
                    binding.edPhone.setError(null);
                }
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
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
                    startActivity(new Intent(SignupActivity.this, SignupDetailsActivity.class)
                            .putExtra(Constant.FIRST_NAME, edFirstName)
                            .putExtra(Constant.LAST_NAME, edLastName)
                            .putExtra(Constant.MOBILE, mobile)
                            .putExtra(Constant.EMAIL_ID, email));
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

    public boolean isValidEmail(String target) {
        String pattern = "^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)$";
        return target.matches(pattern);
    }

    private boolean check_validation() {

        String edFirstName = binding.edFirstName.getText().toString();
        String edLastName = binding.edLastName.getText().toString();
        String email = binding.edEmail.getText().toString();
        String mobile = binding.edPhone.getText().toString();

//                Pattern pattern = Pattern.compile("/^[A-Za-z]+$/");
        Matcher matcher1 = pattern.matcher(edFirstName);
        Matcher matcher2 = pattern.matcher(edLastName);


        if (TextUtils.isEmpty(edFirstName)) {
            Utils.displayToast(context, "Enter First Name !");
            return false;
        }
        if (!matcher1.find() || edFirstName.length() < 3) {
            Utils.displayToast(context, "Enter First Name Proper Format. First Name Must Contain Only Alphabet, No Space and min 3 letter required !");
            return false;
        }
        if (TextUtils.isEmpty(edLastName)) {
            Utils.displayToast(context, "Enter Last Name !");
            return false;
        }
        if (!matcher2.find() || edLastName.length() < 3) {
            Utils.displayToast(context, "Enter Last Name Proper Format. Last Name Must Contain Only Alphabet, No Space and min 3 letter required !");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            Utils.displayToast(context, "Enter Email ID");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Utils.displayToast(context, "Enter Email In Proper Format !");
            return false;
        }

        if (TextUtils.isEmpty(mobile) ||
                (mobile.contains("+") && (mobile.length() < 13 || mobile.length() > 13)) ||
                (!mobile.contains("+") && (mobile.length() < 10 || mobile.length() > 10))) {
            Utils.displayToast(context, "Enter Valid Mobile Nos");
            return false;
        }

        return true;
    }
}