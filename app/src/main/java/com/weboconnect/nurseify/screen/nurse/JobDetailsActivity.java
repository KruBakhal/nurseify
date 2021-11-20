package com.weboconnect.nurseify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.LikeModel;
import com.weboconnect.nurseify.databinding.ActivityJobDetailsBinding;
import com.weboconnect.nurseify.screen.nurse.model.JobModel;
import com.weboconnect.nurseify.screen.nurse.model.JobModel.JobDatum;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailsActivity extends AppCompatActivity {

    ActivityJobDetailsBinding binding;
    private JobDatum model;
    private Context context;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(JobDetailsActivity.this, R.layout.activity_job_details);
        context = this;
        model = new Gson().fromJson(getIntent().getStringExtra(Constant.STR_RESPONSE_DATA), Utils.typeJob);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait");

        setData();

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.layApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                tearmsDialog();
            }
        });
        binding.layLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                performLike(model.getJobId(), model.getIsLiked(), 0, model);
            }
        });
    }

    private void performLike(String jobId, String isLiked, int position, JobModel.JobDatum datum) {

        Utils.displayToast(context, null); // to cancel toast if showing on screen

        if (!Utils.isNetworkAvailable(context)) {
            Utils.displayToast(context, getResources().getString(R.string.no_internet));
            return;
        }
        Utils.displayToast(context, null); // to cancel toast if showing on screen
        progressDialog.show();
        if (isLiked.equals("0")) {
            isLiked = "1";
        } else {
            isLiked = "0";
        }
        String user_id = new SessionManager(context).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody jobId1 = RequestBody.create(MediaType.parse("multipart/form-data"), jobId);
        RequestBody isLiked1 = RequestBody.create(MediaType.parse("multipart/form-data"), isLiked);

    /*    Call<JobModel> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_browser_filter_job(page);*/

        Call<LikeModel> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_like_job(user_id1, jobId1, isLiked1);

        String finalIsLiked = isLiked;
        call.enqueue(new Callback<LikeModel>() {
            @Override
            public void onResponse(Call<LikeModel> call, Response<LikeModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        progressDialog.dismiss();
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        LikeModel jobModel = response.body();
                        Utils.displayToast(context, "" + jobModel.getMessage());
                        datum.setIsLiked(finalIsLiked);

                        if (model.getIsLiked().equals("0")) {
                            binding.imgHeart.setImageResource(R.drawable.heart);
                        } else {
                            binding.imgHeart.setColorFilter(ContextCompat.getColor(context, R.color.black), PorterDuff.Mode.SRC_IN);
                        }

                    } else {
//                        errorProgress(true);
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LikeModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


    }

    private void tearmsDialog() {
        final View loc = getLayoutInflater().from(context).inflate(R.layout.dialog_tearms, null);
        final Dialog dialog = new Dialog(context, R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        ImageView closeDialog = dialog.findViewById(R.id.close_dialog);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView textApply = dialog.findViewById(R.id.text_apply);
        textApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectDate();
                dialog.dismiss();
            }
        });
    }

    private void setData() {
        if (model == null) {
            Utils.displayToast(context, "Empty Data");
            return;
        }
        binding.tvName.setText("" + model.getName());
        binding.tvSpecialty.setText("" + model.getPreferredSpecialtyDefinition());
        binding.tvDate.setText("" + model.getCreatedAtDefinition());
        binding.tvAssignmentDurationDefinition.setText("" + model.getPreferredAssignmentDurationDefinition());
        binding.tvShiftDuration.setText("" + model.getPreferredShiftDurationDefinition());
        binding.tvHourlyRate.setText("$ " + model.getPreferredHourlyPayRate() + "/Hr");

        if (model.getIsLiked().equals("0")) {
            binding.imgHeart.setImageResource(R.drawable.heart);
        } else {
            binding.imgHeart.setColorFilter(ContextCompat.getColor(context, R.color.black), PorterDuff.Mode.SRC_IN);
        }
        if (model.getIsApplied().equals("0")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.layApply.setBackgroundTintList(ContextCompat.getColorStateList(
                        context, R.color.grad1));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.layApply.setBackgroundTintList(ContextCompat.getColorStateList(
                        context, R.color.secondary_till));
            }
        }
        try {
            Glide.with(context).load(model.getFacilityLogo()).into(binding.circleImageView);
        } catch (Exception e) {

        }
        binding.tvDescriptions.setText("" + model.getDescription());
        binding.tvSeniority.setText("" + model.getSeniorityLevelDefinition());
        binding.tvShiftDuration.setText("" + model.getPreferredShiftDurationDefinition());
        binding.tvPrefferedExp.setText("" + model.getPreferredExperience());
        binding.tvCerner.setText("" + model.getJobCernerExpDefinition());
        binding.tvMeditech.setText("" + model.getJobMeditechExpDefinition());
        binding.tvEpic.setText("" + model.getJobEpicExpDefinition());

    }
}