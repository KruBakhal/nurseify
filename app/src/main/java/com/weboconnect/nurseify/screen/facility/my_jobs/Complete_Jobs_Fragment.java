package com.weboconnect.nurseify.screen.facility.my_jobs;

import static com.weboconnect.nurseify.utils.PaginationListener.PAGE_START;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.ActiveFAdapter;
import com.weboconnect.nurseify.adapter.Active_My_job_FAdapter;
import com.weboconnect.nurseify.adapter.Completed_F_Adapter;
import com.weboconnect.nurseify.adapter.PastAdapter;
import com.weboconnect.nurseify.adapter.ProgressHolder;
import com.weboconnect.nurseify.adapter.TabAdapter;
import com.weboconnect.nurseify.databinding.FragmentNurseBinding;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.intermediate.OfferedJobCallback;
import com.weboconnect.nurseify.screen.facility.model.FacilityJobModel;
import com.weboconnect.nurseify.screen.facility.model.Facility_JobDatum;
import com.weboconnect.nurseify.screen.facility.ui.BrowseFFragment;
import com.weboconnect.nurseify.screen.facility.ui.MyJobFFragment;
import com.weboconnect.nurseify.screen.nurse.adapters.CompletedAdapter;
import com.weboconnect.nurseify.screen.nurse.model.CompletedJobModel;
import com.weboconnect.nurseify.utils.PaginationListener;
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


public class Complete_Jobs_Fragment extends Fragment {
    String id;
    FragmentNurseBinding binding;
    View view;
    private TabAdapter adapter;
    private int list_Completed_Job;
    private List<CompletedJobModel.CompletedDatum> offeredJobCallback;
    private boolean isFirstTime = true;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    private Completed_F_Adapter pastAdapter;
    private boolean isFragActive = false;
    private boolean isFilterApply = false;
    private List<Facility_JobDatum> listPostedJob = new ArrayList<>();
    private PaginationListener pagination;

    public Complete_Jobs_Fragment() {
    }

    public Complete_Jobs_Fragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nurse, null, false);

        setAdapter();
//        observeer_View();
        setData();
        refreshData();
        return view = binding.getRoot();
    }

    private void setData() {
        try {
            MyJobFFragment browseFFragment = (MyJobFFragment) getParentFragment();
            if (browseFFragment != null && browseFFragment.binding != null
                    && browseFFragment.binding.editTextSearch != null)
                browseFFragment.binding.editTextSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (isFragActive) {
                            String text = browseFFragment.binding.editTextSearch.getText().toString().toLowerCase();
                            if (TextUtils.isEmpty(text)) {
                                pastAdapter.getFilter().filter(text);
                                isFilterApply = false;
                                if (listPostedJob != null && listPostedJob.size() != 0 && currentPage < totalPage) {
                                    pastAdapter.addLoading();
                                }
                            } else {
//                                binding.recyclerView.addOnScrollListener(null);
                                if (pastAdapter.isLoaderVisible) {
                                    pastAdapter.removeLoading();
                                }
                                pastAdapter.getFilter().filter(text);
                                isFilterApply = true;
                            }
                        }
                    }
                });
            pagination = new PaginationListener((LinearLayoutManager) binding.recyclerView.getLayoutManager()) {
                @Override
                protected void loadMoreItems() {

                    isLoading = true;
                    currentPage++;
                    fetchData();

                }

                @Override
                public boolean isLastPage() {
                    return isLastPage;
                }

                @Override
                public boolean isLoading() {
                    return isLoading;
                }

                @Override
                public boolean isFilter() {
                    return isFilterApply;
                }

            };
            binding.recyclerView.addOnScrollListener(pagination);
        } catch (Exception e) {
            Log.d("tag", "setData: " + e.getMessage());
        }
    }

    private void observeer_View() {
        binding.layProgress.setOnTouchListener(touchListner);

    }

    private void setAdapter() {
        pastAdapter = new Completed_F_Adapter(getActivity(), listPostedJob, new ItemCallback() {

            @Override
            public void onClick(int position) {

            }
        });
        binding.recyclerView.setAdapter(pastAdapter);
    }

    public void fetchData() {
        if (!Utils.isNetworkAvailable(getContext())) {
            init_Data(null, true);
            return;
        }
        try {
            ProgressHolder holder = (ProgressHolder) binding.recyclerView.findViewHolderForAdapterPosition(pastAdapter.getItemCount() - 1);
            if (holder != null && holder.item_tv_msg != null) {
                holder.item_tv_msg.setText(getString(R.string.loading_job));
            }
        } catch (Exception exception) {
            Log.d("TAG", "init_Data: " + exception.getMessage());
        }
        if (isFirstTime) {
            isFirstTime = false;
            showProgress();
        } else {
            isFirstTime = false;
        }
        String user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody api_key = RequestBody.create(MediaType.parse("multipart/form-data"), new SessionManager(getContext()).get_API_KEY());
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody current_Page1 = RequestBody.create(MediaType.parse("multipart/form-data"), ""
                + currentPage);


        Call<FacilityJobModel> call = RetrofitClient.getInstance().getFacilityApi()
                .call_my_jobs_completed(user_id1, current_Page1);

        call.enqueue(new Callback<FacilityJobModel>() {
            @Override
            public void onResponse(Call<FacilityJobModel> call, Response<FacilityJobModel> response) {
                if (response == null || response.body() == null) {
                    init_Data(null, false);
                    return;
                }
                if (response.isSuccessful()) {
                    FacilityJobModel facilityJobModel = response.body();
                    if (!facilityJobModel.getApiStatus().equals("1")) {
                        init_Data(facilityJobModel, false);
                    } else {
                        init_Data(facilityJobModel, false);
                    }
                } else {
                    init_Data(null, false);
                }
            }

            @Override
            public void onFailure(Call<FacilityJobModel> call, Throwable t) {
                init_Data(null, false);
                Log.d("TAG", getContext().getClass().getSimpleName() + " onFailure: " + t.getMessage());
            }
        });

    }


    private void init_Data(FacilityJobModel NurseModel, boolean isNetwork) {
        if (NurseModel == null ||
                ((NurseModel.getData().getData() == null
                        || NurseModel.getData().getData().size() == 0))) {
            if (isNetwork) {
                if (listPostedJob != null && listPostedJob.size() != 0) {
                    dismissProgress();
                    if (currentPage != PAGE_START) {
//                        nursesAdapter.removeLoading(); // must be comment
//                        binding.swipeRefresh.setRefreshing(false);
                        isLoading = false;
                        currentPage--;
                        try {
                            ProgressHolder holder = (ProgressHolder) binding.recyclerView.findViewHolderForAdapterPosition(pastAdapter.getItemCount() - 1);
                            if (holder != null && holder.item_tv_msg != null) {
                                holder.item_tv_msg.setText("No Internet Connectivity Found !");
                            }
                        } catch (Exception exception) {
                            Log.d("TAG", "init_Data: " + exception.getMessage());
                        }
                    }
                } else {
                    pastAdapter.removeLoading();
                    errorProgress(false);
                }
            } else if (listPostedJob != null && listPostedJob.size() != 0) {
                dismissProgress();
//                pastAdapter.removeLoading();
            } else {
//                pastAdapter.removeLoading();
                errorProgress(true);
                binding.tvMsg.setText("Yet, No Job Found !");
            }
        } else {
            dismissProgress();
            if (currentPage != PAGE_START)
                pastAdapter.removeLoading();
            pastAdapter.addItems(NurseModel.getData().getData());
//            binding.swipeRefresh.setRefreshing(false);
            currentPage = Integer.parseInt(NurseModel.getData().getCurrentPage());
            totalPage = Integer.parseInt(NurseModel.getData().getTotalPagesAvailable());
            PaginationListener.PAGE_SIZE = Integer.parseInt(NurseModel.getData().getResultsPerPage());
            if (currentPage < totalPage) {
                pastAdapter.addLoading();
            } else {
                isLastPage = true;
            }
            isLoading = false;
        }
    }

    private void dismissProgress() {
        binding.layProgress.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.VISIBLE);
//        binding.swipeRefresh.setRefreshing(false);
    }

    private void errorProgress(boolean status) {
//        binding.swipeRefresh.setRefreshing(false);
        binding.recyclerView.setVisibility(View.GONE);
        binding.layProgress.setVisibility(View.VISIBLE);
        binding.pg.setVisibility(View.GONE);
        if (status)
            binding.tvMsg.setText(getString(R.string.something_when_wrong));
        else
            binding.tvMsg.setText(getString(R.string.no_internet));
    }

    private void showProgress() {
        binding.recyclerView.setVisibility(View.GONE);
        binding.layProgress.setVisibility(View.VISIBLE);
        binding.pg.setVisibility(View.VISIBLE);
        binding.tvMsg.setText("Please Wait");
    }

    public void refreshData() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        isFirstTime = true;
        if (pastAdapter != null)
            pastAdapter.clear();
        fetchData();
    }

    private View.OnTouchListener touchListner = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        isFragActive = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isFragActive = false;
    }

}