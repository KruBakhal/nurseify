package com.weboconnect.nurseify.screen.facility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.google.gson.Gson;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityLoginFacilityBinding;
import com.weboconnect.nurseify.screen.ForgetPasswordActivity;
import com.weboconnect.nurseify.screen.facility.model.FacilityLoginModel;
import com.weboconnect.nurseify.screen.nurse.HomeActivity;
import com.weboconnect.nurseify.screen.nurse.LoginActivity;
import com.weboconnect.nurseify.screen.nurse.RegisterActivity;
import com.weboconnect.nurseify.screen.nurse.model.UserProfile;
import com.weboconnect.nurseify.screen.nurse.model.UserProfileData;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFacilityActivity extends AppCompatActivity {

    ActivityLoginFacilityBinding binding;

    ProgressDialog progressDialog;
    private SessionManager sessionManger;

    String TAG = "LoginFacilityActivity ";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(LoginFacilityActivity.this, R.layout.activity_login_facility);
        context = this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("loading...");
        sessionManger = new SessionManager(getApplicationContext());
        vaild_view();
        click();
    }

    private void vaild_view() {
        binding.editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    binding.editTextEmail.setError(null);
                else if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    binding.editTextEmail.setError("Enter Email Id In Proper Format !");
                }
            }
        });

    }

    private void click() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.textForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextEmail.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Utils.displayToast(context, "Enter Email ID First");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).find()) {
                    Utils.displayToast(context, "Enter Email ID In Proper Format !");
                    return;
                }
                Intent i = new Intent(context, ForgetPasswordActivity.class);
                i.putExtra(Constant.EMAIL_ID, email);
                startActivityForResult(i, 123);
                Utils.onClickEvent(v);
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.editTextEmail.getText().toString();
                String password = binding.editTextPassword.getText().toString();

                if (email.equals("")) {
                    binding.editTextEmail.setText("Enter Email");
                } else if (password.equals("")) {
                    binding.editTextPassword.setText("Enter Password");
                } else {
                    logIn();
                }

            }
        });
    }

    private void logIn() {

        progressDialog.show();
        RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), binding.editTextEmail.getText().toString());
        RequestBody password = RequestBody.create(MediaType.parse("multipart/form-data"), binding.editTextPassword.getText().toString());
        RequestBody fcm_token = RequestBody.create(MediaType.parse("multipart/form-data"), "" + Build.ID);
        Call<FacilityLoginModel> call = RetrofitClient.getInstance().getFacilityApi().call_login_check_user(email, password, fcm_token);
        call.enqueue(new Callback<FacilityLoginModel>() {
            @Override
            public void onResponse(Call<FacilityLoginModel> call, Response<FacilityLoginModel> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    FacilityLoginModel profile = response.body();
                    sessionManger.set_TYPE(Constant.CONST_FACULTY_TYPE);
                    sessionManger.setSession_IN_facility(profile.getData().getUserId(),
                            profile.getData().getFacilityId(), profile.getData());

//                    if (profile.getData().getFacilityProfileFlag().equals("0")) {
                    Intent i = new Intent(context, RegistrationFActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(profile.getData()));
                    startActivity(i);

                  /*  } else {
                        Intent i = new Intent(context, HomeFActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }*/

                } else {
                    progressDialog.dismiss();
                    Log.e(TAG + "LogIn code", response.code() + "");
                    Log.e(TAG + "LogIn Msg", response.message());
                }

            }

            @Override
            public void onFailure(Call<FacilityLoginModel> call, Throwable t) {
                Log.e(TAG + "LogIn", t.toString());
                progressDialog.dismiss();
            }
        });

    }

}