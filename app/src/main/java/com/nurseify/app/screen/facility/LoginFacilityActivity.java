package com.nurseify.app.screen.facility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.google.gson.Gson;
import com.nurseify.app.R;
import com.nurseify.app.databinding.ActivityLoginFacilityBinding;
import com.nurseify.app.screen.ForgetPasswordActivity;
import com.nurseify.app.screen.facility.model.FacilityLoginModel;
import com.nurseify.app.utils.Constant;
import com.nurseify.app.utils.SessionManager;
import com.nurseify.app.utils.Utils;
import com.nurseify.app.webService.RetrofitClient;

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
    private boolean isPassVisible = false;

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
                /*if (s == null || s.length() == 0)
                    binding.editTextEmail.setError(null);
                else if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    binding.editTextEmail.setError("Enter Email Id In Proper Format !");
                }*/
            }
        });
        binding.imgPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!TextUtils.isEmpty(binding.editTextPassword.getText().toString())) {
                if (isPassVisible) {
                    binding.editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                        binding.imgPass.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    binding.imgPass.setImageResource(R.drawable.eye_off_outline);
                    isPassVisible = false;
                } else {
                    binding.editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    binding.imgPass.setImageResource(R.drawable.ey_outline);
                    isPassVisible = true;

                }
                if (!TextUtils.isEmpty(binding.editTextPassword.getText().toString()))
                    binding.editTextPassword.setSelection(binding.editTextPassword.getText().length());
                Utils.onClickEvent(v);
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

               /* if (email.equals("")) {
                    binding.editTextEmail.setText("Enter Email");
                } else if (password.equals("")) {
                    binding.editTextPassword.setText("Enter Password");
                } else {

                }
               */
                if (checkValidation())
                    if (Utils.isNetworkAvailable(context)) {
                        logIn();
                    } else {
                        Utils.displayToast(context, context.getResources().getString(R.string.no_internet));
                    }

            }
        });
    }

    private boolean checkValidation() {
        String email = binding.editTextEmail.getText().toString();
        String pasas = binding.editTextPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Utils.displayToast(context, "Enter Email ID");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Utils.displayToast(context, "Enter Email Id In Proper Format");
            return false;
        }
        if (TextUtils.isEmpty(pasas)) {
            Utils.displayToast(context, "Enter Password");
            return false;
        }

        return true;
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
                assert response.body() != null;
                if (!response.body().getApiStatus().equals("1")) {
                    progressDialog.dismiss();
                    Utils.displayToast(context, "" + response.body().getMessage());
                    return;
                }
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    FacilityLoginModel profile = response.body();
                    if (profile != null && profile.getData() != null) {
                        if (!TextUtils.isEmpty(profile.getData().getRole()) &&
                                !profile.getData().getRole().equals("NURSE")) {

                            sessionManger.set_TYPE(Constant.CONST_FACULTY_TYPE);
                            sessionManger.setSession_IN_facility(profile.getData().getUserId(),
                                    profile.getData().getFacilityId(), profile.getData());

                            if (profile.getData().getFacilityProfileFlag().equals("0")) {
                                Intent i = new Intent(context, RegistrationFActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i.putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(profile.getData()));
                                startActivity(i);

                            } else {
                                Intent i = new Intent(context, HomeFActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                        } else {
                            Utils.displayToast(context, "Invalid email or password");
                        }
                    } else
                        Utils.displayToast(context, context.getResources().getString(R.string.something_when_wrong));
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