package com.weboconnect.nurseify.screen.nurse.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.nurse.ActiveJobDetailsActivity;
import com.weboconnect.nurseify.screen.nurse.CompletedJobDetailsActivity;
import com.weboconnect.nurseify.screen.nurse.Browse_Facility_Offered_JobDetailsActivity;
import com.weboconnect.nurseify.screen.nurse.adapters.ActiveAdapter;
import com.weboconnect.nurseify.screen.nurse.adapters.CompletedAdapter;
import com.weboconnect.nurseify.intermediate.OfferedJobCallback;
import com.weboconnect.nurseify.databinding.FragmentMyJobsBinding;
import com.weboconnect.nurseify.screen.nurse.adapters.OfferedJobAdapter;
import com.weboconnect.nurseify.screen.nurse.model.ActiveModel;
import com.weboconnect.nurseify.screen.nurse.model.CompletedJobModel;
import com.weboconnect.nurseify.screen.nurse.model.OfferedJobModel;
import com.weboconnect.nurseify.screen.nurse.model.PrivacyPolicyModel;
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

public class MyJobFragment extends Fragment {
    String id;
    FragmentMyJobsBinding binding;
    View view;

    String user_id;
    String TAG = "MyJobFragment ";
    OfferedJobAdapter offeredJobAdapter;
    List<OfferedJobModel.OfferedJob> list_Offered_Job = new ArrayList<>();
    List<ActiveModel.ActiveDatum> list_Active_Job = new ArrayList<>();
    List<CompletedJobModel.CompletedDatum> list_Completed_Job = new ArrayList<>();
    private ActiveAdapter activeAdapter;
    private CompletedAdapter completedAdapter;
    private ProgressDialog progressDialog;
    int currentTabSelected = 1;
    private boolean isFirstTime = true;
    private int selected_page = 1;
    private int selected_page2 = 1;
    private int selected_page3 = 1;

    public MyJobFragment() {
    }

    public MyJobFragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_jobs, null, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        binding.recyclerViewJobs.addOnScrollListener(recyclerViewOnScrollListener);
        binding.recyclerViewJobs2.addOnScrollListener(recyclerViewOnScrollListener2);
        binding.recyclerViewJobs3.addOnScrollListener(recyclerViewOnScrollListener3);
        get_Offered_Job(false);
        click();

        return view = binding.getRoot();
    }

    private void click() {
        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (currentTabSelected == 1 && offeredJobAdapter != null && list_Offered_Job != null && list_Offered_Job.size() != 0) {
                    try {
                        String text = binding.editTextSearch.getText().toString().toLowerCase();
                        offeredJobAdapter.getFilter().filter(text);
                    } catch (Exception e) {

                    }
                } else if (currentTabSelected == 2 && activeAdapter != null && list_Active_Job != null && list_Active_Job.size() != 0) {

                    try {
                        String text = binding.editTextSearch.getText().toString().toLowerCase();
                        activeAdapter.getFilter().filter(text);
                    } catch (Exception e) {

                    }
                } else if (currentTabSelected == 3 && completedAdapter != null && list_Completed_Job != null && list_Completed_Job.size() != 0) {

                    try {
                        String text = binding.editTextSearch.getText().toString().toLowerCase();
                        completedAdapter.getFilter().filter(text);
                    } catch (Exception e) {

                    }
                }

            }
        });

        binding.textOffered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTabSelected = 1;
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textCompleted.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textOffered.setTextColor(Color.parseColor("#8A4999"));
                binding.textActive.setTextColor(Color.parseColor("#000000"));
                binding.textCompleted.setTextColor(Color.parseColor("#000000"));
                binding.recyclerViewJobs.setVisibility(View.VISIBLE);
                binding.recyclerViewJobs2.setVisibility(View.GONE);
                binding.recyclerViewJobs3.setVisibility(View.GONE);
                binding.progress.setVisibility(View.GONE);
                if (list_Offered_Job == null || list_Offered_Job.size() == 0) {
                    isFirstTime = true;
                    get_Offered_Job(false);
                } else {
                    binding.recyclerViewJobs.setAdapter(offeredJobAdapter);
                    dismissProgress();
                }
            }
        });

        binding.textActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTabSelected = 2;
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textCompleted.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textActive.setTextColor(Color.parseColor("#8A4999"));
                binding.textOffered.setTextColor(Color.parseColor("#000000"));
                binding.textCompleted.setTextColor(Color.parseColor("#000000"));
//                binding.recyclerViewJobs.setAdapter(new ActiveAdapter(getActivity(), list_Active_Job, offeredJobCallback));
                binding.recyclerViewJobs.setVisibility(View.GONE);
                binding.recyclerViewJobs2.setVisibility(View.VISIBLE);
                binding.recyclerViewJobs3.setVisibility(View.GONE);
                binding.progress.setVisibility(View.GONE);
                if (list_Active_Job == null || list_Active_Job.size() == 0) {
                    isFirstTime = true;
                    get_Active_Job();
                } else {
                    binding.recyclerViewJobs2.setAdapter(activeAdapter);
                    dismissProgress();
                }
            }
        });

        binding.textCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTabSelected = 3;
                binding.textCompleted.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textCompleted.setTextColor(Color.parseColor("#8A4999"));
                binding.textActive.setTextColor(Color.parseColor("#000000"));
                binding.textOffered.setTextColor(Color.parseColor("#000000"));
//                binding.recyclerViewJobs.setAdapter(new CompletedAdapter(getActivity(), list_Completed_Job, offeredJobCallback));
                binding.recyclerViewJobs.setVisibility(View.GONE);
                binding.recyclerViewJobs2.setVisibility(View.GONE);
                binding.recyclerViewJobs3.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
                if (list_Completed_Job == null || list_Completed_Job.size() == 0) {
                    isFirstTime = true;
                    get_Completed_Job();
                } else {
                    binding.recyclerViewJobs3.setAdapter(completedAdapter);
                    dismissProgress();
                }
            }
        });

    }

    private void get_Offered_Job(boolean isAcceptCall) {
        if (isFirstTime) {
            isFirstTime = false;
            showProgress();
        } else {
            selected_page++;
            binding.progress.setVisibility(View.VISIBLE);
        }

        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<OfferedJobModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_offered_job(String.valueOf(selected_page), user_id1);

        call.enqueue(new Callback<OfferedJobModel>() {
            @Override
            public void onResponse(Call<OfferedJobModel> call, Response<OfferedJobModel> response) {
//                Log.d(TAG + "getOfferedJob ResCode", response.code() + "");
//                assert response.body() != null;
                if (response != null && response.body() != null && !response.body().getApiStatus().equals("1")) {
                    errorProgress(false);

                    return;
                }
                if (response.isSuccessful()) {
                    try {
                        dismissProgress();
                        OfferedJobModel offeredJobModel = response.body();
                        if (offeredJobModel.getData() == null || offeredJobModel.getData().getOffer() == null || offeredJobModel.getData().getOffer().size() == 0) {
                            if (selected_page >= 1)
                                selected_page--;
                            if (list_Offered_Job == null && list_Offered_Job.size() == 0) {
                                errorProgress(false);
                            }
                            return;
                        }

                        if (list_Offered_Job == null) {
                            list_Offered_Job = new ArrayList<>();
                        }
                        if (isAcceptCall) {
                            list_Offered_Job.clear();
                        }
                        list_Offered_Job.addAll(offeredJobModel.getData().getOffer());
                        if (list_Offered_Job.size() > 0) {
                            set_Offered_Adapter();
                        } else {
                            list_Offered_Job.addAll(offeredJobModel.getData().getOffer());
                            set_Offered_Adapter();
                        }


                    } catch (Exception e) {
                        errorProgress(false);
                        Log.e(TAG + "getOfferedJob", e.toString());
                    }

                } else {
                    dismissProgress();
                    Log.e(TAG + "getOfferedJob", response.message());
                    return;
                }

            }

            @Override
            public void onFailure(Call<OfferedJobModel> call, Throwable t) {
                Log.e(TAG + "getOfferedJob", t.toString());
                errorProgress(false);
            }
        });

    }

    private void get_Active_Job() {

        if (isFirstTime) {
            isFirstTime = false;
            showProgress();
        } else {
            selected_page2++;
            binding.progress.setVisibility(View.VISIBLE);
        }


        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<ActiveModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_active_job(String.valueOf(selected_page2), user_id1);

        call.enqueue(new Callback<ActiveModel>() {
            @Override
            public void onResponse(Call<ActiveModel> call, Response<ActiveModel> response) {
//                Log.d(TAG + "getOfferedJob ResCode", response.code() + "");
                assert response.body() != null;
                if (!response.body().getApiStatus().equals("1")) {
                    errorProgress(false);
                    return;
                }
                if (response.isSuccessful()) {
                    dismissProgress();
                    try {
                        ActiveModel offeredJobModel = response.body();
                        if (offeredJobModel.getData() == null || offeredJobModel.getData().size() == 0) {
//                            Utils.displayToast(getContext(), "no data found");
                            if (selected_page2 >= 1)
                                selected_page2--;
                            if (list_Active_Job == null && list_Active_Job.size() == 0) {
                                errorProgress(false);
                            }
                            return;
                        }
                        if (list_Active_Job == null) {
                            list_Active_Job = new ArrayList<>();
                        }
                        list_Active_Job.addAll(offeredJobModel.getData());
                        if (list_Active_Job.size() > 0) {
                            if (activeAdapter != null) {
                                set_Active_Adapter();
                                binding.textActive.performClick();
                            } else
                                set_Active_Adapter();
                        } else {
                            list_Active_Job.addAll(offeredJobModel.getData());
                            set_Active_Adapter();
                        }
                    } catch (Exception e) {
                        Log.e(TAG + "getOfferedJob", e.toString());
                        errorProgress(false);
                    }
                    dismissProgress();
                } else {
                    Log.e(TAG + "getOfferedJob", response.message());
                    return;
                }
                dismissProgress();
            }

            @Override
            public void onFailure(Call<ActiveModel> call, Throwable t) {
                errorProgress(false);
                Log.e(TAG + "getOfferedJob", t.toString());
            }
        });

    }

    private void get_Completed_Job() {
        if (isFirstTime) {
            isFirstTime = false;
            showProgress();
        } else {
            selected_page3++;
            binding.progress.setVisibility(View.VISIBLE);
        }


        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<CompletedJobModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_completed_job(String.valueOf(selected_page3), user_id1);

        call.enqueue(new Callback<CompletedJobModel>() {
            @Override
            public void onResponse(Call<CompletedJobModel> call, Response<CompletedJobModel> response) {
//                Log.d(TAG + "getOfferedJob ResCode", response.code() + "");
                assert response.body() != null;
                if (!response.body().getApiStatus().equals("1")) {
                    errorProgress(false);
                    return;
                }
                if (response.isSuccessful()) {
                    dismissProgress();
                    try {
                        CompletedJobModel offeredJobModel = response.body();
                        if (offeredJobModel.getData() == null || offeredJobModel.getData().size() == 0) {
//                            Utils.displayToast(getContext(), "no data found");
                            if (selected_page3 >= 1)
                                selected_page3--;
                            if (list_Completed_Job == null && list_Completed_Job.size() == 0) {
                                errorProgress(false);
                            }
                            return;
                        }
                        if (list_Completed_Job == null) {
                            list_Completed_Job = new ArrayList<>();
                        }
                        list_Completed_Job.addAll(offeredJobModel.getData());
                        if (list_Completed_Job.size() > 0) {
                            if (completedAdapter != null) {
                                set_Completed_Adapter();
                                binding.textCompleted.performClick();
                            } else
                                set_Completed_Adapter();
                        } else {
                            list_Completed_Job.addAll(offeredJobModel.getData());
                            set_Completed_Adapter();
                        }
                    } catch (Exception e) {
                        Log.e(TAG + "getOfferedJob", e.toString());
                        errorProgress(false);
                    }
                    dismissProgress();
                } else {
                    Log.e(TAG + "getOfferedJob", response.message());
                    return;
                }
                dismissProgress();
            }

            @Override
            public void onFailure(Call<CompletedJobModel> call, Throwable t) {
                errorProgress(false);
                Log.e(TAG + "getOfferedJob", t.toString());
            }
        });

    }

    private void set_Offered_Adapter() {
        offeredJobAdapter = new OfferedJobAdapter(getActivity(), list_Offered_Job, new OfferedJobCallback() {
            @Override
            public void onAccept(String jobId) {
                offeredJobAccept(jobId);
            }

            @Override
            public void onReject(String jobId) {
                offeredJobReject(jobId);
            }

            @Override
            public void onClick(int pos) {
                String job_id = list_Offered_Job.get(pos).getOfferId();
                if (list_Offered_Job.get(pos).getStatus().equals("pending")) {
                    getActivity().startActivity(new Intent(getActivity(), Browse_Facility_Offered_JobDetailsActivity.class)
                            .putExtra("data", job_id)
                            .putExtra(Constant.FLAG, 3));
                } else if (list_Offered_Job.get(pos).getStatus().equals("active")) {
                    getActivity().startActivity(new Intent(getActivity(), ActiveJobDetailsActivity.class)
                            .putExtra("data", job_id));
                } else if (list_Offered_Job.get(pos).getStatus().equals("completed")) {
                    getActivity().startActivity(new Intent(getActivity(), CompletedJobDetailsActivity.class)
                            .putExtra("data", job_id));
                }
            }
        });
        binding.recyclerViewJobs.setAdapter(offeredJobAdapter);
        offeredJobAdapter.notifyDataSetChanged();
    }

    private void set_Active_Adapter() {
        activeAdapter = new ActiveAdapter(getActivity(), list_Active_Job, new OfferedJobCallback() {
            @Override
            public void onAccept(String jobId) {
//                offeredJobAccept(jobId);
            }

            @Override
            public void onReject(String jobId) {
//                offeredJobReject(jobId);
            }

            @Override
            public void onClick(int pos) {

                getActivity().startActivity(new Intent(getActivity(), ActiveJobDetailsActivity.class)
                        .putExtra("data", list_Active_Job.get(pos).getOffer_id()));

            }
        });
        binding.recyclerViewJobs2.setAdapter(activeAdapter);
//        activeAdapter.notifyDataSetChanged();
    }

    private void set_Completed_Adapter() {
        completedAdapter = new CompletedAdapter(getActivity(), 1, list_Completed_Job, new OfferedJobCallback() {
            @Override
            public void onAccept(String jobId) {
//                offeredJobAccept(jobId);
            }

            @Override
            public void onReject(String jobId) {
//                offeredJobReject(jobId);
            }

            @Override
            public void onClick(int pos) {
                getActivity().startActivity(new Intent(getActivity(), CompletedJobDetailsActivity.class)
//                        .putExtra(Constant.FLAG, 5)
                                .putExtra("data", list_Completed_Job.get(pos).getOffer_id())
                );
            }
        });
        binding.recyclerViewJobs3.setAdapter(completedAdapter);
//        completedAdapter.notifyDataSetChanged();
    }

    private void offeredJobAccept(String jobId) {

        progressDialog.show();

        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody job_id = RequestBody.create(MediaType.parse("multipart/form-data"), jobId);

        Call<PrivacyPolicyModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_offered_job_accept(user_id1, job_id);

        call.enqueue(new Callback<PrivacyPolicyModel>() {
            @Override
            public void onResponse(Call<PrivacyPolicyModel> call, Response<PrivacyPolicyModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        progressDialog.dismiss();
                        return;
                    }
                    if (response.isSuccessful()) {
                        PrivacyPolicyModel responseModel = response.body();
                        if (responseModel.getApiStatus().equals("1")) {
//                            Utils.displayToast(getContext(), responseModel.getData());
                            get_Offered_Job(true);
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
            public void onFailure(Call<PrivacyPolicyModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG + "accept", t.toString());
            }
        });

    }

    private void offeredJobReject(String jobId) {

        progressDialog.show();

        user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody job_id = RequestBody.create(MediaType.parse("multipart/form-data"), jobId);

        Call<PrivacyPolicyModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_offered_job_reject(user_id1, job_id);

        call.enqueue(new Callback<PrivacyPolicyModel>() {
            @Override
            public void onResponse(Call<PrivacyPolicyModel> call, Response<PrivacyPolicyModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        progressDialog.dismiss();
                        return;
                    }
                    if (response.isSuccessful()) {
                        PrivacyPolicyModel responseModel = response.body();
                        if (responseModel.getApiStatus().equals("1")) {
//                            Utils.displayToast(getContext(), responseModel.getData());
                            get_Offered_Job(true);
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
            public void onFailure(Call<PrivacyPolicyModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG + "reject", t.toString());
            }
        });

    }

    private void dismissProgress() {
        binding.layProgress.setVisibility(View.GONE);
        binding.recyclerViewJobs.setVisibility(View.VISIBLE);
        binding.progress.setVisibility(View.GONE);
        if (currentTabSelected == 1) {
            binding.recyclerViewJobs.setVisibility(View.VISIBLE);
            binding.recyclerViewJobs2.setVisibility(View.GONE);
            binding.recyclerViewJobs3.setVisibility(View.GONE);
        } else if (currentTabSelected == 2) {
            binding.recyclerViewJobs.setVisibility(View.GONE);
            binding.recyclerViewJobs2.setVisibility(View.VISIBLE);
            binding.recyclerViewJobs3.setVisibility(View.GONE);
        } else if (currentTabSelected == 3) {
            binding.recyclerViewJobs.setVisibility(View.GONE);
            binding.recyclerViewJobs2.setVisibility(View.GONE);
            binding.recyclerViewJobs3.setVisibility(View.VISIBLE);
        }
    }

    private void errorProgress(boolean status) {
        binding.recyclerViewJobs.setVisibility(View.GONE);
        binding.layProgress.setVisibility(View.VISIBLE);
        binding.pg.setVisibility(View.GONE);
        binding.progress.setVisibility(View.GONE);
        if (status)
            binding.tvMsg.setText(getString(R.string.something_when_wrong));
        else
            binding.tvMsg.setText(getString(R.string.no_internet));

        binding.recyclerViewJobs.setVisibility(View.GONE);
        binding.recyclerViewJobs2.setVisibility(View.GONE);
        binding.recyclerViewJobs3.setVisibility(View.GONE);
    }

    private void showProgress() {
        binding.recyclerViewJobs.setVisibility(View.GONE);
        binding.recyclerViewJobs2.setVisibility(View.GONE);
        binding.recyclerViewJobs3.setVisibility(View.GONE);
        binding.layProgress.setVisibility(View.VISIBLE);

    }

    private boolean loading = false;
    private boolean isLastPage = false;

    private int offset = 10;
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = binding.recyclerViewJobs.getLayoutManager().getChildCount();
            int totalItemCount = binding.recyclerViewJobs.getLayoutManager().getItemCount();
            int firstVisibleItemPosition = ((LinearLayoutManager) binding.recyclerViewJobs.getLayoutManager()).findFirstVisibleItemPosition();

            if (!loading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= offset) {
                    get_Offered_Job(false);

                }
            }
        }
    };
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener2 = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = binding.recyclerViewJobs2.getLayoutManager().getChildCount();
            int totalItemCount = binding.recyclerViewJobs2.getLayoutManager().getItemCount();
            int firstVisibleItemPosition = ((LinearLayoutManager) binding.recyclerViewJobs2.getLayoutManager()).findFirstVisibleItemPosition();

            if (!loading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= offset) {
                    get_Active_Job();

                }
            }
        }
    };
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener3 = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = binding.recyclerViewJobs3.getLayoutManager().getChildCount();
            int totalItemCount = binding.recyclerViewJobs3.getLayoutManager().getItemCount();
            int firstVisibleItemPosition = ((LinearLayoutManager) binding.recyclerViewJobs3.getLayoutManager()).findFirstVisibleItemPosition();

            if (!loading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= offset) {
                    get_Completed_Job();

                }
            }
        }
    };
}