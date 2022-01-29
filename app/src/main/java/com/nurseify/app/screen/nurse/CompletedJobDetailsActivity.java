package com.nurseify.app.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nurseify.app.R;
import com.nurseify.app.databinding.ActivityCompletedJobDetailsBinding;
import com.nurseify.app.databinding.DialogRatingBinding;
import com.nurseify.app.screen.nurse.adapters.RatingAdapter;
import com.nurseify.app.screen.nurse.model.MyJobModel;
import com.nurseify.app.screen.nurse.model.ResponseModel;
import com.nurseify.app.utils.SessionManager;
import com.nurseify.app.utils.Utils;
import com.nurseify.app.webService.RetrofitClient;

import java.util.ArrayList;

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
    public String selected_onBoard;
    public String selected_onNurse;
    public String selected_onLeader;
    public String selected_onTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(CompletedJobDetailsActivity.this, R.layout.activity_completed_job_details);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        binding.layReview.setVisibility(View.GONE);
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
                        Utils.displayToast(context, "" + response.body().getMessage());
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
        binding.tvTitle.setText("" + jobModel.getJobTitle());
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
        binding.tvAddress.setText("" + jobModel.getPreferredWorkLocationDefinition());
        if (jobModel.getRating_flag().equals("0")) {
            ratingDailog();
        } else {
            if (jobModel.getRating() != null) {
                binding.layReview.setVisibility(View.VISIBLE);
                MyJobModel.MyJobDatum.RatingComment ratingComment = jobModel.getRatingComment();
                try {
                    Glide.with(this).load(ratingComment.getNurseImage())
                            .placeholder(R.drawable.person).error(R.drawable.person)
                            .into(binding.imgProfile);
                } catch (Exception e) {

                }
                binding.tvFacilityName.setText(ratingComment.getNurseName());
                binding.tvRating.setText(ratingComment.getRating());
                if (!TextUtils.isEmpty(ratingComment.getRating()))
                    binding.reatingBar.setRating(Float.parseFloat(ratingComment.getRating()));
                binding.tvReview.setText(ratingComment.getExperience());
            } else {
                binding.layReview.setVisibility(View.GONE);
            }
        }
    }

    private void ratingDailog() {
//        final View loc = getLayoutInflater().from(CompletedJobDetailsActivity.this).inflate(R.layout.dialog_rating, null);
        DialogRatingBinding dialogRatingBinding = DialogRatingBinding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(CompletedJobDetailsActivity.this, R.style.AlertDialog);
        dialog.setContentView(dialogRatingBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);

        dialogRatingBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ArrayList<String> onBoard = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            onBoard.add("" + (i + 1));
        }
        RatingAdapter ratingAdapter1 = new RatingAdapter(this, 1, onBoard);
        dialogRatingBinding.rvOnBoard.setAdapter(ratingAdapter1);
        RatingAdapter ratingAdapter2 = new RatingAdapter(this, 2, onBoard);
        dialogRatingBinding.rvNurse.setAdapter(ratingAdapter2);
        RatingAdapter ratingAdapter3 = new RatingAdapter(this, 3, onBoard);
        dialogRatingBinding.rvLeader.setAdapter(ratingAdapter3);
        RatingAdapter ratingAdapter4 = new RatingAdapter(this, 4, onBoard);
        dialogRatingBinding.rvTools.setAdapter(ratingAdapter4);


        dialogRatingBinding.textSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    if (Utils.isNetworkAvailable(context)) {
                        performRating_Call((int) dialogRatingBinding.reatingBar.getRating(),
                                dialogRatingBinding.edReview.getText().toString());
                    } else {
                        Utils.displayToast(context, getResources().getString(R.string.no_internet));
                    }
                }
//                dialog.dismiss();
            }

            private boolean checkValidation() {
                if (dialogRatingBinding.reatingBar.getRating() == 0) {
                    Utils.displayToast(context, "Please give rating to facility first !");
                    return false;
                }
                if (TextUtils.isEmpty(selected_onBoard)) {
                    Utils.displayToast(context, "Please Rate Onboarding !");
                    return false;
                }
                if (TextUtils.isEmpty(selected_onNurse)) {
                    Utils.displayToast(context, "Please Rate Nurse Teamwork  !");
                    return false;
                }
                if (TextUtils.isEmpty(selected_onLeader)) {
                    Utils.displayToast(context, "Please Rate Leadership Support  !");
                    return false;
                }
                if (TextUtils.isEmpty(selected_onTool)) {
                    Utils.displayToast(context, "Please Rate Tools to do my job   !");
                    return false;
                }
                if (TextUtils.isEmpty(dialogRatingBinding.edReview.getText().toString())) {
                    Utils.displayToast(context, "Please, enter your experience !");
                    return false;
                }

                return true;
            }

            private void performRating_Call(int rating, String review) {
                if (!Utils.isNetworkAvailable(context)) {
                    Utils.displayToast(context, getResources().getString(R.string.no_internet));
                    return;
                }
                Utils.displayToast(context, null); // to cancel toast if showing on screen
//                progressDialog.show();
                String user_id = new SessionManager(context).get_user_register_Id();
                RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
                RequestBody user_id2 = RequestBody.create(MediaType.parse("multipart/form-data"), job_id);
                RequestBody user_id3 = RequestBody.create(MediaType.parse("multipart/form-data"), "" + rating);
                RequestBody user_id4 = RequestBody.create(MediaType.parse("multipart/form-data"), selected_onBoard);
                RequestBody user_id5 = RequestBody.create(MediaType.parse("multipart/form-data"), selected_onNurse);
                RequestBody user_id6 = RequestBody.create(MediaType.parse("multipart/form-data"), selected_onLeader);
                RequestBody user_id7 = RequestBody.create(MediaType.parse("multipart/form-data"), selected_onTool);
                RequestBody user_id8 = RequestBody.create(MediaType.parse("multipart/form-data"), review);

                Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                        .call_facility_rating(user_id1, user_id2, user_id3, user_id4, user_id5, user_id6, user_id7, user_id8);

                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        try {
                            assert response.body() != null;
                            if (!response.body().getApiStatus().equals("1")) {
//                                progressDialog.dismiss();
                                return;
                            }
                            if (response.isSuccessful()) {
//                                progressDialog.dismiss();
                                ResponseModel jobModel = response.body();
                                Utils.displayToast(context, jobModel.getMessage());
                                dialog.dismiss();
                                ratingDailogDone();

                            } else {
                                Utils.displayToast(context, "Failed to submit your rating details");
//                                progressDialog.dismiss();
                            }
                        } catch (Exception e) {
//                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
//                        progressDialog.dismiss();
                    }
                });
            }

        });
        dialog.show();
    }


    private void ratingDailogDone() {
        final View loc = getLayoutInflater().from(CompletedJobDetailsActivity.this).inflate(R.layout.dialog_rating_done, null);
        final Dialog dialog = new Dialog(CompletedJobDetailsActivity.this, R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        TextView text_ok = dialog.findViewById(R.id.text_ok);
        text_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                dialog.dismiss();
                //  fetch_Job_Detail(job_id);
            }
        });
    }
}