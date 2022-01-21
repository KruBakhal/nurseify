package com.weboconnect.nurseify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityJobDetailsBinding;
import com.weboconnect.nurseify.screen.nurse.model.FacilityJobModel;
import com.weboconnect.nurseify.screen.nurse.model.JobModel;
import com.weboconnect.nurseify.screen.nurse.model.JobModel.JobDatum;
import com.weboconnect.nurseify.screen.nurse.model.MyJobModel;
import com.weboconnect.nurseify.screen.nurse.model.PrivacyPolicyModel;
import com.weboconnect.nurseify.screen.nurse.model.ResponseModel;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Browse_Facility_Offered_JobDetailsActivity extends AppCompatActivity {

    ActivityJobDetailsBinding binding;
    private JobDatum model;
    private FacilityJobModel.Facility facility;
    private Context context;
    private ProgressDialog progressDialog;
    String user_id;
    String TAG = "JobDetailsActivity ";
    int flag = 1;    //1=browse job,  2= facility , 3= offered job
    String job_id;
    private String str_terms_conditions = "";
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(Browse_Facility_Offered_JobDetailsActivity.this, R.layout.activity_job_details);
        context = this;

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");

        if (getIntent().hasExtra(Constant.FLAG)) {
            flag = getIntent().getIntExtra(Constant.FLAG, 1);
        }
        setResult(RESULT_CANCELED);
        switch (flag) {
            case 1:
                model = new Gson().fromJson(getIntent().getStringExtra(Constant.STR_RESPONSE_DATA), Utils.typeJob);
                position = getIntent().getIntExtra(Constant.POSITION, 0);
                setJobData();
                break;
            case 2:
                Type type = new TypeToken<FacilityJobModel.Facility>() {
                }.getType();
                facility = new Gson().fromJson(getIntent().getStringExtra("data"), type);
                binding.tvApplied.setText("Follow");
                setFacilityData();
                break;

            case 3:
                job_id = getIntent().getStringExtra("data");
                binding.layoutLikeShare.setVisibility(View.GONE);
                binding.layoutAccept.setVisibility(View.VISIBLE);
                fetch_Job_Detail(job_id);
                break;
            case 4:
//                job_id = getIntent().getStringExtra("data");
//                fetch_Job_Detail(job_id);
                break;
            case 5:
//                job_id = getIntent().getStringExtra("data");
//                fetch_Job_Detail(job_id);
                break;

        }
        click();

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

    private void click() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.layApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 1) {
                    if (model.getIsApplied().equals("1")) {
                        performApply(model, position);
                    } else /*if (TextUtils.isEmpty(str_terms_conditions))
//                            fetch_terms_conditions(datum, position);
                        else {
                        }*/
                        terms_conditions_Dialog(model, str_terms_conditions, position);
                } else if (flag == 2) {
                    followFacility(facility.getId(), facility.getFacilityType().toString());
                }

            }
        });

        binding.layLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1) {
                    performLike(model.getJobId(), model.getIsLiked(), position);
                } else if (flag == 2) {
                    if (facility.getIsLike().equals("1")) {
                        likeFacility(facility.getId(), "0");
                    } else {
                        likeFacility(facility.getId(), "1");
                    }
                }
            }
        });

        binding.textAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offeredJobAccept(job_id);
            }
        });

        binding.textReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offeredJobReject(job_id);
            }
        });
    }


    private void fetch_terms_conditions(JobModel.JobDatum datum, int position) {
        progressDialog.show();
        String id = "";
        Call<PrivacyPolicyModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_terms_conditions();

        call.enqueue(new Callback<PrivacyPolicyModel>() {
            @Override
            public void onResponse(Call<PrivacyPolicyModel> call, Response<PrivacyPolicyModel> response) {
                assert response.body() != null;
                if (!response.body().getApiStatus().equals("1")) {
                    progressDialog.dismiss();
                    Utils.displayToast(context, "" + response.body().getMessage());
                    Log.d("TAG", "onResponse: " + response.body().toString());
                    return;
                }
                if (response.isSuccessful()) {

                    progressDialog.dismiss();
                    if (response.body() != null && !TextUtils.isEmpty(response.body().getData())) {
                        //                   binding.tvText.setText(Html.fromHtml("" + response.body().getData()));
                        str_terms_conditions = response.body().getData();
                        terms_conditions_Dialog(datum, response.body().getData(), position);
                    } else {
                        Utils.displayToast(context, "Retry again, couldn't able to fetch data !");
                    }
                } else {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Utils.displayToast(context, response.body().getMessage());
                }

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<PrivacyPolicyModel> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

//                Utils.displayToast(JobDetailsActivity.this, "Login Failed, please retry again ");
            }
        });

    }

    private void performLike(String jobId, String isLiked, int position) {

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


        Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_like_job(user_id1, jobId1, isLiked1);

        String finalIsLiked = isLiked;
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        progressDialog.dismiss();
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        ResponseModel jobModel = response.body();
                        Utils.displayToast(context, "" + jobModel.getMessage());
                        model.setIsLiked(finalIsLiked);
                        setResult(RESULT_OK, new Intent()
                                .putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(model))
                                .putExtra(Constant.POSITION, position));

                        if (model.getIsLiked().toString().equals("0")) {
                            binding.imgHeart.setVisibility(View.VISIBLE);
                            binding.imgHeart1.setVisibility(View.GONE);
                        } else {
                            binding.imgHeart1.setVisibility(View.VISIBLE);
                            binding.imgHeart.setVisibility(View.GONE);
                        }

                    } else {
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


    }


    private void terms_conditions_Dialog(JobModel.JobDatum datum, String str_terms_conditions, int position) {
        final View loc = getLayoutInflater().from(context).inflate(R.layout.dialog_tearms, null);
        final Dialog dialog = new Dialog(context, R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        ImageView closeDialog = dialog.findViewById(R.id.close_dialog);
        TextView tv_text = dialog.findViewById(R.id.tv_text);
        View sdsds = dialog.findViewById(R.id.sdsds);
//        tv_text.setText(Html.fromHtml(str_terms_conditions));
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        tv_text.setText(Constant.URL_TERMS_CONDITION);
        sdsds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                performApply(datum, position);

                dialog.dismiss();
            }
        });
        tv_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(Constant.URL_TERMS_CONDITION);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
    }

    private void performApply(JobModel.JobDatum datum, int position) {
        Utils.displayToast(context, null); // to cancel toast if showing on screen

        if (!Utils.isNetworkAvailable(context)) {
            Utils.displayToast(context, getResources().getString(R.string.no_internet));
            return;
        }
        Utils.displayToast(context, null); // to cancel toast if showing on screen
        progressDialog.show();
        String isApplied = datum.getIsApplied();
        if (isApplied.equals("0")) {
            isApplied = "1";
        } else {
            isApplied = "0";
        }
        user_id = new SessionManager(context).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody jobId1 = RequestBody.create(MediaType.parse("multipart/form-data"), datum.getJobId());
        RequestBody isLiked1 = RequestBody.create(MediaType.parse("multipart/form-data"), isApplied);

        Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_job_applied(user_id1, jobId1, isLiked1);

        String finalIsApplied = isApplied;
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        progressDialog.dismiss();
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        ResponseModel jobModel = response.body();
                        Utils.displayToast(context, "" + jobModel.getMessage());
                        model.setIsApplied(finalIsApplied);
                        setResult(RESULT_OK, new Intent()
                                .putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(model))
                                .putExtra(Constant.POSITION, position));
//                        list_jobs.set(position, datum);
//                        browserJobsAdapter.notifyItemChanged(position);
                        if (finalIsApplied.equals("0")) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                binding.layApply.setBackgroundTintList(ContextCompat.getColorStateList(
                                        context, R.color.grad1));
                            }
                            binding.tvApplied.setText("Apply");
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                binding.layApply.setBackgroundTintList(ContextCompat.getColorStateList(
                                        context, R.color.secondary_till));
                            }
                            binding.tvApplied.setText("Applied");
                        }
                    } else {
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d("TAG_performApply()", "onFailure: " + t.getMessage());
                progressDialog.dismiss();

            }
        });

    }

    private void setJobData() {
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
        binding.tvCreatedAtDefinition.setText("" + model.getCreatedAtDefinition());

        if (model.getIsLiked().toString().equals("0")) {
            binding.imgHeart.setVisibility(View.VISIBLE);
            binding.imgHeart1.setVisibility(View.GONE);
        } else {
            binding.imgHeart1.setVisibility(View.VISIBLE);
            binding.imgHeart.setVisibility(View.GONE);
        }

        if (model.getIsApplied().equals("0")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.layApply.setBackgroundTintList(ContextCompat.getColorStateList(
                        context, R.color.grad1));
            }
            binding.tvApplied.setText("Apply");
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.layApply.setBackgroundTintList(ContextCompat.getColorStateList(
                        context, R.color.secondary_till));
            }
            binding.tvApplied.setText("Applied");
        }
        try {
            Glide.with(context).load(model.getFacilityLogo()).into(binding.circleImageView);
        } catch (Exception e) {

        }
        binding.tvDescriptions.setText("" + Html.fromHtml(model.getDescription()));
        binding.tvSeniority.setText("" + model.getSeniorityLevelDefinition());
        binding.tvShiftDuration.setText("" + model.getPreferredShiftDurationDefinition());
        binding.tvPrefferedExp.setText("" + model.getPreferredExperience());
        binding.tvCerner.setText("" + model.getJobCernerExpDefinition());
        binding.tvMeditech.setText("" + model.getJobMeditechExpDefinition());
        binding.tvEpic.setText("" + model.getJobEpicExpDefinition());

    }

    private void setFacilityData() {
        if (facility == null) {
            Utils.displayToast(context, "Empty Data");
            return;
        }
        binding.tvName.setText("" + facility.getName());
        binding.tvSpecialty.setText("" + facility.getPreferredSpecialty());
        binding.tvDate.setText("" + facility.getCreatedAt());
        binding.tvAssignmentDurationDefinition.setText("");
        binding.tvShiftDuration.setText("");
//        binding.tvHourlyRate.setText("$ "  + "/Hr");

        if (facility.getIsLike().toString().equals("0")) {
            binding.imgHeart.setVisibility(View.VISIBLE);
            binding.imgHeart1.setVisibility(View.GONE);
        } else {
            binding.imgHeart1.setVisibility(View.VISIBLE);
            binding.imgHeart.setVisibility(View.GONE);
        }

        if (facility.getIsFollow().toString().equals("0")) {
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
            Glide.with(context).load(facility.getFacilityLogo()).into(binding.circleImageView);
        } catch (Exception e) {

        }
        binding.tvDescriptions.setText(Html.fromHtml(facility.getAboutFacility()));
        binding.tvSeniority.setText("");
        binding.tvShiftDuration.setText("");
        binding.tvPrefferedExp.setText("");
        binding.tvCerner.setText("");
        binding.tvMeditech.setText("");
        binding.tvEpic.setText("");

    }

    private void setupOfferedJobData(MyJobModel.MyJobDatum jobModel) {
        if (jobModel == null) {
            Utils.displayToast(context, "Empty Data");
            return;
        }
        binding.tvName.setText("" + jobModel.getFacilityName());
        binding.tvSpecialty.setText("" + jobModel.getJobTitle());
        binding.tvDate.setText("" + jobModel.getStartDate());
        binding.tvAssignmentDurationDefinition.setText("" + jobModel.getAssignmentDurationDefinition());
        binding.tvShiftDuration.setText("" + jobModel.getShiftDefinition());
        binding.tvHourlyRate.setText("$ " + jobModel.getHourlyPayRate() + "/Hr");

        try {
            Glide.with(context).load(jobModel.getFacilityLogo()).into(binding.circleImageView);
        } catch (Exception e) {

        }
        binding.tvDescriptions.setText("" + jobModel.getJobDescription());
        binding.tvSeniority.setText("" + jobModel.getAboutJob().getSeniorityLevelDefinition());
        binding.tvShiftDuration.setText("" + jobModel.getAboutJob().getPreferredShiftDurationDefinition());
        binding.tvPrefferedExp.setText("" + jobModel.getAboutJob().getPreferredExperience());
        binding.tvCerner.setText("" + jobModel.getAboutJob().getCernerDefinition());
        binding.tvMeditech.setText("" + jobModel.getAboutJob().getMeditechDefinition());
        binding.tvEpic.setText("" + jobModel.getAboutJob().getEpicDefinition());

    }

    private void followFacility(String facilityId, String type) {

        Utils.displayToast(getApplicationContext(), null); // to cancel toast if showing on screen

        if (!Utils.isNetworkAvailable(getApplicationContext())) {
            Utils.displayToast(getApplicationContext(), getResources().getString(R.string.no_internet));
            return;
        }

        progressDialog.show();

        user_id = new SessionManager(getApplicationContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody facility_id = RequestBody.create(MediaType.parse("multipart/form-data"), facilityId);
        RequestBody type_ = RequestBody.create(MediaType.parse("multipart/form-data"), type);


        Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_follow_facility(user_id1, facility_id, type_);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();


                    } else {
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });


    }

    private void likeFacility(String facilityId, String like) {

        Utils.displayToast(getApplicationContext(), null); // to cancel toast if showing on screen

        if (!Utils.isNetworkAvailable(getApplicationContext())) {
            Utils.displayToast(getApplicationContext(), getResources().getString(R.string.no_internet));
            return;
        }
        Utils.displayToast(getApplicationContext(), null); // to cancel toast if showing on screen
        progressDialog.show();
        user_id = new SessionManager(getApplicationContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody facility_id = RequestBody.create(MediaType.parse("multipart/form-data"), facilityId);
        RequestBody type_ = RequestBody.create(MediaType.parse("multipart/form-data"), like);


        Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_like_facility(user_id1, facility_id, type_);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {

                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        Log.d("follow", "Success");


                    } else {
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


    }

    private void offeredJobAccept(String jobId) {

        progressDialog.show();

        user_id = new SessionManager(getApplicationContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody job_id = RequestBody.create(MediaType.parse("multipart/form-data"), jobId);

        Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_offered_job_accept(user_id1, job_id);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        progressDialog.dismiss();
                        return;
                    }
                    if (response.isSuccessful()) {
                        ResponseModel responseModel = response.body();
                        if (responseModel.getApiStatus().equals("1")) {
                            Utils.displayToast(context, responseModel.getMessage());
                        }
                    } else {
                        Log.e(TAG + "accept", response.message());
                        return;
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.e(TAG + "accept", e.toString());
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG + "accept", t.toString());
            }
        });

    }

    private void offeredJobReject(String jobId) {

        progressDialog.show();

        user_id = new SessionManager(getApplicationContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody job_id = RequestBody.create(MediaType.parse("multipart/form-data"), jobId);

        Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_offered_job_reject(user_id1, job_id);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        progressDialog.dismiss();
                        return;
                    }
                    if (response.isSuccessful()) {
                        ResponseModel responseModel = response.body();
                        if (responseModel.getApiStatus().equals("1")) {
                            Utils.displayToast(context, responseModel.getMessage());
                        }


                    } else {
                        Log.e(TAG + "reject", response.message());
                        return;
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.e(TAG + "reject", e.toString());
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG + "reject", t.toString());
            }
        });

    }

}