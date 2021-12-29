package com.weboconnect.nurseify.screen.facility.add_job_fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.AppController;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.ActiveFAdapter;
import com.weboconnect.nurseify.adapter.CommonDropDownAdapter;
import com.weboconnect.nurseify.adapter.HourlyRateWindowAdapter;
import com.weboconnect.nurseify.adapter.PostedAdapter;
import com.weboconnect.nurseify.adapter.SpecialtyAdapter;
import com.weboconnect.nurseify.common.CommonDatum;
import com.weboconnect.nurseify.databinding.ActivityAddJob1Binding;
import com.weboconnect.nurseify.databinding.FragmentMyJobsFBinding;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.screen.facility.AddJob2Activity;
import com.weboconnect.nurseify.screen.facility.AddJobActivity1;
import com.weboconnect.nurseify.screen.facility.Add_Jobs_Activity;
import com.weboconnect.nurseify.screen.facility.RegistrationFActivity;
import com.weboconnect.nurseify.screen.facility.profile_setup_dialog.ProfileSetupDialog_1;
import com.weboconnect.nurseify.screen.facility.viewModel.Add_Job_ViewModel;
import com.weboconnect.nurseify.screen.facility.viewModel.DialogStatus;
import com.weboconnect.nurseify.screen.facility.viewModel.DialogStatusMessage;
import com.weboconnect.nurseify.screen.facility.viewModel.ProgressUIType;
import com.weboconnect.nurseify.screen.nurse.RegisterActivity;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_DayOfWeek_OptionModel;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_DayOfWeek_OptionModel.HourlyRate_DayOfWeek_OptionDatum;
import com.weboconnect.nurseify.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

public class Add_Job_1_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    ActivityAddJob1Binding binding;
    View view;
    Add_Job_ViewModel viewModel;
    private SpecialtyAdapter daysOfWeekAdapter;

    public Add_Job_1_Fragment() {

    }

    public static Add_Job_1_Fragment newInstance(int param1, String param2) {
        Add_Job_1_Fragment fragment = new Add_Job_1_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1, 0);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_add_job1, null, false);
        viewModel = new ViewModelProvider(requireActivity()).get(Add_Job_ViewModel.class);
        setData();
        observer_view();
        click();
        return view = binding.getRoot();
    }

    private void setData() {
        AppController appController = (AppController) getActivity().getApplication();
        if (!checkDataEmpty()) {
            viewModel.fetch_add_job_data(appController);
        } else {
            viewModel.list_assignment_durations.setValue(appController.getList_assignment_durations());
            viewModel.list_senior_level.setValue(appController.getList_senior_level());
            viewModel.list_job_funcs.setValue(appController.getList_job_funcs());
            viewModel.list_preferred_shift.setValue(appController.getList_preferred_shift());
            viewModel.list_speciality.setValue(appController.getList_speciality());
            viewModel.list_work_loc.setValue(appController.getList_work_loc());
            viewModel.list_days_of_week.setValue(appController.getList_days_of_week());
        }
        daysOfWeekAdapter = new SpecialtyAdapter(getContext(), viewModel.select_daysOfWeek,
                viewModel.list_days_of_week.getValue(), 3, 3, new SpecialtyAdapter.SpecialtyListener() {
            @Override
            public void onClickItem(int position) {
                if (viewModel.select_daysOfWeek != null && viewModel.select_daysOfWeek.size() != 0 && position < viewModel.select_daysOfWeek.size()) {
                    viewModel.select_daysOfWeek.remove(position);
                    if (daysOfWeekAdapter != null)
                        daysOfWeekAdapter.notifyDataSetChanged();
                    if (binding.tvWeeksDays != null && binding.rvWeeksDays != null)
                        if (viewModel.select_daysOfWeek == null || viewModel.select_daysOfWeek.size() == 0) {
                            binding.tvWeeksDays.setVisibility(View.VISIBLE);
                            binding.rvWeeksDays.setVisibility(View.GONE);
                        } else {
                            binding.tvWeeksDays.setVisibility(View.GONE);
                            binding.rvWeeksDays.setVisibility(View.VISIBLE);
                        }
                }
            }
        });

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
        parentChildAdapter = new HourlyRateWindowAdapter((Add_Jobs_Activity) getActivity(),
                type, type, type,
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

    private boolean checkDataEmpty() {
        AppController appController = (AppController) getActivity().getApplication();
        int status = 0;
        if (appController.getList_assignment_durations() != null
                && appController.getList_assignment_durations().size() != 0) {
            status++;
        }
        if (appController.getList_senior_level() != null
                && appController.getList_senior_level().size() != 0) {
            status++;
        }
        if (appController.getList_job_funcs() != null
                && appController.getList_job_funcs().size() != 0) {
            status++;
        }
        if (appController.getList_speciality() != null
                && appController.getList_speciality().size() != 0) {
            status++;
        }
        if (appController.getList_preferred_shift() != null
                && appController.getList_preferred_shift().size() != 0) {
            status++;
        }
        if (appController.getList_work_loc() != null
                && appController.getList_work_loc().size() != 0) {
            status++;
        }
        if (appController.getList_days_of_week() != null
                && appController.getList_days_of_week().size() != 0) {
            status++;
        }
        if (status == 7) {
            return true;
        } else
            return false;
    }

    private void observer_view() {
        binding.layProgress.setOnTouchListener(touchListner);
        viewModel.getProgressBar().observe(requireActivity(), new Observer<ProgressUIType>() {
            @Override
            public void onChanged(ProgressUIType progressUIType) {
                if (progressUIType == ProgressUIType.SHOW) {
                    binding.layProgress.setVisibility(View.VISIBLE);
                } else if (progressUIType == ProgressUIType.DIMISS) {
                    binding.layProgress.setVisibility(View.GONE);
                } else if (progressUIType == ProgressUIType.CANCEL) {
                    binding.layProgress.setVisibility(View.GONE);
                } else if (progressUIType == ProgressUIType.DATA_ERROR) {
                    binding.layProgress.setVisibility(View.GONE);
                }
            }
        });
    }

    private void click() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.do_DismissDialog(new DialogStatusMessage(DialogStatus.Close, mParam1));

            }
        });
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.do_DismissDialog(new DialogStatusMessage(DialogStatus.Dismiss, mParam1));

            }
        });
        binding.layAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup(getContext(), binding.view1, 1, binding.img1, binding.spinnerAssignment,
                        viewModel.list_assignment_durations.getValue(), viewModel.selected_assignment_duration
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_assignment_duration = position;
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding.laySeniorityLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup(getContext(), binding.view2, 2, binding.img2, binding.spinnerSeniorityLevel,
                        viewModel.list_senior_level.getValue(), viewModel.selected_senior_level
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_senior_level = position;
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding.layJobFuncs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup(getContext(), binding.view3, 3, binding.img3, binding.spinnerJobFuncs,
                        viewModel.list_job_funcs.getValue(), viewModel.selected_job_funcs
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_job_funcs = position;
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding.laySpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup(getContext(), binding.view4, 4, binding.img4, binding.tvSpecialty,
                        viewModel.list_speciality.getValue(), viewModel.selected_speciality
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_speciality = position;
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding.layShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup(getContext(), binding.view5, 5, binding.img5, binding.spinnerShiftDuration,
                        viewModel.list_preferred_shift.getValue(), viewModel.selected_shift_duration
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_shift_duration = position;
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding.layWorkLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup(getContext(), binding.view6, 6, binding.img6, binding.spinnerWorkLoc,
                        viewModel.list_work_loc.getValue(), viewModel.selected_work_loc
                        , new ItemCallback() {
                            @Override
                            public void onClick(int position) {
                                viewModel.selected_work_loc = position;
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
        binding.layWeeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionPopup_Weeks(getContext(), 4, binding.view8, binding.img8, binding.tvWeeksDays,
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
                                binding.tvWeeksDays.setVisibility(View.GONE);
                                binding.rvWeeksDays.setVisibility(View.VISIBLE);
                                daysOfWeekAdapter.notifyDataSetChanged();
                            }
                        });
                Utils.onClickEvent(v);
            }
        });
    }

    private void showOptionPopup(Context context, View v, int type, ImageView img1,
                                 TextView tvEmr, List<CommonDatum> cityData, int selected_City, ItemCallback itemCallback) {
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

        CommonDropDownAdapter parentChildAdapter
                = new CommonDropDownAdapter((Add_Jobs_Activity)getActivity()
                , type
                , selected_City, cityData, new CommonDropDownAdapter.CommonDropDownInterface() {
            @Override
            public void onCLickItem(int position, int i) {
                tvEmr.setText(cityData.get(position).getName());
                itemCallback.onClick(position);
                popup.dismiss();
            }
        });
        recyclerView.setAdapter(parentChildAdapter);
        recyclerView.scrollToPosition(selected_City);
        popup.showAsDropDown(v, 0, 0);
    }

    private View.OnTouchListener touchListner = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };
}