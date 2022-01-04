package com.weboconnect.nurseify.screen.facility.browse;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.PastAdapter;
import com.weboconnect.nurseify.adapter.ProgressHolder;
import com.weboconnect.nurseify.databinding.FragmentNurseBinding;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.screen.facility.model.NurseDatum;
import com.weboconnect.nurseify.screen.facility.model.NurseModel;
import com.weboconnect.nurseify.screen.facility.ui.BrowseFFragment;
import com.weboconnect.nurseify.screen.facility.viewModel.Browse_Nurse_ViewModel;
import com.weboconnect.nurseify.screen.facility.viewModel.DialogStatus;
import com.weboconnect.nurseify.screen.facility.viewModel.DialogStatusMessage;
import com.weboconnect.nurseify.screen.facility.viewModel.ProgressUIType;
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


public class Past_Browse_Fragment extends Fragment {
    String id;
    FragmentNurseBinding binding;
    View view;
    private boolean isFirstTime = true;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    private PastAdapter pastAdapter;
    private Browse_Nurse_ViewModel viewModel;
    private boolean isFragActive = false;
    private boolean isFilterApply = false;
    private List<NurseDatum> listPostedJob= new ArrayList<>();

    public Past_Browse_Fragment() {
    }

    public Past_Browse_Fragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nurse, null, false);

        viewModel = new ViewModelProvider(requireActivity()).get(Browse_Nurse_ViewModel.class);

        setAdapter();
        observeer_View();
        setData();
        refreshData();
        return view = binding.getRoot();
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
                                pastAdapter.removeLoading();
                                pastAdapter.getFilter().filter(text);
                                pastAdapter.addLoading();
//                                binding.recyclerView.addOnScrollListener(pagination);
                                isFilterApply = false;
                            } else {
//                                binding.recyclerView.addOnScrollListener(null);
                                pastAdapter.removeLoading();
                                pastAdapter.getFilter().filter(text);
                                isFilterApply = true;
                            }
                        }
                    }
                });

        } catch (Exception e) {

        }
    }

    private void observeer_View() {
        binding.layProgress.setOnTouchListener(touchListner);
        viewModel.getProgressBar().observe(requireActivity(), new Observer<ProgressUIType>() {
            @Override
            public void onChanged(ProgressUIType progressUIType) {
                if (progressUIType == ProgressUIType.SHOW) {
                    binding.layProgress.setVisibility(View.VISIBLE);
                } else if (progressUIType == ProgressUIType.DIMISS) {
                    setAdapter();
                    binding.layProgress.setVisibility(View.GONE);
                } else if (progressUIType == ProgressUIType.CANCEL) {
                    binding.layProgress.setVisibility(View.GONE);
                } else if (progressUIType == ProgressUIType.DATA_ERROR) {
                    binding.layProgress.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getDialogStatus().observe(requireActivity(), new Observer<DialogStatusMessage>() {
            @Override
            public void onChanged(DialogStatusMessage dialogStatusMessage) {
                if (dialogStatusMessage.getDialogStatus() == DialogStatus.Done
                        && dialogStatusMessage.getDialogType() == 1) {
//                    open_filter();
                }
            }
        });
    }

    private void setAdapter() {
        pastAdapter = new PastAdapter(getActivity(), listPostedJob, new ItemCallback() {

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


        Call<NurseModel> call = RetrofitClient.getInstance().getFacilityApi()
                .call_job_past_list(user_id1, current_Page1);

        call.enqueue(new Callback<NurseModel>() {
            @Override
            public void onResponse(Call<NurseModel> call, Response<NurseModel> response) {
                if (response == null || response.body() == null) {
                    init_Data(null, false);
                    return;
                }
                if (response.isSuccessful()) {
                    NurseModel NurseModel = response.body();
                    if (!NurseModel.getApiStatus().equals("1")) {
                        init_Data(NurseModel, false);
                    } else {
                        init_Data(NurseModel, false);
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


    private void init_Data(NurseModel NurseModel, boolean isNetwork) {
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