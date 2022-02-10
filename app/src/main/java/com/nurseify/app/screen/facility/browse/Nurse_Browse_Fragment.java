package com.nurseify.app.screen.facility.browse;

import static com.nurseify.app.utils.PaginationListener.PAGE_START;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.nurseify.app.R;
import com.nurseify.app.adapter.CommonDropDownAdapter;
import com.nurseify.app.adapter.HourlyRateWindowAdapter;
import com.nurseify.app.adapter.NursesAdapter;
import com.nurseify.app.adapter.PersonalDetailWindowAdapter;
import com.nurseify.app.adapter.ProgressHolder;
import com.nurseify.app.adapter.SpecialtyAdapter;
import com.nurseify.app.common.CommonDatum;
import com.nurseify.app.databinding.DialogFilterFBinding;
import com.nurseify.app.databinding.FragmentNurseBinding;
import com.nurseify.app.intermediate.API_ResponseCallback;
import com.nurseify.app.intermediate.ItemCallback;
import com.nurseify.app.screen.facility.HomeFActivity;
import com.nurseify.app.screen.facility.NurseDetailsActivity;
import com.nurseify.app.screen.facility.model.NurseDatum;
import com.nurseify.app.screen.facility.model.NurseModel;
import com.nurseify.app.screen.facility.ui.BrowseFFragment;
import com.nurseify.app.screen.facility.viewModel.Browse_Nurse_ViewModel;
import com.nurseify.app.screen.facility.viewModel.DialogStatus;
import com.nurseify.app.screen.facility.viewModel.DialogStatusMessage;
import com.nurseify.app.screen.facility.viewModel.ProgressUIType;
import com.nurseify.app.screen.nurse.model.CityDatum;
import com.nurseify.app.screen.nurse.model.CityModel;
import com.nurseify.app.screen.nurse.model.HourlyRate_DayOfWeek_OptionDatum;
import com.nurseify.app.screen.nurse.model.State_Datum;
import com.nurseify.app.utils.Constant;
import com.nurseify.app.utils.PaginationListener;
import com.nurseify.app.utils.SessionManager;
import com.nurseify.app.utils.Utils;
import com.nurseify.app.webService.RetrofitClient;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private boolean isFLiterTime = false;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    private List<NurseDatum> listPostedJob = new ArrayList<>();
    public Browse_Nurse_ViewModel viewModel;
    private SpecialtyAdapter daysOfWeekAdapter;
    private SpecialtyAdapter specialtyAdapter;
    private SpecialtyAdapter certificateAdapter;
    String state = "", city = "", zipcode = "", speciality = "", availability = "",
            keywords = "", bill_from = "", bill_to = "",
            certificate = "", tenure_from = "", tenure_to = "";
    private boolean isFragActive = false;
    private boolean isFilterApply = false;
    private RecyclerView.OnScrollListener pagination;
    private boolean isOpenFIlter = false;

    public Nurse_Browse_Fragment() {
    }

    public Nurse_Browse_Fragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nurse, null, false);
        viewModel = new ViewModelProvider(getActivity()).get(Browse_Nurse_ViewModel.class);
        binding.filter.setVisibility(View.VISIBLE);
        setAdapter_Nurse();
        observeer_View();
        setData();
        setAdapter_Nurse();
        return view = binding.getRoot();
    }

    private void observeer_View() {

        binding.layProgress.setOnTouchListener(touchListner);
        viewModel.getProgressBar().observe(requireActivity(), new Observer<ProgressUIType>() {
            @Override
            public void onChanged(ProgressUIType progressUIType) {
                if (progressUIType == ProgressUIType.SHOW) {
                    binding.layProgress.setVisibility(View.VISIBLE);
                } else if (progressUIType == ProgressUIType.DIMISS) {
                    setAdapter_Nurse();
                    binding.layProgress.setVisibility(View.GONE);
                } else if (progressUIType == ProgressUIType.CANCEL) {
                    binding.layProgress.setVisibility(View.GONE);
                } else if (progressUIType == ProgressUIType.DATA_ERROR) {
                    binding.layProgress.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getDialogStatus().observe(getViewLifecycleOwner(), new Observer<DialogStatusMessage>() {
            @Override
            public void onChanged(DialogStatusMessage dialogStatusMessage) {
                if (dialogStatusMessage.getDialogStatus() == DialogStatus.Done
                        && dialogStatusMessage.getDialogType() == 1) {
                    if (isFragActive)
                        open_filter();
                }
            }
        });

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
                                nursesAdapter.getFilter().filter(text);
                                isFilterApply = false;
                                if (listPostedJob != null && listPostedJob.size() != 0
                                        && currentPage < totalPage && !nursesAdapter.isLoaderVisible) {
                                    nursesAdapter.addLoading();
                                }
                            } else {
//                                binding.recyclerView.addOnScrollListener(null);
                                if (nursesAdapter.isLoaderVisible) {
                                    nursesAdapter.removeLoading();
                                }
                                nursesAdapter.getFilter().filter(text);
                                isFilterApply = true;
                            }
                        }
                    }
                });

        } catch (Exception e) {
            Log.d("TAG", "setData: " + e.getMessage());
        }
       /* binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!Utils.isNetworkAvailable(getContext())) {
                    Utils.displayToast(getContext(), getContext().getString(R.string.no_internet));
                    if (listPostedJob != null && listPostedJob.size() != 0)
                        binding.tvMsg.setText(getContext().getString(R.string.no_internet));
                    binding.swipeRefresh.setRefreshing(false);
                    return;
                }
                refreshData(true);
            }
        });*/
        binding.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (!isOpenFIlter && check_Any_is_empty()) {
                        viewModel.fetch_data(getContext());
                    } else {
                        open_filter();
                    }
                } catch (Exception exception) {
                    Log.d("TAG", "onClick: " + exception.getMessage());
                }
                Utils.onClickEvent(v);
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
    }

    private void open_filter() {
        @NonNull DialogFilterFBinding filterBinding = DialogFilterFBinding.inflate(getLayoutInflater(), null, false);
        final Dialog dialog = new Dialog(getContext(), R.style.AlertDialog);
        dialog.setContentView(filterBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        setAdapter_Days(filterBinding);
        setAdapter_Speciality(filterBinding);
        setAdapter_Certifcate(filterBinding);
        filterBinding.edKeywords.setText(keywords);
        filterBinding.edZipcode.setText(zipcode);
        if (!TextUtils.isEmpty(bill_from) && !TextUtils.isEmpty(bill_to)) {
            Float[] sd = new Float[2];
            sd[0] = Float.valueOf(bill_from);
            sd[1] = Float.valueOf(bill_to);
            filterBinding.sliderBillRate.setValues(sd);
        }
        if (!TextUtils.isEmpty(tenure_from) && !TextUtils.isEmpty(tenure_to)) {
            Float[] sd = new Float[2];
            sd[0] = Float.valueOf(tenure_from);
            sd[1] = Float.valueOf(tenure_to);
            filterBinding.sliderTenure.setValues(sd);
        }
        if (viewModel.selected_state != 0) {
            filterBinding.tvState.setText("" + viewModel.list_State.getValue().get(viewModel.selected_state).getName());
        }
        if (viewModel.selected_City != 0) {
            filterBinding.tvState.setText("" + viewModel.list_City.getValue().get(viewModel.selected_City).getName());
        }

        filterBinding.layState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                state_click_action(filterBinding);
            }
        });
        if (viewModel.select_speciality.size() == viewModel.getList_speciality().getValue().size()) {
            filterBinding.checkSpecailty.setChecked(true);
        } else
            filterBinding.checkSpecailty.setChecked(false);
        if (viewModel.select_daysOfWeek.size() == viewModel.getList_days_of_week().getValue().size()) {
            filterBinding.checkDays.setChecked(true);
        } else
            filterBinding.checkDays.setChecked(false);
        filterBinding.layCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.selected_state == 0) {
                    Utils.displayToast(getContext(), "Select State First !");
                    return;
                }
                if (viewModel.list_City == null || viewModel.list_City.getValue() == null ||
                        viewModel.list_City.getValue().size() == 0) {
                    Utils.displayToast(getContext(), "No City Found For Select State !");
                    return;
                }
                Utils.hideKeyboardFrom(getContext(), v);
                showOptionPopup_City(getContext(), filterBinding.view1, 7,
                        filterBinding.img1, filterBinding.tvCity
                        , viewModel.list_City.getValue(), viewModel.selected_City, new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_City = position;
                                filterBinding.tvCity
                                        .setText("" + viewModel.list_City.getValue()
                                                .get(position).getName());
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        filterBinding.laySpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup(getContext(), filterBinding.view4, 1, filterBinding.img4, filterBinding.tvSpecialty1,
                        viewModel.list_speciality.getValue(), viewModel.select_speciality
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
//                                viewModel.select_speciality = position;
                                if (viewModel.select_speciality == null)
                                    viewModel.select_speciality = new ArrayList<>();
                                if (position < viewModel.select_speciality.size() && viewModel.select_speciality.contains(position)) {
                                    viewModel.select_speciality.remove(Integer.parseInt(String.valueOf(position)));
                                } else
                                    viewModel.select_speciality.add(position);

                                filterBinding.tvSpecialty1.setVisibility(View.GONE);
                                filterBinding.rvSpecialty1.setVisibility(View.VISIBLE);
                                setAdapter_Speciality(filterBinding);
                                specialtyAdapter.notifyDataSetChanged();
                            }
                        }, false);
                Utils.onClickEvent(v);
            }
        });
        filterBinding.layWeeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup_Weeks(getContext(), 4, filterBinding.view8, filterBinding.img8, filterBinding.tvWeeksDays,
                        viewModel.list_days_of_week.getValue(), viewModel.select_daysOfWeek
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                if (viewModel.select_daysOfWeek == null)
                                    viewModel.select_daysOfWeek = new ArrayList<>();
                                if (position < viewModel.select_daysOfWeek.size() && viewModel.select_daysOfWeek.contains(position)) {
                                    viewModel.select_daysOfWeek.remove(Integer.parseInt(String.valueOf(position)));
                                } else
                                    viewModel.select_daysOfWeek.add(position);
                                filterBinding.tvWeeksDays.setVisibility(View.GONE);
                                filterBinding.rvWeeksDays.setVisibility(View.VISIBLE);
                                setAdapter_Days(filterBinding);
                                daysOfWeekAdapter.notifyDataSetChanged();
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        filterBinding.layCredential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboardFrom(getContext(), v);

                showOptionPopup(getContext(), filterBinding.view5, 11, filterBinding.img5,
                        filterBinding.tvCertificate,
                        viewModel.list_Certificate.getValue(), viewModel.selected_certificate,
                        new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                if (viewModel.selected_certificate == null)
                                    viewModel.selected_certificate = new ArrayList<>();
                                if (position < viewModel.selected_certificate.size() && viewModel.selected_certificate.contains(position)) {
                                    viewModel.selected_certificate.remove(Integer.parseInt(String.valueOf(position)));
                                } else
                                    viewModel.selected_certificate.add(position);
                                filterBinding.tvCertificate.setVisibility(View.GONE);
                                filterBinding.rvCertificate.setVisibility(View.VISIBLE);
                                setAdapter_Certifcate(filterBinding);
                                certificateAdapter.notifyDataSetChanged();
                            }
                        }, false);
                Utils.onClickEvent(v);
            }
        });
        filterBinding.checkDays.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewModel.last_days = new ArrayList<>();
                    viewModel.last_days.addAll(viewModel.select_daysOfWeek);
                    viewModel.select_daysOfWeek.clear();
                    check_all_days(1);
                    setAdapter_Days(filterBinding);
                    daysOfWeekAdapter.notifyDataSetChanged();
                } else {
                    viewModel.select_daysOfWeek.clear();
                    viewModel.select_daysOfWeek = new ArrayList<>();
                    viewModel.select_daysOfWeek.addAll(viewModel.last_days);
                    viewModel.last_days = new ArrayList<>();
                    setAdapter_Days(filterBinding);
                    daysOfWeekAdapter.notifyDataSetChanged();
                }
            }
        });
        filterBinding.checkSpecailty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewModel.last_speciality = new ArrayList<>();
                    viewModel.last_speciality.addAll(viewModel.select_speciality);
                    viewModel.select_speciality.clear();
                    check_all_days(2);
                    setAdapter_Speciality(filterBinding);
                    specialtyAdapter.notifyDataSetChanged();
                } else {
                    viewModel.select_speciality.clear();
                    viewModel.select_speciality = new ArrayList<>();
                    viewModel.select_speciality.addAll(viewModel.last_speciality);
                    viewModel.last_speciality = new ArrayList<>();
                    setAdapter_Speciality(filterBinding);
                    specialtyAdapter.notifyDataSetChanged();
                }
            }
        });
        filterBinding.closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Utils.onClickEvent(v);
            }
        });
        filterBinding.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_filter_params();
                refreshData(true);
                dialog.dismiss();
                Utils.onClickEvent(v);
            }
        });
        filterBinding.textApply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(filterBinding.edZipcode.getText().toString())) {
                    zipcode = filterBinding.edZipcode.getText().toString();

                }
                if (!TextUtils.isEmpty(filterBinding.edKeywords.getText().toString())) {
                    keywords = filterBinding.edKeywords.getText().toString();
                }

                bill_from = String.valueOf(filterBinding.sliderBillRate.getValues().get(0));
                bill_to = String.valueOf(filterBinding.sliderBillRate.getValues().get(1));

                tenure_from = String.valueOf(filterBinding.sliderTenure.getValues().get(0));
                tenure_to = String.valueOf(filterBinding.sliderTenure.getValues().get(1));

                isOpenFIlter = true;
//                apply_filter();
                refreshData(true);
                dialog.dismiss();
            }


        });
        dialog.show();
    }

    void apply_filter() {
        reset_params();
        Call<NurseModel> call = getFilterParams();
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

    private Call<NurseModel> getFilterParams() {
        int status = 0;
        if (viewModel.selected_state != 0) {
            state = viewModel.list_State.getValue().get(viewModel.selected_state).getIso_name();
            status++;
        }
        if (viewModel.selected_City != 0) {
            city = viewModel.list_City.getValue().get(viewModel.selected_City).getName();
            status++;
        }


        if (viewModel.select_speciality != null && viewModel.select_speciality.size() != 0) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < viewModel.select_speciality.size(); i++) {
                Integer pos = viewModel.select_speciality.get(i);
                jsonArray.put("" + viewModel.list_speciality.getValue().get(pos).getId());
            }
            Log.d("TAG", "check_validation: " + jsonArray.toString());
            speciality = jsonArray.toString();
            status++;
        }
        if (viewModel.select_daysOfWeek != null && viewModel.select_daysOfWeek.size() != 0) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < viewModel.select_daysOfWeek.size(); i++) {
                Integer pos = viewModel.select_daysOfWeek.get(i);
                jsonArray.put("" + viewModel.list_days_of_week.getValue().get(pos).getId());
            }
            Log.d("TAG", "check_validation: " + jsonArray.toString());
            availability = jsonArray.toString();
            status++;
        }
        if (viewModel.selected_certificate != null && viewModel.selected_certificate.size() != 0) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < viewModel.selected_certificate.size(); i++) {
                Integer pos = viewModel.selected_certificate.get(i);
                jsonArray.put("" + viewModel.list_Certificate.getValue().get(pos).getId());
            }
            Log.d("TAG", "check_validation: " + jsonArray.toString());
            certificate = jsonArray.toString();
            status++;
        }

        if (!Utils.isNetworkAvailable(getContext())) {
            init_Data(null, true);
            return null;
        }
        try {
            ProgressHolder holder = (ProgressHolder) binding.recyclerView.findViewHolderForAdapterPosition(nursesAdapter.getItemCount() - 1);
            if (holder != null && holder.item_tv_msg != null) {
                holder.item_tv_msg.setText(getString(R.string.loading_job));
            }
        } catch (Exception exception) {
            Log.d("TAG", "init_Data: " + exception.getMessage());
        }

        isOpenFIlter = true;
//        showProgress();

        MediaType mediatTypeStr = MediaType.parse("multipart/form-data");
        RequestBody request_1 = RequestBody.create(mediatTypeStr, "" + speciality);
        RequestBody request_2 = RequestBody.create(mediatTypeStr, "" + availability);
        RequestBody request_3 = RequestBody.create(mediatTypeStr, "" + keywords);
        RequestBody request_4 = RequestBody.create(mediatTypeStr, "" + bill_from);
        RequestBody request_5 = RequestBody.create(mediatTypeStr, "" + bill_to);
        RequestBody request_6 = RequestBody.create(mediatTypeStr, "" + tenure_from);
        RequestBody request_7 = RequestBody.create(mediatTypeStr, "" + tenure_to);
        RequestBody request_8 = RequestBody.create(mediatTypeStr, "" + certificate);
        RequestBody request_9 = RequestBody.create(mediatTypeStr, "" + currentPage);
        RequestBody request_10 = RequestBody.create(mediatTypeStr, "" + state);
        RequestBody request_11 = RequestBody.create(mediatTypeStr, "" + city);
        RequestBody request_12 = RequestBody.create(mediatTypeStr, "" + zipcode);

        Call<NurseModel> call = null;
        if (viewModel != null)
            call = viewModel.backendApi.call_apply_filter(request_1, request_2, request_3, request_4, request_5, request_6, request_7, request_8
                    , request_9, request_10, request_11, request_12);
        return call;
    }

    private void reset_filter_params() {
        isOpenFIlter = false;
        viewModel.selected_state = 0;
        viewModel.selected_City = 0;
        viewModel.select_speciality = new ArrayList<>();
        viewModel.select_daysOfWeek = new ArrayList<>();
        viewModel.selected_certificate = new ArrayList<>();
        state = "";
        city = "";
        zipcode = "";
        speciality = "";
        availability = "";
        keywords = "";
        bill_from = "";
        bill_to = "";
        certificate = "";
        tenure_from = "";
        tenure_to = "";
    }

    private void setAdapter_Certifcate(DialogFilterFBinding filterBinding) {
        certificateAdapter = new SpecialtyAdapter(getActivity(), this, viewModel.selected_certificate,
                viewModel.list_Certificate.getValue(), new SpecialtyAdapter.SpecialtyListener() {
            @Override
            public void onClickItem(int position) {
                if (viewModel.selected_certificate != null && viewModel.selected_certificate.size() != 0 && position < viewModel.selected_certificate.size()) {
                    viewModel.selected_certificate.remove(position);
                    if (certificateAdapter != null)
                        certificateAdapter.notifyDataSetChanged();
                    if (filterBinding.tvWeeksDays != null && filterBinding.rvWeeksDays != null)
                        if (viewModel.selected_certificate != null && viewModel.selected_certificate.size() != 0) {
                            filterBinding.tvCertificate.setVisibility(View.GONE);
                            filterBinding.rvCertificate.setVisibility(View.VISIBLE);
                        } else {
                            filterBinding.tvCertificate.setVisibility(View.VISIBLE);
                            filterBinding.rvCertificate.setVisibility(View.GONE);
                        }
                }
            }
        });
        filterBinding.rvCertificate.setAdapter(certificateAdapter);
        if (viewModel.selected_certificate != null && viewModel.selected_certificate.size() != 0) {
            filterBinding.tvCertificate.setVisibility(View.GONE);
            filterBinding.rvCertificate.setVisibility(View.VISIBLE);
        } else {
            filterBinding.tvCertificate.setVisibility(View.VISIBLE);
            filterBinding.rvCertificate.setVisibility(View.GONE);
        }
    }

    private void setAdapter_Days(DialogFilterFBinding filterBinding) {
        Collections.sort(viewModel.select_daysOfWeek, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 < o2 ? -1 : 1;
            }
        });
        daysOfWeekAdapter = new SpecialtyAdapter(getContext(), viewModel.select_daysOfWeek,
                viewModel.list_days_of_week.getValue(), 3, 3, new SpecialtyAdapter.SpecialtyListener() {
            @Override
            public void onClickItem(int position) {
                if (viewModel.select_daysOfWeek != null && viewModel.select_daysOfWeek.size() != 0 && position < viewModel.select_daysOfWeek.size()) {
                    viewModel.select_daysOfWeek.remove(position);
                    if (daysOfWeekAdapter != null)
                        daysOfWeekAdapter.notifyDataSetChanged();
                    if (filterBinding.tvWeeksDays != null && filterBinding.rvWeeksDays != null)
                        if (viewModel.select_daysOfWeek == null || viewModel.select_daysOfWeek.size() == 0) {
                            filterBinding.tvWeeksDays.setVisibility(View.VISIBLE);
                            filterBinding.rvWeeksDays.setVisibility(View.GONE);
                        } else {
                            filterBinding.tvWeeksDays.setVisibility(View.GONE);
                            filterBinding.rvWeeksDays.setVisibility(View.VISIBLE);
                        }
                }
            }
        });

        filterBinding.rvWeeksDays.setAdapter(daysOfWeekAdapter);
        if (viewModel.select_daysOfWeek != null && viewModel.select_daysOfWeek.size() != 0) {
            filterBinding.tvWeeksDays.setVisibility(View.GONE);
            filterBinding.rvWeeksDays.setVisibility(View.VISIBLE);
        } else {
            filterBinding.tvWeeksDays.setVisibility(View.VISIBLE);
            filterBinding.rvWeeksDays.setVisibility(View.GONE);
        }
    }

    private void setAdapter_Speciality(DialogFilterFBinding filterBinding) {
        specialtyAdapter = new SpecialtyAdapter(getActivity(), this, viewModel.select_speciality,
                viewModel.list_speciality.getValue(),
                new SpecialtyAdapter.SpecialtyListener() {
                    @Override
                    public void onClickItem(int position) {
                        if (viewModel.select_speciality != null && viewModel.select_speciality.size()
                                != 0 && position < viewModel.select_speciality.size()) {
                            viewModel.select_speciality.remove(position);
                            if (specialtyAdapter != null)
                                specialtyAdapter.notifyDataSetChanged();
                            if (filterBinding.tvSpecialty1 != null && filterBinding.rvSpecialty1 != null)
                                if (viewModel.select_speciality == null || viewModel.select_speciality.size() == 0) {
                                    filterBinding.tvSpecialty1.setVisibility(View.VISIBLE);
                                    filterBinding.rvSpecialty1.setVisibility(View.GONE);
                                } else {
                                    filterBinding.tvSpecialty1.setVisibility(View.GONE);
                                    filterBinding.rvSpecialty1.setVisibility(View.VISIBLE);
                                }
                        }
                    }


                });
        filterBinding.rvSpecialty1.setAdapter(specialtyAdapter);
        if (viewModel.select_speciality != null && viewModel.select_speciality.size() != 0) {
            filterBinding.tvSpecialty1.setVisibility(View.GONE);
            filterBinding.rvSpecialty1.setVisibility(View.VISIBLE);
        } else {
            filterBinding.tvSpecialty1.setVisibility(View.VISIBLE);
            filterBinding.rvSpecialty1.setVisibility(View.GONE);
        }
    }

    private void check_all_days(int type) {
        if (type == 1) {

            if (viewModel.select_daysOfWeek == null) {
                viewModel.select_daysOfWeek = new ArrayList<>();
            } else {
                viewModel.select_daysOfWeek.clear();
            }
            if (viewModel.last_days != null && viewModel.last_days.size() != 0)
                viewModel.select_daysOfWeek.addAll(viewModel.last_days);

            for (int i = 0; i < viewModel.list_days_of_week.getValue().size(); i++) {
                if (viewModel.last_days != null && viewModel.last_days.size() != 0
                        && viewModel.last_days.contains(i)) {
//                    return;
                } else
                    viewModel.select_daysOfWeek.add(i);
            }
        } else {
            if (viewModel.select_speciality == null) {
                viewModel.select_speciality = new ArrayList<>();
            } else {
                viewModel.select_speciality.clear();
            }
            if (viewModel.last_speciality != null && viewModel.last_speciality.size() != 0)
                viewModel.select_speciality.addAll(viewModel.last_speciality);
            for (int i = 0; i < viewModel.list_speciality.getValue().size(); i++) {
                if (viewModel.last_speciality != null && viewModel.last_speciality.size() != 0
                        && viewModel.last_speciality.contains(i)) {
//                    return;
                } else
                    viewModel.select_speciality.add(i);
            }
        }
    }

    private void state_click_action(DialogFilterFBinding setup1Binding) {
        showOptionPopup_State(getContext(), setup1Binding.view2, 3,
                setup1Binding.img2, setup1Binding.tvState
                , new ItemCallback() {
                    @Override
                    public void onClick(int position) {
                        viewModel.selected_state = position;
                        setup1Binding.tvState.setText("" + viewModel.getList_State()
                                .getValue().get(position).getNames());

                        call_city_task(position, false, setup1Binding);

                    }
                });
    }

    private void call_city_task(int position, boolean status, DialogFilterFBinding setup1Binding) {
        viewModel.fetch_state_by_City(viewModel.getList_State().getValue()
                        .get(position).getState_id(),
                new API_ResponseCallback() {
                    @Override
                    public void onShowProgress() {
                        setup1Binding.layProgress.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSucces(Object models) {
                        setup1Binding.layProgress.setVisibility(View.GONE);
                        if (models instanceof CityModel) {
                            CityModel combineData = (CityModel) models;
                            try {
                                viewModel.selected_City = 0;
                                setup1Binding.tvCity.setText("" + viewModel.getList_City().getValue()
                                        .get(viewModel.selected_City).getName());
                                if (status) {
                                    set_city_init_data(viewModel.getList_City().getValue());
                                }
                            } catch (Exception e) {
                                Log.d("TAG", "call_city_task() onError: " + e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailed(String s) {
                        setup1Binding.layProgress.setVisibility(View.GONE);
                        Utils.displayToast(getContext(), s);
                    }

                    private void set_city_init_data(List<CityDatum> list_City) {
                        viewModel.selected_City = 0;
                        setup1Binding.tvCity.setText(list_City.get(viewModel.selected_City).getName());
                    }

                    @Override
                    public void onError() {
                        setup1Binding.layProgress.setVisibility(View.GONE);
                    }
                });
    }

    private void setAdapter_Nurse() {
        nursesAdapter = new NursesAdapter(getActivity(), listPostedJob, new NursesAdapter.NurseListener() {

            @Override
            public void onClick_Msg(NurseDatum model, int position) {

            }

            @Override
            public void onClick_Like(NurseDatum model, int position) {

            }

            @Override
            public void onClick_Hire(NurseDatum model, int position) {
                model.setNurseLogo_base(null);
                startActivity(new Intent(getContext(), NurseDetailsActivity.class)
                        .putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(model))
                        .putExtra(Constant.ID, model.getUserId())
                );

            }
        });
        binding.recyclerView.setAdapter(nursesAdapter);

    }

    private void showOptionPopup_State(Context context, View v, int type, ImageView img1,
                                       TextView tvState, ItemCallback itemCallback) {
        if (viewModel.getList_State() == null || viewModel.getList_State().getValue() == null || viewModel.getList_State().getValue().size() == 0) {
            Utils.displayToast(getContext(), "No Data For State Found");
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
        popup.setWidth(v.getWidth());
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
        PersonalDetailWindowAdapter parentChildAdapter =
                new PersonalDetailWindowAdapter(((HomeFActivity) getActivity()), this,
                        viewModel.getList_State().getValue(), viewModel.getSelected_state(),
                        new PersonalDetailWindowAdapter.UserPopupWindowAdapterInterface() {
                            @Override
                            public void onCLickItem(int i, int position) {
                                itemCallback.onClick(i);
                                popup.dismiss();
                            }
                        });
        parentChildAdapter.setType(type);
        recyclerView.setAdapter(parentChildAdapter);
        recyclerView.scrollToPosition(viewModel.selected_state);
        popup.showAsDropDown(v, 0, 0);
    }

    private void showOptionPopup_City(Context context, View v, int type, ImageView img1,
                                      TextView tvState, List<CityDatum> cityData, int selected_City, ItemCallback
                                              itemCallback) {
        if (cityData == null || cityData.size() == 0) {
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
        popup.setWidth(v.getWidth());
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

        PersonalDetailWindowAdapter parentChildAdapter
                = new PersonalDetailWindowAdapter(((HomeFActivity) getActivity()), this
                , type
                , cityData, selected_City, new PersonalDetailWindowAdapter.UserPopupWindowAdapterInterface() {
            @Override
            public void onCLickItem(int position, int i) {
                itemCallback.onClick(position);
                popup.dismiss();
            }
        });
        recyclerView.setAdapter(parentChildAdapter);
        recyclerView.scrollToPosition(selected_City);
        popup.showAsDropDown(v, 0, 0);
    }

    private void showOptionPopup_Weeks(Context context, int type, View view4, ImageView img2,
                                       TextView tv_weeks_days,
                                       List<HourlyRate_DayOfWeek_OptionDatum> list_days_of_week,
                                       List<Integer> select_daysOfWeek, ItemCallback itemCallback) {
        if (list_days_of_week == null || list_days_of_week.size() == 0) {
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
        popup.setWidth(view4.getWidth());
        popup.setHeight(getActivity().getWindowManager().getDefaultDisplay().getHeight() * height / 1080);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.rv_pop);
        img2.setRotation(-180);
        View finalImg = img2;
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finalImg.setRotation(0);
            }
        });

        HourlyRateWindowAdapter parentChildAdapter = null;
        parentChildAdapter = new HourlyRateWindowAdapter((HomeFActivity) getActivity(), this,
                type,
                list_days_of_week,
                new HourlyRateWindowAdapter.HourlyRateWindowInterface() {
                    @Override
                    public void onCLickItem(int position, int type) {
                        itemCallback.onClick(position);
                        popup.dismiss();
                    }
                });
        recyclerView.setAdapter(parentChildAdapter);
        popup.showAsDropDown(view4, 0, 0);

    }


    private void showOptionPopup(Context context, View v, int type, ImageView img1,
                                 TextView tvEmr, List<CommonDatum> cityData,
                                 List<Integer> selected_City, ItemCallback itemCallback, boolean b) {
        if (cityData == null || cityData.size() == 0) {
            Utils.displayToast(context, "dropdown list is empty");
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
        popup.setWidth(v.getWidth());
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

        final CommonDropDownAdapter parentChildAdapter
                = new CommonDropDownAdapter((HomeFActivity) getActivity(), this
                , type
                , selected_City, cityData, new CommonDropDownAdapter.CommonDropDownInterface() {
            @Override
            public void onCLickItem(int position, int i) {
                if (b)
                    tvEmr.setText(cityData.get(position).getName());
                itemCallback.onClick(position);
                popup.dismiss();
            }
        });
        recyclerView.setAdapter(parentChildAdapter);
        recyclerView.scrollToPosition(0);
        popup.showAsDropDown(v, 0, 0);
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


        Call<NurseModel> call = null;
        if (isOpenFIlter) {
            call = getFilterParams();
        } else
            call = get_base_params();

        if (call != null)
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

    private Call<NurseModel> get_base_params() {
        String user_id = new SessionManager(getContext()).get_user_register_Id();
        RequestBody api_key = RequestBody.create(MediaType.parse("multipart/form-data"), new SessionManager(getContext()).get_API_KEY());
        RequestBody user_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody current_Page1 = RequestBody.create(MediaType.parse("multipart/form-data"), ""
                + currentPage);


        Call<NurseModel> call = RetrofitClient.getInstance().getFacilityApi()
                .call_browse_nurse(current_Page1);

        return call;
    }

    private void init_Data(NurseModel jobPostedModel, boolean isNetwork) {
        try {
            if (jobPostedModel == null ||
                    ((jobPostedModel.getData() == null || jobPostedModel.getData().getData() == null
                            || jobPostedModel.getData().getData().size() == 0))) {
                if (isNetwork) {
                    if (listPostedJob != null && listPostedJob.size() != 0) {
                        dismissProgress();
                        if (currentPage != PAGE_START) {
//                        jobPostAdapter.removeLoading();
//                        binding.swipeRefresh.setRefreshing(false);
                            isLoading = false;
                            currentPage--;

                            ProgressHolder holder = (ProgressHolder) binding.recyclerView.findViewHolderForAdapterPosition(nursesAdapter.getItemCount() - 1);
                            if (holder != null && holder.item_tv_msg != null) {
                                holder.item_tv_msg.setText("No Internet Connectivity Found !");
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
                    /*if (isOpenFIlter) {
                        open_filter();
                    }*/
                }
            } else {
                dismissProgress();
                if (currentPage != PAGE_START)
                    nursesAdapter.removeLoading();


                currentPage = Integer.parseInt(jobPostedModel.getData().getCurrentPage());
                totalPage = Integer.parseInt(jobPostedModel.getData().getTotalPagesAvailable());
                PaginationListener.PAGE_SIZE = Integer.parseInt(jobPostedModel.getData().getResultsPerPage());

                if (currentPage == totalPage) {
                    isLastPage = true;
                    nursesAdapter.removeLoading();
                }

                nursesAdapter.addItems(jobPostedModel.getData().getData());
//            binding.swipeRefresh.setRefreshing(false);


                if (currentPage < totalPage) {
                    nursesAdapter.addLoading();
                } else {
                    isLastPage = true;
//                nursesAdapter.removeLoading();
                }

                isLoading = false;
            }
        } catch (Exception exception) {
            Log.d("TAG", "init_Data: " + exception.getMessage());
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
            binding.tvMsg.setText(getResources().getString(R.string.something_when_wrong));
        else
            binding.tvMsg.setText(getResources().getString(R.string.no_internet));
    }

    private void showProgress() {
        binding.recyclerView.setVisibility(View.GONE);
        binding.layProgress.setVisibility(View.VISIBLE);
        binding.pg.setVisibility(View.VISIBLE);
        binding.tvMsg.setText("Please Wait");
    }

    public void refreshData(boolean b) {
        reset_params();
        if (b)
            fetchData();
    }

    public void reset_params() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        isFirstTime = true;
        nursesAdapter.clear();
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
            refreshData(true);
        }
        Log.d("TAG", "onResume: nb ");
    }

    @Override
    public void onPause() {
        super.onPause();
        isFragActive = false;
        Log.d("TAG", "onPause: nb");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("TAG", "onViewCreated: NB ");
    }

    private boolean check_Any_is_empty() {

        if (getEmptyCall(viewModel.list_State) && getEmptyCall1(viewModel.list_speciality)
                && getEmptyCall2(viewModel.list_days_of_week) && getEmptyCall1(viewModel.list_Certificate)) {
            return true;
        }
        return false;
    }

    private boolean getEmptyCall2(MutableLiveData<List<HourlyRate_DayOfWeek_OptionDatum>> list_State) {
        if (list_State == null || list_State.getValue() == null || list_State.getValue().size() == 0) {
            return true;
        }
        return false;
    }

    private boolean getEmptyCall1(MutableLiveData<List<CommonDatum>> list_State) {
        if (list_State == null || list_State.getValue() == null || list_State.getValue().size() == 0) {
            return true;
        }
        return false;
    }

    private boolean getEmptyCall(MutableLiveData<List<State_Datum>> list_State) {
        if (list_State == null || list_State.getValue() == null || list_State.getValue().size() == 0) {
            return true;
        }
        return false;
    }

}