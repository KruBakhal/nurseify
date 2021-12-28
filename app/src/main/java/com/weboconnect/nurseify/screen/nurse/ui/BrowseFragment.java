package com.weboconnect.nurseify.screen.nurse.ui;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.google.android.material.slider.RangeSlider;
import com.google.gson.Gson;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.nurse.adapters.FacilityAdapter;
import com.weboconnect.nurseify.adapter.HourlyRateWindowAdapter;
import com.weboconnect.nurseify.adapter.WorkHistoryWindowAdapter;
import com.weboconnect.nurseify.intermediate.API_ResponseCallback;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.databinding.DialogFilterBinding;
import com.weboconnect.nurseify.databinding.FragmentBrowseBinding;
import com.weboconnect.nurseify.intermediate.FacilityListCallback;
import com.weboconnect.nurseify.screen.nurse.Browse_Facility_Offered_JobDetailsActivity;
import com.weboconnect.nurseify.screen.nurse.adapters.BrowserJobsAdapter;
import com.weboconnect.nurseify.screen.nurse.model.FacilityJobModel;
import com.weboconnect.nurseify.screen.nurse.model.JobModel;
import com.weboconnect.nurseify.screen.nurse.model.PrivacyPolicyModel;
import com.weboconnect.nurseify.screen.nurse.model.ResponseModel;
import com.weboconnect.nurseify.screen.nurse.model.SpecialtyModel;
import com.weboconnect.nurseify.screen.nurse.model.SpecialtyDatum;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitApi;
import com.weboconnect.nurseify.webService.RetrofitClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
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
    public int search_location = 75002;
    public int selected_open_assignment_type = 0;
    public int selected_facility_type = 0;
    public int selected_electronic_medical_records = 0;
    private String user_id = " ";
    boolean isJobType = true;
    private List<JobModel.JobDatum> list_jobs = new ArrayList<>();
    private List<FacilityJobModel.Facility> list_facility = new ArrayList<>();
    private BrowserJobsAdapter browserJobsAdapter;
    private FacilityAdapter facilityAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog progressDialog;
    private List<SpecialtyDatum> list_assignment = new ArrayList<>();
    private List<SpecialtyDatum> list_facilty_type = new ArrayList<>();
    private List<SpecialtyDatum> list_media = new ArrayList<>();
    private Dialog dialog;
    private String zipCity;
    private int hours1 = 1;
    private int hours2 = 1;
    private String str_terms_conditions;
    Pattern patternAlphabetNumbers = Pattern.compile("^[a-zA-Z0-9]*$");

    public BrowseFragment() {
    }

    public BrowseFragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_browse, null, false);
        list_facility = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
//        binding.recyclerViewJobs.setAdapter(new JobAdapter(getActivity(), 1));
//        facilityAdapter = new FacilityAdapter(getActivity(), list_facility, facilityListCallback);
        click();
        layoutManager = binding.recyclerViewJobs.getLayoutManager();
        binding.recyclerViewJobs.addOnScrollListener(recyclerViewOnScrollListener);
//        fecthBrowseFacility();
        ((SimpleItemAnimator) binding.recyclerViewJobs.getItemAnimator()).setSupportsChangeAnimations(false);

        return view = binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Utils.isNetworkAvailable(getContext())) {
            if (isJobType) {
                fecthBrowseJobs(false, null, null, null);
            } else {

            }
        } else {
            errorProgress(false);
        }
    }

    private void openFilter() {
        DialogFilterBinding dialogFilterBinding =
                DialogFilterBinding.inflate(getLayoutInflater());
        dialog = new Dialog(getContext(), R.style.AlertDialog);
        dialog.setContentView(dialogFilterBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialogFilterBinding.tvZip.setText(zipCity);
        dialogFilterBinding.distanceSlider.setValues(Float.valueOf(hours1), Float.valueOf(hours2));
        dialogFilterBinding.tvHourRate.setText("$" + dialogFilterBinding.distanceSlider.getValues().get(0).intValue()
                + "/hr - $" + dialogFilterBinding.distanceSlider.getValues().get(1).intValue() + "/hr");
        dialogFilterBinding.distanceSlider.setLeft(hours1);
        dialogFilterBinding.distanceSlider.setRight(hours2);
        dialogFilterBinding.spinnerAssignment.setText(list_assignment.get(selected_open_assignment_type).getName());
        dialogFilterBinding.spinnerFacility.setText(list_facilty_type.get(selected_facility_type).getName());
        dialogFilterBinding.spinnerElectric.setText(list_media.get(selected_electronic_medical_records).getName());
        dialogFilterBinding.tvZip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    dialogFilterBinding.tvZip.setError(null);
                else if (!patternAlphabetNumbers.matcher(s.toString()).find() || s.toString().length() < 2) {
                    dialogFilterBinding.tvZip.setError("Enter City Name / Zip Code properly!");
                } else {
                    dialogFilterBinding.tvZip.setError(null);
                }
            }
        });


        dialogFilterBinding.distanceSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                hours1 = slider.getValues().get(0).intValue();
                hours2 = slider.getValues().get(1).intValue();
                dialogFilterBinding.tvHourRate.setText("$" + slider.getValues().get(0).intValue()
                        + "/hr - $" + slider.getValues().get(1).intValue() + "/hr");

            }
        });
        dialogFilterBinding.layAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboardFrom(getContext(), v);
                showOptionPopup_Filter(getContext(), 6,
                        dialogFilterBinding.spinnerAssignment, dialogFilterBinding.view1,
                        dialogFilterBinding.img1
                        , Collections.singletonList(list_assignment), new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        dialogFilterBinding.layFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(getContext(), v);
                showOptionPopup_Filter(getContext(), 7, dialogFilterBinding.spinnerFacility, dialogFilterBinding.view2,
                        dialogFilterBinding.img2
                        , Collections.singletonList(list_facilty_type), new ItemCallback() {
                            @Override
                            public void onClick(int position) {


                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        dialogFilterBinding.layElectric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboardFrom(getContext(), v);
                showOptionPopup_Filter(getContext(), 8, dialogFilterBinding.spinnerElectric, dialogFilterBinding.view3,
                        dialogFilterBinding.img3
                        , Collections.singletonList(list_media), new ItemCallback() {
                            @Override
                            public void onClick(int position) {

                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        dialogFilterBinding.closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboardFrom(getContext(), v);
                dialog.dismiss();
            }
        });
        dialogFilterBinding.textReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zipCity = "";
                selected_open_assignment_type = 0;
                selected_facility_type = 0;
                selected_electronic_medical_records = 0;
                hours1 = 4;
                hours2 = 5;
                dialogFilterBinding.tvZip.setText(zipCity);
                dialogFilterBinding.distanceSlider.setValues(4.0F, 5.0F);
//                dialogFilterBinding.rangeSeekbar3.setValues(4.0F, 5.0F);
                dialogFilterBinding.tvHourRate.setText("$" + dialogFilterBinding.rangeSeekbar3.getSelectedMinValue().intValue()
                        + "/hr - $" + dialogFilterBinding.rangeSeekbar3.getSelectedMaxValue().intValue() + "/hr");
                dialogFilterBinding.spinnerAssignment.setText(list_assignment.get(selected_open_assignment_type).getName());
                dialogFilterBinding.spinnerFacility.setText(list_facilty_type.get(selected_facility_type).getName());
                dialogFilterBinding.spinnerElectric.setText(list_media.get(selected_electronic_medical_records).getName());

                Utils.displayToast(getContext(), "All Field Value has been reset !");
//                dialog.dismiss();
            }
        });
        dialogFilterBinding.textApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    dialog.dismiss();
                    zipCity = dialogFilterBinding.tvZip.getText().toString();
                    hours1 = dialogFilterBinding.rangeSeekbar3.getSelectedMinValue().intValue();
                    hours2 = dialogFilterBinding.rangeSeekbar3.getSelectedMaxValue().intValue();
                    selected_page = 1;
                    fecthBrowseJobs(true, dialogFilterBinding.tvZip.getText().toString(),
                            "" + dialogFilterBinding.rangeSeekbar3.getSelectedMinValue().intValue()
                            , "" + dialogFilterBinding.rangeSeekbar3.getSelectedMaxValue().intValue());

                }
            }

            private boolean checkValidation() {
                String zipCity = dialogFilterBinding.tvZip.getText().toString();
                int hours1 = dialogFilterBinding.rangeSeekbar3.getSelectedMinValue().intValue();
                int hours2 = dialogFilterBinding.rangeSeekbar3.getSelectedMaxValue().intValue();

                if (TextUtils.isEmpty(zipCity)) {
                    Utils.displayToast(getContext(), "Enter Zip/City First !");
                    return false;
                }
                if (selected_open_assignment_type == 0) {
                    Utils.displayToast(getContext(), "Select Assignment Type First !");
                    return false;
                }
                if (selected_facility_type == 0) {
                    Utils.displayToast(getContext(), "Select Facility Type First !");
                    return false;
                }
                if (selected_electronic_medical_records == 0) {
                    Utils.displayToast(getContext(), "Select Electronic Media Record  First !");
                    return false;
                }

                if (hours1 == hours2) {
                    Utils.displayToast(getContext(), "Select proper range of hours rate  !");
                    return false;
                }
                return true;
            }
        });

//        dialogFilterBinding.rangeSeekbar3.setMaxStartValue(hours1);
//        dialogFilterBinding.rangeSeekbar3.setMinStartValue(hours2);
        dialogFilterBinding.rangeSeekbar3.setMinValue(1.0f);
        dialogFilterBinding.rangeSeekbar3.setMaxValue(100.0f);

// set listener
        dialogFilterBinding.rangeSeekbar3.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
//                tvMin.setText(String.valueOf(minValue));
//                tvMax.setText(String.valueOf(maxValue));
                hours1 = minValue.intValue();
                hours2 = maxValue.intValue();
                dialogFilterBinding.tvHourRate.setText("$" + minValue.intValue()
                        + "/hr - $" + maxValue.intValue() + "/hr");
            }
        });

// set final value listener
        dialogFilterBinding.rangeSeekbar3.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });
        dialog.show();
    }

    private void showOptionPopup_Filter(Context context, int type, TextView textView, View view1, ImageView img1,
                                        List<Object> list_nurse_degrees,
                                        ItemCallback itemCallback) {
        if (list_nurse_degrees == null || list_nurse_degrees.size() == 0) {
            Utils.displayToast(context, "data empty");
            return;
        }
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_dropdown, null);

        PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int height = 193;
        popup.setWidth(view1.getWidth());
        popup.setHeight(getActivity().getWindowManager().getDefaultDisplay().getHeight() * height / 1080);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.rv_pop);
        img1.setRotation(-180);
        View finalImg = img1;
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finalImg.setRotation(0);
            }
        });

        HourlyRateWindowAdapter parentChildAdapter = null;
        WorkHistoryWindowAdapter adapter_degree;
        adapter_degree = new WorkHistoryWindowAdapter(this, type,
                list_nurse_degrees,
                new WorkHistoryWindowAdapter.WorkHistoryWindowInterface() {
                    @Override
                    public void onCLickItem(int position, int type) {
                        if (type == 6) {
                            selected_open_assignment_type = position;
                            textView.setText(list_assignment.get(selected_open_assignment_type).getName());
                        } else if (type == 7) {
                            selected_facility_type = position;
                            textView.setText(list_facilty_type.get(selected_facility_type).getName());
                        } else if (type == 8) {
                            selected_electronic_medical_records = position;
                            textView.setText(list_media.get(selected_electronic_medical_records).getName());
                        }
                        itemCallback.onClick(position);
                        popup.dismiss();
                    }
                });


        recyclerView.setAdapter(adapter_degree);
        popup.showAsDropDown(view1, 0, 0);
    }

    private void fetch_the_filter_data(API_ResponseCallback apiResponseCallback) {
        progressDialog.show();
        RetrofitApi backendApi = RetrofitClient.getInstance().getNurseRetrofitApi();
        List<Observable<SpecialtyModel>> requests = new ArrayList<>();

        requests.add(backendApi.call_specialty());
        requests.add(backendApi.call_facility_types());
        requests.add(backendApi.call_get_medica_records());

        Observable.zip(requests, new Function<Object[], CombineCommonData>() {
            @Override
            public CombineCommonData apply(Object[] objects) throws Exception {
                List<SpecialtyModel> combinedNewsItems = new ArrayList<>();
                for (Object response : objects) {
                    combinedNewsItems.add((SpecialtyModel) response);
                }
                return new CombineCommonData(combinedNewsItems);
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread())
                .subscribe(new Observer<CombineCommonData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CombineCommonData combineService) {
                        Log.d("TAG", "onNext: " + combineService.mainServiceModels.size());
                        apiResponseCallback.onSucces(combineService);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onComplete() {

                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });

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

    private void fecthBrowseJobs(boolean b, String s, String s1, String s2) {

        Utils.displayToast(getContext(), null); // to cancel toast if showing on screen

        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.displayToast(getContext(), getResources().getString(R.string.no_internet));
            return;
        }
        Utils.displayToast(getContext(), null); // to cancel toast if showing on screen
        showProgress();
        String page = "" + selected_page;
        user_id = new SessionManager(getContext()).get_user_register_Id();

        Call<JobModel> call = null;
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        if (b) {
            RequestBody search_location1 = RequestBody.create(MediaType.parse("multipart/form-data"), s);
            RequestBody open_assignment_type1 = RequestBody.create(MediaType.parse("multipart/form-data"), list_assignment.get(selected_open_assignment_type).getId().toString());
            RequestBody facility_type1 = RequestBody.create(MediaType.parse("multipart/form-data"), list_facilty_type.get(selected_facility_type).getId().toString());
            RequestBody electronic_medical_records1 = RequestBody.create(MediaType.parse("multipart/form-data"), list_media.get(selected_electronic_medical_records).getId().toString());
            String range = s1 + "-" + s2;
            RequestBody rangeSLider = RequestBody.create(MediaType.parse("multipart/form-data"), range);

            call = RetrofitClient.getInstance().getNurseRetrofitApi()
                    .call_browser_filter_job(page, search_location1, open_assignment_type1,
                            facility_type1, electronic_medical_records1, user_id1, rangeSLider);

        } else
            call = RetrofitClient.getInstance().getNurseRetrofitApi()
                    .call_browser_job(page, user_id1);

        call.enqueue(new Callback<JobModel>() {
            @Override
            public void onResponse(Call<JobModel> call, Response<JobModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        errorProgress(true);
                        if (b)
                            openFilter();
                        return;
                    }
                    if (response.isSuccessful()) {
                        dismissProgress();
                        JobModel jobModel = response.body();
                        if (jobModel.getData() == null || jobModel.getData().size() == 0) {
                            Utils.displayToast(getContext(), "No Jobs found !");
                            if (b)
                                openFilter();
                            return;
                        }
                        if (list_jobs == null) {
                            list_jobs = new ArrayList<>();
                        }/*else {
                            list_jobs.clear();
                        }*/
                        if (list_jobs.size() > 0) {
                            list_jobs.addAll(jobModel.getData());
                            if (browserJobsAdapter != null) {
                                setAdapter();
                                browserJobsAdapter.notifyDataSetChanged();
                            } else
                                setAdapter();
                        } else {
                            list_jobs.addAll(jobModel.getData());
                            setAdapter();
                        }
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    } else {
                        errorProgress(true);
                        if (b)
                            openFilter();
                    }
                } catch (Exception e) {
                    errorProgress(true);
                }
            }

            @Override
            public void onFailure(Call<JobModel> call, Throwable t) {
                errorProgress(true);
                if (b)
                    openFilter();

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

        Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_like_job(user_id1, jobId1, isLiked1);

        String finalIsLiked = isLiked;
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {
                        dismissProgress();
                        return;
                    }
                    if (response.isSuccessful()) {
                        dismissProgress();
                        ResponseModel jobModel = response.body();
//                        Utils.displayToast(getContext(), "" + jobModel.getMessage());
                        datum.setIsLiked(finalIsLiked);
                        list_jobs.set(position, datum);
//                        browserJobsAdapter.setList(datum, position);
                        browserJobsAdapter.notifyItemChanged(position);
                        if (finalIsLiked.equals("0")) {
//                            isLiked = "1";
//                            holder.img_heart.setVisibility(View.VISIBLE);
//                            holder.img_heart1.setVisibility(View.GONE);
//                            holder.img_heart.setImageResource(R.drawable.heart);
                        } else {
//                            holder.img_heart.setVisibility(View.GONE);
//                            holder.img_heart1.setVisibility(View.VISIBLE);

//                            holder.img_heart.setImageResource(R.drawable.heart_press);
                        }
                    } else {
                        dismissProgress();
                    }
                } catch (Exception e) {

                    dismissProgress();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

                dismissProgress();

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


        Call<FacilityJobModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_browser_facility(page, user_id1);

        call.enqueue(new Callback<FacilityJobModel>() {
            @Override
            public void onResponse(Call<FacilityJobModel> call, Response<FacilityJobModel> response) {
                try {
                    assert response.body() != null;
                    if (!response.body().getApiStatus().equals("1")) {

                        dismissProgress();
                        return;
                    }
                    if (response.isSuccessful()) {
                        dismissProgress();
                        FacilityJobModel facilityModel = response.body();
                        if (list_facility == null) {
                            list_facility = new ArrayList<>();
                        }
                        list_facility.addAll(facilityModel.getData());
                        if (list_facility.size() > 0) {
                            if (facilityAdapter != null) {
                                setAdapter_Facility();
                                binding.textFacilities.performClick();
                            } else
                                setAdapter_Facility();
                        } else {
                            list_facility.addAll(facilityModel.getData());
                            setAdapter_Facility();
                        }


                    } else {

                        dismissProgress();
                    }
                } catch (Exception e) {

                    dismissProgress();
                }
            }

            @Override
            public void onFailure(Call<FacilityJobModel> call, Throwable t) {

                dismissProgress();

            }
        });


    }

    private void setAdapter_Facility() {
        facilityAdapter = new FacilityAdapter(getActivity(), list_facility, new FacilityListCallback() {
            @Override
            public void onFollow(int pos, String type, FacilityJobModel.Facility facility) {
                followFacility(pos, facility.getId(), type, facility);
            }

            @Override
            public void onLike(int pos, String like, FacilityJobModel.Facility facility) {
                likeFacility(pos, facility.getId(), like, facility);
            }

            @Override
            public void onClick(int position, FacilityJobModel.Facility facility) {
//                startActivityForResult(new Intent(getContext(), Browse_Facility_Offered_JobDetailsActivity.class)
//                        , Constant.REQUEST_Facility_FUNS);
            }
        });
        binding.recyclerViewJobs.setAdapter(facilityAdapter);
    }

    private void followFacility(int pos, String facilityId, String type, FacilityJobModel.Facility facility) {

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


        Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_follow_facility(user_id1, facility_id, type_);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.isSuccessful()) {
                        dismissProgress();
                        Log.e("follow", "Success");
//                        fecthBrowseFacility();
                        ResponseModel responseModel = response.body();
//                        Utils.displayToast(getContext(), "" + responseModel.getMessage());
                        facility.setIsFollow(Integer.valueOf(type));
                        list_facility.set(pos, facility);
                        facilityAdapter.notifyItemChanged(pos);
                    } else {
                        errorProgress(true);
                    }
                } catch (Exception e) {
                    errorProgress(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                errorProgress(true);

            }
        });


    }

    private void likeFacility(int pos, String facilityId, String like, FacilityJobModel.Facility facility) {

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


        Call<ResponseModel> call = RetrofitClient.getInstance().getNurseRetrofitApi()
                .call_like_facility(user_id1, facility_id, type_);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.isSuccessful()) {
                        dismissProgress();
                        Log.e("follow", "Success");
                        ResponseModel responseModel = response.body();
//                        Utils.displayToast(getContext(), "" + responseModel.getMessage());
                        facility.setIsLike(Integer.valueOf(like));
                        list_facility.set(pos, facility);
                        facilityAdapter.notifyItemChanged(pos);
                    } else {
                        errorProgress(true);
                    }
                } catch (Exception e) {
                    errorProgress(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                errorProgress(true);

            }
        });


    }

    private void click() {
        binding.imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((list_facilty_type == null || list_facilty_type.size() == 0)
                        || (list_assignment == null || list_assignment.size() == 0)
                        || (list_media == null || list_media.size() == 0)) {
                    API_ResponseCallback apiResponseCallback = new API_ResponseCallback() {
                        @Override
                        public void onShowProgress() {
                        }

                        @Override
                        public void onSucces(Object combineData) {
                            if (combineData == null)
                                return;
                            if (combineData instanceof CombineCommonData) {
                                CombineCommonData combineService = (CombineCommonData) combineData;
                                list_assignment = new ArrayList<>();
                                list_facilty_type = new ArrayList<>();
                                list_media = new ArrayList<>();
                                list_assignment.add(new SpecialtyDatum(0, "Please Assignment Type"));
                                list_facilty_type.add(new SpecialtyDatum(0, "Please Facility Type"));
                                list_media.add(new SpecialtyDatum(0, "Please Electronic Media Record"));
                                list_assignment.addAll(combineService.mainServiceModels.get(0).getData());
                                list_facilty_type.addAll(combineService.mainServiceModels.get(1).getData());
                                list_media.addAll(combineService.mainServiceModels.get(2).getData());
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        openFilter();
                                    }
                                });
                            } else {
                                Utils.displayToast(getContext(), "Please, retry again unable to get data !");
                            }
                        }

                        @Override
                        public void onFailed(String s) {

                        }

                        @Override
                        public void onError() {

                        }
                    };
                    fetch_the_filter_data(apiResponseCallback);
                } else {
                    openFilter();

                }

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
                if (list_jobs == null || list_jobs.size() == 0) {
                    fecthBrowseJobs(false, null, null, null);
                } else {
                    binding.recyclerViewJobs.setAdapter(browserJobsAdapter);
                    dismissProgress();
//                    binding.recyclerViewJobs.notify();
                }
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
                if (list_facility == null || list_facility.size() == 0) {
                    fecthBrowseFacility();
                } else {
                    binding.recyclerViewJobs.setAdapter(facilityAdapter);
//                    binding.recyclerViewJobs.notify();
                    dismissProgress();
                }
            }
        });
        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isJobType && browserJobsAdapter != null && list_jobs != null && list_jobs.size() != 0) {
                    try {
                        String text = binding.editTextSearch.getText().toString().toLowerCase();
                        browserJobsAdapter.getFilter().filter(text);
                    } catch (Exception e) {

                    }
                } else if (!isJobType && facilityAdapter != null && list_facility != null && list_facility.size() != 0) {

                    try {
                        String text = binding.editTextSearch.getText().toString().toLowerCase();
                        facilityAdapter.getFilter().filter(text);
                    } catch (Exception e) {

                    }
                }

            }
        });
    }

    private void setAdapter() {
        browserJobsAdapter = new BrowserJobsAdapter(getActivity(), list_jobs,
                new BrowserJobsAdapter.BrowseJobInteface() {
                    @Override
                    public void onClick_Like(JobModel.JobDatum datum, int position) {
                        list_jobs.set(position, datum);
//                        performLike(datum.getJobId(), datum.getIsLiked(), position, datum);
                    }

                    @Override
                    public void onClick_Apply(int position, JobModel.JobDatum datum) {
                        if (datum.getIsApplied().equals("1")) {
                            performApply(datum, position);
                        } else if (TextUtils.isEmpty(str_terms_conditions))
                            fetch_terms_conditions(datum, position);
                        else {
                            terms_conditions_Dialog(datum, str_terms_conditions, position);
                        }
                    }

                    @Override
                    public void onClick_Job(JobModel.JobDatum datum, int position) {
                        startActivityForResult(new Intent(getContext(), Browse_Facility_Offered_JobDetailsActivity.class)
                                .putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(datum))
                                .putExtra(Constant.POSITION, position), Constant.REQUEST_Browse_Job_FUNS);
                    }
                });
        binding.recyclerViewJobs.setAdapter(browserJobsAdapter);
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
                    Utils.displayToast(getContext(), "" + response.body().getMessage());
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
                        Utils.displayToast(getContext(), "Retry again, couldn't able to fetch data !");
                    }
                } else {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Utils.displayToast(getContext(), response.body().getMessage());
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

    private void terms_conditions_Dialog(JobModel.JobDatum datum, String str_terms_conditions, int position) {
        final View loc = getLayoutInflater().from(getContext()).inflate(R.layout.dialog_tearms, null);
        final Dialog dialog = new Dialog(getContext(), R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        ImageView closeDialog = dialog.findViewById(R.id.close_dialog);
        TextView tv_text = dialog.findViewById(R.id.tv_text);
        View sdsds = dialog.findViewById(R.id.sdsds);
        tv_text.setText(Html.fromHtml(str_terms_conditions));
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        TextView textApply = dialog.findViewById(R.id.text_apply);
        sdsds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                performApply(datum, position);

                dialog.dismiss();
            }
        });
    }

    private void performApply(JobModel.JobDatum datum, int position) {
        Utils.displayToast(getContext(), null); // to cancel toast if showing on screen

        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.displayToast(getContext(), getResources().getString(R.string.no_internet));
            return;
        }
        Utils.displayToast(getContext(), null); // to cancel toast if showing on screen
        progressDialog.show();
        String isApplied = datum.getIsApplied();
        if (isApplied.equals("0")) {
            isApplied = "1";
        } else {
            isApplied = "0";
        }
        user_id = new SessionManager(getContext()).get_user_register_Id();
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
//                        Utils.displayToast(getContext(), "" + jobModel.getMessage());
                        datum.setIsApplied(finalIsApplied);
                        list_jobs.set(position, datum);
                        browserJobsAdapter.notifyItemChanged(position);
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



    public class CombineCommonData {


        List<SpecialtyModel> mainServiceModels;

        public CombineCommonData(List<SpecialtyModel> mainServiceModels) {
            this.mainServiceModels = mainServiceModels;
        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_Browse_Job_FUNS) {
            if (resultCode == RESULT_OK) {
                int pos = data.getIntExtra(Constant.POSITION, 0);
                JobModel.JobDatum model = new Gson().fromJson(data.getStringExtra(Constant.STR_RESPONSE_DATA), Utils.typeJob);
                if (list_jobs != null && list_jobs.size() != 0 && pos < list_jobs.size()) {
                    list_jobs.set(pos, model);
                    if (browserJobsAdapter != null) {
                        browserJobsAdapter.notifyItemChanged(pos);
                    }
                }


            } else {


            }
        } else if (requestCode == Constant.REQUEST_Facility_FUNS) {
            if (resultCode == RESULT_OK) {
                int pos = data.getIntExtra(Constant.POSITION, 0);
                FacilityJobModel.Facility model = new Gson().fromJson(data.getStringExtra(Constant.STR_RESPONSE_DATA),
                        Utils.typeFacilityJob);
                if (list_facility != null && list_facility.size() != 0 && pos < list_facility.size()) {
                    list_facility.set(pos, model);
                    if (browserJobsAdapter != null) {
                        browserJobsAdapter.notifyItemChanged(pos);
                    }
                }
            } else {


            }
        }
    }
}