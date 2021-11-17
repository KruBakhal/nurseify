package com.weboconnect.nurseify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import com.google.gson.Gson;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityLoginBinding;
import com.weboconnect.nurseify.screen.nurse.model.UserProfile;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private Context context;
    private ProgressDialog progressDialog;
    private SessionManager sessionManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        context = this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        sessionManger = new SessionManager(context);
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(context)) {
                    peformLoginProcess();
                } else {
                    Utils.displayToast(context, getResources().getString(R.string.no_internet));
                }
                Utils.onClickEvent(v);
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
                Utils.onClickEvent(v);
            }
        });
    }

    private void peformLoginProcess() {
        Utils.hideKeyboardFrom(context, binding.login);
        if (checkValidation()) {
            Utils.displayToast(context, null); // to cancel toast if showing on screen

            if (!Utils.isNetworkAvailable(context)) {

                Utils.displayToast(context, getResources().getString(R.string.no_internet));
            }
            Utils.displayToast(context, null); // to cancel toast if showing on screen

            progressDialog.show();
            RequestBody requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), binding.editTextEmail.getText().toString());
            RequestBody requestBody13 = RequestBody.create(MediaType.parse("multipart/form-data"), binding.editTextPassword.getText().toString());
            RequestBody requestBody131 = RequestBody.create(MediaType.parse("multipart/form-data"), sessionManger.get_API_KEY());


            Call<UserProfile> call = RetrofitClient.getInstance().getRetrofitApi()
                    .call_login_check_user(requestBody3, requestBody13, requestBody131);

            call.enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        progressDialog.dismiss();
                        Utils.displayToast(context, "" + response.body().getMessage());
                        return;
                    }
                    if (response.isSuccessful()) {

                        progressDialog.dismiss();
                        UserProfile profile = response.body();
                        sessionManger.set_TYPE(Constant.CONST_NURSE_TYPE);
                        sessionManger.setSession_IN(profile.getData().getId(),
                                profile.getData());

                        Utils.displayToast(context, "Login Successfully Completed");
                        if (profile.getData().getProfileDetailFlag().equals("0") ||
                                profile.getData().getHourlyRateAndAvailFlag().equals("0")) {

                            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(profile.getData()));
                            startActivity(i);

                        } else {

                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }
                        LoginActivity.this.finish();

                    } else {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Utils.displayToast(context, response.body().getMessage());
                    }

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<UserProfile> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Utils.displayToast(context, "Login Failed, please retry again ");
                }
            });


        }
    }

    private boolean checkValidation() {
        String email = binding.editTextEmail.getText().toString();
        String pasas = binding.editTextPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Utils.displayToast(context, "Enter Email ID");
            return false;
        }
        if (TextUtils.isEmpty(pasas)) {
            Utils.displayToast(context, "Enter Password");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }
        return true;
    }

}