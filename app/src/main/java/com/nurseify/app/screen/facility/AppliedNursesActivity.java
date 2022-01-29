package com.nurseify.app.screen.facility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nurseify.app.R;
import com.nurseify.app.adapter.AppliedNursesAdapter;
import com.nurseify.app.databinding.ActivityAppliedNursesBinding;
import com.nurseify.app.intermediate.ItemCallback;
import com.nurseify.app.screen.facility.model.AppliedNurseModel;
import com.nurseify.app.screen.facility.model.Offered_Job_F_Model;
import com.nurseify.app.utils.Constant;
import com.nurseify.app.utils.SessionManager;
import com.nurseify.app.utils.Utils;
import com.nurseify.app.webService.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppliedNursesActivity extends AppCompatActivity {

    ActivityAppliedNursesBinding binding;
    private String job_id;
    private AppliedNurseModel nurseProfileModel;
    private List<AppliedNurseModel.AppliedNurseDatum> list = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(AppliedNursesActivity.this, R.layout.activity_applied_nurses);
        job_id = getIntent().getStringExtra(Constant.JOB_ID);

        getApplied_Nurse_data();

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getApplied_Nurse_data() {
        if (!Utils.isNetworkAvailable(AppliedNursesActivity.this)) {
            Utils.displayToast(AppliedNursesActivity.this, getResources().getString(R.string.no_internet));
            errorProgress(false);
            return;
        }

        showProgress();

        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), job_id);

        Call<AppliedNurseModel> call = RetrofitClient.getInstance().getFacilityApi()
                .call_applied_nurse(user_id1);

        call.enqueue(new Callback<AppliedNurseModel>() {
            @Override
            public void onResponse(Call<AppliedNurseModel> call, Response<AppliedNurseModel> response) {
                if (response.body() == null) {
                    try {
                        errorProgress(false);
                        Log.d("TAG", "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                if (response.body().getApiStatus().equals("1")) {
                    dismissProgress();
                    nurseProfileModel = response.body();
                    list = new ArrayList<>();
                    list.addAll(nurseProfileModel.getData());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setAdapter();
                        }
                    });
                } else {
//                    Utils.displayToast(AppliedNursesActivity.this, "Data has not been updated");
                    errorProgress(false);
                }

            }

            @Override
            public void onFailure(Call<AppliedNurseModel> call, Throwable t) {
                errorProgress(false);
                Utils.displayToast(AppliedNursesActivity.this, "Failed to get updated data !");
                Log.e("TAG", "onFailure applied nurse " + t.toString());
            }
        });

    }

    private void setAdapter() {
        binding.recyclerView.setAdapter(new AppliedNursesAdapter(AppliedNursesActivity.this, list, new ItemCallback() {
            @Override
            public void onClick(int position) {
                AppliedNurseModel.AppliedNurseDatum datum = list.get(position);
                if (datum != null) {
                    make_offer(datum.getNurseId());
                }
            }

            private void make_offer(String nurseId) {
                if (!Utils.isNetworkAvailable(AppliedNursesActivity.this)) {
                    Utils.displayToast(AppliedNursesActivity.this, getResources().getString(R.string.no_internet));
                    return;
                }
//                showProgress();
                progressDialog = new ProgressDialog(AppliedNursesActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please Wait");
                progressDialog.show();
                String facilityId = new SessionManager(AppliedNursesActivity.this).get_facilityProfile().getUserId();
                RequestBody nurseReques = RequestBody.create(MediaType.parse("multipart/form-data"), nurseId);
                RequestBody facilityReques = RequestBody.create(MediaType.parse("multipart/form-data"), facilityId);
                RequestBody jo_id = RequestBody.create(MediaType.parse("multipart/form-data"), "" + job_id);


                Call<Offered_Job_F_Model> call = RetrofitClient.getInstance().getFacilityApi()
                        .call_send_offer(nurseReques, facilityReques, jo_id);

                call.enqueue(new Callback<Offered_Job_F_Model>() {
                    @Override
                    public void onResponse(Call<Offered_Job_F_Model> call, Response<Offered_Job_F_Model> response) {
                        if (response == null || response.body() == null) {
//                            errorProgress(false);
                            if (progressDialog != null && progressDialog.isShowing())
                                progressDialog.dismiss();
                            Utils.displayToast(AppliedNursesActivity.this, "" + response.message());
                            return;
                        }
                        if (!response.body().getApiStatus().equals("1")) {
                            if (progressDialog != null && progressDialog.isShowing())
                                progressDialog.dismiss();
                            Utils.displayToast(AppliedNursesActivity.this, "" + response.body().getMessage());
                            return;
                        }
                        if (response.isSuccessful()) {
                            if (progressDialog != null && progressDialog.isShowing())
                                progressDialog.dismiss();
                            Utils.displayToast(AppliedNursesActivity.this, response.body().getMessage());
                        } else {
                            Utils.displayToast(AppliedNursesActivity.this, getString(R.string.something_when_wrong));
                            if (progressDialog != null && progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Offered_Job_F_Model> call, Throwable t) {
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();
                        Utils.displayToast(AppliedNursesActivity.this,
                                getString(R.string.something_when_wrong));
                    }
                });
            }

        }));
    }

    private void dismissProgress() {
        binding.layProgress.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.VISIBLE);
    }

    private void errorProgress(boolean status) {
        binding.recyclerView.setVisibility(View.GONE);
        binding.layProgress.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
        if (status)
            binding.tvProgress.setText(getString(R.string.something_when_wrong));
        else
            binding.tvProgress.setText(getString(R.string.no_internet));
    }

    private void showProgress() {
        binding.recyclerView.setVisibility(View.GONE);
        binding.layProgress.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.tvProgress.setText("Please Wait");
    }

}