package com.weboconnect.nurseify.screen.facility.browse;

import static com.weboconnect.nurseify.utils.PaginationListener.PAGE_START;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.NursesAdapter;
import com.weboconnect.nurseify.adapter.ProgressHolder;
import com.weboconnect.nurseify.databinding.FragmentNurseBinding;
import com.weboconnect.nurseify.screen.facility.model.NurseDatum;
import com.weboconnect.nurseify.screen.facility.model.NurseModel;
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


public class Nurse_Browse_Fragment extends Fragment {
    String id;
    FragmentNurseBinding binding;
    View view;
    private NursesAdapter nursesAdapter;
    private boolean isFirstTime = true;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    private List<NurseDatum> listPostedJob = new ArrayList<>();

    public Nurse_Browse_Fragment() {
    }

    public Nurse_Browse_Fragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nurse, null, false);

        nursesAdapter = new NursesAdapter(getActivity(), new ArrayList<>(), new NursesAdapter.NurseListener() {

            @Override
            public void onClick_Msg(NurseDatum model, int position) {

            }

            @Override
            public void onClick_Like(NurseDatum model, int position) {

            }

            @Override
            public void onClick_Hire(NurseDatum model, int position) {

            }
        });
        binding.recyclerView.setAdapter(nursesAdapter);

        binding.recyclerView.addOnScrollListener(new PaginationListener((LinearLayoutManager) binding.recyclerView.getLayoutManager()) {
            @Override
            protected void loadMoreItems() {

            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return false;
            }

            @Override
            public void fetchData() {

            }

            @Override
            public void refreshData() {

            }
        });
        refreshData();

        return view = binding.getRoot();
    }

    public void fetchData() {
        if (!Utils.isNetworkAvailable(getContext())) {
            init_Data(null, true);
            return;
        }
        try {
            ProgressHolder holder = (ProgressHolder) binding.recyclerView.findViewHolderForAdapterPosition(nursesAdapter.getItemCount() - 1);
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
        RequestBody current_Page1 = RequestBody.create(MediaType.parse("multipart/form-data"), "" + currentPage);

        Call<NurseModel> call = RetrofitClient.getInstance().getFacilityApi()
                .call_browse_nurse(current_Page1);

        call.enqueue(new Callback<NurseModel>() {
            @Override
            public void onResponse(Call<NurseModel> call, Response<NurseModel> response) {
                if (response == null || response.body() == null) {
                    init_Data(null, false);
                    return;
                }
                if (response.isSuccessful()) {
                    NurseModel jobPostedModel = response.body();
                    if (!jobPostedModel.getApiStatus().equals("1")) {
                        init_Data(jobPostedModel, false);
                    } else {
                        init_Data(jobPostedModel, false);
                    }
                } else {
                    init_Data(null, false);
                }
            }

            @Override
            public void onFailure(Call<NurseModel> call, Throwable t) {
                init_Data(null, false);
                Log.d("TAG", getContext().getClass().getSimpleName() + " onFailure: " + t.getMessage());
            }
        });

    }

    private void init_Data(NurseModel jobPostedModel, boolean isNetwork) {
        if (jobPostedModel == null ||
                ((jobPostedModel.getData() == null
                        || jobPostedModel.getData().size() == 0))) {
            if (isNetwork) {
                if (listPostedJob != null && listPostedJob.size() != 0) {
                    dismissProgress();
                    if (currentPage != PAGE_START) {
//                        jobPostAdapter.removeLoading();
//                        binding.swipeRefresh.setRefreshing(false);
                        isLoading = false;
                        currentPage--;
                        try {
                            ProgressHolder holder = (ProgressHolder) binding.recyclerView.findViewHolderForAdapterPosition(nursesAdapter.getItemCount() - 1);
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
            }
        } else {
            dismissProgress();
            if (currentPage != PAGE_START) nursesAdapter.removeLoading();
            nursesAdapter.addItems(jobPostedModel.getData());
//            binding.swipeRefresh.setRefreshing(false);
            /*currentPage = Integer.parseInt(jobPostedModel.getData().getCurrentPage());
            totalPage = jobPostedModel.getData().getTotalPages();
            PaginationListener.PAGE_SIZE = Integer.parseInt(jobPostedModel.getData().getPerPage());
            if (currentPage < totalPage) {
                nursesAdapter.addLoading();
            } else {
                isLastPage = true;
            }*/
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
        nursesAdapter.clear();
        fetchData();
    }
}