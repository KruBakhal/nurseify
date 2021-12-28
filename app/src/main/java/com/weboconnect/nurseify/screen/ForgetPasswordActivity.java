package com.weboconnect.nurseify.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityForgetPasswordBinding;
import com.weboconnect.nurseify.screen.nurse.model.ResponseModel;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {

    private ActivityForgetPasswordBinding binding;
    private Context context;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ForgetPasswordActivity.this;
        binding = DataBindingUtil.setContentView(ForgetPasswordActivity.this,
                R.layout.activity_forget_password);
        progressDialog = new ProgressDialog(ForgetPasswordActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        binding.editTextEmail.setText(getIntent().getStringExtra(Constant.EMAIL_ID));
        setResult(RESULT_OK);
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perform_Email_Verification();
                Utils.onClickEvent(v);
            }
        });
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Utils.onClickEvent(v);
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Utils.onClickEvent(v);
            }
        });
    }

    private void perform_Email_Verification() {
        if (checkValidation()) {

            if (Utils.isNetworkAvailable(context)) {
                apiCall();
            } else {
                Utils.displayToast(context, getString(R.string.no_internet));
            }

        }
    }

    private void apiCall() {
        Utils.displayToast(context, null); // to cancel toast if showing on screen

        if (!Utils.isNetworkAvailable(context)) {

            Utils.displayToast(context, getResources().getString(R.string.no_internet));
        }
        Utils.displayToast(context, null); // to cancel toast if showing on screen
        progressDialog.show();
        RequestBody requestBody131 = RequestBody.create(MediaType.parse("multipart/form-data"), new SessionManager(context).get_API_KEY());
        RequestBody email_id = RequestBody.create(MediaType.parse("multipart/form-data"), binding.editTextEmail.getText().toString());

        Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_forget_password(email_id);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                assert response.body() != null;
                if (!response.body().getApiStatus().equals("1")) {
                    Utils.displayToast(ForgetPasswordActivity.this, "" + response.body().getMessage());
                    return;
                }
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    ResponseModel responseModel = response.body();
                    if (responseModel != null) {
                        Utils.displayToast(context, null);
                        Utils.displayToast(context, responseModel.getMessage());
                        ForgetPasswordActivity.this.finish();
                    } else {
                        Utils.displayToast(context, "Please Enter Valid Email Id !");
                    }
                } else {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Utils.displayToast(context, "Email verification failed !");
                }

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Utils.displayToast(context, "Something when wrong, please retry again !");
            }
        });

    }

    private boolean checkValidation() {
        String email = binding.editTextEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Utils.displayToast(context, "Enter Email ID");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }
        return true;
    }
}