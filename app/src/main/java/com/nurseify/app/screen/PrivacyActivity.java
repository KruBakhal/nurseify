package com.nurseify.app.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.nurseify.app.R;
import com.nurseify.app.databinding.ActivityPrivacyBinding;
import com.nurseify.app.screen.nurse.model.PrivacyPolicyModel;
import com.nurseify.app.utils.Utils;
import com.nurseify.app.webService.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyActivity extends AppCompatActivity {

    ActivityPrivacyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(PrivacyActivity.this, R.layout.activity_privacy);
        performTest();
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void performTest() {
        ProgressDialog progressDialog = new ProgressDialog(PrivacyActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        String id = "";

        Call<PrivacyPolicyModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_privacy_policy();

        call.enqueue(new Callback<PrivacyPolicyModel>() {
            @Override
            public void onResponse(Call<PrivacyPolicyModel> call, Response<PrivacyPolicyModel> response) {
                assert response.body() != null;
                if (!response.body().getApiStatus().equals("1")) {
                    progressDialog.dismiss();
                    Utils.displayToast(PrivacyActivity.this, "" + response.body().getMessage());
                    Log.d("TAG", "onResponse: " + response.body().toString());
                    return;
                }
                if (response.isSuccessful()) {

                    progressDialog.dismiss();

                    if (response.body() != null && !TextUtils.isEmpty(response.body().getData()))
                        binding.tvText.setText(Html.fromHtml("" + response.body().getData()));

                } else {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Utils.displayToast(PrivacyActivity.this, response.body().getMessage());
                }

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<PrivacyPolicyModel> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

//                Utils.displayToast(PrivacyActivity.this, "Login Failed, please retry again ");
            }
        });

    }

}