package com.nurseify.app.screen.facility.my_jobs;

import static com.nurseify.app.utils.PaginationListener.PAGE_START;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.nurseify.app.R;
import com.nurseify.app.adapter.Completed_F_Adapter;
import com.nurseify.app.adapter.ProgressHolder;
import com.nurseify.app.adapter.TabAdapter;
import com.nurseify.app.databinding.DialogRatingBinding;
import com.nurseify.app.databinding.DialogReviewBinding;
import com.nurseify.app.databinding.FragmentNurseBinding;
import com.nurseify.app.intermediate.ItemCallback;
import com.nurseify.app.screen.facility.model.FacilityJobModel;
import com.nurseify.app.screen.facility.model.Facility_JobDatum;
import com.nurseify.app.screen.facility.ui.MyJobFFragment;
import com.nurseify.app.screen.nurse.adapters.RatingAdapter;
import com.nurseify.app.screen.nurse.model.CompletedJobModel;
import com.nurseify.app.screen.nurse.model.ResponseModel;
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
    public String selected_onBoard;
    public String selected_onNurse;
    public String selected_onLeader;
    public String selected_onTool;

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
        setData();

        return view = binding.getRoot();
    }

    private void setData() {
        try {
            binding.layProgress.setOnTouchListener(touchListner);
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
                                if (listPostedJob != null && listPostedJob.size() != 0
                                        && currentPage < totalPage && !pastAdapter.isLoaderVisible) {
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


    }

    private void setAdapter() {
        pastAdapter = new Completed_F_Adapter(getActivity(), listPostedJob, new ItemCallback() {

            @Override
            public void onClick(int position) {
                if (listPostedJob.get(position).getRating_flag() != null
                        && listPostedJob.get(position).getRating_flag().equals("0")) {
                    ratingDailog(listPostedJob.get(position).getJobId(), listPostedJob.get(position).getOffered_nurse_id());
                } else {
                    open_rated_popup(listPostedJob.get(position).getRatingComment());
                }
            }
        });
        binding.recyclerView.setAdapter(pastAdapter);
    }

    private void open_rated_popup(Facility_JobDatum.RatingComment ratingComment) {
        DialogReviewBinding dialogRatingBinding = DialogReviewBinding.inflate(getActivity().getLayoutInflater());
        final Dialog dialog = new Dialog(getActivity(), R.style.AlertDialog);
        dialog.setContentView(dialogRatingBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        try {
            Glide.with(getActivity()).load(ratingComment.getNurseImage())
                    .placeholder(R.drawable.person).error(R.drawable.person)
                    .into(dialogRatingBinding.imgProfile);
        } catch (Exception e) {

        }
        dialogRatingBinding.tvFacilityName.setText(ratingComment.getNurseName());
        dialogRatingBinding.tvRating.setText(ratingComment.getRating());
        if (!TextUtils.isEmpty(ratingComment.getRating()))
            dialogRatingBinding.reatingBar.setRating(Float.parseFloat(ratingComment.getRating()));
        dialogRatingBinding.tvReview.setText(ratingComment.getExperience());
        dialogRatingBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }

    private void ratingDailog(String jobId, String offered_nurse_id) {
//        final View loc = getLayoutInflater().from(CompletedJobDetailsActivity.this).inflate(R.layout.dialog_rating, null);
        DialogRatingBinding dialogRatingBinding = DialogRatingBinding.inflate(getActivity().getLayoutInflater());
        final Dialog dialog = new Dialog(getActivity(), R.style.AlertDialog);
        dialog.setContentView(dialogRatingBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        dialogRatingBinding.tvTitle.setText("Rate The Nurse");
        dialogRatingBinding.tv1.setText("Clinical Skills");
        dialogRatingBinding.tv2.setText("Nurse Teamwork");
        dialogRatingBinding.tv3.setText("Interpersonal Skills");
        dialogRatingBinding.tv4.setText("Work Ethic ");
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
        RatingAdapter ratingAdapter1 = new RatingAdapter(Complete_Jobs_Fragment.this, 1, onBoard, true);
        dialogRatingBinding.rvOnBoard.setAdapter(ratingAdapter1);
        RatingAdapter ratingAdapter2 = new RatingAdapter(Complete_Jobs_Fragment.this, 2, onBoard, true);
        dialogRatingBinding.rvNurse.setAdapter(ratingAdapter2);
        RatingAdapter ratingAdapter3 = new RatingAdapter(Complete_Jobs_Fragment.this, 3, onBoard, true);
        dialogRatingBinding.rvLeader.setAdapter(ratingAdapter3);
        RatingAdapter ratingAdapter4 = new RatingAdapter(Complete_Jobs_Fragment.this, 4, onBoard, true);
        dialogRatingBinding.rvTools.setAdapter(ratingAdapter4);


        dialogRatingBinding.textSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    if (Utils.isNetworkAvailable(getActivity())) {
                        performRating_Call((int) dialogRatingBinding.reatingBar.getRating(),
                                dialogRatingBinding.edReview.getText().toString());
                    } else {
                        Utils.displayToast(getActivity(), getActivity().getResources().getString(R.string.no_internet));
                    }
                }
//                dialog.dismiss();
            }

            private boolean checkValidation() {
                if (dialogRatingBinding.reatingBar.getRating() == 0) {
                    Utils.displayToast(getActivity(), "Please give rating to nurse first !");
                    return false;
                }
                if (TextUtils.isEmpty(selected_onBoard)) {
                    Utils.displayToast(getActivity(), "Please Rate Clinical Skills !");
                    return false;
                }
                if (TextUtils.isEmpty(selected_onNurse)) {
                    Utils.displayToast(getActivity(), "Please Rate Nurse Teamwork !");
                    return false;
                }
                if (TextUtils.isEmpty(selected_onLeader)) {
                    Utils.displayToast(getActivity(), "Please Rate Interpersonal Skills !");
                    return false;
                }
                if (TextUtils.isEmpty(selected_onTool)) {
                    Utils.displayToast(getActivity(), "Please Work Ethic !");
                    return false;
                }
                if (TextUtils.isEmpty(dialogRatingBinding.edReview.getText().toString())) {
                    Utils.displayToast(getActivity(), "Please, enter your experience !");
                    return false;
                }

                return true;
            }

            private void performRating_Call(int rating, String review) {
                if (!Utils.isNetworkAvailable(getActivity())) {
                    Utils.displayToast(getActivity(), getActivity().getResources().getString(R.string.no_internet));
                    return;
                }
                Utils.displayToast(getActivity(), null); // to cancel toast if showing on screen
//                progressDialog.show();
                String user_id = new SessionManager(getActivity()).get_user_register_Id();
                RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
                RequestBody user_id11 = RequestBody.create(MediaType.parse("multipart/form-data"), offered_nurse_id);
                RequestBody user_id2 = RequestBody.create(MediaType.parse("multipart/form-data"), jobId);
                RequestBody user_id3 = RequestBody.create(MediaType.parse("multipart/form-data"), "" + rating);
                RequestBody user_id4 = RequestBody.create(MediaType.parse("multipart/form-data"), selected_onBoard);
                RequestBody user_id5 = RequestBody.create(MediaType.parse("multipart/form-data"), selected_onNurse);
                RequestBody user_id6 = RequestBody.create(MediaType.parse("multipart/form-data"), selected_onLeader);
                RequestBody user_id7 = RequestBody.create(MediaType.parse("multipart/form-data"), selected_onTool);
                RequestBody user_id8 = RequestBody.create(MediaType.parse("multipart/form-data"), review);

                Call<ResponseModel> call = RetrofitClient.getInstance().getFacilityApi()
                        .call_nurse_rating(user_id1, user_id11, user_id2, user_id3, user_id4, user_id5, user_id6, user_id7, user_id8);

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
                                Utils.displayToast(getActivity(), jobModel.getMessage());
                                dialog.dismiss();
                                ratingDailogDone();

                            } else {
                                Utils.displayToast(getActivity(), "Failed to submit your rating details");
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
        final View loc = getActivity().getLayoutInflater().from(getActivity()).inflate(R.layout.dialog_rating_done, null);
        final Dialog dialog = new Dialog(getActivity(), R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        dialog.show();
        TextView text_ok = dialog.findViewById(R.id.text_ok);
        text_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                refreshData();
            }
        });
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
                isFirstTime = true;

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
        if (isFirstTime) {
            refreshData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isFragActive = false;
    }

}