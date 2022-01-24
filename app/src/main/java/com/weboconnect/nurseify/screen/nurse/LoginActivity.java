package com.weboconnect.nurseify.screen.nurse;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityLoginBinding;
import com.weboconnect.nurseify.screen.ForgetPasswordActivity;
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
        progressDialog.setMessage("Please Wait");
        sessionManger = new SessionManager(context);

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peformLoginProcess();
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
                startActivityForResult(i, 123);
                Utils.onClickEvent(v);
            }
        });
        binding.tvForget.setOnClickListener(new View.OnClickListener() {
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
                Intent i = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                i.putExtra(Constant.EMAIL_ID, email);
                startActivityForResult(i, 123);
                Utils.onClickEvent(v);
            }
        });
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

    private void permissionSetup() {
        if (!checkReadExternal()) {
            requestPermission();
            return;
        }
    }

    private boolean checkReadExternal() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 123:
                if (grantResults.length > 0) {
                    boolean READ_EXTERNAL_STORAGE = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WRITE_EXTERNAL_STORAGE = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (READ_EXTERNAL_STORAGE && WRITE_EXTERNAL_STORAGE) {
                        // perform action when allow permission success
                        Utils.displayToast(context, "Permission Allowed");
                    } else {
                        Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                        permissionSetup();
                    }
                }
                break;
        }
    }

    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                startActivityForResult(intent, 229);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 229);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{READ_EXTERNAL_STORAGE,
                            WRITE_EXTERNAL_STORAGE},
                    123);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            binding.editTextEmail.setText("");
            binding.editTextPassword.setText("");
            binding.editTextEmail.clearFocus();
            binding.editTextPassword.clearFocus();
        } else if (requestCode == 229) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    Utils.displayToast(context, "Permission Allowed");
                } else {
                    Toast.makeText(this, "Please, Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                    permissionSetup();
                }
            }
        }
    }

    private void peformLoginProcess() {
        Utils.hideKeyboardFrom(context, binding.login);
        if (checkValidation()) {
            Utils.displayToast(context, null); // to cancel toast if showing on screen

            if (!Utils.isNetworkAvailable(context)) {

                Utils.displayToast(context, getResources().getString(R.string.no_internet));
                return;
            }
            Utils.displayToast(context, null); // to cancel toast if showing on screen

            progressDialog.show();
            RequestBody requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), binding.editTextEmail.getText().toString());
            RequestBody requestBody13 = RequestBody.create(MediaType.parse("multipart/form-data"), binding.editTextPassword.getText().toString());
            RequestBody requestBody131 = RequestBody.create(MediaType.parse("multipart/form-data"), "1a2b3c4d5e6f7g8h9ij10" );


            Call<UserProfile> call = RetrofitClient.getInstance().getNurseRetrofitApi()
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
                    Log.d("TAG", "onFailure: " + t.getMessage());
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
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }
        if (TextUtils.isEmpty(pasas)) {
            Utils.displayToast(context, "Enter Password");
            return false;
        }

        return true;
    }

}