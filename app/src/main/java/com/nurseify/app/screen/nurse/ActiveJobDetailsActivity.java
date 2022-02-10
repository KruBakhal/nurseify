package com.nurseify.app.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.nurseify.app.R;
import com.nurseify.app.databinding.ActivityActiveJobDetailsBinding;
import com.nurseify.app.screen.nurse.model.MyJobModel;
import com.nurseify.app.utils.SessionManager;
import com.nurseify.app.utils.Utils;
import com.nurseify.app.webService.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiveJobDetailsActivity extends AppCompatActivity {


    ActivityActiveJobDetailsBinding binding;
    private Context context = ActiveJobDetailsActivity.this;
    private ProgressDialog progressDialog;
    private String job_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(ActiveJobDetailsActivity.this, R.layout.activity_active_job_details);

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");

//        ratingDailog();
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

        Call<MyJobModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_view_job_detail(user_id1, jobId1);

        call.enqueue(new Callback<MyJobModel>() {
            @Override
            public void onResponse(Call<MyJobModel> call, Response<MyJobModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        progressDialog.dismiss();
                        Utils.displayToast(context, "" + response.body().getMessage());
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        MyJobModel jobModel = response.body();
                        setupOfferedJobData(jobModel.getData().get(0));
//                        ratingDailog();

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

        binding.tvTitle.setText("" + jobModel.getJobTitle());
        binding.tvName.setText("" + jobModel.getFacilityName());
        binding.tvSpecialty.setText("" + jobModel.getJobTitle());
        binding.tvDate.setText("" + jobModel.getStartDate());
        binding.tvAssignmentDurationDefinition.setText("" + jobModel.getAssignmentDurationDefinition());
        binding.tvShiftDuration.setText("" + jobModel.getShiftDefinition());
        binding.tvHourlyRate.setText("$ " + jobModel.getHourlyPayRate() + "/Hr");

        try {
            if (!TextUtils.isEmpty(jobModel.getFacilityLogo())) {
                byte[] decodeString = Utils.get_base_images(jobModel.getFacilityLogo_base());
                Glide.with(context).load(decodeString).placeholder(R.drawable.person)
                        .error(R.drawable.person).into(binding.circleImageView);
            }
        } catch (Exception e) {

        }
        binding.tvDescriptions.setText(Html.fromHtml("" + jobModel.getJobDescription()));
        binding.tvSeniority.setText("" + jobModel.getAboutJob().getSeniorityLevelDefinition());
        binding.tvShiftDuration.setText("" + jobModel.getAboutJob().getPreferredShiftDurationDefinition());
        binding.tvPrefferedExp.setText("" + jobModel.getAboutJob().getPreferredExperience());
        binding.tvCerner.setText("" + jobModel.getAboutJob().getCernerDefinition());
        binding.tvMeditech.setText("" + jobModel.getAboutJob().getMeditechDefinition());
        binding.tvEpic.setText("" + jobModel.getAboutJob().getEpicDefinition());
        binding.tvAddress.setText("" + jobModel.getPreferredWorkLocationDefinition());

    }


}