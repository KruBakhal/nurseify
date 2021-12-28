package com.weboconnect.nurseify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivitySettingBinding;
import com.weboconnect.nurseify.screen.AboutActivity;
import com.weboconnect.nurseify.screen.LoginSelectActivity;
import com.weboconnect.nurseify.screen.PrivacyActivity;
import com.weboconnect.nurseify.screen.nurse.sample.SampleModel;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;
    private boolean isFirst = true;
    private Context context = SettingActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SettingActivity.this, R.layout.activity_setting);
        if (new SessionManager(context) != null && new SessionManager(context).get_User() != null
                && !TextUtils.isEmpty(new SessionManager(context).get_User().getMobile()))
            binding.edPhone.setText(new SessionManager(context).get_User().getMobile());

        binding.layoutAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
            }
        });
        binding.layoutPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                performTest();
                startActivity(new Intent(SettingActivity.this, PrivacyActivity.class));
            }
        });
        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View loc = getLayoutInflater().from(SettingActivity.this).inflate(R.layout.dialog_reset_phone, null);
                final Dialog dialog = new Dialog(SettingActivity.this, R.style.AlertDialog);
                dialog.setContentView(loc);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.setCancelable(true);
                dialog.show();
                ImageView close = dialog.findViewById(R.id.close_dialog);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                LinearLayout done = dialog.findViewById(R.id.done);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SessionManager(context).setSession_OUT();
                startActivity(new Intent(SettingActivity.this, LoginSelectActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void performTest() {
        ProgressDialog progressDialog = new ProgressDialog(SettingActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        String id = "";
        if (isFirst) {
            isFirst = false;
            id = "sdsd";
        } else {
            isFirst = true;
            id = "";
        }
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), id);


        Call<SampleModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_test(requestBody3);

        call.enqueue(new Callback<SampleModel>() {
            @Override
            public void onResponse(Call<SampleModel> call, Response<SampleModel> response) {
                assert response.body() != null;
                if (!response.body().getApiStatus().equals("1")) {
                    progressDialog.dismiss();
                    Utils.displayToast(context, "" + response.body().getMessage());
                    Log.d("TAG", "onResponse: " + response.body().toString());
                    return;
                }
                if (response.isSuccessful()) {

                    progressDialog.dismiss();

                    Utils.displayToast(context, "Login Successfully Completed");
                    Log.d("TAG", "onResponse: " + response.body().toString());
                } else {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Utils.displayToast(context, response.body().getMessage());
                }

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SampleModel> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                Utils.displayToast(context, "Login Failed, please retry again ");
            }
        });

    }


}