package com.weboconnect.nurseify.screen.nurse.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.FacilityAdapter;
import com.weboconnect.nurseify.adapter.JobAdapter;
import com.weboconnect.nurseify.adapter.LikeModel;
import com.weboconnect.nurseify.databinding.DialogFilterBinding;
import com.weboconnect.nurseify.databinding.FragmentBrowseBinding;
import com.weboconnect.nurseify.intermediate.FacilityListCallback;
import com.weboconnect.nurseify.screen.nurse.JobDetailsActivity;
import com.weboconnect.nurseify.screen.nurse.adapters.BrowserJobsAdapter;
import com.weboconnect.nurseify.screen.nurse.model.FacilityModel;
import com.weboconnect.nurseify.screen.nurse.model.FollowFacilityModel;
import com.weboconnect.nurseify.screen.nurse.model.JobModel;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BrowseFragment extends Fragment {
    String id;
    FragmentBrowseBinding binding;
    View view;
    private int selected_page = 1;
    private String search_location = "77004";
    private String open_assignment_type = "26";
    private String facility_type = "9";
    private String electronic_medical_records = "327";
    private String user_id = " ";
    boolean isJobType = true;
    private List<JobModel.JobDatum> list_jobs = new ArrayList<>();
    private List<FacilityModel.Facility> list_facility = new ArrayList<>();
    private BrowserJobsAdapter browserJobsAdapter;
    private FacilityAdapter facilityAdapter;
    private RecyclerView.LayoutManager layoutManager;
    FacilityListCallback facilityListCallback = new FacilityListCallback() {
        @Override
        public void onFollow(String facilityId, String type) {
            Log.e("facilityId", facilityId);
            Log.e("type", type);
//            followFacility(facilityId,type);
        }

        @Override
        public void onLike(String facilityId, String like) {
            likeFacility(facilityId, like);
        }
    };

    public BrowseFragment() {
    }

    public BrowseFragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_browse, null, false);
        binding.recyclerViewJobs.setAdapter(new JobAdapter(getActivity()));
        list_facility = new ArrayList<>();
        facilityAdapter = new FacilityAdapter(getActivity(), list_facility, facilityListCallback);

        click();
        layoutManager = binding.recyclerViewJobs.getLayoutManager();
        binding.recyclerViewJobs.addOnScrollListener(recyclerViewOnScrollListener);
        fecthBrowseFacility();
        return view = binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Utils.isNetworkAvailable(getContext())) {
            if (isJobType) {
                fecthBrowseJobs();
            } else {

            }
        } else {
            errorProgress(false);
        }
    }

    private void openFilter() {
        DialogFilterBinding dialogFilterBinding =
                DialogFilterBinding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(getContext(), R.style.AlertDialog);
        dialog.setContentView(dialogFilterBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);

        dialogFilterBinding.closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogFilterBinding.textApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();

           /* if (!fetchLiveData.loading && !fetchLiveData.isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= fetchLiveData.offset) {
                    fecthBrowseJobs(selected_page);
                }
            }*/
        }
    };

    private void fecthBrowseJobs() {

        Utils.displayToast(getContext(), null); // to cancel toast if showing on screen

        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.displayToast(getContext(), getResources().getString(R.string.no_internet));
            return;
        }
        Utils.displayToast(getContext(), null); // to cancel toast if showing on screen
        showProgress();
        String page = "" + selected_page;
        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody search_location1 = RequestBody.create(MediaType.parse("multipart/form-data"), search_location);
        RequestBody open_assignment_type1 = RequestBody.create(MediaType.parse("multipart/form-data"), open_assignment_type);
        RequestBody facility_type1 = RequestBody.create(MediaType.parse("multipart/form-data"), facility_type);
        RequestBody electronic_medical_records1 = RequestBody.create(MediaType.parse("multipart/form-data"), electronic_medical_records);
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

    /*    Call<JobModel> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_browser_filter_job(page);*/

        Call<JobModel> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_browser_filter_job(page, search_location1, open_assignment_type1, facility_type1,
                        electronic_medical_records1, user_id1);

        call.enqueue(new Callback<JobModel>() {
            @Override
            public void onResponse(Call<JobModel> call, Response<JobModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        errorProgress(true);
                        return;
                    }
                    if (response.isSuccessful()) {
                        dismissProgress();
                        JobModel jobModel = response.body();
                        if (list_jobs == null) {
                            list_jobs = new ArrayList<>();
                        }
                        if (list_jobs.size() > 0) {
                            list_jobs.addAll(jobModel.getData());
                            if (browserJobsAdapter != null) {
                                browserJobsAdapter.notifyDataSetChanged();
                            } else
                                setAdapter();
                        } else {
                            list_jobs.addAll(jobModel.getData());
                            setAdapter();
                        }

                    } else {
                        errorProgress(true);
                    }
                } catch (Exception e) {
                    errorProgress(true);
                }
            }

            @Override
            public void onFailure(Call<JobModel> call, Throwable t) {
                errorProgress(true);

            }
        });


    }

    private void performLike(String jobId, String isLiked, int position, JobModel.JobDatum datum) {

        Utils.displayToast(getContext(), null); // to cancel toast if showing on screen

        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.displayToast(getContext(), getResources().getString(R.string.no_internet));
            return;
        }
        Utils.displayToast(getContext(), null); // to cancel toast if showing on screen
        showProgress();
        String page = "" + selected_page;
        if (isLiked.equals("0")) {
            isLiked = "1";
        } else {
            isLiked = "0";
        }
        user_id = new SessionManager(getContext()).get_user_register_Id();
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
                        errorProgress(true);
                        return;
                    }
                    if (response.isSuccessful()) {
                        dismissProgress();
                        LikeModel jobModel = response.body();
                        Utils.displayToast(getContext(), "" + jobModel.getMessage());
                        datum.setIsLiked(finalIsLiked);
                        list_jobs.set(position, datum);
                        browserJobsAdapter.notifyDataSetChanged();
                    } else {
                        errorProgress(true);
                    }
                } catch (Exception e) {
                    errorProgress(true);
                }
            }

            @Override
            public void onFailure(Call<LikeModel> call, Throwable t) {
                errorProgress(true);

            }
        });


    }


    private void fecthBrowseFacility() {

        Utils.displayToast(getContext(), null); // to cancel toast if showing on screen

        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.displayToast(getContext(), getResources().getString(R.string.no_internet));
            return;
        }
        Utils.displayToast(getContext(), null); // to cancel toast if showing on screen
        showProgress();
        String page = "" + selected_page;
        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);


        Call<FacilityModel> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_browser_facility(page, user_id1);

        call.enqueue(new Callback<FacilityModel>() {
            @Override
            public void onResponse(Call<FacilityModel> call, Response<FacilityModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        errorProgress(true);
                        return;
                    }
                    if (response.isSuccessful()) {
                        dismissProgress();
                        FacilityModel facilityModel = response.body();
                        if (list_facility == null) {
                            list_facility = new ArrayList<>();
                        }
                        if (list_facility.size() > 0) {
                            list_facility.addAll(facilityModel.getData());
                            if (browserJobsAdapter != null) {
                                facilityAdapter.notifyDataSetChanged();
                            } else
                                setAdapter();
                        } else {
                            list_facility.addAll(facilityModel.getData());
                            setAdapter();
                        }

                    } else {
                        errorProgress(true);
                    }
                } catch (Exception e) {
                    errorProgress(true);
                }
            }

            @Override
            public void onFailure(Call<FacilityModel> call, Throwable t) {
                errorProgress(true);

            }
        });


    }

    private void followFacility(String facilityId, String type) {

        Utils.displayToast(getContext(), null); // to cancel toast if showing on screen

        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.displayToast(getContext(), getResources().getString(R.string.no_internet));
            return;
        }
        Utils.displayToast(getContext(), null); // to cancel toast if showing on screen
        showProgress();
        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody facility_id = RequestBody.create(MediaType.parse("multipart/form-data"), facilityId);
        RequestBody type_ = RequestBody.create(MediaType.parse("multipart/form-data"), type);


        Call<FollowFacilityModel> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_follow_facility(user_id1, facility_id, type_);

        call.enqueue(new Callback<FollowFacilityModel>() {
            @Override
            public void onResponse(Call<FollowFacilityModel> call, Response<FollowFacilityModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        errorProgress(true);
                        return;
                    }
                    if (response.isSuccessful()) {
                        dismissProgress();
                        Log.e("follow", "Success");
                        fecthBrowseFacility();

                    } else {
                        errorProgress(true);
                    }
                } catch (Exception e) {
                    errorProgress(true);
                }
            }

            @Override
            public void onFailure(Call<FollowFacilityModel> call, Throwable t) {
                errorProgress(true);

            }
        });


    }

    private void likeFacility(String facilityId, String like) {

        Utils.displayToast(getContext(), null); // to cancel toast if showing on screen

        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.displayToast(getContext(), getResources().getString(R.string.no_internet));
            return;
        }
        Utils.displayToast(getContext(), null); // to cancel toast if showing on screen
        showProgress();
        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody facility_id = RequestBody.create(MediaType.parse("multipart/form-data"), facilityId);
        RequestBody type_ = RequestBody.create(MediaType.parse("multipart/form-data"), like);


        Call<FollowFacilityModel> call = RetrofitClient.getInstance().getRetrofitApi()
                .call_like_facility(user_id1, facility_id, type_);

        call.enqueue(new Callback<FollowFacilityModel>() {
            @Override
            public void onResponse(Call<FollowFacilityModel> call, Response<FollowFacilityModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        errorProgress(true);
                        return;
                    }
                    if (response.isSuccessful()) {
                        dismissProgress();
                        Log.e("follow", "Success");
                        fecthBrowseFacility();

                    } else {
                        errorProgress(true);
                    }
                } catch (Exception e) {
                    errorProgress(true);
                }
            }

            @Override
            public void onFailure(Call<FollowFacilityModel> call, Throwable t) {
                errorProgress(true);

            }
        });


    }

    private void click() {
        binding.imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilter();
            }
        });
        binding.textJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isJobType = true;
                binding.imgFilter.setVisibility(View.VISIBLE);
                binding.editTextSearch.setHint("Search for jobs...");
                binding.textJobs.setTextColor(Color.parseColor("#8A4999"));
                binding.textFacilities.setTextColor(Color.parseColor("#000000"));
                binding.textJobs.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textFacilities.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.recyclerViewJobs.setAdapter(browserJobsAdapter);
            }
        });
        binding.textFacilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isJobType = false;
                binding.imgFilter.setVisibility(View.GONE);
                binding.editTextSearch.setHint("Search for Facilities");
                binding.textFacilities.setTextColor(Color.parseColor("#8A4999"));
                binding.textJobs.setTextColor(Color.parseColor("#000000"));
                binding.textFacilities.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textJobs.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.recyclerViewJobs.setAdapter(facilityAdapter);
            }
        });
    }

    private void setAdapter() {
        browserJobsAdapter = new BrowserJobsAdapter(getActivity(), list_jobs,
                new BrowserJobsAdapter.BrowseJobInteface() {
                    @Override
                    public void onClick_Like(JobModel.JobDatum datum, int position) {
                        performLike(datum.getJobId(), datum.getIsLiked(), position, datum);
                    }

                    @Override
                    public void onClick_Apply() {

                    }

                    @Override
                    public void onClick_Job(JobModel.JobDatum datum) {
                        startActivity(new Intent(getContext(), JobDetailsActivity.class)
                                .putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(datum)));
                    }
                });
        binding.recyclerViewJobs.setAdapter(browserJobsAdapter);
    }

    private void dismissProgress() {
        binding.layProgress.setVisibility(View.GONE);
        binding.recyclerViewJobs.setVisibility(View.VISIBLE);
    }

    private void errorProgress(boolean status) {
        binding.recyclerViewJobs.setVisibility(View.GONE);
        binding.layProgress.setVisibility(View.VISIBLE);
        binding.pg.setVisibility(View.GONE);
        if (status)
            binding.tvMsg.setText(getString(R.string.something_when_wrong));
        else
            binding.tvMsg.setText(getString(R.string.no_internet));


    }

    private void showProgress() {
        binding.recyclerViewJobs.setVisibility(View.GONE);
        binding.layProgress.setVisibility(View.VISIBLE);
    }

}