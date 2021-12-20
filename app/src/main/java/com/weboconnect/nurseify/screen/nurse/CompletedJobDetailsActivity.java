package com.weboconnect.nurseify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.bumptech.glide.Glide;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityCompletedJobDetailsBinding;
import com.weboconnect.nurseify.screen.nurse.model.MyJobModel;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletedJobDetailsActivity extends AppCompatActivity {

    ActivityCompletedJobDetailsBinding binding;
    private Context context = CompletedJobDetailsActivity.this;
    private ProgressDialog progressDialog;
    private String job_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(CompletedJobDetailsActivity.this, R.layout.activity_completed_job_details);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        job_id = getIntent().getStringExtra("data");
        fetch_Job_Detail(job_id);
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fetch_Job_Detail(String jobId) {
        if (!Utils.isNetworkAvailable(context)) {
//            Utils.displayToast(context, getResources().getString(R.string.no_internet));
            return;
        }
        Utils.displayToast(context, null); // to cancel toast if showing on screen
        progressDialog.show();
        String user_id = new SessionManager(context).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody jobId1 = RequestBody.create(MediaType.parse("multipart/form-data"), jobId);

        Call<MyJobModel> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_view_job_detail(user_id1, jobId1);

        call.enqueue(new Callback<MyJobModel>() {
            @Override
            public void onResponse(Call<MyJobModel> call, Response<MyJobModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        Utils.displayToast(context,""+response.body().getMessage());
                        progressDialog.dismiss();
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        MyJobModel jobModel = response.body();
                        setupOfferedJobData(jobModel.getData().get(0));
                    } else {
                        Utils.displayToast(context, "Failed to fetch job detail");
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<MyJobModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void setupOfferedJobData(MyJobModel.MyJobDatum jobModel) {
        if (jobModel == null) {
            Utils.displayToast(context, "Empty Data");
            return;
        }
        binding.tvName.setText("" + jobModel.getFacilityName());
        binding.tvSpecialty.setText("" + jobModel.getJobTitle());
        binding.tvDate.setText("" + jobModel.getStartDate());
        binding.tvEndDate.setText("" + jobModel.getEndDate());
        binding.tvAssignmentDurationDefinition.setText("" + jobModel.getAssignmentDurationDefinition());
        binding.tvShiftDuration.setText("" + jobModel.getShiftDefinition());
        binding.tvHourlyRate.setText("$ " + jobModel.getHourlyPayRate() + "/Hr");

        try {
            Glide.with(context).load(jobModel.getFacilityLogo()).into(binding.circleImageView);
        } catch (Exception e) {

        }
        binding.tvDescriptions.setText(Html.fromHtml("" + jobModel.getJobDescription()));
        binding.tvSeniority.setText("" + jobModel.getAboutJob().getSeniorityLevelDefinition());
        binding.tvShiftDuration.setText("" + jobModel.getAboutJob().getPreferredShiftDurationDefinition());
        binding.tvPrefferedExp.setText("" + jobModel.getAboutJob().getPreferredExperience());
        binding.tvCerner.setText("" + jobModel.getAboutJob().getCernerDefinition());
        binding.tvMeditech.setText("" + jobModel.getAboutJob().getMeditechDefinition());
        binding.tvEpic.setText("" + jobModel.getAboutJob().getEpicDefinition());

    }


}