package com.nurseify.app.screen.facility.browse;

import static com.nurseify.app.utils.PaginationListener.PAGE_START;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nurseify.app.R;
import com.nurseify.app.adapter.OfferedFAdapter;
import com.nurseify.app.adapter.ProgressHolder;
import com.nurseify.app.adapter.TabAdapter;
import com.nurseify.app.databinding.FragmentNurseBinding;
import com.nurseify.app.intermediate.ItemCallback;
import com.nurseify.app.screen.facility.model.OfferedNurse_Datum;
import com.nurseify.app.screen.facility.model.OfferedNurse_F_Model;
import com.nurseify.app.screen.facility.ui.BrowseFFragment;
import com.nurseify.app.utils.PaginationListener;
import com.nurseify.app.utils.SessionManager;
import com.nurseify.app.utils.Utils;
import com.nurseify.app.webService.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Offered_Browse_Fragment extends Fragment {
    FragmentNurseBinding binding;
    View view;
    private TabAdapter adapter;
    private boolean isFirstTime = true;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    private OfferedFAdapter offeredFAdapter;
    private boolean isFragActive = false;
    private boolean isFilterApply = false;
    private List<OfferedNurse_Datum> listPostedJob = new ArrayList<>();
    private PaginationListener pagination;


    public Offered_Browse_Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nurse, null, false);

        setAdapter();
        observeer_View();
        setData();
        setAdapter();
        return view = binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("TAG", "onViewCreated: Of ");
    }

    private void setData() {
        try {
            BrowseFFragment browseFFragment = (BrowseFFragment) getParentFragment();
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
                                offeredFAdapter.getFilter().filter(text);
                                isFilterApply = false;
                                if (listPostedJob != null && listPostedJob.size() != 0
                                        && currentPage < totalPage && !offeredFAdapter.isLoaderVisible) {
                                    offeredFAdapter.addLoading();
                                }
                            } else {
//                                binding.recyclerView.addOnScrollListener(null);
//                                offeredFAdapter.removeLoading();
                                if (offeredFAdapter.isLoaderVisible) {
                                    offeredFAdapter.removeLoading();
                                }
                                offeredFAdapter.getFilter().filter(text);
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
        offeredFAdapter = new OfferedFAdapter(getActivity(), listPostedJob, new ItemCallback() {

            @Override
            public void onClick(int position) {

            }
        });
        binding.recyclerView.setAdapter(offeredFAdapter);
    }

    public void fetchData() {
        if (!Utils.isNetworkAvailable(getContext())) {
            init_Data(null, true);
            return;
        }
        try {
            ProgressHolder holder = (ProgressHolder) binding.recyclerView.findViewHolderForAdapterPosition(offeredFAdapter.getItemCount() - 1);
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

        Call<OfferedNurse_F_Model> call = RetrofitClient.getInstance().getFacilityApi()
                .call_job_offered_list(user_id1, current_Page1);

        call.enqueue(new Callback<OfferedNurse_F_Model>() {
            @Override
            public void onResponse(Call<OfferedNurse_F_Model> call, Response<OfferedNurse_F_Model> response) {
                if (response == null || response.body() == null) {
                    init_Data(null, false);
                    return;
                }
                if (response.isSuccessful()) {
                    OfferedNurse_F_Model facilityJobModel = response.body();
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
            public void onFailure(Call<OfferedNurse_F_Model> call, Throwable t) {
                init_Data(null, false);
                Log.d("TAG", getContext().getClass().getSimpleName() + " onFailure: " + t.getMessage());
            }
        });

    }


    private void init_Data(OfferedNurse_F_Model NurseModel, boolean isNetwork) {
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
                            ProgressHolder holder = (ProgressHolder) binding.recyclerView.findViewHolderForAdapterPosition(offeredFAdapter.getItemCount() - 1);
                            if (holder != null && holder.item_tv_msg != null) {
                                holder.item_tv_msg.setText("No Internet Connectivity Found !");
                            }
                        } catch (Exception exception) {
                            Log.d("TAG", "init_Data: " + exception.getMessage());
                        }
                    }
                } else {
                    errorProgress(false);
                }
            } else if (listPostedJob != null && listPostedJob.size() != 0) {
                dismissProgress();
            } else {
                errorProgress(true);
                binding.tvMsg.setText("Yet, No Job Found !");
                isFirstTime=true;
            }
        } else {
            dismissProgress();
            if (currentPage != PAGE_START)
                offeredFAdapter.removeLoading();
            offeredFAdapter.addItems(NurseModel.getData().getData());
//            binding.swipeRefresh.setRefreshing(false);
            currentPage = Integer.parseInt(NurseModel.getData().getCurrentPage());
            totalPage = Integer.parseInt(NurseModel.getData().getTotalPagesAvailable());
            PaginationListener.PAGE_SIZE = Integer.parseInt(NurseModel.getData().getResultsPerPage());
            if (currentPage < totalPage) {
                offeredFAdapter.addLoading();
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
        if (offeredFAdapter != null)
            offeredFAdapter.clear();
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
        if (isFirstTime) {
            refreshData();
        }
        Log.d("TAG", "onResume: Of ");
    }

    @Override
    public void onPause() {
        super.onPause();
        isFragActive = false;
        Log.d("TAG", "onPause: Of ");
    }

}