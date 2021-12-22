package com.weboconnect.nurseify.screen.facility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityLoginFacilityBinding;
import com.weboconnect.nurseify.screen.facility.model.FacilityLoginModel;
import com.weboconnect.nurseify.screen.nurse.HomeActivity;
import com.weboconnect.nurseify.screen.nurse.LoginActivity;
import com.weboconnect.nurseify.screen.nurse.RegisterActivity;
import com.weboconnect.nurseify.screen.nurse.model.UserProfile;
import com.weboconnect.nurseify.screen.nurse.model.UserProfileData;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(LoginFacilityActivity.this,R.layout.activity_login_facility);

        progressDialog = new ProgressDialog(LoginFacilityActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("loading...");

        sessionManger = new SessionManager(getApplicationContext());

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.textForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.editTextEmail.getText().toString();
                String password = binding.editTextPassword.getText().toString();

                if (email.equals("")){
                    binding.editTextEmail.setText("Enter Email");
                }
                else if (password.equals("")){
                    binding.editTextPassword.setText("Enter Password");
                }
                else {
                    logIn();
                }

            }
        });

    }

    private void logIn(){

        progressDialog.show();

        RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), binding.editTextEmail.getText().toString());
        RequestBody password = RequestBody.create(MediaType.parse("multipart/form-data"), binding.editTextPassword.getText().toString());
        RequestBody fcm_token = RequestBody.create(MediaType.parse("multipart/form-data"), "" + Build.ID);

        Call<FacilityLoginModel> call = RetrofitClient.getInstance().getFacilityApi().call_login_check_user(email,password,fcm_token);

        call.enqueue(new Callback<FacilityLoginModel>() {
            @Override
            public void onResponse(Call<FacilityLoginModel> call, Response<FacilityLoginModel> response) {

                if (response.isSuccessful()){
                    FacilityLoginModel profile = response.body();
                    sessionManger.set_TYPE(Constant.CONST_FACULTY_TYPE);
                    sessionManger.setSession_IN_facility(profile.getData().getUserId(),profile.getData().getFacilityId(),profile);


                    if (profile.getData().getProfileDetailFlag().equals("0") ||
                            profile.getData().getHourlyRateAndAvailFlag().equals("0")) {

                        Intent i = new Intent(LoginFacilityActivity.this, RegistrationFActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(profile.getData()));
                        startActivity(i);

                    } else {

                        Intent i = new Intent(LoginFacilityActivity.this, HomeFActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }

                }
                else {
                    progressDialog.dismiss();
                    Log.e(TAG+"LogIn code",response.code()+"");
                    Log.e(TAG+"LogIn Msg",response.message());
                }

            }

            @Override
            public void onFailure(Call<FacilityLoginModel> call, Throwable t) {
                Log.e(TAG+"LogIn",t.toString());
                progressDialog.dismiss();
            }
        });

    }

}